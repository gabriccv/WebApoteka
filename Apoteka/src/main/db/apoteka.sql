DROP SCHEMA IF EXISTS apoteka;
CREATE SCHEMA apoteka DEFAULT CHARACTER SET utf8;
USE apoteka;

CREATE TABLE korisnici (
	id BIGINT AUTO_INCREMENT,
	ime VARCHAR(20),
	prezime VARCHAR(20),
	email VARCHAR(20) NOT NULL,
	lozinka VARCHAR(20) NOT NULL,
	PRIMARY KEY(id)

);


CREATE TABLE kategorijeLekova (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(35),
	namena VARCHAR(100),
	opis VARCHAR(100),
	PRIMARY KEY(id)
);

INSERT INTO kategorijeLekova(naziv,namena,opis) VALUES ('panadol', 'bolovi','2-3 dnevno');

