DESC salaries
;

SELECT COUNT(1)
FROM salaries
WHERE from_date > to_date
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
