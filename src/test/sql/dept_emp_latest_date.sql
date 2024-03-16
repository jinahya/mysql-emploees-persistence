DESC dept_emp_latest_date;

SHOW
CREATE VIEW dept_emp_latest_date;

-- CREATE ALGORITHM = UNDEFINED DEFINER =`root`@`localhost` SQL SECURITY DEFINER VIEW `dept_emp_latest_date` AS
select `dept_emp`.`emp_no`         AS `emp_no`,
       max(`dept_emp`.`from_date`) AS `from_date`,
       max(`dept_emp`.`to_date`)   AS `to_date`
from `dept_emp`
group by `dept_emp`.`emp_no`
;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT emp_no, COUNT(1) AS c
FROM employees.dept_emp_latest_date
GROUP BY emp_no
HAVING c > 1
;

-- ----------------------------------------------------------------------------------------------------------- from_date

-- ------------------------------------------------------------------------------------------------------------- to_date
SELECT *
FROM dept_emp_latest_date
WHERE to_date <> '9999-01-01'
;

-- --------------------------------------------------------------------------------------------------- from_date,to_date
SELECT *
FROM employees.dept_emp_latest_date
WHERE from_date > to_date
;
