package hello.world.model;

public class Movie {
    private String id;
    private String name;
    private Category category;
    private Integer duration;
    private Integer releaseYear;
    private String description;
    private Director director;

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }

    public Category getCategory() { return this.category; }
    public void setCategory(Category category) { this.category = category; }

    public Integer getDuration() { return this.duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Integer getReleaseYear() { return this.releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Director getDirector() { return this.director; }
    public void setDirector(Director director) { this.director = director; }
}
