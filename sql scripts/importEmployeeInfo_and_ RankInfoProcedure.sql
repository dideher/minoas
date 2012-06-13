USE minoas
IF EXISTS (SELECT name FROM sysobjects 
         WHERE name = 'importEmployeeInfoAndRankInfo' AND type = 'P')
   DROP PROCEDURE importEmployeeInfoAndRankInfo
GO
USE minoas
GO

CREATE PROCEDURE importEmployeeInfoAndRankInfo
AS 
BEGIN

	DECLARE @EMPLOYEE_ID int;
	DECLARE @LAST_RANK_INFO_ID int;
	DECLARE @LAST_EMPLOYEE_INFO_ID int;

	DECLARE @KatataxiCursor CURSOR; 
	DECLARE @ID int;
	DECLARE @AM nvarchar(255);
	DECLARE @SURNAME nvarchar(255);
	DECLARE @NAME nvarchar(255);
	DECLARE @FATHERNAME nvarchar(255);
	DECLARE @EDUCATIONAL_LEVEL nvarchar(255);
	DECLARE @GOG_APPOINTMENT_NO int;
	DECLARE @GOG_APPOINTMENT_DATE datetime;
	DECLARE @proyphresia int;
	DECLARE @pragm_proyphresia int;
	DECLARE @proyphresia_metaptyx int; 
	DECLARE @days_not_for_promotion int;
	DECLARE @HAS_A_MASTER_DEGREE bit;
	DECLARE @MSC_DATE datetime;
	DECLARE @HAS_A_PHD_DEGREE bit;
	DECLARE @PHD_DATE datetime;
	DECLARE @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE bit;
	DECLARE @POSTGRAD nvarchar(255);
	DECLARE @POSTGRAD_DATE datetime;
	DECLARE @RANK nvarchar(255);
	DECLARE @SURPLUS_TIME_IN_RANK int;
	DECLARE @SALARY_GRADE int
	DECLARE @SURPLUS_TIME_IN_SALARY_GRADE int
	DECLARE @ENTRY_INTO_SERVICE_ACT nvarchar(255);
	DECLARE @ENTRY_INTO_SERVICE_DATE datetime;
	DECLARE @PERMANENT_EMPLOYEE_ACT nvarchar(255);
	DECLARE @PERMANENT_EMPLOYEE_ACT_DATE datetime;
	DECLARE @NEODIORISMENOS bit
	SET NOCOUNT ON;

	UPDATE minoas.slavikos.RANK_INFO SET EMPLOYEE_INFO_ID= null
	DELETE slavikos.EMPLOYEE_INFO	
	DELETE slavikos.RANK_INFO	
	
	

	SET @KatataxiCursor = CURSOR FOR select 
			ID, AM, SURNAME, NAME, FATHERNAME, EDUCATIONAL_LEVEL, GOG_APPOINTMENT_NO, APPOINTMENT_GOG_DATE,
			proyphresia, pragm_proyphresia, proyphresia_metaptyx, days_not_for_promotion, HAS_A_MASTER_DEGREE,
			MSC_DATE, HAS_A_PHD_DEGREE, PHD_DATE, IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			RANK, SURPLUS_TIME_IN_RANK, SALARY_GRADE, SURPLUS_TIME_IN_SALARY_GRADE, ENTRY_INTO_SERVICE_ACT,
			ENTRY_INTO_SERVICE_DATE, PERMANENT_EMPLOYEE_ACT, PERMANENT_EMPLOYEE_ACT_DATE, NEODIORISMENOS
		from mkdb.dbo.Katataxi_Import where AM in (
		select rei.REGISTRY_ID from minoas.slavikos.REGULAR_EMPLOYEE_INFO rei, minoas.slavikos.EMPLOYEE e 
		where rei.EMPLOYEE_ID = e.ID
		and rei.REGISTRY_ID is not null 
		and rei.REGISTRY_ID <> 0
		and e.IS_ACTIVE = 1
	) 

	OPEN @KatataxiCursor 
	FETCH NEXT FROM @KatataxiCursor INTO @ID, @AM, @SURNAME, @NAME, @FATHERNAME, @EDUCATIONAL_LEVEL, @GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
		@proyphresia, @pragm_proyphresia, @proyphresia_metaptyx, @days_not_for_promotion, @HAS_A_MASTER_DEGREE,
		@MSC_DATE, @HAS_A_PHD_DEGREE, @PHD_DATE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
		@RANK, @SURPLUS_TIME_IN_RANK, @SALARY_GRADE, @SURPLUS_TIME_IN_SALARY_GRADE, @ENTRY_INTO_SERVICE_ACT,
		@ENTRY_INTO_SERVICE_DATE, @PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE, @NEODIORISMENOS
	WHILE @@FETCH_STATUS = 0 
	BEGIN 

		SELECT @EMPLOYEE_ID=e.ID 
		FROM minoas.slavikos.EMPLOYEE e, minoas.slavikos.REGULAR_EMPLOYEE_INFO rei
		WHERE rei.EMPLOYEE_ID = e.ID AND e.IS_ACTIVE=1 AND
				rei.REGISTRY_ID=@AM

		IF(@EMPLOYEE_ID IS NOT NULL)
		BEGIN

			INSERT INTO slavikos.RANK_INFO(INSERTED_ON, VERSION, 
				RANK, SALARY_GRADE, LAST_RANK_DATE, LAST_SALARY_GRADE_DATE, 
				EDUCATIONAL_LEVEL, 
				SURPLUS_TIME_IN_RANK, SURPLUS_TIME_IN_SALARY_GRADE)
			VALUES(CURRENT_TIMESTAMP, 0,
				@RANK, @SALARY_GRADE, '20111101', '20111101',
				@EDUCATIONAL_LEVEL,
				@SURPLUS_TIME_IN_RANK, @SURPLUS_TIME_IN_SALARY_GRADE)

			SELECT @LAST_RANK_INFO_ID=ID from minoas.slavikos.RANK_INFO where ID = SCOPE_IDENTITY()

			
			IF(@LAST_RANK_INFO_ID IS NOT NULL)
			BEGIN

				INSERT INTO slavikos.EMPLOYEE_INFO(INSERTED_ON, VERSION, 
					GOG_APPOINTMENT_NO, GOG_APPOINTMENT_DATE, 
					ENTRY_INTO_SERVICE_ACT, ENTRY_INTO_SERVICE_DATE, 
					HAS_A_MASTER_DEGREE, HAS_A_PHD_DEGREE, IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
					MSC_DATE, PHD_DATE, NAT_SCH_PUB_ADMIN_DATE, 
					PERMANENT_EMPLOYEE_ACT, PERMANENT_EMPLOYEE_ACT_DATE, 
					IS_RECENTLY_HIRED, EMPLOYEE_ID, RANK_INFO_ID)
				VALUES(CURRENT_TIMESTAMP, 0,
					@GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
					@ENTRY_INTO_SERVICE_ACT, @ENTRY_INTO_SERVICE_DATE,
					@HAS_A_MASTER_DEGREE, @HAS_A_PHD_DEGREE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
					@MSC_DATE, @PHD_DATE, null, 
					@PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE,
					@NEODIORISMENOS, @EMPLOYEE_ID, @LAST_RANK_INFO_ID
				)
				
				SELECT @LAST_EMPLOYEE_INFO_ID=ID from minoas.slavikos.EMPLOYEE_INFO where ID = SCOPE_IDENTITY()

				UPDATE minoas.slavikos.RANK_INFO
					SET EMPLOYEE_INFO_ID = @LAST_EMPLOYEE_INFO_ID
				WHERE ID = @LAST_RANK_INFO_ID;
			END
		END


		FETCH NEXT FROM @KatataxiCursor INTO @ID, @AM, @SURNAME, @NAME, @FATHERNAME, @EDUCATIONAL_LEVEL, @GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
			@proyphresia, @pragm_proyphresia, @proyphresia_metaptyx, @days_not_for_promotion, @HAS_A_MASTER_DEGREE,
			@MSC_DATE, @HAS_A_PHD_DEGREE, @PHD_DATE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			@RANK, @SURPLUS_TIME_IN_RANK, @SALARY_GRADE, @SURPLUS_TIME_IN_SALARY_GRADE, @ENTRY_INTO_SERVICE_ACT,
			@ENTRY_INTO_SERVICE_DATE, @PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE, @NEODIORISMENOS
	END 
	
	CLOSE @KatataxiCursor 
	DEALLOCATE @KatataxiCursor
