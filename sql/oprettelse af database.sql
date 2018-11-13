DROP DATABASE IF EXISTS Fog;
CREATE DATABASE Fog;
USE Fog;


DROP TABLE IF EXISTS Forespoergsel;
DROP TABLE IF EXISTS Skur;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Kunde;
DROP TABLE IF EXISTS Postnummer;
DROP TABLE IF EXISTS Vare;
DROP TABLE IF EXISTS Varetype;

CREATE TABLE Varetype(
	id int PRIMARY KEY AUTO_INCREMENT,
	`type` varchar(50) NOT NULL
);

CREATE TABLE Vare(
	id int PRIMARY KEY AUTO_INCREMENT,
	varetypeId int NOT NULL,
	navn VARCHAR(100) NOT NULL,
	hjaelpetekst VARCHAR(255) NOT NULL,
	pris decimal(6,2) NOT NULL,
	CONSTRAINT fk_Vare_Varetype
	FOREIGN KEY (varetypeId)
	REFERENCES Varetype(id)
	ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);


create table Postnummer(
	postnr smallint primary key not null,
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

/*
CREATE TABLE Tag(
	id int PRIMARY KEY AUTO_INCREMENT,
    vareId int NOT NULL,
    haeldning int NOT NULL,
    CONSTRAINT fk_Tag_Vare
    FOREIGN KEY(vareId)
    REFERENCES Vare(id)
    ON DELETE NO ACTION -- vare som indgår i tag, må ikke slettes.    
);
*/
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
	/*CONSTRAINT fk_Forespoergsel_Tag
	FOREIGN KEY(tagId)
	REFERENCES Tag(id)
	ON DELETE NO ACTION, -- tag refereret her, må ikke slettes.*/
	CONSTRAINT fk_Forespoergsel_Skur
	FOREIGN KEY(skurId)
	REFERENCES Skur(id)
	ON DELETE SET NULL-- slettes skuret, skal referencen til det fjernes.
);

CREATE TABLE Dimensioner(
	id int PRIMARY KEY AUTO_INCREMENT,
	laengde int NOT NULL
);

-- LOOKUP mellem Vare og Dimensioner pga. n--m relation.
CREATE TABLE VareDimensioner(
	vareId int NOT NULL,
	dimensionerId int NOT NULL,
	PRIMARY KEY(vareId, dimensionerId),
	CONSTRAINT fk_VareDimensioner_Vare
	FOREIGN KEY (vareId)
	REFERENCES Vare(id)
	ON DELETE CASCADE, -- slettes varen, gemmes dens dimensioner ikke.
	CONSTRAINT fk_VareDimensioner_Dimensioner
	FOREIGN KEY(dimensionerId)
	REFERENCES Dimensioner(id)
	ON DELETE NO ACTION -- en dimension kan ikke slettes, så længe der er varer.
);