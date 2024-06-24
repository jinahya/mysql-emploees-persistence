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
