
# Francisco Huelsz Prince
# A01019512

# parte 2
DROP TABLE IF EXISTS LOG_FILM;
CREATE TABLE LOG_FILM (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  tipo VARCHAR(10),
  film_id SMALLINT(5),
  last_value TINYINT(3) UNSIGNED,
  new_value TINYINT(3) UNSIGNED,
  time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

# parte 3
DROP PROCEDURE IF EXISTS log_film_entry;
DELIMITER $$
CREATE PROCEDURE log_film_entry(
  IN tipo_in VARCHAR(10),
  IN film_id_in SMALLINT(5),
  IN last_value_in TINYINT(3) UNSIGNED,
  IN new_value_in TINYINT(3) UNSIGNED
)
  BEGIN
    INSERT INTO LOG_FILM (tipo, film_id, last_value, new_value) VALUES
      (tipo_in, film_id_in, last_value_in, new_value_in);
  END $$
DELIMITER ;

# parte 4
DROP TRIGGER IF EXISTS on_update_films;
DELIMITER $$
CREATE TRIGGER on_update_films AFTER UPDATE ON film
  FOR EACH ROW
  BEGIN
    DECLARE old_value TINYINT(3) UNSIGNED DEFAULT NULL;
    DECLARE new_value TINYINT(3) UNSIGNED DEFAULT NULL;
    SET old_value = OLD.original_language_id;
    SET new_value = NEW.original_language_id;
    CALL log_film_entry('UPDATE', OLD.film_id, old_value, new_value);
  END;
DELIMITER ;

# parte 5
DELIMITER $$
CREATE PROCEDURE update_original_languages()
  BEGIN
    DECLARE ids int;
    DECLARE done int DEFAULT FALSE;
    DECLARE cursor1 CURSOR FOR SELECT film_id FROM film;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = true;

    OPEN cursor1;
    read_loop: LOOP
      FETCH cursor1 INTO ids;
      IF done THEN
        LEAVE read_loop;
      END IF;

      IF (SELECT category_id FROM film_category WHERE film_id = ids) = 6 THEN
        # Documentary(6) then italian(2)
        UPDATE film SET original_language_id=2 WHERE film_id = ids;
      ELSEIF (SELECT category_id FROM film_category WHERE film_id = ids) = 9 THEN
        # Foreign(9) then japanese(3)
        UPDATE film SET original_language_id=3 WHERE film_id = ids;
      ELSEIF (SELECT COUNT(*) FROM film_actor WHERE film_id = ids AND actor_id=31) = 1 THEN
        # SOBIESKI(31) then german(6)
        UPDATE film SET original_language_id=6 WHERE film_id = ids;
      ELSEIF (SELECT COUNT(*) FROM film_actor WHERE film_id = ids AND actor_id=3) = 1 THEN
        # ED CHASE(3) then mandarin(4)
        UPDATE film SET original_language_id=4 WHERE film_id = ids;
      ELSEIF (SELECT COUNT(*) FROM film_actor WHERE film_id = ids AND actor_id=34) = 1 THEN
        # AUDREY OLIVIER(34) then french(5)
        UPDATE film SET original_language_id=5 WHERE film_id = ids;
      ELSE
        # Else english then 1
        UPDATE film SET original_language_id=1 WHERE film_id = ids;
      END IF;
    END LOOP;
    CLOSE cursor1;
  END $$
DELIMITER ;

CALL update_original_languages();



## DB2

DROP TABLE gomitas;
CREATE TABLE gomitas(
  ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  NAME VARCHAR(150) NOT NULL,
  PRICE DECIMAL(10,6) NOT NULL,
  cstart DATE NOT NULL,
  cend DATE NOT NULL,
  period business_time(cstart, cend),
  PRIMARY KEY(ID, business_time WITHOUT overlaps)
);

INSERT INTO gomitas (NAME, PRICE, cstart, cend) VALUES
  ('gom1', 5, '2018-1-1', '2019-1-1'),
  ('gom2', 6, '2018-1-1', '2019-1-1'),
  ('gom3', 7, '2018-1-1', '2019-1-1'),
  ('gom4', 8, '2018-1-1', '2019-1-1'),
  ('gom5', 9, '2018-1-1', '2019-1-1'),
  ('gom6', 10, '2018-1-1', '2019-1-1'),
  ('gom7', 11, '2018-1-1', '2019-1-1'),
  ('gom8', 12, '2018-1-1', '2019-1-1'),
  ('gom9', 13, '2018-1-1', '2019-1-1'),
  ('gom10', 14, '2018-1-1', '2019-1-1'),
  ('gom11', 15, '2018-1-1', '2019-1-1'),
  ('gom12', 16, '2018-1-1', '2019-1-1');

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-2-1' to '2019-1-1'
  SET PRICE = PRICE*1.45;

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-2-15' to '2019-1-1'
  SET PRICE = (PRICE/1.45)*1.1;

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-4-25' to '2019-1-1'
  SET PRICE = PRICE*1.45;

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-5-5' to '2019-1-1'
  SET PRICE = (PRICE/1.45)*1.1;

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-10-25' to '2019-1-1'
  SET PRICE = PRICE*1.45;

UPDATE gomitas
FOR PORTION OF BUSINESS_TIME FROM '2018-11-5' to '2019-1-1'
  SET PRICE = (PRICE/1.45)*1.1;

SELECT * from gomitas WHERE NAME='gom1';

SELECT SUM(PRICE)/COUNT(*) as AVG from gomitas WHERE NAME='gom1';

SELECT MAX(PRICE) as MAX from gomitas WHERE NAME='gom1';