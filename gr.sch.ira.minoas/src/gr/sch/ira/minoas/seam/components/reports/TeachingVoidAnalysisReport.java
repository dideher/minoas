package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationGroupSearchType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 */
@Name(value = "teachingVoidAnalysisReport")
@Scope(ScopeType.PAGE)
public class TeachingVoidAnalysisReport extends BaseReport {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public static final int HOURS_FOR_REGULAR_POSITION = 18;

    private Character region;

    private Boolean regularEmploymentsOnly = Boolean.FALSE;
    
    @DataModel(value = "teachingVoidReport")
    private List<Map<String, Object>> teachingVoidReport;

    
    private School school;

    private SpecializationGroup specializationGroup;

    private List<SpecializationGroup> specializationGroups = new ArrayList<SpecializationGroup>();

    @Out(required = false, scope = ScopeType.CONVERSATION)
    private List<SpecializationGroup> availableSpecializationGroupsForVoidAnalysisReport;

    private SpecializationGroupSearchType specializationGroupSearchType = SpecializationGroupSearchType.SINGLE_SPECIALIZATION_GROUP;

    @Factory("availableSpecializationGroupsForVoidAnalysisReport")
    public void constructAvailableSpecializationGroupList() {
        EntityManager em = getEntityManager();
        this.availableSpecializationGroupsForVoidAnalysisReport = new ArrayList(getCoreSearching().getSpecializationGroups(
                getCoreSearching().getActiveSchoolYear(em), em));
    }

