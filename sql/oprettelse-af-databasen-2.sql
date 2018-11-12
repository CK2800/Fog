DROP DATABASE IF EXISTS Fog;
CREATE DATABASE Fog;
USE Fog;

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
pris decimal(8) NOT NULL,
CONSTRAINT fk_Vare_Varetype
FOREIGN KEY (varetypeId)
REFERENCES Varetype(id)
ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);


create table postnummer(
	postnr smallint primary key not null,
    bynavn varchar(150) not null
);

CREATE TABLE kunder(
	id int primary key auto_increment,
    navn varchar(200) not null,
    postnr smallint not null,
    telefon int not null,
    email varchar(200) not null,
    CONSTRAINT fk_kunder_postnr
    FOREIGN KEY (postnr)
	REFERENCES postnummer(postnr)
	ON DELETE NO ACTION -- varetype refereret i vare må ikke slettes.
);

