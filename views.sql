CREATE VIEW informacjaSpecjalista
AS
select  s.id_specjalista, s.imie, s.nazwisko, s.specjalizacja, s.cena_wizyty, g.id_gabinet, p.id_placowka, p.mejscowosc, p.ulica, p.nr_budynku from gabinet g
                                            join gabinet_placowka gp using (id_gabinet)
                                            join placowka p using (id_placowka)
                                            join specjalista s using (id_specjalista);

----------------------------------------------------------------
CREATE VIEW dniPracySpecjalista
as
select dp.id_dzien, dp.dzien, s.id_specjalista, s.imie, s.nazwisko from dzien_pracy dp
                                join plan_specjalisty ps using(id_dzien)
                                join specjalista s using(id_specjalista);
----------------------------------------------------------------
CREATE VIEW informacjaPacjenta
as
select p.id_pacjent, p.imie, p.nazwisko, p.wiek, p.nr_telefonu, ps.id_specjalista, ps.data, hc.id_historia from pacjent p join pacjent_specjalista ps on p.id_pacjent = ps.id_pacjent
                      join historia_choroby hc on p.id_pacjent = hc.id_pacjent;

----------------------------------------------------------------
CREATE VIEW chorobyPacjenta
as
select hc.id_pacjent, ch.id_historia, ch.id_choroba, ch.s_imie, ch.s_nazwisko, ch.data, c.nazwa from historia_choroby hc
                                  join choroba_historia ch on hc.id_historia = ch.id_historia
                                  join choroba c on ch.id_choroba = c.id_choroba;