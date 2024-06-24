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
DESC dept_emp_latest_date;

SHOW
CREATE VIEW dept_emp_latest_date;

-- CREATE ALGORITHM = UNDEFINED DEFINER =`root`@`localhost` SQL SECURITY DEFINER VIEW `dept_emp_latest_date` AS
select `dept_emp`.`emp_no`         AS `emp_no`,
       max(`dept_emp`.`from_date`) AS `from_date`,
       max(`dept_emp`.`to_date`)   AS `to_date`
from `dept_emp`
group by `dept_emp`.`emp_no`
;

-- -------------------------------------------------------------------------------------------------------------- emp_no
SELECT emp_no, COUNT(1) AS c
FROM employees.dept_emp_latest_date
GROUP BY emp_no
HAVING c > 1
;

-- ----------------------------------------------------------------------------------------------------------- from_date

-- ------------------------------------------------------------------------------------------------------------- to_date
SELECT *
FROM dept_emp_latest_date
WHERE to_date <> '9999-01-01'
;

-- --------------------------------------------------------------------------------------------------- from_date,to_date
SELECT *
FROM employees.dept_emp_latest_date
WHERE from_date > to_date
;