END



exec minoas..importEmployeeInfoAndRankInfo



	DELETE slavikos.EVOLUTION_CDR
	DELETE slavikos.EVALUATION



use mkdb
select * from Katataxi_Import where AM in (

select rei.REGISTRY_ID from minoas.slavikos.REGULAR_EMPLOYEE_INFO rei, minoas.slavikos.EMPLOYEE e 
where rei.EMPLOYEE_ID = e.ID
and rei.REGISTRY_ID is not null 
and rei.REGISTRY_ID <> 0
and e.IS_ACTIVE = 1

)

select count(*) from minoas.slavikos.REGULAR_EMPLOYEE_INFO rei, minoas.slavikos.EMPLOYEE e 
where rei.EMPLOYEE_ID = e.ID
and rei.REGISTRY_ID is not null 
and rei.REGISTRY_ID <> 0
and e.IS_ACTIVE = 1

select * from basiko where AM in (select AM from Katataxi_Import)

select * from Katataxi_Import where AM is null


select * from Katataxi_Import where AM in (select REGISTRY_ID from minoas.slavikos.REGULAR_EMPLOYEE_INFO)


select REGISTRY_ID from minoas.slavikos.REGULAR_EMPLOYEE_INFO where REGISTRY_ID is not null and REGISTRY_ID <> 0



