insert into placowka (mejscowosc, ulica, nr_budynku) values
    ('Kraków', 'Jasnogórska', 1),
    ('Kraków', 'Juliusza Lea', 22),
    ('Kraków', 'Młodej Polski', 7);

----------------------------------------------------------------
insert into dyrektor (id_placowka, imie, nazwisko) values
(1, 'Edyta', 'Lewczyk'),
(2, 'Tomasz', 'Kwiatkowski'),
(3, 'Stanisław', 'Dąbrowski');

----------------------------------------------------------------
insert into dane_dyrektor (id_dyrektor, login, haslo) values
(1, 'dyrektorEdytaL', 'edyta123'),
(2, 'dyrektorTomaszK', 'tomasz123'),
(3, 'dyrektorStanisławD', 'stanislaw123');

----------------------------------------------------------------
insert into gabinet (id_gabinet) values
(1),
(2),
(3),
(4);

----------------------------------------------------------------
insert into specjalista (imie, nazwisko, specjalizacja, cena_wizyty) values
('Adam', 'Abacki', 'Psychoterapeuta', 100),
('Jakub', 'Rogowski', 'Diagnostyk', 75),
----------------------------------------------------------------
insert into dane_specjalista (id_specjalista, login, haslo) values
(1, 'specjalistaAdamA', 'adam123'),
(2, 'specjalistaJakubR', 'jakub123'),


----------------------------------------------------------------
insert into dzien_pracy (dzien) VALUES
('Poniedziałek'),
('Wtorek'),
('Środa'),
('Czwartek'),
('Piątek'),
('Sobota'),
('Niedzila');

----------------------------------------------------------------
insert into plan_specjalisty (id_specjalista, id_dzien) values
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(2, 5);
----------------------------------------------------------------
insert into gabinet_placowka (id_gabinet, id_placowka, id_specjalista) values
(1, 1, 1),
(2, 1, 2),
----------------------------------------------------------------
insert into choroba (nazwa) values
('Psychoza depresyjna'),
('Autyzm'),
('Anoreksja'),
('Bulimia'),
('Paranoja'),
('Apatia'),
('Zapalenie trzustki'),
('Nieżyt żołądka'),
('Zapalenie pęcherzyka żółcioweg'),
('Choroba wrzodowa'),
('Osteochondroza'),
('Rwa kulszowa'),
('Dystonia wegetatywna'),
('Uszkodzenia nerwów'),
('Wstrząs'),
('Zapalenie nerwu'),
('Zapalenie skóry'),
('Opryszczka'),
('Wyprysk'),
('Atopowe zapalenie skóry'),
('Grzybica'),
('Zapalenie gardła'),
('Katar'),
('Dusznica'),
('Zapalenie zatok'),
('Zapalenie ucha'),
('Dusznica'),
('Uraz nosa');