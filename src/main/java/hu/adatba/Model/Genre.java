package hu.adatba.Model;

public class Genre {
    private String GenreName;
    private String SubGenreName;

    public Genre(String genreName, String subGenreName) {
        GenreName = genreName;
        SubGenreName = subGenreName;
    }

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        GenreName = genreName;
    }

    public String getSubGenreName() {
        return SubGenreName;
    }

    public void setSubGenreName(String subGenreName) {
        SubGenreName = subGenreName;
    }
}
