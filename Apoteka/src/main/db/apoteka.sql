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

CREATE TABLE knjige (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(75) NOT NULL,
	registarskiBrojPrimerka VARCHAR(75) NOT NULL,
	jezik VARCHAR(75) NOT NULL,
	brojStranica INT NOT NULL,
	izdata BOOLEAN NOT NULL,
	datum DATE,
	PRIMARY KEY(id)
);

CREATE TABLE autori (
	id BIGINT AUTO_INCREMENT,
	ime VARCHAR(20),
	prezime VARCHAR(20),
	PRIMARY KEY(id)
);

CREATE TABLE kategorijeLekova (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(35),
	namena VARCHAR(100),
	opis VARCHAR(100),
	PRIMARY KEY(id)
);

CREATE TABLE clanskeKarte (
	id BIGINT AUTO_INCREMENT,
	registarskiBroj VARCHAR(25) NOT NULL,
	korisnikId BIGINT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(korisnikId) REFERENCES korisnici(id)
		ON DELETE CASCADE
);

CREATE TABLE knjigeAutori (
    knjigaId BIGINT,
    autorId BIGINT,
    PRIMARY KEY(knjigaId, autorId),
    FOREIGN KEY(knjigaId) REFERENCES knjige(id)
		ON DELETE CASCADE,
    FOREIGN KEY(autorId) REFERENCES autori(id)
		ON DELETE CASCADE
);

CREATE TABLE knjigeClanskeKarte (
    knjigaId BIGINT,
    clanskaKartaId BIGINT,
    PRIMARY KEY(knjigaId, clanskaKartaId),
    FOREIGN KEY(knjigaId) REFERENCES knjige(id)
		ON DELETE CASCADE,
    FOREIGN KEY(clanskaKartaId) REFERENCES clanskeKarte(id)
		ON DELETE CASCADE
);

INSERT INTO korisnici (ime, prezime, email, lozinka) VALUES ('Pera', 'Peric', 'pera@mail.com', 'pera');
INSERT INTO korisnici (ime, prezime, email, lozinka) VALUES ('Mika', 'Mikic', 'mika@mail.com', 'mika');
INSERT INTO korisnici (ime, prezime, email, lozinka) VALUES ('Jova', 'Jovic', 'jova@mail.com', 'jova');

INSERT INTO autori (ime, prezime) VALUES ('Dan', 'Brown');
INSERT INTO autori (ime, prezime) VALUES ('Stephen', 'King');
INSERT INTO autori (ime, prezime) VALUES ('Jo', 'Nesbo');
INSERT INTO autori (ime, prezime) VALUES ('Harper', 'Lee');

INSERT INTO kategorijeLekova(naziv,namena,opis) VALUES ('panadol', 'bolovi','2-3 dnevno');

INSERT INTO knjige (naziv, registarskiBrojPrimerka, jezik, brojStranica, izdata, datum) VALUES ('Inferno', '456/785', 'srpski', 496, false, '2015-06-22');
INSERT INTO knjige (naziv, registarskiBrojPrimerka, jezik, brojStranica, izdata, datum) VALUES ('To kill a mockingbird', '478/956', 'engleski', 320, false, '1980-01-02');
INSERT INTO knjige (naziv, registarskiBrojPrimerka, jezik, brojStranica, izdata, datum) VALUES ('Ostrvo', '985/784', 'srpski', 473, false, '1999-10-01');

INSERT INTO clanskeKarte (registarskiBroj, korisnikId) VALUES ('455/456', 1);
INSERT INTO clanskeKarte (registarskiBroj, korisnikId) VALUES ('456/456', 2);

INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (1, 1);
INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (1, 2);

INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (2, 3);

INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (3, 3);
INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (3, 4);
