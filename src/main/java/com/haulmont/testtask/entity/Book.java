package com.haulmont.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "Book", schema = "public")
public class Book implements com.haulmont.testtask.entity.Entity {
    private long id;
    private String name;
    private String city;
    private int year;
    private Publisher publisher;
    private Author author;
    private Genre genre;

    public Book() {
    }

    public Book(long id) {
        this.id = id;
    }

    public Book(String name, String city, int year, Publisher publisher, Author author, Genre genre) {
        this.name = name;
        this.city = city;
        this.year = year;
        this.publisher = publisher;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String name, String city, int year, Publisher publisher, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.year = year;
        this.publisher = publisher;
        this.author = author;
        this.genre = genre;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return name;
    }
}
