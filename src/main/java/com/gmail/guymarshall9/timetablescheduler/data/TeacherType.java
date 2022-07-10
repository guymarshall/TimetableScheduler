package com.gmail.guymarshall9.timetablescheduler.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class TeacherType extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, Name, DisplayName
	}

	private int m_nID;                // Primary key
	private String m_sName;
	private String m_sDisplayName;

	private TeacherType(int nID, String sName, String sDisplayName)
	{
		m_nID = nID;
		m_sName = sName;
		m_sDisplayName = sDisplayName;
	}

	public int getID()
	{
		return m_nID;
	}

	public String getName()
	{
		return m_sName;
	}

	public String getDisplayName()
	{
		return m_sDisplayName;
	}

	public static String getCSVFileName()
	{
		return getCSVFileName(TeacherType.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static TeacherType parseCSVRecord(CSVRecord record)
	{
		int    nID          = parseInteger(TeacherType.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String sName        = record.get(CSVHeaders.Name);
		String sDisplayName = record.get(CSVHeaders.DisplayName);
		return new TeacherType(nID, sName, sDisplayName);
	}
}
