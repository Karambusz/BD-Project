
CREATE SEQUENCE public.dzien_pracy_id_dzien_seq_1;

CREATE TABLE public.dzien_pracy (
                id_dzien INTEGER NOT NULL DEFAULT nextval('public.dzien_pracy_id_dzien_seq_1'),
                dzien VARCHAR NOT NULL,
                CONSTRAINT dzien_pracy_pk PRIMARY KEY (id_dzien)
);


ALTER SEQUENCE public.dzien_pracy_id_dzien_seq_1 OWNED BY public.dzien_pracy.id_dzien;

CREATE SEQUENCE public.wizyta_id_wizyta_seq;

CREATE TABLE public.wizyta (
                id_wizyta INTEGER NOT NULL DEFAULT nextval('public.wizyta_id_wizyta_seq'),
                kod_wizyty VARCHAR NOT NULL,
                CONSTRAINT wizyta_pk PRIMARY KEY (id_wizyta)
);


ALTER SEQUENCE public.wizyta_id_wizyta_seq OWNED BY public.wizyta.id_wizyta;

CREATE SEQUENCE public.choroba_id_choroba_seq;

CREATE TABLE public.choroba (
                id_choroba INTEGER NOT NULL DEFAULT nextval('public.choroba_id_choroba_seq'),
                nazwa VARCHAR(30) NOT NULL,
                CONSTRAINT id_choroba PRIMARY KEY (id_choroba)
);


ALTER SEQUENCE public.choroba_id_choroba_seq OWNED BY public.choroba.id_choroba;

CREATE SEQUENCE public.gabinet_id_gabinet_seq;

CREATE TABLE public.gabinet (
                id_gabinet INTEGER NOT NULL DEFAULT nextval('public.gabinet_id_gabinet_seq'),
                CONSTRAINT id_gabinet PRIMARY KEY (id_gabinet)
);


ALTER SEQUENCE public.gabinet_id_gabinet_seq OWNED BY public.gabinet.id_gabinet;

CREATE SEQUENCE public.placowka_id_placowka_seq;

CREATE TABLE public.placowka (
                id_placowka INTEGER NOT NULL DEFAULT nextval('public.placowka_id_placowka_seq'),
                mejscowosc VARCHAR(30) NOT NULL,
                ulica VARCHAR(30) NOT NULL,
                nr_budynku INTEGER NOT NULL,
                CONSTRAINT id_placowka PRIMARY KEY (id_placowka)
);


ALTER SEQUENCE public.placowka_id_placowka_seq OWNED BY public.placowka.id_placowka;

CREATE SEQUENCE public.dyrektor_id_dyrektor_seq;

CREATE TABLE public.dyrektor (
                id_dyrektor INTEGER NOT NULL DEFAULT nextval('public.dyrektor_id_dyrektor_seq'),
                id_placowka INTEGER NOT NULL,
                imie VARCHAR(20) NOT NULL,
                nazwisko VARCHAR(30) NOT NULL,
                CONSTRAINT id_dyrektor PRIMARY KEY (id_dyrektor)
);


ALTER SEQUENCE public.dyrektor_id_dyrektor_seq OWNED BY public.dyrektor.id_dyrektor;

CREATE TABLE public.dane_dyrektor (
                id_dyrektor INTEGER NOT NULL,
                login VARCHAR(30) NOT NULL,
                haslo VARCHAR(30) NOT NULL,
                CONSTRAINT dane_dyrektor_pk PRIMARY KEY (id_dyrektor)
);


CREATE SEQUENCE public.specjalista_id_specjalista_seq;

CREATE TABLE public.specjalista (
                id_specjalista INTEGER NOT NULL DEFAULT nextval('public.specjalista_id_specjalista_seq'),
                imie VARCHAR(20) NOT NULL,
                nazwisko VARCHAR(30) NOT NULL,
                specjalizacja VARCHAR(30) NOT NULL,
                cena_wizyty INTEGER NOT NULL,
                CONSTRAINT id_specjalista PRIMARY KEY (id_specjalista)
);


ALTER SEQUENCE public.specjalista_id_specjalista_seq OWNED BY public.specjalista.id_specjalista;

CREATE TABLE public.plan_specjalisty (
                id_specjalista INTEGER NOT NULL,
                id_dzien INTEGER NOT NULL,
                CONSTRAINT id_plan_specjalisty PRIMARY KEY (id_specjalista, id_dzien)
);


CREATE TABLE public.dane_specjalista (
                id_specjalista INTEGER NOT NULL,
                login VARCHAR(30) NOT NULL,
                haslo VARCHAR(30) NOT NULL,
                CONSTRAINT dane_specjalista_pk PRIMARY KEY (id_specjalista)
);


CREATE TABLE public.gabinet_placowka (
                id_gabinet INTEGER NOT NULL,
                id_placowka INTEGER NOT NULL,
                id_specjalista INTEGER NOT NULL,
                CONSTRAINT gabinet_placowka_pk PRIMARY KEY (id_gabinet, id_placowka, id_specjalista)
);


CREATE SEQUENCE public.pacjent_id_pacjent_seq;

