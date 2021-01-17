create function dodajrezerwacje() returns trigger
    language plpgsql
as
$$
BEGIN
        IF EXISTS(SELECT 1 FROM pacjent_specjalista WHERE id_specjalista = New.id_specjalista and id_pacjent = New.id_pacjent and data = New.data) THEN
            RAISE EXCEPTION 'Masz juz rezerwację do specjalisty w tym dniu. Wybierz inny dzień lub specjalistę';
        ELSE
            RETURN NEW;
        END IF;
    END;
$$;

CREATE TRIGGER rezerwacjaCheck
    BEFORE INSERT OR UPDATE ON pacjent_specjalista
    FOR EACH ROW EXECUTE PROCEDURE dodajRezerwacje();
------------------------------------------------------------------------------------------------------------
create function dodajgabinet() returns trigger
    language plpgsql
as
$$
BEGIN
        IF EXISTS(SELECT 1 FROM gabinet where id_gabinet=New.id_gabinet) THEN
            RAISE EXCEPTION 'Taki numer juz istnieje. Proszę wpisać inny.';
        ELSE
            RETURN NEW;
        END IF;
    END;
$$;

CREATE TRIGGER dodajGabinetCheck
    BEFORE INSERT OR UPDATE ON gabinet
    FOR EACH ROW EXECUTE PROCEDURE dodajGabinet();
------------------------------------------------------------------------------------------------------------
create function dodajwpisdohistorii() returns trigger
    language plpgsql
as
$$
BEGIN
        IF EXISTS(SELECT 1 FROM choroba_historia WHERE id_historia=New.id_historia and id_choroba=New.id_choroba and s_imie = New.s_imie and s_nazwisko = New.s_nazwisko and data = New.data) THEN
            RAISE EXCEPTION 'Wpis juz został dodany. Proszę wybrać inny.';
        ELSE
            RETURN NEW;
        END IF;
    END;
$$;

CREATE TRIGGER dodajWpisCheck
    BEFORE INSERT OR UPDATE ON choroba_historia
    FOR EACH ROW EXECUTE PROCEDURE dodajWpisDoHistorii();