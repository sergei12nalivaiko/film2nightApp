package by.itechart.film2nightApp.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int kinopoiskId;
    @Column(nullable = false)
    private String nameOrigin;
    @Column(nullable = false)
    private String posterUrl;
    @Column(nullable = false)
    private Float ratingKinopoisk;
    @Column(nullable = false)
    private int ratingKinopoiskVoteCount;
    @Column(nullable = false)
    private String webUrl;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int filmLength;
    @Column(nullable = false)
    private String lastSync;
    @Column(nullable = false)
    private String isBlocked;

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getNameOrigin() {
        return nameOrigin;
    }

    public void setNameOrigin(String nameOrigin) {
        this.nameOrigin = nameOrigin;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Float getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(Float ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }

    public int getRatingKinopoiskVoteCount() {
        return ratingKinopoiskVoteCount;
    }

    public void setRatingKinopoiskVoteCount(int ratingKinopoiskVoteCount) {
        this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(int filmLength) {
        this.filmLength = filmLength;
    }

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && kinopoiskId == film.kinopoiskId &&
                ratingKinopoiskVoteCount == film.ratingKinopoiskVoteCount &&
                year == film.year && filmLength == film.filmLength && Objects.equals(nameOrigin, film.nameOrigin) &&
                Objects.equals(posterUrl, film.posterUrl) && Objects.equals(ratingKinopoisk, film.ratingKinopoisk) &&
                Objects.equals(webUrl, film.webUrl) && Objects.equals(lastSync, film.lastSync) &&
                Objects.equals(isBlocked, film.isBlocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kinopoiskId, nameOrigin, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount, webUrl,
                year, filmLength, lastSync, isBlocked);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", kinopoiskId=" + kinopoiskId +
                ", nameOrigin='" + nameOrigin + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", ratingKinopoiskVoteCount=" + ratingKinopoiskVoteCount +
                ", webUrl='" + webUrl + '\'' +
                ", year=" + year +
                ", filmLength=" + filmLength +
                ", lastSync='" + lastSync + '\'' +
                ", isBlocked='" + isBlocked + '\'' +
                '}';
    }
}
