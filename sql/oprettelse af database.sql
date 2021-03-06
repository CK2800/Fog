DROP DATABASE IF EXISTS Fog;
CREATE DATABASE Fog;
USE Fog;

DROP TABLE IF EXISTS Sheds;
DROP TABLE IF EXISTS Carportrequests;
DROP TABLE IF EXISTS RooftypeMaterials;
DROP TABLE IF EXISTS Rooftypes;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Zipcodes;
DROP TABLE IF EXISTS Materials;
DROP TABLE IF EXISTS Materialtypes;
DROP TABLE IF EXISTS Remarks;

CREATE TABLE Remarks(
	id int PRIMARY KEY AUTO_INCREMENT,
    `text` VARCHAR(255) NOT NULL
);

CREATE TABLE Materialtypes(
	id int PRIMARY KEY AUTO_INCREMENT,
	`type` varchar(50) NOT NULL
);

CREATE TABLE Materials(
	id int PRIMARY KEY AUTO_INCREMENT,
	materialtypeId int NOT NULL,
    `name` VARCHAR(100) NOT NULL,    
    length int,    
    unit VARCHAR(10) NOT NULL,	
    price DECIMAL(6,2) NOT NULL DEFAULT 0, -- 6 tal, 4 foran komma, 2 efter.
	CONSTRAINT fk_Materials_Materialtypes
	FOREIGN KEY (materialtypeId)
	REFERENCES Materialtypes(id)
	ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);

create table Zipcodes(
	zip smallint primary key not null, -- 14 bits til 9999 ==> 16 bits eller 2 bytes ==> smallint.
    city varchar(150) not null
);

CREATE TABLE Users(
	id int primary key auto_increment,
    `name` varchar(200) not null,
    zip smallint not null, -- 14 bits til 9999 ==> 16 bits eller 2 bytes ==> smallint.
    phone int not null,
    email varchar(200) not null unique, 
    `password` varchar(200) not null ,
    rank int not null DEFAULT 5,
    CONSTRAINT fk_Users_Zipcodes
    FOREIGN KEY (zip)
	REFERENCES Zipcodes(zip)
	ON DELETE NO ACTION -- postnumre i brug i denne tabel må ikke slettes.
);

-- F.eks. rødt tegltag, sort tegltag, paptag, plasttag.
CREATE TABLE Rooftypes(
	id int PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(50) NOT NULL
);
-- Info om hvilke materialer der indgår i en tagtype. F.eks. røde rygningssten og røde belægningssten til rødt tegltag. 
-- Tabellen findes, fordi en tagtype kan have flere materialer, og disse kan være af forskellig materialetype. Koden må 
-- afgøre hvordan materialeantallet beregnes. V. flade tage kan man se efter materialets dimension, v. tag m. rejsning 
-- må man kigge efter materialets materialetype, f.eks. enten rygsten eller tagsten, og så beregne antallet.
CREATE TABLE RooftypeMaterials(
	rooftypeId int,
    materialId int,
    materialtypeId int NOT NULL,
    slope boolean,    
    -- PRIMARY KEY(rooftypeId, materialTypeId), -- kombination af tagets type og materialets type sikrer, at der f.eks. kun 
    -- er 1 belægningstype for hver tagtype. MEN: Duer ikke v. plasttag mv. pga flere dimensioner for samme materialetype.
    PRIMARY KEY(rooftypeId, materialId),
    CONSTRAINT fk_RooftypeMaterials_Rooftypes
    FOREIGN KEY(rooftypeId)
    REFERENCES Rooftypes(id)
    ON DELETE CASCADE, -- Hvis tagtype slettes, slettes information om tagtypens materialer også.
    CONSTRAINT fk_RooftypeMaterials_Materialtypes
    FOREIGN KEY(materialtypeId)
    REFERENCES Materialtypes(id)
    ON DELETE CASCADE, -- Hvis materialetypen slettes, skal information om tagtypens materiale også slettes.
    CONSTRAINT fk_RooftypeMaterials_Materials
    FOREIGN KEY(materialId)
    REFERENCES Materials(id)
    ON DELETE CASCADE -- Hvis materialet slettes, skal denne information også slettes.
);

CREATE TABLE Carportrequests(
	id int PRIMARY KEY AUTO_INCREMENT,
    rooftypeId int NOT NULL, -- id for tagtypen.
	/*tagId int NOT NULL, -- carport har altid et tag.*/
    slope int NOT NULL default 0, -- hældning er 0 hvis intet andet angives.
	-- shedId int, -- carport har ikke altid et skur.
	width int NOT NULL,
	height int NOT NULL,
	length int NOT NULL,
	remark text,	
	/*CONSTRAINT fk_Carportrequests_Sheds
	FOREIGN KEY(shedId)
	REFERENCES Sheds(id)
	ON DELETE SET NULL, -- slettes skuret, skal referencen til det fjernes.*/
    CONSTRAINT fk_Carportrequests_Rooftypes
    FOREIGN KEY (rooftypeId)
    REFERENCES Rooftypes(id)
    ON DELETE NO ACTION -- en tagtype, som refereres af en carport forespørgsel, må ikke slettes.
);
CREATE TABLE Sheds(
	id int PRIMARY KEY AUTO_INCREMENT,
    carportRequestId int NOT NULL,
    length int NOT NULL,
    width int NOT NULL,
    CONSTRAINT fk_Sheds_Carportrequests
    FOREIGN KEY(carportRequestId)
    REFERENCES Carportrequests(id)
    ON DELETE CASCADE -- slettes carportforespørgslen, skal skuret også slettes.
);