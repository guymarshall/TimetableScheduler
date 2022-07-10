package com.gmail.guymarshall9.timetablescheduler.data;

import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.buildCSVFormat;
import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.getCSVFileName;
import static com.gmail.guymarshall9.timetablescheduler.data.DatabaseTable.parseInteger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Curriculum extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, StudentID, SubjectID, NumberOfLessonsPerWeek
	}

	private int m_nID;                       // Primary key
	private String m_sStudentID;
	private int m_nSubjectID;
	private int m_nNumberOfLessonsPerWeek;

	private Curriculum(int nID, String sStudentID, int nSubjectID, int nNumberOfLessonsPerWeek)
	{
		m_nID = nID;
		m_sStudentID = sStudentID;
		m_nSubjectID = nSubjectID;
		m_nNumberOfLessonsPerWeek = nNumberOfLessonsPerWeek;
	}

	public int getID()
	{
		return m_nID;
	}

	public String getStudentID()
	{
		return m_sStudentID;
	}

	public int getSubjectID()
	{
		return m_nSubjectID;
	}

	public int getNumberOfLessonsPerWeek()
	{
		return m_nNumberOfLessonsPerWeek;
	}

	public static String getCSVFileName()
	{
		return getCSVFileName(Curriculum.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static Curriculum parseCSVRecord(CSVRecord record)
	{
		int    nID                     = parseInteger(Curriculum.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String sStudentID              = record.get(CSVHeaders.StudentID);
		int    nSubjectID              = parseInteger(Curriculum.class, record, CSVHeaders.SubjectID, Integer.valueOf(1));
		int    nNumberOfLessonsPerWeek = parseInteger(Curriculum.class, record, CSVHeaders.NumberOfLessonsPerWeek, Integer.valueOf(1));
		return new Curriculum(nID, sStudentID, nSubjectID, nNumberOfLessonsPerWeek);
	}
}
