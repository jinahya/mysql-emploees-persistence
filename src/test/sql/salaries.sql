DESC salaries
;

SELECT COUNT(1)
FROM salaries
WHERE from_date > to_date
;

SELECT *
FROM salaries
;

-- -------------------------------------------------------------------------------------------------------------- emp_no

-- ---------------------------------------------------------------------------------------------------- emp_no/from_date
SELECT MAX(to_date)
FROM salaries
;
SELECT emp_no, MAX(from_date) AS latest_from_date
FROM salaries
GROUP BY emp_no
ORDER BY emp_no
;
SELECT s.emp_no, s.salary, l.latest_from_date
FROM salaries AS s
         JOIN (SELECT emp_no, MAX(from_date) AS latest_from_date
               FROM salaries
               GROUP BY emp_no) AS l
              ON s.emp_no = l.emp_no AND s.from_date = l.latest_from_date
ORDER BY s.salary DESC
;

-- -------------------------------------------------------------------------------------------------------------- salary
SELECT COUNT(1)
FROM salaries
WHERE salary <= 0
;

-- --------------------------------------------------------------------------------------------------- from_date/to_date
SELECT COUNT(1)
FROM salaries
WHERE from_date < to_date
;
SELECT COUNT(1) AS c
FROM salaries
GROUP BY emp_no, from_date
HAVING c > 1
;
SELECT s1.emp_no,
       s1.from_date,
       s2.emp_no,
       s2.from_date
FROM salaries AS s1
         LEFT OUTER JOIN salaries AS s2 ON s1.emp_no = s2.emp_no
WHERE s1.emp_no = 10001
ORDER BY s1.emp_no,
         s1.from_date
;

-- ---------------------------------------------------------------------------------------------------------------------
EXPLAIN
SELECT emp_no, from_date
FROM salaries
WHERE to_date = '9999-01-01'
;

EXPLAIN
SELECT emp_no, MAX(from_date) AS max_from_date
FROM (SELECT emp_no, from_date
      FROM salaries
      WHERE to_date = '9999-01-01') AS s
GROUP BY emp_no
;

EXPLAIN
SELECT s.emp_no, s.max_from_date, s2.salary
FROM (SELECT emp_no, MAX(from_date) AS max_from_date
      FROM (SELECT emp_no, from_date
            FROM salaries
            WHERE to_date = '9999-01-01') AS s
      GROUP BY emp_no) AS s
         JOIN salaries AS s2 ON s.emp_no = s2.emp_no AND s.max_from_date = s2.from_date
;

EXPLAIN
SELECT s.emp_no, s.max_from_date, s.salary, e.gender
FROM (SELECT s.emp_no, s.max_from_date, s2.salary
      FROM (SELECT emp_no, MAX(from_date) AS max_from_date
            FROM (SELECT emp_no, from_date
                  FROM salaries
                  WHERE to_date = '9999-01-01') AS s
            GROUP BY emp_no) AS s
               JOIN salaries AS s2 ON s.emp_no = s2.emp_no AND s.max_from_date = s2.from_date) AS s
         JOIN employees AS e ON s.emp_no = e.emp_no
;

EXPLAIN
SELECT e.gender, AVG(s.salary)
FROM (SELECT s.emp_no, s.max_from_date, s2.salary
      FROM (SELECT emp_no, MAX(from_date) AS max_from_date
            FROM (SELECT emp_no, from_date
                  FROM salaries
                  WHERE to_date = '9999-01-01') AS s
            GROUP BY emp_no) AS s
               JOIN salaries AS s2 ON s.emp_no = s2.emp_no AND s.max_from_date = s2.from_date) AS s
         JOIN employees AS e ON s.emp_no = e.emp_no
GROUP BY e.gender
;

EXPLAIN
SELECT s.*, e.gender
FROM salaries AS s
         JOIN employees e on s.emp_no = e.emp_no
;

EXPLAIN
SELECT AVG(s.salary), e.gender
FROM (SELECT emp_no, salary, MAX(from_date)
      FROM salaries
      WHERE to_date = '9999-01-01'
      GROUP BY emp_no, salary) AS s
         JOIN employees AS e ON s.emp_no = e.emp_no
GROUP BY e.gender
;
