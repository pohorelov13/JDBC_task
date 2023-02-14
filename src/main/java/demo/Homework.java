package demo;

public class Homework {
    private Integer id;
    private String name;
    private String description;

    public Homework(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Homework(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Homework(int id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}