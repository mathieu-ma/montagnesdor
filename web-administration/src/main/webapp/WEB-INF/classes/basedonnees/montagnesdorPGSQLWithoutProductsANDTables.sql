DROP TABLE t_vat_revenue;
DROP TABLE t_day_revenue;
DROP TABLE t_type_table_language;
DROP TABLE t_type_table;
DROP TABLE t_cashing;
DROP TABLE t_category_relation;
DROP TABLE t_stats_consumption_product;
DROP TABLE t_order_line;
DROP TABLE t_product_language;
DROP TABLE t_vat_table;
DROP TABLE t_dinner_table;
DROP TABLE t_product;
DROP TABLE t_product_part_language;
DROP TABLE t_product_part;
DROP TABLE t_product_special_code;
DROP TABLE t_value_added_tax;
DROP TABLE t_category_language;
DROP TABLE t_category;
DROP TABLE t_user_locale;
DROP TABLE t_user;
DROP TABLE t_user_authentication;
DROP TABLE t_user_role_language;
DROP TABLE t_user_role;
DROP TABLE t_locale_language;
DROP TABLE t_locale;


--loc_id : Identifiant de cette table
CREATE TABLE t_locale
(
  loc_id varchar(3),
  CONSTRAINT loc_id_pk PRIMARY KEY (loc_id)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_locale (loc_id) VALUES ('en');
INSERT INTO t_locale (loc_id) VALUES ('fr');
INSERT INTO t_locale (loc_id) VALUES ('zh');


--loc_id : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id_language forment la clé primaire de cette table.
--loc_id_language : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
--lol_label : Ce champ donne la description du champ loc_id suivant le champ loc_id_language. Par example : si loc_id = fr et loc_id_language = fr lol_label = Français ou bien loc_id = fr et loc_id_language = zh lol_label = Chinois.
CREATE TABLE t_locale_language
(
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  loc_id_language varchar(3) NOT NULL DEFAULT 'fr',
  lol_label varchar(50) NOT NULL,
  CONSTRAINT loc_id_loc_id_language_pk PRIMARY KEY (loc_id, loc_id_language),
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_language_fk FOREIGN KEY (loc_id_language) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('en', 'fr', 'Anglais');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('fr', 'fr', 'Francais');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('zh', 'fr', 'Chinois');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('en', 'en', 'English');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('fr', 'en', 'French');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('zh', 'en', 'Chinese');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('en', 'zh', 'Anglais en chinois');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('fr', 'zh', 'Francais en chinois');
INSERT INTO t_locale_language (loc_id, loc_id_language, lol_label) VALUES ('zh', 'zh', 'Chinois en chinois');


--rol_id : Idetntifiant de cette table
--rol_label_code : Rôle de l'utilisateur
CREATE TABLE t_user_role
(
  rol_id serial,
  rol_label_code varchar(20) NOT NULL,  
  CONSTRAINT rol_id_pk PRIMARY KEY (rol_id),
  CONSTRAINT rol_label_code_uni UNIQUE (rol_label_code)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_user_role (rol_id, rol_label_code) VALUES (1, 'ADMINISTRATOR');
INSERT INTO t_user_role (rol_id, rol_label_code) VALUES (2, 'USER');
INSERT INTO t_user_role (rol_id, rol_label_code) VALUES (3, 'CUSTOMER');
INSERT INTO t_user_role (rol_id, rol_label_code) VALUES (4, 'GUEST');

--rol_id : Idetntifiant de la table t_user_role : c'est une clé étrangère de cette table
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser ou bien celle de l'utilisateur connecté
--url_label : Rôle de l'utilisateur dans une langue choisie
CREATE TABLE t_user_role_language
(
  rol_id int8 NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  url_label varchar(20) NOT NULL,
  CONSTRAINT rol_id_loc_id_pk PRIMARY KEY (rol_id, loc_id),
  CONSTRAINT rol_id_fk FOREIGN KEY (rol_id) REFERENCES t_user_role (rol_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (1, 'en', 'Administrator');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (2, 'en', 'User');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (3, 'en', 'Customer');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (4, 'en', 'Guest');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (1, 'fr', 'Administrateur');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (2, 'fr', 'Utilisateur');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (3, 'fr', 'Client');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (4, 'fr', 'Invit�');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (1, 'zh', 'Admin en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (2, 'zh', 'Util en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (3, 'zh', 'Client en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (4, 'zh', 'Invit� en chinois');


--aut_id : Identifiant de cette table
--aut_login : Identifiant de l'utilisateur
--aut_password : Mot de passe de l'utilisateur
--aut_level_pass1 : Niveau de mot de passe 1
--aut_level_pass2 : Niveau de mot de passe 2
--aut_level_pass3 : Niveau de mot de passe 3
CREATE TABLE t_user_authentication
(
  aut_id serial,
  aut_login varchar(20) NOT NULL,  
  aut_password varchar(20) NOT NULL,
  aut_level_pass1 varchar(20),
  aut_level_pass2 varchar(20),
  aut_level_pass3 varchar(20),
  CONSTRAINT aut_id_pk PRIMARY KEY (aut_id),
  CONSTRAINT aut_login_uni UNIQUE (aut_login)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_user_authentication (aut_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3) VALUES (1, 'kimsan', 'kimsan', 'kimsan', 'kimsan', 'kimsan');
INSERT INTO t_user_authentication (aut_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3) VALUES (2, 'mathieu', 'mathieu', 'mathieu', NULL, NULL);
INSERT INTO t_user_authentication (aut_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3) VALUES (3, 'maxime', 'maxime', NULL, NULL, NULL);


--usr_id : Identifiant de cette table
--aut_id : Identifiant de la table t_user_authentication : clé étrangère de cette table
--rol_id : Identifiant de la table t_user_role : clé étrangère de cette table
--loc_id : Identifiant de la table t_locale permettant de savoir dans quel language l'impression doit se faire
--usr_name : Nom de l'utilisateur
--usr_forename1 : Premier prénom de l'utilisateur
--usr_forename2 : Deuxième prénom de l'utilisateur
--usr_birthdate : Date de naissance
--usr_sex : Sexe de l'utilisateur : 0 homme, 1 femme
--usr_picture : Photo de l'utilisateur
CREATE TABLE t_user
(
  usr_id serial,
  aut_id int8 NOT NULL DEFAULT 0,
  rol_id int8 NOT NULL DEFAULT 0,
  loc_id varchar(3),
  usr_name varchar(20) NOT NULL,  
  usr_forename1 varchar(20) NOT NULL,
  usr_forename2 varchar(20),
  usr_birthdate date NOT NULL,
  usr_sex int2 NOT NULL DEFAULT 0,
  usr_picture bytea,
  CONSTRAINT usr_id_pk PRIMARY KEY (usr_id),
  CONSTRAINT aut_id_fk FOREIGN KEY (aut_id) REFERENCES t_user_authentication (aut_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rol_id_fk FOREIGN KEY (rol_id) REFERENCES t_user_role (rol_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT aut_id_uni UNIQUE (aut_id)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_user (usr_id, aut_id, rol_id, usr_name, usr_forename1, usr_forename2, usr_birthdate, usr_sex, usr_picture) VALUES (1, 1, 1, 'MA', 'Edouard', NULL, '1968-08-15', 0, NULL);
INSERT INTO t_user (usr_id, aut_id, rol_id, usr_name, usr_forename1, usr_forename2, usr_birthdate, usr_sex, usr_picture) VALUES (2, 2, 2, 'MA', 'Mathieu', NULL, '1970-08-15', 0, NULL);
INSERT INTO t_user (usr_id, aut_id, rol_id, usr_name, usr_forename1, usr_forename2, usr_birthdate, usr_sex, usr_picture) VALUES (3, 3, 3, 'MA', 'Maxime', 'Max', '2003-08-15', 1, NULL);


--ulo_id : Identifiant de cette table
--usr_id : Identifiant de la table t_user : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
--loc_id : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
CREATE TABLE t_user_locale
(
  ulo_id serial,
  usr_id int8 NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  CONSTRAINT ulo_id_pk PRIMARY KEY (ulo_id),
  CONSTRAINT usr_id_loc_id_uni UNIQUE (usr_id, loc_id),
  CONSTRAINT usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (1, 1, 'en');
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (2, 1, 'fr');
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (3, 1, 'zh');
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (4, 2, 'en');
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (5, 2, 'fr');
INSERT INTO t_user_locale (ulo_id, usr_id, loc_id) VALUES (6, 3, 'zh');


--cat_id : Identifiant de cette table
CREATE TABLE t_category
(
  cat_id serial,
  CONSTRAINT cat_id_pk PRIMARY KEY (cat_id)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------


--cat_id : Identifiant de la table t_category
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser ou bien celle de l'utilisateur connecté
--ctl_label : Libellé de la catégorie
CREATE TABLE t_category_language
(
  cat_id serial,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  ctl_label varchar(50) NOT NULL,
  CONSTRAINT cat_id_loc_id_pk PRIMARY KEY (cat_id, loc_id),
  CONSTRAINT cat_id_fk FOREIGN KEY (cat_id) REFERENCES t_category (cat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------


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
INSERT INTO t_value_added_tax (vat_id, vat_value) VALUES (2, '5.50');


--psc_id : Identifiant de cette table
--psc_code : Code applicatif
CREATE TABLE t_product_special_code
(
  psc_id serial,
  psc_code varchar(50) NOT NULL,
  CONSTRAINT psc_id_pk PRIMARY KEY (psc_id),
  CONSTRAINT psc_code_uni UNIQUE (psc_code)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_product_special_code (psc_id, psc_code) VALUES (1, 'NOTHING');
INSERT INTO t_product_special_code (psc_id, psc_code) VALUES (2, 'OFFERED_PRODUCT');
INSERT INTO t_product_special_code (psc_id, psc_code) VALUES (3, 'REDUCED_ORDER'); 
INSERT INTO t_product_special_code (psc_id, psc_code) VALUES (4, 'USER_ORDER');


--prp_id : Identifiant de cette table
CREATE TABLE t_product_part
(
  prp_id serial,
  CONSTRAINT prp_id_pk PRIMARY KEY (prp_id)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_product_part (prp_id) VALUES (1);
INSERT INTO t_product_part (prp_id) VALUES (2);
INSERT INTO t_product_part (prp_id) VALUES (3);
INSERT INTO t_product_part (prp_id) VALUES (4);
INSERT INTO t_product_part (prp_id) VALUES (5);
INSERT INTO t_product_part (prp_id) VALUES (6);


--pdt_id : Code produit, identifiant de cette table
--psc_id : Identifiant de la table t_product_special_code. Ce champ permet de savoir s'il faut faire un traitement spécial sur ce produit, par exemple quand le champ pdt_id est "#", cela veut dire la ligne de commande correspondant à ce code est offert, la valeur du produit offert se trouve dans le champ orl_special_code_value de la table t_order_line
--pdt_price : Prix du produit
--pdt_colorRGB : Couleur du produit(de la forme FFFFFF) : permet par exemple d'afficher une ligne de commande dans la couleur choisie, si cette valeur est nulle on garde la couleur par d�faut du css
--vat_id : Identifiant de la table t_value_added_tax permettant de récupérer la valeur en pourcentage de la TVA du produit : ceci représente la valeur du champ vat_value de la table t_value_added_tax : c'est une clé étrangère de la table t_value_added_tax
CREATE TABLE t_product
(
  pdt_id varchar(5) NOT NULL,
  psc_id int8 NOT NULL DEFAULT 1,
  pdt_price numeric(10,2) NOT NULL DEFAULT 0.00,
  pdt_colorRGB varchar(6),
  vat_id int8 NOT NULL DEFAULT 1,
  CONSTRAINT pdt_id_pk PRIMARY KEY (pdt_id),
  CONSTRAINT psc_id FOREIGN KEY (psc_id) REFERENCES t_product_special_code (psc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_product (pdt_id, psc_id) VALUES ('#', 2);
INSERT INTO t_product (pdt_id, psc_id) VALUES ('-', 3);
INSERT INTO t_product (pdt_id, psc_id) VALUES ('/', 4);


--prp_id : Identifiant de la table t_product_part : clé étrangère, ce champ avec le champ loc_id forment une clé primaire.
--ppl_label : Description de la partie auquelle appartient une ligne de commande : par exemple, Appéritif, Entrée, Plat de résistance, Dessert, Digestif, Divers ...  
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser. Ce champ avec le champ orp_id forment une clé primaire
CREATE TABLE t_product_part_language
(
  prp_id int8 NOT NULL,
  ppl_label varchar(50) NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  CONSTRAINT prp_id_loc_id_pk PRIMARY KEY (prp_id, loc_id),
  CONSTRAINT prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (1, 'Divers', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (2, 'App�ritif', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (3, 'Entr�e', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (4, 'Plat', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (5, 'Dessert', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (6, 'Digestif', 'fr');


--dtb_id : Identifiant de la table
--usr_id : Identifiant de la table t_user : cl� �trang�re de cette table.
--roo_id : Identifant de la salle dans laquelle se trouve cette table
--dtb_number : Numéro de la table
--dtb_customers_number : Nombre de personnes occupant la table
--dtb_scp_quantitys_sum : Somme de toutes les quantités commandées par la table
--dtb_accounts_sum : Somme de tous les montants de chaque lignes de commande
--dtb_reduction_ratio : Taux de réduction pour cette table
--dtb_amount_pay : Somme réellement due par la table : faisant intervenir le taux de réduction
--dtb_registration_date : Date d'enregistrement de la table dans la base de données
--dtb_print_date : Date d'impression de la table
--dtb_cashing_date : Date d'encaissement de la table
--dtb_reduction_ratio_changed : Permet de savoir si l'utilisateur a volontairement changé le taux de réduction
--dtb_takeaway : Permet de savoir si c'est une table à emporter ou bien sur place
CREATE TABLE t_dinner_table
(
  dtb_id serial,
  usr_id int8 NOT NULL DEFAULT 1,
  roo_id int8 NOT NULL DEFAULT 0,
  dtb_number varchar(5) NOT NULL,
  dtb_customers_number int8 NOT NULL DEFAULT 0,
  dtb_quantities_sum numeric(10,2) NOT NULL DEFAULT 0.00,
  dtb_amounts_sum numeric(10,2) NOT NULL DEFAULT 0.00,
  dtb_reduction_ratio numeric(10,2) NOT NULL DEFAULT 0.00,
  dtb_amount_pay numeric(10,2) NOT NULL DEFAULT 0.00,
  dtb_registration_date timestamp,
  dtb_print_date timestamp,  
  dtb_cashing_date date,  
  dtb_reduction_ratio_changed bool NOT NULL DEFAULT false,
  dtb_takeaway bool NOT NULL DEFAULT false,  
  CONSTRAINT dtb_id_pk PRIMARY KEY (dtb_id),
  CONSTRAINT usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT 
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------


--vtt_id : identifiant de cette table
--dtb_id : identifant de la table t_dinner_table, c'est une cl� �trang�re
--vat_id : identifiant de la table t_value_added_tax, c'est une cl� �trang�re
--vtt_amount : montant de la tva dont l'identifiant est vat_id, de la table dont l'identifiant est dtb_id
--vtt_value : valeur de la tva appliqu� au montant (vtt_amount) dont l'identifiant est vat_id, de la table dont l'identifiant est dtb_id
CREATE TABLE t_vat_table
(
  vtt_id serial,
  dtb_id int8 NOT NULL,
  vat_id int8 NOT NULL DEFAULT 1,
  vtt_amount numeric(10,2) NOT NULL DEFAULT 0.00,
  vtt_value numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT vtt_id_pk PRIMARY KEY (vtt_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT dtb_id_vat_id_uni UNIQUE (dtb_id,vat_id)
) WITHOUT OIDS;



--pdt_id : Code produit, clé étrangère de la table t_product. Ce champ avec le champ loc_id forment une clé primaire
--pdl_label : Désingation du produit
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser. Ce champ avec le champ pdt_id forment une clé primaire
CREATE TABLE t_product_language
(
  pdt_id varchar(5) NOT NULL,
  pdl_label varchar(60) NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',  
  CONSTRAINT pdt_id_loc_id_pk PRIMARY KEY (pdt_id, loc_id),
  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('#', 'PRODUIT OFFERT :');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('-', 'POURCENTAGE OFFERT SUR LES COMMANDES PRECEDENTES :');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('/', 'LIGNE DE COMMANDE DEFINIE PAR L''UTILISATEUR');

 
--orl_id : Identifiant de cette table
--dtb_id : Identifiant de la table t_dinner_table : c'est une clé étrangère de la table t_order_line
--prp_id : Identifiant de la table t_product_part : c'est une clé étrangère de la table t_order_line
--orl_quantity : Quantité commandée
--pdt_id : Code produit : c'est une clé étrangère de la table t_product
--orl_special_code_value : Ce champ permet de stocker la valeur spéciale du champ pdt_id lorsque ce-dernier est du type "-", "#"(voir aussi la table t_product_special_code), c'est aussi une cléf étrangère vers la table t_product
--orl_label : Désignation du produit : si le code produit de cette enregistrement existe dans la table t_product alors cette désignation sera la même que celle associée au code produit de la table t_product
--orl_unit_price : Prix du produit à l'unité
--orl_amount : Montant de la ligne de commande : c'est la quantité multipliée par le prix unitaire
CREATE TABLE t_order_line
(
  orl_id serial,
  dtb_id int8 NOT NULL,
  prp_id int8 NOT NULL DEFAULT 1,
  orl_quantity numeric(10,2) NOT NULL DEFAULT 0.00,
  pdt_id varchar(5) NOT NULL,
  orl_special_code_value varchar(5),
  orl_label varchar(50) NOT NULL,
  orl_unit_price numeric(10,2) NOT NULL DEFAULT 0.00,
  orl_amount numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT orl_id_pk PRIMARY KEY (orl_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT orl_special_code_value_fk FOREIGN KEY (orl_special_code_value) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------

--scp_id : Identifiant de cette table
--scp_update_date : Date de mise à jour de l'enregistrement
--pdt_id : Identifiant de la table t_product permettant de récupérer le code produit correspondant à un enregistrement de la table t_product : ce champ avec le champ cat_id forment une clé primaire
--scp_quantity : Quantité consommé à la date de mise à jour de l'enregistrement
CREATE TABLE t_stats_consumption_product
(
  scp_id serial,
  scp_updated_day int2 NOT NULL,
  scp_updated_month int2 NOT NULL,
  scp_updated_year int2 NOT NULL,
  pdt_id varchar(5) NOT NULL,
  scp_quantity numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT scp_id_pk PRIMARY KEY (scp_id)
--  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
--  CONSTRAINT pdt_id_uni UNIQUE (pdt_id)
) WITHOUT OIDS;


--ctr_id : Identifiant de cette table
--pdt_id : Identifiant de la table t_product permettant de récupérer le code produit correspondant à un enregistrement de la table t_product : ce champ avec le champ cat_id forment une clé primaire
--cat_id : Identifiant faisant référence à celui de la table t_category : ce champ et le champ pdt_id forment une clé primaire
--ctr_quantity : Quantité contenue dans une catégorie pour un produit donné
CREATE TABLE t_category_relation
(
  ctr_id serial,
  pdt_id varchar(5) NOT NULL,
  cat_id int8 NOT NULL,
  ctr_quantity numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT ctr_id_pk PRIMARY KEY (ctr_id),
  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cat_id_fk FOREIGN KEY (cat_id) REFERENCES t_category (cat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_id_cat_id_uni UNIQUE (pdt_id,cat_id)
) WITHOUT OIDS;


--csh_id : Identifiant de la table t_cashing représentant les encaissements
--dtb_id : Identifiant de la table t_dinner_table
--csh_cash : Paiement en espèce
--csh_ticket : Paiement en chèque restaurant
--csh_cheque : Paiement par chèque
--csh_card : Paiement par carte bancaire
--csh_unpaid : Impayé
CREATE TABLE t_cashing
(
  csh_id serial,
  dtb_id int8 NOT NULL,
  csh_cash numeric(10,2) NOT NULL DEFAULT 0.00,
  csh_ticket numeric(10,2) NOT NULL DEFAULT 0.00,
  csh_cheque numeric(10,2) NOT NULL DEFAULT 0.00,
  csh_card numeric(10,2) NOT NULL DEFAULT 0.00,
  csh_unpaid numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT csh_id_pk PRIMARY KEY (csh_id),
  CONSTRAINT dtb_id_uni UNIQUE (dtb_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------


--ttb_id : Identifiant de cette table
CREATE TABLE t_type_table
(
  ttb_id serial,
  CONSTRAINT ttb_id_pk PRIMARY KEY (ttb_id)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_type_table (ttb_id) VALUES (1);
INSERT INTO t_type_table (ttb_id) VALUES (2);


--ttl_id : Identifiant de cette table
--ttb_id : Identifiant de la table t_type_table : c'est une clé étrangère de cette table
--ttl_label : Type de table : Emporter, Sur place ...
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser
CREATE TABLE t_type_table_language
(
  ttl_id serial,
  ttb_id int8 NOT NULL,
  ttl_label varchar(50) NOT NULL,
  loc_id varchar(3) NOT NULL DEFAULT 'fr',
  CONSTRAINT ttl_id_pk PRIMARY KEY (ttl_id),
  CONSTRAINT ttb_id_fk FOREIGN KEY (ttb_id) REFERENCES t_type_table (ttb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------
INSERT INTO t_type_table_language (ttl_id, ttb_id, ttl_label, loc_id) VALUES (1, 1, 'SUR PLACE', 'fr');
INSERT INTO t_type_table_language (ttl_id, ttb_id, ttl_label, loc_id) VALUES (2, 2, 'EMPORTER', 'fr');
INSERT INTO t_type_table_language (ttl_id, ttb_id, ttl_label, loc_id) VALUES (3, 1, 'IN PLACE', 'en');
INSERT INTO t_type_table_language (ttl_id, ttb_id, ttl_label, loc_id) VALUES (4, 2, 'TAKE AWAY', 'en');


--drv_id : Identifiant de cette table
--drv_revenue_date : Date d'enregistrement de la recette journalière
--drv_print_date : Date d'impression de la recette rejournalière
--drv_closing_date : Date de clôture
--drv_cash : Paiement par espèce
--drv_ticket : Paiement par chèque restaurant
--drv_cheque : Paiement par chèque
--drv_card : Paiement par carte bancaire
--drv_unpaid : Impayé
--drv_amount : Montant total
--drv_takeaway : permet de connaitre le type de table : Emporter, Sur place ...
CREATE TABLE t_day_revenue
(
  drv_id serial,
  drv_revenue_date date NOT NULL,
  drv_print_date date,  
  drv_closing_date date,
  drv_cash numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_ticket numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_cheque numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_card numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_unpaid numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_amount numeric(10,2) NOT NULL DEFAULT 0.00,
  drv_takeaway bool NOT NULL DEFAULT false,
  CONSTRAINT drv_id_pk PRIMARY KEY (drv_id),
  CONSTRAINT drv_revenue_date_drv_takeaway_uni UNIQUE (drv_revenue_date,drv_takeaway)
) WITHOUT OIDS;
-----------------------------------------------------
-- DATA
-----------------------------------------------------

--vtr_id : identifiant de cette table
--drv_id : identifant de la table t_day_revenue, c'est une cl� �trang�re
--vat_id : identifiant de la table t_value_added_tax, c'est une cl� �trang�re
--vtr_amount : montant de la tva dont l'identifiant est vat_id, de la table dont l'identifiant est drv_id
--vtr_value : valeur de la tva appliqu� au montant (vtt_amount) dont l'identifiant est vat_id, de la table dont l'identifiant est drv_id
CREATE TABLE t_vat_revenue
(
  vtr_id serial,
  drv_id int8 NOT NULL,
  vat_id int8 NOT NULL DEFAULT 1,
  vtr_amount numeric(10,2) NOT NULL DEFAULT 0.00,
  vtr_value numeric(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT vtr_id_pk PRIMARY KEY (vtr_id),
  CONSTRAINT drv_id_fk FOREIGN KEY (drv_id) REFERENCES t_day_revenue (drv_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT drv_id_vat_id_uni UNIQUE (drv_id,vat_id)
) WITHOUT OIDS;





