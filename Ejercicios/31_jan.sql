-- Class def
CREATE TABLE class(
  course_number INT NOT NULL,
  title VARCHAR(20) NOT NULL,
  credits SMALLINT NOT NULL WITH DEFAULT 3,
  price DECIMAL(6,2) NOT NULL,
  cstart DATE NOT NULL,
  cend DATE NOT NULL,
  period business_time(cstart, cend),
  PRIMARY KEY(course_number, business_time WITHOUT overlaps)
);

--Inserts
INSERT INTO class(course_number, title, credits, price, cstart, cend) values
  (1, 'BD', 5, 300, '2017-01-01', '2017-06-01'),
  (2, 'BDA', 8, 800, '2017-06-01', '2017-12-01'),
  (3, 'C++', 6, 550, '2018-01-01', '2018-06-01'),
  (4, 'Meta Programming', 3, 380, '2018-03-01', '2018-06-01');

SELECT * FROM class
FOR business_time
AS OF '2017-05-01';

SELECT * FROM class
FOR business_time
FROM '2017-01-01' TO '2017-12-31';

-- Consultas datos temporales
-- __ FOR BUSINESS_TIME AS OF ___
-- __ FOR BUSINESS TIME FROM __ TO __

UPDATE CLASS
FOR PORTION OF BUSINESS_TIME FROM '2017-04-01' TO '2018-05-01'
set PRICE = 200.00
WHERE COURSE_NUMBER = 4;

--FAILS
INSERT INTO class(course_number, title, credits, price, cstart, cend) values
  (2, 'BDA', 8, 800, '2017-08-01', '2017-09-01');

DELETE FROM CLASS
FOR PORTION OF BUSINESS_TIME FROM '2018-02-01' TO '2018-03-01'
WHERE COURSE_NUMBER = 3;

-- SYS TIME
CREATE TABLE COURSE_SYS (
  course_number INT NOT NULL,
  title VARCHAR(20) NOT NULL,
  credits SMALLINT NOT NULL WITH DEFAULT 3,
  price DECIMAL(6,2) NOT NULL,
  SYS_START TIMESTAMP(12) GENERATED ALWAYS AS ROW BEGIN NOT NULL,
  SYS_END TIMESTAMP(12) GENERATED ALWAYS AS ROW END NOT NULL,
  TRANS_START TIMESTAMP(12) GENERATED ALWAYS AS TRANSACTION START ID IMPLICITLY HIDDEN,
  PERIOD SYSTEM_TIME (SYS_START, SYS_END)
);

CREATE TABLE COURSE_SYS_HISTORY LIKE COURSE_SYS;

ALTER TABLE COURSE_SYS
    ADD VERSIONING USE HISTORY TABLE COURSE_SYS_HISTORY;

INSERT INTO COURSE_SYS (COURSE_NUMBER, TITLE, CREDITS, PRICE) VALUES
  (500, 'intro to sql', 2, 200.00),
  (600, 'intro to ruby', 2, 250.00),
  (650, 'advanced ruby', 3, 400.00);

UPDATE COURSE_SYS SET PRICE = 250 WHERE COURSE_NUMBER = 650;

DELETE FROM COURSE_SYS WHERE course_number = 600;

