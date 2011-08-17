-- For PostGresql script replace *{ by empty string '' and } by empty string ''.
-- IF EXISTS Statement must be at the end of the DROP SEQUENCE statment for HSQLDB.
-- For PostGresql, it must be after DROP TABLE or DROP SEQUENCE.
DROP TABLE IF EXISTS t_revenue_vat;
DROP TABLE IF EXISTS t_revenue_cashing;
DROP TABLE IF EXISTS t_revenue;
DROP TABLE IF EXISTS t_cashing_type;
DROP TABLE IF EXISTS t_table_cashing;
DROP TABLE IF EXISTS t_table_vat;
DROP TABLE IF EXISTS t_table_bill;
DROP TABLE IF EXISTS t_table_credit;
DROP TABLE IF EXISTS t_order_line;
DROP TABLE IF EXISTS t_dinner_table;
DROP TABLE IF EXISTS t_credit;
DROP TABLE IF EXISTS t_product_sold;
DROP TABLE IF EXISTS t_product_category;
DROP TABLE IF EXISTS t_product_language;
DROP TABLE IF EXISTS t_product;
DROP TABLE IF EXISTS t_product_part_language;
DROP TABLE IF EXISTS t_product_part;
DROP TABLE IF EXISTS t_product_special_code_language;
DROP TABLE IF EXISTS t_product_special_code;
DROP TABLE IF EXISTS t_category_language;
DROP TABLE IF EXISTS t_category;
DROP TABLE IF EXISTS t_user_locale;
DROP TABLE IF EXISTS t_user_authentication;
DROP TABLE IF EXISTS t_user_restaurant;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_user_role_language;
DROP TABLE IF EXISTS t_user_role;
DROP TABLE IF EXISTS t_printing_information_language;
DROP TABLE IF EXISTS t_printing_information;
DROP TABLE IF EXISTS t_locale;
DROP TABLE IF EXISTS t_restaurant_vat;
DROP TABLE IF EXISTS t_restaurant_prefix_table;
DROP TABLE IF EXISTS t_restaurant;
DROP TABLE IF EXISTS t_table_type;
DROP TABLE IF EXISTS t_value_added_tax;
DROP TABLE IF EXISTS t_enum;

DROP SEQUENCE IF EXISTS t_revenue_vat_rva_id_seq;
DROP SEQUENCE IF EXISTS t_revenue_cashing_rvc_id_seq;
DROP SEQUENCE IF EXISTS t_revenue_rev_id_seq;
DROP SEQUENCE IF EXISTS t_cashing_type_cst_id_seq;
DROP SEQUENCE IF EXISTS t_table_cashing_tcs_id_seq;
DROP SEQUENCE IF EXISTS t_table_vat_tvt_id_seq;
DROP SEQUENCE IF EXISTS t_table_bill_tbi_id_seq;
DROP SEQUENCE IF EXISTS t_table_credit_tcr_id_seq;
DROP SEQUENCE IF EXISTS t_order_line_orl_id_seq;
DROP SEQUENCE IF EXISTS t_dinner_table_dtb_id_seq;
DROP SEQUENCE IF EXISTS t_credit_cre_id_seq;
DROP SEQUENCE IF EXISTS t_product_pdt_id_seq;
DROP SEQUENCE IF EXISTS t_product_language_pdl_id_seq;
DROP SEQUENCE IF EXISTS t_product_category_pdc_id_seq;
DROP SEQUENCE IF EXISTS t_product_part_language_ppl_id_seq;
DROP SEQUENCE IF EXISTS t_product_part_prp_id_seq;
DROP SEQUENCE IF EXISTS t_product_special_code_language_pcl_id_seq;
DROP SEQUENCE IF EXISTS t_product_special_code_psc_id_seq;
DROP SEQUENCE IF EXISTS t_product_sold_pds_id_seq;
DROP SEQUENCE IF EXISTS t_category_language_ctl_id_seq;
DROP SEQUENCE IF EXISTS t_category_cat_id_seq;
DROP SEQUENCE IF EXISTS t_user_locale_ulo_id_seq;
DROP SEQUENCE IF EXISTS t_user_authentication_aut_id_seq;
DROP SEQUENCE IF EXISTS t_user_restaurant_urt_id_seq;
DROP SEQUENCE IF EXISTS t_user_usr_id_seq;
DROP SEQUENCE IF EXISTS t_user_role_language_url_id_seq;
DROP SEQUENCE IF EXISTS t_user_role_uro_id_seq;
DROP SEQUENCE IF EXISTS t_printing_information_language_pil_id_seq;
DROP SEQUENCE IF EXISTS t_printing_information_pin_id_seq;
DROP SEQUENCE IF EXISTS t_locale_loc_id_seq;
DROP SEQUENCE IF EXISTS t_restaurant_vat_rvt_id_seq;
DROP SEQUENCE IF EXISTS t_restaurant_prefix_table_rpt_id_seq;
DROP SEQUENCE IF EXISTS t_restaurant_res_id_seq;
DROP SEQUENCE IF EXISTS t_table_type_tbt_id_seq;
DROP SEQUENCE IF EXISTS t_value_added_tax_vat_id_seq;
DROP SEQUENCE IF EXISTS t_enum_enm_id_seq;


