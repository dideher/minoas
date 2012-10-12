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
	DECLARE @KVD int;
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
	DECLARE @SALARY_GRADE int;
	DECLARE @SURPLUS_TIME_IN_SALARY_GRADE int
	DECLARE @ENTRY_INTO_SERVICE_ACT nvarchar(255);
	DECLARE @ENTRY_INTO_SERVICE_DATE datetime;
	DECLARE @PERMANENT_EMPLOYEE_ACT nvarchar(255);
	DECLARE @PERMANENT_EMPLOYEE_ACT_DATE datetime;
	DECLARE @NEODIORISMENOS bit;
	DECLARE @SECTOR nvarchar(255);

	SET NOCOUNT ON;

	UPDATE minoas.slavikos.RANK_INFO SET EMPLOYEE_INFO_ID= null
	DELETE slavikos.EMPLOYEE_INFO	
	DELETE slavikos.RANK_INFO	
	
	

	SET @KatataxiCursor = CURSOR FOR select 
			KVD, AM, SURNAME, NAME, FATHERNAME, EDUCATIONAL_LEVEL, GOG_APPOINTMENT_NO, APPOINTMENT_GOG_DATE,
			proyphresia, pragm_proyphresia, proyphresia_metaptyx, days_not_for_promotion, HAS_A_MASTER_DEGREE,
			MSC_DATE, HAS_A_PHD_DEGREE, PHD_DATE, IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			RANK, SURPLUS_TIME_IN_RANK, SALARY_GRADE, SURPLUS_TIME_IN_SALARY_GRADE, ENTRY_INTO_SERVICE_ACT,
			ENTRY_INTO_SERVICE_DATE, PERMANENT_EMPLOYEE_ACT, PERMANENT_EMPLOYEE_ACT_DATE, NEODIORISMENOS
		from mkdb.dbo.Katataxi_Import where KVD in (

		select distinct(e.LEGACY_CODE) from minoas.slavikos.EMPLOYEE e 
		where e.IS_ACTIVE = 1
		

	) 

	OPEN @KatataxiCursor 
	FETCH NEXT FROM @KatataxiCursor INTO @KVD, @AM, @SURNAME, @NAME, @FATHERNAME, @EDUCATIONAL_LEVEL, @GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
		@proyphresia, @pragm_proyphresia, @proyphresia_metaptyx, @days_not_for_promotion, @HAS_A_MASTER_DEGREE,
		@MSC_DATE, @HAS_A_PHD_DEGREE, @PHD_DATE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
		@RANK, @SURPLUS_TIME_IN_RANK, @SALARY_GRADE, @SURPLUS_TIME_IN_SALARY_GRADE, @ENTRY_INTO_SERVICE_ACT,
		@ENTRY_INTO_SERVICE_DATE, @PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE, @NEODIORISMENOS
	WHILE @@FETCH_STATUS = 0 
	BEGIN 

		SELECT @EMPLOYEE_ID=e.ID 
		FROM minoas.slavikos.EMPLOYEE e
		WHERE e.IS_ACTIVE=1 AND e.LEGACY_CODE = @KVD

		select @SECTOR = IE_ID from mkdb.dbo.basiko where KVD = @KVD

		IF(@SECTOR = '1')
			BEGIN
				set @SECTOR = 'PUBLIC_SECTOR'
			END
		ELSE 
		IF(@SECTOR = '2')
			BEGIN
				set @SECTOR = 'PRIVATE_SECTOR'
			END


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
					IS_RECENTLY_HIRED, EMPLOYEE_ID, CURRENT_RANK_INFO_ID, SECTOR)
				VALUES(CURRENT_TIMESTAMP, 0,
					@GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
					@ENTRY_INTO_SERVICE_ACT, @ENTRY_INTO_SERVICE_DATE,
					@HAS_A_MASTER_DEGREE, @HAS_A_PHD_DEGREE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
					@MSC_DATE, @PHD_DATE, null, 
					@PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE,
					@NEODIORISMENOS, @EMPLOYEE_ID, @LAST_RANK_INFO_ID, @SECTOR
				)
				
				SELECT @LAST_EMPLOYEE_INFO_ID=ID from minoas.slavikos.EMPLOYEE_INFO where ID = SCOPE_IDENTITY()

				UPDATE minoas.slavikos.RANK_INFO
					SET EMPLOYEE_INFO_ID = @LAST_EMPLOYEE_INFO_ID
				WHERE ID = @LAST_RANK_INFO_ID;
			END
		END


		FETCH NEXT FROM @KatataxiCursor INTO @KVD, @AM, @SURNAME, @NAME, @FATHERNAME, @EDUCATIONAL_LEVEL, @GOG_APPOINTMENT_NO, @GOG_APPOINTMENT_DATE,
			@proyphresia, @pragm_proyphresia, @proyphresia_metaptyx, @days_not_for_promotion, @HAS_A_MASTER_DEGREE,
			@MSC_DATE, @HAS_A_PHD_DEGREE, @PHD_DATE, @IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			@RANK, @SURPLUS_TIME_IN_RANK, @SALARY_GRADE, @SURPLUS_TIME_IN_SALARY_GRADE, @ENTRY_INTO_SERVICE_ACT,
			@ENTRY_INTO_SERVICE_DATE, @PERMANENT_EMPLOYEE_ACT, @PERMANENT_EMPLOYEE_ACT_DATE, @NEODIORISMENOS
	END 
	

	update minoas.slavikos.RANK_INFO set rank = 'RANK_A' where rank = 'Α'
	update minoas.slavikos.RANK_INFO set rank = 'RANK_B' where rank = 'Β'
	update minoas.slavikos.RANK_INFO set rank = 'RANK_C' where rank = 'Γ'
	update minoas.slavikos.RANK_INFO set rank = 'RANK_D' where rank = 'Δ'
	update minoas.slavikos.RANK_INFO set rank = 'RANK_E' where rank = 'Ε'
	update minoas.slavikos.RANK_INFO set rank = 'RANK_ST' where rank = 'ΣΤ'

	CLOSE @KatataxiCursor 
	DEALLOCATE @KatataxiCursor
