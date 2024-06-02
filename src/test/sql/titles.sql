DESC titles
;

SELECT COUNT(1)
FROM titles
;
SELECT COUNT(1)
FROM titles
WHERE from_date > to_date
;

SELECT *
FROM titles
;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT emp_no, COUNT(1) AS c
FROM titles
GROUP BY emp_no
HAVING c > 1
ORDER BY c DESC
;

SELECT *
FROM titles
WHERE emp_no = 479669
ORDER BY from_date ASC
;

-- --------------------------------------------------------------------------------------------------------------- title
SELECT DISTINCT title
FROM titles
ORDER BY title ASC
;

SELECT t.*, de.*
FROM (SELECT * FROM titles WHERE title = 'Manager') AS t
         LEFT JOIN dept_manager AS de ON t.emp_no = de.emp_no
-- WHERE de.emp_no IS NULL
;

-- ----------------------------------------------------------------------------------------------------------- from_date
SELECT COUNT(1)
FROM titles
WHERE to_date IS NULL
;

-- ------------------------------------------------------------------------------------------------------------- to_date
SELECT COUNT(1)
FROM titles
WHERE to_date < titles.from_date
;
SELECT DISTINCT emp_no, COUNT(1) AS c
FROM titles
WHERE to_date <> '9999-01-01'
GROUP BY emp_no
HAVING c > 1
;

SELECT *
FROM (SELECT *
      FROM titles
      ORDER BY emp_no ASC, from_date ASC) AS t1
;


-- ---------------------------------------------------------------------------------------------- emp_no/title/from_date
SELECT emp_no, title, MAX(from_date)
FROM titles
GROUP BY emp_no, title
ORDER BY emp_no, title
;
