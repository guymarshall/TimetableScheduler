package com.gmail.guymarshall9.timetablescheduler;

import com.gmail.guymarshall9.timetablescheduler.data.CSVDataFilesParser;
import com.gmail.guymarshall9.timetablescheduler.data.DataStore;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Runnable
{
	public class WorkerUncaughtExceptionHandler implements UncaughtExceptionHandler
	{
		public WorkerUncaughtExceptionHandler()
		{
		}

		@Override
		public void uncaughtException(Thread t, Throwable th)
		{
			m_WorkerResult = new WorkerResult(m_bProcessingCompleted, th, m_nRun);
			m_bStopped = true;
		}

	}

	private static final Logger logger = LoggerFactory.getLogger(Worker.class);
	private int m_nRun = 0;
	private final int M_N_RUNS = 2;
	private volatile boolean m_bStopping = false;
	private boolean m_bStopped = false;
	private boolean m_bProcessingCompleted = false;
	private WorkerUncaughtExceptionHandler m_wuehExceptionHandler = null;
	private WorkerResult m_WorkerResult = null;
	private String m_sDataSubfolderName = "";
	private DataStore m_dsDataStore = null;

	/**
	 * The constructor.
	 * @param spStartParameters
	 *   The the application's start parameters.
	 * @param nRun
	 *   The number of runs already executed. A value of <code>0</code> means no run has yet been executed.
	 */
	public Worker(StartParameters spStartParameters, int nRun, DataStore dsDataStore)
	{
		m_nRun = nRun;
		m_bStopping = false;
		m_bStopped = false;
		m_bProcessingCompleted = false;
		m_wuehExceptionHandler = new WorkerUncaughtExceptionHandler();
		m_sDataSubfolderName = spStartParameters.getDataSubfolderName();
		m_dsDataStore = dsDataStore;
	}

	public void stopExecution()
	{
		m_bStopping = true;
		logger.info(String.format("Stopping run number %d...", m_nRun));
	}

	/**
	 * @return
	 *   Whether working has stopped, whether all processing has been completed or not.
	 */
	public boolean getStopped()
	{
		return m_bStopped;
	}

	public WorkerUncaughtExceptionHandler getWorkerUncaughtExceptionHandler()
	{
		return m_wuehExceptionHandler;
	}

	public WorkerResult getWorkerResult()
	{
		return m_WorkerResult;
	}

	@Override
	public void run()
	{
		////////////////////////////////////////////////////////////////////////////
		// Replace the use of m_nRun, m_nRuns and the sleep with whatever pausable
		// processing is to be done.
		//

		while ((!m_bStopping) && (m_nRun < M_N_RUNS))
		{
			m_nRun++;
			logger.info(String.format("Started run number %d.", m_nRun));

			switch (m_nRun)
			{
				case 1:
					// Parse CSV files and extract from them
					CSVDataFilesParser parseFiles = new CSVDataFilesParser(m_sDataSubfolderName);
					{
						try
						{
							parseFiles.parseAll(m_dsDataStore);
						}
						catch (IOException e)
						{
							throw new RuntimeException(e);
						}
					}

					//
					// Insert code here…
					//

					break;

				default:
					try
					{
						Thread.sleep(5000);    // 5s
					}
					catch (InterruptedException e)
					{
						// Do nothing
					}

					break;
			}

			logger.info(String.format("Completed run number %d.", m_nRun));

			//
			//////////////////////////////////////////////////////////////////////////
		}

		if (m_nRun >= M_N_RUNS)
		{
			m_bProcessingCompleted = true;
			logger.info("All processing has been completed.");
		}
		else
			logger.info("Stopped before all processing completed.");

		m_WorkerResult = new WorkerResult(m_bProcessingCompleted, null, m_nRun);
		m_bStopped = true;
	}
}
