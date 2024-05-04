# mysql-employees-persistence

## departments

### all

```sql
SELECT d.*
FROM departments AS d
ORDER BY d.dept_no ASC
```

```jpaql
SELECT e
FROM Department AS e
ORDER BY e.deptNo ASC
```

### one whose `dept_no` is equal to specified

```sql
SELECT d.*
FROM departments AS d
WHERE d.dept_no = ?
```

```jpaql
SELECT e
FROM Department AS e
WHERE e.deptNo = :deptNo
```

### one whose `dept_name` is equal to specified

```sql
SELECT d.*
FROM departments AS d
WHERE d.dept_name = ?
```

```jpaql
SELECT e
FROM Department AS e
WHERE e.deptName = :deptName
```

## dept_emp

## dept_manager

## employees ([employees.sql](src/test/sql/employees.sql))

### all

### one whose `emp_no` is equals to specified

```sql
SELECT e.*
FROM employees AS e
WHERE e.emp_no = ?
```

```jpaql
SELECT e
FROM Employee AS e
WHERE e.empNo = :empNo
```

## salaries

## titles ([titles.sql](src/test/sql/titles.sql))

### Find all, of an employee, sorted by `from_date` in ascending order

```sql
SELECT t.*
FROM titles AS t
WHERE t.emp_no = ?
ORDER BY t.from_date ASC
```

```jpaql
SELECT e
FROM Title AS e
WHERE e.id.empNo = :idEmpNo
ORDER BY e.id.fromDate ASC
```
```jpaql
SELECT e
FROM Title AS e
WHERE e.employee = :employee
ORDER BY e.id.fromDate ASC
```


## current_dept_emp

## dept_emp_latest_date
