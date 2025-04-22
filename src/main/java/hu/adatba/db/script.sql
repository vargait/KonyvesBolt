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
create or replace trigger FELHASZNALO_TRG1
before insert on FELHASZNALO
for each row
begin
    if inserting and :new.USERID is null then
select FELHASZNALO_SEQ.nextval into :new.USERID from dual;
end if;
end;
/

create or replace trigger KONYV_TRG
before insert on KONYV
for each row
begin
    if inserting and :new.KONYVID is null then
select KONYV_SEQ.nextval into :new.KONYVID from dual;
end if;
end;
/

create or replace trigger RENDELES_F_TRG
before insert on RENDELES_F
for each row
begin
    if inserting and :new.RENDELESFID is null then
select RENDELES_F_SEQ.nextval into :new.RENDELESFID from dual;
end if;
end;
/

create or replace trigger RENDELES_L_TRG
before insert on RENDELES_L
for each row
begin
    if inserting and :new.RENDELESLID is null then
select RENDELES_L_SEQ.nextval into :new.RENDELESLID from dual;
end if;
end;
/

create or replace trigger SZAMLA_F_TRG
before insert on SZAMLA_F
for each row
begin
    if inserting and :new.SZAMLAFID is null then
select SZAMLA_F_SEQ.nextval into :new.SZAMLAFID from dual;
end if;
end;
/

create or replace trigger SZAMLA_L_TRG
before insert on SZAMLA_L
for each row
begin
    if inserting and :new.SZAMLALID is null then
select SZAMLA_L_SEQ.nextval into :new.SZAMLALID from dual;
end if;
end;
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