

select * from minoas.slavikos.EMPLOYEE_INFO
where EMPLOYEE_ID = 10865

select * from minoas.slavikos.REGULAR_EMPLOYEE_INFO
where EMPLOYEE_ID = 10865

select * from minoas.slavikos.EMPLOYMENT 
where EMPLOYEE_ID = 10865

select * from minoas.slavikos.NONREGULAR_EMPLOYMENT_INFO



update minoas.slavikos.EMPLOYMENT set NONREGULAR_EMPLOYMENT_INFO_id = 1 
where EMPLOYEE_ID = 10864


select * from minoas.slavikos.EMPLOYMENT 
where ID in (34670)

select * from minoas.slavikos.NONREGULAR_EMPLOYMENT_INFO

select * from minoas.slavikos.RANK_INFO
where EMPLOYEE_INFO_ID = 11773


update minoas.slavikos.EMPLOYEE_INFO set CURRENT_RANK_INFO_ID = null where EMPLOYEE_ID = 9360
delete minoas.slavikos.RANK_INFO where EMPLOYEE_INFO_ID = 11772
delete minoas.slavikos.EMPLOYEE_INFO where EMPLOYEE_ID = 9360

SELECT     *
FROM         minoas.slavikos.EMPLOYEE
WHERE ID = 10864




****** All the ACTIVE REGULARS with no EmployeeInfo
select e.* from minoas.slavikos.EMPLOYEE e, minoas.slavikos.EMPLOYMENT em
where em.IS_ACTIVE = 1 and em.EMPLOYMENT_TYPE='REGULAR'
and em.EMPLOYEE_ID = e.ID
and em.EMPLOYEE_ID NOT IN (
	select EMPLOYEE_ID from minoas.slavikos.EMPLOYEE_INFO
)



*********************************************************************************

Get lost ENTRY_INTO_SERVICE_ACT & ENTRY_INTO_SERVICE_DATE employments
**************************
SELECT e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, em.* FROM slavikos.EMPLOYMENT em, slavikos.EMPLOYEE e
WHERE em.IS_ACTIVE=1 AND em.ENTRY_INTO_SERVICE_DATE IS NULL AND em.SCHOOL_YEAR_ID=12
AND e.ID = em.EMPLOYEE_ID


SELECT * FROM slavikos.EMPLOYMENT em
where em.EMPLOYEE_ID = 6362



SELECT * FROM slavikos.EMPLOYMENT em
where em.EMPLOYEE_ID in (
SELECT en.EMPLOYEE_ID FROM slavikos.EMPLOYMENT en 
WHERE en.IS_ACTIVE=1 AND en.ENTRY_INTO_SERVICE_DATE IS NULL AND en.SCHOOL_YEAR_ID=12
) ORDER BY em.EMPLOYEE_ID


Get previous employments for lost ENTRY_INTO_SERVICE_ACT & ENTRY_INTO_SERVICE_DATE employments
************************
SELECT * FROM slavikos.EMPLOYMENT em 
WHERE em.SUPERSEDED_BY_ID in (
SELECT en.ID FROM slavikos.EMPLOYMENT en 
WHERE en.IS_ACTIVE=1 AND en.ENTRY_INTO_SERVICE_DATE IS NULL AND en.SCHOOL_YEAR_ID=12
) AND em.ENTRY_INTO_SERVICE_DATE is not NULL






Query for the repair of lost ENTRY_INTO_SERVICE_ACT & ENTRY_INTO_SERVICE_DATE
****************************************************
DECLARE @EmploymentCursor CURSOR; 
DECLARE @SUPERSEDED_BY_ID int;
DECLARE @ENTRY_INTO_SERVICE_ACT nvarchar(255);
DECLARE @ENTRY_INTO_SERVICE_DATE datetime;

SET @EmploymentCursor = CURSOR FOR SELECT em.SUPERSEDED_BY_ID, em.ENTRY_INTO_SERVICE_ACT, em.ENTRY_INTO_SERVICE_DATE FROM slavikos.EMPLOYMENT em 
									WHERE em.SUPERSEDED_BY_ID in (
										SELECT en.ID FROM slavikos.EMPLOYMENT en 
										WHERE en.IS_ACTIVE=1 AND en.ENTRY_INTO_SERVICE_DATE IS NULL AND en.SCHOOL_YEAR_ID=12
									)
									AND em.ENTRY_INTO_SERVICE_DATE is not NULL

OPEN @EmploymentCursor 
FETCH NEXT FROM @EmploymentCursor INTO @SUPERSEDED_BY_ID, @ENTRY_INTO_SERVICE_ACT, @ENTRY_INTO_SERVICE_DATE
WHILE @@FETCH_STATUS = 0 
BEGIN

	update minoas.slavikos.EMPLOYMENT 
		set ENTRY_INTO_SERVICE_ACT = @ENTRY_INTO_SERVICE_ACT, ENTRY_INTO_SERVICE_DATE = @ENTRY_INTO_SERVICE_DATE
	where ID = @SUPERSEDED_BY_ID

	FETCH NEXT FROM @EmploymentCursor INTO @SUPERSEDED_BY_ID, @ENTRY_INTO_SERVICE_ACT, @ENTRY_INTO_SERVICE_DATE
END