    public void resetReport() {
        info("reseting report.");
        setTeachingVoidReport(new ArrayList<Map<String,Object>>());
        school = null;
        region = null;
        specializationGroup = null;
        specializationGroups = new ArrayList<SpecializationGroup>();
        specializationGroupSearchType = SpecializationGroupSearchType.SINGLE_SPECIALIZATION_GROUP;
        constructAvailableSpecializationGroupList();
    }
    public void generateReport() {

        try {
            long started = System.currentTimeMillis(), finished;
            
            /* our report data contains a list contaning a map with key-values. Each map entry coresponds to group report item. 
             * Each group report item has several attributes, like 'title' and 'groups' and a special 'data' which contains
             * another hash map contained the rows (the actual report data) for the given group report item. For example :
             * 
             * "title" - > "ΠΕ19-20" (String)
             * "groups" -> {SpecializationGroup}
             * "data" ->
             *    "ΓΥ ΜΟΙΡΩΝΑ", xx, yy
             *    "ΓΥ ΜΠΛΑ", zz,ff
             */
    
            List<Map<String, Object>> tempReportData = new ArrayList<Map<String,Object>>();
            
            info("generating report ");

            /* get the active school year */
            SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

            /* According to the user's request, construct an array of all specialization groups
             * that need to appear in the report
             */
            Collection<SpecializationGroup> reportSpecializationGroups = null;
            if (getSpecializationGroupSearchType() == SpecializationGroupSearchType.SINGLE_SPECIALIZATION_GROUP) {
                /* One specialization group type requested, therefore the report will contain 
                 * either the user's selected specialization group or all specialization groups if
                 * the user left the field unspecified
                 */
                if (getSpecializationGroup() != null) {
                    /* user has specified a concrete specialization group */
                    reportSpecializationGroups = new ArrayList<SpecializationGroup>(1);
                    reportSpecializationGroups.add(getSpecializationGroup());
                    Map<String, Object> item = new LinkedHashMap<String, Object>();
                    item.put("title", getSpecializationGroup().getTitle());
                    item.put("groups", reportSpecializationGroups);
                    tempReportData.add(item);
                    
                } else {
                    /* user did not specified a concrete specialization group,
                     * so fetch all groups
                     */
                    reportSpecializationGroups = getCoreSearching().getSpecializationGroups(activeSchoolYear,
                            getEntityManager());
                    for(SpecializationGroup group : reportSpecializationGroups) {
                        Map<String, Object> item = new LinkedHashMap<String, Object>();
                        item.put("title", group.getTitle());
                        List<SpecializationGroup> h = new ArrayList<SpecializationGroup>();
                        h.add(group);
                        item.put("groups", h);
                        tempReportData.add(item);
                    }
                }
            } else {
                /* user has selected many specialization groups */
                reportSpecializationGroups = new ArrayList<SpecializationGroup>(getSpecializationGroups().size());
                StringBuffer sb = new StringBuffer();
                for(SpecializationGroup group : getSpecializationGroups()) {
                    sb.append(group.getTitle());
                    sb.append(" ");
                }
                reportSpecializationGroups.addAll(getSpecializationGroups());
                Map<String, Object> item = new LinkedHashMap<String, Object>();
                item.put("title", sb.toString());
                item.put("groups", reportSpecializationGroups);
                tempReportData.add(item);
            }
            
            /* Determine on which schools the report should be applied */

            Collection<School> schools = null;
            
            if (school != null) {
                schools = new ArrayList<School>(1);
                schools.add(school);
            } else {
                schools = getCoreSearching().getSchools(getEntityManager(), region);
            }

            /* do the report */
                        
            int requiredHours = 0;
            int availableHours = 0;
            int totalMissingRegularEmployees = 0;
            for(Map<String, Object> reportItem : tempReportData) {
                /* fetch the actual effective SpecializationGroups */
                List<SpecializationGroup> effectiveGroup = (List<SpecializationGroup>)reportItem.get("groups");
                
                /* for the report schools fetch the required hours */
                String queryRequiredHours = "SELECT t.school.id, SUM(t.hours) FROM TeachingRequirement t " +
                "WHERE t.schoolYear=:schoolYear " +
                "AND t.school IN (:reportSchools) " +
                "AND t.specialization IN (:reportSpecializationGroups) " +
                "GROUP BY (t.school.id)";
                
                Collection<Object[]> declaredRequiredHours  = (Collection<Object[]>)getEntityManager().createQuery(queryRequiredHours).setParameter("schoolYear", activeSchoolYear).setParameter("reportSpecializationGroups", effectiveGroup).setParameter("reportSchools", schools).getResultList();
                Map<String, Long> declaredRequiredHoursMap = new HashMap<String, Long>(declaredRequiredHours.size());
                for(Object[] oo : declaredRequiredHours) {
                    declaredRequiredHoursMap.put((String)oo[0], (Long)oo[1]);
                }
                
                /* fetch the cdrs for for the report schools and the desired specialization groups */
                String query2 = "SELECT t.unit.id, SUM(t.hours), MAX(t.unit.title) FROM TeachingHourCDR t " +
                "WHERE t.schoolYear=:schoolYear " +
                "AND t.unit IN (:reportSchools) " +
                "AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:reportSpecializationGroups) AND g.schoolYear=:schoolYear AND t.specialization MEMBER OF g.specializations) " +
                "GROUP BY (t.unit.id)";
                
               
                
                Collection<Object[]> o2  = (Collection<Object[]>)getEntityManager().createQuery(query2).setParameter("schoolYear", activeSchoolYear).setParameter("reportSpecializationGroups", effectiveGroup).setParameter("reportSchools", schools).getResultList();
                List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
                for(Object[] oo : o2) {
                    Map<String, Object> d = new HashMap<String, Object>();
                    Long unitAvailableHours = (Long)oo[1];
                    Long unitRequiredHours = declaredRequiredHoursMap.containsKey(oo[0]) ?  declaredRequiredHoursMap.get(oo[0]) : new Long(0);
                    Long unitMissingHours = new Long(unitRequiredHours.longValue()-unitAvailableHours.longValue());
                    Long unitMissingRegularEmployees = new Long(0);
                    if(unitMissingHours > HOURS_FOR_REGULAR_POSITION) {
                        unitMissingRegularEmployees = new Long(unitMissingHours.longValue() / HOURS_FOR_REGULAR_POSITION); 
                    } 
                    
                    d.put("unitAvailableHours", unitAvailableHours);
                    d.put("school", getEntityManager().find(Unit.class, oo[0]));
                    /* at this point we should find the required hours for the given school */
                    
                    data.add(d);
                    availableHours+=unitAvailableHours.longValue();
                    requiredHours+=unitRequiredHours.longValue();
                    totalMissingRegularEmployees+=unitMissingRegularEmployees.longValue();
                    d.put("unitRequiredHours", unitRequiredHours);
                    d.put("unitMissingHours", unitMissingHours);
                    d.put("unitMissingHoursNeg", unitMissingHours*(-1));
                    d.put("unitMissingRegularEmployees", unitMissingRegularEmployees);
                }
                reportItem.put("data", data);
                reportItem.put("totalRequiredHours", new Long(requiredHours));
                reportItem.put("totalAvailableHours", new Long(availableHours));
                reportItem.put("totalMissingHours", new Long(requiredHours-availableHours));
                reportItem.put("totalMissingHoursNeg", new Long((-1)* (requiredHours-availableHours)));
                reportItem.put("totalMissingRegularEmployees", new Long(totalMissingRegularEmployees));
            }
           
            setTeachingVoidReport(tempReportData);
           
            finished = System.currentTimeMillis();
            info("report has been generated in #0 [ms]", (finished - started));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new RuntimeException(ex);
        }

    }

   

