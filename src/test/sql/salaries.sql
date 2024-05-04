DESC salaries
;

SELECT COUNT(1)
FROM salaries
WHERE from_date > to_date
;

SELECT *
FROM salaries
ORDER BY emp_no ASC, from_date ASC
;

-- -------------------------------------------------------------------------------------------------------------- emp_no

-- ---------------------------------------------------------------------------------------------------- emp_no/from_date
SELECT COUNT(1)
FROM employees
;
SELECT COUNT(distinct emp_no)
FROM salaries
;
SELECT MAX(to_date)
FROM salaries
;
SELECT emp_no,
       MAX(from_date) AS max_from_date
FROM salaries
GROUP BY emp_no
ORDER BY emp_no ASC
;
SELECT s.emp_no, s.salary, s2.max_from_date
FROM salaries AS s
         JOIN (SELECT emp_no, MAX(from_date) AS max_from_date
               FROM salaries
               GROUP BY emp_no) AS s2
              ON s.emp_no = s2.emp_no AND s.from_date = s2.max_from_date
ORDER BY s.salary DESC
;

-- ---------------------------------------------------------------------------------------------------- emp_no/from_date
SELECT emp_no, MAX(to_date)
FROM salaries
GROUP BY emp_no
ORDER BY emp_no ASC
;
SELECT *
FROM salaries
WHERE emp_no = 10008
ORDER BY from_date ASC
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

EXPLAIN
SELECT emp_no, MAX(from_date)
FROM salaries
WHERE to_date = '9999-01-01'
GROUP BY emp_no
;

EXPLAIN
SELECT AVG(s.salary), e.gender
FROM salaries AS s
         JOIN (SELECT emp_no, MAX(from_date) AS max_from_date
               FROM salaries
               WHERE to_date = '9999-01-01'
               GROUP BY emp_no) AS s2 ON s.emp_no = s2.emp_no AND s.from_date = s2.max_from_date
         JOIN employees AS e ON s.emp_no = e.emp_no
GROUP BY e.gender
;

-- ------------------------------------------------------------------------------------------------------- emp_no/salary
SELECT *
FROM salaries
WHERE emp_no = 10001
ORDER BY from_date DESC
;
SELECT *
FROM salaries
WHERE emp_no = 10001
ORDER BY to_date DESC
;
SELECT *
FROM salaries
WHERE emp_no = 10001
  AND to_date = '9999-01-01'
ORDER BY to_date DESC
;

-- -------------------------------------------------------------------------------------------------- AVG(salary)/emp_no
EXPLAIN
SELECT s.emp_no, AVG(s.salary) AS avg_salary
FROM salaries AS s
GROUP BY s.emp_no
ORDER BY avg_salary DESC
;

-- ---------------------------------------------------------------------------------------- AVG(salary)/employees.gender
EXPLAIN
SELECT e.gender, AVG(s.salary)
FROM salaries AS s
         JOIN employees AS e ON s.emp_no = e.emp_no #
WHERE s.to_date = '9999-01-01'
GROUP BY e.gender
ORDER BY e.gender
;
-- selectAllAvgSalaryByEmployeeGender1__eclipselink
SELECT t0.gender, AVG(t1.salary)
FROM employees t0,
     salaries t1
WHERE (t0.emp_no = t1.emp_no)
GROUP BY t0.gender
ORDER BY t0.gender
;
-- selectAllAvgSalaryByEmployeeGender1__hibernate
select e1_0.gender,
       avg(s1_0.salary)
from salaries s1_0
         join
     employees e1_0
     on e1_0.emp_no = s1_0.emp_no
group by e1_0.gender
order by e1_0.gender
;
-- selectAllAvgSalaryByEmployeeGender3__eclipselink
SELECT t0.gender, AVG(t1.salary)
FROM employees t0,
     salaries t1
WHERE (t0.emp_no = t1.emp_no)
GROUP BY t0.gender
ORDER BY t0.gender ASC
;
-- selectAllAvgSalaryByEmployeeGender3__hibernate
select e1_0.gender,
       avg(s1_0.salary)
from salaries s1_0
         join
     employees e1_0
     on e1_0.emp_no = s1_0.emp_no
group by 1
order by 1
;