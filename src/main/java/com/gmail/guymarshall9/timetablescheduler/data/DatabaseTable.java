package com.gmail.guymarshall9.timetablescheduler.data;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVRecord;

public class DatabaseTable
{
	protected static String getCSVFileName(Class<? extends DatabaseTable> clazz)
	{
		return clazz.getSimpleName() + CSVDataFilesParser.G_S_CSV_FILE_NAME_EXTENSION;
	}

	protected static CSVFormat buildCSVFormat(Class<? extends Enum<?>> clazz)
	{
		Builder builder = CSVDataFilesParser.baseBuilder();
		builder.setHeader(clazz);
		return builder.build();
	}

	/*
	public static DatabaseTable parseCSVRecord(CSVRecord record)
	{
		throw new RuntimeException();
	}
	*/

	/**
	 * Parse a value of a record element, which consists of an integer.
	 * @param clazz
	 *   The class, for which a CSV file is being processed.
	 * @param record
	 *   The CSV record to be parsed.
	 * @param enValue
	 *   The value of the CSV record to be parsed.
	 * @param iMinimumValue
	 *   The minimum allowed value of the value to be parsed. If this is <code>null</code> then it will be ignored.
	 * @return
	 *   The <code>int</code> represented by the value to be parsed.
	 */
	protected static int parseInteger(Class<? extends DatabaseTable> clazz, CSVRecord record, Enum<?> enValue,
	 Integer iMinimumValue)
	{
		int nID = 0;
		String sID = record.get(enValue);
		try
		{
			nID = Integer.parseInt(sID);
		}
		catch (NumberFormatException e)
		{
			// Do nothing here
		}

		if ((iMinimumValue != null) && (nID < iMinimumValue.intValue()))
		{
			String sMsg = String.format("Invalid %s %s value of \"%s\" found in record No. %d: \"%s\"."
			 + "%n  This value needs to be a whole number greater than 0.",
			 clazz.getSimpleName(), enValue.toString(), sID, record.getRecordNumber(), record.toString());

			throw new RuntimeException(sMsg);
		}

		return nID;
	}

	/**
	 * Parse a value of a record element, which consists of a comma-separated list of positive integers.
	 * @param clazz
	 *   The class, for which a CSV file is being processed.
	 * @param record
	 *   The CSV record to be parsed.
	 * @param enValue
	 *   The value of the CSV record to be parsed.
	 * @return
	 *   The set of <code>Integer</code>s represented by the value to be parsed.
	 *   If the value is empty then an empty set will be returned.
	 */
	protected static Set<Integer> parseIDs(Class<? extends DatabaseTable> clazz, CSVRecord record, Enum<?> enValue)
	{
		Set<Integer> setResult = new LinkedHashSet<>();

		String sIDs = "";

		try
		{
			sIDs = record.get(enValue);
		}
		catch (IllegalArgumentException e)
		{
			// Do nothing: we return an empty set instead
		}

		String[] asIDs;
		if (!sIDs.isEmpty())
			asIDs = sIDs.split(",", -1);
		else
			asIDs = new String[0];

		for (String sID: asIDs)
		{
			int nID = 0;

			try
			{
				nID = Integer.parseInt(sID.trim());
			}
			catch (NumberFormatException e)
			{
				// Do nothing here
			}

			if (nID < 1)
			{
				String sMsg = String.format("Invalid %s %s value of \"%s\" found in record No. %d: \"%s\"."
				 + "%n  This value needs to be a whole number greater than 0.",
				 clazz.getSimpleName(), enValue.toString(), sID, record.getRecordNumber(), record.toString());

				throw new RuntimeException(sMsg);
			}

			boolean bAdded = setResult.add(Integer.valueOf(nID));

			if (!bAdded)
			{
				String sMsg = String.format("Invalid %s %s value of \"%s\" found in record No. %d: \"%s\"."
				 + "%n  This value is present more than once in the list of ID values.",
				 clazz.getSimpleName(), enValue.toString(), sID, record.getRecordNumber(), record.toString());

				throw new RuntimeException(sMsg);
			}
		}

		return setResult;
	}
}
