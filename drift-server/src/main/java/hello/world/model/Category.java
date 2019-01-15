package hello.world.model;

public class Category {
    private String id;
    private String name;
    private String description;

    public Category() {
        super();
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
}
