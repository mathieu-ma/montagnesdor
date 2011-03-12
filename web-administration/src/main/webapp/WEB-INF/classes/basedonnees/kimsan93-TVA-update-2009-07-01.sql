-- Tous les TVA à 5.5
UPDATE t_product SET vat_id = 2;

--Les produits suivants la TVA à 19.6
UPDATE t_product SET vat_id = 1 WHERE pdt_id IN ('1/4','A1','A2','A3','A4','A5','A6','A7','B1/4','BH','BJ','BO','BO/','BT','BV','BVV','BX','BX/','BXV','BY','BY/','CA','CA/','CH','CHV','CP','CP/','CPV','CR','CR/','CRV','DG','DG1','DG2','DGO','IR','MU','MU/','MUV','PF','PF/','SC','SC/','ST','ST/','TV','TV/');

--Copie des produits suivants en ajoutant E
--INSERT INTO t_product(pdt_id, psc_id, pdt_price, pdt_colorrgb, vat_id) SELECT pdt_id||'E', psc_id, pdt_price, pdt_colorrgb, vat_id  FROM t_product WHERE pdt_id IN ('3','7','8','11','34','35','36','37','38','39','40','41','42','43','44','45','46','48','49','50','51','52','53','54','55','56','57','58','59','60','61','62','64','65','66','67','68','69','70','71','72','73','73B','74','75','76','77','78','79');

--Baisse de 10% pour les produits suivants 11.8
--UPDATE t_product SET pdt_price = pdt_price*0.9, pdt_colorrgb='' WHERE pdt_id IN ('3','7','8','11','34','35','36','37','38','39','40','41','42','43','44','45','46','48','49','50','51','52','53','54','55','56','57','58','59','60','61','62','64','65','66','67','68','69','70','71','72','73','73B','74','75','76','77','78','79');


