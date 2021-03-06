

USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importEmployee' AND type = 'P')
   DROP PROCEDURE importEmployee
GO
USE minoas
GO

CREATE PROCEDURE importEmployee 
   @employeeLegacyCode nvarchar(6),
   @employmentStartDate nvarchar(8)
AS
BEGIN
DECLARE @FIRST_NAME varchar(25) ;
DECLARE @LAST_NAME varchar(35);
DECLARE @MOTHER_NAME varchar(25);
DECLARE @FATHER_NAME varchar(25);
DECLARE @LAST_SPECIALIZATION_ID varchar(6);
DECLARE @DFEK datetime;
DECLARE @DPRAKSI datetime;
DECLARE @ORARIO smallint;
DECLARE @AM nvarchar(7);
DECLARE @EMPLOYEE_ID numeric(19,0);
DECLARE @SCHOOL_ID nvarchar(5);
DECLARE @ACTIVE_SCHOOL_YEAR_ID numeric(19,0);
DECLARE @CURRENT_EMPLOYMENT_ID numeric(19,0);
DECLARE @REGULAR_INFO_ID numeric(19,0);

BEGIN TRAN;


SELECT 
    @LAST_NAME=b.EPIUETO,
    @FIRST_NAME=b.ONOMA,
    @FATHER_NAME=b.PATERAS,
    @LAST_SPECIALIZATION_ID=b.EIDKLAD,
    @DFEK=b.DFEK,
    @DPRAKSI=b.DPRAJH,
    @ORARIO=b.IE_ORES,
    @AM=b.AM,
    @SCHOOL_ID=b.OKVD
FROM mkdb..basiko b 
WHERE b.KVD=@employeeLegacyCode

INSERT INTO minoas..EMPLOYEE(
    INSERTED_ON, 
    VERSION, 
    IS_ACTIVE, 
    LAST_NAME,
    FIRST_NAME,
    FATHER_NAME,
    MOTHER_NAME,
    LEGACY_CODE,
    PYSDE_ID,
    EMPLOYEE_TYPE,
    VAT_NUMBER,
    LAST_SPECIALIZATION_ID
) VALUES (
    NULL, /* INSERTED_ON */
    0, /* VERSION */
    1, /* IS ACTIVE */
    @LAST_NAME, /* LAST_NAME */
    @FIRST_NAME, /* FIRST_NAME  */
    @FATHER_NAME, /* FATHER_NAME */
    NULL, /* MOTHER NAME */
    @employeeLegacyCode, /* LEGACY_CODE */ 
    1 /* PYSDE_ID */,
    'REGULAR', /* EMPLOYEE_TYPE */
    @employeeLegacyCode, /* VAT NUMBER HACK */
    @LAST_SPECIALIZATION_ID /* LAST_SPECIALIZATION_ID */
);

/* FIND OUT OUR NEW EMLPOYEE'S ID */
SELECT 
    @EMPLOYEE_ID=e.ID 
FROM 
    minoas..EMPLOYEE e 
    WHERE e.LEGACY_CODE=@employeeLegacyCode


INSERT INTO minoas..REGULAR_EMPLOYEE_INFO(
    APPOINTMENT_GOF,
    APPOINTMENT_GOF_DATE,
    REGISTRY_ID,
    version,
    EMPLOYEE_ID)
VALUES (
    @DFEK,
    @DPRAKSI,
    @AM,
    0,
    @EMPLOYEE_ID)


SELECT 
    @REGULAR_INFO_ID=i.ID
FROM 
    minoas..REGULAR_EMPLOYEE_INFO i
WHERE 
    i.EMPLOYEE_ID=@EMPLOYEE_ID


SELECT 
    @ACTIVE_SCHOOL_YEAR_ID = s.ID
FROM minoas..SCHOOL_YEAR s 
    WHERE s.IS_CURRENT_YEAR=1

/* INSERT THE EMPLOYMENT */
INSERT INTO minoas..EMPLOYMENT(
IS_ACTIVE,
ESTABLISHED_DATE,
FINAL_WORKING_HOURS,
MANDATORY_WORK_HRS,
EMPLOYMENT_TYPE,
version,
SCHOOL_ID,
SCHOOL_YEAR_ID,
SPECIALIZATION_ID,
EMPLOYEE_ID,
IS_DELETED
) VALUES 
(1, /* IS_ACTIVE */
@employmentStartDate,
@ORARIO,
@ORARIO,
'REGULAR',
0,
@SCHOOL_ID,
@ACTIVE_SCHOOL_YEAR_ID,
@LAST_SPECIALIZATION_ID,
@EMPLOYEE_ID,
0 /* IS DELETED */
)

/* UPDATE THE EMPLOYEE WITH THE CURRENT EMPLOYMENT */
SELECT 
    @CURRENT_EMPLOYMENT_ID=em.ID
FROM minoas..EMPLOYMENT em 
    WHERE em.EMPLOYEE_ID=@EMPLOYEE_ID

UPDATE minoas..EMPLOYEE
    SET CURRENT_EMPLOYMENT_ID=@CURRENT_EMPLOYMENT_ID, 
    REGULAR_EMPLOYMEE_INFO_ID=@REGULAR_INFO_ID
WHERE ID=@EMPLOYEE_ID

COMMIT TRAN;
END
GO






 






