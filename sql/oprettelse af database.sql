DROP DATABASE IF EXISTS Fog;
CREATE DATABASE Fog;
USE Fog;


DROP TABLE IF EXISTS Forespoergsel;
DROP TABLE IF EXISTS Skur;
DROP TABLE IF EXISTS Kunde;
DROP TABLE IF EXISTS Postnummer;
DROP TABLE IF EXISTS Materiale;
DROP TABLE IF EXISTS Materialetype;
DROP TABLE IF EXISTS Hjaelpetekst;

CREATE TABLE Hjaelpetekst(
	id int PRIMARY KEY AUTO_INCREMENT,
    tekst VARCHAR(255) NOT NULL
);

CREATE TABLE Materialetype(
	id int PRIMARY KEY AUTO_INCREMENT,
	`type` varchar(50) NOT NULL
);

CREATE TABLE Materiale(
	id int PRIMARY KEY AUTO_INCREMENT,
	materialetypeId int NOT NULL,
    navn VARCHAR(100) NOT NULL,    
    laengde int,    
    enhed VARCHAR(10) NOT NULL,	   
	CONSTRAINT fk_Materiale_Materialetype
	FOREIGN KEY (materialetypeId)
	REFERENCES Materiale(id)
	ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);

create table Postnummer(
	postnr smallint primary key not null, -- 14 bits til 9999 ==> 16 bits eller 2 bytes ==> smallint.
    bynavn varchar(150) not null
);

CREATE TABLE Kunde(
	id int primary key auto_increment,
    navn varchar(200) not null,
    postnr smallint not null,
    telefon int not null,
    email varchar(200) not null,
    CONSTRAINT fk_Kunde_Postnummer
    FOREIGN KEY (postnr)
	REFERENCES Postnummer(postnr)
	ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);

CREATE TABLE Skur(
	id int PRIMARY KEY AUTO_INCREMENT,
    laengde int NOT NULL,
    bredde int NOT NULL
);

CREATE TABLE Forespoergsel(
	id int PRIMARY KEY AUTO_INCREMENT,
    vareId int NOT NULL, -- id for tagbelægningen (i vare tabel)
	/*tagId int NOT NULL, -- carport har altid et tag.*/
    haeldning int NOT NULL default 0, -- hældning er 0 hvis intet andet angives.
	skurId int, -- carport har ikke altid et skur.
	bredde int NOT NULL,
	hoejde int NOT NULL,
	laengde int NOT NULL,
	bemaerkning text,	
	CONSTRAINT fk_Forespoergsel_Skur
	FOREIGN KEY(skurId)
	REFERENCES Skur(id)
	ON DELETE SET NULL-- slettes skuret, skal referencen til det fjernes.
);