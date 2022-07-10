package com.gmail.guymarshall9.timetablescheduler.data;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Teacher extends DatabaseTable
{
	private enum CSVHeaders
	{
		ID, FirstName, MiddleNames, Surname, Initials, TeacherTypeID, SubjectTaughtIDs, RoomTaughtIDs
	}

	private int m_nID;                             // Primary key
	private String m_sFirstName;
	private String m_sMiddleNames;
	private String m_sSurname;
	private String m_sInitials;
	private int m_nTeacherTypeID;                  // Unresolved foreign key
//private TeacherType m_ttTeacherType;           // Resolved foreign key
	private Set<Integer> m_setSubjectTaughtIDs;    // Unresolved foreign keys
//private Set<Subject> m_setSubjectsTaught;      // Resolved foreign keys
	private Set<Integer> m_setRoomTaughtIDs;       // Unresolved foreign keys
//private Set<Room> m_setRoomsTaught;            // Resolved foreign keys

	private Teacher(int nID, String sFirstName, String sMiddleNames, String sSurname, String sInitials, int nTeacherTypeID,
	 Set<Integer> setSubjectTaughtIDs, Set<Integer> setRoomTaughtIDs)
	{
		m_nID = nID;
		m_sFirstName = sFirstName;
		m_sMiddleNames = sMiddleNames;
		m_sSurname = sSurname;
		m_sInitials = sInitials;
		m_nTeacherTypeID = nTeacherTypeID;

		m_setSubjectTaughtIDs = new LinkedHashSet<>(setSubjectTaughtIDs.size());
		m_setSubjectTaughtIDs.addAll(setSubjectTaughtIDs);

		m_setRoomTaughtIDs = new LinkedHashSet<>(setRoomTaughtIDs.size());
		m_setRoomTaughtIDs.addAll(setRoomTaughtIDs);
	}

	public int getID()
	{
		return m_nID;
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

	public int getTeacherTypeID()
	{
		return m_nTeacherTypeID;
	}

	/*
	public TeacherType getTeacherType()
	{
		return m_ttTeacherType;
	}
	*/

	public Set<Integer> getSubjectTaughtIDs()
	{
		return Collections.unmodifiableSet(m_setSubjectTaughtIDs);
	}

	/*
	public Set<Subject> getSubjectsTaught()
	{
		return Collections.unmodifiableSet(m_setSubjectsTaught);
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
		return getCSVFileName(Teacher.class);
	}

	public static CSVFormat buildCSVFormat()
	{
		return buildCSVFormat(CSVHeaders.class);
	}

	public static Teacher parseCSVRecord(CSVRecord record)
	{
		int          nID                 = parseInteger(Teacher.class, record, CSVHeaders.ID, Integer.valueOf(1));
		String       sFirstName          = record.get(CSVHeaders.FirstName);
		String       sMiddleName         = record.get(CSVHeaders.MiddleNames);
		String       sSurname            = record.get(CSVHeaders.Surname);
		String       sInitials           = record.get(CSVHeaders.Initials);
		int          nTeacherTypeID      = parseInteger(Teacher.class, record, CSVHeaders.TeacherTypeID, Integer.valueOf(1));
		Set<Integer> setSubjectTaughtIDs = parseIDs(Teacher.class, record, CSVHeaders.SubjectTaughtIDs);
		Set<Integer> setRoomTaughtIDs    = parseIDs(Teacher.class, record, CSVHeaders.RoomTaughtIDs);
		return new Teacher(nID, sFirstName, sMiddleName, sSurname, sInitials, nTeacherTypeID, setSubjectTaughtIDs,
		 setRoomTaughtIDs);
	}
}
