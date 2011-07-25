DELETE FROM t_revenue_vat;
DELETE FROM t_revenue_cashing;
DELETE FROM t_revenue;
DELETE FROM t_cashing_type;
DELETE FROM t_table_cashing;
DELETE FROM t_table_vat;
DELETE FROM t_table_bill;
DELETE FROM t_table_credit;
DELETE FROM t_order_line;
DELETE FROM t_dinner_table;
DELETE FROM t_credit;
DELETE FROM t_product_sold;
DELETE FROM t_product_category;
DELETE FROM t_product_language;
DELETE FROM t_product;
DELETE FROM t_product_part_language;
DELETE FROM t_product_part;
DELETE FROM t_product_special_code_language;
DELETE FROM t_product_special_code;
DELETE FROM t_category_language;
DELETE FROM t_category;
DELETE FROM t_user_locale;
DELETE FROM t_user_authentication;
DELETE FROM t_user_restaurant;
DELETE FROM t_user;
DELETE FROM t_user_role_language;
DELETE FROM t_user_role;
DELETE FROM t_printing_information_language;
DELETE FROM t_printing_information;
DELETE FROM t_locale;
DELETE FROM t_restaurant_vat;
DELETE FROM t_restaurant_prefix_table;
DELETE FROM t_restaurant;
DELETE FROM t_table_type;
DELETE FROM t_value_added_tax;
DELETE FROM t_enum;

--COMMENT ON TABLE t_locale IS 'This table is used for i18n.';
--COMMENT ON COLUMN t_locale.loc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_locale.loc_language IS 'This is language ISO code.';
--COMMENT ON COLUMN t_locale.loc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_locale VALUES(1, 'fr', false);
INSERT INTO t_locale VALUES(2, 'zh', false);

