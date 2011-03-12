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
DROP TABLE t_bill;
DROP TABLE t_credit_table;
DROP TABLE t_credit;
DROP TABLE t_dinner_table;
DROP TABLE t_product;
DROP TABLE t_product_part_language;
DROP TABLE t_product_part;
DROP TABLE t_psc_language;
DROP TABLE t_product_special_code;
DROP TABLE t_value_added_tax;
DROP TABLE t_category_language;
DROP TABLE t_category;
DROP TABLE t_user_locale;
DROP TABLE t_user_authentication;
DROP TABLE t_user_restaurant;
DROP TABLE t_user;
DROP TABLE t_user_role_language;
DROP TABLE t_user_role;
DROP TABLE t_print_info_plus_language;
DROP TABLE t_print_info_plus;
DROP TABLE t_restaurant;
DROP TABLE t_locale_language;
DROP TABLE t_locale;


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
--res_prefixes_takeaway_name
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
  res_vat_by_takeaway boolean DEFAULT true NOT NULL,
  res_takeaway_basic_reduction numeric(12,4) NOT NULL,
  res_takeaway_min_amount_reduction numeric(12,4) NOT NULL,
  res_prefixes_takeaway_name varchar(100),
  res_specific_round int8 NOT NULL,
  res_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT res_id_pk PRIMARY KEY (res_id),
  CONSTRAINT res_reference_uni UNIQUE (res_reference)
) WITHOUT OIDS;

--loc_id : Identifiant de cette table
--loc_language : code language voir java.util.Locale.language
CREATE TABLE t_locale
(
  loc_id serial,
  loc_language varchar(3) NOT NULL,
  loc_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT loc_id_pk PRIMARY KEY (loc_id),
  CONSTRAINT loc_language_uni UNIQUE (loc_language)
) WITHOUT OIDS;

CREATE TABLE t_print_info_plus
(
  pip_id serial,
  res_id int8 NOT NULL,
  pip_label_code varchar(20) NOT NULL,
  pip_order int8 NOT NULL,
  pip_alignment varchar(20) NOT NULL,
  pip_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT pip_id_pk PRIMARY KEY (pip_id),
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pip_label_code_uni UNIQUE (pip_label_code)
) WITHOUT OIDS;

