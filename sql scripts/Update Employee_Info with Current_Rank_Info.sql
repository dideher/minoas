
set transaction isolation level read committed;
begin tran;

	DECLARE @ID int;
	DECLARE @EMPLOYEE_INFO_ID int;
	DECLARE @RankInfoCursor CURSOR; 
	

	SET NOCOUNT ON;

	SET @RankInfoCursor = CURSOR FOR select ID, EMPLOYEE_INFO_ID
	from minoas.slavikos.RANK_INFO

	OPEN @RankInfoCursor 
	FETCH NEXT FROM @RankInfoCursor INTO @ID, @EMPLOYEE_INFO_ID
	WHILE @@FETCH_STATUS = 0
	BEGIN 

		IF(@EMPLOYEE_INFO_ID IS NOT NULL)
		BEGIN
		 update minoas.slavikos.EMPLOYEE_INFO set CURRENT_RANK_INFO_ID = @ID 
		 where ID = @EMPLOYEE_INFO_ID
		END

		FETCH NEXT FROM @RankInfoCursor INTO @ID, @EMPLOYEE_INFO_ID
	END 
	
	CLOSE @RankInfoCursor 
	DEALLOCATE @RankInfoCursor

commit tran





rollback tran

select * from minoas.slavikos.EMPLOYEE where ID=6413
select * from minoas.slavikos.EMPLOYEE_INFO where ID=7058
select * from minoas.slavikos.RANK_INFO where ID=7064