package com.gmail.guymarshall9.timetablescheduler.data;

import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.buildCSVFormat;
import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.getCSVFileName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Student extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, FirstName, MiddleNames, Surname, Initials
	}

	private String m_sID;             // Primary key
	private String m_sFirstName;
	private String m_sMiddleNames;
	private String m_sSurname;
	private String m_sInitials;

	private Student(String sID, String sFirstName, String sMiddleNames, String sSurname, String sInitials)
	{
		m_sID = sID;
		m_sFirstName = sFirstName;
		m_sMiddleNames = sMiddleNames;
		m_sSurname = sSurname;
		m_sInitials = sInitials;
	}

	public String getID()
	{
		return m_sID;
	}

	public String getFirstName()
	{
		return m_sFirstName;
	}

	public String getMiddleNames()
	{
		return m_sMiddleNames;
	}

	public String getSurname()
	{
		return m_sSurname;
	}

	public String getInitials()
	{
		return m_sInitials;
	}

	public static String getCSVFileName()
	{
		return getCSVFileName(Student.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static Student parseCSVRecord(CSVRecord record)
	{
		String       sID          = record.get(CSVHeaders.ID);
		String       sFirstName   = record.get(CSVHeaders.FirstName);
		String       sMiddleNames = record.get(CSVHeaders.MiddleNames);
		String       sSurname     = record.get(CSVHeaders.Surname);
		String       sInitials    = record.get(CSVHeaders.Initials);
		return new Student(sID, sFirstName, sMiddleNames, sSurname, sInitials);
	}
}
