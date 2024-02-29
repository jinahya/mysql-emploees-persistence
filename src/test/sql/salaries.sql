SELECT COUNT(1)
FROM salaries
WHERE from_date > to_date
;

-- -------------------------------------------------------------------------------------------------------------- salary
SELECT COUNT(1)
FROM salaries
WHERE salary <= 0
;

-- ----------------------------------------------------------------------------------------------------- from_date/to_date
SELECT COUNT(1)
FROM salaries
WHERE from_date < to_date
;
