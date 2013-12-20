select * from basiko where AM = 219330

select e.ID, e.LEGACY_CODE, e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, rei.REGISTRY_ID 
from minoas..EMPLOYEE e, minoas..REGULAR_EMPLOYEE_INFO rei
where e.IS_ACTIVE = 1 and e.LEGACY_CODE is not null and e.EMPLOYEE_TYPE = 'REGULAR'
and e.REGULAR_EMPLOYMEE_INFO_ID = rei.ID


######  Employees me LEGACY_CODE twn opoiwn to REGULAR_EMPLOYMEE_INFO_ID einai NULL ######
select e.ID, e.LEGACY_CODE, e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, e.REGULAR_EMPLOYMEE_INFO_ID
from minoas..EMPLOYEE e
where e.IS_ACTIVE = 1 and e.LEGACY_CODE is not null and e.EMPLOYEE_TYPE = 'REGULAR'
and e.ID not in (
select e.ID
from minoas..EMPLOYEE e, minoas..REGULAR_EMPLOYEE_INFO rei
where e.IS_ACTIVE = 1 and e.LEGACY_CODE is not null and e.EMPLOYEE_TYPE = 'REGULAR'
and e.REGULAR_EMPLOYMEE_INFO_ID = rei.ID

)




### Active Regular Employees me LegacyCode tvn opown h hm/nia FEK den symfwnei me Spanoudaki #### 
select e.ID, e.LEGACY_CODE, e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, rei.REGISTRY_ID, rei.GOG_APPOINTMENT_NO, rei.GOG_APPOINTMENT_DATE, b.FEK, b.DFEK, b.PR_MONIM, b.DA_MONIM, b.DGEN
from minoas..EMPLOYEE e, minoas..REGULAR_EMPLOYEE_INFO rei, mkdb..basiko b
where e.IS_ACTIVE = 1 and e.LEGACY_CODE is not null and e.EMPLOYEE_TYPE = 'REGULAR'
and e.REGULAR_EMPLOYMEE_INFO_ID = rei.ID
and b.KVD is not null and b.ANHKEI_ID <> 9
and e.LEGACY_CODE = b.KVD
and (rei.GOG_APPOINTMENT_DATE <> b.DFEK OR rei.GOG_APPOINTMENT_NO <> RTRIM(LTRIM(b.FEK)))


### Active Regular Employees με LegacyCode των οποίων η Πράξη Μονιμοποίησης δεν συμφωνεί με Σπανουδάκη #### 
select e.ID, e.LEGACY_CODE, e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, rei.REGISTRY_ID, rei.PERMANENT_EMPLOYEE_ACT, rei.PERMANENT_EMPLOYEE_ACT_DATE, b.PR_MONIM, b.DA_MONIM
from minoas..EMPLOYEE e, minoas..REGULAR_EMPLOYEE_INFO rei, mkdb..basiko b
where e.IS_ACTIVE = 1 and e.LEGACY_CODE is not null and e.EMPLOYEE_TYPE = 'REGULAR'
and e.REGULAR_EMPLOYMEE_INFO_ID = rei.ID
and b.KVD is not null and b.ANHKEI_ID <> 9
and e.LEGACY_CODE = b.KVD
and (rei.PERMANENT_EMPLOYEE_ACT_DATE <> b.DA_MONIM OR rei.PERMANENT_EMPLOYEE_ACT <> b.PR_MONIM)





select * from mkdb..basiko where KVD is not null and ANHKEI_ID <> 9