
select mnt.EMPLOYEE_ID, emp.LAST_NAME, emp.FIRST_NAME, emp.FATHER_NAME, ra.RANK, ra.LAST_RANK_DATE, ra.SALARY_GRADE, ra.LAST_SALARY_GRADE_DATE
from EMPLOYEE emp, EMPLOYMENT mnt, EMPLOYEE_INFO inf, RANK_INFO ra
where mnt.SCHOOL_ID=235 and mnt.IS_ACTIVE=1 and mnt.SCHOOL_YEAR_ID=12
and emp.ID = mnt.EMPLOYEE_ID
and inf.EMPLOYEE_ID = emp.ID
and inf.CURRENT_RANK_INFO_ID=ra.ID
