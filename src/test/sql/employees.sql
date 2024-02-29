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

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT COUNT(1)
FROM employees
WHERE emp_no <= 0
;
SELECT MIN(emp_no), MAX(emp_no)
FROM employees
;

-- ---------------------------------------------------------------------------------------------------------- birth_date
SELECT YEAR(birth_date) AS birth_year, COUNT(1) AS count
FROM employees
GROUP BY birth_year
ORDER BY count DESC
;

SELECT MONTH(birth_date) AS birth_month, MONTHNAME(birth_date) AS birth_monthname, COUNT(1) AS count
FROM employees
GROUP BY birth_month, birth_monthname
ORDER BY birth_month
;

SELECT DAYOFWEEK(birth_date) AS birth_dayofweek, DAYNAME(birth_date) AS birth_dayname, COUNT(1) AS count
FROM employees
GROUP BY birth_dayofweek, birth_dayname
ORDER BY birth_dayofweek
;

SELECT DAYOFMONTH(birth_date) AS birth_dayofmonth, COUNT(1) AS count
FROM employees
GROUP BY birth_dayofmonth
ORDER BY birth_dayofmonth
;

-- ---------------------------------------------------------------------------------------------------------- first_name
SELECT first_name, COUNT(1) AS count
FROM employees
GROUP BY first_name
HAVING count > 1
ORDER BY count DESC
;

-- ----------------------------------------------------------------------------------------------------------- last_name
SELECT last_name, COUNT(1) AS count
FROM employees
GROUP BY last_name
HAVING count > 1
ORDER BY count DESC
;

-- ------------------------------------------------------------------------------------------------ first_name/last_name
SELECT first_name, last_name, COUNT(1) AS count
FROM employees
GROUP BY first_name, last_name
HAVING count > 1
ORDER BY count DESC
;

-- -------------------------------------------------------------------------------------------------------------- gender
SELECT gender, COUNT(1) AS count
FROM employees
GROUP BY employees.gender
;
