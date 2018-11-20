-- hjælpetekster.
INSERT INTO Hjaelpetekst(tekst) VALUES('Beklædning af skur 1 på 2');
INSERT INTO Hjaelpetekst(tekst) VALUES('beklædning af gavle 1 på 2');
INSERT INTO Hjaelpetekst(tekst) VALUES('byg-selv spær (skal samles) ? stk.');

INSERT INTO Hjaelpetekst(tekst) VALUES('løsholter til skur gavle');
INSERT INTO Hjaelpetekst(tekst) VALUES('løsholter til skur sider');

INSERT INTO Hjaelpetekst(tekst) VALUES('monteres på toppen af spæret (til toplægte)');
INSERT INTO Hjaelpetekst(tekst) VALUES('monteres på taglægter 6 rækker af 24 sten på hver side af taget');
INSERT INTO Hjaelpetekst(tekst) VALUES('monteres på toplægte med medfølgende beslag se tagstens vejledning');

INSERT INTO Hjaelpetekst(tekst) VALUES('oversternbrædder til siderne');
INSERT INTO Hjaelpetekst(tekst) VALUES('oversternbrædder til forenden');

INSERT INTO Hjaelpetekst(tekst) VALUES('Remme i sider, sadles ned i stolper');
INSERT INTO Hjaelpetekst(tekst) VALUES('Remme i sider, sadles ned i stolper Skur del');
INSERT INTO Hjaelpetekst(tekst) VALUES('Remme i sider, sadles ned i stolper Carport del');
INSERT INTO Hjaelpetekst(tekst) VALUES('Remme i sider, sadles ned i stolper (skur del, deles)');

INSERT INTO Hjaelpetekst(tekst) VALUES('Skruer til tagplader');
INSERT INTO Hjaelpetekst(tekst) VALUES('Spær, monteres på rem');
INSERT INTO Hjaelpetekst(tekst) VALUES('Stolper nedgraves 90 cm. i jord');
INSERT INTO Hjaelpetekst(tekst) VALUES('Stolper nedgraves 90 cm. i jord + skråstiver');
INSERT INTO Hjaelpetekst(tekst) VALUES('Sternbrædder til siderne Carport del');
INSERT INTO Hjaelpetekst(tekst) VALUES('Sternbrædder til siderne Skur del ( deles )');



INSERT INTO Hjaelpetekst(tekst) VALUES('Til skurdør');
INSERT INTO Hjaelpetekst(tekst) VALUES('til taglægter');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til vindkryds på spær');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til lås på dør i skur');
INSERT INTO Hjaelpetekst(tekst) VALUES('til z på bagside af dør');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af rygsten');
INSERT INTO Hjaelpetekst(tekst) VALUES('tagplader monteres på spær');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af løsholter');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af spær på rem');
INSERT INTO Hjaelpetekst(tekst) VALUES('til beklædning af skur 1 på 2');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af rem på stolper');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af stern&vandbrædt');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af løsholter i skur');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering oven på tagfodslægte');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af yderste beklædning');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af inderste beklædning');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af universalbeslag + hulbånd');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af universalbeslag + toplægte');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af yderste bræt ved beklædning');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af inderste bræt ved beklædning');
INSERT INTO Hjaelpetekst(tekst) VALUES('Til montering af Stern, vindskeder, vindkryds & vandbræt');
INSERT INTO Hjaelpetekst(tekst) VALUES('toplægte til montering af rygsten lægges i toplægte holder');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering af tagsten, alle ydersten + hver anden fastgøres');
INSERT INTO Hjaelpetekst(tekst) VALUES('til montering på spær, 7 rækker lægter på hver skiftevis 1 hel & 1 halv lægte');

INSERT INTO Hjaelpetekst(tekst) VALUES('understernbrædder til siderne');
INSERT INTO Hjaelpetekst(tekst) VALUES('understernbrædder til for- & bagende');

INSERT INTO Hjaelpetekst(tekst) VALUES('Vandbræt på vindskeder');
INSERT INTO Hjaelpetekst(tekst) VALUES('Vindskeder på rejsning');
INSERT INTO Hjaelpetekst(tekst) VALUES('vandbrædt på stern i sider');
INSERT INTO Hjaelpetekst(tekst) VALUES('vandbrædt på stern i forende');

