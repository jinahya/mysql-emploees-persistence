DESC departments;

SHOW
CREATE TABLE departments
;
#
CREATE TABLE `departments`
    #
(
    #
    `dept_no`
    char
(
    4
) NOT NULL,
    # `dept_name` varchar
(
    40
) NOT NULL,
    # PRIMARY KEY
(
    `dept_no`
),
    # UNIQUE KEY `dept_name`
(
    `dept_name`
)
    # ) ENGINE = InnoDB
    # DEFAULT CHARSET = utf8mb4
    # COLLATE = utf8mb4_0900_ai_ci
    #;

SELECT COUNT(1)
FROM departments
;

-- ------------------------------------------------------------------------------------------------------------- dept_no
SELECT *
FROM departments
WHERE dept_no = 'd001'
;

-- ----------------------------------------------------------------------------------------------------------- dept_name
SELECT *
FROM departments
WHERE dept_name = 'Marketing'
;
