DELETE FROM t_revenue_vat;
DELETE FROM t_revenue_cashing;
DELETE FROM t_revenue;
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

--COMMENT ON TABLE t_enum IS 'This table is used for enumeration type for other tables.';
--COMMENT ON COLUMN t_enum.enm_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_enum.enm_type IS 'This is the type of the enum. This field consists with the enm_name an unique key.';
--COMMENT ON COLUMN t_enum.enm_name IS 'This is the name of the enum. This field consists with the enm_type an unique key.';
--COMMENT ON COLUMN t_enum.enm_order IS 'This is the order of the enum for a specific enum type.';
--COMMENT ON COLUMN t_enum.enm_default_label IS 'This field is a default label to display to user.';
--COMMENT ON COLUMN t_enum.enm_language_key_label IS 'This field is used for java i18n. We can map this field with java properties files as a properties key in order to find the label value.';
--COMMENT ON COLUMN t_enum.enm_deleted IS 'This is used for logical deletion.';
-- Step 1
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('VALUE_ADDED_TAX', 'DEFAULT', 0, '5.50', 'VALUE_ADDED_TAX.DEFAULT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('VALUE_ADDED_TAX', 'ALCOHOL', 1, '19.60', 'VALUE_ADDED_TAX.ALCOHOL.1', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('TABLE_TYPE', 'EAT_IN', 0, 'Eat in', 'TABLE_TYPE.EAT_IN.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('TABLE_TYPE', 'TAKE_AWAY', 1, 'Take away', 'TABLE_TYPE.TAKE_AWAY.1', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('SPECIFIC_ROUND', 'HALF_ROUND', 0, 'HALF_ROUND', 'SPECIFIC_ROUND.HALF_ROUND.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('SPECIFIC_ROUND', 'TENTH_ROUND', 1, 'TENTH_ROUND', 'SPECIFIC_ROUND.TENTH_ROUND.1', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PREFIX_TABLE_NAME', 'E', 0, 'E', 'PREFIX_TABLE_NAME.E.0', false);

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
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_ROLE', 'GUEST', 4, 'Guest', 'USER_ROLE.GUEST.4', false);

-- Step 2.3.1
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_TITLE', 'MISTER', 0, 'Mister', 'USER_TITLE.MISTER.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('USER_TITLE', 'MISS', 1, 'Miss', 'USER_TITLE.MISS.1', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'MEAT', 0, 'Meat','CATEGORY.MEAT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'FISH', 1, 'Fish','CATEGORY.FISH.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'WATER', 2, 'Water','CATEGORY.WATER.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'WINE', 3, 'Wine','CATEGORY.WINE.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'WHITE_WINE', 4, 'White Wine','CATEGORY.WHITE_WINE.4', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'RED_WINE', 5, 'Red Wine','CATEGORY.RED_WINE.5', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'STEAM', 6, 'Steam','CATEGORY.STEAM.6', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'SHELLFISH', 7, 'Shellfish','CATEGORY.SHELLFISH.7', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'RICE', 8, 'Rice','CATEGORY.RICE.8', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'POULTRY', 9, 'Poultry','CATEGORY.POULTRY.9', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'ALCOHOL', 10, 'Alcohol','CATEGORY.ALCOHOL.10', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'COFFEE', 11, 'Coffee','CATEGORY.COFFEE.11', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'TEA', 12, 'Tea','CATEGORY.TEA.12', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'SODA', 13, 'Soda','CATEGORY.SODA.13', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'JUICE', 14, 'Juice','CATEGORY.JUICE.14', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'BEER', 15, 'Beer','CATEGORY.BEER.15', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'FRUIT', 16, 'Fruit','CATEGORY.FRUIT.16', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'VEGETABLE', 17, 'Vegetable','CATEGORY.VEGETABLE.17', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'ICE_CREAM', 18, 'Ice Cream','CATEGORY.ICE_CREAM.18', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CATEGORY', 'INFUSION', 19, 'Ice Cream','CATEGORY.INFUSION.19', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'DEFAULT', 0, 'Default product', 'PRODUCT_SPECIAL_CODE.DEFAULT.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'OFFERED_PRODUCT', 1, 'Offered product', 'PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'DISCOUNT_ORDER', 2, 'Reduced order', 'PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'USER_ORDER', 3, 'User order', 'PRODUCT_SPECIAL_CODE.USER_ORDER.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_SPECIAL_CODE', 'CREDIT', 4, 'Credit', 'PRODUCT_SPECIAL_CODE.CREDIT.4', false);

INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'MISCELLANEOUS', 0, 'Miscellaneous', 'PRODUCT_PART.MISCELLANEOUS.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'APERITIF', 1, 'Aperitif', 'PRODUCT_PART.APERITIF.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'APPETIZER', 2, 'Appetizer', 'PRODUCT_PART.APPETIZER.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'MAIN_COURSE', 3, 'Main Course', 'PRODUCT_PART.MAIN_COURSE.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'DESSERT', 4, 'Dessert', 'PRODUCT_PART.DESSERT.4', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('PRODUCT_PART', 'DIGESTIF', 5, 'Digestif', 'PRODUCT_PART.DIGESTIF.5', false);


INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CASH', 0, 'Generic Cash', 'CASHING_TYPE.GENERIC_CASH.0', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_TICKET', 1, 'Tcket Cash', 'CASHING_TYPE.GENERIC_TICKET.1', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CHECK', 2, 'Check Cash', 'CASHING_TYPE.GENERIC_CHECK.2', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'GENERIC_CARD', 3, 'Card Cash', 'CASHING_TYPE.GENERIC_CARD.3', false);
INSERT INTO t_enum (enm_type, enm_name, enm_order, enm_default_label, enm_language_key_label, enm_deleted) VALUES('CASHING_TYPE', 'UNPAID', 4, 'Unpaid Cash', 'CASHING_TYPE.UNPAID.4', false);



--COMMENT ON TABLE t_value_added_tax IS 'This table is used for product value added tax.';
--COMMENT ON COLUMN t_value_added_tax.vat_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_value_added_tax.vat_code_enm_id IS 'This is the code of the value added tax. This is a unique field. It is a foreign that refers to the t_enum table for type VAT.';
--COMMENT ON COLUMN t_value_added_tax.vat_rate IS 'This is rate of value added tax. This is a unique field.';
--COMMENT ON COLUMN t_value_added_tax.vat_deleted IS 'This is used for logical deletion.';
INSERT INTO t_value_added_tax (vat_code_enm_id, vat_rate, vat_deleted) SELECT enm_id, 5.50, false FROM t_enum WHERE enm_language_key_label='VALUE_ADDED_TAX.DEFAULT.0';
INSERT INTO t_value_added_tax (vat_code_enm_id, vat_rate, vat_deleted) SELECT enm_id, 19.60, false FROM t_enum WHERE enm_language_key_label='VALUE_ADDED_TAX.ALCOHOL.1';


-- Step 2.1
--COMMENT ON TABLE t_table_type IS 'This table is used for table type.';
--COMMENT ON COLUMN t_table_type.tbt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_table_type.tbt_code_enm_id IS 'This is the code of the table type. This is a unique field. It is a foreign that refers to the t_enum table for type TABLE_TYPE.';
--COMMENT ON COLUMN t_table_type.tbt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_table_type (tbt_code_enm_id, tbt_deleted) SELECT enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='TABLE_TYPE.EAT_IN.0';
INSERT INTO t_table_type (tbt_code_enm_id, tbt_deleted) SELECT enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='TABLE_TYPE.TAKE_AWAY.1';


-- Step 2.2
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
INSERT INTO t_restaurant (res_registration_date, res_reference, res_name, res_address_road, res_address_zip, res_address_city, res_phone, res_vat_ref, res_visa_ref, res_triple_DES_key, res_vat_by_takeaway, res_takeaway_basic_reduction, res_takeaway_min_amount_reduction, res_specific_round, tbt_id, res_deleted) 
SELECT CURRENT_TIMESTAMP, '10203040506070', 'Kim-San', '11 allée Clémencet', '93340', 'Le Raincy', '01 43 02 50 90', 'FR 19 313 105 397 000 19', '313 105 397', 'F5E4D3C2B1A0', true, 10, 15, 
t_enum.enm_id, t_table_type.tbt_id, false 
FROM t_enum, t_table_type JOIN t_enum enm_table_type ON enm_table_type.enm_id = t_table_type.tbt_code_enm_id  
WHERE t_enum.enm_language_key_label='SPECIFIC_ROUND.HALF_ROUND.0'
AND enm_table_type.enm_language_key_label='TABLE_TYPE.EAT_IN.0';

--COMMENT ON TABLE t_restaurant_prefix_table IS 'This table is a association table. This table is used to store the list of restaurant table prefix names. These prefix names is used to know that a table is considered as a takeaway table.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_restaurant_prefix_table.res_id IS 'This is a foreign key that refers to t_restaurant. This field and the other tbt_id and rpt_prefix_enm_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.tbt_id IS 'This is a foreign key that refers to t_table_type. This field and the other res_id and rpt_prefix_enm_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_prefix_enm_id IS 'This is a foreign key that refers to t_enum table for type PREFIX_TAKEAWAY_TABLE_NAME. This field and the other res_id and tbt_id fields consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_prefix_table.rpt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_restaurant_prefix_table (res_id, tbt_id, rpt_prefix_enm_id, rpt_deleted) SELECT t_restaurant.res_id, t_table_type.tbt_id, enum1.enm_id, false FROM t_restaurant, t_table_type JOIN t_enum enum1 ON enum1.enm_id = t_table_type.tbt_code_enm_id, t_enum enum2 WHERE t_restaurant.res_reference = '10203040506070' AND enum1.enm_language_key_label = 'TABLE_TYPE.TAKE_AWAY.1' AND enum2.enm_language_key_label = 'PREFIX_TABLE_NAME.E.0';


--COMMENT ON TABLE t_restaurant_vat IS 'This table is used for restaurant value added tax. Each restaurant has a list of value added tax.';
--COMMENT ON COLUMN t_restaurant_vat.rvt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_restaurant_vat.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant. This field and the other vat_id field consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_vat.vat_id IS 'This is a foreign key that refers to t_value_added_tax. It is used to specify the value added tax. This field and the other res_id field consist of a unique field.';
--COMMENT ON COLUMN t_restaurant_vat.rvt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_restaurant_vat (res_id, vat_id, rvt_deleted) SELECT t_restaurant.res_id, t_value_added_tax.vat_id, false FROM t_value_added_tax JOIN t_enum ON t_enum.enm_id = t_value_added_tax.vat_code_enm_id, t_restaurant WHERE enm_language_key_label='VALUE_ADDED_TAX.DEFAULT.0' AND t_restaurant.res_reference = '10203040506070';
INSERT INTO t_restaurant_vat (res_id, vat_id, rvt_deleted) SELECT t_restaurant.res_id, t_value_added_tax.vat_id, false FROM t_value_added_tax JOIN t_enum ON t_enum.enm_id = t_value_added_tax.vat_code_enm_id, t_restaurant WHERE enm_language_key_label='VALUE_ADDED_TAX.ALCOHOL.1' AND t_restaurant.res_reference = '10203040506070';

--COMMENT ON TABLE t_locale IS 'This table is used for i18n.';
--COMMENT ON COLUMN t_locale.loc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_locale.loc_language IS 'This is language ISO code.';
--COMMENT ON COLUMN t_locale.loc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_locale (loc_language, loc_deleted) VALUES('fr', false);
INSERT INTO t_locale (loc_language, loc_deleted) VALUES('en', false);
INSERT INTO t_locale (loc_language, loc_deleted) VALUES('zh', false);

--COMMENT ON TABLE t_printing_information IS 'This table is used for printing custom informations on specific restaurant.';
--COMMENT ON COLUMN t_printing_information.pin_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_printing_information.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant.';
--COMMENT ON COLUMN t_printing_information.pin_order IS 'It is used to specify the order of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_alignment_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the alignment of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_size_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the size of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_part_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the part of the printing information.';
--COMMENT ON COLUMN t_printing_information.pin_deleted IS 'This is used for logical deletion.';
INSERT INTO t_printing_information (res_id, pin_order, pin_alignment_enm_id, pin_size_enm_id, pin_part_enm_id, pin_deleted) 
SELECT t_restaurant.res_id, 0, PRINTING_INFORMATION_ALIGNMENT.enm_id, PRINTING_INFORMATION_SIZE.enm_id, PRINTING_INFORMATION_PART.enm_id, false 
FROM t_restaurant, t_enum AS PRINTING_INFORMATION_ALIGNMENT, t_enum AS PRINTING_INFORMATION_SIZE, t_enum AS PRINTING_INFORMATION_PART 
WHERE t_restaurant.res_reference = '10203040506070' 
AND PRINTING_INFORMATION_ALIGNMENT.enm_language_key_label='PRINTING_INFORMATION_ALIGNMENT.CENTER.1' 
AND PRINTING_INFORMATION_SIZE.enm_language_key_label='PRINTING_INFORMATION_SIZE.SMALL.0' 
AND PRINTING_INFORMATION_PART.enm_language_key_label='PRINTING_INFORMATION_PART.FOOTER.1';

--COMMENT ON TABLE t_printing_information_language IS 'This table is used for printing custom informations on specific restaurant depending on the specific language.';
--COMMENT ON COLUMN t_printing_information_language.pil_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_printing_information_language.pin_id IS 'This is a foreign key that refers to t_printing_information. It is used to specify the printing information on specific restaurant. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_printing_information_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the printing information. This field and the other pin_id field consist of a unique field.';
--COMMENT ON COLUMN t_printing_information_language.pil_label IS 'This is the label of the printing information depending on the language.';
--COMMENT ON COLUMN t_printing_information_language.pil_deleted IS 'This is used for logical deletion.';
INSERT INTO t_printing_information_language (pin_id, loc_id, pil_label, pil_deleted) 
SELECT t_printing_information.pin_id, t_locale.loc_id, 
'Merci de votre visite', false 
FROM t_locale, 
t_printing_information JOIN t_restaurant ON t_restaurant.res_id = t_printing_information.res_id
JOIN t_enum pin_alignment_enm ON pin_alignment_enm.enm_id = t_printing_information.pin_alignment_enm_id
JOIN t_enum pin_size_enm ON pin_size_enm.enm_id = t_printing_information.pin_size_enm_id
JOIN t_enum pin_part_enm ON pin_part_enm.enm_id = t_printing_information.pin_part_enm_id
WHERE t_locale.loc_language='fr' 
AND t_restaurant.res_reference='10203040506070'
AND t_printing_information.pin_order=0
AND pin_alignment_enm.enm_language_key_label='PRINTING_INFORMATION_ALIGNMENT.CENTER.1'
AND pin_size_enm.enm_language_key_label='PRINTING_INFORMATION_SIZE.SMALL.0'
AND pin_part_enm.enm_language_key_label='PRINTING_INFORMATION_PART.FOOTER.1';
INSERT INTO t_printing_information_language (pin_id, loc_id, pil_label, pil_deleted) 
SELECT t_printing_information.pin_id, t_locale.loc_id, 
'Thanks for you visit', false 
FROM t_locale, 
t_printing_information JOIN t_restaurant ON t_restaurant.res_id = t_printing_information.res_id
JOIN t_enum pin_alignment_enm ON pin_alignment_enm.enm_id = t_printing_information.pin_alignment_enm_id
JOIN t_enum pin_size_enm ON pin_size_enm.enm_id = t_printing_information.pin_size_enm_id
JOIN t_enum pin_part_enm ON pin_part_enm.enm_id = t_printing_information.pin_part_enm_id
WHERE t_locale.loc_language='en' 
AND t_restaurant.res_reference='10203040506070'
AND t_printing_information.pin_order=0
AND pin_alignment_enm.enm_language_key_label='PRINTING_INFORMATION_ALIGNMENT.CENTER.1'
AND pin_size_enm.enm_language_key_label='PRINTING_INFORMATION_SIZE.SMALL.0'
AND pin_part_enm.enm_language_key_label='PRINTING_INFORMATION_PART.FOOTER.1';

--COMMENT ON TABLE t_user_role IS 'This table is used for user role.';
--COMMENT ON COLUMN t_user_role.uro_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_role.uro_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the user role code like GLOBAL_ADMINISTRATOR, ADMINISTRATOR ...';
--COMMENT ON COLUMN t_user_role.uro_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_role (uro_code_enm_id, uro_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_ROLE.GLOBAL_ADMINISTRATOR.0';
INSERT INTO t_user_role (uro_code_enm_id, uro_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_ROLE.ADMINISTRATOR.1';
INSERT INTO t_user_role (uro_code_enm_id, uro_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_ROLE.EMPLOYEE.2';
INSERT INTO t_user_role (uro_code_enm_id, uro_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_ROLE.CUSTOMER.3';
INSERT INTO t_user_role (uro_code_enm_id, uro_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_ROLE.GUEST.4';

--COMMENT ON TABLE t_user_role_language IS 'This table is used for user role depending on the specific language.';
--COMMENT ON COLUMN t_user_role_language.url_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_role_language.uro_id IS 'This is a foreign key that refers to t_user_role. It is used to specify the user role. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_role_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the user role. This field and the other uro_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_role_language.url_label IS 'This is the label of the user role depending on the language.';
--COMMENT ON COLUMN t_user_role_language.url_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Administrateur Global', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='USER_ROLE.GLOBAL_ADMINISTRATOR.0';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Global Administrator', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='USER_ROLE.GLOBAL_ADMINISTRATOR.0';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Administrateur', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='USER_ROLE.ADMINISTRATOR.1';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Administrator', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='USER_ROLE.ADMINISTRATOR.1';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Employé(e)', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='USER_ROLE.EMPLOYEE.2';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Employee', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='USER_ROLE.EMPLOYEE.2';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Client', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='USER_ROLE.CUSTOMER.3';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Customer', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='USER_ROLE.CUSTOMER.3';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Invité(e)', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='USER_ROLE.GUEST.4';
INSERT INTO t_user_role_language (uro_id, loc_id, url_label, url_deleted) SELECT t_user_role.uro_id, t_locale.loc_id, 'Guest', false FROM t_locale, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='USER_ROLE.GUEST.4';

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
INSERT INTO t_user (usr_name, usr_forename1, usr_forename2, usr_birthdate, usr_sex, usr_title_enm_id, usr_picture, usr_deleted) SELECT 'MA', 'Chhui Huy', 'Mathieu', '1970-08-15 15:08:19', true, t_enum.enm_id, null, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_TITLE.MISTER.0';
INSERT INTO t_user (usr_name, usr_forename1, usr_forename2, usr_birthdate, usr_sex, usr_title_enm_id, usr_picture, usr_deleted) SELECT 'MA', 'Sui Tao', 'Edouard', '1968-10-04 04:10:19', true, t_enum.enm_id, null, false FROM t_enum WHERE t_enum.enm_language_key_label='USER_TITLE.MISTER.0';

--COMMENT ON TABLE t_user_restaurant IS 'This table is used to specify that a user has or works in several users.';
--COMMENT ON COLUMN t_user_restaurant.urt_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_restaurant.usr_id IS 'This is a foreign key that refers to t_user. It is used to specify the user restaurant. This field and the other res_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_restaurant.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant of the user. This field and the other usr_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_restaurant.urt_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_restaurant (usr_id, res_id, urt_deleted) SELECT t_user.usr_id, t_restaurant.res_id, false FROM t_user, t_restaurant WHERE t_user.usr_name='MA' AND t_user.usr_forename1='Chhui Huy' AND t_restaurant.res_reference = '10203040506070';
INSERT INTO t_user_restaurant (usr_id, res_id, urt_deleted) SELECT t_user.usr_id, t_restaurant.res_id, false FROM t_user, t_restaurant WHERE t_user.usr_name='MA' AND t_user.usr_forename1='Sui Tao' AND t_restaurant.res_reference = '10203040506070';


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
INSERT INTO t_user_authentication (loc_id, usr_id, res_id, uro_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3, aut_deleted) SELECT t_locale.loc_id, t_user.usr_id, t_restaurant.res_id, t_user_role.uro_id, 'mch', 'mch', 'mch1', 'mch2', 'mch3', false
FROM t_locale, t_user, t_restaurant, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id
WHERE t_locale.loc_language='fr' AND t_user.usr_name='MA' AND t_user.usr_forename1='Chhui Huy' AND t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='USER_ROLE.GLOBAL_ADMINISTRATOR.0';
INSERT INTO t_user_authentication (loc_id, usr_id, res_id, uro_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3, aut_deleted) SELECT t_locale.loc_id, t_user.usr_id, t_restaurant.res_id, t_user_role.uro_id, 'mst', 'mst', 'mst1', 'mst2', 'mst3', false
FROM t_locale, t_user, t_restaurant, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id
WHERE t_locale.loc_language='fr' AND t_user.usr_name='MA' AND t_user.usr_forename1='Sui Tao' AND t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='USER_ROLE.ADMINISTRATOR.1';
INSERT INTO t_user_authentication (loc_id, usr_id, res_id, uro_id, aut_login, aut_password, aut_level_pass1, aut_level_pass2, aut_level_pass3, aut_deleted) SELECT t_locale.loc_id, t_user.usr_id, t_restaurant.res_id, t_user_role.uro_id, 'kimsan', 'kimsan', 'kimsan1', 'kimsan2', 'kimsan3', false
FROM t_locale, t_user, t_restaurant, t_user_role JOIN t_enum ON t_enum.enm_id = t_user_role.uro_code_enm_id
WHERE t_locale.loc_language='fr' AND t_user.usr_name='MA' AND t_user.usr_forename1='Sui Tao' AND t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='USER_ROLE.ADMINISTRATOR.1';

--COMMENT ON TABLE t_user_locale IS 'This table is used to specify the authenticated user locales.';
--COMMENT ON COLUMN t_user_locale.ulo_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_user_locale.aut_id IS 'This is a foreign key that refers to t_user_authentication. It is used to specify the authenticated user. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_locale.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the authenticated user locale. This field and the other aut_id field consist of a unique field.';
--COMMENT ON COLUMN t_user_locale.ulo_deleted IS 'This is used for logical deletion.';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='fr' AND t_user_authentication.aut_login='mch';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='en' AND t_user_authentication.aut_login='mch';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='zh' AND t_user_authentication.aut_login='mch';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='fr' AND t_user_authentication.aut_login='mst';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='en' AND t_user_authentication.aut_login='mst';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='zh' AND t_user_authentication.aut_login='mst';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='fr' AND t_user_authentication.aut_login='kimsan';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='en' AND t_user_authentication.aut_login='kimsan';
INSERT INTO t_user_locale (aut_id, loc_id, ulo_deleted) SELECT t_user_authentication.aut_id, t_locale.loc_id, false FROM t_locale, t_user_authentication WHERE t_locale.loc_language='zh' AND t_user_authentication.aut_login='kimsan';


--COMMENT ON TABLE t_category IS 'This table is used for product category.';
--COMMENT ON COLUMN t_category.cat_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_category.cat_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product category code like MEAT, FISH ...';
--COMMENT ON COLUMN t_category.cat_deleted IS 'This is used for logical deletion.';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.MEAT.0';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.FISH.1';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.WATER.2';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.WINE.3';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.WHITE_WINE.4';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.RED_WINE.5';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.STEAM.6';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.SHELLFISH.7';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.RICE.8';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.POULTRY.9';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.ALCOHOL.10';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.COFFEE.11';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.TEA.12';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.SODA.13';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.JUICE.14';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.BEER.15';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.FRUIT.16';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.VEGETABLE.17';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.ICE_CREAM.18';
INSERT INTO t_category (cat_code_enm_id, cat_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='CATEGORY.INFUSION.19';

--COMMENT ON TABLE t_category_language IS 'This table is used for product category depending on the specific language.';
--COMMENT ON COLUMN t_category_language.ctl_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_category_language.cat_id IS 'This is a foreign key that refers to t_category. It is used to specify the product category. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_category_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product category. This field and the other cat_id field consist of a unique field.';
--COMMENT ON COLUMN t_category_language.ctl_label IS 'This is the label of the product category depending on the language.';
--COMMENT ON COLUMN t_category_language.ctl_deleted IS 'This is used for logical deletion.';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Viande', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.MEAT.0';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Poisson', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.FISH.1';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Eau', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.WATER.2';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Vin', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.WINE.3';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Vin Blanc', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.WHITE_WINE.4';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Vin Rouge', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.RED_WINE.5';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Vapeur', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.STEAM.6';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Fruit de Mer', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.SHELLFISH.7';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Riz', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.RICE.8';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Volaille', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.POULTRY.9';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Alcool', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.ALCOHOL.10';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Café', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.COFFEE.11';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Thé', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.TEA.12';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Soda', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.SODA.13';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Jus', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.JUICE.14';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Bière', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.BEER.15';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Fruit', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.FRUIT.16';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Légume', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.VEGETABLE.17';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Glace', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.ICE_CREAM.18';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Infusion', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='CATEGORY.INFUSION.19';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Meat', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.MEAT.0';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Fish', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.FISH.1';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Water', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.WATER.2';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Wine', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.WINE.3';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'White Wine', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.WHITE_WINE.4';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Red Wine', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.RED_WINE.5';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Steam', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.STEAM.6';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Shellfish', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.SHELLFISH.7';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Rice', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.RICE.8';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Poultry', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.POULTRY.9';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Alcohol', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.ALCOHOL.10';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Coffee', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.COFFEE.11';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Tea', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.TEA.12';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Soda', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.SODA.13';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Juice', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.JUICE.14';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Beer', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.BEER.15';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Fruit', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.FRUIT.16';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Vegetable', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.VEGETABLE.17';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Ice Cream', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.ICE_CREAM.18';
INSERT INTO t_category_language (cat_id, loc_id, ctl_label, ctl_deleted) SELECT t_category.cat_id, t_locale.loc_id, 'Infusion', false FROM t_locale, t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='CATEGORY.INFUSION.19';

--COMMENT ON TABLE t_product_special_code IS 'This table is used for product special code.';
--COMMENT ON COLUMN t_product_special_code.psc_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_special_code.res_id IS 'This is a foreign key that refers to t_restaurant. It is used to specify the restaurant.';
--COMMENT ON COLUMN t_product_special_code.psc_short_code IS 'This is used to specify the short code enter by user.';
--COMMENT ON COLUMN t_product_special_code.psc_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product special code.';
--COMMENT ON COLUMN t_product_special_code.psc_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_special_code (res_id, psc_short_code, psc_code_enm_id, psc_deleted) SELECT t_restaurant.res_id, '', t_enum.enm_id, false FROM t_restaurant, t_enum WHERE t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.NOTHING.0';
INSERT INTO t_product_special_code (res_id, psc_short_code, psc_code_enm_id, psc_deleted) SELECT t_restaurant.res_id, '#', t_enum.enm_id, false FROM t_restaurant, t_enum WHERE t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1';
INSERT INTO t_product_special_code (res_id, psc_short_code, psc_code_enm_id, psc_deleted) SELECT t_restaurant.res_id, '-', t_enum.enm_id, false FROM t_restaurant, t_enum WHERE t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2';
INSERT INTO t_product_special_code (res_id, psc_short_code, psc_code_enm_id, psc_deleted) SELECT t_restaurant.res_id, '/', t_enum.enm_id, false FROM t_restaurant, t_enum WHERE t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.USER_ORDER.3';
INSERT INTO t_product_special_code (res_id, psc_short_code, psc_code_enm_id, psc_deleted) SELECT t_restaurant.res_id, '@', t_enum.enm_id, false FROM t_restaurant, t_enum WHERE t_restaurant.res_reference = '10203040506070' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.CREDIT.4';

--COMMENT ON TABLE t_product_special_code_language IS 'This table is used for product special code depending on the specific language.';
--COMMENT ON COLUMN t_product_special_code_language.pcl_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_special_code_language.psc_id IS 'This is a foreign key that refers to t_product_special_code. It is used to specify the product special code. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_special_code_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product special code. This field and the other psc_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_special_code_language.pcl_label IS 'This is the label of the product special code depending on the language.';
--COMMENT ON COLUMN t_product_special_code_language.pcl_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Rien', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.NOTHING.0';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Produit Offert', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Réduction', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, '', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.USER_ORDER.3';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Avoir', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.USER_CREDIT.4';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Nothing', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.NOTHING.0';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Offered Product', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Discount', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, '', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.USER_ORDER.3';
INSERT INTO t_product_special_code_language (psc_id, loc_id, pcl_label, pcl_deleted) SELECT t_product_special_code.psc_id, t_locale.loc_id, 'Credit', false FROM t_locale, t_product_special_code JOIN t_enum ON t_enum.enm_id = t_product_special_code.psc_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_SPECIAL_CODE.USER_CREDIT.4';

--COMMENT ON TABLE t_product_part IS 'This table is used for product part.';
--COMMENT ON COLUMN t_product_part.prp_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_part.prp_code_enm_id IS 'This is a foreign key that refers to t_enum. It is used to specify the product part: ENTREE, PLAT DE RESISTANCE, DESSERT, CAFE...';
--COMMENT ON COLUMN t_product_part.prp_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.MISCELLANEOUS.0';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.APERITIF.1';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.APPETIZER.2';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.MAIN_COURSE.3';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.DESSERT.4';
INSERT INTO t_product_part (prp_code_enm_id, prp_deleted) SELECT t_enum.enm_id, false FROM t_enum WHERE t_enum.enm_language_key_label='PRODUCT_PART.DIGESTIF.5';

--COMMENT ON TABLE t_product_part_language IS 'This table is used for product part depending on the specific language.';
--COMMENT ON COLUMN t_product_part_language.ppl_id IS 'This is primary key of this table.';
--COMMENT ON COLUMN t_product_part_language.prp_id IS 'This is a foreign key that refers to t_product_part. It is used to specify the product part. This field and the other loc_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_part_language.loc_id IS 'This is a foreign key that refers to t_locale. It is used to specify the language of the product part. This field and the other prp_id field consist of a unique field.';
--COMMENT ON COLUMN t_product_part_language.ppl_label IS 'This is the label of the product part depending on the language.';
--COMMENT ON COLUMN t_product_part_language.ppl_deleted IS 'This is used for logical deletion.';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Divers', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.MISCELLANEOUS.0';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Apéritif', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.APERITIF.1';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Entrée', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.APPETIZER.2';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Plat', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.MAIN_COURSE.3';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Dessert', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.DESSERT.4';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Digestif', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='fr' AND t_enum.enm_language_key_label='PRODUCT_PART.DIGESTIF.5';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Miscellaneous', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.MISCELLANEOUS.0';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Aperitif', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.APERITIF.1';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Appetizer', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.APPETIZER.2';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Main Course', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.MAIN_COURSE.3';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Dessert', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.DESSERT.4';
INSERT INTO t_product_part_language (prp_id, loc_id, ppl_label, ppl_deleted) SELECT t_product_part.prp_id, t_locale.loc_id, 'Digestif', false FROM t_locale, t_product_part JOIN t_enum ON t_enum.enm_id = t_product_part.prp_code_enm_id WHERE t_locale.loc_language='en' AND t_enum.enm_language_key_label='PRODUCT_PART.DIGESTIF.5';