-- New START
CREATE SEQUENCE t_enum_enm_id_seq;
CREATE TABLE t_enum (
  enm_id integer *{DEFAULT NEXTVAL('t_enum_enm_id_seq')} NOT null PRIMARY KEY,
  enm_type VARCHAR(50) NOT null,
  enm_name VARCHAR(50) NOT null,
  enm_order integer DEFAULT 0 NOT null,
  enm_default_label VARCHAR(255) NOT null,
  enm_language_key_label VARCHAR(100) NOT null,
  enm_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT enm_id_uni UNIQUE (enm_id),
  CONSTRAINT enm_type_enm_name_uni UNIQUE (enm_type, enm_name)
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_enum IS 'This table is used for enumeration type for other tables.';
COMMENT ON COLUMN t_enum.enm_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_enum.enm_type IS 'This is the type of the enum. This field consists with the enm_name an unique key.';
COMMENT ON COLUMN t_enum.enm_name IS 'This is the name of the enum. This field consists with the enm_type an unique key.';
COMMENT ON COLUMN t_enum.enm_order IS 'This is the order of the enum for a specific enum type.';
COMMENT ON COLUMN t_enum.enm_default_label IS 'This field is a default label to display to user.';
COMMENT ON COLUMN t_enum.enm_language_key_label IS 'This field is used for java i18n. We can map this field with java properties files as a properties key in order to find the label value.';
COMMENT ON COLUMN t_enum.enm_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_enum_enm_id_seq *{OWNED BY t_enum.enm_id};

-- New START
CREATE SEQUENCE t_value_added_tax_vat_id_seq;
CREATE TABLE t_value_added_tax (
  vat_id integer *{DEFAULT NEXTVAL('t_value_added_tax_vat_id_seq')} NOT null PRIMARY KEY,
  vat_code_enm_id integer NOT null,
  vat_rate numeric(12,4) DEFAULT 19.60 NOT null,
  vat_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT vat_id_uni UNIQUE (vat_id),
  CONSTRAINT vat_code_enm_id_fk FOREIGN KEY (vat_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT vat_code_enm_id_uni UNIQUE (vat_code_enm_id)
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_value_added_tax IS 'This table is used for product value added tax.';
COMMENT ON COLUMN t_value_added_tax.vat_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_value_added_tax.vat_code_enm_id IS 'This is the code of the value added tax. This is a unique field. It is a foreign that refers to the t_enum table for type VAT.';
COMMENT ON COLUMN t_value_added_tax.vat_rate IS 'This is rate of value added tax.';
COMMENT ON COLUMN t_value_added_tax.vat_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_value_added_tax_vat_id_seq *{OWNED BY t_value_added_tax.vat_id};

-- New START
CREATE SEQUENCE t_table_type_tbt_id_seq;
CREATE TABLE t_table_type (
  tbt_id integer *{DEFAULT NEXTVAL('t_table_type_tbt_id_seq')} NOT null PRIMARY KEY,
  tbt_code_enm_id integer NOT null,
  tbt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT tbt_id_uni UNIQUE (tbt_id),
  CONSTRAINT tbt_code_enm_id_fk FOREIGN KEY (tbt_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT tbt_code_enm_id_uni UNIQUE (tbt_code_enm_id)
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_table_type IS 'This table is used for table type. The dinner table must specify a table type which can be EAT_IN, TAKEAWAY...';
COMMENT ON COLUMN t_table_type.tbt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_table_type.tbt_code_enm_id IS 'This is the code of the table type. This is a unique field. It is a foreign that refers to the t_enum table for type TABLE_TYPE.';
COMMENT ON COLUMN t_table_type.tbt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_table_type_tbt_id_seq *{OWNED BY t_table_type.tbt_id};

-- New START
CREATE SEQUENCE t_restaurant_res_id_seq;
CREATE TABLE t_restaurant (
  res_id integer *{DEFAULT NEXTVAL('t_restaurant_res_id_seq')} NOT null PRIMARY KEY,
  res_registration_date TIMESTAMP,
  res_reference VARCHAR(90) NOT null,
  res_name VARCHAR(80) NOT null,
  res_address_road VARCHAR(40) NOT null,
  res_address_zip VARCHAR(10) NOT null,
  res_address_city VARCHAR(20) NOT null,
  res_phone VARCHAR(30) NOT null,
  res_vat_ref VARCHAR(25) NOT null,
  res_visa_ref VARCHAR(25) NOT null,
  res_triple_DES_key VARCHAR(24) NOT null,
  res_vat_by_takeaway BOOLEAN DEFAULT true NOT null,
  res_takeaway_basic_reduction numeric(12,4) NOT null,
  res_takeaway_min_amount_reduction numeric(12,4) NOT null,
  res_specific_round integer NOT null,
  tbt_id integer NOT null,
  res_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT res_id_uni UNIQUE (res_id),
  CONSTRAINT res_specific_round_fk FOREIGN KEY (res_specific_round) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT res_tbt_id_fk FOREIGN KEY (tbt_id) REFERENCES t_table_type (tbt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT res_reference_uni UNIQUE (res_reference)
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_restaurant IS 'This table contains the restaurants informations.';
COMMENT ON COLUMN t_restaurant.res_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_restaurant.res_registration_date IS 'This is the restaurant creation date in the application.';
--res_reference reference unique permettant par exemple d'identifier quelle type impression faut-il effectuer grace au nom du fichier javascript(c'est pour cela qu'il ne faut pas d'espace)
COMMENT ON COLUMN t_restaurant.res_reference IS 'This is the restaurant reference in the application.';
COMMENT ON COLUMN t_restaurant.res_name IS 'This is the restaurant name.';
COMMENT ON COLUMN t_restaurant.res_address_road IS 'This is the restaurant address road.';
COMMENT ON COLUMN t_restaurant.res_address_zip IS 'This is the restaurant address zip code.';
COMMENT ON COLUMN t_restaurant.res_address_city IS 'This is the restaurant address city.';
COMMENT ON COLUMN t_restaurant.res_phone IS 'This is the restaurant phone number.';
COMMENT ON COLUMN t_restaurant.res_vat_ref IS 'This is the restaurant V.A.T(Value Added Taxes) reference.';
COMMENT ON COLUMN t_restaurant.res_visa_ref IS 'This is the restaurant visa reference.';
COMMENT ON COLUMN t_restaurant.res_triple_DES_key IS 'This is the restaurant triple DES key.';
COMMENT ON COLUMN t_restaurant.res_vat_by_takeaway IS 'This is used to know if we have to apply the V.A.T(Value Added Taxes) when it is a takeaway table. The default value is true.';
COMMENT ON COLUMN t_restaurant.res_takeaway_basic_reduction IS 'This is the restaurant reduction for takeaway table we have to apply. This field depends on the field res_takeaway_min_amount_reduction.';
COMMENT ON COLUMN t_restaurant.res_takeaway_min_amount_reduction IS 'This is the minimum amount value to apply a reduction for takeaway table.';
--res_specific_round 1 = HALF ROUND 2 = TENTH ROUND
COMMENT ON COLUMN t_restaurant.res_specific_round IS 'This is the specific round to apply on all amounts calculations. It is a foreign that refers to the t_enum table for type SPECIFIC_ROUND_CALCULATION.';
COMMENT ON COLUMN t_restaurant.tbt_id IS 'This is the default table type. It is a foreign that refers to the t_table_type table. It is used to specify the dinner table type which can be EAT_IN, TAKEAWAY, ....';
COMMENT ON COLUMN t_restaurant.res_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_restaurant_res_id_seq *{OWNED BY t_restaurant.res_id};


-- New START
CREATE SEQUENCE t_restaurant_prefix_table_rpt_id_seq;
CREATE TABLE t_restaurant_prefix_table (
  rpt_id integer *{DEFAULT NEXTVAL('t_restaurant_prefix_table_rpt_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  tbt_id integer NOT null,
  rpt_prefix_enm_id integer NOT null,
  rpt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT rpt_id_uni UNIQUE (rpt_id),
  CONSTRAINT rpt_res_id_tbt_id_rpt_prefix_enm_id_uni UNIQUE (res_id, tbt_id, rpt_prefix_enm_id),
  CONSTRAINT rpt_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rpt_tbt_id_fk FOREIGN KEY (tbt_id) REFERENCES t_table_type (tbt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rpt_prefix_enm_id_fk FOREIGN KEY (rpt_prefix_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_restaurant_prefix_table IS 'This table is a association table. This table is used to store the list of restaurant table prefix names. These prefix names is used to know that a table is considered as a takeaway table.';
COMMENT ON COLUMN t_restaurant_prefix_table.rpt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_restaurant_prefix_table.res_id IS 'This is a foreign key that refers to t_restaurant. This field and the other tbt_id and rpt_prefix_enm_id fields consist of a unique field.';
COMMENT ON COLUMN t_restaurant_prefix_table.tbt_id IS 'This is a foreign key that refers to t_table_type. This field and the other res_id and rpt_prefix_enm_id fields consist of a unique field. It could be TAKE_AWAY type or EAT_IN type.';
COMMENT ON COLUMN t_restaurant_prefix_table.rpt_prefix_enm_id IS 'This is a foreign key that refers to t_enum table for type PREFIX_TABLE_NAME. This field and the other res_id and tbt_id fields consist of a unique field. It could a letter as "E", "A", "B" ...';
COMMENT ON COLUMN t_restaurant_prefix_table.rpt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_restaurant_prefix_table_rpt_id_seq *{OWNED BY t_restaurant_prefix_table.rpt_id};

-- New START
CREATE SEQUENCE t_restaurant_vat_rvt_id_seq;
CREATE TABLE t_restaurant_vat (
  rvt_id integer *{DEFAULT NEXTVAL('t_restaurant_vat_rvt_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  vat_id integer NOT null,
  rvt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT rvt_id_uni UNIQUE (rvt_id),
  CONSTRAINT rvt_res_id_vat_id_uni UNIQUE (res_id, vat_id),
  CONSTRAINT rvt_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rvt_vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_restaurant_vat IS 'This table is used for restaurant value added tax. Each restaurant has a list of value added tax.';
COMMENT ON COLUMN t_restaurant_vat.rvt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_restaurant_vat.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the other vat_id field consist of a unique field.';
COMMENT ON COLUMN t_restaurant_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the value added tax. This field and the other res_id field consist of a unique field.';
COMMENT ON COLUMN t_restaurant_vat.rvt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_restaurant_vat_rvt_id_seq *{OWNED BY t_restaurant_vat.rvt_id};

-- New START
CREATE SEQUENCE t_locale_loc_id_seq;
CREATE TABLE t_locale (
	loc_id integer *{DEFAULT NEXTVAL('t_locale_loc_id_seq')} NOT null PRIMARY KEY, 
	loc_language VARCHAR(3) NOT null, 
	loc_deleted BOOLEAN DEFAULT false NOT null, 
	CONSTRAINT loc_id_uni UNIQUE (loc_id), 
	CONSTRAINT loc_language_uni UNIQUE (loc_language)
);  
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_locale IS 'This table is used for i18n.';
COMMENT ON COLUMN t_locale.loc_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_locale.loc_language IS 'This is language ISO code.';
COMMENT ON COLUMN t_locale.loc_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_locale_loc_id_seq *{OWNED BY t_locale.loc_id};

-- New START
CREATE SEQUENCE t_printing_information_pin_id_seq;
CREATE TABLE t_printing_information (
  pin_id integer *{DEFAULT NEXTVAL('t_printing_information_pin_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  pin_order integer DEFAULT 0 NOT null,
  pin_alignment_enm_id integer NOT null,
  pin_size_enm_id integer NOT null,
  pin_part_enm_id integer NOT null,
  pin_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pin_id_uni UNIQUE (pin_id),
  CONSTRAINT pin_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pin_alignment_enm_id_fk FOREIGN KEY (pin_alignment_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pin_size_enm_id_fk FOREIGN KEY (pin_size_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pin_part_enm_id_fk FOREIGN KEY (pin_part_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_printing_information IS 'This table is used for printing custom informations on specific restaurant.';
COMMENT ON COLUMN t_printing_information.pin_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_printing_information.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the other enm_id field consist of a unique field.';
COMMENT ON COLUMN t_printing_information.pin_order IS 'It is used to specify the order of the printing information.';
COMMENT ON COLUMN t_printing_information.pin_alignment_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the alignment of the printing information.';
COMMENT ON COLUMN t_printing_information.pin_size_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the size of the printing information.';
COMMENT ON COLUMN t_printing_information.pin_part_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the part of the printing information.';
COMMENT ON COLUMN t_printing_information.pin_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_printing_information_pin_id_seq *{OWNED BY t_printing_information.pin_id};

-- New START
CREATE SEQUENCE t_printing_information_language_pil_id_seq;
CREATE TABLE t_printing_information_language (
  pil_id integer *{DEFAULT NEXTVAL('t_printing_information_language_pil_id_seq')} NOT null PRIMARY KEY,
  pin_id integer NOT null,
  loc_id integer NOT null,
  pil_label VARCHAR(200) NOT null,
  pil_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pil_id_uni UNIQUE (pil_id),
  CONSTRAINT pil_pin_id_loc_id_uni UNIQUE (pin_id, loc_id),
  CONSTRAINT pil_pin_id_fk FOREIGN KEY (pin_id) REFERENCES t_printing_information (pin_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pil_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_printing_information_language IS 'This table is used for printing custom informations on specific restaurant depending on the specific language.';
COMMENT ON COLUMN t_printing_information_language.pil_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_printing_information_language.pin_id IS 'This is a foreign key that refers to t_printing_information. It is used to specify the printing information on specific restaurant. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_printing_information_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the printing information. This field and the other pin_id field consist of a unique field.';
COMMENT ON COLUMN t_printing_information_language.pil_label IS 'This is the label of the printing information depending on the language.';
COMMENT ON COLUMN t_printing_information_language.pil_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_printing_information_language_pil_id_seq *{OWNED BY t_printing_information_language.pil_id};

-- New START
CREATE SEQUENCE t_user_role_uro_id_seq;
CREATE TABLE t_user_role (
  uro_id integer *{DEFAULT NEXTVAL('t_user_role_uro_id_seq')} NOT null PRIMARY KEY,
  uro_code_enm_id integer NOT null,
  uro_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT uro_id_uni UNIQUE (uro_id),
  CONSTRAINT uro_code_enum_id_uni UNIQUE (uro_code_enm_id),
  CONSTRAINT uro_code_enm_id_fk FOREIGN KEY (uro_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user_role IS 'This table is used for user role.';
COMMENT ON COLUMN t_user_role.uro_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user_role.uro_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the user role code like GLOBAL_ADMINISTRATOR, ADMINISTRATOR ...';
COMMENT ON COLUMN t_user_role.uro_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_role_uro_id_seq *{OWNED BY t_user_role.uro_id};

-- New START
CREATE SEQUENCE t_user_role_language_url_id_seq;
CREATE TABLE t_user_role_language (
  url_id integer *{DEFAULT NEXTVAL('t_user_role_language_url_id_seq')} NOT null PRIMARY KEY,
  uro_id integer NOT null,
  loc_id integer NOT null,
  url_label VARCHAR(200) NOT NULL,
  url_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT url_id_uni UNIQUE (url_id),
  CONSTRAINT url_uro_id_loc_id_uni UNIQUE (uro_id, loc_id),
  CONSTRAINT url_uro_id_fk FOREIGN KEY (uro_id) REFERENCES t_user_role (uro_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT url_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user_role_language IS 'This table is used for user role depending on the specific language.';
COMMENT ON COLUMN t_user_role_language.url_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user_role_language.uro_id IS 'This is a foreign key that refers to t_user_role. It is used to specify the user role. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_user_role_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the user role. This field and the other uro_id field consist of a unique field.';
COMMENT ON COLUMN t_user_role_language.url_label IS 'This is the label of the user role depending on the language.';
COMMENT ON COLUMN t_user_role_language.url_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_role_language_url_id_seq *{OWNED BY t_user_role_language.url_id};

-- New START
CREATE SEQUENCE t_user_usr_id_seq;
CREATE TABLE t_user (
  usr_id integer *{DEFAULT NEXTVAL('t_user_usr_id_seq')} NOT null PRIMARY KEY,
  usr_name VARCHAR(200) NOT null,  
  usr_forename1 VARCHAR(200) NOT null,
  usr_forename2 VARCHAR(200),
  usr_birthdate TIMESTAMP NOT null,
  usr_sex BOOLEAN DEFAULT true NOT null,
  usr_title_enm_id integer NOT null,
  usr_picture *{bytea},
  usr_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT usr_id_uni UNIQUE (usr_id),
  CONSTRAINT usr_title_enm_id_fk FOREIGN KEY (usr_title_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user IS 'This table is used for user.';
COMMENT ON COLUMN t_user.usr_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user.usr_name IS 'This is the user name.';
COMMENT ON COLUMN t_user.usr_forename1 IS 'This is the first forename of the user.';
COMMENT ON COLUMN t_user.usr_forename2 IS 'This is the second forename of the user.';
COMMENT ON COLUMN t_user.usr_birthdate IS 'This is the birthdate of the user.';
COMMENT ON COLUMN t_user.usr_sex IS 'This is the sex of the user.';
COMMENT ON COLUMN t_user.usr_title_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the user title like MR, MRS, MISS, DR ...';
COMMENT ON COLUMN t_user.usr_picture IS 'This is the picture of the user.';
COMMENT ON COLUMN t_user.usr_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_usr_id_seq *{OWNED BY t_user.usr_id};

-- New START
CREATE SEQUENCE t_user_restaurant_urt_id_seq;
CREATE TABLE t_user_restaurant (
  urt_id integer *{DEFAULT NEXTVAL('t_user_restaurant_urt_id_seq')} NOT null PRIMARY KEY,
  usr_id integer NOT null,
  res_id integer NOT null,
  urt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT urt_id_uni UNIQUE (urt_id),
  CONSTRAINT urt_usr_id_res_id_uni UNIQUE (usr_id, res_id),
  CONSTRAINT urt_usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT,  
  CONSTRAINT urt_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user_restaurant IS 'This table is used to specify that a user has or works in several users.';
COMMENT ON COLUMN t_user_restaurant.urt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user_restaurant.usr_id IS 'This is a foreign key that refers to t_user. It is used to specify the user restaurant. This field and the other res_id field consist of a unique field.';
COMMENT ON COLUMN t_user_restaurant.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant of the user. This field and the other usr_id field consist of a unique field.';
COMMENT ON COLUMN t_user_restaurant.urt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_restaurant_urt_id_seq *{OWNED BY t_user_restaurant.urt_id};

-- New START
CREATE SEQUENCE t_user_authentication_aut_id_seq;
CREATE TABLE t_user_authentication (
  aut_id integer *{DEFAULT NEXTVAL('t_user_authentication_aut_id_seq')} NOT null PRIMARY KEY,
  loc_id integer NOT null,
  usr_id integer NOT null,
  res_id integer NOT null,
  uro_id integer NOT null,
  aut_login VARCHAR(200) NOT null,  
  aut_password VARCHAR(200) NOT null,  
  aut_level_pass1 VARCHAR(200),  
  aut_level_pass2 VARCHAR(200),  
  aut_level_pass3 VARCHAR(200),  
  aut_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT aut_id_uni UNIQUE (aut_id),
  CONSTRAINT aut_login_uni UNIQUE (aut_login),
  CONSTRAINT aut_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT aut_usr_id_fk FOREIGN KEY (usr_id) REFERENCES t_user (usr_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT aut_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT aut_uro_id_fk FOREIGN KEY (uro_id) REFERENCES t_user_role (uro_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user_authentication IS 'This table is used for users authentication.';
COMMENT ON COLUMN t_user_authentication.aut_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user_authentication.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the printing language.';
COMMENT ON COLUMN t_user_authentication.usr_id IS 'This is a foreign key that refers to t_user. It is used to specify the authenticated user.';
COMMENT ON COLUMN t_user_authentication.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant of the authenticated user.';
COMMENT ON COLUMN t_user_authentication.uro_id IS 'This is a foreign key that refers to t_user_role. It is used to specify the role of the authenticated user.';
COMMENT ON COLUMN t_user_authentication.aut_login IS 'This is the authenticated user login. It is an unique field.';
COMMENT ON COLUMN t_user_authentication.aut_password IS 'This is the authenticated user password.';
COMMENT ON COLUMN t_user_authentication.aut_level_pass1 IS 'This is the authenticated user password level 1.';
COMMENT ON COLUMN t_user_authentication.aut_level_pass2 IS 'This is the authenticated user password level 2.';
COMMENT ON COLUMN t_user_authentication.aut_level_pass3 IS 'This is the authenticated user password level 3.';
COMMENT ON COLUMN t_user_authentication.aut_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_authentication_aut_id_seq *{OWNED BY t_user_authentication.aut_id};

-- New START
CREATE SEQUENCE t_user_locale_ulo_id_seq;
CREATE TABLE t_user_locale (
  ulo_id integer *{DEFAULT NEXTVAL('t_user_locale_ulo_id_seq')} NOT null PRIMARY KEY,
  aut_id integer NOT null,
  loc_id integer NOT null,
  ulo_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT ulo_id_uni UNIQUE (ulo_id),
  CONSTRAINT ulo_aut_id_loc_id_uni UNIQUE (aut_id, loc_id),
  CONSTRAINT urt_aut_id_fk FOREIGN KEY (aut_id) REFERENCES t_user_authentication (aut_id) ON UPDATE RESTRICT ON DELETE RESTRICT,  
  CONSTRAINT urt_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_user_locale IS 'This table is used to specify the authenticated user locales.';
COMMENT ON COLUMN t_user_locale.ulo_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_user_locale.aut_id IS 'This is a foreign key that refers to t_user_authentication. It is used to specify the authenticated user. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_user_locale.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the authenticated user locale. This field and the other aut_id field consist of a unique field.';
COMMENT ON COLUMN t_user_locale.ulo_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_user_locale_ulo_id_seq *{OWNED BY t_user_locale.ulo_id};

-- New START
CREATE SEQUENCE t_category_cat_id_seq;
CREATE TABLE t_category (
  cat_id integer *{DEFAULT NEXTVAL('t_category_cat_id_seq')} NOT null PRIMARY KEY,
  cat_code_enm_id integer NOT null,
  cat_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT cat_id_uni UNIQUE (cat_id),
  CONSTRAINT cat_code_enum_id_uni UNIQUE (cat_code_enm_id),
  CONSTRAINT cat_code_enm_id_fk FOREIGN KEY (cat_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_category IS 'This table is used for product category.';
COMMENT ON COLUMN t_category.cat_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_category.cat_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product category code like MEAT, FISH ...';
COMMENT ON COLUMN t_category.cat_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_category_cat_id_seq *{OWNED BY t_category.cat_id};

-- New START
CREATE SEQUENCE t_category_language_ctl_id_seq;
CREATE TABLE t_category_language (
  ctl_id integer *{DEFAULT NEXTVAL('t_category_language_ctl_id_seq')} NOT null PRIMARY KEY,
  cat_id integer NOT null,
  loc_id integer NOT null,
  ctl_label VARCHAR(200) NOT NULL,
  ctl_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT ctl_id_uni UNIQUE (ctl_id),
  CONSTRAINT ctl_cat_id_loc_id_uni UNIQUE (cat_id, loc_id),
  CONSTRAINT ctl_cat_id_fk FOREIGN KEY (cat_id) REFERENCES t_category (cat_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT ctl_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_category_language IS 'This table is used for product category depending on the specific language.';
COMMENT ON COLUMN t_category_language.ctl_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_category_language.cat_id IS 'This is a foreign key that refers to t_category. It is used to specify the product category. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_category_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product category. This field and the other cat_id field consist of a unique field.';
COMMENT ON COLUMN t_category_language.ctl_label IS 'This is the label of the product category depending on the language.';
COMMENT ON COLUMN t_category_language.ctl_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_category_language_ctl_id_seq *{OWNED BY t_category_language.ctl_id};

-- New START
CREATE SEQUENCE t_product_special_code_psc_id_seq;
CREATE TABLE t_product_special_code (
  psc_id integer *{DEFAULT NEXTVAL('t_product_special_code_psc_id_seq')} NOT null PRIMARY KEY,
  res_id integer,
  psc_short_code VARCHAR(1),
  psc_code_enm_id integer NOT null,
  psc_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT psc_id_uni UNIQUE (psc_id),
  CONSTRAINT psc_psc_code_enum_id_uni UNIQUE (psc_code_enm_id),
--  CONSTRAINT psc_res_id_psc_short_code_uni UNIQUE (res_id, psc_short_code),
--  CONSTRAINT psc_res_id_psc_code_enum_id_uni UNIQUE (res_id, psc_code_enm_id),
--  CONSTRAINT psc_res_id_psc_short_code_psc_enum_id_uni UNIQUE (res_id, psc_short_code, psc_code_enm_id),
  CONSTRAINT psc_code_enm_id_fk FOREIGN KEY (psc_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT psc_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_special_code IS 'This table is used for product special code.';
COMMENT ON COLUMN t_product_special_code.psc_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_special_code.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. Currently NOT USED.';
COMMENT ON COLUMN t_product_special_code.psc_short_code IS 'This is used to specify the short code enter by user.Currently NOT USED.';
COMMENT ON COLUMN t_product_special_code.psc_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product special code.';
COMMENT ON COLUMN t_product_special_code.psc_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_special_code_psc_id_seq *{OWNED BY t_product_special_code.psc_id};

-- New START
CREATE SEQUENCE t_product_special_code_language_pcl_id_seq;
CREATE TABLE t_product_special_code_language (
  pcl_id integer *{DEFAULT NEXTVAL('t_product_special_code_language_pcl_id_seq')} NOT null PRIMARY KEY,
  psc_id integer NOT null,
  loc_id integer NOT null,
  pcl_label VARCHAR(200) NOT NULL,
  pcl_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pcl_id_uni UNIQUE (pcl_id),
  CONSTRAINT pcl_psc_id_loc_id_uni UNIQUE (psc_id, loc_id),
  CONSTRAINT pcl_psc_id_fk FOREIGN KEY (psc_id) REFERENCES t_product_special_code (psc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pcl_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_special_code_language IS 'This table is used for product special code depending on the specific language.';
COMMENT ON COLUMN t_product_special_code_language.pcl_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_special_code_language.psc_id IS 'This is a foreign key that refers to t_product_special_code. It is used to specify the product special code. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_product_special_code_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product special code. This field and the other psc_id field consist of a unique field.';
COMMENT ON COLUMN t_product_special_code_language.pcl_label IS 'This is the label of the product special code depending on the language.';
COMMENT ON COLUMN t_product_special_code_language.pcl_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_special_code_language_pcl_id_seq *{OWNED BY t_product_special_code_language.pcl_id};


-- New START
CREATE SEQUENCE t_product_part_prp_id_seq;
CREATE TABLE t_product_part (
  prp_id integer *{DEFAULT NEXTVAL('t_product_part_prp_id_seq')} NOT null PRIMARY KEY,
  prp_code_enm_id integer NOT null,
  prp_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT prp_id_uni UNIQUE (prp_id),
  CONSTRAINT prp_code_enum_id_uni UNIQUE (prp_code_enm_id),
  CONSTRAINT prp_code_enm_id_fk FOREIGN KEY (prp_code_enm_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_part IS 'This table is used for product part.';
COMMENT ON COLUMN t_product_part.prp_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_part.prp_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product part: ENTREE, PLAT DE RESISTANCE, DESSERT, CAFE...';
COMMENT ON COLUMN t_product_part.prp_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_part_prp_id_seq *{OWNED BY t_product_part.prp_id};

-- New START
CREATE SEQUENCE t_product_part_language_ppl_id_seq;
CREATE TABLE t_product_part_language (
  ppl_id integer *{DEFAULT NEXTVAL('t_product_part_language_ppl_id_seq')} NOT null PRIMARY KEY,
  prp_id integer NOT null,
  loc_id integer NOT null,
  ppl_label VARCHAR(200) NOT NULL,
  ppl_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT ppl_id_uni UNIQUE (ppl_id),
  CONSTRAINT ppl_prp_id_loc_id_uni UNIQUE (prp_id, loc_id),
  CONSTRAINT ppl_prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT ppl_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_part_language IS 'This table is used for product part depending on the specific language.';
COMMENT ON COLUMN t_product_part_language.ppl_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_part_language.prp_id IS 'This is a foreign key that refers to t_product_part. It is used to specify the product part. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_product_part_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product part. This field and the other prp_id field consist of a unique field.';
COMMENT ON COLUMN t_product_part_language.ppl_label IS 'This is the label of the product part depending on the language.';
COMMENT ON COLUMN t_product_part_language.ppl_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_part_language_ppl_id_seq *{OWNED BY t_product_part_language.ppl_id};

-- New START
CREATE SEQUENCE t_product_pdt_id_seq;
CREATE TABLE t_product (
  pdt_id integer *{DEFAULT NEXTVAL('t_product_pdt_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  pdt_code VARCHAR(5) NOT null,
  pdt_price numeric(12,4) NOT null,
  pdt_colorRGB VARCHAR(6),
  vat_id integer NOT null,
  pdt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pdt_id_uni UNIQUE (pdt_id),
  CONSTRAINT pdt_res_id_pdt_code_uni UNIQUE (res_id, pdt_code),
  CONSTRAINT pdt_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdt_vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product IS 'This table is used for product.';
COMMENT ON COLUMN t_product.pdt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant.';
COMMENT ON COLUMN t_product.pdt_code IS 'This is product code.';
COMMENT ON COLUMN t_product.pdt_price IS 'This is product price.';
COMMENT ON COLUMN t_product.pdt_colorRGB IS 'This is the highlight color product line see table t_order_line. The value is formatted as css color like xxyyzz.';
COMMENT ON COLUMN t_product.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the product value added tax.';
COMMENT ON COLUMN t_product.pdt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_pdt_id_seq *{OWNED BY t_product.pdt_id};

-- New START
CREATE SEQUENCE t_product_language_pdl_id_seq;
CREATE TABLE t_product_language (
  pdl_id integer *{DEFAULT NEXTVAL('t_product_language_pdl_id_seq')} NOT null PRIMARY KEY,
  pdt_id integer NOT null,
  loc_id integer NOT null,
  pdl_label VARCHAR(200) NOT null,
  pdl_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pdl_id_uni UNIQUE (pdl_id),
  CONSTRAINT pdl_pdt_id_loc_id_uni UNIQUE (pdt_id, loc_id),
  CONSTRAINT pdl_pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdl_loc_id_fk FOREIGN KEY (loc_id) REFERENCES t_locale (loc_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_language IS 'This table is used for product depending on the specific language.';
COMMENT ON COLUMN t_product_language.pdl_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_language.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. This field and the other loc_id field consist of a unique field.';
COMMENT ON COLUMN t_product_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product. This field and the other pdt_id field consist of a unique field.';
COMMENT ON COLUMN t_product_language.pdl_label IS 'This is the label of the product depending on the language.';
COMMENT ON COLUMN t_product_language.pdl_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_language_pdl_id_seq *{OWNED BY t_product_language.pdl_id};

-- New START
CREATE SEQUENCE t_product_category_pdc_id_seq;
CREATE TABLE t_product_category (
  pdc_id integer *{DEFAULT NEXTVAL('t_product_category_pdc_id_seq')} NOT null PRIMARY KEY,
  pdt_id integer NOT null,
  cat_id integer NOT null,
  pdc_quantity numeric(12,4) DEFAULT 0.00 NOT null,
  pdc_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pdc_id_uni UNIQUE (pdc_id),
  CONSTRAINT pdc_pdt_id_cat_id_uni UNIQUE (pdt_id, cat_id),
  CONSTRAINT pdc_pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT pdc_cat_id_fk FOREIGN KEY (cat_id) REFERENCES t_category (cat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_category IS 'This table is used for product depending on the specific categories.';
COMMENT ON COLUMN t_product_category.pdc_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_category.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. This field and the other cat_id field consist of a unique field.';
COMMENT ON COLUMN t_product_category.cat_id IS 'This is a foreign key that refers to t_category. It is used to specify the category of the product. This field and the other pdt_id field consist of a unique field.';
COMMENT ON COLUMN t_product_category.pdc_quantity IS 'This is the quantity of the category for the product. For example, if the category is FISH then the quantity is the quantity of fish for this product. This can be used for stock management.';
COMMENT ON COLUMN t_product_category.pdc_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_category_pdc_id_seq *{OWNED BY t_product_category.pdc_id};

-- New START
CREATE SEQUENCE t_product_sold_pds_id_seq;
CREATE TABLE t_product_sold (
  pds_id integer *{DEFAULT NEXTVAL('t_product_sold_pds_id_seq')} NOT null PRIMARY KEY,
  pds_sold_year integer NOT null,
  pds_sold_month integer NOT null,
  pds_sold_day integer NOT null,
  pdt_id integer NOT null,
  pds_quantity numeric(12,4) DEFAULT 0.00 NOT null,
  pds_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT pds_id_uni UNIQUE (pds_id),
  CONSTRAINT pds_sold_date_pdt_id_uni UNIQUE (pds_sold_year, pds_sold_month, pds_sold_day, pdt_id),
  CONSTRAINT rev_pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_product_sold IS 'This table is used for reporting of sold product.';
COMMENT ON COLUMN t_product_sold.pds_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_product_sold.pds_sold_year IS 'This is the sold year of the product. This field and the others pdt_id, pds_sold_month, and pds_sold_day consist of a unique field.';
COMMENT ON COLUMN t_product_sold.pds_sold_month IS 'This is the sold month of the product. This field and the others pdt_id, pds_sold_year, and pds_sold_day consist of a unique field.';
COMMENT ON COLUMN t_product_sold.pds_sold_day IS 'This is the sold day of the product. This field and the others pdt_id, pds_sold_year, and pds_sold_month consist of a unique field.';
COMMENT ON COLUMN t_product_sold.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the sold product. This field and the others pds_sold_year, pds_sold_month and pds_sold_day consist of a unique field.';
COMMENT ON COLUMN t_product_sold.pds_quantity IS 'This is the quantity of the sold product for a specific date.';
COMMENT ON COLUMN t_product_sold.pds_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_product_sold_pds_id_seq *{OWNED BY t_product_sold.pds_id};

-- New START
CREATE SEQUENCE t_credit_cre_id_seq;
CREATE TABLE t_credit (
  cre_id integer *{DEFAULT NEXTVAL('t_credit_cre_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  cre_reference VARCHAR(40) NOT null,
  cre_amount numeric(12,4) DEFAULT 0.00 NOT null,
  cre_created_date TIMESTAMP NOT null,
  cre_closing_date TIMESTAMP,
  cre_printed BOOLEAN DEFAULT false NOT null,
  cre_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT cre_id_uni UNIQUE (cre_id),
  CONSTRAINT cre_res_id_cre_reference_uni UNIQUE (res_id, cre_reference),
  CONSTRAINT cre_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_credit IS 'This table is used for restaurant credits. One credit must belong to a restaurant.';
COMMENT ON COLUMN t_credit.cre_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_credit.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant where the credit belongs. This field and the others cre_reference consist of a unique field.';
COMMENT ON COLUMN t_credit.cre_reference IS 'This is the reference of the credit. This field and the others res_id consist of a unique field.';
COMMENT ON COLUMN t_credit.cre_amount IS 'This is the amount of the credit.';
COMMENT ON COLUMN t_credit.cre_created_date IS 'This is the creation date of the credit.';
COMMENT ON COLUMN t_credit.cre_closing_date IS 'This is the using date of the credit.';
COMMENT ON COLUMN t_credit.cre_printed IS 'This is used to know if the bill has already been printed. Be careful to just print this credit once.';
COMMENT ON COLUMN t_credit.cre_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_credit_cre_id_seq *{OWNED BY t_credit.cre_id};

-- New START
CREATE SEQUENCE t_dinner_table_dtb_id_seq;
CREATE TABLE t_dinner_table (
  dtb_id integer *{DEFAULT NEXTVAL('t_dinner_table_dtb_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  dtb_code VARCHAR(5) NOT null,
  aut_id integer NOT null,
  roo_id integer,
  dtb_customers_number integer DEFAULT 0 NOT null,
  dtb_quantities_sum numeric(12,4) DEFAULT 0.00,
  dtb_amounts_sum numeric(12,4) DEFAULT 0.00,
  dtb_reduction_ratio numeric(12,4) DEFAULT 0.00,
  dtb_amount_pay numeric(12,4) DEFAULT 0.00,
  dtb_registration_date TIMESTAMP,
  dtb_printing_date TIMESTAMP,  
  dtb_reduction_ratio_changed BOOLEAN DEFAULT false NOT null,
  tbt_id integer NOT null,  
  dtb_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT dtb_id_uni UNIQUE (dtb_id),
  CONSTRAINT dtb_res_id_dtb_code_dtb_cashing_date_uni UNIQUE (res_id, dtb_code, dtb_registration_date),
  CONSTRAINT dtb_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT, 
  CONSTRAINT dtb_aut_id_fk FOREIGN KEY (aut_id) REFERENCES t_user_authentication (aut_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT dtb_tbt_id_fk FOREIGN KEY (tbt_id) REFERENCES t_table_type (tbt_id) ON UPDATE RESTRICT ON DELETE RESTRICT 
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_dinner_table IS 'This table is used for dinner tables.';
COMMENT ON COLUMN t_dinner_table.dtb_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_dinner_table.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant where the dinner table belongs. Notice that this information is already known with the aut_id field. But this field is used for the unicity of the dinner table. So this field and the others dtb_code and dtb_registration_date consist of a unique field.';
COMMENT ON COLUMN t_dinner_table.dtb_code IS 'This is the code number of the dinner table. This field and the others res_id and dtb_registration_date consist of a unique field.';
COMMENT ON COLUMN t_dinner_table.aut_id IS 'This is a foreign key that refers to t_user_authentication. It is used to specify the user authentication that created this dinner table.';
COMMENT ON COLUMN t_dinner_table.roo_id IS 'This is an id for the room where the dinner table is. It is not currently used.';
COMMENT ON COLUMN t_dinner_table.dtb_customers_number IS 'This is used to specify the number of customers.';
COMMENT ON COLUMN t_dinner_table.dtb_quantities_sum IS 'This is used to specify the sum of the order lines quantities. This value could be calculated from order lines table.';
COMMENT ON COLUMN t_dinner_table.dtb_amounts_sum IS 'This is used to specify the sum of the order lines amounts. This value could be calculated from order lines table.';
COMMENT ON COLUMN t_dinner_table.dtb_reduction_ratio IS 'This is used to specify the reduction ratio.';
COMMENT ON COLUMN t_dinner_table.dtb_amount_pay IS 'This is used to specify the amount to pay. This value could be calculated with value of dtb_reduction_ratio and dtb_amounts_sum. amountPay = dtb_amounts_sum-dtb_amounts_sum*dtb_reduction_ratio/100.';
COMMENT ON COLUMN t_dinner_table.dtb_registration_date IS 'This is used to specify the registration/creation date. This field and the others res_id and dtb_code consist of a unique field.';
COMMENT ON COLUMN t_dinner_table.dtb_printing_date IS 'This is used to specify the printing date.';
COMMENT ON COLUMN t_dinner_table.dtb_reduction_ratio_changed IS 'This is used to specify if user has changed the reduction ratio.';
COMMENT ON COLUMN t_dinner_table.tbt_id IS 'This is used to specify the type of dinner table. Could be TAKE-AWAY, EAT-IN ... This is a foreign key that refers to t_table_type.';
COMMENT ON COLUMN t_dinner_table.dtb_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_dinner_table_dtb_id_seq *{OWNED BY t_dinner_table.dtb_id};

-- New START
CREATE SEQUENCE t_order_line_orl_id_seq;
CREATE TABLE t_order_line (
  orl_id integer *{DEFAULT NEXTVAL('t_order_line_orl_id_seq')} NOT null PRIMARY KEY,
  dtb_id integer NOT null,
  psc_id integer NOT null,
  pdt_id integer,
  cre_id integer,
  prp_id integer,
  orl_quantity numeric(12,4) DEFAULT 0.00 NOT null,
  orl_label VARCHAR(50) NOT null,
  orl_unit_price numeric(12,4) DEFAULT 0.00 NOT null,
  orl_amount numeric(12,4) DEFAULT 0.00 NOT null,
  orl_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT orl_id_uni UNIQUE (orl_id),
  CONSTRAINT orl_dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT orl_psc_id_fk FOREIGN KEY (psc_id) REFERENCES t_product_special_code (psc_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT orl_pdt_id_fk FOREIGN KEY (pdt_id) REFERENCES t_product (pdt_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT orl_cre_id_fk FOREIGN KEY (cre_id) REFERENCES t_credit (cre_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT orl_prp_id_fk FOREIGN KEY (prp_id) REFERENCES t_product_part (prp_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_order_line IS 'This table is used for order lines depending on the specific dinner table.';
COMMENT ON COLUMN t_order_line.orl_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_order_line.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table.';
COMMENT ON COLUMN t_order_line.psc_id IS 'This is a foreign key that refers to t_product_special_code. It is used to specify the product special code. This code is never null, it takes a default value with "". The code could be for example "/", "-", "#", "@".';
COMMENT ON COLUMN t_order_line.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. If this field is null then the order line depends on the product special code psc_id which must not be null.';
COMMENT ON COLUMN t_order_line.cre_id IS 'This is a foreign key that refers to t_credit. It is used to specify the credit consumed. If this field is NOT null then the order line depends on the product special code psc_id which must not be null with code value equals to @.';
COMMENT ON COLUMN t_order_line.prp_id IS 'This is a foreign key that refers to t_product_part. It is used to specify the product part the order line belongs: ENTREE, PLAT or DESSERT for example.';
COMMENT ON COLUMN t_order_line.orl_quantity IS 'This is the quantity of the product.';
COMMENT ON COLUMN t_order_line.orl_label IS 'This is the label of the product. If the psc_id is of type "/" then the label is the user entry description. If the psc_id is null then the label is the label of the product pdt_id depending on the user locale.';
COMMENT ON COLUMN t_order_line.orl_unit_price IS 'This is the unit price of the order line. Here, we do not take into account the quantity.';
COMMENT ON COLUMN t_order_line.orl_amount IS 'This is the amount of the order line. The value is equals to orl_quantity multiply by orl_unit_price.';
COMMENT ON COLUMN t_order_line.orl_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_order_line_orl_id_seq *{OWNED BY t_order_line.orl_id};

-- New START
CREATE SEQUENCE t_table_credit_tcr_id_seq;
CREATE TABLE t_table_credit (
  tcr_id integer *{DEFAULT NEXTVAL('t_table_credit_tcr_id_seq')} NOT null PRIMARY KEY,
  cre_id integer NOT null,
  dtb_id integer NOT null,
  tcr_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT tcr_id_uni UNIQUE (tcr_id),
  CONSTRAINT tcr_cre_id_dtb_id_uni UNIQUE (cre_id, dtb_id),
  CONSTRAINT tcr_cre_id_fk FOREIGN KEY (cre_id) REFERENCES t_credit (cre_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT tcr_dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_table_credit IS 'This table is used for dinner table credits. This table is used for credits dinner tables association. For a given dinner table, we could have several credits but often just one. These credits must have the cre_closing_date value equals to null.';
COMMENT ON COLUMN t_table_credit.tcr_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_table_credit.cre_id IS 'This is a foreign key that refers to t_credit. It is used to specify the credit of the dinner table. This field and the others dtb_id consist of a unique field.';
COMMENT ON COLUMN t_table_credit.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the others cre_id consist of a unique field.';
COMMENT ON COLUMN t_table_credit.tcr_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_table_credit_tcr_id_seq *{OWNED BY t_table_credit.tcr_id};

-- New START
CREATE SEQUENCE t_table_bill_tbi_id_seq;
CREATE TABLE t_table_bill (
  tbi_id integer *{DEFAULT NEXTVAL('t_table_bill_tbi_id_seq')} NOT null PRIMARY KEY,
  dtb_id integer NOT null,
  tbi_reference VARCHAR(40) NOT null,
  tbi_order integer,
  tbi_meal_number integer,
  tbi_amount numeric(12,4) DEFAULT 0.00 NOT null,
  tbi_printed BOOLEAN DEFAULT false NOT null,
  tbi_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT tbi_id_uni UNIQUE (tbi_id),
  CONSTRAINT tbi_dtb_id_tbi_reference_tbi_order_uni UNIQUE (dtb_id, tbi_reference, tbi_order),
  CONSTRAINT tbi_dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_table_bill IS 'This table is used for bills of dinner table. There are several bills for a specific dinner table.';
COMMENT ON COLUMN t_table_bill.tbi_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_table_bill.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the other tbi_reference, tbi_order fields consist of a unique field.';
COMMENT ON COLUMN t_table_bill.tbi_reference IS 'This is a the bill reference for authentication checking. This field and the other dtb_id, tbi_order fields consist of a unique field.';
COMMENT ON COLUMN t_table_bill.tbi_order IS 'This is a the bill order. We can have several bill for a specific table. So this field is used to increment the bill table number for printing information. This field and the other dtb_id, tbi_reference fields consist of a unique field.';
COMMENT ON COLUMN t_table_bill.tbi_meal_number IS 'This is the bill meal number.';
COMMENT ON COLUMN t_table_bill.tbi_amount IS 'This is the bill amount.';
COMMENT ON COLUMN t_table_bill.tbi_printed IS 'This is used to know if the bill has already been printed.';
COMMENT ON COLUMN t_table_bill.tbi_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_table_bill_tbi_id_seq *{OWNED BY t_table_bill.tbi_id};

-- New START
CREATE SEQUENCE t_table_vat_tvt_id_seq;
CREATE TABLE t_table_vat (
  tvt_id integer *{DEFAULT NEXTVAL('t_table_vat_tvt_id_seq')} NOT null PRIMARY KEY,
  dtb_id integer NOT null,
  vat_id integer NOT null,
  tvt_amount numeric(12,4) DEFAULT 0.00 NOT null,
  tvt_value numeric(12,4) DEFAULT 0.00 NOT null,
  tvt_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT tvt_id_uni UNIQUE (tvt_id),
  CONSTRAINT tvt_dtb_id_vat_id_uni UNIQUE (dtb_id, vat_id),
  CONSTRAINT tvt_dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT tvt_vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_table_vat IS 'This table is used for amounts and values of dinner table depending on the Value Added Tax.';
COMMENT ON COLUMN t_table_vat.tvt_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_table_vat.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the other vat_id field consist of a unique field.';
COMMENT ON COLUMN t_table_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the Value Added Tax. This field and the other dtb_id field consist of a unique field.';
COMMENT ON COLUMN t_table_vat.tvt_amount IS 'This is the amount of the dinner table depending on the specific Value Added Tax. If the dinner table has order lines with Value Added Tax rate of 5.5, then the amount is the sum of the amount of dinner table order lines with the specific Value Added Tax rate 5.5.';
COMMENT ON COLUMN t_table_vat.tvt_value IS 'This is the value of the dinner table depending on the specific Value Added Tax. If the Value Added Tax rate is 19.6 then the value is equals to tvt_amount*19.6./(19.6+100).';
COMMENT ON COLUMN t_table_vat.tvt_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_table_vat_tvt_id_seq *{OWNED BY t_table_vat.tvt_id};


-- New START
CREATE SEQUENCE t_table_cashing_tcs_id_seq;
CREATE TABLE t_table_cashing (
  tcs_id integer *{DEFAULT NEXTVAL('t_table_cashing_tcs_id_seq')} NOT null PRIMARY KEY,
  dtb_id integer NOT null,
  tcs_cashing_date DATE NOT null,
  tcs_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT tcs_id_uni UNIQUE (tcs_id),
  CONSTRAINT tcs_dtb_id_uni UNIQUE (dtb_id),
  CONSTRAINT tcs_dtb_id_fk FOREIGN KEY (dtb_id) REFERENCES t_dinner_table (dtb_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_table_cashing IS 'This table is used for cashing of dinner table.';
COMMENT ON COLUMN t_table_cashing.tcs_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_table_cashing.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. There is only one dinner table for one cashing. This field consist of a unique field.';
COMMENT ON COLUMN t_table_cashing.tcs_cashing_date IS 'This is the date of the dinner table cashing.';
COMMENT ON COLUMN t_table_cashing.tcs_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_table_cashing_tcs_id_seq *{OWNED BY t_table_cashing.tcs_id};

-- New START
CREATE SEQUENCE t_cashing_type_cst_id_seq;
CREATE TABLE t_cashing_type (
  cst_id integer *{DEFAULT NEXTVAL('t_cashing_type_cst_id_seq')} NOT null PRIMARY KEY,
  tcs_id integer NOT null,
  cst_type_enum_id integer NOT null,
  cst_amount numeric(12,4) DEFAULT 0.00 NOT null,
  cst_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT cst_id_uni UNIQUE (cst_id),
  CONSTRAINT cst_tcs_id_cst_type_enum_id_uni UNIQUE (tcs_id, cst_type_enum_id),
  CONSTRAINT cst_tcs_id_fk FOREIGN KEY (tcs_id) REFERENCES t_table_cashing (tcs_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT cst_type_enum_id_fk FOREIGN KEY (cst_type_enum_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_cashing_type IS 'This table is used for cashing of dinner table depending on type of cashing.';
COMMENT ON COLUMN t_cashing_type.cst_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_cashing_type.tcs_id IS 'This is a foreign key that refers to t_table_cashing. It is used to specify the cashed table. This field and the other cst_type_enum_id fields consist of a unique field.';
COMMENT ON COLUMN t_cashing_type.cst_type_enum_id IS 'This is a foreign key that refers to t_enum. It is used to specify the type of cashing. It could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID... This field and the other tcs_id fields consist of a unique field.';
COMMENT ON COLUMN t_cashing_type.cst_amount IS 'This is the amount of the dinner table depending on the specific type of cashing.';
COMMENT ON COLUMN t_cashing_type.cst_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_cashing_type_cst_id_seq *{OWNED BY t_cashing_type.cst_id};

-- New START
CREATE SEQUENCE t_revenue_rev_id_seq;
CREATE TABLE t_revenue
(
  rev_id integer *{DEFAULT NEXTVAL('t_revenue_rev_id_seq')} NOT null PRIMARY KEY,
  res_id integer NOT null,
  rev_revenue_date DATE NOT null,
  tbt_id integer NOT null,
  rev_printing_date TIMESTAMP,
  rev_closing_date TIMESTAMP,
  rev_amount numeric(12,4) DEFAULT 0.00 NOT null,
  rev_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT rev_id_uni UNIQUE (rev_id),
  CONSTRAINT rev_res_id_rev_revenue_date_tbt_id_uni UNIQUE (res_id, rev_revenue_date, tbt_id),
  CONSTRAINT rev_res_id_fk FOREIGN KEY (res_id) REFERENCES t_restaurant (res_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rev_tbt_id_fk FOREIGN KEY (tbt_id) REFERENCES t_table_type (tbt_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_revenue IS 'This table is used for reporting day revenue depending on the type of table.';
COMMENT ON COLUMN t_revenue.rev_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_revenue.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the others rev_revenue_date and tbt_id fields consist of a unique field.';
COMMENT ON COLUMN t_revenue.rev_revenue_date IS 'This is the value of the dinner table depending on the specific type of cashing. This field and the others tbt_id and res_id fields consist of a unique field.';
COMMENT ON COLUMN t_revenue.tbt_id IS 'This is a foreign key that refers to t_table_type. It is used to specify the type of table. It could be for instance TAKE AWAY or EAT IN. This field and the others rev_revenue_date and res_id fields consist of a unique field.';
COMMENT ON COLUMN t_revenue.rev_printing_date IS 'This is the printing date of the day revenue depending on the type of table.';
COMMENT ON COLUMN t_revenue.rev_closing_date IS 'This is the closing date of the day revenue depending on the type of table.';
COMMENT ON COLUMN t_revenue.rev_amount IS 'This is the amount of the day revenue depending on the type of table.';
COMMENT ON COLUMN t_revenue.rev_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_revenue_rev_id_seq *{OWNED BY t_revenue.rev_id};

-- New START
CREATE SEQUENCE t_revenue_cashing_rvc_id_seq;
CREATE TABLE t_revenue_cashing (
  rvc_id integer *{DEFAULT NEXTVAL('t_revenue_cashing_rvc_id_seq')} NOT null PRIMARY KEY,
  rev_id integer NOT null,
  rvc_type_enum_id integer NOT null,
  rvc_amount numeric(12,4) DEFAULT 0.00 NOT null,
  rvc_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT rvc_id_uni UNIQUE (rvc_id),
  CONSTRAINT rvc_rev_id_rvc_type_enum_id_uni UNIQUE (rev_id, rvc_type_enum_id),
  CONSTRAINT rvc_rev_id_fk FOREIGN KEY (rev_id) REFERENCES t_revenue (rev_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rvc_type_enum_id_fk FOREIGN KEY (rvc_type_enum_id) REFERENCES t_enum (enm_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_revenue_cashing IS 'This table is used for cashing revenue depending on type of cashing.';
COMMENT ON COLUMN t_revenue_cashing.rvc_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_revenue_cashing.rev_id IS 'This is a foreign key that refers to t_revenue. It is used to specify the revenue. This field and the other rvc_type_enum_id field consist of a unique field.';
COMMENT ON COLUMN t_revenue_cashing.rvc_type_enum_id IS 'This is a foreign key that refers to t_enum. It is used to specify the type of cashing. It could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID... This field and the other rev_id field consist of a unique field.';
COMMENT ON COLUMN t_revenue_cashing.rvc_amount IS 'This is the amount of the revenue depending on the specific type of cashing.';
COMMENT ON COLUMN t_revenue_cashing.rvc_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_revenue_cashing_rvc_id_seq *{OWNED BY t_revenue_cashing.rvc_id};


-- New START
CREATE SEQUENCE t_revenue_vat_rva_id_seq;
CREATE TABLE t_revenue_vat (
  rva_id integer *{DEFAULT NEXTVAL('t_revenue_vat_rva_id_seq')} NOT null PRIMARY KEY,
  rev_id integer NOT null,
  vat_id integer NOT null,
  rva_amount numeric(12,4) DEFAULT 0.00 NOT null,
  rva_value numeric(12,4) DEFAULT 0.00 NOT null,
  rva_deleted BOOLEAN DEFAULT false NOT null,
  CONSTRAINT rva_id_uni UNIQUE (rva_id),
  CONSTRAINT rva_rev_id_vat_id_uni UNIQUE (rev_id, vat_id),
  CONSTRAINT rva_rev_id_fk FOREIGN KEY (rev_id) REFERENCES t_revenue (rev_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT rva_vat_id_fk FOREIGN KEY (vat_id) REFERENCES t_value_added_tax (vat_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
COMMENT ON TABLE t_revenue_vat IS 'This table is used for amounts and values of revenue depending on the Value Added Tax.';
COMMENT ON COLUMN t_revenue_vat.rva_id IS 'This is primary key of this table.';
COMMENT ON COLUMN t_revenue_vat.rev_id IS 'This is a foreign key that refers to t_revenue. It is used to specify the revenue. This field and the other vat_id field consist of a unique field.';
COMMENT ON COLUMN t_revenue_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the Value Added Tax. This field and the other rev_id field consist of a unique field.';
COMMENT ON COLUMN t_revenue_vat.rva_amount IS 'This is the amount of the revenue depending on the specific Value Added Tax. The amount revenue is the sum of the amount of dinner table order lines with the specific Value Added Tax rate 5.5 for specific day.';
COMMENT ON COLUMN t_revenue_vat.rva_value IS 'This is the value of the revenue depending on the specific Value Added Tax. If the Value Added Tax rate is 19.6 then the value is equals to tvt_amount*19.6./(19.6+100).';
COMMENT ON COLUMN t_revenue_vat.rva_deleted IS 'This is used for logical deletion.';
-- For PostGresql, the sequence is marked as "*{OWNED BY" the column, so that it will be dropped if the column or table is dropped.
ALTER SEQUENCE t_revenue_vat_rva_id_seq *{OWNED BY t_revenue_vat.rva_id};


