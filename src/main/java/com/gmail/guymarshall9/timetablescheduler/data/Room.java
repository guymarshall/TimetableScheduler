package com.gmail.guymarshall9.timetablescheduler.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Room extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, Name, MaximumClassSize
	}

	private int m_nID;                           // Primary key
	private String m_sName;
	private int m_nMaximumClassSize;
//private Set<Subject> m_setSubjectsTaught;    // Resolved foreign keys
//private Set<Room> m_setRoomsTaught;          // Resolved foreign keys

	private Room(int nID, String sName, int nMaximumClassSize)
	{
		m_nID = nID;
		m_sName = sName;
		m_nMaximumClassSize = nMaximumClassSize;
	}

	public int getID()
	{
		return m_nID;
	}

	public String getName()
	{
		return m_sName;
	}

	public int getMaximumClassSize()
	{
		return m_nMaximumClassSize;
	}

	/*
	public Set<Subject> getSubjectsTaught()
	{
		return Collections.unmodifiableSet(m_setSubjectsTaught);
	}
	*/

	/*
	public Set<Room> getRoomsTaught()
	{
		return Collections.unmodifiableSet(m_setRoomsTaught);
	}
	*/

	public static String getCSVFileName()
	{
		return getCSVFileName(Room.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static Room parseCSVRecord(CSVRecord record)
	{
		int    nID            = parseInteger(Room.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String sName          = record.get(CSVHeaders.Name);
		int    nTeacherTypeID = parseInteger(Room.class, record, CSVHeaders.MaximumClassSize, Integer.valueOf(1));
		return new Room(nID, sName, nTeacherTypeID);
	}
}
