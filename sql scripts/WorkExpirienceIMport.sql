

USE minoas
IF EXISTS (SELECT * FROM sysobjects 
         WHERE name = 'importWorkExperience' AND type = 'P')
   DROP PROCEDURE importWorkExperience
GO
USE minoas
GO

CREATE PROCEDURE importWorkExperience 
   @legacyCode int,
   @employeeLegacyCode varchar(6),
   @established datetime,
   @dueto datetime,
   @calendar_diff int,
   @workExpirenceCode varchar(2),
   @comment varchar(33)
AS
BEGIN
SET NOCOUNT ON;
DECLARE @minoasEmployeeID int 
DECLARE @minoasExpirienceType int
DECLARE @educational bit
SET @minoasExpirienceType = NULL;
SET @minoasEmployeeID = NULL;



SELECT @minoasEmployeeID=e.ID FROM minoas.slavikos.EMPLOYEE e WHERE e.LEGACY_CODE=@employeeLegacyCode
IF(@minoasEmployeeID IS NULL)
BEGIN
    PRINT 'COULD NOT FIND EMPLOYEE with LEGACY CODE: ' + @employeeLegacyCode
END
ELSE 
    BEGIN

SET @minoasExpirienceType =
CASE 
    WHEN @workExpirenceCode = '11' THEN  1
    WHEN @workExpirenceCode = '12' THEN  2
    WHEN @workExpirenceCode = '13' THEN  3
    WHEN @workExpirenceCode = '14' THEN  4
    WHEN @workExpirenceCode = '15' THEN  5
    WHEN @workExpirenceCode = '16' THEN  6
    WHEN @workExpirenceCode = '17' THEN  7
    WHEN @workExpirenceCode = '18' THEN  8
    WHEN @workExpirenceCode = '19' THEN  9
END; 

IF(@minoasExpirienceType= NULL)
BEGIN
    PRINT 'COULD NOT MAP EMPLOYEE EXPERIENCE TYPE'
    RETURN;
END

SET @educational =
CASE 
    WHEN @workExpirenceCode = '11' THEN  0
    WHEN @workExpirenceCode = '12' THEN  0
    WHEN @workExpirenceCode = '13' THEN  1
    WHEN @workExpirenceCode = '14' THEN  1
    WHEN @workExpirenceCode = '15' THEN  0
    WHEN @workExpirenceCode = '16' THEN  0
    WHEN @workExpirenceCode = '17' THEN  0
    WHEN @workExpirenceCode = '18' THEN  0
    WHEN @workExpirenceCode = '19' THEN  0
END; 



INSERT INTO minoas.slavikos.WORK_EXPERIENCE(
    INSERTED_ON, 
    VERSION, 
    IS_ACTIVE, 
    FROM_DATE, 
    TO_DATE,
    COMMENT, 
    EMPLOYEE_ID,
    EXPERIENCE_TYPE_ID,
    CALENDAR_EXPERIENCE_DAYS,
    ACTUAL_DAYS,
	EDUCATIONAL,
	LEGACY_CODE
) VALUES (
    NULL, /* INSERTED_ON */
    0, /* VERSION */
    1, /* IS ACTIVE */
    @established, /* FROM_DATE */
    @dueto, /* TO_DATE  */
    @comment, /* COMMENT */
    @minoasEmployeeID, /* EMPLOYEE_ID */
    @minoasExpirienceType, /* EXPERIENCE_TYPE_ID */
    DATEDIFF(day, @established, @dueto), /* CALENDAR_EXPERIENCE_DAYS */
    @calendar_diff, /* ACTUAL_DAYS */
	@educational, /* EDUCATIONAL */
	@legacyCode
);



    END


END
GO


USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importWorkExperienceHelper' AND type = 'P')
   DROP PROCEDURE importWorkExperienceHelper
GO
USE minoas
GO

/* ---------------------------------------------------------- */
/* ---------------------------------------------------------- */
/* ---------------------------------------------------------- */
USE minoas
GO
CREATE PROCEDURE importWorkExperienceHelper 
@importLegacyCode varchar(2) AS
BEGIN

DECLARE @legacyCode int;
DECLARE @employeeLegacyCode varchar(6);
DECLARE @established datetime;
DECLARE @dueto datetime;
DECLARE @calendar_diff int;
DECLARE @workExpirenceCode varchar(2);
DECLARE @comment varchar(33);
DECLARE @MyCursor CURSOR; 

SET @MyCursor = CURSOR FOR SELECT  ID, KVD, APO, EVS, ARHMER, CODE, NOTE FROM mkdb..bohu WHERE CODE LIKE @importLegacyCode

