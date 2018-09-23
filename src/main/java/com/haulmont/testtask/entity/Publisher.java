package com.haulmont.testtask.entity;

import com.google.gwt.thirdparty.guava.common.collect.Sets;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Publisher", schema = "public")
public class Publisher implements com.haulmont.testtask.entity.Entity {
    private long id;
    private String name;
    private Set<Book> books;

    public Publisher() {
        this.books = Sets.newHashSet();
    }

    public Publisher(long id) {
        this.id = id;
        this.books = Sets.newHashSet();
    }

    public Publisher(String name) {
        this.name = name;
        this.books = Sets.newHashSet();
    }

    public Publisher(long id, String name) {
        this.id = id;
        this.name = name;
        this.books = Sets.newHashSet();
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

    @OneToMany(mappedBy = "publisher")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return name;
    }
}
