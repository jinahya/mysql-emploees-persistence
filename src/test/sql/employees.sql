---
-- #%L
-- mysql-employees-persistece
-- %%
-- Copyright (C) 2024 Jinahya, Inc.
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---
DESC employees;

SHOW CREATE TABLE employees;
# CREATE TABLE `employees`
# (
#     `emp_no`     int            NOT NULL,
#     `birth_date` date           NOT NULL,
#     `first_name` varchar(14)    NOT NULL,
#     `last_name`  varchar(16)    NOT NULL,
#     `gender`     enum ('M','F') NOT NULL,
#     `hire_date`  date           NOT NULL,
#     PRIMARY KEY (`emp_no`)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_0900_ai_ci
# ;

SELECT COUNT(1)
FROM employees
;

SELECT *
FROM employees
ORDER BY emp_no ASC
;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT COUNT(1)
FROM employees
WHERE emp_no <= 0
;
SELECT MIN(emp_no), MAX(emp_no)
FROM employees
;

-- ---------------------------------------------------------------------------------------------------------- birth_date
-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html
-- min/max
SELECT MIN(birth_date) AS min_birth_date,
       MAX(birth_date) AS min_birth_date
FROM employees
;

SELECT MIN(birth_date)                                    AS min_birth_date,
       MAX(TIMESTAMPDIFF(YEAR, birth_date, CURRENT_DATE)) AS max_age,
       MAX(birth_date)                                    AS min_birth_date,
       MIN(TIMESTAMPDIFF(YEAR, birth_date, CURRENT_DATE)) AS min_age
FROM employees
;

-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_year
-- birth_year
SELECT DISTINCT YEAR(birth_date) AS birth_year
FROM employees
GROUP BY birth_year
ORDER BY birth_year ASC
;

SELECT YEAR(birth_date) AS birth_year,
       COUNT(1)         AS c
FROM employees
GROUP BY birth_year
ORDER BY birth_year ASC
;
SELECT YEAR(birth_date) AS birth_year,
       gender,
       COUNT(1)         AS c
FROM employees
GROUP BY birth_year, gender
ORDER BY birth_year ASC, gender ASC
;

SELECT *
FROM employees
WHERE YEAR(birth_date) = 1952
#   AND gender = 'F'
#   AND gender = 'M'
ORDER BY emp_no ASC
;

-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_quarter
-- quarter
SELECT DISTINCT QUARTER(birth_date) AS birth_quarter
FROM employees
GROUP BY birth_quarter
ORDER BY birth_quarter ASC
;
-- quarter/count
SELECT QUARTER(birth_date) AS birth_quarter,
       COUNT(1)            AS c
FROM employees
GROUP BY birth_quarter
ORDER BY birth_quarter ASC
;
-- quarter/gender/count
SELECT QUARTER(birth_date) AS birth_quarter,
       gender,
       COUNT(1)            AS c
FROM employees
GROUP BY birth_quarter, gender
ORDER BY birth_quarter ASC, gender ASC
;
-- year/quarter/gender/count
SELECT YEAR(birth_date)    AS birth_year,
       QUARTER(birth_date) AS birth_quarter,
       gender,
       COUNT(1)            AS c
FROM employees
GROUP BY birth_year, birth_quarter, gender
ORDER BY birth_year ASC, birth_quarter ASC, gender ASC
;

-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_month
-- birth_month
SELECT DISTINCT MONTH(birth_date)     AS birth_month,
                MONTHNAME(birth_date) AS birth_monthname
FROM employees
ORDER BY birth_month ASC
;
-- birth_month/count
SELECT MONTH(birth_date)     AS birth_month,
       MONTHNAME(birth_date) AS birth_monthname,
       COUNT(1)              AS c
FROM employees
GROUP BY birth_month, birth_monthname
ORDER BY birth_month ASC
;
-- birth_year/birth_month/count
SELECT YEAR(birth_date)  AS birth_year,
       MONTH(birth_date) AS birth_month,
       COUNT(1)          AS c
FROM employees
GROUP BY birth_year, birth_month
ORDER BY birth_year ASC, birth_month ASC
;
-- birth_year/birth_month/gender/count
SELECT YEAR(birth_date)  AS birth_year,
       MONTH(birth_date) AS birth_month,
       gender,
       COUNT(1)          AS c
FROM employees
GROUP BY birth_year, birth_month, gender
ORDER BY birth_year ASC, birth_month ASC, gender ASC
;

-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_dayofweek
-- birth_dayofweek
SELECT DISTINCT DAYOFWEEK(birth_date) AS birth_dayofweek
FROM employees
GROUP BY birth_dayofweek
ORDER BY birth_dayofweek ASC
;
SELECT DAYOFWEEK(birth_date) AS birth_dayofweek,
       DAYNAME(birth_date)   AS birth_dayname,
       COUNT(1)              AS c
FROM employees
GROUP BY birth_dayofweek, birth_dayname
ORDER BY birth_dayofweek ASC
;

-- https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_dayofmonth
-- birth_dayofmonth
SELECT DISTINCT DAYOFMONTH(birth_date) AS birth_dayofmonth
FROM employees
ORDER BY birth_dayofmonth ASC
;
-- birth_dayofmonth/count
SELECT DAYOFMONTH(birth_date) AS birth_dayofmonth,
       COUNT(1)               AS c
FROM employees
GROUP BY birth_dayofmonth
ORDER BY birth_dayofmonth ASC
;

-- Happy Birthday To You!
SELECT e.*
FROM employees AS e
WHERE MONTH(e.birth_date) = MONTH(CURDATE())
  AND DAYOFMONTH(e.birth_date) = DAYOFMONTH(CURDATE())
ORDER BY e.emp_no ASC
;
SELECT e.*
FROM employees AS e
WHERE DATE_FORMAT(e.birth_date, '%m%d') = DATE_FORMAT(CURDATE(), '%m%d')
ORDER BY e.emp_no ASC
;

-- ---------------------------------------------------------------------------------------------------------- first_name
SELECT first_name,
       COUNT(1) AS c
FROM employees
GROUP BY first_name
HAVING c > 1
ORDER BY c DESC, first_name ASC
;

-- ----------------------------------------------------------------------------------------------------------- last_name
SELECT last_name,
       COUNT(1) AS c
FROM employees
GROUP BY last_name
HAVING c > 1
ORDER BY c DESC, last_name ASC
;

-- ------------------------------------------------------------------------------------------------ first_name/last_name
SELECT first_name,
       last_name,
       COUNT(1) AS c
FROM employees
GROUP BY first_name, last_name
HAVING c > 1
ORDER BY c DESC, first_name ASC, last_name ASC
;

-- -------------------------------------------------------------------------------------------------------------- gender
SELECT gender, COUNT(1) AS c
FROM employees
GROUP BY gender ASC
;

-- ----------------------------------------------------------------------------------------------------------- hire_date
SELECT *
FROM employees
ORDER BY hire_date ASC
LIMIT 32
;
SELECT *
FROM employees
WHERE hire_date = (SELECT MIN(hire_date) FROM employees)
;
