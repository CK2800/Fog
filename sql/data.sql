INSERT INTO Varetype(type) VALUES('Beslag');
INSERT INTO Varetype(type) VALUES('Strø');
INSERT INTO Varetype(type) VALUES('Lægte');
INSERT INTO Varetype(type) VALUES('Remtræ');
INSERT INTO Varetype(type) VALUES('Stolpe');
INSERT INTO Varetype(type) VALUES('Brædt');
INSERT INTO Varetype(type) VALUES('Spærtræ');
INSERT INTO Varetype(type) VALUES('Tagbeklædning - fladt tag');
INSERT INTO Varetype(type) VALUES('Tagbeklædning - rejsning');

INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(1,'beslag', 'dette er et beslag', 5);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(2,'strø', 'dette er en strø', 6);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(3,'lægte', 'dette er en lægte', 9);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(4,'rem', 'dette er en rem', 8);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(5,'stolpe', 'dette er en stolpe', 7);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(6,'brædt', 'dette er et brædt', 4);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(7,'spær', 'dette er spær til tag m. rejsning', 7.5);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(8,'tagbeklædning - fladt tag', 'dette er en tagbeklædning til fladt tag', 3);
INSERT INTO Vare(varetypeId, navn, hjaelpetekst, pris) VALUES(9,'tagbeklædning - tag m rejsning', 'dette er en tagbeklædning til tag med rejsning', 3);

INSERT INTO Dimensioner(laengde) VALUES(100);
INSERT INTO Dimensioner(laengde) VALUES(130);
INSERT INTO Dimensioner(laengde) VALUES(120);
INSERT INTO Dimensioner(laengde) VALUES(150);
INSERT INTO Dimensioner(laengde) VALUES(170);
INSERT INTO Dimensioner(laengde) VALUES(200);
INSERT INTO Dimensioner(laengde) VALUES(230);
INSERT INTO Dimensioner(laengde) VALUES(430);
INSERT INTO Dimensioner(laengde) VALUES(550);
INSERT INTO Dimensioner(laengde) VALUES(580);
INSERT INTO Dimensioner(laengde) VALUES(670);
INSERT INTO Dimensioner(laengde) VALUES(720);

-- opret strøer
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,1);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,2);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,3);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,4);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,5);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,6);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,7);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,8);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,9);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,10);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,11);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(2,12);
-- opret lægter
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,1);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,2);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,3);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,4);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,5);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,6);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,7);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,8);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,9);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,10);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,11);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(3,12);
-- opret remme
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,1);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,2);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,3);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,4);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,5);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,6);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,7);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,8);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,9);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,10);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,11);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(4,12);
-- opret stolper, højde 200 ell. 230 cm
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(5,6);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(5,7);
-- opret brædder
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,1);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,2);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,3);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,4);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,5);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,6);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,7);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,8);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,9);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,10);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,11);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(6,12);
-- opret spærtræ, lægde starter v 430 cm
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(7,8);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(7,9);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(7,10);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(7,11);
INSERT INTO VareDimensioner(vareId, dimensionerId) VALUES(7,12);