-- materiale typer.
INSERT INTO Materialetype(type) VALUES('Trykimp. brædt');
INSERT INTO Materialetype(type) VALUES('lægte ubh.');
INSERT INTO Materialetype(type) VALUES('reglar ub.');
INSERT INTO Materialetype(type) VALUES('spærtræ ubh.');
INSERT INTO Materialetype(type) VALUES('trykimp. stolpe'); -- 5
INSERT INTO Materialetype(type) VALUES('plasttag');
INSERT INTO Materialetype(type) VALUES('taglægte T1');
INSERT INTO Materialetype(type) VALUES('tagsten');
INSERT INTO Materialetype(type) VALUES('rygsten');
INSERT INTO Materialetype(type) VALUES('toplægteholder'); -- 10
INSERT INTO Materialetype(type) VALUES('rygstensbeslag');
INSERT INTO Materialetype(type) VALUES('tagstensbindere & nakkekroge');
INSERT INTO Materialetype(type) VALUES('universalbeslag højre');
INSERT INTO Materialetype(type) VALUES('universalbeslag venstre');
INSERT INTO Materialetype(type) VALUES('stalddørsgreb'); -- 15
INSERT INTO Materialetype(type) VALUES('T-hængsel'); 
INSERT INTO Materialetype(type) VALUES('vinkelbeslag');
INSERT INTO Materialetype(type) VALUES('skruer');
INSERT INTO Materialetype(type) VALUES('beslagskruer');
INSERT INTO Materialetype(type) VALUES('bræddebolt'); -- 20
INSERT INTO Materialetype(type) VALUES('firkantskiver');
INSERT INTO Materialetype(type) VALUES('bundskruer');
INSERT INTO Materialetype(type) VALUES('hulbånd');
INSERT INTO Materialetype(type) VALUES('byg-selv spær'); 


-- trykimprægnerede brædder.
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x200 mm.', 360, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x200 mm.', 540, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x150 mm.', 480, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x150 mm.', 540, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x150 mm.', 600, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x125 mm.', 360, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x125 mm.', 540, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '25x50 mm.', 540, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '19x100 mm.', 210, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '19x100 mm.', 240, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '19x100 mm.', 360, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '19x100 mm.', 480, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (1, '19x100 mm.', 540, 'stk');
-- ubehandlede lægter
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (2, '38x73 mm.', 420, 'stk');
-- ubehandlede reglar
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (3, '45x95 mm.', 360, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (3, '45x95 mm.', 270, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (3, '45x95 mm.', 240, 'stk');
-- spærtræ, ubehandlet.
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (4, '45x195 mm.', 600, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (4, '45x195 mm.', 480, 'stk');
-- trykimprægnerede stolper.
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (5, '97x97 mm.', 300, 'stk');
-- tagplader, plast
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (6, 'Plastmo ecolite blåtonet', 600, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (6, 'Plastmo ecolite blåtonet', 360, 'stk');
-- taglægte T1
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (7, '38x73 mm.', 540, 'stk');
INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (7, '38x73 mm.', 420, 'stk');
-- tagsten
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (8, 'B & C Dobbelt-s sort', 'stk');
-- rygsten
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (9, 'B & C sort', 'stk');
-- toplægteholder
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (10, 'B & C', 'stk');
-- rygstensbeslag
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (11, 'B & C', 'stk');
-- tagstensbindere og kroge sampak
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (12, 'B & C', 'pk');
-- universalbeslag højre
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (13, '190 mm.', 'stk');
-- universalbeslag venstre
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (14, '190 mm.', 'stk');
-- stalddørsgreb
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (15, '50x75', 'stk');
-- T-hængsel
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (16, '390 mm.', 'stk');
-- Vinkelbeslag
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (17, '35 mm.', 'stk');
-- skruer
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '300 stk 4,5x50 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '350 stk 4,5x50 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '200 stk 4,5x60 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '200 stk 4,5x70 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '400 stk 4,5x70 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (18, '100 stk 5,0x100 mm.', 'pakke');
-- beslagskruer
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (19, '250 stk 4,0x50 mm.', 'pakke');
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (19, '250 stk 5,0x40 mm.', 'pakke');
-- bræddebolt
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (20, '10 x 120 mm.', 'stk');
-- firkantskiver
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (21, '40x40x11 mm.', 'stk');
-- bundskruer
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (22, '200 stk Plastmo', 'pakke');
-- hulbånd
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (23, '1x20 mm., 10 mtr', 'rulle');
-- byg-selv spær
INSERT INTO Materiale(materialetypeId, navn, enhed) VALUES (24, 'færdigskåret spær', 'sæt');
