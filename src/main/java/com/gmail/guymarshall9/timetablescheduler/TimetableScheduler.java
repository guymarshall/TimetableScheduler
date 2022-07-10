package com.gmail.guymarshall9.timetablescheduler;

import com.gmail.guymarshall9.timetablescheduler.data.DataStore;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimetableScheduler
{
	private static final Logger logger = LoggerFactory.getLogger(TimetableScheduler.class);
	private DataStore m_dsDataStore = null;

	public TimetableScheduler()
	{
		m_dsDataStore = new DataStore();
	}

	public static void main(String[] asArgs) throws IOException
	{
		TimetableScheduler ts = new TimetableScheduler();
		ts.execute(asArgs);
	}

	private void execute(String[] asArgs) throws IOException
	{
		StartParameters spStartParams = new StartParameters();
		spStartParams.showUsage();
		String sError = spStartParams.parseArguments(asArgs);

		if (sError.isEmpty())
		{
			String sDataSubfolderName = spStartParams.getDataSubfolderName();

			logger.info(String.format("Parameter values:"
			 + "%n  %s = \"%s\"."
			 + "%n%nTo pause execution enter \"P\"."
			 + "%nFrom a paused execution, enter \"S\" to stop execution and anything else to resume execution.",
			 StartParameters.S_ARG_NAME_DATA_SUBFOLDER_NAME, sDataSubfolderName));

			Supervisor supervisor = new Supervisor(spStartParams, m_dsDataStore);
			WorkerResult wrResult = supervisor.execute();
			boolean bProcessingCompleted = wrResult.getProcessingCompleted();
			int nRun = wrResult.getRun();
			Throwable th = wrResult.getThrowable();

			String sFormat;
			if (bProcessingCompleted)
				sFormat = "Processing completed. The latest run number executed was %d.";
			else
				sFormat = "Processing was stopped before it completed. The latest run number executed was %d.";

			logger.info(String.format(sFormat, nRun));

			StringBuilder sb = new StringBuilder();

			while (th != null)
			{
				if (sb.length() == 0)
					sb.append(String.format("An exception or error was thrown: "));
				else
					sb.append(String.format("caused by: "));

				sb.append(String.format("\"%s\"%n with stack trace:%n", th.toString()));
				StackTraceElement[] asteStackTraceElements = th.getStackTrace();

				for (StackTraceElement steStackTraceElement: asteStackTraceElements)
					sb.append(String.format("  %s%n", steStackTraceElement.toString()));

				th = th.getCause();
			}

			sError = sb.toString();
		}

		if (!sError.isEmpty())
			logger.error(sError);
	}
}
