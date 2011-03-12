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
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (4, 'fr', 'Invite');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (1, 'zh', 'Admin en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (2, 'zh', 'Util en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (3, 'zh', 'Client en chinois');
INSERT INTO t_user_role_language (rol_id, loc_id, url_label) VALUES (4, 'zh', 'Invité en chinois');


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

INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('12', 4.50, 2);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('R', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('10', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('9', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('8', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('7', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('6', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('5', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('4', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('2', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('3', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('97', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('96', 11.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('95', 11.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('94', 11.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('93', 11.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('92', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('91', 12.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('90', 10.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('89', 10.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('88', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('87', 12.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('86', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('85', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('77', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('76', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('75', 5.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('74', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('73', 2.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('72', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('71', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('70', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('69', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('68', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('67', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('66', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('65', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('64', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('63', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('62', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('61', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('60', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('59', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('58', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('57', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('56', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('55', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('54', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('53', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('52', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('51', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('50', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('49', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('48', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('47', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('46', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('45', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('44', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('43', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('42', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('41', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('40', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('39', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('38', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('37', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('36', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('35', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('34', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('33', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('32', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('31', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('30', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('29', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('28', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('27', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('26', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('25', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('24', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('23', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('22', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('21', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('20', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('19', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('18', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('H', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('G', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('E', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('D', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('C', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('B', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('17', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('16', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('15', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('14', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('13', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('1', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('98', 43.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('99', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('101', 10.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('102', 11.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('103', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('104', 18.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('105', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('106', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('107', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('108', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('109', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('110', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('111', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('112', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('113', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('114', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('115', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('116', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('117', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('118', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('119', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('120', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('121', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('122', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('123', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('124', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('125', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('126', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('127', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('128', 12.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('129', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('130', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('131', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('132', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T1', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T2', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T3', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T4', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T5', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T6', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T7', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T8', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T9', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T10', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T11', 8.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T12', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T13', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T14', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T15', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T16', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T17', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T18', 43.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T19', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T20', 13.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T21', 13.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T22', 2.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('F', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('100', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('RI/', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('RI', 14.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T23', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CO', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A2', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A3', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A1', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GC', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CF', 2.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('B2', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('TV', 18.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('TV/', 10.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('DS', 2.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('1/4', 2.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BC', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CP', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CP/', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CR', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CR/', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CA', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('ST', 15.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('ST/', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BX', 14.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BX/', 8.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BJ', 14.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BJ/', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BO', 17.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BO/', 9.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('SC', 18.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('SC/', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BY', 18.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BY/', 10.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('MO', 19.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('MO/', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('MU', 12.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('MU/', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CH', 45.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CH/', 24.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BT', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BH', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('O', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('O/', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('DG1', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('DG', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('DG2', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A4', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M1', 11.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M2', 10.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M3', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M27', 27.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M20', 20.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('M14', 14.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GL', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GL2', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GL3', 6.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('B1', 2.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('B3', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GL1', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GP', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('P', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BE', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BF', 5.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BFM', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('PC', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('COU', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('AC', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('AG', 3.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('AS', 2.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('FN', 2.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('FF', 6.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('IR', 7.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('AP', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('BOI', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('11', 4.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CFO', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('T', 2.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('EM', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('A0', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('78', 9.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('S', 4.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('DGO', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('73B', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('SU', 1.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CA/', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('J', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('CV', 3.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('PO', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GLF', 7.50, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GLO', 0.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('GR', 1.00, 1);
INSERT INTO t_product (pdt_id, pdt_price, vat_id ) VALUES ('ABC', 15.00, 1);

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

INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('12', 'SALADE  AUX  CREVETTES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('R', 'REPAS COMPLET :');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('10', 'SOUPE  TONKINOISE  (PHO)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('9', 'SOUPE  PHNOM-PENH  SPECIALE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('8', 'SOUPE  AUX  VERMICELLES  CHINOIS  ET  AU  POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('7', 'SOUPE  AUX  NOUILLES  ET  AU  POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('5', 'POTAGE  PIQUANT  PEKINOIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('4', 'POTAGE  ASPERGES  AU  CRABE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('2', 'POTAGE  AUX  AILERONS  DE  REQUINS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('3', 'POTAGE  AUX  MAIS ET JAMBONS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('97', 'CAILLE  A  LA  SAUCE  CITRON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('96', 'GAMBAS  GRILLEES  AU  POIVRE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('95', 'NOUILLES  AUX  CREVETTES  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('94', 'CREVETTES  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('93', 'COQUILLES  SAINT-JACQUES  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('92', 'BOEUF  AU  SATE  SUR  PLAQUE  CHAUFFANTEO');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('91', 'FRUITS  DE  MER  A  LIMPERIALE  SUR  PLAQUE  CHAU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('90', 'GIGOT  AU  POIVRE  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('89', 'GIGOT  THAI SUR  PLAQUE  CHAUFFANTE Nouveauté');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('88', 'BOEUF  AU  POIVRE  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('87', 'FRUITS  DE  MER  AU  SATE  SUR  PLAQUE  CHAUFFANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('86', 'BOEUF  LOC-LACNOUVEAUT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('85', 'MOULES AUX  BASILICS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('77', 'BROCOLIS  SAUTES  NATURES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('76', 'RIZ SAUTE  AU  POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('75', 'NOUILLES  SAUTEES  NATURES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('74', 'RIZ  COCOTTE  DU  CHEF');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('73', 'RIZ  NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('72', 'RIZ  CANTONAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('71', 'NOUILLES  SAUTEES  AU  BOEUF');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('70', 'NOUILLES  SAUTEES  AU  PORC');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('69', 'NOUILLES  SAUTEES  AU  POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('68', 'LEGUMES  CHOP-SUEY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('67', 'GERMES  DE  SOJA  SAUTES  NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('66', 'CHAMPIGNONS  NOIRS  SAUTES  NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('65', 'BOEUF  AUX  CHAMPIGNONS  NOIRS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('64', 'BOEUF  AUX POUSSES DE BAMBOU  ET  AUX CHAMPIGNONS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('63', 'BOEUF  AUX  POUSSES  DE  BAMBOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('62', 'BOEUF  SAUCE  PIQUANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('61', 'BOEUF  SAUTE  AUX  BROCOLIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('60', 'BOEUF  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('59', 'BOEUF  AUX  GERMES  DE  SOJA');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('58', 'BOEUF AUX  POIVRONS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('57', 'BOEUF  AUX  OIGNONS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('56', 'BOEUF  AUX  TOMATES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('55', 'PORC  AUX  CHAMPIGNONS  NOIRS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('54', 'PORC  AUX POUSSES DE BAMBOU  ET  AUX CHAMPIGNONS P');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('53', 'PORC  A  LA  SAUCE  PIQUANTE  (EPICE)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('52', 'PORC  A  LA  SAUCE  AIGRE-DOUCE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('51', 'PORC  SAUTE  AUX  BROCOLIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('50', 'PORC  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('49', 'BOULETTES  DE  PORC  A  LA  SAUCE  PIQUANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('48', 'PORC  LAQUE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('47', 'FILET  DE  CANARD  LAQUE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('46', 'CANARD  AUX  ANANAS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('45', 'CANARD  AUX  CHAMPIGNONS  NOIRS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('44', 'CANARD AUX POUSSES DE BAMBOU ET AUX CHAMPIGNONS PA');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('43', 'CANARD  AUX  POUSSES  DE  BAMBOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('42', 'CANARD  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('41', 'POULET  CHOP-SUEY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('40', 'POULET  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('39', 'POULET  A  L?IMPERIALE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('38', 'POULET  AUX  AMANDES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('37', 'POULET  AUX  NOIX  DE  CAJOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('36', 'POULET  AUX  ANANAS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('35', 'POULET  AUX  CHAMPIGNONS  NOIRS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('34', 'POULET  AUX  POUSSES DE BAMBOU  ET  AUX CHAMPIGNON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('33', 'COQUILLES  SAINT-JACQUES  SAUTEES  NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('32', 'COQUILLES  SAINT-JACQUES  A  L?IMPERIALE  (PIQUANT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('31', 'BEIGNETS  DE  COQUILLES  SAINT-JACQUES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('30', 'BEIGNETS  DE  POISSON  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('29', 'BEIGNETS  DE  POISSON  A  LA  SAUCE  AIGRE-DOUCE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('28', 'BEIGNETS  DE  POISSON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('27', 'SEICHE  SAUTEE  AU  POIVRE  PARFUME');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('26', 'SEICHE  A  L?IMPERIALE  (PIQUANTE)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('25', 'SEICHE  SAUTE  AU  SATE  ET  AUX  BROCOLISNouvea');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('24', 'CREVETTES  A  L''IMPERIALE  (PIQUANTE)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('23', 'CREVETTES  SAUTEES  NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('22', 'CREVETTES  AUX  CREPES  DE RIZ');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('21', 'BEIGNETS DE CREVETTES A LA SAUCE AIGRE-DOUCE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('20', 'BEIGNETS  DE  CREVETTES  AU  CURRY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('19', 'BEIGNETS  DE  RAVIOLIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('18', 'BEIGNETS DE CREVETTES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('H', 'CREPES  DE  RIZ  AU  PORC  (BANH CUONG');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('G', 'BRIOCHES  AU  PORC  LAQUE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('E', 'BOUCHES AUX CREVETTES Nouveauté »');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('D', 'RAVIOLIS  DE  BOEUF  A  LA  VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('C', 'PETITS  CROISSANTS  A  LA  VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('B', 'RAVIOLIS  DE PORC  A  LA  VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A', 'RAVIOLIS  DE  CREVETTES  A  LA VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('17', 'OMELETTE  AU POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('16', 'OMELETTE  AU  CRABE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('15', 'OMELETTE  AUX  CREVETTES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('14', 'SALADE  AU  POULET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('13', 'SALADE  AU  CRABE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('1', 'SOUPE AUX TROIS FRAICHEURS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('98', 'FONDUE  VIETNAMIENNE  (POUR 2 PERSONNES)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('99', 'BOULETTES  DE  CREVETTES  DU  CHEF');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('101', 'HORS D OEUVRE SPECIAL CHAUD(POUR 2 PERSONNES)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('102', 'GAMBAS  SAUTEES  SPECIALITE  DU  CHEF  (PIQUANTE)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('103', 'CREVETTES  AUX  QUEUES  DE  PHENIX');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('104', 'HORS D''OEUVRE SPECIAL  CHAUD(2 PERSONNES)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('105', 'CREVETTES  AUX  GINGEMBRE  ET  CIBOULETTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('106', 'GIGOT  SAUTE AUX GIMGEMBRES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('107', 'MARMITE DE FRUIT DE MER');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('108', 'LES  TROIS  MERVEILLES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('109', 'NID  DE  CRUSTACES  VARIES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('110', 'POULET  FRIT  A  LA  CANTONAISE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('111', 'BEIGNETS  DE  CUISSES  DE  GRENOUILLES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('112', 'CUISSES  DE  GRENOUILLES  A  L''IMPERIALE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('113', 'CUISSES  DE  GRENOUILLES  SAUTEES  SPECIALITE  DU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('114', 'CUISSES  DE  GRENOUILLES  A  LA  SAUCE  PIQUANTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('115', 'CUISSES  DE  GRENOUILLES  SAUTEES  AU  POIVRE  PAR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('116', 'CREVETTES  SAUTEES  AUX  BROCOLIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('117', 'FRUITS  DE  MER  SAUTES  AUX  BROCOLIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('118', 'POULET  GRILLE  AU  POIVRE  DE  CAYENNE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('119', 'FILETS  DE  CABILLAUD  A  LA  VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('120', 'TRAVERS  DE  PORC  PEKINOIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('121', 'BEIGNET  DE  POULET  AU  CITRON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('122', 'FILET  DE  CABILLAUD  A  LA  CANTONAISE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('123', 'PORC  AU  CARAMEL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('124', 'ROULEAU  DE  PRINTEMPS  (GOI-CUON)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('125', 'BOEUF  AUX  VERMICELLES  DE  RIZ  (BO-BONG');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('126', 'BROCHETTES  DE  BOEUF  GRILLE  (BO-LUI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('127', 'BOULETTES  DE  PORC  GRILLE  (NEM-NUONG)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('128', 'CREVETTES  GRILLEES  (TOM-NUONG)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('129', 'TRAVERS  DE  PORC  GRILLES  A  LA  CITRONELLE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('130', 'BOEUF  SAUTE  AU  GINGEMBRE  (CIBOULETTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('131', 'FILETS  DE  POISSON  SAUTES  AU  GINGEMBRE  (CIBOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('132', 'CANARD  AU  CARAMEL  (CITRON)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T1', 'SOUPE  AUX  CREVETTES  PIMENTEES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T2', 'SALADE  DE  B?UF  MACERE  PIMENTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T3', 'SALADE  THAI  AUX  CREVETTES  PIMENTEES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T4', 'POTAGE  AUX  CREVETTES  AU  LAIT  DE  COCO  EPICE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T5', 'POTAGE  DE  COQUILLES  SAINT-JACQUES  AU  LAIT  DE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T6', 'PATES  IMPERIAUX  THAI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T7', 'TRAVERS  DE  PORC  AU  POIVRE  PARFUME');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T8', 'FRUITS  DE  MER  SAUTES  AU  BASILIC  ET  PIMENT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T9', 'CREVETTES  SAUTEES  AU  BASILIC  ET  PIMENT  FRAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T10', 'COQUILLES  SAINT-JACQUES  SAUTEES  AU  BASILIC  ET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T11', 'CUISSES  DE  GRENOUILLES  SAUTEES  AU  BASILIC  ET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T12', 'SEICHE  SAUTEE  AU  BASILIC  ET  PIMENT  FRAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T13', 'CANARD  SAUTE  AU  BASILIC  ET  PIMENT  FRAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T14', 'POULET  SAUTE  AU  BASILIC  ET  PIMENT  FRAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T15', 'BOEUF SAUTE AU BASILIC ET PIMENT FRAIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T16', 'BROCHETTES  DE  GIGOT  GRILLE  AUX  EPICES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T17', 'BROCHETTES  DE  POULET  GRILLE  AUX  EPICES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T18', 'FONDUE  THAILANDAISE  (POUR 2 PERSONNES)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T19', 'POISSON  A  LA  SAUCE  PATTAYA');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T20', 'SOLE  ENTIERE  A  LA  VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T21', 'SOLE  FRITE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T22', 'RIZ  GLUANT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('F', 'ASSORTIMENT A LA VAPEUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('100', 'CRABE FARCI SAIGONNAIS (A LA VAPEUR)');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('RI/', '1/2 RIESLING');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('RI', 'RIESLING');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T23', 'HA MOK');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CO', 'COCA-COLA, ORANGINA TONIC JUS DE FRUIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A2', 'COKTAIL MAISON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A3', 'WHISKY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A1', 'PORTO, MARTINI, SUZE, RICARD, PASTIS, KIR, AMERICA');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GC', 'GATEAU COCO');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CF', 'CAFE DECAFEINE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('B2', '2 BOULES DE GLACE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('TV', 'TAVEL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('TV/', '1/2 TAVEL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('DS', 'FRUITS AU SIROP(LYCHEES, LONGANES, MANGUES, FRAISE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('1/4', '1/4 VIN DE TABLE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BC', 'BIERE CHINOISE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CP', 'COTE DE PROVENCE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CP/', '1/2 COTE DE PROVENCE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CR', 'COTE DU RHONE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CR/', '1/2 COTE DU RHONE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CA', 'CABERNET D ANJOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('ST', 'SAINT TROPEZ');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('ST/', '1/2 ST TROPEZ');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BX', 'BORDEAUX');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BX/', '1/2 BORDEAUX');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BJ', 'BEAUJOLAIS VILLAGES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BJ/', '1/2 BEAUJOLAIS VILLAGES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BO', 'BOURGOGNE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BO/', '1/2 BOURGOGNE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('SC', 'SAUMUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('SC/', '1/2 SAUMUR');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BY', 'BROUILLY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BY/', '1/2 BROUILLY');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('MO', 'MOULIN A VENT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('MO/', '1/2 MOULIN A VENT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('MU', 'MUSCADET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('MU/', '1/2 MUSCADET');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CH', 'CHAMPAGNE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CH/', '1/2 CHAMPAGNE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BT', 'BIERE THAI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BH', 'CALSBERG HEINEKEN');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('O', 'VITTEL 1L BADOIT 1L');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('O/', '1/2 VITTEL 1/2 BADOIT PERRIER');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('DG1', 'ALCOOL DE RIZ');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('DG', 'COINTREAU GRAND MARNIER CALVADOS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('DG2', 'COGNAC');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A4', 'TEQUILA VOLDKA SOHO MALIBU GIN GET 27');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M1', 'MENU MIDI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M2', 'MENU MIDI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M3', 'MENU MIDI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M27', 'MENU DRAGON');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M20', 'MENU OISEAU PARADIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('M14', 'MENU A 14.5');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GL', 'MYSTERE MOUSCOCO TRUFFE.....');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GL2', 'GLACE LIEGEOIS');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GL3', 'COLONEL BAN SPLIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('B1', 'BOULE DE GLACE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('B3', 'SORBET DE GLACE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GL1', 'SOUFFLE MAN COCO ANANAS GIVRE....');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GP', 'PROFITEROL MANGUE GIVRE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('P', 'PERLE DE COCO');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BE', 'BEIGNET DE FRUIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BF', 'BEIGNET FLAMBE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BFM', 'BEIGNET FLAMBE MKL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('PC', 'BEIGNET AU CARAMEL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('COU', 'COUPE DE FRUIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('AC', 'ASSORTIMENT CONFIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('AG', 'ANANAS GIN KIRSH');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('AS', 'ANANAS SIROP');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('FN', 'FRUIT NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('FF', 'MANGUE NATURE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('IR', 'IRISH COOFEE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('AP', 'APPERITIF OFFERT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('BOI', 'BOISSON OFFERT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('11', 'NEM');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CFO', 'CAFE OFFERT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('T', 'THE,VERVEINE,NOISETTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('EM', 'PLAT A EMPORTER');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('A0', 'COCKTAIL SANS ALCOOL');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('78', 'NOUILLE AUX CREVETTES');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('S', 'BIERE THAI');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('DGO', 'DIGESTIF OFFERT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('73B', 'RIZ LOC-LAC');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('SU', 'SUPPLEMENT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CA/', '1/2CABERNET D ANJOU');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('J', 'COCA-COLA, ORANGINA TONIC JUS DE FRUIT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('CV', 'CHOCOLAT OU CAFE VIENNOIS, CAPUCCINO');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('PO', 'PLAT OFFERT');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GLF', 'GLACE FLAMBE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GLO', 'GLACE OFFERTE');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('GR', 'SIROP');
INSERT INTO t_product_language (pdt_id, pdl_label) VALUES ('ABC', 'VAPEUR ABC');
INSERT INTO t_product_language (pdt_id, pdl_label, loc_id) VALUES ('11', 'ENGLISH NEM', 'en');
INSERT INTO t_product_language (pdt_id, pdl_label, loc_id) VALUES ('12', 'ENGLISH SALADE AUX CREVETTES', 'en');
INSERT INTO t_product_language (pdt_id, pdl_label, loc_id) VALUES ('13', 'ENGLISH SALADE AU CRABE', 'en');

 
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





