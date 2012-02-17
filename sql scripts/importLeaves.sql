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


SET @oldLeavesCursor = CURSOR FOR SELECT b.APO, b.EVS, b.ARHMER, b.NOTE, b.CODE FROM mkdb..bohu b WHERE b.KVD=@legacyEmployeeID 

OPEN @oldLeavesCursor 
FETCH NEXT FROM @oldLeavesCursor INTO @legacyFrom, @legacyTo, @legacyDayCount, @legacyNote, @legacyCode
WHILE @@FETCH_STATUS = 0 
BEGIN 
    // Match minoas leave type from the legacy leave code
     SELECT @minoasLeaveType=t.ID, @minoasLeaveGenerateCDR=t.GENERATES_CDRS  FROM minoas..EMPLOYEE_LEAVE_TYPE t WHERE t.LEGACY_CODE = @legacyCode
    IF(@minoasLeaveType= NULL)
    BEGIN
        PRINT 'COULD NOT MAP LEAVE TYPE'
        RETURN
    END
    INSERT INTO minoas..EMPLOYEE_LEAVES(VERSION, IS_ACTIVE, COMMENT, DUE_TO, ESTABLISHED, EFFECTIVE_DAYS_COUNT, EMPLOYEE_LEAVE_TYPE_ID, EMPLOYEE_ID)
        VALUES(0, 0,@legacyNote, @legacyTo, @legacyFrom, @legacyDayCount, @minoasLeaveType, @minoasEmployeeID)
    FETCH NEXT FROM @oldLeavesCursor INTO @legacyFrom, @legacyTo, @legacyDayCount, @legacyNote, @legacyCode
END 
CLOSE @oldLeavesCursor 
DEALLOCATE @oldLeavesCursor

END 

