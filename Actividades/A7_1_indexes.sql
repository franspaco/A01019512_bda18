# A01019512
# Francisco Huelsz

# Version 1 (14 rows)
explain
SELECT productLine, count(*) as count
FROM products p
INNER JOIN orderdetails o
    ON p.productCode = o.productCode
INNER JOIN orders o2
    ON o.orderNumber = o2.orderNumber
INNER JOIN customers c
    ON o2.customerNumber = c.customerNumber
WHERE c.customerNumber=112
GROUP BY p.productLine;

# Version 2 (13 rows)
explain
SELECT productLine, count(*) as count
FROM products p
INNER JOIN orderdetails o
    ON p.productCode = o.productCode
INNER JOIN orders o2
    ON o.orderNumber = o2.orderNumber
WHERE o2.customerNumber=112 
GROUP BY p.productLine;


# Encontrar cliente con mas compras
EXPLAIN
SELECT o.customerNumber, SUM(quantityOrdered) AS sum
FROM orderdetails
INNER JOIN orders o
    ON orderdetails.orderNumber = o.orderNumber
GROUP BY customerNumber
ORDER BY sum
LIMIT 1;