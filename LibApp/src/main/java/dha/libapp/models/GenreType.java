package dha.libapp.models;

public class GenreType {
    private int genreId;
    private String genreName;
    private float weight;

    public GenreType() {
    }

    public GenreType(int genreId, String genreName, float weight) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.weight = weight;
    }

    public GenreType(GenreType genreType) {
        this.genreId = genreType.getGenreId();
        this.genreName = genreType.getGenreName();
        this.weight = genreType.getWeight();
    }

    public int getGenreId() {
        return genreId;
    }

    public GenreType setGenreId(int genreId) {
        this.genreId = genreId;
        return this;
    }

    public String getGenreName() {
        return genreName;
    }

    public GenreType setGenreName(String genreName) {
        this.genreName = genreName;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public GenreType setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    public String toString() {
        return  "["
                + "genreId: " + genreId + ", "
                + "genreName: " + genreName + ", "
                + "weight: " + weight
                + "]\n";
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenreType genreType) {
            return this.genreId == genreType.getGenreId()
                    && this.genreName.equals(genreType.getGenreName());
        }
        return false;
    }
}
