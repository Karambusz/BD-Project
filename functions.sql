create function dodajpacjenta(imiep character varying, nazwiskop character varying, wiekp integer, numerp character varying, loginp character varying, haslop character varying) returns boolean
    language plpgsql
as
$$
DECLARE
    existsCheck BOOLEAN;
    id          RECORD;
BEGIN
    SELECT EXISTS (select p.nr_telefonu from pacjent p where p.nr_telefonu = numerP) INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        insert into pacjent (imie, nazwisko, wiek, nr_telefonu) values (imieP, nazwiskoP, wiekP, numerP);
        SELECT id_pacjent INTO id FROM pacjent WHERE nr_telefonu = numerP;
        INSERT INTO dane_pacjent (id_pacjent, login, haslo) VALUES (id.id_pacjent, loginP, hasloP);
        INSERT INTO historia_choroby (id_pacjent) values (id.id_pacjent);
    ELSE
        RAISE EXCEPTION 'Pacjent z tym samym numerem już istnieje!';
    END IF;
    RETURN TRUE;
END;
$$;
------------------------------------------------------------------------------------------------------------
create function dodajspecjalista(imies character varying, nazwiskos character varying, specjalizacjas character varying, cenas integer, logins character varying, haslos character varying, gabinet integer, placowka integer) returns boolean
    language plpgsql
as
$$
DECLARE
    existsCheck BOOLEAN;
    id          RECORD;
BEGIN
    SELECT EXISTS (select ds.login from dane_specjalista ds where ds.login = loginS) INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        insert into specjalista (imie, nazwisko, specjalizacja, cena_wizyty) values (imieS, nazwiskoS, specjalizacjaS, cenaS);
        SELECT max(id_specjalista) INTO id FROM specjalista;
        INSERT INTO dane_specjalista (id_specjalista, login, haslo) VALUES (id.max, loginS, hasloS);
        Insert INTO gabinet_placowka (id_gabinet, id_placowka, id_specjalista) values (gabinet, placowka, id.max);
    ELSE
        RAISE EXCEPTION 'Specjalista z tym samym loginem już istnieje!';
    END IF;
    RETURN TRUE;
END;
$$;
------------------------------------------------------------------------------------------------------------
create function usunspecjalista(imies character varying, nazwiskos character varying, specjalizacjas character varying, gabinet integer, placowka integer) returns boolean
    language plpgsql
as
$$
DECLARE
    id          int;
BEGIN
    SELECT id_specjalista INTO id FROM informacjaSpecjalista  where imie=imieS and nazwisko=nazwiskoS and specjalizacja=specjalizacjaS and id_gabinet=gabinet and id_placowka=placowka;
    if (id > 0) THEN
        DELETE from dane_specjalista where id_specjalista=id;
        DELETE from gabinet_placowka where id_specjalista=id;
        DELETE from plan_specjalisty where id_specjalista=id;
        DELETE from pacjent_specjalista where id_specjalista=id;
        DELETE from specjalista where id_specjalista=id;
        return TRUE;
    end if;
    RAISE EXCEPTION 'Brak specjalisty w bazie.';
END
$$;
------------------------------------------------------------------------------------------------------------
create function zalogujpacjenta(numerp character varying, loginp character varying, haslop character varying) returns boolean
    language plpgsql
as
$$
DECLARE
        existsCheckNumber BOOLEAN;
        existsCheckData BOOLEAN;
        id          RECORD;
    BEGIN
        SELECT EXISTS (select p.nr_telefonu from pacjent p where p.nr_telefonu = numerP) into existsCheckNumber;
        if (SELECT existsCheckNumber = TRUE) THEN
            SELECT id_pacjent INTO id FROM pacjent WHERE nr_telefonu = numerP;
            SELECT EXISTS (select dp.login, dp.haslo from dane_pacjent dp where dp.id_pacjent = id.id_pacjent and dp.login = loginP and dp.haslo = hasloP) into existsCheckData;
        ELSE
            RAISE EXCEPTION 'Pacjent z podanym numerem nie istnieje!';
        end if;
        if ((SELECT existsCheckNumber = TRUE) AND (SELECT existsCheckData = TRUE)) THEN
            return true;
        else
           RAISE EXCEPTION 'Podałeś zły login lub  hasło, sprawdź swoje dane i sprobuj ponownie!';
        end if;
    END;
$$;
------------------------------------------------------------------------------------------------------------
create function zalogujspecjalista(logins character varying, haslos character varying) returns boolean
    language plpgsql
as
$$
DECLARE
        existsCheckData BOOLEAN;
    BEGIN
        select exists(select ds.login, ds.haslo from dane_specjalista ds where ds.login = loginS and ds.haslo = hasloS) into existsCheckData;
        if (select existsCheckData = TRUE) THEN
            return true;
        else
           RAISE EXCEPTION 'Podałeś zły login lub  hasło, sprawdź swoje dane i sprobuj ponownie!';
        end if;
    END;
$$;
------------------------------------------------------------------------------------------------------------
create function zalogujdyrektora(logind character varying, haslod character varying) returns boolean
    language plpgsql
as
$$
DECLARE
        existsCheckData BOOLEAN;
    BEGIN
        select exists(select dd.login, dd.haslo from dane_dyrektor dd where dd.login = loginD and dd.haslo = hasloD) into existsCheckData;
        if (select existsCheckData = TRUE) THEN
            return true;
        else
           RAISE EXCEPTION 'Podałeś zły login lub  hasło, sprawdź swoje dane i sprobuj ponownie!';
        end if;
    END;
$$;
------------------------------------------------------------------------------------------------------------








