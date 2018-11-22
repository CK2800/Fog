DROP DATABASE IF EXISTS Fog;
CREATE DATABASE Fog;
USE Fog;


DROP TABLE IF EXISTS Forespoergsel;
DROP TABLE IF EXISTS RooftypeMaterial;
DROP TABLE IF EXISTS Rooftype;
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
-- F.eks. rødt tegltag, sort tegltag, paptag, plasttag.
CREATE TABLE Rooftype(
	id int PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(50) NOT NULL
);
-- Info om hvilke materialer der indgår i en tagtype. F.eks. røde rygningssten og røde belægningssten til rødt tegltag. 
CREATE TABLE RooftypeMaterial(
	rooftypeId int,
    materialId int,
    materialTypeId int NOT NULL,
    slope boolean,    
    -- PRIMARY KEY(rooftypeId, materialTypeId), -- kombination af tagets type og materialets type sikrer, at der eks. kun er 1 belægningstype for hver tagtype. Duer ikke v. plasttag mv. pga flere dimensioner for samme materialetype.
    PRIMARY KEY(rooftypeId, materialId), -- kun 
    CONSTRAINT fk_RooftypeMaterial_Rooftype
    FOREIGN KEY(rooftypeId)
    REFERENCES Rooftype(id)
    ON DELETE CASCADE, -- Hvis tagtype slettes, slettes information om tagtypens materialer også.
    CONSTRAINT fk_RooftypeMaterial_Materialetype
    FOREIGN KEY(materialTypeId)
    REFERENCES Materialetype(id)
    ON DELETE CASCADE, -- Hvis materialetypen slettes, skal information om tagtypens materiale også slettes.
    CONSTRAINT fk_RooftypeMaterial_Materiale
    FOREIGN KEY(materialId)
    REFERENCES Materiale(id)
    ON DELETE CASCADE -- Hvis materialet slettes, skal denne information også slettes.
);

CREATE TABLE Forespoergsel(
	id int PRIMARY KEY AUTO_INCREMENT,
    roofTypeId int NOT NULL, -- id for tagtypen.
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