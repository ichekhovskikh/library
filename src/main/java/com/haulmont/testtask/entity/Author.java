package com.haulmont.testtask.entity;

import com.google.gwt.thirdparty.guava.common.collect.Sets;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Author", schema = "public")
public class Author implements com.haulmont.testtask.entity.Entity {
    private long id;
    private String name;
    private String patronymic;
    private String username;
    private Set<Book> books;

    public Author() {
        books = Sets.newHashSet();
    }

    public Author(long id) {
        this.id = id;
        books = Sets.newHashSet();
    }

    public Author(String name, String patronymic, String username) {
        this.name = name;
        this.username = username;
        this.patronymic = patronymic;
        this.id = -1;
        books = Sets.newHashSet();
    }

    public Author(long id, String name, String patronymic, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.patronymic = patronymic;
        books = Sets.newHashSet();
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
    @Column(name = "patronymic", nullable = false)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "author")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return String.join(" ", name, patronymic, username);
    }
}
