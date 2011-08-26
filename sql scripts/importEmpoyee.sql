

USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importEmployee' AND type = 'P')
   DROP PROCEDURE importEmployee
GO
USE minoas
GO

CREATE PROCEDURE importEmployee 
   @employeeLegacyCode nvarchar(6)
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
DECLARE @AM nvarchar(7)



SELECT 
    @LAST_NAME=b.EPIUETO,
    @FIRST_NAME=b.ONOMA,
    @FATHER_NAME=b.PATERAS,
    @LAST_SPECIALIZATION_ID=b.EIDKLAD,
    @DFEK=b.DFEK,
    @DPRAKSI=b.DPRAJH,
    @ORARIO=b.IE_ORES,
    @AM=b.AM
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
    EMPLOYEE_TYPE
) VALUES (
    NULL, /* INSERTED_ON */
    0, /* VERSION */
    1, /* IS ACTIVE */
    @LAST_NAME, /* LAST_NAME */
    @FIRST_NAME, /* LAST_NAME  */
    @FATHER_NAME, /* FATHER_NAME */
    NULL, /* MOTHER NAME */
    @employeeLegacyCode, /* LEGACY_CODE */ 
    1 /* PYSDE_ID */,
    'REGULAR' /* EMPLOYEE_TYPE */
);

END
GO






 






