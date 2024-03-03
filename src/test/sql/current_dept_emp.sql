DESC current_dept_emp;

SHOW
    CREATE VIEW current_dept_emp;

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