END



exec minoas..importEmployeeInfoAndRankInfo




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



use minoas
SELECT REGISTRY_ID, count(REGISTRY_ID) FROM minoas.slavikos.REGULAR_EMPLOYEE_INFO 
GROUP BY REGISTRY_ID HAVING count(REGISTRY_ID) > 1

use mkdb
select * from Katataxi_Import where AM in (117576, 1234567, 200741)

select * from minoas.slavikos.REGULAR_EMPLOYEE_INFO where REGISTRY_ID = 300058

select * from minoas.slavikos.EMPLOYEE where ID in (7662, 10047)

update mkdb.dbo.Katataxi_Import set rank = 'RANK_A' where rank = 'Α'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_B' where rank = 'Β'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_C' where rank = 'Γ'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_D' where rank = 'Δ'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_E' where rank = 'Ε'
update mkdb.dbo.Katataxi_Import set rank = 'RANK_ST' where rank = 'ΣΤ'

select * from minoas.slavikos.EMPLOYEE_INFO where IS_RECENTLY_HIRED = 1

// Όλοι της Ιδιωτικής Εκπαίδευσης
select * from mkdb.dbo.basiko where IE_ID=2 and ANHKEI_ID <> 9

select * from minoas.slavikos.EMPLOYEE_INFO
select * from minoas.slavikos.RANK_INFO

update minoas.slavikos.EMPLOYEE_INFO set SECTOR='PUBLIC_SECTOR'

select * from minoas.slavikos.EMPLOYEE where LEGACY_CODE = 900652
select * from minoas.slavikos.REGULAR_EMPLOYEE_INFO where EMPLOYEE_ID=6603

select ID, EMPLOYEE_TYPE from minoas.slavikos.EMPLOYEE where ID not in (select distinct EMPLOYEE_ID from minoas.slavikos.REGULAR_EMPLOYEE_INFO) and EMPLOYEE_TYPE ='REGULAR'

select 
			KVD, AM, SURNAME, NAME, FATHERNAME, EDUCATIONAL_LEVEL, GOG_APPOINTMENT_NO, APPOINTMENT_GOG_DATE,
			proyphresia, pragm_proyphresia, proyphresia_metaptyx, days_not_for_promotion, HAS_A_MASTER_DEGREE,
			MSC_DATE, HAS_A_PHD_DEGREE, PHD_DATE, IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			RANK, SURPLUS_TIME_IN_RANK, SALARY_GRADE, SURPLUS_TIME_IN_SALARY_GRADE, ENTRY_INTO_SERVICE_ACT,
			ENTRY_INTO_SERVICE_DATE, PERMANENT_EMPLOYEE_ACT, PERMANENT_EMPLOYEE_ACT_DATE, NEODIORISMENOS
		from mkdb.dbo.Katataxi_Import where KVD in (

		select distinct(e.LEGACY_CODE) from minoas.slavikos.EMPLOYEE e 
		where e.IS_ACTIVE = 1

	) order by NEODIORISMENOS



select 
			KVD, AM, SURNAME, NAME, FATHERNAME, EDUCATIONAL_LEVEL, GOG_APPOINTMENT_NO, APPOINTMENT_GOG_DATE,
			proyphresia, pragm_proyphresia, proyphresia_metaptyx, days_not_for_promotion, HAS_A_MASTER_DEGREE,
			MSC_DATE, HAS_A_PHD_DEGREE, PHD_DATE, IS_A_NAT_SCH_PUB_ADMIN_GRADUATE,
			RANK, SURPLUS_TIME_IN_RANK, SALARY_GRADE, SURPLUS_TIME_IN_SALARY_GRADE, ENTRY_INTO_SERVICE_ACT,
			ENTRY_INTO_SERVICE_DATE, PERMANENT_EMPLOYEE_ACT, PERMANENT_EMPLOYEE_ACT_DATE, NEODIORISMENOS
		from mkdb.dbo.Katataxi_Import where AM in (

		select distinct(rei.REGISTRY_ID) from minoas.slavikos.REGULAR_EMPLOYEE_INFO rei, minoas.slavikos.EMPLOYEE e 
		where rei.EMPLOYEE_ID = e.ID
		and rei.REGISTRY_ID is not null 
		and rei.REGISTRY_ID <> 0
		and e.IS_ACTIVE = 1
	)  order by NEODIORISMENOS


select * from minoas.slavikos.EMPLOYEE where ID=8879
select * from minoas.slavikos.EMPLOYEE_INFO where EMPLOYEE_ID=8879
select * from minoas.slavikos.RANK_INFO where EMPLOYEE_INFO_ID=8510

update minoas.slavikos.EMPLOYEE_INFO set CURRENT_RANK_INFO_ID = 8516 where ID = 8510
delete minoas.slavikos.RANK_INFO where id=9417
                          

select * from minoas.slavikos.EMPLOYEE where ID=6413
select * from minoas.slavikos.EMPLOYEE_INFO where ID=7058
select * from minoas.slavikos.RANK_INFO where ID=7064