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
DESC current_dept_emp;

SHOW
CREATE VIEW current_dept_emp
;
-- CREATE ALGORITHM = UNDEFINED DEFINER =`root`@`localhost` SQL SECURITY DEFINER VIEW `current_dept_emp` AS
select `l`.`emp_no`    AS `emp_no`,
       `d`.`dept_no`   AS `dept_no`,
       `l`.`from_date` AS `from_date`,
       `l`.`to_date`   AS `to_date`
from (`dept_emp` `d` join `dept_emp_latest_date` `l`
      on ((
          (`d`.`emp_no` = `l`.`emp_no`) and
          (`d`.`from_date` = `l`.`from_date`) and
          (`l`.`to_date` = `d`.`to_date`)
          ))
         )
;

-- ------------------------------------------------------------------------------------------------------ emp_no,dept_no
SELECT emp_no, dept_no, COUNT(1) AS c
FROM current_dept_emp
GROUP BY emp_no, dept_no
HAVING c > 1
;


-- ------------------------------------------------------------------------------------------------------------- to_date
SELECT COUNT(1)
FROM current_dept_emp #
WHERE to_date <> '9999-01-01' WHERE to_date = '9999-01-01'
;
