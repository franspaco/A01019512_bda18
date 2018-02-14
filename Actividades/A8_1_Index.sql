
# orderLineNumber:
# indexes:
# 1 - "index_1" - orderLineNumber
# 2 - "index_2" -(quantityOrdered, orderLineNumber)
# 3 - "index_3" -(orderLineNumber, quantityOrdered)

#CREATE INDEX index_1 ON orderdetails (orderLineNumber);
#CREATE INDEX index_2 ON orderdetails (quantityOrdered, orderLineNumber);
#CREATE INDEX index_3 ON orderdetails (orderLineNumber, quantityOrdered);

EXPLAIN format=json
SELECT *
FROM orderdetails force index(index_4)
WHERE orderLineNumber = 1 and quantityOrdered > 50;
# Nothing: 93.41
# 1: 95.20
# 2: 93.41
# 3: 8.01
# 4: 8.01

EXPLAIN format=json
SELECT productCode
FROM orderdetails force index(index_4)
WHERE orderLineNumber = 1 and quantityOrdered > 50;
# Nothing: 93.41
# 1: 95.20
# 2: 27.62
# 3: 3.02
# 4: 3.02

EXPLAIN format=json
SELECT orderLineNumber, count(*)
FROM orderdetails force index(index_4)
WHERE orderLineNumber = 1 and quantityOrdered > 50;
# Nothing: 93.41
# 1: 95.20
# 2: 27.62
# 3: 3.02
# 4: 3.02
