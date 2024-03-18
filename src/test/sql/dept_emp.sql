DESC dept_emp
;

SHOW CREATE TABLE dept_emp
;
# CREATE TABLE `dept_emp`
# (
#     `emp_no`    int     NOT NULL,
#     `dept_no`   char(4) NOT NULL,
#     `from_date` date    NOT NULL,
#     `to_date`   date    NOT NULL,
#     PRIMARY KEY (`emp_no`, `dept_no`),
#     KEY `dept_no` (`dept_no`),
#     CONSTRAINT `dept_emp_ibfk_1` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE,
#     CONSTRAINT `dept_emp_ibfk_2` FOREIGN KEY (`dept_no`) REFERENCES `departments` (`dept_no`) ON DELETE CASCADE
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_0900_ai_ci
# ;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT de.*,
       e.*
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
SELECT emp_no, MAX(from_date), MAX(to_date)
FROM dept_emp
GROUP BY emp_no
;
