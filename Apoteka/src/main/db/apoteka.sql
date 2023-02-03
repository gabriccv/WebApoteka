DROP SCHEMA IF EXISTS apoteka;
CREATE SCHEMA apoteka DEFAULT CHARACTER SET utf8;
USE apoteka;

CREATE TABLE korisnici (
	id BIGINT AUTO_INCREMENT,
	korisnickoIme VARCHAR(30) NOT NULL,
    lozinka VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
	ime VARCHAR(20),
	prezime VARCHAR(20),
	datumRodjenja date,
    adresa VARCHAR(50),
    brojTelefona VARCHAR(15),
    datumIVremeRegistracije datetime,
    uloga BIGINT NOT NULL,
    FOREIGN KEY(uloga) REFERENCES uloge(id)
		ON DELETE CASCADE,
	PRIMARY KEY(id)

);



CREATE TABLE kategorijeLekova (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(35),
	namena VARCHAR(100),
	opis VARCHAR(100),
	PRIMARY KEY(id)
);


CREATE TABLE oblik (
  id BIGINT AUTO_INCREMENT,
  naziv varchar(50)  NOT NULL,
  PRIMARY KEY (id)
);   

CREATE TABLE proizvodjac(
	id BIGINT AUTO_INCREMENT,
	naziv varchar(50)  NOT NULL,
	drzava VARCHAR(3) NOT NULL,
	PRIMARY KEY(id)
	
	);
    

CREATE TABLE lek (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(40) NOT NULL,
	sifra VARCHAR(14) NOT NULL,
	opis VARCHAR(100) NOT NULL,
	kontraindikacije varchar(50) NULL,
	oblik BIGINT NOT NULL,
	prosekOcena FLOAT NOT NULL,
	slika varchar(50)  NOT NULL,
	dostupnaKolicina INT NOT NULL,
	cena DOUBLE NOT NULL,
	proizvodjac BIGINT NOT NULL,
	kategorijaLeka BIGINT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(oblik) REFERENCES oblik(id)
		ON DELETE CASCADE,
	FOREIGN KEY(proizvodjac) REFERENCES proizvodjac(id)
		ON DELETE CASCADE,
	FOREIGN KEY(kategorijaLeka) REFERENCES kategorijeLekova(id)
		ON DELETE CASCADE
);
CREATE TABLE uloge (
  id BIGINT AUTO_INCREMENT,
  naziv varchar(50)  NOT NULL,
  PRIMARY KEY (id)
);   
insert into uloge(naziv) values ('KUPAC');
insert into uloge(naziv) values ('APOTEKAR');
insert into uloge(naziv) values ('ADMINISTRATOR');

INSERT INTO kategorijeLekova(naziv,namena,opis) VALUES ('panadol', 'bolovi','2-3 dnevno');
INSERT INTO proizvodjac (naziv,drzava) VALUES ('Mika','SRB');
INSERT INTO oblik (naziv) VALUES ('TABLETA');
INSERT INTO oblik (naziv) VALUES ('KAPSULA');
INSERT INTO oblik (naziv) VALUES ('SIRUP');
INSERT INTO oblik (naziv) VALUES ('INJEKCIJA');

INSERT INTO lek (naziv,sifra,opis,kontraindikacije,oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,kategorijaLeka) VALUES ('brufenn','1554','opiss','samokontra',2,12.5,'slicica',3,20,1,1);

insert into korisnici(korisnickoIme,lozinka,email,ime,prezime,datumRodjenja,adresa,brojTelefona,datumIVremeRegistracije,uloga)
 values ("maja12",123,"maja@gmail.com","Maja","Majic",'1999-04-17',"Novosadska 34","063/985-85-12",now(),1);

