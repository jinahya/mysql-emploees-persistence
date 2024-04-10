DESC dept_emp
;

SHOW CREATE TABLE dept_emp
;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT de.emp_no, e.*
FROM dept_emp AS de
         JOIN employees AS e ON de.emp_no = e.emp_no
;

-- ------------------------------------------------------------------------------------------------------------- dept_no
SELECT de.*,
       d.*
FROM dept_emp AS de
         JOIN departments AS d ON de.dept_no = d.dept_no
;

-- ----------------------------------------------------------------------------------------------------------- from_date
SELECT MIN(from_date), MAX(from_date)
FROM dept_emp
;

-- ------------------------------------------------------------------------------------------------------------- to_date
SELECT COUNT(1)
FROM dept_emp
WHERE to_date > '9999-01-01'
;

-- --------------------------------------------------------------------------------------------------- from_date/to_date
SELECT COUNT(1)
FROM dept_emp
WHERE from_date > to_date
;

-- -------------------------------------------------------------------------------------------- emp_no/from_date/to_date
-- current job
SELECT *
FROM dept_emp
WHERE from_date <= CURDATE()
  AND to_date >= CURDATE()
ORDER BY emp_no ASC, dept_no ASC
;

-- concurrent job
SELECT emp_no, COUNT(1) AS c
FROM dept_emp
WHERE from_date <= CURDATE()
  AND to_date >= CURDATE()
GROUP BY emp_no
HAVING c > 1
;
