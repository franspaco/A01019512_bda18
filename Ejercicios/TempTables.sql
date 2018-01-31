

CREATE TABLE class( course_number INT NOT NULL, title VARCHAR(20) NOT NULL, credits SMALLINT NOT NULL WITH DEFAULT 3, price DECIMAL(6,2) NOT NULL, cstart DATE NOT NULL, cend DATE NOT NULL, period business_time(cstart, cend), PRIMARY KEY(course_number, business_time WITHOUT overlaps))

INSERT INTO class(course_number, title, credits, price, cstart, cend) values (1, 'BD', 5, 300, '2017-01-01', '2017-06-01'), (2, 'BDA', 8, 800, '2017-06-01', '2017-12-01'), (3, 'C++', 6, 550, '2018-01-01', '2018-06-01'), (4, 'Meta Programming', 3, 380, '2018-03-01', '2018-06-01')

SELECT * FROM class FOR business_time AS OF '2017-05-01'

SELECT * FROM class FOR business_time FROM '2017-01-01' TO '2017-12-31'