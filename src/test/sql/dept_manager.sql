DESC dept_manager
;

SELECT *
FROM dept_manager
;

SELECT COUNT(1)
FROM dept_manager
;

SELECT *
FROM dept_manager
ORDER BY dept_no, from_date DESC
;

-- -------------------------------------------------------------------------------------------------------------- emp_no

-- ------------------------------------------------------------------------------------------------------------- dept_no

-- --------------------------------------------------------------------------------------------------- from_date,to_date
SELECT COUNT(1)
FROM dept_manager
WHERE from_date > to_date
;

-- --------------------------------------------------------------------------------------------------- dept_no,from_date
SELECT dept_no, MAX(from_date) AS max_from_date
FROM dept_manager
GROUP BY dept_no
;

SELECT dm.*
FROM dept_manager AS dm
         JOIN (SELECT emp_no, dept_no, MAX(from_date) AS max_from_date
               FROM dept_manager
               GROUP BY emp_no, dept_no) AS dm2
              ON dm.emp_no = dm2.emp_no AND dm.dept_no = dm2.dept_no AND dm.from_date = dm2.max_from_date
;

SELECT dm.*
FROM dept_manager AS dm
         JOIN (SELECT dept_no, MAX(from_date) AS latest_from_date
               FROM dept_manager
               GROUP BY dept_no) AS dm2
              ON dm.dept_no = dm2.dept_no AND dm.from_date = dm2.latest_from_date
WHERE dm.to_date < CURDATE()
;

-- latest manager
SELECT dept_no, MAX(from_date)
FROM dept_manager
GROUP BY dept_no
;
SELECT emp_no, dept_no, MAX(from_date)
FROM dept_manager
GROUP BY emp_no, dept_no
ORDER BY dept_no ASC
;

-- current managers
SELECT *
FROM departments
;
SELECT *
FROM dept_manager
WHERE from_date < CURDATE()
  AND to_date = '9999-01-01'
;

SELECT d.dept_no
FROM departments AS d
         LEFT JOIN (SELECT *
               FROM dept_manager
               WHERE from_date < CURDATE()
                 AND to_date = '9999-01-01') AS dm ON d.dept_no = dm.dept_no
WHERE dm.dept_no IS NULL
;
