package com.gmail.guymarshall9.timetablescheduler.data;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Subject extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, SubjectName, SubjectYear, Set, MaximumClassSize, RoomsTaught
	}

	private int m_nID;                          // Primary key
	private String m_sSubjectName;
	private int m_nSubjectYear;
	private String m_sSet;
	private int m_nMaximumClassSize;
//private Set<Teacher> m_setTeachers;         // Resolved foreign keys
	private Set<Integer> m_setRoomTaughtIDs;    // Unresolved foreign keys
//private Set<Room> m_setRoomsTaught;         // Resolved foreign keys

	private Subject(int nID, String sSubjectName, int nSubjectYear, String sSet, int nMaximumClassSize,
	 Set<Integer> setRoomTaughtIDs)
	{
		m_nID = nID;
		m_sSubjectName = sSubjectName;
		m_nSubjectYear = nSubjectYear;
		m_sSet = sSet;
		m_nMaximumClassSize = nMaximumClassSize;

		m_setRoomTaughtIDs = new LinkedHashSet<>(setRoomTaughtIDs.size());
		m_setRoomTaughtIDs.addAll(setRoomTaughtIDs);
	}

	public int getID()
	{
		return m_nID;
	}

	public String getSubjectName()
	{
		return m_sSubjectName;
	}

	public int getSubjectYear()
	{
		return m_nSubjectYear;
	}

	public String getSet()
	{
		return m_sSet;
	}

	public int getMaximumClassSize()
	{
		return m_nMaximumClassSize;
	}

	/*
	public Set<Teacher> getTeachers()
	{
		return Collections.unmodifiableSet(m_setTeachers);
	}
	*/

	public Set<Integer> getRoomTaughtIDs()
	{
		return Collections.unmodifiableSet(m_setRoomTaughtIDs);
	}

	/*
	public Set<Room> getRoomsTaught()
	{
		return Collections.unmodifiableSet(m_setRoomsTaught);
	}
	*/

	public static String getCSVFileName()
	{
		return getCSVFileName(Subject.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static Subject parseCSVRecord(CSVRecord record)
	{
		int          nID               = parseInteger(Subject.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String       sSubjectName      = record.get(CSVHeaders.SubjectName);
		int          nSubjectYear      = parseInteger(Subject.class, record, CSVHeaders.SubjectYear, Integer.valueOf(1));
		String       sSet              = record.get(CSVHeaders.Set);
		int          nMaximumClassSize = parseInteger(Subject.class, record, CSVHeaders.MaximumClassSize, Integer.valueOf(1));
		Set<Integer> setRoomTaughtIDs  = parseIDs(Subject.class, record, CSVHeaders.RoomsTaught);
		return new Subject(nID, sSubjectName, nSubjectYear, sSet, nMaximumClassSize, setRoomTaughtIDs);
	}
}
