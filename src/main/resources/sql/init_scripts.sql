CREATE TABLE Author
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) NOT NULL
);

CREATE TABLE Genre
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
    name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX Genre_name_uindex ON Genre (name);

CREATE TABLE Publisher
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE Book
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
    name VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    publisher_id INTEGER NOT NULL,
    author_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES  PUBLISHER(id) ON DELETE RESTRICT,
    FOREIGN KEY (author_id) REFERENCES  AUTHOR(id) ON DELETE RESTRICT,
    FOREIGN KEY (genre_id) REFERENCES  GENRE(id) ON DELETE RESTRICT
);

INSERT INTO "PUBLIC"."PUBLISHER" VALUES (0, 'Москва'), (1, 'Питер'), (2, 'O’Reilly')

INSERT INTO "PUBLIC"."GENRE" VALUES (0, 'Повесть'), (1, 'Рассказ'), (2, 'Роман')

INSERT INTO "PUBLIC"."AUTHOR" VALUES (0, 'Александр', 'Сергеевич', 'Пушкин'), (1, 'Лев','Николаевич', 'Толстой'), (2, 'Иван', 'Сергеевич', 'Тургенев')

INSERT INTO "PUBLIC"."BOOK" VALUES (0, 'Евгений Онегин', 'Кишинев', 1830, 1, 0, 2), (1, 'Борис Годунов', 'Михайловское', 1825, 1, 0, 2), (2, 'Война и Мир', 'Ясная Поляна', 1869, 0, 1, 2), (3, 'Муму', 'Спасское-Лутовиново', 1852, 2, 2, 1)