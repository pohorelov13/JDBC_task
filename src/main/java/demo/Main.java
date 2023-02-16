package demo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LessonDao lessonDaoMySQL = null;
        try (DataBaseConnection dbc = new DataBaseConnection()) {
             lessonDaoMySQL = new LessonDao(dbc.getConnection());
            SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Homework homework = new Homework(4);
            Homework homework1 = new Homework("1homework from JDBC", "descriptionJDBC1");
            Homework homework2 = new Homework("2homework from JDBC", "descriptionJDBC2");
            Homework homework3 = new Homework("3homework from JDBC", "descriptionJDBC3");
            Homework homework4 = new Homework("4homework from JDBC", "descriptionJDBC4");
            Homework homework5 = new Homework("5homework from JDBC", "descriptionJDBC5");

            Lesson lesson = new Lesson("LessonJDBC1", myFormatObj.format(new Date(1676356479410L)), homework);
            Lesson lesson2 = new Lesson("LessonJDBC2", myFormatObj.format(new Date(1676366479410L)), homework1);
            Lesson lesson3 = new Lesson("LessonJDBC3", myFormatObj.format(new Date(1676346479410L)), homework2);
            Lesson lesson4 = new Lesson("LessonJDBC4", myFormatObj.format(new Date(1676336479410L)), homework3);
            Lesson lesson5 = new Lesson("LessonJDBC5", myFormatObj.format(new Date(1676326479410L)), homework4);
            Lesson lesson6 = new Lesson("LessonJDBC6", myFormatObj.format(new Date(1676316479410L)), homework5);

            lessonDaoMySQL.addLesson(lesson);
            lessonDaoMySQL.addLesson(lesson2);
            lessonDaoMySQL.addLesson(lesson3);
            lessonDaoMySQL.addLesson(lesson4);
            lessonDaoMySQL.addLesson(lesson5);
            lessonDaoMySQL.addLesson(lesson6);
            lessonDaoMySQL.addLesson(lesson);

            for (Lesson someLesson :
                    lessonDaoMySQL.getAllLesson()) {
                System.out.println(someLesson);
                System.out.println("----------------------------------------------------------");
            }

            lessonDaoMySQL.removeLesson(lesson);

            System.out.println(lessonDaoMySQL.getLessonById(lesson2.getId()));
            try {
               // dbc.close();
                System.out.println(lessonDaoMySQL.getConnection().isClosed());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(lessonDaoMySQL.getConnection().isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