OPEN @MyCursor 

FETCH NEXT FROM @MyCursor INTO @legacyCode, @employeeLegacyCode,@established, @dueto, @calendar_diff, @workExpirenceCode,@comment

WHILE @@FETCH_STATUS = 0 
BEGIN 
    EXEC importWorkExperience @legacyCode, @employeeLegacyCode, @established, @dueto, @calendar_diff, @workExpirenceCode,@comment
    FETCH NEXT FROM @MyCursor INTO @legacyCode, @employeeLegacyCode, @established, @dueto, @calendar_diff, @workExpirenceCode,@comment 
END 

CLOSE @MyCursor 

DEALLOCATE @MyCursor 


END
GO
 

EXEC importWorkExperienceHelper '1%';


delete slavikos.WORK_EXPERIENCE


INSERT INTO minoas..WORK_EXPERIENCE(
    INSERTED_ON, 
    VERSION, 
    IS_ACTIVE, 
    FROM_DATE, 
    TO_DATE,
    COMMENT, 
    EMPLOYEE_ID,
    EXPERIENCE_TYPE_ID,
    CALENDAR_EXPERIENCE_DAYS,
    ACTUAL_DAYS,
	EDUCATIONAL
) VALUES (
    NULL, /* INSERTED_ON */
    0, /* VERSION */
    1, /* IS ACTIVE */
    @established, /* FROM_DATE */
    @dueto, /* TO_DATE  */
    @comment, /* COMMENT */
    @minoasEmployeeID, /* EMPLOYEE_ID */
    @minoasExpirienceType, /* EXPERIENCE_TYPE_ID */
    DATEDIFF(day, '@established', '@dueto'), /* CALENDAR_EXPERIENCE_DAYS */
    @calendar_diff, /* ACTUAL_DAYS */
	@educational /* EDUCATIONAL */
);





SELECT  ID, KVD, APO, EVS, ARHMER, CODE, NOTE FROM mkdb..bohu WHERE CODE LIKE '1%';
SELECT @minoasEmployeeID=e.ID FROM minoas.slavikos.EMPLOYEE e WHERE e.LEGACY_CODE=@employeeLegacyCode

SELECT * FROM minoas.slavikos.EMPLOYEE e WHERE e.IS_ACTIVE=1 and e.LEGACY_CODE not in (
SELECT  KVD FROM mkdb..bohu WHERE CODE LIKE '1%'
)


select KVD FROM mkdb..bohu WHERE CODE LIKE '1%' and KVD not in (
select LEGACY_CODE FROM minoas.slavikos.EMPLOYEE
)

SELECT DATEDIFF(day, '20070101', '20080101');
SELECT DATEDIFF(dayofyear, '20070101', '20080101');


SELECT DATEDIFF(dayofyear, '2005-12-31 23:59:59.9999999', '2006-01-01 00:00:00.0000000');

SELECT DATEDIFF(day, '2005-12-31 23:59:59.9999999', '2006-01-01 00:00:00.0000000'); 


SELECT COUNT(*) FROM minoas.slavikos.WORK_EXPERIENCE

SELECT * FROM minoas.slavikos.WORK_EXPERIENCE


SELECT COUNT(*) FROM mkdb..bohu 

SELECT * FROM mkdb..bohu where CODE like '1%'

SELECT KVD, APO, EVS, DATEDIFF(day, APO, EVS), ARHMER FROM mkdb..bohu where CODE like '1%' and ARHMER <29

SELECT * FROM mkdb..bohu where CODE like '1%' and ARHMER1 <> ARHMER
select * from mkdb..basiko where KVD = 999114

DELETE FROM minoas..WORK_EXPERIENCE

SELECT * FROM KVD_PROYP

SELECT * FROM basiko WHERE KVD='420658'
SELECT * FROM bohu WHERE CODE='41' ORDER BY EVS

SELECT COUNT(*) FROM mkdb..bohu WHERE  CODE LIKE '1%'
10764


EXEC minoas..importWorkExpirience 33, '010050','1/4/1972', '8/31/1972', 0, '18', 'ΙΔ.ΓΥΜΝ.ΣΟΦΟΥΛΑΚΗ ΕΠΙΣΚΟΠΗ'
EXEC slavikos.importWorkExpirienceHelper '2%';


SELECT * FROM minoas..EMPLOYEE WHERE ID=9153

SELECT * FROM minoas.slavikos.WORK_EXPERIENCE


SELECT * FROM YPHRESIA

SELECT * FROM KVD_PROYP

SELECT * FROM mkdb..bohu