package com.gmail.guymarshall9.timetablescheduler.data;

import java.util.ArrayList;
import java.util.List;

public class DataStore
{
	private List<TeacherType> m_liTeacherTypes = null;
	private List<Teacher> m_liTeachers = null;
	private List<Room> m_liRooms = null;
	private List<Subject> m_liSubjects = null;
	private List<Student> m_liStudents = null;
	private List<PeriodSchedule> m_liPeriodSchedules = null;
	private List<Curriculum> m_liCurricula = null;

	public DataStore()
	{
		m_liTeacherTypes = new ArrayList<>();
		m_liTeachers = new ArrayList<>();
		m_liRooms = new ArrayList<>();
		m_liSubjects = new ArrayList<>();
		m_liStudents = new ArrayList<>();
		m_liPeriodSchedules = new ArrayList<>();
		m_liCurricula = new ArrayList<>();
	}

	public void clearAllData()
	{
		m_liTeacherTypes.clear();
		m_liTeachers.clear();
		m_liRooms.clear();
		m_liSubjects.clear();
		m_liStudents.clear();
		m_liPeriodSchedules.clear();
		m_liCurricula.clear();
	}

	public void addTeacherType(TeacherType ttTeacherType)
	{
		m_liTeacherTypes.add(ttTeacherType);
	}

	public void addTeacher(Teacher tTeacher)
	{
		m_liTeachers.add(tTeacher);
	}

	public void addRoom(Room rRoom)
	{
		m_liRooms.add(rRoom);
	}

	public void addSubject(Subject subSubject)
	{
		m_liSubjects.add(subSubject);
	}

	public void addStudent(Student stuStudent)
	{
		m_liStudents.add(stuStudent);
	}

	public void addPeriodSchedule(PeriodSchedule psPeriodSchedule)
	{
		m_liPeriodSchedules.add(psPeriodSchedule);
	}

	public void addCurriculum(Curriculum curCurriculum)
	{
		m_liCurricula.add(curCurriculum);
	}
}