select * from minoas.slavikos.REGULAR_EMPLOYEE_INFO where REGISTRY_ID in (select AM from Katataxi_Import)


insert into tableTest(testUserEmail,testUserName) 
output inserted.ID
values (@testValue, @testName)



INSERT INTO minoas.slavikos.RANK_INFO(INSERTED_ON, VERSION, 
		RANK, SALARY_GRADE, LAST_RANK_DATE, LAST_SALARY_GRADE_DATE, 
		EDUCATIONAL_LEVEL, EMPLOYEE_INFO_ID, 
		SURPLUS_TIME_IN_RANK, SURPLUS_TIME_IN_SALARY_GRADE)
VALUES(CURRENT_TIMESTAMP, 0, 'RANK_D', 9, '20111101', CURRENT_TIMESTAMP, 'UNIVERSITY_EDUCATION_LEVEL', 1, 68, 69)

SELECT * from minoas.slavikos.RANK_INFO where ID = SCOPE_IDENTITY()

OUTPUT INSERTED.ID

use minoas
SELECT REGISTRY_ID, count(REGISTRY_ID) FROM minoas.slavikos.REGULAR_EMPLOYEE_INFO 
GROUP BY REGISTRY_ID HAVING count(REGISTRY_ID) > 1

use mkdb
select * from Katataxi_Import where AM in (117576, 1234567, 200741)

select * from minoas.slavikos.REGULAR_EMPLOYEE_INFO where REGISTRY_ID = 200741

select * from minoas.slavikos.EMPLOYEE where ID in (7662, 10047)

update mkdb.dbo.Katataxi_Import set rank = 'RANK_A' where rank = 'а'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_B' where rank = 'б'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_C' where rank = 'ц'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_D' where rank = 'д'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_E' where rank = 'е'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_ST' where rank = 'ст'

select * from minoas.slavikos.EMPLOYEE_INFO where IS_RECENTLY_HIRED = 1