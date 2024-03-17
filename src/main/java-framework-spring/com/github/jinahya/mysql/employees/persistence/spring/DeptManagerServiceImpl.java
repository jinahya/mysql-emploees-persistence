package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.DeptManager;
import com.github.jinahya.mysql.employees.persistence.DeptManagerService;
import com.github.jinahya.mysql.employees.persistence.Employee;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
class DeptManagerServiceImpl
        implements DeptManagerService {

    @Override
    public void appoint(final @Nonnull Employee employee, final @Nonnull Department department,
                        final @Nonnull LocalDate fromDate) {
        final var entity = new DeptManager();
        entity.getId().setEmpNo(employee.getEmpNo());                   // emp_no
        entity.getId().setDeptNo(department.getDeptNo());               // dept_no
        entity.setFromDate(fromDate);                                   // from_date
        entity.setToDate(DeptManager.COLUMN_VALUE_TO_DATE_UNSPECIFIED); // to_date
        deptManagerRepository.save(entity);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private DeptManagerRepository deptManagerRepository;
}
