--Táblák legenerálása

CREATE TABLE FELHASZNALO
(
    USERID NUMBER(*, 0) NOT NULL
    , FELHASZNALONEV VARCHAR2(20 BYTE) NOT NULL UNIQUE
    , EMAIL VARCHAR2(50 BYTE) NOT NULL
    , JELSZO VARCHAR2(250 BYTE) NOT NULL
    , TORZSVASARLO NUMBER(*, 0) DEFAULT 0
    , TELJES_NEV VARCHAR2(50 BYTE)
    , FELH_CIM VARCHAR2(50 BYTE)
    , FELH_KARTYASZAM VARCHAR2(50 BYTE)
    , ROLE VARCHAR2(20 BYTE) DEFAULT 'felhasznalo'
    , CONSTRAINT FELHASZNALO_PK PRIMARY KEY
    (
     USERID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX FELHASZNALO_PK ON FELHASZNALO (USERID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

COMMENT ON COLUMN FELHASZNALO.TORZSVASARLO IS '0 = nem 1 = torzsvasarlo';
COMMENT ON COLUMN FELHASZNALO.ROLE IS 'felhasznalo vagy admin';

CREATE TABLE KONYV
(
    KONYVID NUMBER(*, 0) NOT NULL
    , KIADAS_EVE NUMBER(*, 0)
    , OLDALSZAM NUMBER(*, 0)
    , DARAB NUMBER(*, 0)
    , SZERZO VARCHAR2(20 BYTE)
    , EKONYV NUMBER(*, 0)
    , KOTES VARCHAR2(20 BYTE)
    , AR NUMBER(*, 0)
    , MERET VARCHAR2(20 BYTE)
    , CIM VARCHAR2(50 BYTE)
    , MUFAJNEV VARCHAR2(20 BYTE)
    , ALMUFAJ VARCHAR2(20 BYTE)
    , KIADO VARCHAR2(20 BYTE)
    , CONSTRAINT KONYV_PK PRIMARY KEY
    (
     KONYVID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX KONYV_PK ON KONYV (KONYVID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

CREATE TABLE MUFAJ
(
    MUFAJNEV VARCHAR2(20 BYTE) NOT NULL
    , ALMUFAJ VARCHAR2(20 BYTE) NOT NULL
    , CONSTRAINT MUFAJ_PK PRIMARY KEY
    (
     MUFAJNEV
        , ALMUFAJ
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX MUFAJ_PK ON MUFAJ (MUFAJNEV ASC, ALMUFAJ ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

ALTER TABLE KONYV
    ADD CONSTRAINT KONYV_FK1 FOREIGN KEY
        (
         MUFAJNEV
            , ALMUFAJ
            )
        REFERENCES MUFAJ
            (
             MUFAJNEV
                , ALMUFAJ
                )
        ON DELETE SET NULL ENABLE;

CREATE TABLE RENDELES_F
(
    RENDELESFID NUMBER(*, 0) NOT NULL
    , AKCIO_E NUMBER
    , USERID NUMBER(*, 0)
    , KONYVID NUMBER(*, 0)
    , CONSTRAINT RENDELES_F_PK PRIMARY KEY
    (
     RENDELESFID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX RENDELES_F_PK ON RENDELES_F (RENDELESFID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

ALTER TABLE RENDELES_F
    ADD CONSTRAINT RENDELES_F_FK1 FOREIGN KEY
        (
         KONYVID
            )
        REFERENCES KONYV
            (
             KONYVID
                )
        ON DELETE SET NULL ENABLE;

ALTER TABLE RENDELES_F
    ADD CONSTRAINT RENDELES_F_FK2 FOREIGN KEY
        (
         USERID
            )
        REFERENCES FELHASZNALO
            (
             USERID
                )
        ON DELETE SET NULL ENABLE;

CREATE TABLE RENDELES_L
(
    RENDELESLID NUMBER(4, 0) NOT NULL
    , AKCIO_E NUMBER(1, 0)
    , IP_CIM VARCHAR2(20 BYTE) NOT NULL
    , KONYVID NUMBER(4, 0)
    , FELH_CIM VARCHAR2(20 BYTE)
    , FELH_KARTYA VARCHAR2(20 BYTE)
    , FELH_NEV VARCHAR2(20 BYTE)
    , CONSTRAINT RENDELES_L_PK PRIMARY KEY
    (
     RENDELESLID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX RENDELES_L_PK ON RENDELES_L (RENDELESLID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

ALTER TABLE RENDELES_L
    ADD CONSTRAINT RENDELES_L_FK2 FOREIGN KEY
        (
         KONYVID
            )
        REFERENCES KONYV
            (
             KONYVID
                )
        ON DELETE CASCADE ENABLE;

CREATE TABLE SZAMLA_F
(
    SZAMLAFID NUMBER(4, 0) NOT NULL
    , DATUM_EV NUMBER(4, 0) NOT NULL
    , AR NUMBER(20, 0)
    , RENDELESFID NUMBER(4, 0) NOT NULL
    , CONSTRAINT SZAMLA_F_PK PRIMARY KEY
    (
     SZAMLAFID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX SZAMLA_F_PK ON SZAMLA_F (SZAMLAFID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

ALTER TABLE SZAMLA_F
    ADD CONSTRAINT SZAMLA_F_FK1 FOREIGN KEY
        (
         RENDELESFID
            )
        REFERENCES RENDELES_F
            (
             RENDELESFID
                )
        ON DELETE SET NULL ENABLE;


CREATE TABLE SZAMLA_L
(
    SZAMLALID NUMBER(4, 0) NOT NULL
    , DATUM_EV NUMBER(4, 0) NOT NULL
    , AR NUMBER(20, 0) NOT NULL
    , RENDELESLID NUMBER(4, 0) NOT NULL
    , CONSTRAINT SZAMLA_L_PK PRIMARY KEY
    (
     SZAMLALID
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX SZAMLA_L_PK ON SZAMLA_L (SZAMLALID ASC)
    LOGGING
    TABLESPACE USERS
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE USERS
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLEL;

ALTER TABLE SZAMLA_L
    ADD CONSTRAINT SZAMLA_L_FK1 FOREIGN KEY
        (
         RENDELESLID
            )
        REFERENCES RENDELES_L
            (
             RENDELESLID
                )
        ON DELETE SET NULL ENABLE;


-- Sequence-ek
CREATE SEQUENCE FELHASZNALO_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE KONYV_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE RENDELES_F_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE RENDELES_L_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SZAMLA_F_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SZAMLA_L_SEQ START WITH 1 INCREMENT BY 1;

-- Triggerek
create or replace trigger FELHASZNALO_ID_TRIGGER
before insert on FELHASZNALO
for each row
begin
    if inserting and :new.USERID is null then
select FELHASZNALO_SEQ.nextval into :new.USERID from dual;
end if;
end;
/

create or replace trigger KONYV_ID_TRIGGER
before insert on KONYV
for each row
begin
    if inserting and :new.KONYVID is null then
select KONYV_SEQ.nextval into :new.KONYVID from dual;
end if;
end;
/

create or replace trigger RENDELES_F_ID_TRIGGER
before insert on RENDELES_F
for each row
begin
    if inserting and :new.RENDELESFID is null then
select RENDELES_F_SEQ.nextval into :new.RENDELESFID from dual;
end if;
end;
/

create or replace trigger RENDELES_L_ID_TRIGGER
before insert on RENDELES_L
for each row
begin
    if inserting and :new.RENDELESLID is null then
select RENDELES_L_SEQ.nextval into :new.RENDELESLID from dual;
end if;
end;
/

create or replace trigger SZAMLA_F_ID_TRIGGER
before insert on SZAMLA_F
for each row
begin
    if inserting and :new.SZAMLAFID is null then
select SZAMLA_F_SEQ.nextval into :new.SZAMLAFID from dual;
end if;
end;
/

create or replace trigger SZAMLA_L_ID_TRIGGER
    before insert on SZAMLA_L
    for each row
begin
    if inserting and :new.SZAMLALID is null then
        select SZAMLA_L_SEQ.nextval into :new.SZAMLALID from dual;
    end if;
end;
/

-- Triggerek segédcsomagja: Törzsvásárló frissítéshez
CREATE OR REPLACE PACKAGE torzsvasarlo_pkg IS
    TYPE userid_tab IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
    userids userid_tab;
    PROCEDURE reset;
END;
/
CREATE OR REPLACE PACKAGE BODY torzsvasarlo_pkg IS
    PROCEDURE reset IS
    BEGIN
        userids.DELETE;
    END;
END;
/

-- Trigger: USERID-k gyűjtése új rendelések után
CREATE OR REPLACE TRIGGER torzsvasarlo_collect
    AFTER INSERT ON RENDELES_F
    FOR EACH ROW
BEGIN
    torzsvasarlo_pkg.userids(torzsvasarlo_pkg.userids.COUNT + 1) := :NEW.USERID;
END;
/

-- Trigger: Törzsvásárló státusz frissítése a RENDELES_F alapján
CREATE OR REPLACE TRIGGER torzsvasarlo_update
    AFTER INSERT ON RENDELES_F
DECLARE
    v_userid NUMBER;
    v_count  NUMBER;
BEGIN
    IF torzsvasarlo_pkg.userids.COUNT > 0 THEN
        FOR i IN torzsvasarlo_pkg.userids.FIRST .. torzsvasarlo_pkg.userids.LAST LOOP
                v_userid := torzsvasarlo_pkg.userids(i);

                SELECT COUNT(*) INTO v_count
                FROM RENDELES_F
                WHERE USERID = v_userid;

                IF v_count >= 3 THEN
                    UPDATE FELHASZNALO
                    SET TORZSVASARLO = 1
                    WHERE USERID = v_userid;
                END IF;
            END LOOP;

        torzsvasarlo_pkg.reset;
    END IF;
END;
/

-- Trigger létrehozása, amely akkor fut le, ha új rekord kerül a RENDELES_F táblába
CREATE OR REPLACE TRIGGER KONYVDARAB_CSOKKENTES_F
    AFTER INSERT ON RENDELES_F          -- Csak beszúrás (INSERT) esetén aktiválódik
    FOR EACH ROW                        -- Minden új sorra külön lefut
BEGIN
    -- A kapcsolódó könyv darabszámát csökkenti eggyel
    UPDATE KONYV
    SET DARAB = DARAB - 1
    WHERE KONYVID = :NEW.KONYVID;       -- A frissen beszúrt rendelés könyvazonosítója alapján
END;
/

-- Trigger létrehozása, amely akkor fut le, ha új rekord kerül a RENDELES_L táblába
CREATE OR REPLACE TRIGGER KONYVDARAB_CSOKKENTES_L
    AFTER INSERT ON RENDELES_L          -- Csak beszúrás (INSERT) esetén aktiválódik
    FOR EACH ROW                        -- Minden új sorra külön lefut
BEGIN
    -- A kapcsolódó könyv darabszámát csökkenti eggyel
    UPDATE KONYV
    SET DARAB = DARAB - 1
    WHERE KONYVID = :NEW.KONYVID;       -- A frissen beszúrt rendelés könyvazonosítója alapján
END;
/

-- Trigger létrehozása a SZAMLA_F automatikus feltöltésére rendelés esetén
CREATE OR REPLACE TRIGGER SZAMLA_LETREHOZAS_F
    AFTER INSERT ON RENDELES_F
    FOR EACH ROW
DECLARE
    konyv_ar NUMBER;
BEGIN
    -- Lekérjük a könyv árát a rendeléshez tartozó könyv alapján
    SELECT AR INTO konyv_ar
    FROM KONYV
    WHERE KONYVID = :NEW.KONYVID;

    -- Beszúrjuk a számlát a SZAMLA_F táblába
    INSERT INTO SZAMLA_F (DATUM_EV, AR, RENDELESFID)
    VALUES (EXTRACT(YEAR FROM SYSDATE), konyv_ar, :NEW.RENDELESFID);
END;
/

-- Trigger létrehozása a SZAMLA_L automatikus feltöltésére rendelés esetén
CREATE OR REPLACE TRIGGER SZAMLA_LETREHOZAS_L
    AFTER INSERT ON RENDELES_L
    FOR EACH ROW
DECLARE
    konyv_ar NUMBER;
BEGIN
    -- Lekérjük a könyv árát a rendeléshez tartozó könyv alapján
    SELECT AR INTO konyv_ar
    FROM KONYV
    WHERE KONYVID = :NEW.KONYVID;

    -- Beszúrjuk a számlát a SZAMLA_L táblába
    INSERT INTO SZAMLA_L (DATUM_EV, AR, RENDELESLID)
    VALUES (EXTRACT(YEAR FROM SYSDATE), konyv_ar, :NEW.RENDELESLID);
END;
/

-- Létrehozunk egy triggert, ami a RENDELES_F tábla minden új beszúrásánál (INSERT)
-- automatikusan beállítja az AKCIO_E mezőt attól függően, hogy a könyv ára 2000 Ft alatt van-e.

CREATE OR REPLACE TRIGGER AKCIOS_MEZO_TRIGGER_F
    BEFORE INSERT ON RENDELES_F          -- Mielőtt egy új sor beszúrásra kerülne a RENDELES_F táblába
    FOR EACH ROW                         -- Minden egyes új sorra külön lefut
DECLARE
    v_ar NUMBER;                       -- Változó a kiválasztott könyv árának eltárolására
BEGIN
    -- Lekérdezzük a könyv árát a KONYV táblából
    SELECT AR INTO v_ar
    FROM KONYV
    WHERE KONYVID = :NEW.KONYVID;

    -- Ha a könyv ára kisebb, mint 2000, akkor akciós (1), különben nem akciós (0)
    IF v_ar < 2000 THEN
        :NEW.AKCIO_E := 1;
    ELSE
        :NEW.AKCIO_E := 0;
    END IF;
END;
/

-- Létrehozunk egy triggert, ami a RENDELES_L tábla minden új beszúrásánál (INSERT)
-- automatikusan beállítja az AKCIO_E mezőt attól függően, hogy a könyv ára 2000 Ft alatt van-e.

CREATE OR REPLACE TRIGGER AKCIOS_MEZO_TRIGGER_L
    BEFORE INSERT ON RENDELES_L          -- Mielőtt egy új sor beszúrásra kerülne a RENDELES_L táblába
    FOR EACH ROW                         -- Minden egyes új sorra külön lefut
DECLARE
    v_ar NUMBER;                       -- Változó a kiválasztott könyv árának eltárolására
BEGIN
    -- Lekérdezzük a könyv árát a KONYV táblából
    SELECT AR INTO v_ar
    FROM KONYV
    WHERE KONYVID = :NEW.KONYVID;

    -- Ha a könyv ára kisebb, mint 2000, akkor akciós (1), különben nem akciós (0)
    IF v_ar < 2000 THEN
        :NEW.AKCIO_E := 1;
    ELSE
        :NEW.AKCIO_E := 0;
    END IF;
END;
/

-- TRIGGER: Automatikusan frissíti a SZAMLA_F tábla AR mezőjét a RENDELES_F-ben lévő könyv árának megfelelően

CREATE OR REPLACE TRIGGER SZAMLAF_AR_FRISSITES
    BEFORE INSERT ON SZAMLA_F
    FOR EACH ROW
BEGIN
    -- Beállítja a számla árát a rendeléshez tartozó könyv árának megfelelően
    SELECT K.AR INTO :NEW.AR
    FROM KONYV K
             JOIN RENDELES_F R ON R.KONYVID = K.KONYVID
    WHERE R.RENDELESFID = :NEW.RENDELESFID;
END;
/

-- Trigger, amely beállítja a SZAMLA_L.AR értékét a kapcsolódó könyv árának megfelelően
CREATE OR REPLACE TRIGGER SZAMLA_L_AR_BEALLITAS
    BEFORE INSERT ON SZAMLA_L
    FOR EACH ROW
DECLARE
    v_ar KONYV.AR%TYPE;
BEGIN
    -- Könyv ár lekérdezése a RENDELES_L táblán keresztül
    SELECT k.AR INTO v_ar
    FROM KONYV k
             JOIN RENDELES_L rl ON rl.KONYVID = k.KONYVID
    WHERE rl.RENDELESLID = :NEW.RENDELESLID;

    -- Beállítjuk a számlára az árat
    :NEW.AR := v_ar;
END;
/

-- Csomag fejléc, ami eltárolja azoknak a könyveknek az ID-jét,
-- amelyeket törölni kell, mert elfogytak (DARAB = 0)
CREATE OR REPLACE PACKAGE KONYV_TORLES_PKG IS
    TYPE ID_LIST IS TABLE OF KONYV.KONYVID%TYPE INDEX BY PLS_INTEGER;
    KONYV_IDS ID_LIST;    -- Ide kerülnek az ID-k
    INDEXER NUMBER := 0;  -- Indexelő változó
END KONYV_TORLES_PKG;
/

-- Trigger: frissítés előtt fut soronként
-- Ha egy könyv darabszáma 0 lesz, eltároljuk az ID-jét a csomagban
CREATE OR REPLACE TRIGGER KONYV_TORLES_COLLECT
    BEFORE UPDATE ON KONYV
    FOR EACH ROW
BEGIN
    IF :NEW.DARAB = 0 THEN
        KONYV_TORLES_PKG.INDEXER := KONYV_TORLES_PKG.INDEXER + 1;
        KONYV_TORLES_PKG.KONYV_IDS(KONYV_TORLES_PKG.INDEXER) := :NEW.KONYVID;
    END IF;
END;
/

-- Trigger: frissítés után fut egyszer a teljes UPDATE-re
-- Végigmegy a csomagban eltárolt ID-kon, és törli az adott könyveket
CREATE OR REPLACE TRIGGER KONYV_TORLES_DELETE
    AFTER UPDATE ON KONYV
BEGIN
    FOR i IN 1 .. KONYV_TORLES_PKG.INDEXER LOOP
            DELETE FROM KONYV WHERE KONYVID = KONYV_TORLES_PKG.KONYV_IDS(i);
        END LOOP;
    -- Csomag újrainicializálása
    KONYV_TORLES_PKG.INDEXER := 0;
END;
/


--Adatok felvitele
insert into felhasznalo (felhasznalonev, email, jelszo, torzsvasarlo, teljes_nev, felh_cim, felh_kartyaszam) values ('felh1', 'felh1@gmail.com', 'JoErosjelszo', 1, 'Egyes Péter', 'Szeged, Ady tér 2', '223421');
insert into felhasznalo (felhasznalonev, email, jelszo, torzsvasarlo, teljes_nev, felh_cim, felh_kartyaszam) values ('felh2', 'felh2@gmail.com', 'JoErosjelszo2', 0, 'Kettes Ember', 'Szeged, Ady tér 4', '727921');
insert into felhasznalo (felhasznalonev, email, jelszo, torzsvasarlo, teljes_nev, felh_cim, felh_kartyaszam) values ('felh3', 'felh3@gmail.com', 'JoErosjelszo3', 1, 'dd Péter', 'Szdeged, Addy tédr 2', '543521');
insert into felhasznalo (felhasznalonev, email, jelszo, torzsvasarlo, teljes_nev, felh_cim, felh_kartyaszam) values ('felh4', 'felh4@gmail.com', 'JoErosjelszo4', 1, 'Harci Ménes', 'Szeged, Abby tier 6', '323131');
insert into felhasznalo (felhasznalonev, email, jelszo, torzsvasarlo, teljes_nev, felh_cim, felh_kartyaszam) values ('felh5', 'felh5@yahoo.co.uk', 'JoErosjelszo5', 0, 'Hapci Ménes', 'Szeged,Dóm tér 16', '336431');

INSERT INTO MUFAJ VALUES ('Sci-Fi', 'Horror');
INSERT INTO MUFAJ VALUES ('Sci-Fi', 'Kaland');
INSERT INTO MUFAJ VALUES ('Sci-Fi', 'Vígjáték');
INSERT INTO MUFAJ VALUES ('Fantasy', 'Horror');
INSERT INTO MUFAJ VALUES ('Fantasy', 'Vígjáték');

insert into konyv (kiadas_eve, oldalszam, darab, szerzo, ekonyv, kotes, ar, meret, cim, mufajnev, almufaj, kiado) values (2008, 600, 5, 'Probaszerzo1', '1', 'Keményfedeles' ,5000, 'Nagy', 'probacim1', 'Sci-Fi', 'Horror', 'probakiado1');
insert into konyv (kiadas_eve, oldalszam, darab, szerzo, ekonyv, kotes, ar, meret, cim, mufajnev, almufaj, kiado) values (2001, 500, 10, 'Probaszerzo2', '1', 'Papír' ,5000, 'Normál', 'probacim2', 'Sci-Fi', 'Horror', 'probakiado2');
insert into konyv (kiadas_eve, oldalszam, darab, szerzo, ekonyv, kotes, ar, meret, cim, mufajnev, almufaj, kiado) values (2002, 400, 15, 'Probaszerzo3', '1', 'Keményfedeles' ,4000, 'Kicsi', 'probacim3', 'Sci-Fi', 'Horror', 'probakiado3');
insert into konyv (kiadas_eve, oldalszam, darab, szerzo, ekonyv, kotes, ar, meret, cim, mufajnev, almufaj, kiado) values (2003, 300, 20, 'Probaszerzo4', '1', 'Keményfedeles' , 2500, 'Közepes', 'probacim4', 'Sci-Fi', 'Horror', 'probakiado4');
insert into konyv (kiadas_eve, oldalszam, darab, szerzo, ekonyv, kotes, ar, meret, cim, mufajnev, almufaj, kiado) values (2004, 200, 25, 'Probaszerzo5', '0', 'Papír' ,  3500, 'Nagy', 'probacim5', 'Sci-Fi', 'Horror', 'probakiado5');

INSERT INTO RENDELES_F (akcio_e, userid, konyvid) VALUES(0, 3, 3);
INSERT INTO RENDELES_F (akcio_e, userid, konyvid) VALUES(1, 2, 4);
INSERT INTO RENDELES_F (akcio_e, userid, konyvid) VALUES(1, 1, 1);
INSERT INTO RENDELES_F (akcio_e, userid, konyvid) VALUES(0, 3, 3);
INSERT INTO RENDELES_F (akcio_e, userid, konyvid) VALUES(1, 2, 2);

INSERT INTO RENDELES_L (akcio_e, ip_cim, konyvid, felh_cim, felh_kartya, felh_nev) VALUES(0, '192.168.0.1', 5, 'Ady tér 2', '2321321', 'Jani Egy');
INSERT INTO RENDELES_L (akcio_e, ip_cim, konyvid, felh_cim, felh_kartya, felh_nev) VALUES(0, '192.168.0.2', 1, 'Ady tér 4', '2121321', 'Jani Ketto');
INSERT INTO RENDELES_L (akcio_e, ip_cim, konyvid, felh_cim, felh_kartya, felh_nev) VALUES(0, '192.168.0.3', 2, 'Ady tér 5', '2332411', 'Jani Harom');
INSERT INTO RENDELES_L (akcio_e, ip_cim, konyvid, felh_cim, felh_kartya, felh_nev) VALUES(0, '192.168.0.4', 3, 'Ady tér 2', '2323211', 'Jani Negy');
INSERT INTO RENDELES_L (akcio_e, ip_cim, konyvid, felh_cim, felh_kartya, felh_nev) VALUES(0, '192.168.0.5', 4, 'Ady tér 3', '2321321', 'Jani Ot');

INSERT INTO SZAMLA_F (datum_ev, ar, rendelesfid) VALUES (2023, 15000, 1);
INSERT INTO SZAMLA_F (datum_ev, ar, rendelesfid) VALUES (2022, 15000, 2);
INSERT INTO SZAMLA_F (datum_ev, ar, rendelesfid) VALUES (2021, 15000, 3);
INSERT INTO SZAMLA_F (datum_ev, ar, rendelesfid) VALUES (2023, 15000, 4);

INSERT INTO SZAMLA_L (datum_ev, ar, rendeleslid) VALUES (2025, 15000, 1);
INSERT INTO SZAMLA_L (datum_ev, ar, rendeleslid) VALUES (2025, 15000, 2);
INSERT INTO SZAMLA_L (datum_ev, ar, rendeleslid) VALUES (2025, 15000, 3);
INSERT INTO SZAMLA_L (datum_ev, ar, rendeleslid) VALUES (2025, 15000, 4);