CREATE TABLE public.pacjent (
                id_pacjent INTEGER NOT NULL DEFAULT nextval('public.pacjent_id_pacjent_seq'),
                imie VARCHAR(20) NOT NULL,
                nazwisko VARCHAR(30) NOT NULL,
                wiek INTEGER NOT NULL,
                nr_telefonu VARCHAR(11) NOT NULL,
                CONSTRAINT id_pacjent PRIMARY KEY (id_pacjent)
);


ALTER SEQUENCE public.pacjent_id_pacjent_seq OWNED BY public.pacjent.id_pacjent;

CREATE SEQUENCE public.historia_choroby_id_historia_seq;

CREATE TABLE public.historia_choroby (
                id_historia INTEGER NOT NULL DEFAULT nextval('public.historia_choroby_id_historia_seq'),
                id_pacjent INTEGER NOT NULL,
                CONSTRAINT id_historia_choroby PRIMARY KEY (id_historia)
);


ALTER SEQUENCE public.historia_choroby_id_historia_seq OWNED BY public.historia_choroby.id_historia;

CREATE TABLE public.choroba_historia (
                id_historia INTEGER NOT NULL,
                id_choroba INTEGER NOT NULL,
                s_imie VARCHAR(20) NOT NULL,
                s_nazwisko VARCHAR(30) NOT NULL,
                data VARCHAR NOT NULL,
                CONSTRAINT choroba_historia_pk PRIMARY KEY (id_historia, id_choroba)
);


CREATE TABLE public.dane_pacjent (
                id_pacjent INTEGER NOT NULL,
                login VARCHAR(30) NOT NULL,
                haslo VARCHAR(30) NOT NULL,
                CONSTRAINT dane_pacjent_pk PRIMARY KEY (id_pacjent)
);


CREATE SEQUENCE public.wizyta_id_wizyta_seq_1;

CREATE TABLE public.pacjent_specjalista (
                id_wizyta INTEGER NOT NULL DEFAULT nextval('public.wizyta_id_wizyta_seq_1'),
                id_specjalista INTEGER NOT NULL,
                id_pacjent INTEGER NOT NULL,
                data VARCHAR NOT NULL,
                CONSTRAINT pacjent_specjalista_pk PRIMARY KEY (id_wizyta, id_specjalista, id_pacjent)
);


ALTER SEQUENCE public.wizyta_id_wizyta_seq_1 OWNED BY public.pacjent_specjalista.id_wizyta;

ALTER TABLE public.plan_specjalisty ADD CONSTRAINT dzien_pracy_plan_specjalisty_fk
FOREIGN KEY (id_dzien)
REFERENCES public.dzien_pracy (id_dzien)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pacjent_specjalista ADD CONSTRAINT wizyta_pacjent_specjalista_fk
FOREIGN KEY (id_wizyta)
REFERENCES public.wizyta (id_wizyta)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.choroba_historia ADD CONSTRAINT choroba_choroba_historia_fk
FOREIGN KEY (id_choroba)
REFERENCES public.choroba (id_choroba)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.gabinet_placowka ADD CONSTRAINT gabinet_gabinet_placowka_fk
FOREIGN KEY (id_gabinet)
REFERENCES public.gabinet (id_gabinet)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dyrektor ADD CONSTRAINT placowka_dyrektor_fk
FOREIGN KEY (id_placowka)
REFERENCES public.placowka (id_placowka)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.gabinet_placowka ADD CONSTRAINT placowka_gabinet_placowka_fk
FOREIGN KEY (id_placowka)
REFERENCES public.placowka (id_placowka)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dane_dyrektor ADD CONSTRAINT dyrektor_dane_dyrektor_fk
FOREIGN KEY (id_dyrektor)
REFERENCES public.dyrektor (id_dyrektor)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.gabinet_placowka ADD CONSTRAINT specjalista_gabinet_placowka_fk
FOREIGN KEY (id_specjalista)
REFERENCES public.specjalista (id_specjalista)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pacjent_specjalista ADD CONSTRAINT specjalista_pacjent_specjalista_fk
FOREIGN KEY (id_specjalista)
REFERENCES public.specjalista (id_specjalista)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dane_specjalista ADD CONSTRAINT specjalista_dane_specjalista_fk
FOREIGN KEY (id_specjalista)
REFERENCES public.specjalista (id_specjalista)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.plan_specjalisty ADD CONSTRAINT specjalista_plan_specjalisty_fk
FOREIGN KEY (id_specjalista)
REFERENCES public.specjalista (id_specjalista)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pacjent_specjalista ADD CONSTRAINT pacjent_pacjent_specjalista_fk
FOREIGN KEY (id_pacjent)
REFERENCES public.pacjent (id_pacjent)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dane_pacjent ADD CONSTRAINT pacjent_dane_pacjenta_fk
FOREIGN KEY (id_pacjent)
REFERENCES public.pacjent (id_pacjent)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.historia_choroby ADD CONSTRAINT pacjent_historia_choroby_fk
FOREIGN KEY (id_pacjent)
REFERENCES public.pacjent (id_pacjent)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.choroba_historia ADD CONSTRAINT historia_choroby_choroba_historia_fk
FOREIGN KEY (id_historia)
REFERENCES public.historia_choroby (id_historia)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;