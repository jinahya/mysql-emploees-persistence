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
ORDER BY dept_no ASC, from_date DESC
;

-- -------------------------------------------------------------------------------------------------------------- emp_no

-- ------------------------------------------------------------------------------------------------------------- dept_no

-- --------------------------------------------------------------------------------------------------- from_date,to_date
SELECT COUNT(1)
FROM dept_manager
WHERE from_date > to_date
;

SELECT *, DATEDIFF(to_date, from_date) AS datediff_date
FROM dept_manager
ORDER BY datediff_date ASC
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

-- ----------------------------------------------------------------------------------------------------- dept_no/to_date
SELECT *
FROM dept_manager
WHERE to_date = '9999-01-01'
;

-- max_to_date per dept_no
SELECT dept_no,
       MAX(to_date) AS max_to_date
FROM dept_manager
GROUP BY dept_no
;

-- with no permanent manager
SELECT dm.dept_no
FROM (SELECT dept_no,
             MAX(to_date) AS max_to_date
      FROM dept_manager
      GROUP BY dept_no) AS dm
WHERE max_to_date <> '9999-01-01'
;

SELECT d.*, dm.emp_no, from_date, e.emp_no, e.first_name, e.last_name
FROM departments AS d
         LEFT JOIN (SELECT *
                    FROM dept_manager
                    WHERE to_date = '9999-01-01') AS dm ON d.dept_no = dm.dept_no
         JOIN employees AS e ON dm.emp_no = e.emp_no
ORDER BY d.dept_no ASC
;

-- multiple managers per department
SELECT dept_no, COUNT(emp_no) AS emp_no_count
FROM dept_manager
WHERE to_date = '9999-01-01'
GROUP BY dept_no
HAVING emp_no_count > 1
;


SELECT dm.emp_no, dm.dept_no, de.emp_no, de.dept_no
FROM dept_manager AS dm
         LEFT JOIN dept_emp AS de
                   ON dm.emp_no = de.emp_no
                       AND dm.dept_no = de.dept_no
#                        AND dm.from_date = de.from_date
#                        AND dm.to_date = de.to_date
WHERE de.emp_no IS NULL
;
