---
-- #%L
-- mysql-employees-persistece
-- %%
-- Copyright (C) 2024 Jinahya, Inc.
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---
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








-- ------------------------------------------------------------------------- https://stackoverflow.com/q/78632033/330457

SELECT *
FROM dept_emp
WHERE emp_no IN (21076, 37429, 91899, 206466, 228322, 290639, 435075,
                 435183, 440659, 469544, 475919, 496147)
ORDER BY emp_no
;

SELECT *
FROM dept_emp
WHERE emp_no IN (21076, 37429, 91899, 206466, 228322, 290639, 435075,
                 435183, 440659, 469544, 475919, 496147)
ORDER BY emp_no, from_date, to_date
;

SELECT emp_no,
       from_date,
       to_date,
#        LAG(to_date) OVER (PARTITION BY emp_no ORDER BY from_date) previous_to_date
       LAG(to_date) OVER (PARTITION BY emp_no ORDER BY to_date) previous_to_date
FROM dept_emp
;

SELECT emp_no, from_date, to_date, previous_to_date
FROM (SELECT emp_no,
             from_date,
             to_date,
             LAG(to_date) OVER (PARTITION BY emp_no ORDER BY from_date) previous_to_date
#              LAG(to_date) OVER (PARTITION BY emp_no ORDER BY to_date) previous_to_date
      FROM dept_emp) AS de
WHERE from_date < previous_to_date
;

select *
from dept_emp
where exists (select null
              from dept_emp other
              where other.emp_no = dept_emp.emp_no
                and other.dept_no <> dept_emp.dept_no
                and other.from_date < dept_emp.to_date
                and other.to_date > dept_emp.from_date)
;
