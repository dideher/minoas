

USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importWorkExpirience' AND type = 'P')
   DROP PROCEDURE importWorkExpirience
GO
USE minoas
GO

CREATE PROCEDURE importWorkExpirience 
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
SET @minoasExpirienceType = NULL;
SET @minoasEmployeeID = NULL;



SELECT @minoasEmployeeID=e.ID FROM minoas..EMPLOYEE e WHERE e.LEGACY_CODE=@employeeLegacyCode
IF(@minoasEmployeeID IS NULL)
BEGIN
    PRINT 'COULD NOT FIND EMPLOYEE'
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
    PRINT 'COULD NOT MAP EMPLOYEE EXPIRIENCE TYPE'
    RETURN;
END


INSERT INTO minoas..WORK_EXPERIENCE(
    INSERTED_ON, 
    VERSION, 
    IS_ACTIVE, 
    FROM_DATE, 
    TO_DATE, 
    COMMENT, 
    TITLE, 
    EMPLOYEE_ID,
    EXPERIENCE_TYPE_ID,
    CALENDAR_EXPERIENCE_DAYS,
    ACTUAL_DAYS
) VALUES (
    NULL, /* INSERTED_ON */
    0, /* VERSION */
    1, /* IS ACTIVE */
    @established, /* FROM_DATE */
    @dueto, /* TO_DATE  */
    @comment, /* COMMENT */
    @comment, /* TITLE */
    @minoasEmployeeID, /* EMPLOYEE_ID */
    @minoasExpirienceType, /* EXPERIENCE_TYPE_ID */
    0, /* CALENDAR_EXPERIENCE_DAYS */
    0 /* ACTUAL_DAYS */
);


    END


END
GO


USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importWorkExpirienceHelper' AND type = 'P')
   DROP PROCEDURE importWorkExpirienceHelper
GO
USE minoas
GO

/* ---------------------------------------------------------- */
/* ---------------------------------------------------------- */
/* ---------------------------------------------------------- */
USE minoas
GO
CREATE PROCEDURE importWorkExpirienceHelper 
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

SET @MyCursor = CURSOR FOR SELECT  ID, KVD, APO, EVS, 0, CODE, NOTE FROM mkdb..bohu WHERE CODE LIKE @importLegacyCode

OPEN @MyCursor 

FETCH NEXT FROM @MyCursor INTO @legacyCode, @employeeLegacyCode,@established, @dueto, @calendar_diff, @workExpirenceCode,@comment

WHILE @@FETCH_STATUS = 0 
BEGIN 
    EXEC slavikos.importWorkExpirience @legacyCode, @employeeLegacyCode,@established, @dueto, @calendar_diff, @workExpirenceCode,@comment
    FETCH NEXT FROM @MyCursor INTO @legacyCode, @employeeLegacyCode,@established, @dueto, @calendar_diff, @workExpirenceCode,@comment 
END 

CLOSE @MyCursor 

DEALLOCATE @MyCursor 


END
GO
 






SELECT COUNT(*) FROM minoas..WORK_EXPERIENCE
SELECT COUNT(*) FROM mkdb..bohu

DELETE FROM minoas..WORK_EXPERIENCE

SELECT * FROM KVD_PROYP

SELECT * FROM basiko WHERE KVD='420658'
SELECT * FROM bohu WHERE CODE='41' ORDER BY EVS

SELECT COUNT(*) FROM mkdb..bohu WHERE  CODE LIKE '1%'
10764


EXEC minoas..importWorkExpirience 33, '010050','1/4/1972', '8/31/1972', 0, '18', 'ΙΔ.ΓΥΜΝ.ΣΟΦΟΥΛΑΚΗ ΕΠΙΣΚΟΠΗ'
EXEC slavikos.importWorkExpirienceHelper '2%';


SELECT * FROM minoas..EMPLOYEE WHERE ID=9153

SELECT * FROM WORK_EXPERIENCE


SELECT * FROM YPHRESIA

SELECT * FROM KVD_PROYP

SELECT * FROM mkdb..bohu