CREATE TABLE t_print_info_plus_language
(
  pil_id serial,
  pip_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  pil_label varchar(200) NOT NULL,
  pil_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT pil_id_pk PRIMARY KEY (pil_id),
  CONSTRAINT rol_id_fk FOREIGN KEY (pip_id) REFERENCES t_print_info_plus (pip_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;


--loc_id : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id_language forment la clé primaire de cette table.
--loc_id_language : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
--lol_label : Ce champ donne la description du champ loc_id suivant le champ loc_id_language. Par example : si loc_id = fr et loc_id_language = fr lol_label = Français ou bien loc_id = fr et loc_id_language = zh lol_label = Chinois.
CREATE TABLE t_locale_language
(
  lol_id serial,
  loc_id int8 NOT NULL,
  loc_id_language int8 NOT NULL,
  lol_label varchar(200) NOT NULL,
  lol_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT lol_id_pk PRIMARY KEY (lol_id),
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_language_fk FOREIGN KEY (loc_id_language) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;

--rol_id : Idetntifiant de cette table
--rol_label_code : R�le de l'utilisateur
CREATE TABLE t_user_role
(
  rol_id serial,
  rol_label_code varchar(20) NOT NULL,  
  rol_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT rol_id_pk PRIMARY KEY (rol_id),
  CONSTRAINT rol_label_code_uni UNIQUE (rol_label_code)
) WITHOUT OIDS;

--rol_id : Idetntifiant de la table t_user_role : c'est une clé étrangère de cette table
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser ou bien celle de l'utilisateur connecté
--url_label : Rôle de l'utilisateur dans une langue choisie
CREATE TABLE t_user_role_language
(
  url_id serial,
  rol_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  url_label varchar(200) NOT NULL,
  url_deleted boolean NOT NULL DEFAULT false,  
  CONSTRAINT url_id_pk PRIMARY KEY (url_id),
  CONSTRAINT rol_id_fk FOREIGN KEY (rol_id) REFERENCES t_user_role (rol_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;


--usr_id : Identifiant de cette table
--usr_name : Nom de l'utilisateur
--usr_forename1 : Premier prénom de l'utilisateur
--usr_forename2 : Deuxième prénom de l'utilisateur
--usr_birthdate : Date de naissance
--usr_sex : Sexe de l'utilisateur : 0 homme, 1 femme
--usr_picture : Photo de l'utilisateur
CREATE TABLE t_user
(
  usr_id serial,
  usr_name varchar(20) NOT NULL,  
  usr_forename1 varchar(20) NOT NULL,
  usr_forename2 varchar(20),
  usr_birthdate date NOT NULL,
  usr_sex int2 NOT NULL DEFAULT 0,
  usr_picture bytea,
  usr_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT usr_id_pk PRIMARY KEY (usr_id)
) WITHOUT OIDS;

-- Cette table permet de faire la relation entre la table t_user e la table t_restaurant: un utilisateur poss�de ou travaille sur un ou plusieurs restaurant 
--urt_id : identifiant de cette table 
--usr_id : cl�f �trang�re de la table t_user 
--res_id : cl�f �trang�re de la table t_restaurant
--urt_deleted boolean : supprimer ou pas ?
CREATE TABLE t_user_restaurant
(
  urt_id serial,
  usr_id int8 NOT NULL DEFAULT 0,
  res_id int8 NOT NULL DEFAULT 0,
  urt_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT urt_id_pk PRIMARY KEY (urt_id),
  CONSTRAINT usr_id_res_id_uni UNIQUE (usr_id, res_id),
  CONSTRAINT usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT,  
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;


--aut_id : Identifiant de cette table
--loc_id : Identifiant de la table t_locale permettant de savoir dans quel language l'impression doit se faire
--usr_id : Identifiant de la table t_user : clé étrangère de cette table
--res_id : Identifiant de la table t_restaurant : clé étrangère de cette table : le restaurant que l'utilisateur connect� voit 
--rol_id : Identifiant de la table t_user_role : clé étrangère de cette table
--aut_login : Identifiant de l'utilisateur
--aut_password : Mot de passe de l'utilisateur
--aut_level_pass1 : Niveau de mot de passe 1
--aut_level_pass2 : Niveau de mot de passe 2
--aut_level_pass3 : Niveau de mot de passe 3
CREATE TABLE t_user_authentication
(
  aut_id serial,
  loc_id int8,
  usr_id int8 NOT NULL DEFAULT 0,
  res_id int8 NOT NULL DEFAULT 0,
  rol_id int8 NOT NULL DEFAULT 0,
  aut_login varchar(20) NOT NULL,  
  aut_password varchar(20) NOT NULL,
  aut_level_pass1 varchar(20),
  aut_level_pass2 varchar(20),
  aut_level_pass3 varchar(20),
  aut_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT aut_id_pk PRIMARY KEY (aut_id),
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rol_id_fk FOREIGN KEY (rol_id) REFERENCES t_user_role (rol_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT aut_login_uni UNIQUE (aut_login)
) WITHOUT OIDS;


--ulo_id : Identifiant de cette table
--aut_id : Identifiant de la table t_user_authentication : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
--loc_id : Identifiant de la table t_locale : clé étrangère de cette table. Ce champ et le champ loc_id forment la clé primaire de cette table.
CREATE TABLE t_user_locale
(
  ulo_id serial,
  aut_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  ulo_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT ulo_id_pk PRIMARY KEY (ulo_id),
  CONSTRAINT aut_id_loc_id_uni UNIQUE (aut_id, loc_id),
  CONSTRAINT aut_id_fk FOREIGN KEY (aut_id) REFERENCES t_user_authentication (aut_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;

--cat_id : Identifiant de cette table
--cat_label_code : code de la cat�gorie
CREATE TABLE t_category
(
  cat_id serial,
  cat_label_code varchar(20) NOT NULL,  
  cat_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT cat_id_pk PRIMARY KEY (cat_id),
  CONSTRAINT cat_label_code_uni UNIQUE (cat_label_code)
) WITHOUT OIDS;

--cat_id : Identifiant de la table t_category
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser ou bien celle de l'utilisateur connecté
--ctl_label : Libellé de la catégorie
CREATE TABLE t_category_language
(
  ctl_id serial,
  cat_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  ctl_label varchar(200) NOT NULL,
  ctl_deleted boolean NOT NULL DEFAULT false,  
  CONSTRAINT ctl_id_pk PRIMARY KEY (ctl_id),
  CONSTRAINT cat_id_fk FOREIGN KEY (cat_id) REFERENCES t_category (cat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;


--vat_id : Identifiant de cette table
--vat_value : Valeur en pourcentage de la tva : c'est aussi une clé primaire
CREATE TABLE t_value_added_tax
(
  vat_id serial,
  res_id int8 NOT NULL DEFAULT 0,
  vat_value numeric(12,4) NOT NULL DEFAULT 19.60,
  vat_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT vat_id_pk PRIMARY KEY (vat_id),
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_value_uni UNIQUE (vat_value)
) WITHOUT OIDS;

--psc_id : Identifiant de cette table
--psc_code : Code applicatif
--res_id : Restaurant
CREATE TABLE t_product_special_code
(
  psc_id serial,
  res_id int8 NOT NULL DEFAULT 0,
  psc_short_code varchar(1) NOT NULL,
  psc_long_code varchar(20) NOT NULL,
  psc_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT psc_id_pk PRIMARY KEY (psc_id),
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT psc_restaurant_short_code_uni UNIQUE (res_id, psc_short_code),
  CONSTRAINT psc_restaurant_long_code_uni UNIQUE (res_id, psc_long_code),
  CONSTRAINT psc_restaurant_short_long_code_uni UNIQUE (res_id, psc_short_code, psc_long_code)
) WITHOUT OIDS;

--psc_id : Identifiant de la table t_product_special_code : clef etrangere, ce champ avec le champ loc_id forment une clef primaire.
--pcl_label : Description : # == OFFERT ...
--loc_id : Champ de la table t_locale permettant de connaitre la langue du libell� afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser. Ce champ avec le champ orp_id forment une clé primaire
CREATE TABLE t_psc_language
(
  pcl_id serial,
  psc_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  pcl_label varchar(200) NOT NULL,
  pcl_deleted boolean NOT NULL DEFAULT false,  
  CONSTRAINT pcl_id_pk PRIMARY KEY (pcl_id),
  CONSTRAINT psc_id_fk FOREIGN KEY (psc_id) REFERENCES t_product_special_code (psc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;

--prp_id : Identifiant de cette table
--prp_label_code : Code applicatif
CREATE TABLE t_product_part
(
  prp_id serial,
  prp_label_code varchar(20) NOT NULL,
  prp_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT prp_id_pk PRIMARY KEY (prp_id),
  CONSTRAINT prp_label_code_uni UNIQUE (prp_label_code)
) WITHOUT OIDS;

--pdt_id : Identifiant de cette table
--pdt_code : Code produit, identifiant de cette table
--pdt_price : Prix du produit
--pdt_colorRGB : Couleur du produit(de la forme FFFFFF) : permet par exemple d'afficher une ligne de commande dans la couleur choisie, si cette valeur est nulle on garde la couleur par d�faut du css
--vat_id : Identifiant de la table t_value_added_tax permettant de récupérer la valeur en pourcentage de la TVA du produit : ceci représente la valeur du champ vat_value de la table t_value_added_tax : c'est une clé étrangère de la table t_value_added_tax
--res_id : Identifiant de la table t_restaurant permettant de savoir � quel restaurant appartient ce produit : c'est une clef etrangere de la table t_restaurant
CREATE TABLE t_product
(
  pdt_id serial,
  pdt_code varchar(5) NOT NULL,
  pdt_price numeric(12,4) NOT NULL DEFAULT 0.00,
  pdt_colorRGB varchar(6),
  vat_id int8 NOT NULL DEFAULT 1,
  res_id int8 NOT NULL DEFAULT 0,
  pdt_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT pdt_id_pk PRIMARY KEY (pdt_id),
  CONSTRAINT vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_code_uni UNIQUE (pdt_code)
) WITHOUT OIDS;

--prp_id : Identifiant de la table t_product_part : clé étrangère, ce champ avec le champ loc_id forment une clé primaire.
--ppl_label : Description de la partie auquelle appartient une ligne de commande : par exemple, Appéritif, Entrée, Plat de résistance, Dessert, Digestif, Divers ...  
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser. Ce champ avec le champ orp_id forment une clé primaire
CREATE TABLE t_product_part_language
(
  ppl_id serial,
  prp_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  ppl_label varchar(200) NOT NULL,
  ppl_deleted boolean NOT NULL DEFAULT false,  
  CONSTRAINT ppl_id_pk PRIMARY KEY (ppl_id),
  CONSTRAINT prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;


--dtb_id : Identifiant de la table
--aut_id : Identifiant de la table t_user_authentication : cl� �trang�re de cette table.
--roo_id : Identifant de la salle dans laquelle se trouve cette table
--dtb_number : Numéro de la table
--dtb_customers_number : Nombre de personnes occupant la table
--dtb_scp_quantitys_sum : Somme de toutes les quantités commandées par la table
--dtb_accounts_sum : Somme de tous les montants de chaque lignes de commande
--dtb_reduction_ratio : Taux de réduction pour cette table
--dtb_amount_pay : Somme réellement due par la table : faisant intervenir le taux de réduction
--dtb_registration_date : Date d'enregistrement de la table dans la base de données
--dtb_printing_date : Date d'impression de la table
--dtb_cashing_date : Date d'encaissement de la table
--dtb_reduction_ratio_changed : Permet de savoir si l'utilisateur a volontairement changé le taux de réduction
--dtb_takeaway : Permet de savoir si c'est une table à emporter ou bien sur place
CREATE TABLE t_dinner_table
(
  dtb_id serial,
  aut_id int8 NOT NULL DEFAULT 1,
  roo_id int8 NOT NULL DEFAULT 0,
  dtb_number varchar(5) NOT NULL,
  dtb_customers_number int8 NOT NULL DEFAULT 0,
  dtb_quantities_sum numeric(12,4) DEFAULT 0.00,
  dtb_amounts_sum numeric(12,4) DEFAULT 0.00,
  dtb_reduction_ratio numeric(12,4) DEFAULT 0.00,
  dtb_amount_pay numeric(12,4) DEFAULT 0.00,
  dtb_registration_date timestamp,
  dtb_printing_date timestamp,  
  dtb_cashing_date date,  
  dtb_reduction_ratio_changed boolean NOT NULL DEFAULT false,
  dtb_takeaway boolean NOT NULL DEFAULT false,  
  dtb_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT dtb_id_pk PRIMARY KEY (dtb_id),
  CONSTRAINT aut_id_fk FOREIGN KEY (aut_id) REFERENCES t_user_authentication (aut_id) ON UPDATE RESTRICT ON DELETE RESTRICT 
) WITHOUT OIDS;


--bil_id : Identifiant de la table
--dtb_id : Identifiant de la table t_dinner_table 
--bil_reference : Reference de la facture
--bil_order : Ordre/Utiliser pour Numero de table
--bil_meal_number : Nombre de personne
--bil_amount : Montant partiel
--bil_printed: Permet de savoir si on veut imprimer la facture
CREATE TABLE t_bill
(
  bil_id serial,
  dtb_id int8 NOT NULL,
  bil_reference varchar(40) NOT NULL,
  bil_order int8,
  bil_meal_number int8,
  bil_amount numeric(12,4) NOT NULL,
  bil_printed boolean NOT NULL DEFAULT false,
  bil_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT bil_id_pk PRIMARY KEY (bil_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT bil_reference_uni UNIQUE (bil_reference)
) WITHOUT OIDS;

--cre_id : Identifiant de la table
--cre_reference : Reference de la facture
--cre_amount : Montant de l'avoir
CREATE TABLE t_credit
(
  cre_id serial,
  cre_reference varchar(16) NOT NULL,
  cre_amount numeric(12,4) NOT NULL,
  cre_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT cre_id_pk PRIMARY KEY (cre_id),
  CONSTRAINT cre_reference_uni UNIQUE (cre_reference)
) WITHOUT OIDS;

--crt_id : Identifiant de la table
--cre_id : Identifiant de la table t_credit
--dtb_id : Identifiant de la table t_dinner_table 
CREATE TABLE t_credit_table
(
  crt_id serial,
  dtb_id int8 NOT NULL,
  cre_id int8 NOT NULL,
  crt_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT crt_id_pk PRIMARY KEY (crt_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cre_id_fk FOREIGN KEY (cre_id) REFERENCES t_credit (cre_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cre_id_uni UNIQUE (cre_id)
) WITHOUT OIDS;

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
  vtt_amount numeric(12,4) NOT NULL DEFAULT 0.00,
  vtt_value numeric(12,4) NOT NULL DEFAULT 0.00,
  vtt_deleted boolean NOT NULL DEFAULT false,
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
  pdl_id serial,
  pdt_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  pdl_label varchar(200) NOT NULL,
  pdl_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT pdl_id_pk PRIMARY KEY (pdl_id),
  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_id_loc_id_uni UNIQUE (pdt_id, loc_id)
) WITHOUT OIDS;  

--orl_id : Identifiant de cette table
--dtb_id : Identifiant de la table t_dinner_table : c'est une clé étrangère de la table t_order_line
--prp_id : Identifiant de la table t_product_part : c'est une clé étrangère de la table t_order_line
--orl_quantity : Quantite commande
--pdt_id : Code produit : c'est une clef etrangere de la table t_product
--orl_code : code venant de la table t_product ou t_product_special_code : ce-dernier peut etre du type "-", "#"(voir aussi la table t_product_special_code)
--orl_label : Désignation du produit : si le code produit de cette enregistrement existe dans la table t_product alors cette désignation sera la même que celle associée au code produit de la table t_product
--orl_unit_price : Prix du produit à l'unité
--orl_amount : Montant de la ligne de commande : c'est la quantité multipliée par le prix unitaire
CREATE TABLE t_order_line
(
  orl_id serial,
  dtb_id int8 NOT NULL,
  prp_id int8,
  orl_quantity numeric(12,4) NOT NULL DEFAULT 0.00,
  pdt_id int8,
  psc_id int8,
  orl_code varchar(5) NOT NULL,
  orl_label varchar(50) NOT NULL,
  orl_unit_price numeric(12,4) NOT NULL DEFAULT 0.00,
  orl_amount numeric(12,4) NOT NULL DEFAULT 0.00,
  orl_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT orl_id_pk PRIMARY KEY (orl_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT psc_id_fk FOREIGN KEY (psc_id) REFERENCES t_product_special_code (psc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;

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
  pdt_code varchar(5) NOT NULL,
  scp_quantity numeric(12,4) NOT NULL DEFAULT 0.00,
  scp_deleted boolean NOT NULL DEFAULT false,
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
  pdt_id int8 NOT NULL,
  cat_id int8 NOT NULL,
  ctr_quantity numeric(12,4) NOT NULL DEFAULT 0.00,
  ctr_deleted boolean NOT NULL DEFAULT false,
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
  csh_cash numeric(12,4) NOT NULL DEFAULT 0.00,
  csh_ticket numeric(12,4) NOT NULL DEFAULT 0.00,
  csh_cheque numeric(12,4) NOT NULL DEFAULT 0.00,
  csh_card numeric(12,4) NOT NULL DEFAULT 0.00,
  csh_unpaid numeric(12,4) NOT NULL DEFAULT 0.00,
  csh_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT csh_id_pk PRIMARY KEY (csh_id),
  CONSTRAINT dtb_id_uni UNIQUE (dtb_id),
  CONSTRAINT dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT OIDS;

--ttb_id : Identifiant de cette table
CREATE TABLE t_type_table
(
  ttb_id serial,
  ttb_label_code varchar(20) NOT NULL,
  ttb_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT ttb_id_pk PRIMARY KEY (ttb_id),
  CONSTRAINT ttb_label_code_uni UNIQUE (ttb_label_code)
) WITHOUT OIDS;


--ttl_id : Identifiant de cette table
--ttb_id : Identifiant de la table t_type_table : c'est une clé étrangère de cette table
--ttl_label : Type de table : Emporter, Sur place ...
--loc_id : Champ de la table t_locale permettant de connaître la langue du libellé à afficher, si ce champ est "null" alors sa valeur est soit celle du système soit celle provenant du browser
CREATE TABLE t_type_table_language
(
  ttl_id serial,
  ttb_id int8 NOT NULL,
  loc_id int8 NOT NULL,
  ttl_label varchar(200) NOT NULL,
  ttl_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT ttl_id_pk PRIMARY KEY (ttl_id),
  CONSTRAINT ttb_id_fk FOREIGN KEY (ttb_id) REFERENCES t_type_table (ttb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT  
) WITHOUT OIDS;


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
  drv_cash numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_ticket numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_cheque numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_card numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_unpaid numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_amount numeric(12,4) NOT NULL DEFAULT 0.00,
  drv_takeaway boolean NOT NULL DEFAULT false,
  drv_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT drv_id_pk PRIMARY KEY (drv_id),
  CONSTRAINT drv_revenue_date_drv_takeaway_uni UNIQUE (drv_revenue_date,drv_takeaway)
) WITHOUT OIDS;


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
  vtr_amount numeric(12,4) NOT NULL DEFAULT 0.00,
  vtr_value numeric(12,4) NOT NULL DEFAULT 0.00,
  vtr_deleted boolean NOT NULL DEFAULT false,
  CONSTRAINT vtr_id_pk PRIMARY KEY (vtr_id),
  CONSTRAINT drv_id_fk FOREIGN KEY (drv_id) REFERENCES t_day_revenue (drv_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT drv_id_vat_id_uni UNIQUE (drv_id,vat_id)
) WITHOUT OIDS;




