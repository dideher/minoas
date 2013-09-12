SELECT     e.LAST_SPECIALIZATION_ID, e.LAST_NAME, e.FIRST_NAME, el.ESTABLISHED, el.DUE_TO, el.COMMENT, el.EMPLOYEE_LEAVE_TYPE_ID, 
                      elt.DESCRIPTION
FROM         minoas.slavikos.EMPLOYEE_LEAVES AS el INNER JOIN
                      minoas.slavikos.EMPLOYEE_LEAVE_TYPE AS elt ON el.EMPLOYEE_LEAVE_TYPE_ID = elt.ID INNER JOIN
                      minoas.slavikos.EMPLOYEE AS e ON el.EMPLOYEE_ID = e.ID
WHERE     (el.IS_DELETED IS NOT NULL) AND (el.IS_DELETED = 0) AND (el.ESTABLISHED >= '20120901') AND (el.ESTABLISHED <= '20130212') AND 
                      (elt.LEGACY_CODE IN (41, 55, 45, 46, 37)) OR
                      (el.DUE_TO >= '20130901') AND (el.DUE_TO <= '20130212')
ORDER BY el.DUE_TO