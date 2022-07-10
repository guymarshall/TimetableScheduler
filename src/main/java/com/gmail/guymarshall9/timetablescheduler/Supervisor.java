package com.gmail.guymarshall9.timetablescheduler;

import com.gmail.guymarshall9.timetablescheduler.data.DataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.gmail.guymarshall9.timetablescheduler.Supervisor.ExecutionCommand.CONTINUE;
import static com.gmail.guymarshall9.timetablescheduler.Supervisor.ExecutionCommand.PAUSE;
import static com.gmail.guymarshall9.timetablescheduler.Supervisor.ExecutionCommand.STOP;

public class Supervisor
{
	public enum ExecutionCommand
	{
		CONTINUE(""), PAUSE("P"), STOP("S");

		private String m_sValue = "";

		ExecutionCommand(String sValue)
		{
			m_sValue = sValue;
		}

		public String value()
		{
			return m_sValue;
		}

		public static ExecutionCommand parse(String sValue)
		{
			ExecutionCommand ecResult = CONTINUE;

			if (sValue == null)
				sValue = "";

			if (sValue.equalsIgnoreCase(PAUSE.value()))
				ecResult = PAUSE;
			else if (sValue.equalsIgnoreCase(STOP.value()))
				ecResult = STOP;

			return ecResult;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(Supervisor.class);
	private StartParameters m_spStartParameters = null;
	private BufferedReader m_brReader = null;
	private DataStore m_dsDataStore = null;

	public Supervisor(StartParameters spStartParameters, DataStore dsDataStore)
	{
		m_spStartParameters = spStartParameters;
		InputStreamReader isrReader = new InputStreamReader(System.in);
		m_brReader = new BufferedReader(isrReader);
		m_dsDataStore = dsDataStore;
	}

	public WorkerResult execute() throws IOException
	{
		WorkerResult srResult = null;
		Worker worker = new Worker(m_spStartParameters, 0, m_dsDataStore);
		Thread thread = new Thread(worker);
		thread.setUncaughtExceptionHandler(worker.getWorkerUncaughtExceptionHandler());
		thread.start();

		ExecutionCommand ecState = CONTINUE;
		ExecutionCommand ecStatePrevious = ecState;

		while (ecState != STOP)
		{
			String sMsg;
			if (ecState == ecStatePrevious)
				sMsg = String.format("The current processing state is \"%s\".", ecState.toString());
			else
				sMsg = String.format("The processing state has changed from \"%s\" to \"%s\".",
				 ecStatePrevious.toString(), ecState.toString());
			logger.info(sMsg);

			ecStatePrevious = ecState;
			ExecutionCommand ecKey = CONTINUE;
			boolean bLoop = true;

			while (bLoop)
			{
				WorkerResult wrWorkerResult = worker.getWorkerResult();
				boolean bProcessingCompleted = (wrWorkerResult != null)
				 && (wrWorkerResult.getProcessingCompleted() || (wrWorkerResult.getThrowable() != null));

				if (bProcessingCompleted)
				{
					ecState = PAUSE;
					ecKey = STOP;
					bLoop = false;
				}
				else if ((ecState == PAUSE) || m_brReader.ready())
				{
					if (!m_brReader.ready())
						logger.info("Waiting for character input...");

					String sInput = m_brReader.readLine();
					ecKey = ExecutionCommand.parse(sInput);

					if (sInput.isEmpty())
						sInput = "[nothing]";
					else
						sInput = String.format("\"%s\"", sInput.toUpperCase());

					logger.info(String.format("Your entry of %s is interpreted as the command \"%s\".",
					 sInput, ecKey.toString()));

					bLoop = false;
				}
				else
					try
					{
						Thread.sleep(1000);    // 1s
					}
					catch (InterruptedException e)
					{
						// Do nothing
					}
			}

			switch (ecState)
			{
				case CONTINUE:
					if (ecKey == PAUSE)
					{
						ecState = ecKey;
						worker.stopExecution();
					}
					break;
				case PAUSE:
					boolean bStopped = worker.getStopped();

					while (!bStopped)
					{
						logger.info("Waiting for the processing to stop...");

						try
						{
							thread.join();
						}
						catch (InterruptedException e)
						{
							// Do nothing
						}

						bStopped = worker.getStopped();    // This should be true always
					}

					if ((ecKey == CONTINUE) || (ecKey == STOP))
					{
						ecState = ecKey;
						WorkerResult wrWorkerResult = worker.getWorkerResult();
						boolean bProcessingCompleted = wrWorkerResult.getProcessingCompleted()
						 || (wrWorkerResult.getThrowable() != null);

						if ((ecKey == STOP) || bProcessingCompleted)
						{
							ecState = STOP;
							srResult = wrWorkerResult;
						}
						else if (ecKey == CONTINUE)
						{
							int nRun = wrWorkerResult.getRun();
							worker = new Worker(m_spStartParameters, nRun, m_dsDataStore);
							thread = new Thread(worker);
							thread.setUncaughtExceptionHandler(worker.getWorkerUncaughtExceptionHandler());
							thread.start();
						}
					}
					break;
				default:
					break;
			}
		}

		m_brReader.close();
		return srResult;
	}
}
