--DROP TABLE t_bill;
--DROP TABLE t_table_credit;
--DROP TABLE t_credit;

--cre_id : Identifiant de la table
--cre_reference : Reference de la facture
--cre_amount : Montant de l'avoir
CREATE TABLE t_credit
(
  cre_id serial,
  cre_reference varchar(16) NOT NULL,
  cre_amount numeric(10,2) NOT NULL,
  CONSTRAINT cre_id_pk PRIMARY KEY (cre_id),
  CONSTRAINT cre_reference_uni UNIQUE (cre_reference)
) WITHOUT OIDS;

--tcr_id : Identifiant de la table
--cre_id : Identifiant de la table t_credit
--dtb_id : Identifiant de la table t_dinner_table 
CREATE TABLE t_table_credit
(
  tcr_id serial,
  dtb_id int8 NOT NULL,
  cre_id int8 NOT NULL,
  CONSTRAINT tcr_id_pk PRIMARY KEY (tcr_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cre_id_fk FOREIGN KEY (cre_id) REFERENCES t_credit (cre_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cre_id_uni UNIQUE (cre_id)
) WITHOUT OIDS;

--bil_id : Identifiant de la table
--dtb_id : Identifiant de la table t_dinner_table 
--bil_reference : Reference de la facture
--bil_order : Ordre/Utiliser pour Numero de table
--bil_meal_number : Nombre de personne
--bil_amount : Montant partiel
--bil_printing: Permet de savoir si on veut imprimer la facture
CREATE TABLE t_bill
(
  bil_id serial,
  dtb_id int8 NOT NULL,
  bil_reference varchar(40) NOT NULL,
  bil_order int8,
  bil_meal_number int8,
  bil_amount numeric(10,2) NOT NULL,
  bil_printing bool NOT NULL DEFAULT true,
  CONSTRAINT bil_id_pk PRIMARY KEY (bil_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT bil_reference_uni UNIQUE (bil_reference)
) WITHOUT OIDS;



INSERT INTO t_product_special_code (psc_id, psc_code) VALUES (5, 'CANCEL_ORDER');

INSERT INTO t_product (pdt_id, psc_id) VALUES ('@', 5);

INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('@', 'PRODUIT ANNULE :');

--res_id serial,
--res_registration_date
--res_reference reference unique permettant par exemple d'identifier quelle type impression faut-il effectuer grace au nom du fichier javascript(c'est pour cela qu'il ne faut pas d'espace)
--res_name 
--res_address_road 
--res_address_zip 
--res_address_city 
--res_phone 
--res_vat_ref 
--res_visa_ref 
--res_triple_DES_key 
--res_vat_by_takeaway 
--res_takeaway_basic_reduction 
--res_takeaway_min_amount_reduction
--res_specific_round 1 = HALF ROUND 2 = TENTH ROUND
CREATE TABLE t_restaurant
(
  res_id serial,
  res_registration_date timestamp,
  res_reference varchar(90) NOT NULL,
  res_name varchar(80) NOT NULL,
  res_address_road varchar(40) NOT NULL,
  res_address_zip varchar(10) NOT NULL,
  res_address_city varchar(20) NOT NULL,
  res_phone varchar(30) NOT NULL,
  res_vat_ref varchar(25) NOT NULL,
  res_visa_ref varchar(25) NOT NULL,
  res_triple_DES_key varchar(24) NOT NULL,
  res_vat_by_takeaway bool NOT NULL DEFAULT true,
  res_takeaway_basic_reduction int8 NOT NULL,
  res_takeaway_min_amount_reduction int8 NOT NULL,
  res_specific_round int8 NOT NULL,
  CONSTRAINT res_id_pk PRIMARY KEY (res_id),
  CONSTRAINT res_reference_uni UNIQUE (res_reference)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_restaurant (res_id, res_registration_date, res_reference, res_name, res_address_road, res_address_zip, res_address_city, res_phone, res_vat_ref, res_visa_ref, res_triple_DES_key, res_vat_by_takeaway, res_takeaway_basic_reduction, res_takeaway_min_amount_reduction, res_specific_round) VALUES (0, '2003-01-01 00:00:00', 'KIM_SAN', 'KIM SAN', '11 allée clémencet', '93 340', 'Le Raincy', '01 43 02 50 90', 'FR 19 313 105 397 000 19', 'R.C B 313 105 397', '12345678ABCDDCBA12345678', true, 10, 15, 1);
INSERT INTO t_restaurant (res_id, res_registration_date, res_reference, res_name, res_address_road, res_address_zip, res_address_city, res_phone, res_vat_ref, res_visa_ref, res_triple_DES_key, res_vat_by_takeaway, res_takeaway_basic_reduction, res_takeaway_min_amount_reduction, res_specific_round) VALUES (1, '2007-01-01 00:00:00', 'KIM_SAN_95', 'KIM SAN', '11 allée clémencet', '93 340', 'Le Raincy', '01 43 02 50 90', 'FR 19 313 105 397 000 19', 'R.C B 313 105 397', '12345678ABCDDCBA12345678', true, 5, 1, 2);

ALTER TABLE t_user DROP COLUMN loc_id;
ALTER TABLE t_user ADD COLUMN loc_id varchar(3);
ALTER TABLE t_user ALTER COLUMN loc_id SET STORAGE PLAIN;
ALTER TABLE t_user ALTER COLUMN loc_id SET DEFAULT 'fr'::character varying;
UPDATE t_user SET loc_id = 'fr';
ALTER TABLE t_user ALTER COLUMN loc_id SET NOT NULL;
ALTER TABLE t_user ADD CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

--ALTER TABLE t_user DROP COLUMN res_id;
ALTER TABLE t_user ADD COLUMN res_id int8;
ALTER TABLE t_user ALTER COLUMN res_id SET STORAGE PLAIN;
ALTER TABLE t_user ALTER COLUMN res_id SET DEFAULT 0;
UPDATE t_user SET res_id = 0;
ALTER TABLE t_user ALTER COLUMN res_id SET NOT NULL;
ALTER TABLE t_user ADD CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE t_print_info_plus
(
  pip_id serial,
  res_id int8 NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  pip_order int8 NOT NULL,
  pip_alignment varchar(20) NOT NULL,
  CONSTRAINT pip_id_pk PRIMARY KEY (pip_id),
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;

CREATE TABLE t_print_info_plus_language
(
  pip_id int8 NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  pil_label varchar(40) NOT NULL,
  CONSTRAINT pip_id_loc_id_pk PRIMARY KEY (pip_id, loc_id),
  CONSTRAINT rol_id_fk FOREIGN KEY (pip_id) REFERENCES t_print_info_plus (pip_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;


