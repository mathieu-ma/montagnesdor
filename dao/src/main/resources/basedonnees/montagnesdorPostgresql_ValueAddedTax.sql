--vat_id : Identifiant de cette table
--vat_value : Valeur en pourcentage de la tva : c'est aussi une clé primaire
CREATE TABLE t_value_added_tax
(
  vat_id serial,
  vat_value numeric(10,2) NOT NULL DEFAULT 19.60,
  CONSTRAINT vat_id_pk PRIMARY KEY (vat_id),
  CONSTRAINT vat_value_uni UNIQUE (vat_value)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_value_added_tax (vat_id, vat_value) VALUES (1, '19.6');
INSERT INTO t_value_added_tax (vat_id, vat_value) VALUES (2, '5.5');
