package com.gmail.guymarshall9.timetablescheduler.data;

import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.buildCSVFormat;
import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.getCSVFileName;
import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.parseInteger;
import java.util.Arrays;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class PeriodSchedule extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, DayOfWeek, NumberOfPeriods
	}

	private enum DayOfWeek
	{
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
	}

	private int m_nID;                   // Primary key
	private DayOfWeek m_dowDayOfWeek;
	private int m_nNumberOfPeriods;

	private PeriodSchedule(int nID, DayOfWeek dowDayOfWeek, int nNumberOfPeriods)
	{
		m_nID = nID;
		m_dowDayOfWeek = dowDayOfWeek;
		m_nNumberOfPeriods = nNumberOfPeriods;
	}

	public int getID()
	{
		return m_nID;
	}

	public DayOfWeek getDayOfWeek()
	{
		return m_dowDayOfWeek;
	}

	public int getNumberOfPeriods()
	{
		return m_nNumberOfPeriods;
	}

	public static String getCSVFileName()
	{
		return getCSVFileName(PeriodSchedule.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static PeriodSchedule parseCSVRecord(CSVRecord record)
	{
		int nID = parseInteger(PeriodSchedule.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String sDayOfWeek = record.get(CSVHeaders.DayOfWeek);

		DayOfWeek dowDayOfWeek;
		try
		{
			dowDayOfWeek = DayOfWeek.valueOf(sDayOfWeek);
		}
		catch (IllegalArgumentException e)
		{
			String sMsg = String.format("Invalid %s %s value of \"%s\" found in record No. %d: \"%s\"."
			 + "%n  This value needs to be one of %s.",
			 PeriodSchedule.class.getSimpleName(), CSVHeaders.DayOfWeek.toString(), sDayOfWeek, record.getRecordNumber(),
			 record.toString(), Arrays.toString(DayOfWeek.values()));

			throw new IllegalArgumentException(sMsg, e);
		}

		int nNumberOfPeriods = parseInteger(PeriodSchedule.class, record, CSVHeaders.NumberOfPeriods, Integer.valueOf(0));
		return new PeriodSchedule(nID, dowDayOfWeek, nNumberOfPeriods);
	}
}
