--Select * from t_order_line left outer join t_product on 
--t_order_line.pdt_id = t_product.pdt_id 
--and t_product.loc_id='en'
--where 
--t_order_line.dtb_id = 3268

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
INSERT INTO t_category (cat_id) VALUES (1);
INSERT INTO t_category (cat_id) VALUES (2);
INSERT INTO t_category (cat_id) VALUES (3);
INSERT INTO t_category (cat_id) VALUES (4);
INSERT INTO t_category (cat_id) VALUES (5);
INSERT INTO t_category (cat_id) VALUES (6);
INSERT INTO t_category (cat_id) VALUES (7);
INSERT INTO t_category (cat_id) VALUES (8);
INSERT INTO t_category (cat_id) VALUES (9);
INSERT INTO t_category (cat_id) VALUES (10);
INSERT INTO t_category (cat_id) VALUES (11);
INSERT INTO t_category (cat_id) VALUES (12);


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
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (1, 'VAPEURS');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (2, 'CRUSTACES');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (3, 'VIANDE');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (4, 'RIZ');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (5, 'VOLAILLE');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (6, 'ALCOOL');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (7, 'VIN');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (8, 'CAFE THE INFUSION');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (9, 'EAU JUS SODA');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (10, 'BIERE');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (11, 'FRUITS ET LEGUME');
INSERT INTO t_category_language (cat_id, ctl_label) VALUES (12, 'GLACE');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (1, 'VAPEURS en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (2, 'CRUSTACES en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (3, 'VIANDE en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (4, 'RIZ en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (5, 'VOLAILLE en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (6, 'ALCOOL en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (7, 'VIN en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (8, 'CAFE THE INFUSION en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (9, 'EAU JUS SODA en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (10, 'BIERE en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (11, 'FRUITS ET LEGUME en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (12, 'GLACE en anglais', 'en');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (1, 'VAPEURS en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (2, 'CRUSTACES en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (3, 'VIANDE en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (4, 'RIZ en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (5, 'VOLAILLE en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (6, 'ALCOOL en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (7, 'VIN en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (8, 'CAFE THE INFUSION en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (9, 'EAU JUS SODA en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (10, 'BIERE en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (11, 'FRUITS ET LEGUME en chinois', 'zh');
INSERT INTO t_category_language (cat_id, ctl_label, loc_id) VALUES (12, 'GLACE en chinois', 'zh');


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
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (2, 'Appéritif', 'fr');
INSERT INTO t_product_part_language (prp_id, ppl_label, loc_id) VALUES (3, 'Entrée', 'fr');
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
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3233, 0, '1', 2, '4', '20', '0', '20', '2003-04-06 13:50:01', '0103-05-01 10:04:52', '2003-05-29 21:45:12', true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3218, 0, 'E', 0, '1', '119', '0', '119', '2003-03-25 21:26:18', '2003-03-25 21:27:26', '2003-03-25 21:27:38', true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3217, 0, 'E', 0, '5', '35', '5', '33.5', '2003-03-25 21:24:01', '2003-03-25 21:24:25', '2003-03-25 21:24:56', true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3219, 0, 'E1', 0, '1', '105', '0', '105', '2003-03-25 21:27:59', '2003-03-25 21:28:01', '2003-03-25 21:28:41', true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3223, 0, '2', 2, '2', '14', '0', '14', '2003-04-02 20:42:38', '2003-04-02 20:58:21', '2003-04-02 21:30:08', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3221, 0, '10', 2, '6', '32', '2', '31.5', '2003-03-30 18:21:59', '2003-04-02 21:24:42', '2003-04-02 21:29:48', true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3222, 0, '1', 2, '4', '16', '0', '16', '2003-04-02 20:42:30', '2003-04-02 21:23:29', '2003-04-02 21:29:43', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3224, 0, '3', 2, '1', '7', '0', '7', '2003-04-02 20:42:46', '2003-04-02 20:58:25', '2003-04-02 21:30:14', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3225, 0, '4', 2, '1', '7', '0', '7', '2003-04-02 20:42:54', '2003-04-02 20:42:56', '2003-04-02 21:30:19', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3226, 0, '5', 2, '2', '14', '0', '14', '2003-04-02 20:43:16', '2003-04-02 20:43:19', '2003-04-02 21:30:24', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3227, 0, '6', 2, '1', '4.5', '0', '4.5', '2003-04-02 20:43:29', '2003-04-02 20:43:30', '2003-04-02 21:30:30', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3228, 0, '7', 2, '1', '7', '0', '7', '2003-04-02 20:43:35', '2003-04-02 20:43:37', '2003-04-02 21:30:39', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3229, 0, '8', 2, '1', '7', '0', '7', '2003-04-02 20:43:42', '2003-04-02 20:43:44', '2003-04-02 21:30:44', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3230, 0, '11', 2, '1', '4', '0', '4', '2003-04-02 20:43:48', '2003-04-02 20:43:49', '2003-04-02 21:29:53', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3231, 0, '12', 2, '1', '4', '0', '4', '2003-04-02 20:43:54', '2003-04-02 20:43:55', '2003-04-02 21:29:58', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3232, 0, '13', 2, '1', '7', '0', '7', '2003-04-02 21:19:46', '2003-04-02 21:21:44', '2003-04-02 21:30:03', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3243, 0, 'E', 0, '2', '9', '0', '9', '2003-03-30 01:25:34', '2003-04-20 01:25:38', '2003-04-20 01:26:01', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3244, 0, 'E1', 0, '2', '13', '0', '13', '2003-03-30 01:26:15', '2003-04-20 01:26:18', '2003-04-20 01:26:28', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3245, 0, '11', 2, '2', '11.5', '0', '11.5', '2003-03-30 01:26:54', '2003-04-20 01:26:58', '2003-04-20 01:27:07', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3246, 0, '123', 1, '21', '99.5', '0', '99.5', '2003-04-20 21:57:36', '2003-04-20 22:48:25', '2003-04-21 00:22:19', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3249, 0, '5', 2, '1', '4.5', '0', '4.5', '2003-04-22 00:27:20', '2003-05-25 19:16:16', '2003-05-29 21:58:41', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3255, 0, '1', 2, '1', '7', '0', '7', '2003-05-29 18:13:51', '2003-05-29 21:59:21', '2003-05-29 21:59:36', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3256, 0, '1', 2, '1', '7', '0', '7', '2003-05-29 22:01:01', '2003-05-29 22:01:18', '2003-05-29 22:01:33', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3257, 0, '1', 2, '1', '7', '0', '7', '2003-05-29 22:01:01', '2003-05-29 22:03:34', '2003-05-29 22:03:46', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3258, 0, '1', 2, '1', '7', '0', '7', '2003-05-29 22:04:07', '2003-05-29 22:08:10', '2003-05-29 22:08:54', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3259, 0, '1', 4, '2', '14', '0', '14', '2003-05-29 22:09:26', '2003-06-01 17:37:08', '2003-06-01 17:37:29', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3261, 0, '1', 9, '5', '35', '0', '35', '2003-06-06 22:27:27', '2003-06-06 22:42:14', '2003-06-14 21:52:17', false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3263, 0, '3', 1, '10', '65', '8', '60', '2003-09-18 21:36:37', '2003-09-20 13:37:48', NULL, true);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3264, 0, '4', 2, '2', '11.5', '0', '11.5', '2003-09-18 21:43:51', NULL, NULL, false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3268, 0, '1', 2, '10', '30.5', '0', '30.5', '2003-10-07 21:49:55', NULL, NULL, false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3267, 0, '5', 2, '0', '0', '0', '0', '2003-10-06 22:07:04', NULL, NULL, false);
INSERT INTO t_dinner_table (dtb_id, roo_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed) VALUES (3270, 0, '7', 2, '2', '23.5', '0', '23.5', '2003-11-30 19:32:31', NULL, NULL, false);


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
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21043, 3233, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21044, 3233, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21024, 3219, '/', 'PLAT A EMPORTER', '1', '105', '105');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21023, 3218, '/', 'PLAT A EMPORTER', '1', '119', '119');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21022, 3217, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '5', '7', '35');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21027, 3221, 'CF', 'CAFE DECAFEINE', '2', '2', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21026, 3221, '9', 'SOUPE  PHNOM-PENH  SPECIALE', '1', '5.5', '5.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21028, 3222, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21029, 3223, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21030, 3224, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21031, 3225, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21032, 3226, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '2', '7', '14');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21033, 3227, '5', 'POTAGE  PIQUANT  PEKINOIS', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21034, 3228, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21035, 3229, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21036, 3230, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21037, 3231, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21038, 3222, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '2', '4', '8');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21039, 3221, '1', 'SOUPE AUX TROIS FRAICHEURS', '3', '7.5', '22.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21040, 3222, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21041, 3223, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21042, 3232, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21045, 3233, '4', 'POTAGE  ASPERGES  AU  CRABE', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21059, 3244, '19', 'BEIGNETS  DE  RAVIOLIS', '1', '5.5', '5.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21057, 3243, '11', 'NEM', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21058, 3243, '12', 'SALADE  AUX  CREVETTES', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21060, 3244, '18', 'BEIGNETS DE CREVETTES', '1', '7.5', '7.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21061, 3245, '11', 'NEM', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21062, 3245, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21075, 3249, '11', 'NEM', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21066, 3246, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21067, 3246, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '2', '4', '8');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21068, 3246, '4', 'POTAGE  ASPERGES  AU  CRABE', '3', '4.5', '13.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21069, 3246, '5', 'POTAGE  PIQUANT  PEKINOIS', '4', '4.5', '18');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21070, 3246, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '5', '7', '35');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21071, 3246, '7', 'SOUPE  AUX  NOUILLES  ET  AU  POULET', '6', '3', '18');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21073, 3233, '4', 'POTAGE  ASPERGES  AU  CRABE', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21090, 3255, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21091, 3256, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21094, 3259, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21092, 3257, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21093, 3258, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21095, 3259, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21098, 3261, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21102, 3261, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21103, 3261, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21107, 3261, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21109, 3261, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21119, 3264, '5', 'POTAGE  PIQUANT  PEKINOIS', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21120, 3264, '6', 'SOUPE  DE  NOUILLES  AUX  RAVIOLIS  CHINOIS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21112, 3263, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '8', '7', '56');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21115, 3263, '5', 'POTAGE  PIQUANT  PEKINOIS', '2', '4.5', '9');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21121, 3268, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '1', '7', '7');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21122, 3268, '3', 'POTAGE  AUX  MAIS ET JAMBONS', '1', '4', '4');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21123, 3268, '12', 'SALADE  AUX  CREVETTES', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21124, 3268, '12', 'SALADE  AUX  CREVETTES', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21125, 3268, '13', 'SALADE  AU  CRABE', '1', '5.5', '5.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21126, 3268, '12', 'SALADE  AUX  CREVETTES', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21129, 3268, '11', 'NEM', '1', '4.5', '4.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21131, 3270, '1', 'SOUPE AUX TROIS FRAICHEURS', '1', '10', '10');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21132, 3270, '2', 'POTAGE  AUX  AILERONS  DE  REQUINS', '3', '7', '21');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21133, 3270, '4', 'POTAGE  ASPERGES  AU  CRABE', '2', '4.5', '9');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_special_code_value, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21127, 3268, '-', '10', 'REDUCTION DE 10%', '1', '-3', '-3');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_special_code_value, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21128, 3268, '-', '1', 'REDUCTION DE 1%', '1', '-0.5', '-0.5');
INSERT INTO t_order_line (orl_id, dtb_id, pdt_id, orl_special_code_value, orl_label, orl_quantity, orl_unit_price, orl_amount) VALUES (21130, 3268, '-', '2', 'REDUCTION DE 2%', '1', '-0.5', '-0.5');


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
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (43, 3244, '0', '13', '0', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (42, 3243, '9', '0', '0', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (59, 3261, '0', '0', '0', '0', '35');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (27, 3217, '-6.5', '0', '40', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (28, 3218, '0', '0', '0', '119', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (29, 3219, '-2', '0', '107', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (30, 3222, '0', '0', '0', '0', '16');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (31, 3221, '0', '0', '0', '0', '31.5');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (32, 3230, '0', '0', '0', '0', '4');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (33, 3231, '0', '0', '0', '0', '4');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (34, 3232, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (35, 3223, '0', '0', '0', '0', '14');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (36, 3224, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (37, 3225, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (38, 3226, '0', '0', '0', '0', '14');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (39, 3227, '0', '0', '0', '0', '4.5');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (40, 3228, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (41, 3229, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (44, 3245, '11.5', '0', '0', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (45, 3246, '0', '99.5', '0', '0', '0');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (49, 3233, '0', '0', '0', '0', '20');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (52, 3249, '0', '0', '0', '0', '4.5');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (54, 3255, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (55, 3256, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (56, 3257, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (57, 3258, '0', '0', '0', '0', '7');
INSERT INTO t_cashing (csh_id, dtb_id, csh_cash, csh_ticket, csh_cheque, csh_card, csh_unpaid) VALUES (58, 3259, '0', '0', '0', '0', '14');


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
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (164, '2002-07-24', '2003-01-12', '0', '0', '170.5', '66', '236.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (165, '2002-07-24', '2003-01-12', '0', '0', '271', '722', '993', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (166, '2002-07-25', '2003-01-12', '23', '0', '0', '81.5', '98.45', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (167, '2002-07-25', '2003-01-12', '27', '0', '260', '646.5', '933.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (168, '2002-09-04', '2003-03-09', '0', '0', '90', '129', '219', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (169, '2002-09-04', '2003-03-09', '59.5', '13.5', '127.75', '361.5', '600.25', 'false', '38', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (170, '2002-07-26', '2003-01-12', '0', '0', '0', '57.5', '57.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (171, '2002-07-26', '2003-01-12', '269.5', '7', '276.5', '728.5', '1281.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (172, '2002-09-29', '2003-01-12', '0', '0', '0', '45.5', '45.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (173, '2002-09-29', '2003-01-12', '25', '14.08', '1008', '720.42', '1767.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (174, '2002-09-30', '2003-01-12', '0', '0', '17.3', '134.3', '151.6', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (175, '2002-09-30', '2003-01-12', '34', '0', '0', '545.5', '579.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (176, '2002-10-02', '2003-01-12', '8', '0', '84', '218.74', '310.74', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (177, '2002-10-02', '2003-01-12', '10', '13', '139', '319', '481', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (178, '2002-10-03', '2003-01-12', '0', '0', '80.5', '156', '236.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (179, '2002-10-03', '2003-01-12', '34.83', '7.17', '82', '373.5', '497.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (180, '2002-10-04', '2003-01-12', '0', '0', '0', '66.5', '66.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (181, '2002-10-04', '2003-01-12', '17.8', '122', '379.2', '687.25', '1206.25', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (182, '2002-10-05', '2003-01-12', '0', '6.86', '0', '102.64', '109.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (183, '2002-10-05', '2003-01-12', '21', '20.88', '770.52', '1028.6', '1841', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (184, '2002-10-06', '2003-01-12', '0', '0', '0', '263.5', '263.5', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (185, '2002-10-06', '2003-01-12', '13.1', '24.4', '354', '849.5', '993.5', 'false', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (186, '2002-10-07', '2003-05-05', '0', '20', '21', '0', '108.5', 'true', '26.5', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (187, '2002-10-07', '2003-05-05', '0', '11.5', '122.5', '139', '305.76', 'false', '32.76', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (188, '2002-12-02', '2003-01-12', '0', '0', '0', '0', '0', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (189, '2002-12-02', '2003-01-12', '0', '0', '0', '0', '59.5', 'false', '59.5', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (190, '2002-12-24', '2003-01-14', '0', '0', '0', '0', '36.5', 'true', '36.5', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (191, '2002-12-24', '2003-01-14', '0', '0', '0', '0', '18.5', 'false', '18.5', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (192, '2002-12-28', '2003-01-12', '0', '0', '0', '0', '22', 'true', '22', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (193, '2002-12-28', '2003-01-12', '0', '0', '0', '0', '45', 'false', '45', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (194, '2002-12-31', '2003-01-12', '0', '0', '0', '0', '0', 'true', '0', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (195, '2002-12-31', '2003-01-12', '6', '0', '0', '0', '33', 'false', '27', '2003-01-12');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (196, '2003-01-13', '2003-01-13', '0', '0', '0', '0', '0', 'true', '0', '2003-01-13');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (197, '2003-01-13', '2003-01-13', '0', '0', '0', '0', '4', 'false', '4', '2003-01-13');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (198, '2003-01-18', '2003-01-18', '0', '0', '0', '0', '0', 'true', '0', '2003-01-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (199, '2003-01-18', '2003-01-18', '14', '0', '0', '13', '27', 'false', '0', '2003-01-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (200, '2003-03-16', '2003-03-16', '57', '0', '78', '0', '135', 'true', '0', '2003-03-16');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (201, '2003-03-16', '2003-03-16', '0', '0', '0', '0', '0', 'false', '0', '2003-03-16');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (202, '2003-03-25', NULL, '0', '0', '0', '0', '0', 'true', '0', '2003-03-25');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (203, '2003-03-25', NULL, '0', '0', '0', '0', '0', 'false', '0', '2003-03-25');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (205, '2003-04-18', '2003-04-19', '7', '12', '0', '0', '19', 'false', '0', '2003-04-19');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (206, '2003-04-02', NULL, '0', '0', '0', '0', '0', 'true', '0', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (207, '2003-04-02', NULL, '0', '0', '0', '0', '0', 'false', '0', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (208, '2003-03-23', '2003-06-14', '0', '0', '107.5', '0', '97', 'true', '0', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (209, '2003-03-23', '2003-06-14', '0', '0', '5', '0', '6', 'false', '1', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (210, '2003-03-30', NULL, '0', '0', '0', '0', '0', 'true', '0', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (211, '2003-03-30', NULL, '0', '0', '0', '0', '0', 'false', '0', '2003-04-20');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (212, '2003-03-09', '2003-06-09', '0', '0', '0', '0', '74', 'true', '74', '2003-06-09');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (213, '2003-03-09', '2003-06-09', '48.5', '35', '0', '37', '181.5', 'false', '61', '2003-06-09');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (214, '2003-06-08', '2003-06-11', '0', '0', '0', '0', '0', 'true', '0', '2003-06-11');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (215, '2003-06-08', '2003-06-11', '0', '0', '0', '0', '17.5', 'false', '17.5', '2003-06-11');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (216, '2003-06-11', '2003-06-11', '0', '0', '0', '0', '0', 'true', '0', '2003-06-11');
INSERT INTO t_day_revenue (drv_id, drv_revenue_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque, drv_card, drv_amount, drv_takeaway, drv_unpaid, drv_print_date) VALUES (217, '2003-06-11', '2003-06-11', '0', '0', '0', '0', '10.5', 'false', '10.5', '2003-06-11');


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





