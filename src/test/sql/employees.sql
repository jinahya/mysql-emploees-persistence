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
SELECT birth_date, COUNT(1) AS c
FROM employees
GROUP BY birth_date
HAVING c > 1
ORDER BY C desc
;
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

select e1_0.emp_no,
       e1_0.birth_date,
       e1_0.first_name,
       e1_0.gender,
       e1_0.hire_date,
       e1_0.last_name
from employees e1_0
where e1_0.birth_date = '1953-09-02'
# limit 0, 2
limit 0, 1
;

select e1_0.emp_no,
       e1_0.birth_date,
       e1_0.first_name,
       e1_0.gender,
       e1_0.hire_date,
       e1_0.last_name
from employees e1_0
where e1_0.birth_date = '1958-02-19'
limit 0, 2
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
SELECT gender, COUNT(1) AS c
FROM employees
GROUP BY gender
;

-- ----------------------------------------------------------------------------------------------------------- hire_date
SELECT MIN(hire_date), MAX(hire_date)
FROM employees
;

SELECT DISTINCT YEAR(hire_date) AS hire_year, COUNT(1) AS c
FROM employees
GROUP BY hire_year
ORDER BY hire_year
;

SELECT DISTINCT YEAR(hire_date) AS hire_year, MONTH(hire_date) AS hire_month, COUNT(1) AS c
FROM employees
GROUP BY hire_year, hire_month
ORDER BY hire_year, hire_month
;

SELECT DISTINCT MONTH(hire_date) AS hire_month, COUNT(1) AS c
FROM employees
GROUP BY hire_month
ORDER BY hire_month
;

SELECT DISTINCT DAYOFWEEK(hire_date) AS hire_weekday, COUNT(1)
FROM employees
GROUP BY hire_weekday
ORDER BY hire_weekday
;
