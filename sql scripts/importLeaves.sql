// sample : EXEC minoas..importEmployeeLeave 420968, 9281

USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importEmployeeLeave' AND type = 'P')
   DROP PROCEDURE importEmployeeLeave
GO
USE minoas
GO


CREATE PROCEDURE importEmployeeLeave
@legacyEmployeeID varchar(6),
@minoasEmployeeID int
AS 
BEGIN
DECLARE @oldLeavesCursor CURSOR; 
DECLARE @legacyFrom datetime;
DECLARE @legacyTo datetime;
DECLARE @legacyDayCount float
DECLARE @legacyNote nvarchar(33)
DECLARE @legacyCode nvarchar(2)
DECLARE @minoasLeaveType int;
DECLARE @minoasLeaveGenerateCDR int;
SET NOCOUNT ON;

DELETE minoas..EMPLOYEE_LEAVES WHERE EMPLOYEE_ID=@minoasEmployeeID
SET @oldLeavesCursor = CURSOR FOR SELECT b.APO, b.EVS, b.ARHMER, b.NOTE, b.CODE 
    FROM mkdb..bohu b 
WHERE b.KVD=@legacyEmployeeID 
    ORDER BY b.APO

OPEN @oldLeavesCursor 
FETCH NEXT FROM @oldLeavesCursor INTO @legacyFrom, @legacyTo, @legacyDayCount, @legacyNote, @legacyCode
WHILE @@FETCH_STATUS = 0 
BEGIN 
    // Match minoas leave type from the legacy leave code
    SELECT @minoasLeaveType=t.ID, @minoasLeaveGenerateCDR=t.GENERATES_CDRS  FROM minoas..EMPLOYEE_LEAVE_TYPE t WHERE t.LEGACY_CODE = @legacyCode
    IF(@minoasLeaveType IS NOT NULL)
    BEGIN
        INSERT INTO minoas..EMPLOYEE_LEAVES(VERSION, IS_ACTIVE, COMMENT, DUE_TO, ESTABLISHED, EFFECTIVE_DAYS_COUNT, EMPLOYEE_LEAVE_TYPE_ID, EMPLOYEE_ID, GENERATES_CDRS)
        VALUES(0, 0,@legacyNote, @legacyTo, @legacyFrom, @legacyDayCount, @minoasLeaveType, @minoasEmployeeID, @minoasLeaveGenerateCDR)
    END
    FETCH NEXT FROM @oldLeavesCursor INTO @legacyFrom, @legacyTo, @legacyDayCount, @legacyNote, @legacyCode
END 
CLOSE @oldLeavesCursor 
DEALLOCATE @oldLeavesCursor

END 

SELECT * FROM EMPLOYEE e WHERE e.LAST_NAME LIKE 'ΑΝΔΡΕΑΔ%'

exec minoas..importEmployeeLeave 420029, 8879


// DO THE IMPORT
    BEGIN
        SET NOCOUNT ON;
        DECLARE @legacyEmployeeID varchar(6);
        DECLARE @minoasEmployeeID int;
        DECLARE @cursor CURSOR;
        DECLARE @RetValue int;
        SET @cursor = CURSOR FOR SELECT b.KVD,e.ID  FROM minoas..EMPLOYEE e
            INNER JOIN minoas..REGULAR_EMPLOYEE_INFO i ON i.EMPLOYEE_ID=e.ID
            INNER JOIN mkdb..basiko b ON LTRIM(RTRIM(b.AM))=i.REGISTRY_ID
        WHERE e.EMPLOYEE_TYPE='REGULAR'

        OPEN @cursor 
        FETCH NEXT FROM @cursor INTO @legacyEmployeeID, @minoasEmployeeID;
        WHILE @@FETCH_STATUS = 0 
        BEGIN
            EXEC @RetValue = minoas..importEmployeeLeave @legacyEmployeeID, @minoasEmployeeID
            FETCH NEXT FROM @cursor INTO @legacyEmployeeID, @minoasEmployeeID;
        END
        CLOSE @cursor 
        DEALLOCATE @cursor
    END




