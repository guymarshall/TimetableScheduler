package com.gmail.guymarshall9.timetablescheduler.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVDataFilesParser
{
	public static final String G_S_CSV_FILE_NAME_EXTENSION = ".csv";
	private String m_sDataSubFolderName = "";

	public CSVDataFilesParser(String sDataSubfolderName)
	{
		m_sDataSubFolderName = sDataSubfolderName;
	}

	public static Builder baseBuilder()
	{
		Builder builder = Builder.create(CSVFormat.DEFAULT).setAllowDuplicateHeaderNames(false)
		 .setAllowMissingColumnNames(false).setCommentMarker('#').setIgnoreHeaderCase(true).setIgnoreSurroundingSpaces(true)
		 .setSkipHeaderRecord(true).setTrim(true);
		return builder;
	}

	public void parseAll(DataStore dsDataStore) throws IOException
	{
 // List<DatabaseTable> liTables = Collections.unmodifiableList(Arrays.asList({TeacherType, Teacher}));
 // List<Class<? extends DatabaseTable>> liTables = Arrays.asList((TeacherType.class, Teacher.class));

 // List<Class<? extends DatabaseTable>> liTables = new ArrayList<Class<? extends DatabaseTable>>();
 // liTables.add(TeacherType.class);
 // liTables.add(Teacher.class);

 // Class[] aclClasses = new Class[] {TeacherType.class, Teacher.class};

 // List<Class<? extends DatabaseTable>> liTables2 = Arrays.asList(TeacherType.class, Teacher.class);
 // liTables2 = Collections.unmodifiableList(liTables2);

		/*
		List<Class<? extends DatabaseTable>> liDBTables =
		 Collections.unmodifiableList(Arrays.asList(TeacherType.class, Teacher.class));

		for (Class<? extends DatabaseTable> classDBTable: liDBTables)
		{
		}
		*/

		dsDataStore.clearAllData();
		CSVFormat format = null;

		for (int i = 0; i <= 6; i++)
		{
			String sFileName = m_sDataSubFolderName + File.separator;

			switch (i)
			{
				case 0:
					sFileName += TeacherType.getCSVFileName();
					format = TeacherType.buildCSVFormat();
					break;
				case 1:
					sFileName += Teacher.getCSVFileName();
					format = Teacher.buildCSVFormat();
					break;
				case 2:
					sFileName += Room.getCSVFileName();
					format = Room.buildCSVFormat();
					break;
				case 3:
					sFileName += Subject.getCSVFileName();
					format = Subject.buildCSVFormat();
					break;
				case 4:
					sFileName += Student.getCSVFileName();
					format = Student.buildCSVFormat();
					break;
				case 5:
					sFileName += PeriodSchedule.getCSVFileName();
					format = PeriodSchedule.buildCSVFormat();
					break;
				case 6:
					sFileName += Curriculum.getCSVFileName();
					format = Curriculum.buildCSVFormat();
					break;
			}

			File file = new File(sFileName);
			FileReader fr = null;
			BufferedReader br = null;
			CSVParser parser = null;

			try
			{
				fr = new FileReader(file, StandardCharsets.UTF_8);
				br = new BufferedReader(fr);
				parser = CSVParser.parse(br, format);

				for (CSVRecord record: parser)
				{
					switch (i)
					{
						case 0:
							TeacherType ttTeacherType = TeacherType.parseCSVRecord(record);
							dsDataStore.addTeacherType(ttTeacherType);
							break;
						case 1:
							Teacher tTeacher = Teacher.parseCSVRecord(record);
							dsDataStore.addTeacher(tTeacher);
							break;
						case 2:
							Room rRoom = Room.parseCSVRecord(record);
							dsDataStore.addRoom(rRoom);
							break;
						case 3:
							Subject subSubject = Subject.parseCSVRecord(record);
							dsDataStore.addSubject(subSubject);
							break;
						case 4:
							Student stuStudent = Student.parseCSVRecord(record);
							dsDataStore.addStudent(stuStudent);
							break;
						case 5:
							PeriodSchedule psPeriodSchedule = PeriodSchedule.parseCSVRecord(record);
							dsDataStore.addPeriodSchedule(psPeriodSchedule);
							break;
						case 6:
							Curriculum curCurriculum = Curriculum.parseCSVRecord(record);
							dsDataStore.addCurriculum(curCurriculum);
							break;
					}

					//
					// Add code to do the second pass (to populate cross-table sets of resolved foreign keys)…
					//

				}
			}
			finally
			{
				if (parser != null)
					parser.close();
				else if (br != null)
					br.close();
				else if (fr != null)
					fr.close();
			}
		}
	}
}
