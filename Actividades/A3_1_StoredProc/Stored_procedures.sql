

use classicmodels;

DROP PROCEDURE IF EXISTS product_by_line;
DROP PROCEDURE IF EXISTS clients_by_letter;
DROP PROCEDURE IF EXISTS product_max_min_values;
DROP PROCEDURE IF EXISTS product_max_min;

delimiter $$
CREATE PROCEDURE product_by_line
(
	IN linea varchar(50)
)
BEGIN
	SELECT *
    FROM products
    WHERE productLine like concat(linea, "%");
END$$

CREATE PROCEDURE clients_by_letter
(
	IN letter VARCHAR(1)
)
BEGIN
	SELECT *
    FROM customers
    WHERE customerName like concat(letter, '%');
END$$

CREATE PROCEDURE product_max_min_values()
BEGIN
    SELECT MAX(MSRP), MIN(MSRP) FROM products;
END$$


CREATE PROCEDURE product_max_min()
BEGIN
    SELECT *
    FROM products
    WHERE MSRP = (SELECT MAX(MSRP) FROM products) OR MSRP = (SELECT MIN(MSRP) FROM products)
    ORDER BY MSRP DESC;
END$$

delimiter ; 
