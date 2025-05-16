package hu.adatba.Model;

public class Genre {
    private int GenreID;
    private String GenreName;
    private String SubGenreName;

    public Genre(String genreName, String subGenreName) {
        GenreName = genreName;
        SubGenreName = subGenreName;
    }

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreID(int genreID) {
        GenreID = genreID;
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
