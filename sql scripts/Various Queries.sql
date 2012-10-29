select sum(ACTUAL_DAYS) from minoas.slavikos.WORK_EXPERIENCE 
where IS_ACTIVE=1 and EDUCATIONAL=1 and EMPLOYEE_ID=8879


select sum(ACTUAL_DAYS) from minoas.slavikos.WORK_EXPERIENCE 
where IS_ACTIVE=1 and EMPLOYEE_ID=8879


select * from minoas.slavikos.EMPLOYEE where id = 7507


select * from minoas.slavikos.PENALTY

select * from minoas.slavikos.EMPLOYEE where ID=8879
select * from minoas.slavikos.EMPLOYEE_INFO where EMPLOYEE_ID=8879
select * from minoas.slavikos.RANK_INFO where EMPLOYEE_INFO_ID=8510


select * from minoas.slavikos.WORK_EXPERIENCE where EMPLOYEE_ID=8879



select distinct COMMENT from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID in(3, 4)
and ( COMMENT not like '%>>%' and COMMENT not like '%тее%' and COMMENT not like '%тек%'
and COMMENT not like '%дГЛ%' and COMMENT not like '%цУЛМ%' and COMMENT not like '%аМАПК%'
and COMMENT not like '%пдс%' and COMMENT not like '%ц/сио%' and COMMENT not like '%куй%' and COMMENT not like '%кЩЙ%'
and COMMENT not like '%цек%' and COMMENT not like '%епак%' and COMMENT not like '%е.к.%'
and COMMENT not like '%дс%' and COMMENT not like '%ку %' and COMMENT not like '%цу %'
and COMMENT not like '%лпй%' and COMMENT not like '%д.св%' and COMMENT not like '%т.е.к%'
and COMMENT not like '%т.е.с%' and COMMENT not like '%йете%' and COMMENT not like '%сумоко%'
and COMMENT not like '%е.п.к%' and COMMENT not like '%ееее%' and COMMENT not like '%ек %'
and COMMENT not like '%тес %' and COMMENT not like '%т.е.е%' and COMMENT not like '%своке%'
and COMMENT not like '%сибитам%' and COMMENT not like '%сей %' and COMMENT not like '%сде%'
and COMMENT not like '%пяосх%' and COMMENT not like '%пе %' and COMMENT not like '%памеп%'
and COMMENT not like '%пАМ/ЛИО%' and COMMENT not like '%оКОчЛ%' and COMMENT not like '%мгп%'
and COMMENT not like '%лОУСИЙ%' and COMMENT not like '%л.п.й%' and COMMENT not like '%к.%'
and COMMENT not like '%й.е.т.е%' and COMMENT not like '%епк %' and COMMENT not like '%еспея%'
and COMMENT not like '%ек. %' and COMMENT not like '%е. к.%' and COMMENT not like '%д.с%'
and COMMENT not like '%цул/сио%' and COMMENT not like '%д/мсг%' and COMMENT not like '%цул.%'
and COMMENT not like '%цк %' and COMMENT not like '%ц.%' and COMMENT not like '%а/хлиа%')

order by comment

select * from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID in(3, 4)
and ( COMMENT like '%теи%' or COMMENT like '%т.е.и.%' or COMMENT like '%памеп%' or COMMENT like '%мпдд%'
or COMMENT like '%м.п.д.д.%' or COMMENT like '%ией%' or COMMENT like '%и.е.й.%')


select * from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID = 8


update minoas.slavikos.WORK_EXPERIENCE set TEACHING=1 where EXPERIENCE_TYPE_ID = 8