--COMMENT ON TABLE t_enum IS 'This table is used for enumeration type for other tables.';
--COMMENT ON COLUMN t_enum.enm_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_enum.enm_type IS 'This is the type of the enum. This field consists with the enm_name an unique key.';
--COMMENT ON COLUMN t_enum.enm_name IS 'This is the name of the enum. This field consists with the enm_type an unique key.';
--COMMENT ON COLUMN t_enum.enm_order IS 'This is the order of the enum for a specific enum type.';
--COMMENT ON COLUMN t_enum.enm_default_label IS 'This field is a default label to display to user.';
--COMMENT ON COLUMN t_enum.enm_language_key_label IS 'This field is used for java i18n. We can map this field with java properties files as a properties key in order to find the label value.';
--COMMENT ON COLUMN t_enum.enm_deleted IS 'This is used for logical deletion.';
-- The first value of generated id is 0
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PREFIX_TABLE_NAME', 'A', 0, 'A', 'PREFIX_TABLE_NAME.A.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PREFIX_TABLE_NAME', 'B', 1, 'B', 'PREFIX_TABLE_NAME.B.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('SPECIFIC_ROUND', 'HALF_ROUND', 0, 'HALF_ROUND', 'SPECIFIC_ROUND.HALF_ROUND.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('SPECIFIC_ROUND', 'TENTH_ROUND', 1, 'TENTH_ROUND', 'SPECIFIC_ROUND.TENTH_ROUND.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_ALIGNMENT', 'RIGHT', 0, 'Right', 'PRINTING_INFORMATION_ALIGNMENT.RIGHT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_ALIGNMENT', 'CENTER', 1, 'Center', 'PRINTING_INFORMATION_ALIGNMENT.CENTER.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_ALIGNMENT', 'LEFT', 2, 'Left', 'PRINTING_INFORMATION_ALIGNMENT.LEFT.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_SIZE', 'SMALL', 0, 'Small', 'PRINTING_INFORMATION_SIZE.SMALL.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_SIZE', 'MEDIUM', 1, 'Medium', 'PRINTING_INFORMATION_SIZE.MEDIUM.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_SIZE', 'LARGE', 2, 'Large', 'PRINTING_INFORMATION_SIZE.LARGE.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_PART', 'HEADER', 0, 'Header', 'PRINTING_INFORMATION_PART.HEADER.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRINTING_INFORMATION_PART', 'FOOTER', 1, 'Footer', 'PRINTING_INFORMATION_PART.FOOTER.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_ROLE', 'GLOBAL_ADMINISTRATOR', 0, 'Global Administrator', 'USER_ROLE.GLOBAL_ADMINISTRATOR.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_ROLE', 'ADMINISTRATOR', 1, 'Administrator', 'USER_ROLE.ADMINISTRATOR.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_ROLE', 'EMPLOYEE', 2, 'Employee', 'USER_ROLE.EMPLOYEE.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_ROLE', 'CUSTOMER', 3, 'Customer', 'USER_ROLE.CUSTOMER.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'MEAT', 0, 'Meat', 'CATEGORY.MEAT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'FISH', 1, 'Fish', 'CATEGORY.FISH.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'WATER', 2, 'Water', 'CATEGORY.WATER.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'WHITE_WINE', 3, 'White Wine', 'CATEGORY.WHITE_WINE.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'DEFAULT', 0, 'Default product', 'PRODUCT_SPECIAL_CODE.DEFAULT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'OFFERED_PRODUCT', 1, 'Offered product', 'PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'DISCOUNT_ORDER', 2, 'Reduced order', 'PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'USER_ORDER', 3, 'User order', 'PRODUCT_SPECIAL_CODE.USER_ORDER.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'TEST1', 4, 'Test 1', 'PRODUCT_SPECIAL_CODE.TEST1.4', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'TEST2', 5, 'Test 2', 'PRODUCT_SPECIAL_CODE.TEST2.5', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'ENTRY', 0, 'Test 1', 'PRODUCT_PART.ENTRY.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'DESSERT', 1, 'Test 2', 'PRODUCT_PART.DESSERT.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'RED_WINE', 4, 'Red Wine', 'CATEGORY.RED_WINE.4', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_TITLE', 'MISTER', 0, 'Mister', 'USER_TITLE.MISTER.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_TITLE', 'MISS', 1, 'Miss', 'USER_TITLE.MISS.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_TITLE', 'DOCTOR', 2, 'Doctor', 'USER_TITLE.DOCTOR.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('VALUE_ADDED_TAX', 'ALCOHOL', 0, '19.60', 'VALUE_ADDED_TAX.ALCOHOL.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('VALUE_ADDED_TAX', 'DEFAULT', 1, '5.50', 'VALUE_ADDED_TAX.DEFAULT.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('TABLE_TYPE', 'TAKE_AWAY', 0, 'Take away', 'TABLE_TYPE.TAKE_AWAY.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('TABLE_TYPE', 'EAT_IN', 1, 'Eat in', 'TABLE_TYPE.EAT_IN.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CASH', 0, 'Generic Cash', 'CASHING_TYPE.GENERIC_CASH.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'EURO_CASH', 1, 'Euro Cash', 'CASHING_TYPE.EURO_CASH.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'DOLLAR_CASH', 2, 'Dollar Cash', 'CASHING_TYPE.DOLLAR_CASH.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_TICKET', 3, 'Tcket Cash', 'CASHING_TYPE.GENERIC_TICKET.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'MEAL_TICKET', 4, 'Meal Ticket Cash', 'CASHING_TYPE.MEAL_TICKET.4', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'HOLIDAYS_TICKET', 5, 'Holidays Ticket Cash', 'CASHING_TYPE.HOLIDAYS_TICKET.5', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CHECK', 6, 'Check Cash', 'CASHING_TYPE.GENERIC_CHECK.6', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'BNP_CHECK', 7, 'BNP Cash', 'CASHING_TYPE.BNP_CHECK.7', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CARD', 8, 'Card Cash', 'CASHING_TYPE.GENERIC_CARD.8', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'VISA_CARD', 9, 'VISA Card Cash', 'CASHING_TYPE.VISA_CARD.9', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'MASTER_CARD', 10, 'Master Card Cash', 'CASHING_TYPE.MASTER_CARD.10', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'UNPAID', 11, 'Unpaid Cash', 'CASHING_TYPE.UNPAID.11', false);

--COMMENT ON TABLE t_table_type IS 'This table is used for table type.';
--COMMENT ON COLUMN t_table_type.tbt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_type.tbt_code_enm_id IS 'This is the code of the table type. This is a unique field. It is a foreign that refers to the t_enum table for type TABLE_TYPE.';
--COMMENT ON COLUMN t_table_type.tbt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_type VALUES(1, 34, false);
INSERT INTO t_table_type VALUES(2, 35, false);

--COMMENT ON TABLE t_restaurant IS 'This table contains the restaurants informations.';
--COMMENT ON COLUMN t_restaurant.res_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_restaurant.res_registration_date IS 'This is the restaurant creation date in the application.';
--COMMENT ON COLUMN t_restaurant.res_reference IS 'This is the restaurant reference in the application.';
--COMMENT ON COLUMN t_restaurant.res_name IS 'This is the restaurant name.';
--COMMENT ON COLUMN t_restaurant.res_address_road IS 'This is the restaurant address road.';
--COMMENT ON COLUMN t_restaurant.res_address_zip IS 'This is the restaurant address zip code.';
--COMMENT ON COLUMN t_restaurant.res_address_city IS 'This is the restaurant address city.';
--COMMENT ON COLUMN t_restaurant.res_phone IS 'This is the restaurant phone number.';
--COMMENT ON COLUMN t_restaurant.res_vat_ref IS 'This is the restaurant V.A.T(Value Added Taxes) reference.';
--COMMENT ON COLUMN t_restaurant.res_visa_ref IS 'This is the restaurant visa reference.';
--COMMENT ON COLUMN t_restaurant.res_triple_DES_key IS 'This is the restaurant triple DES key.';
--COMMENT ON COLUMN t_restaurant.res_vat_by_takeaway IS 'This is used to know if we have to apply the V.A.T(Value Added Taxes) when it is a takeaway table. The default value is true.';
--COMMENT ON COLUMN t_restaurant.res_takeaway_basic_reduction IS 'This is the restaurant reduction for takeaway table we have to apply. This field depends on the field res_takeaway_min_amount_reduction.';
--COMMENT ON COLUMN t_restaurant.res_takeaway_min_amount_reduction IS 'This is the minimum amount value to apply a reduction for takeaway table.';
--COMMENT ON COLUMN t_restaurant.res_specific_round IS 'This is the specific round to apply on all amounts calculations. It is a foreign that refers to the t_enum table for type SPECIFIC_ROUND_CALCULATION.';
--COMMENT ON COLUMN t_restaurant.tbt_id IS 'This is the default table type. It is a foreign that refers to the t_table_type table. It is used to specify the dinner table type which can be EAT_IN, TAKEAWAY, ....';
--COMMENT ON COLUMN t_restaurant.res_deleted IS 'This is used for logical deletion.';
INSERT INTO t_restaurant VALUES(1, '1970-08-15 15:08:19', '10203040506070', 
'Kim-San', '11 allée Clémencet', '93340', 'Le Raincy', '01 43 02 50 90', 
'1234567890', '0987654321', 'F5E4D3C2B1A0',
true, 10, 15, 2, 1, false);
INSERT INTO t_restaurant VALUES(2, '1970-08-15 15:08:19', '10203040506070B0', 
'Kim-San', '11 allée Clémencet', '93340', 'Le Raincy', '01 43 02 50 90', 
'1234567890', '0987654321', 'F5E4D3C2B1A0',
true, 10, 15, 2, 2, false);

--COMMENT ON TABLE t_restaurant_prefix_table IS 'This table is a association table. This table is used to store the list of restaurant table prefix names. These prefix names is used to know that a table is considered as a takeaway table.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_restaurant_prefix_table.res_id IS 'This is a foreign key that refers to t_restaurant. This field and the other tbt_id and rpt_prefix_enm_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.tbt_id IS 'This is a foreign key that refers to t_table_type. This field and the other res_id and rpt_prefix_enm_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_prefix_enm_id IS 'This is a foreign key that refers to t_enum table for type PREFIX_TAKEAWAY_TABLE_NAME. This field and the other res_id and tbt_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_restaurant_prefix_table VALUES(1, 1, 1, 1, false);

--COMMENT ON TABLE t_value_added_tax IS 'This table is used for product value added tax.';
--COMMENT ON COLUMN t_value_added_tax.vat_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_value_added_tax.vat_code_enm_id IS 'This is the code of the value added tax. This is a unique field. It is a foreign that refers to the t_enum table for type VAT.';
--COMMENT ON COLUMN t_value_added_tax.vat_rate IS 'This is rate of value added tax. This is a unique field.';
--COMMENT ON COLUMN t_value_added_tax.vat_deleted IS 'This is used for logical deletion.';
INSERT INTO t_value_added_tax VALUES(1, 32, 19.60, false);
INSERT INTO t_value_added_tax VALUES(2, 33, 5.50, false);

--COMMENT ON TABLE t_restaurant_vat IS 'This table is used for restaurant value added tax. Each restaurant has a list of value added tax.';
--COMMENT ON COLUMN t_restaurant_vat.rvt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_restaurant_vat.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the other vat_id field consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the value added tax. This field and the other res_id field consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_vat.rvt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_restaurant_vat VALUES(1, 1, 1, false);

--COMMENT ON TABLE t_user_role IS 'This table is used for user role.';
--COMMENT ON COLUMN t_user_role.uro_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_role.uro_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the user role code like GLOBAL_ADMINISTRATOR, ADMINISTRATOR ...';
--COMMENT ON COLUMN t_user_role.uro_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_role VALUES(1, 12, false);


--COMMENT ON TABLE t_user IS 'This table is used for user.';
--COMMENT ON COLUMN t_user.usr_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user.usr_name IS 'This is the user name.';
--COMMENT ON COLUMN t_user.usr_forename1 IS 'This is the first forename of the user.';
--COMMENT ON COLUMN t_user.usr_forename2 IS 'This is the second forename of the user.';
--COMMENT ON COLUMN t_user.usr_birthdate IS 'This is the birthdate of the user.';
--COMMENT ON COLUMN t_user.usr_sex IS 'This is the sex of the user.';
--COMMENT ON COLUMN t_user.usr_title_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the user title like MR, MRS, MISS, DR ...';
--COMMENT ON COLUMN t_user.usr_picture IS 'This is the picture of the user.';
--COMMENT ON COLUMN t_user.usr_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user VALUES(1, 'MA', 'Chhui Huy', 'Mathieu', '1970-08-15 15:08:19', true, 28, null, false);

--COMMENT ON TABLE t_user_authentication IS 'This table is used for users authentication.';
--COMMENT ON COLUMN t_user_authentication.aut_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_authentication.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the printing language.';
--COMMENT ON COLUMN t_user_authentication.usr_id IS 'This is a foreign key that refers to t_user. It is used to specify the authenticated user.';
--COMMENT ON COLUMN t_user_authentication.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant of the authenticated user.';
--COMMENT ON COLUMN t_user_authentication.uro_id IS 'This is a foreign key that refers to t_user_role. It is used to specify the role of the authenticated user.';
--COMMENT ON COLUMN t_user_authentication.aut_login IS 'This is the authenticated user login. It is an unique field.';
--COMMENT ON COLUMN t_user_authentication.aut_password IS 'This is the authenticated user password.';
--COMMENT ON COLUMN t_user_authentication.aut_level_pass1 IS 'This is the authenticated user password level 1.';
--COMMENT ON COLUMN t_user_authentication.aut_level_pass2 IS 'This is the authenticated user password level 2.';
--COMMENT ON COLUMN t_user_authentication.aut_level_pass3 IS 'This is the authenticated user password level 3.';
--COMMENT ON COLUMN t_user_authentication.aut_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_authentication VALUES(1, 1, 1, 1, 1, 'mch', 'mch', 'mch1', 'mch2', 'mch3', false);

--COMMENT ON TABLE t_user_restaurant IS 'This table is used to specify that a user has or works in several users.';
--COMMENT ON COLUMN t_user_restaurant.urt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_restaurant.usr_id IS 'This is a foreign key that refers to t_user. It is used to specify the user restaurant. This field and the other res_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_restaurant.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant of the user. This field and the other usr_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_restaurant.urt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_restaurant VALUES(1, 1, 1, false);

--COMMENT ON TABLE t_category IS 'This table is used for product category.';
--COMMENT ON COLUMN t_category.cat_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_category.cat_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product category code like MEAT, FISH ...';
--COMMENT ON COLUMN t_category.cat_deleted IS 'This is used for logical deletion.';
INSERT INTO t_category VALUES(1, 16, false);
INSERT INTO t_category VALUES(2, 27, false);

--COMMENT ON TABLE t_product_special_code IS 'This table is used for product special code.';
--COMMENT ON COLUMN t_product_special_code.psc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_special_code.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant.';
--COMMENT ON COLUMN t_product_special_code.psc_short_code IS 'This is used to specify the short code enter by user.';
--COMMENT ON COLUMN t_product_special_code.psc_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product special code.';
--COMMENT ON COLUMN t_product_special_code.psc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_special_code VALUES(1, 1, '#', 20, false);

--COMMENT ON TABLE t_product_part IS 'This table is used for product part.';
--COMMENT ON COLUMN t_product_part.prp_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_part.prp_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product part: ENTREE, PLAT DE RESISTANCE, DESSERT, CAFE...';
--COMMENT ON COLUMN t_product_part.prp_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_part VALUES(1, 26, false);


--COMMENT ON TABLE t_product IS 'This table is used for product.';
--COMMENT ON COLUMN t_product.pdt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant.';
--COMMENT ON COLUMN t_product.pdt_code IS 'This is product code.';
--COMMENT ON COLUMN t_product.pdt_price IS 'This is product price.';
--COMMENT ON COLUMN t_product.pdt_colorRGB IS 'This is the highlight color product line see table t_order_line. The value is formatted as css color like xxyyzz.';
--COMMENT ON COLUMN t_product.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the product value added tax.';
--COMMENT ON COLUMN t_product.pdt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product VALUES(1, 1, '11', 3.50, null, 1, false);
INSERT INTO t_product VALUES(2, 1, '12', 43.50, null, 1, false);
INSERT INTO t_product VALUES(3, 1, '13', 4.50, null, 1, false);

--COMMENT ON TABLE t_product_language IS 'This table is used for product depending on the specific language.';
--COMMENT ON COLUMN t_product_language.pdl_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_language.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product. This field and the other pdt_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_language.pdl_label IS 'This is the label of the product depending on the language.';
--COMMENT ON COLUMN t_product_language.pdl_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_language (pdt_id, loc_id, pdl_label, pdl_deleted) VALUES(1, 1, 'Nems', false);

--COMMENT ON TABLE t_product_category IS 'This table is used for product depending on the specific categories.';
--COMMENT ON COLUMN t_product_category.pdc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_category.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. This field and the other cat_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_category.cat_id IS 'This is a foreign key that refers to t_category. It is used to specify the category of the product. This field and the other pdt_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_category.pdc_quantity IS 'This is the quantity of the category for the product. For example, if the category is FISH then the quantity is the quantity of fish for this product. This can be used for stock management.';
--COMMENT ON COLUMN t_product_category.pdc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_category (pdt_id, cat_id, pdc_quantity, pdc_deleted) VALUES(1, 1, 1.9, false);

--COMMENT ON TABLE t_product_sold IS 'This table is used for reporting of sold product.';
--COMMENT ON COLUMN t_product_sold.pds_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_sold.pds_sold_year IS 'This is the sold year of the product. This field and the others pdt_id, pds_sold_month, and pds_sold_day consist of a unique field.';
--COMMENT ON COLUMN t_product_sold.pds_sold_month IS 'This is the sold month of the product. This field and the others pdt_id, pds_sold_year, and pds_sold_day consist of a unique field.';
--COMMENT ON COLUMN t_product_sold.pds_sold_day IS 'This is the sold day of the product. This field and the others pdt_id, pds_sold_year, and pds_sold_month consist of a unique field.';
--COMMENT ON COLUMN t_product_sold.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the sold product. This field and the others pds_updated_date fields consist of a unique field.';
--COMMENT ON COLUMN t_product_sold.pds_quantity IS 'This is the quantity of the sold product for a specific date.';
--COMMENT ON COLUMN t_product_sold.pds_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_sold VALUES(1, 1970, 8, 15, 1, 2, false);

--COMMENT ON TABLE t_credit IS 'This table is used for restaurant credits. One credit must belong to a restaurant.';
--COMMENT ON COLUMN t_credit.cre_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_credit.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant where the credit belongs. This field and the others cre_reference consist of a unique field.';
--COMMENT ON COLUMN t_credit.cre_reference IS 'This is the reference of the credit. This field and the others res_id consist of a unique field.';
--COMMENT ON COLUMN t_credit.cre_amount IS 'This is the amount of the credit.';
--COMMENT ON COLUMN t_credit.cre_created_date IS 'This is the creation date of the credit.';
--COMMENT ON COLUMN t_credit.cre_closing_date IS 'This is the using date of the credit.';
--COMMENT ON COLUMN t_credit.cre_printed IS 'This is used to know if the bill has already been printed. Be careful to just print this credit once.';
--COMMENT ON COLUMN t_credit.cre_deleted IS 'This is used for logical deletion.';
INSERT INTO t_credit VALUES(1, 1, '123456789', 1.9, '1970-08-15 15:08:19', null, false, false);

--COMMENT ON TABLE t_dinner_table IS 'This table is used for dinner tables.';
--COMMENT ON COLUMN t_dinner_table.dtb_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_dinner_table.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant where the dinner table belongs. Notice that this information is already known with the aut_id field. But this field is used for the unicity of the dinner table. So this field and the others dtb_code and dtb_registration_date consist of a unique field.';
--COMMENT ON COLUMN t_dinner_table.dtb_code IS 'This is the code number of the dinner table. This field and the others res_id and dtb_registration_date consist of a unique field.';
--COMMENT ON COLUMN t_dinner_table.aut_id IS 'This is a foreign key that refers to t_user_authentication. It is used to specify the user authentication that created this dinner table.';
--COMMENT ON COLUMN t_dinner_table.roo_id IS 'This is an id for the room where the dinner table is. It is not currently used.';
--COMMENT ON COLUMN t_dinner_table.dtb_customers_number IS 'This is used to specify the number of customers.';
--COMMENT ON COLUMN t_dinner_table.dtb_quantities_sum IS 'This is used to specify the sum of the order lines quantities. This value could be calculated from order lines table.';
--COMMENT ON COLUMN t_dinner_table.dtb_amounts_sum IS 'This is used to specify the sum of the order lines amounts. This value could be calculated from order lines table.';
--COMMENT ON COLUMN t_dinner_table.dtb_reduction_ratio IS 'This is used to specify the reduction ratio.';
--COMMENT ON COLUMN t_dinner_table.dtb_amount_pay IS 'This is used to specify the amount to pay. This value could be calculated with value of dtb_reduction_ratio and dtb_amounts_sum. amountPay = dtb_amounts_sum-dtb_amounts_sum*dtb_reduction_ratio/100.';
--COMMENT ON COLUMN t_dinner_table.dtb_registration_date IS 'This is used to specify the registration/creation date. This field and the others res_id and dtb_code consist of a unique field.';
--COMMENT ON COLUMN t_dinner_table.dtb_printing_date IS 'This is used to specify the printing date.';
--COMMENT ON COLUMN t_dinner_table.dtb_reduction_ratio_changed IS 'This is used to specify if user has changed the reduction ratio.';
--COMMENT ON COLUMN t_dinner_table.tbt_id IS 'This is used to specify the type of dinner table. Could be TAKE-AWAY, EAT-IN ... This is a foreign key that refers to t_table_type.';
--COMMENT ON COLUMN t_dinner_table.dtb_deleted IS 'This is used for logical deletion.';
INSERT INTO t_dinner_table VALUES(1, 1, '12', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:19', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(2, 1, '12', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:20', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(3, 1, '123', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:21', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(4, 2, '123', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:22', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(5, 2, '124', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:23', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(6, 1, '23', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:21', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(7, 2, '24', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:22', null, false, 1, false);
INSERT INTO t_dinner_table VALUES(8, 2, '25', 1, null, 2, 3, 23, 0, 23, '1970-08-15 15:08:23', null, false, 1, false);

--COMMENT ON TABLE t_order_line IS 'This table is used for order lines depending on the specific dinner table.';
--COMMENT ON COLUMN t_order_line.orl_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_order_line.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table.';
--COMMENT ON COLUMN t_order_line.psc_id IS 'This is a foreign key that refers to t_product_special_code. It is used to specify the product special code. This code is never null, it takes a default value with "". The code could be for example "/", "-", "#", "@".';
--COMMENT ON COLUMN t_order_line.pdt_id IS 'This is a foreign key that refers to t_product. It is used to specify the product. If this field is null then the order line depends on the product special code psc_id which must not be null.';
--COMMENT ON COLUMN t_order_line.cre_id IS 'This is a foreign key that refers to t_credit. It is used to specify the credit consumed. If this field is NOT null then the order line depends on the product special code psc_id which must not be null with code value equals to @.';
--COMMENT ON COLUMN t_order_line.prp_id IS 'This is a foreign key that refers to t_product_part. It is used to specify the product part the order line belongs: ENTREE, PLAT or DESSERT for example.';
--COMMENT ON COLUMN t_order_line.orl_quantity IS 'This is the quantity of the product.';
--COMMENT ON COLUMN t_order_line.orl_label IS 'This is the label of the product. If the psc_id is of type "/" then the label is the user entry description. If the psc_id is null then the label is the label of the product pdt_id depending on the user locale.';
--COMMENT ON COLUMN t_order_line.orl_unit_price IS 'This is the unit price of the order line. Here, we do not take into account the quantity.';
--COMMENT ON COLUMN t_order_line.orl_amount IS 'This is the amount of the order line. The value is equals to orl_quantity multiply by orl_unit_price.';
--COMMENT ON COLUMN t_order_line.orl_deleted IS 'This is used for logical deletion.';
INSERT INTO t_order_line VALUES(1, 1, 1, 1, 1, 1, 2.5, 'Nems', 5.4, 13.5, false);
INSERT INTO t_order_line VALUES(2, 1, 1, 1, 1, 1, 5, 'Nems', 5, 25, false);

--COMMENT ON TABLE t_table_credit IS 'This table is used for dinner table credits. This table is used for credits dinner tables association. For a given dinner table, we could have several credits but often just one. These credits must have the cre_closing_date value equals to null.';
--COMMENT ON COLUMN t_table_credit.tcr_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_credit.cre_id IS 'This is a foreign key that refers to t_credit. It is used to specify the credit of the dinner table. This field and the others dtb_id consist of a unique field.';
--COMMENT ON COLUMN t_table_credit.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the others cre_id consist of a unique field.';
--COMMENT ON COLUMN t_table_credit.tcr_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_credit VALUES(1, 1, 1, false);

--COMMENT ON TABLE t_table_bill IS 'This table is used for bills of dinner table. There are several bills for a specific dinner table.';
--COMMENT ON COLUMN t_table_bill.tbi_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_bill.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the other tbi_reference, tbi_order fields consist of a unique field.';
--COMMENT ON COLUMN t_table_bill.tbi_reference IS 'This is a the bill reference for authentication checking. This field and the other dtb_id, tbi_order fields consist of a unique field.';
--COMMENT ON COLUMN t_table_bill.tbi_order IS 'This is a the bill order. We can have several bill for a specific table. So this field is used to increment the bill table number for printing information. This field and the other dtb_id, tbi_reference fields consist of a unique field.';
--COMMENT ON COLUMN t_table_bill.tbi_meal_number IS 'This is the bill meal number.';
--COMMENT ON COLUMN t_table_bill.tbi_amount IS 'This is the bill amount.';
--COMMENT ON COLUMN t_table_bill.tbi_printed IS 'This is used to know if the bill has already been printed.';
--COMMENT ON COLUMN t_table_bill.tbi_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_bill VALUES(1, 1, 'reference1', 1, 2, 3, false, false);


--COMMENT ON TABLE t_table_vat IS 'This table is used for amounts and values of dinner table depending on the Value Added Tax.';
--COMMENT ON COLUMN t_table_vat.tvt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_vat.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. This field and the other vat_id field consist of a unique field.';
--COMMENT ON COLUMN t_table_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the Value Added Tax. This field and the other dtb_id field consist of a unique field.';
--COMMENT ON COLUMN t_table_vat.tvt_amount IS 'This is the amount of the dinner table depending on the specific Value Added Tax. If the dinner table has order lines with Value Added Tax rate of 5.5, then the amount is the sum of the amount of dinner table order lines with the specific Value Added Tax rate 5.5.';
--COMMENT ON COLUMN t_table_vat.tvt_value IS 'This is the value of the dinner table depending on the specific Value Added Tax. If the Value Added Tax rate is 19.6 then the value is equals to tvt_amount*19.6./(19.6+100).';
--COMMENT ON COLUMN t_table_vat.tvt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_vat VALUES(1, 1, 1, 1, 2, false);


--COMMENT ON TABLE t_table_cashing IS 'This table is used for cashing of dinner table.';
--COMMENT ON COLUMN t_table_cashing.tcs_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_cashing.dtb_id IS 'This is a foreign key that refers to t_dinner_table. It is used to specify the dinner table. There is only one dinner table for one cashing. This field consist of a unique field.';
--COMMENT ON COLUMN t_table_cashing.tcs_cashing_date IS 'This is the date of the dinner table cashing.';
--COMMENT ON COLUMN t_table_cashing.tcs_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_cashing VALUES(1, 2, '1970-08-15', false);
INSERT INTO t_table_cashing VALUES(2, 3, '1970-08-15', false);
INSERT INTO t_table_cashing VALUES(3, 5, '1970-08-15', false);

--COMMENT ON TABLE t_cashing_type IS 'This table is used for cashing of dinner table depending on type of cashing.';
--COMMENT ON COLUMN t_cashing_type.cst_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_cashing_type.tcs_id IS 'This is a foreign key that refers to t_table_cashing. It is used to specify the cashed table. This field and the other cst_type_enum_id fields consist of a unique field.';
--COMMENT ON COLUMN t_cashing_type.cst_type_enum_id IS 'This is a foreign key that refers to t_enum. It is used to specify the type of cashing. It could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID... This field and the other tcs_id fields consist of a unique field.';
--COMMENT ON COLUMN t_cashing_type.cst_amount IS 'This is the amount of the dinner table depending on the specific type of cashing.';
--COMMENT ON COLUMN t_cashing_type.cst_deleted IS 'This is used for logical deletion.';
INSERT INTO t_cashing_type VALUES(1, 1, 35, 115.12, false);


--COMMENT ON TABLE t_revenue IS 'This table is used for reporting day revenue depending on the type of table.';
--COMMENT ON COLUMN t_revenue.rev_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_revenue.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the others rev_revenue_date and tbt_id fields consist of a unique field.';
--COMMENT ON COLUMN t_revenue.rev_revenue_date IS 'This is the value of the dinner table depending on the specific type of cashing. This field and the others tbt_id and res_id fields consist of a unique field.';
--COMMENT ON COLUMN t_revenue.tbt_id IS 'This is a foreign key that refers to t_table_type. It is used to specify the type of table. It could be for instance TAKE AWAY or EAT IN. This field and the others rev_revenue_date and res_id fields consist of a unique field.';
--COMMENT ON COLUMN t_revenue.rev_printing_date IS 'This is the printing date of the day revenue depending on the type of table.';
--COMMENT ON COLUMN t_revenue.rev_closing_date IS 'This is the closing date of the day revenue depending on the type of table.';
--COMMENT ON COLUMN t_revenue.rev_amount IS 'This is the amount of the day revenue depending on the type of table.';
--COMMENT ON COLUMN t_revenue.rev_deleted IS 'This is used for logical deletion.';
INSERT INTO t_revenue VALUES(1, 1, '1970-08-15', 1, null, null, 345.6789, false);
INSERT INTO t_revenue VALUES(2, 1, '1970-08-16', 1, null, null, 987.6543, false);

-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
--COMMENT ON TABLE t_revenue_cashing IS 'This table is used for cashing revenue depending on type of cashing.';
--COMMENT ON COLUMN t_revenue_cashing.rvc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_revenue_cashing.rev_id IS 'This is a foreign key that refers to t_revenue. It is used to specify the revenue. This field and the other rvc_type_enum_id field consist of a unique field.';
--COMMENT ON COLUMN t_revenue_cashing.rvc_type_enum_id IS 'This is a foreign key that refers to t_enum. It is used to specify the type of cashing. It could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID... This field and the other rev_id field consist of a unique field.';
--COMMENT ON COLUMN t_revenue_cashing.rvc_amount IS 'This is the amount of the revenue depending on the specific type of cashing.';
--COMMENT ON COLUMN t_revenue_cashing.rvc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_revenue_cashing VALUES(1, 1, 35, 115.12, false);

-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
--COMMENT ON TABLE t_revenue_vat IS 'This table is used for amounts and values of revenue depending on the Value Added Tax.';
--COMMENT ON COLUMN t_revenue_vat.rva_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_revenue_vat.rev_id IS 'This is a foreign key that refers to t_revenue. It is used to specify the revenue. This field and the other vat_id field consist of a unique field.';
--COMMENT ON COLUMN t_revenue_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the Value Added Tax. This field and the other rev_id field consist of a unique field.';
--COMMENT ON COLUMN t_revenue_vat.rva_amount IS 'This is the amount of the revenue depending on the specific Value Added Tax. The amount revenue is the sum of the amount of dinner table order lines with the specific Value Added Tax rate 5.5 for specific day.';
--COMMENT ON COLUMN t_revenue_vat.rva_value IS 'This is the value of the revenue depending on the specific Value Added Tax. If the Value Added Tax rate is 19.6 then the value is equals to tvt_amount*19.6./(19.6+100).';
--COMMENT ON COLUMN t_revenue_vat.rva_deleted IS 'This is used for logical deletion.';
INSERT INTO t_revenue_vat VALUES(1, 1, 1, 123.456, 23.5, false);

-- COMMENT Statement is used for PostGresql but this is also compatible with HSQLDB 2.0.
--COMMENT ON TABLE t_printing_information IS 'This table is used for printing custom informations on specific restaurant.';
--COMMENT ON COLUMN t_printing_information.pin_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_printing_information.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the other enm_id field consist of a unique field.';
--COMMENT ON COLUMN t_printing_information.pin_order IS 'It is used to specify the order of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_alignment_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the alignment of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_size_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the size of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_part_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the part of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_deleted IS 'This is used for logical deletion.';
INSERT INTO t_printing_information VALUES(1, 1, 1, 4, 7, 10, false);
INSERT INTO t_printing_information VALUES(2, 1, 2, 5, 8, 11, false);
