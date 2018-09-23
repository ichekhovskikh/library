package com.haulmont.testtask;

public final class SqlQueries {

    private SqlQueries() {
    }

    public static String getPublisherFilterQuery() {
        return "SELECT * FROM Publisher " +
                "WHERE UPPER(Publisher.name) LIKE UPPER(:name)";
    }

    public static String getGenreFilterQuery() {
        return "SELECT * FROM Genre " +
                "WHERE UPPER(Genre.name) LIKE UPPER(:name)";
    }

    public static String getAuthorFilterQuery() {
        return "SELECT * FROM Author " +
                "WHERE UPPER(Author.name) LIKE UPPER(:name) " +
                "AND UPPER(Author.patronymic) LIKE UPPER(:patronymic) " +
                "AND UPPER(Author.username) LIKE UPPER(:username)";
    }

    public static String getBookFilterQuery() {
        return "SELECT * FROM Book " +
                "WHERE UPPER(Book.name) LIKE UPPER(:book_name) " +
                "AND UPPER(Book.city) LIKE UPPER(:city) " +
                "AND UPPER(CAST(Book.year AS VARCHAR(4))) LIKE UPPER(:year) " +
                "AND (Book.publisher_id = :publisher_id OR :publisher_id = 0) " +
                "AND (Book.author_id = :author_id OR :author_id = 0) " +
                "AND (Book.genre_id = :genre_id OR :genre_id = 0)";
    }
}