    /**
     * @return the region
     */
    public Character getRegion() {
        return region;
    }

    
    
    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @return the specializationGroup
     */
    public SpecializationGroup getSpecializationGroup() {
        return specializationGroup;
    }

    /**
     * @return the specializationGroups
     */
    public List<SpecializationGroup> getSpecializationGroups() {
        return specializationGroups;
    }

    /**
     * @return the specializationGroupSearchType
     */
    public SpecializationGroupSearchType getSpecializationGroupSearchType() {
        return specializationGroupSearchType;
    }

    

    /**
     * @param region the region to set
     */
    public void setRegion(Character region) {
        this.region = region;
    }

    

   

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * @param specializationGroup the specializationGroup to set
     */
    public void setSpecializationGroup(SpecializationGroup specializationGroup) {
        this.specializationGroup = specializationGroup;
    }

    /**
     * @param specializationGroups the specializationGroups to set
     */
    public void setSpecializationGroups(List<SpecializationGroup> specializationGroups) {
        this.specializationGroups = specializationGroups;
    }

    /**
     * @param specializationGroupSearchType the specializationGroupSearchType to set
     */
    public void setSpecializationGroupSearchType(SpecializationGroupSearchType specializationGroupSearchType) {
        this.specializationGroupSearchType = specializationGroupSearchType;
    }

    /**
     * @return the regularEmploymentsOnly
     */
    public Boolean getRegularEmploymentsOnly() {
        return regularEmploymentsOnly;
    }

    /**
     * @param regularEmploymentsOnly the regularEmploymentsOnly to set
     */
    public void setRegularEmploymentsOnly(Boolean regularEmploymentsOnly) {
        this.regularEmploymentsOnly = regularEmploymentsOnly;
    }

    


    /**
     * @return the teachingVoidReport
     */
    public List<Map<String, Object>> getTeachingVoidReport() {
        return teachingVoidReport;
    }


    /**
     * @param teachingVoidReport the teachingVoidReport to set
     */
    public void setTeachingVoidReport(List<Map<String, Object>> teachingVoidReport) {
        this.teachingVoidReport = teachingVoidReport;
    }

    /**
     * @return the availableSpecializationGroupsForVoidAnalysisReport
     */
    public List<SpecializationGroup> getAvailableSpecializationGroupsForVoidAnalysisReport() {
        return availableSpecializationGroupsForVoidAnalysisReport;
    }

    /**
     * @param availableSpecializationGroupsForVoidAnalysisReport the availableSpecializationGroupsForVoidAnalysisReport to set
     */
    public void setAvailableSpecializationGroupsForVoidAnalysisReport(
            List<SpecializationGroup> availableSpecializationGroupsForVoidAnalysisReport) {
        this.availableSpecializationGroupsForVoidAnalysisReport = availableSpecializationGroupsForVoidAnalysisReport;
    }

}
