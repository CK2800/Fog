-- hjælpetekster.
INSERT INTO Remarks(text) VALUES('Beklædning af skur 1 på 2');
INSERT INTO Remarks(text) VALUES('beklædning af gavle 1 på 2');
INSERT INTO Remarks(text) VALUES('byg-selv spær (skal samles) ? stk.');
INSERT INTO Remarks(text) VALUES('løsholter til skur gavle');
INSERT INTO Remarks(text) VALUES('løsholter til skur sider');
INSERT INTO Remarks(text) VALUES('monteres på toppen af spæret (til toplægte)');
INSERT INTO Remarks(text) VALUES('monteres på taglægter 6 rækker af 24 sten på hver side af taget');
INSERT INTO Remarks(text) VALUES('monteres på toplægte med medfølgende beslag se tagstens vejledning');
INSERT INTO Remarks(text) VALUES('oversternbrædder til siderne');
INSERT INTO Remarks(text) VALUES('oversternbrædder til forenden');
INSERT INTO Remarks(text) VALUES('Remme i sider, sadles ned i stolper');
INSERT INTO Remarks(text) VALUES('Remme i sider, sadles ned i stolper Skur del');
INSERT INTO Remarks(text) VALUES('Remme i sider, sadles ned i stolper Carport del');
INSERT INTO Remarks(text) VALUES('Remme i sider, sadles ned i stolper (skur del, deles)');
INSERT INTO Remarks(text) VALUES('Skruer til tagplader');
INSERT INTO Remarks(text) VALUES('Spær, monteres på rem');
INSERT INTO Remarks(text) VALUES('Stolper nedgraves 90 cm. i jord');
INSERT INTO Remarks(text) VALUES('Stolper nedgraves 90 cm. i jord + skråstiver');
INSERT INTO Remarks(text) VALUES('Sternbrædder til siderne Carport del');
INSERT INTO Remarks(text) VALUES('Sternbrædder til siderne Skur del ( deles )');
INSERT INTO Remarks(text) VALUES('Til skurdør');
INSERT INTO Remarks(text) VALUES('til taglægter');
INSERT INTO Remarks(text) VALUES('Til vindkryds på spær');
INSERT INTO Remarks(text) VALUES('Til lås på dør i skur');
INSERT INTO Remarks(text) VALUES('til z på bagside af dør');
INSERT INTO Remarks(text) VALUES('Til montering af rygsten');
INSERT INTO Remarks(text) VALUES('tagplader monteres på spær');
INSERT INTO Remarks(text) VALUES('til montering af løsholter');
INSERT INTO Remarks(text) VALUES('Til montering af spær på rem');
INSERT INTO Remarks(text) VALUES('til beklædning af skur 1 på 2');
INSERT INTO Remarks(text) VALUES('Til montering af rem på stolper');
INSERT INTO Remarks(text) VALUES('Til montering af stern&vandbrædt');
INSERT INTO Remarks(text) VALUES('Til montering af løsholter i skur');
INSERT INTO Remarks(text) VALUES('til montering oven på tagfodslægte');
INSERT INTO Remarks(text) VALUES('til montering af yderste beklædning');
INSERT INTO Remarks(text) VALUES('til montering af inderste beklædning');
INSERT INTO Remarks(text) VALUES('Til montering af universalbeslag + hulbånd');
INSERT INTO Remarks(text) VALUES('Til montering af universalbeslag + toplægte');
INSERT INTO Remarks(text) VALUES('til montering af yderste bræt ved beklædning');
INSERT INTO Remarks(text) VALUES('til montering af inderste bræt ved beklædning');
INSERT INTO Remarks(text) VALUES('Til montering af Stern, vindskeder, vindkryds & vandbræt');
INSERT INTO Remarks(text) VALUES('toplægte til montering af rygsten lægges i toplægte holder');
INSERT INTO Remarks(text) VALUES('til montering af tagsten, alle ydersten + hver anden fastgøres');
INSERT INTO Remarks(text) VALUES('til montering på spær, 7 rækker lægter på hver skiftevis 1 hel & 1 halv lægte');
INSERT INTO Remarks(text) VALUES('understernbrædder til siderne');
INSERT INTO Remarks(text) VALUES('understernbrædder til for- & bagende');
INSERT INTO Remarks(text) VALUES('Vandbræt på vindskeder');
INSERT INTO Remarks(text) VALUES('Vindskeder på rejsning');
INSERT INTO Remarks(text) VALUES('vandbrædt på stern i sider');
INSERT INTO Remarks(text) VALUES('vandbrædt på stern i forende');

