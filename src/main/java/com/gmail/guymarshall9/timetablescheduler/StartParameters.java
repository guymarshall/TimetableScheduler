package com.gmail.guymarshall9.timetablescheduler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartParameters
{
	private static final int N_ARG_PAIRS = 1;
	public static final String S_ARG_NAME_DATA_SUBFOLDER_NAME = "dataSubfolderName";
	private static final String S_ARG_DESC_DATA_SUBFOLDER_NAME = "data subfolder name";

	private static final Logger logger = LoggerFactory.getLogger(StartParameters.class);
	private String m_sDataSubfolderName = "";

	public StartParameters()
	{
	}

	public String getDataSubfolderName()
	{
		return m_sDataSubfolderName;
	}

	public void showUsage()
	{
		String sMsg = String.format(
		   "%nUsage"
		 + "%n-----"
		 + "%n  %s %s [%s]%n"
		 + "%n[%3$s] is the name of the subfolder where the data CSV files are to be present for reading."
		 + "%n",
		 TimetableScheduler.class.getSimpleName(),
		 S_ARG_NAME_DATA_SUBFOLDER_NAME, S_ARG_DESC_DATA_SUBFOLDER_NAME);

		logger.info(sMsg);
	}

	public String parseArguments(String[] asArgs)
	{
		StringBuilder sbError = new StringBuilder();
		logger.info(String.format("About to parse the command line arguments \"%s\".", Arrays.asList(asArgs)));

		if (asArgs.length == N_ARG_PAIRS * 2)
		{
			int nIndexDataSubfolderName = -1;

			for (int i = 0; i <= (N_ARG_PAIRS - 1) * 2; i += 2)
			{
				if (S_ARG_NAME_DATA_SUBFOLDER_NAME.equalsIgnoreCase(asArgs[i]))
					nIndexDataSubfolderName = i + 1;
			}

			if (nIndexDataSubfolderName > -1)
			{
				m_sDataSubfolderName = asArgs[nIndexDataSubfolderName];

				if (m_sDataSubfolderName.isEmpty())
					sbError.append(String.format("The parameter \"%s\" of value \"%s\" must not be empty.",
					 S_ARG_NAME_DATA_SUBFOLDER_NAME, m_sDataSubfolderName));
			}
			else
				sbError.append(String.format("The parameter \"%s\" is missing.", S_ARG_NAME_DATA_SUBFOLDER_NAME));
		}
		else
			sbError.append(String.format("Please specify exactly %d parameters.", N_ARG_PAIRS * 2));

		if (sbError.length() > 0)
			sbError.append(" Please see the program's usage for details.");

		return sbError.toString();
	}
}
