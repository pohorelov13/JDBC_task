package demo;

public class Lesson {
    private Integer id;
    private final String name;
    private final String updatedAt;
    private final Homework homework;


    public String getUpdatedAt() {
        return updatedAt;
    }


    public Lesson(String name, String updatedAt, Homework homework) {
        this.name = name;
        this.updatedAt = updatedAt;
        this.homework = homework;
    }


    public Lesson(Integer id, String name, String updatedAt, Homework homework) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.homework = homework;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Homework getHomework() {
        return homework;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updatedAt=" + updatedAt +
                ", homework=" + homework +
                '}';
    }
}