USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importPenalties' AND type = 'P')
   DROP PROCEDURE importPenalties
GO
USE minoas
GO

CREATE PROCEDURE importPenalties

AS 
BEGIN
DECLARE @oldPenaltiesCursor CURSOR; 
DECLARE @legacyID int;
DECLARE @legacyKVD nvarchar(6)
DECLARE @legacyFrom datetime;
DECLARE @legacyTo datetime;
DECLARE @legacyNote nvarchar(33)
DECLARE @legacyCode nvarchar(2)

DECLARE @minoasID int;
DECLARE @minoasLeaveGenerateCDR int;
DECLARE @minosPenaltyType nvarchar(25)
SET NOCOUNT ON;

DELETE slavikos.PENALTY WHERE EMPLOYEE_ID in (select ID from slavikos.EMPLOYEE where LEGACY_CODE in (
SELECT KVD FROM mkdb..bohu where code in (21, 22, 23, 24)))

SET @oldPenaltiesCursor = CURSOR FOR SELECT b.ID, b.KVD, b.APO, b.EVS, b.CODE, b.NOTE
	FROM mkdb..bohu b 
WHERE b.CODE in (21, 22, 23, 24)
    ORDER BY b.KVD

OPEN @oldPenaltiesCursor 
FETCH NEXT FROM @oldPenaltiesCursor INTO @legacyID, @legacyKVD, @legacyFrom, @legacyTo, @legacyCode, @legacyNote
WHILE @@FETCH_STATUS = 0 
BEGIN 
    	SELECT @minoasID=e.ID FROM slavikos.EMPLOYEE e WHERE e.LEGACY_CODE=@legacyKVD
    IF(@minoasID IS NOT NULL)
    BEGIN

		IF(@legacyCode = 21)
		BEGIN
        SET @minosPenaltyType = 'PROSECUTION'
		END
		IF(@legacyCode = 22)
		BEGIN
        SET @minosPenaltyType = 'DISEASE'
		END
		IF(@legacyCode = 23)
		BEGIN
        SET @minosPenaltyType = 'UNAUTHORIZED_ABSENCE'
		END
		IF(@legacyCode = 24)
		BEGIN
        SET @minosPenaltyType = 'TEMPORARY_TERMINATION'
		END

		INSERT INTO slavikos.PENALTY(INSERTED_ON, VERSION, PENALTY_TYPE, PENALTY_START_DATE, PENALTY_END_DATE, COMMENT, EMPLOYEE_ID)
        VALUES(CURRENT_TIMESTAMP, 0, @minosPenaltyType, @legacyFrom, @legacyTo, @legacyNote, @minoasID)
		
    END
    FETCH NEXT FROM @oldPenaltiesCursor INTO @legacyID, @legacyKVD, @legacyFrom, @legacyTo, @legacyCode, @legacyNote
END 
CLOSE @oldPenaltiesCursor 
DEALLOCATE @oldPenaltiesCursor

END 

exec minoas..importPenalties
