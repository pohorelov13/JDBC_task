package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    private  Connection connection;

    public LessonDao(Connection connection) {
        this.connection = connection;
    }

    public void addLesson(Lesson lesson) {
        addHomeWork(lesson);
        try (PreparedStatement selectStatement = connection.prepareStatement(
                "SELECT id FROM lesson WHERE name = ? AND updatedAt = ?")) {
            selectStatement.setString(1, lesson.getName());
            selectStatement.setString(2, lesson.getUpdatedAt());
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    lesson.setId(resultSet.getInt("id"));
                    System.out.println("Already exist: " + lesson);
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO lesson(name, updatedAt, homework_id) VALUES(?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS)) {
                        insertStatement.setString(1, lesson.getName());
                        insertStatement.setString(2, lesson.getUpdatedAt());
                        insertStatement.setInt(3, lesson.getHomework().getId());

                        insertStatement.executeUpdate();

                        try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                lesson.setId(generatedKeys.getInt(1));
                            }
                        }
                    }
                    System.out.println("Successfully added: " + lesson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addHomeWork(Lesson lesson) {
        if (lesson.getHomework().getName() != null && lesson.getHomework().getDescription() != null) {
            try (PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT id FROM homework WHERE name = ? AND description = ?;")) {
                selectStatement.setString(1, lesson.getHomework().getName());
                selectStatement.setString(2, lesson.getHomework().getDescription());
                selectStatement.execute();
                try (ResultSet resultSet = selectStatement.getResultSet()) {
                    if (resultSet.next()) {
                        lesson.getHomework().setId(resultSet.getInt("id"));
                    } else {
                        try (PreparedStatement insertStatement = connection.prepareStatement(
                                "INSERT INTO homework (name, description) VALUES(?, ?);",
                                Statement.RETURN_GENERATED_KEYS)) {
                            insertStatement.setString(1, lesson.getHomework().getName());
                            insertStatement.setString(2, lesson.getHomework().getDescription());

                            insertStatement.executeUpdate();
                            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                lesson.getHomework().setId(generatedKeys.getInt(1));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM homework WHERE id = ?")) {
                preparedStatement.setInt(1, lesson.getHomework().getId());
                preparedStatement.execute();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    resultSet.next();
                    lesson.getHomework().setName(resultSet.getString("name"));
                    lesson.getHomework().setDescription(resultSet.getString("description"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void removeLesson(Lesson lesson) {
        try (PreparedStatement selectStatement = connection.prepareStatement(
                "SELECT id FROM lesson WHERE name = ? AND updatedAt = ? AND homework_id = ?")) {
            selectStatement.setString(1, lesson.getName());
            selectStatement.setString(2, lesson.getUpdatedAt());
            selectStatement.setInt(3, lesson.getHomework().getId());

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    try (PreparedStatement preparedStatement = connection.prepareStatement(
                            "DELETE FROM lesson WHERE id = ?")) {
                        preparedStatement.setInt(1, id);
                        preparedStatement.executeUpdate();
                        System.out.println("Successfully removed");
                    }
                } else System.out.println("Missing in database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Lesson> getAllLesson() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT lesson.*, homework.id as homeworkId, homework.name as homeworkName, " +
                            "homework.description " +
                            "FROM lesson " +
                            "JOIN homework " +
                            "ON lesson.homework_id = homework.id;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("updatedAt"), new Homework(resultSet.getInt("homeworkId"),
                        resultSet.getString("homeworkName"),
                        resultSet.getString("description")));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lessons;
    }

    public Lesson getLessonById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT lesson.*, homework.id as homeworkId, homework.name as homeworkName, homework.description" +
                        "                        FROM lesson" +
                        "                        JOIN homework" +
                        "                        ON (lesson.homework_id = homework.id)" +
                        " WHERE lesson.id = ?;")) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return new Lesson(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("updatedAt"),
                        new Homework(resultSet.getInt("homeworkId"),
                                resultSet.getString("homeworkName"),
                                resultSet.getString("description")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}