-- trykimprægnerede brædder.
INSERT INTO Materialtypes(type) VALUES('Trykimp. brædt');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x200 mm.', 360, 'stk', 75.42);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x200 mm.', 540, 'stk', 113.13);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x150 mm.', 480, 'stk', 100.56);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x150 mm.', 540, 'stk', 113.13);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x150 mm.', 600, 'stk', 125.7);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x125 mm.', 360, 'stk', 75.42);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x125 mm.', 540, 'stk', 113.13);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '25x50 mm.', 540, 'stk', 113.13);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '19x100 mm.', 210, 'stk', 43.99);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '19x100 mm.', 240, 'stk', 50.28 );
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '19x100 mm.', 360, 'stk', 75.42);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '19x100 mm.', 480, 'stk', 100.56);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (1, '19x100 mm.', 540, 'stk', 113.13); -- 13
-- ubehandlede lægter
INSERT INTO Materialtypes(type) VALUES('lægte ubh.');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (2, '38x73 mm.', 420, 'stk', 41.79);
-- ubehandlede reglar
INSERT INTO Materialtypes(type) VALUES('reglar ub.');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (3, '45x95 mm.', 360, 'stk', 169.02);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (3, '45x95 mm.', 270, 'stk', 126.77);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (3, '45x95 mm.', 240, 'stk', 112.68);
-- spærtræ, ubehandlet.
INSERT INTO Materialtypes(type) VALUES('spærtræ ubh.');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (4, '45x195 mm.', 600, 'stk', 233.7);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (4, '45x195 mm.', 480, 'stk', 186.96);
-- trykimprægnerede stolper.
INSERT INTO Materialtypes(type) VALUES('trykimp. stolpe');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (5, '97x97 mm.', 300, 'stk', 68.31 ); -- 20
-- taglægte T1
INSERT INTO Materialtypes(type) VALUES('taglægte T1');
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (6, '38x73 mm.', 540, 'stk', 134.73 );
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (6, '38x73 mm.', 420, 'stk', 104.79 );
-- tagfladebelægning.
INSERT INTO Materialtypes(type) VALUES('tagfladebelægning'); -- 7
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 240, 'stk', 79.95);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 300, 'stk', 99.95);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 420, 'stk', 149 ); -- 25
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 480, 'stk', 179);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 540, 'stk', 229);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 600, 'stk', 289);
INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (7, 'Plastmo ecolite blåtonet', 360, 'stk', 119);
INSERT INTO Materials(materialtypeId, name, unit, price) VALUES (7, 'B & C Dobbelt-s sort', 'stk', 15.95); -- 30
-- tagrygbelægning.
INSERT INTO Materialtypes(type) VALUES('tagrygbelægning'); -- tidl. rygsten
INSERT INTO Materials(materialtypeId, name, unit, price) VALUES (8, 'B & C sort', 'stk', 94.95);
-- toplægteholder
INSERT INTO Materialtypes(type) VALUES('toplægteholder');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (9, 'B & C', 'stk');
-- rygstensbeslag
INSERT INTO Materialtypes(type) VALUES('rygstensbeslag');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (10, 'B & C', 'stk');
-- tagstensbindere og kroge sampak
INSERT INTO Materialtypes(type) VALUES('tagstensbindere & nakkekroge');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (11, 'B & C', 'pk');
-- universalbeslag højre
INSERT INTO Materialtypes(type) VALUES('universalbeslag højre');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (12, '190 mm.', 'stk');
-- universalbeslag venstre
INSERT INTO Materialtypes(type) VALUES('universalbeslag venstre');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (13, '190 mm.', 'stk');
-- stalddørsgreb
INSERT INTO Materialtypes(type) VALUES('stalddørsgreb');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (14, '50x75', 'stk');
-- T-hængsel
INSERT INTO Materialtypes(type) VALUES('T-hængsel'); 
INSERT INTO Materials(materialtypeId, name, unit) VALUES (15, '390 mm.', 'stk');
-- Vinkelbeslag
INSERT INTO Materialtypes(type) VALUES('vinkelbeslag');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (16, '35 mm.', 'stk');
-- skruer
INSERT INTO Materialtypes(type) VALUES('skruer');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '300 stk 4,5x50 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '350 stk 4,5x50 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '200 stk 4,5x60 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '200 stk 4,5x70 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '400 stk 4,5x70 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (17, '100 stk 5,0x100 mm.', 'pakke');
-- beslagskruer
INSERT INTO Materialtypes(type) VALUES('beslagskruer');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (18, '250 stk 4,0x50 mm.', 'pakke');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (18, '250 stk 5,0x40 mm.', 'pakke');
-- bræddebolt
INSERT INTO Materialtypes(type) VALUES('bræddebolt'); 
INSERT INTO Materials(materialtypeId, name, unit) VALUES (19, '10 x 120 mm.', 'stk');
-- firkantskiver
INSERT INTO Materialtypes(type) VALUES('firkantskiver');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (20, '40x40x11 mm.', 'stk');
-- bundskruer
INSERT INTO Materialtypes(type) VALUES('bundskruer');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (21, '200 stk Plastmo', 'pakke');
-- hulbånd
INSERT INTO Materialtypes(type) VALUES('hulbånd');
INSERT INTO Materials(materialtypeId, name, unit) VALUES (22, '1x20 mm., 10 mtr', 'rulle');
-- byg-selv spær
INSERT INTO Materialtypes(type) VALUES('byg-selv spær'); 
INSERT INTO Materials(materialtypeId, name, unit) VALUES (23, 'færdigskåret spær', 'sæt');

-- roof types
INSERT INTO Rooftypes(type) VALUES('Rødt tegltag');
INSERT INTO Rooftypes(type) VALUES('Sort tegltag');
INSERT INTO Rooftypes(type) VALUES('Plast tag');
INSERT INTO Rooftypes(type) VALUES('Pap tag');

-- roof type materials
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (2, 7, true, 30); -- tagsten sort
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (2, 8, true, 31); -- rygsten sort
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 23); -- plast tag 600 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 24); -- plast tag 360 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 25); -- plast tag 240 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 26); -- plast tag 300 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 27); -- plast tag 420 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 28); -- plast tag 480 cm
INSERT INTO RooftypeMaterials(rooftypeId, materialtypeId, slope, materialId) VALUES (3, 7, false, 29); -- plast tag 540 cm
