package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.seam.components.home.SchoolHome;
import gr.sch.ira.minoas.seam.components.reports.resource.DisposalReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmploymentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmployments;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.ServiceAllocationItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:fsla@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolReport")
@Scope(ScopeType.CONVERSATION)
public class SchoolReport extends BaseReport {

    public final class EmployeeSchoolCDRReportItem {
        
        private boolean containsLeaveCDR = false;
        
        private EmployeeReportItem employee;

        private Collection<TeachingHourCRDReportItem> employeeCDRS = new ArrayList<SchoolReport.TeachingHourCRDReportItem>();

        private Integer totalHours = 0;
        
        public boolean hasCDRsOtherThanEmployment() {
            if(employeeCDRS.size()==1 && ((TeachingHourCRDReportItem)employeeCDRS.toArray()[0]).getType().equals("EMPLOYMENT")) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * @param employee
         */
        public EmployeeSchoolCDRReportItem(EmployeeReportItem employee) {
            super();
            this.employee = employee;
            this.totalHours = 0;
        }

        public TeachingHourCDR addCDR(TeachingHourCDR employeeCDR) {
            getEmployeeCDRS().add(new TeachingHourCRDReportItem(employeeCDR));
            totalHours += employeeCDR.getHours();
            if(!containsLeaveCDR) {
                /* check if the new employee CDR to be added is a leave CDR */
                containsLeaveCDR = (employeeCDR.getCdrType() == TeachingHourCDRType.LEAVE);
            }
            return employeeCDR;
        }

        /**
         * @return the employee
         */
        public EmployeeReportItem getEmployee() {
            return employee;
        }

        /**
         * @return the employeeCRS
         */
        public Collection<TeachingHourCRDReportItem> getEmployeeCDRS() {
            return employeeCDRS;
        }

        /**
         * Return the total hours of teaching. Returns 0 if the employee
         * is associated with leave.
         * @return the totalHours
         */
        public Integer getTotalHours() {
            return containsLeaveCDR ? 0 : totalHours;
        }

        /**
         * @param employee the employee to set
         */
        public void setEmployee(EmployeeReportItem employee) {
            this.employee = employee;
        }

        /**
         * @param employeeCRS the employeeCRS to set
         */
        public void setEmployeeCDRS(Collection<TeachingHourCRDReportItem> employeeCRS) {
            this.employeeCDRS = employeeCRS;
        }

        /**
         * @param totalHours the totalHours to set
         */
        public void setTotalHours(Integer totalHours) {
            this.totalHours = totalHours;
        }
    }

    public final class GroupedEmployeesCDRItem {

        private HashMap<Integer, EmployeeSchoolCDRReportItem> groupEmployeesCache = new LinkedHashMap<Integer, SchoolReport.EmployeeSchoolCDRReportItem>();

        private Integer groupRequiredTeachingHours = 0;
        
        private Integer groupMandatoryTeachingHours = 0;

        private String groupTitle;

        /**
         * @param groupTitle
         * @param groupRequiredTeachingHours
         */
        public GroupedEmployeesCDRItem(String groupTitle, Integer groupRequiredTeachingHours) {
            super();
            this.groupTitle = groupTitle;
            this.groupRequiredTeachingHours = groupRequiredTeachingHours;
        }

        public void addEmployeeCDRs(EmployeeSchoolCDRReportItem item) {
            this.groupEmployeesCache.put(item.getEmployee().getEmployeeID(), item);
            groupMandatoryTeachingHours += item.getEmployee().getEmployeeMandatoryHours();
        }

        public EmployeeSchoolCDRReportItem getEmployeeCDRReportItem(Integer employeeID) {
            return groupEmployeesCache.get(employeeID);
        }

       public Integer getGroupAvailableTeachingHours() {
            int returnValue = 0;
            for(EmployeeSchoolCDRReportItem item : groupEmployeesCache.values()) {
                returnValue += item.getTotalHours();
            }
            return Integer.valueOf(returnValue);
        }
       
       

        /**
         * @return the groupEmployees
         */
        public Collection<EmployeeSchoolCDRReportItem> getGroupEmployees() {
            List<SchoolReport.EmployeeSchoolCDRReportItem> returnValue = new ArrayList<SchoolReport.EmployeeSchoolCDRReportItem>(groupEmployeesCache.values());
            Collections.sort(returnValue, new Comparator<SchoolReport.EmployeeSchoolCDRReportItem>() {
                public int compare(EmployeeSchoolCDRReportItem o1, EmployeeSchoolCDRReportItem o2) {
                    return o1.getEmployee().getEmployeeLastName().compareTo(o2.getEmployee().getEmployeeLastName());
                }
            });
            return returnValue;
         }

        /**
         * @return the groupRequiredTeachingHours
         */
        public Integer getGroupRequiredTeachingHours() {
            return groupRequiredTeachingHours;
        }
        
        public Integer getMissingHours() {
            return getGroupAvailableTeachingHours()-groupRequiredTeachingHours;
        }
        
        public boolean hasMissingHours() {
            return getMissingHours() > 0;
        }

        public boolean isEmpty() {
            return  (getGroupRequiredTeachingHours().intValue() + getMissingHours().intValue()  == 0);
        }
        /**
         * @return the groupTitle
         */
        public String getGroupTitle() {
            return groupTitle;
        }

       /**
         * @param groupRequiredTeachingHours the groupRequiredTeachingHours to set
         */
        private void setGroupRequiredTeachingHours(Integer groupRequiredTeachingHours) {
            this.groupRequiredTeachingHours = groupRequiredTeachingHours;
        }

        /**
         * @param groupTitle the groupTitle to set
         */
        private void setGroupTitle(String groupTitle) {
            this.groupTitle = groupTitle;
        }

        /**
         * @return the groupMandatoryTeachingHours
         */
        public Integer getGroupMandatoryTeachingHours() {
            return groupMandatoryTeachingHours;
        }

        
    }

    public final class TeachingHourCRDReportItem {

        private String comment;

        private Integer hours;

        private String type;

        public TeachingHourCRDReportItem(TeachingHourCDR cdr) {
            super();
            setHours(cdr.getHours());
            setComment(cdr.getComment());
            setType(cdr.getCdrType().toString());
        }

        /**
         * @return the comment
         */
        public String getComment() {
            return comment;
        }

        /**
         * @return the hours
         */
        public Integer getHours() {
            return hours;
        }

        /**
         * @param comment the comment to set
         */
        public void setComment(String comment) {
            this.comment = comment;
        }

        /**
         * @param hours the hours to set
         */
        public void setHours(Integer hours) {
            this.hours = hours;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private DateFormat dateFormat;

    @DataModel(value = "incomingDisposals")
    private Collection<DisposalReportItem> incomingDisposals;

    @DataModel(value = "incomingSecondments")
    private Collection<SecondmentItem> incomingSecondments;

    @DataModel(value = "incomingServiceAllocations")
    private Collection<ServiceAllocationItem> incomingServiceAllocations;

    @DataModel(value = "schoolOutgoingDisposals")
    private Collection<DisposalReportItem> outcomingDisposals;

    @DataModel(value = "schoolOutgoingSecondments")
    private Collection<SecondmentItem> outcomingSecondments;

    @DataModel(value = "schoolOutgoingServiceAllocations")
    private Collection<ServiceAllocationItem> outcomingServiceAllocations;

    @DataModel(value = "schoolGroupedEmployeeCDRs")
    private Collection<GroupedEmployeesCDRItem> schoolGroupedEmployeeCDRs;

    @DataModel(value = "schoolChiefs")
    private Collection<ServiceAllocationItem> schoolChiefs;

    @DataModel(value = "schoolDeputyEmployees")
    private Collection<EmployeeReportItem> schoolDeputyEmployees;

    @DataModel(value = "schoolDeputyEmployments")
    private Collection<EmploymentReportItem> schoolDeputyEmployments;

    @DataModel(value = "schoolEmployments")
    private SchoolUniversalEmployments schoolEmployments;

    @In
    private SchoolHome schoolHome;

    @DataModel(value = "schoolHourlyBasedEmployments")
    private Collection<EmploymentReportItem> schoolHourlyBasedEmployments;

    @DataModel(value = "schoolLeaves")
    private Collection<LeaveReportItem> schoolLeaves;

    @DataModel(value = "schoolRegularBasedEmployments")
    private Collection<EmploymentReportItem> schoolRegularBasedEmployments;

    @DataModel(value = "schoolRegularEmployments")
    private SchoolUniversalEmployments schoolRegularEmployments;

    @DataModel(value = "schoolRegularsEmployees")
    private Collection<EmployeeReportItem> schoolRegularsEmployees;

    /**
     * 
     */
    public SchoolReport() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    private String constructComment(Disposal disposal) {
        StringBuffer sb = new StringBuffer();
        sb.append("Διάθεση απο τις ");
        sb.append(this.dateFormat.format(disposal.getEstablished()));
        sb.append(" εως και ");
        sb.append(this.dateFormat.format(disposal.getDueTo()));
        sb.append(" στο/σε ");
        sb.append(disposal.getDisposalUnit().getTitle());
        sb.append(" για ");
        sb.append(disposal.getHours());
        sb.append(" ωρες και ");
        sb.append(disposal.getDays());
        sb.append(" ημέρες. ");
        return sb.toString();

    }

    private String constructComment(Leave leave) {
        StringBuffer sb = new StringBuffer();
        sb.append("Άδεια τύπου ");
        sb.append(getLocalizedMessage(leave.getLeaveType().getKey()));
        sb.append(" απο τις ");
        sb.append(this.dateFormat.format(leave.getEstablished()));
        sb.append(" εως και ");
        sb.append(this.dateFormat.format(leave.getDueTo()));
        sb.append(". ");
        return sb.toString();
    }

    private String constructComment(Secondment secondment) {
        StringBuffer sb = new StringBuffer();
        sb.append("Αποσπάση απο τις ");
        sb.append(this.dateFormat.format(secondment.getEstablished()));
        sb.append(" στο/σε ");
        sb.append(secondment.getTargetUnit().getTitle());
        sb.append(". ");
        return sb.toString();
    }

    private String constructComment(ServiceAllocation serviceAllocation) {
        StringBuffer sb = new StringBuffer();
        sb.append("Θητεία τύπου ");
        sb.append(getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
        sb.append(" απο τις ");
        sb.append(this.dateFormat.format(serviceAllocation.getEstablished()));
        sb.append(" εως και ");
        sb.append(this.dateFormat.format(serviceAllocation.getDueTo()));
        sb.append(" στο/σε ");
        sb.append(serviceAllocation.getServiceUnit().getTitle());
        sb.append(". ");
        return sb.toString();
    }

    public SchoolUniversalEmploymentItem constructDeputyEmploymentInfoItem(Employment employment) {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(employment);

        /* check if the employment is associated with a disposal */
        Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
                employment.getEmployee(), today);
        if (disposals != null && disposals.size() > 0) {
            StringBuffer sb = new StringBuffer();
            int total_hours = 0;
            for (Disposal d : disposals) {
                sb.append(constructComment(d));
                total_hours += d.getHours();
            }
            item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours() - total_hours);
            item.addEmploymentComment(sb.toString());
        }

        /* check if the employment is associated with a service allocation */
        ServiceAllocation serviceAllocation = getCoreSearching().getEmployeeActiveServiceAllocation(getEntityManager(),
                employment.getEmployee(), today);
        if (serviceAllocation != null) {
            /* the employment is overriden by a service allocation */
            item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnRegularPosition());
            item.addEmploymentComment(constructComment(serviceAllocation));
        }

        /* check if the employment is associated with a leave */
        Leave leave = getCoreSearching().getEmployeeActiveLeave(getEntityManager(), employment.getEmployee(), today);
        if (leave != null) {
            /* the employment is overriden by a service allocation */
            item.setEmployeeFinalWorkingHours(0);
            item.addEmploymentComment(constructComment(leave));
        }

        return item;
    }
   

    public SchoolUniversalEmploymentItem constructRegularEmploymentInfoItem(Employment regularEmployment) {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        School referenceSchool = schoolHome.getInstance();
        SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(regularEmployment);

        /* check if the employment is associated with an secondment */
        Secondment s = getCoreSearching().getEmployeeActiveSecondment(getEntityManager(),
                regularEmployment.getEmployee(), today);
        if (s != null) {
            /* the employment is overriden by a secondment */
            item.setEmployeeFinalWorkingHours(0);
            item.addEmploymentComment(constructComment(s));
        }

        /* check if the employment is associated with a disposal */
        Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
                regularEmployment.getEmployee(), today);
        if (disposals != null && disposals.size() > 0) {
            int total_hours = 0;
            for (Disposal d : disposals) {
                item.addEmploymentComment(constructComment(d));
                /* substract hours if the displosal is outgoing from this very school unit */
                if ((d.getAffectedSecondment() != null && d.getAffectedSecondment().getTargetUnit().getId()
                        .equals(referenceSchool.getId())) ||
                        (d.getAffectedEmployment() != null && d.getAffectedEmployment().getSchool().getId()
                                .equals(referenceSchool.getId())))
                    total_hours += d.getHours();
            }
            item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours() - total_hours);
        }

        /* check if the employment is associated with a service allocation */
        ServiceAllocation serviceAllocation = getCoreSearching().getEmployeeActiveServiceAllocation(getEntityManager(),
                regularEmployment.getEmployee(), today);
        if (serviceAllocation != null) {
            /* the employment is overriden by a service allocation */
            if (serviceAllocation.getServiceUnit().getId().equals(referenceSchool.getId())) {
                /* since the reference school is currently the service allocation target unit 
                 * use the appropriate working hours element.
                 */
                item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnServicingPosition());
            } else {
                item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnRegularPosition());
            }
            item.addEmploymentComment(constructComment(serviceAllocation));
        }

        /* check if the employment is associated with a leave */
        Leave leave = getCoreSearching().getEmployeeActiveLeave(getEntityManager(), regularEmployment.getEmployee(),
                today);
        if (leave != null) {
            /* the employment is overriden by a service allocation */
            item.setEmployeeFinalWorkingHours(0);
            item.addEmploymentComment(constructComment(leave));
        }

        return item;

    }

    public void fetchSchoolChiefs() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        schoolChiefs = convertServiceAllocationCollection(getCoreSearching().getSchoolIncomingServiceAllocationsOfType(
                getEntityManager(), schoolHome.getInstance(), today,
                Arrays.asList(ServiceAllocationType.SCHOOL_HEADMASTER, ServiceAllocationType.SCHOOL_SUBHEADMASTER)));
    }

    public void generateEmploymentsCDRReport() {
        long started = System.currentTimeMillis(), finished;
        info("generating employments cdr report ");
       

        SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
        // fetch all cdrs that are associated with our unit
        Collection<TeachingHourCDR> schoolsCDRS = getCoreSearching().getSchoolTeachingHoursCDRs(getEntityManager(),
                activeSchoolYear, schoolHome.getInstance());
        // fetch school teaching req
        Collection<TeachingRequirement> teachingReqs = getCoreSearching().getSchoolTeachingRequirement(
                getEntityManager(), schoolHome.getInstance(), activeSchoolYear);
        Map<SpecializationGroup, Integer> teachingReqsCache = new HashMap<SpecializationGroup, Integer>();
        for(TeachingRequirement r : teachingReqs) {
            teachingReqsCache.put(r.getSpecialization(), r.getHours());
        }
        // fetch all specialization groups
        Collection<SpecializationGroup> specializationGruops = getCoreSearching().getSpecializationGroups(
                activeSchoolYear, getEntityManager());
        Map<Specialization, SpecializationGroup> specializationGroupCache = new HashMap<Specialization, SpecializationGroup>();
        for (SpecializationGroup group : specializationGruops) {
            for (Specialization sp : group.getSpecializations()) {
                specializationGroupCache.put(sp, group);
            }
        }

        Map<String, GroupedEmployeesCDRItem> groupCache = new LinkedHashMap<String, SchoolReport.GroupedEmployeesCDRItem>();
        
        /* gh-33 */
        
        /* populate the teaching requirement group cache using the required hours for this concrete school unit.
         * By doing so, even if there is no employee with a matching specialization the group will appear (empty)
         * in the school report.
         */
        for(TeachingRequirement teachingReq : teachingReqs) {
            String groupTitle = String.format("%s", teachingReq.getSpecialization().getTitle());
            GroupedEmployeesCDRItem g = new GroupedEmployeesCDRItem(groupTitle, teachingReq.getHours());
            groupCache.put(groupTitle, g);
        }
        /* gh-33 */

       
        for (TeachingHourCDR cdr : schoolsCDRS) {
            Employee employee = cdr.getEmployee();
            SpecializationGroup sgroup = specializationGroupCache.get(employee.getLastSpecialization());
            /* if the employee's specialization does not map to a concrete specialization group, then
             * just use the specialization title as group title.
             */
            String groupTitle = (sgroup != null) ? sgroup.getTitle() : employee.getLastSpecialization().getTitle();
            GroupedEmployeesCDRItem group = groupCache.get(groupTitle);
            if (group == null) {
                /* no such group so far */
                Integer reqHours = 0;
                if(sgroup!=null) {
                    reqHours = teachingReqsCache.get(sgroup);
                    
                }
                group = new GroupedEmployeesCDRItem(groupTitle, reqHours !=null ? reqHours : 0); /* we need to add the required hours here */
                groupCache.put(groupTitle, group);
            }
            EmployeeSchoolCDRReportItem item = group.getEmployeeCDRReportItem(employee.getId());
            if (item == null) {
                item = new EmployeeSchoolCDRReportItem(new EmployeeReportItem(cdr.getEmployee()));
                group.addEmployeeCDRs(item);
            }
            item.addCDR(cdr);
        }

        List<SchoolReport.GroupedEmployeesCDRItem> result = new ArrayList<SchoolReport.GroupedEmployeesCDRItem>(
                groupCache.values());
        Collections.sort(result, new Comparator<SchoolReport.GroupedEmployeesCDRItem>() {

            public int compare(GroupedEmployeesCDRItem o1, GroupedEmployeesCDRItem o2) {
                return o1.getGroupTitle().compareTo(o2.getGroupTitle());
            }

        });
        setSchoolGroupedEmployeeCDRs(result);
        finished = System.currentTimeMillis();
        info("report has been generated in #0 [ms]", (finished - started));
        

    }

    

    public void generateReport() {

        long started = System.currentTimeMillis(), finished;
        info("generating report ");
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
        schoolChiefs = convertServiceAllocationCollection(getCoreSearching().getSchoolIncomingServiceAllocationsOfType(
                getEntityManager(), schoolHome.getInstance(), today,
                Arrays.asList(ServiceAllocationType.SCHOOL_HEADMASTER, ServiceAllocationType.SCHOOL_SUBHEADMASTER)));

        schoolRegularsEmployees = convertEmployeeCollection(getCoreSearching()
                .getSchoolActiveEmployeesOfEmploymentType(getEntityManager(), schoolHome.getInstance(),
                        getCoreSearching().getActiveSchoolYear(getEntityManager()), today, EmploymentType.REGULAR));

        schoolDeputyEmployees = convertEmployeeCollection(getCoreSearching().getSchoolActiveEmployeesOfEmploymentType(
                getEntityManager(), schoolHome.getInstance(),
                getCoreSearching().getActiveSchoolYear(getEntityManager()), today, EmploymentType.DEPUTY));

        incomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolSecondments(getEntityManager(),
                schoolHome.getInstance(), activeSchoolYear, today));

        incomingServiceAllocations = convertServiceAllocationCollection(getCoreSearching()
                .getSchoolIncomingServiceAllocations(getEntityManager(), schoolHome.getInstance(), today));

        incomingDisposals = convertDisposalCollection(getCoreSearching().getSchoolDisposals(getEntityManager(),
                schoolHome.getInstance(), activeSchoolYear, today, null));

        outcomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolOutgoingSecondments(
                getEntityManager(), schoolHome.getInstance(), activeSchoolYear, today));

        schoolLeaves = convertLeaveCollection(getCoreSearching().getSchoolLeaves(getEntityManager(),
                schoolHome.getInstance(), activeSchoolYear, today, null));

        schoolRegularBasedEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
                getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR));

        schoolDeputyEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
                getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.DEPUTY));

        schoolHourlyBasedEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
                getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.HOURLYBASED));

        outcomingServiceAllocations = convertServiceAllocationCollection(getCoreSearching()
                .getSchoolReallyOutgoingServiceAllocations(getEntityManager(), schoolHome.getInstance(), today, null));

        finished = System.currentTimeMillis();
        info("report has been generated in #0 [ms]", (finished - started));

    }

    /**
     * @return the incomingDisposals
     */
    public Collection<DisposalReportItem> getIncomingDisposals() {
        return incomingDisposals;
    }

    /**
     * @return the incomingSecondments
     */
    public Collection<SecondmentItem> getIncomingSecondments() {
        return incomingSecondments;
    }

    /**
     * @return the incomingServiceAllocations
     */
    public Collection<ServiceAllocationItem> getIncomingServiceAllocations() {
        return incomingServiceAllocations;
    }

    /**
     * @return the outcomingDisposals
     */
    public Collection<DisposalReportItem> getOutcomingDisposals() {
        return outcomingDisposals;
    }

    /**
     * @return the outcomingSecondments
     */
    public Collection<SecondmentItem> getOutcomingSecondments() {
        return outcomingSecondments;
    }

    /**
     * @return the outcomingServiceAllocations
     */
    public Collection<ServiceAllocationItem> getOutcomingServiceAllocations() {
        return outcomingServiceAllocations;
    }

    /**
     * @return the schoolChiefs
     */
    public Collection<ServiceAllocationItem> getSchoolChiefs() {
        return schoolChiefs;
    }

    /**
     * @return the schoolDeputysEmployees
     */
    public Collection<EmployeeReportItem> getSchoolDeputyEmployees() {
        return schoolDeputyEmployees;
    }

    /**
     * @return the schoolDeputyEmployments
     */
    public Collection<EmploymentReportItem> getSchoolDeputyEmployments() {
        return schoolDeputyEmployments;
    }

    /**
     * @return the schoolEmployments
     */
    public SchoolUniversalEmployments getSchoolEmployments() {
        return schoolEmployments;
    }

    /**
     * @return the schoolHourlyBasedEmployments
     */
    public Collection<EmploymentReportItem> getSchoolHourlyBasedEmployments() {
        return schoolHourlyBasedEmployments;
    }

    /**
     * @return the schoolLeaves
     */
    public Collection<LeaveReportItem> getSchoolLeaves() {
        return schoolLeaves;
    }

    /**
     * @return the schoolRegularBasedEmployments
     */
    public Collection<EmploymentReportItem> getSchoolRegularBasedEmployments() {
        return schoolRegularBasedEmployments;
    }

    /**
     * @return the schoolRegularEmployments
     */
    public SchoolUniversalEmployments getSchoolRegularEmployments() {
        return schoolRegularEmployments;
    }

    /**
     * @return the schoolRegularsEmployees
     */
    public Collection<EmployeeReportItem> getSchoolRegularsEmployees() {
        return schoolRegularsEmployees;
    }

    public void lookupDeputyEmployments() {
        long started = System.currentTimeMillis(), finished;
        SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
        info("deputy employee lookup in school unit #0 during school year #1", schoolHome.getInstance(),
                activeSchoolYear);
        Collection<Employment> deputyEmployments = getCoreSearching().getSchoolEmploymentsOfType(getEntityManager(),
                activeSchoolYear, schoolHome.getInstance(), EmploymentType.DEPUTY);
        schoolDeputyEmployments = new ArrayList<EmploymentReportItem>(deputyEmployments.size());
        for (Employment employment : deputyEmployments) {
            schoolDeputyEmployments.add(constructRegularEmploymentInfoItem(employment));
        }
        finished = System.currentTimeMillis();
        info("lookup found #0 deputy employment(s) totally in #1 [ms]", schoolDeputyEmployments.size(),
                (finished - started));
    }

    public void lookupHourlyBasedEmployments() {
        long started = System.currentTimeMillis(), finished;
        SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
        info("hourly based employee lookup in school unit #0 during school year #1", schoolHome.getInstance(),
                activeSchoolYear);
        schoolHourlyBasedEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
                getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.HOURLYBASED));
        finished = System.currentTimeMillis();
        info("lookup found #0 hourly based employment(s) totally in #1 [ms]", schoolHourlyBasedEmployments.size(),
                (finished - started));
    }

    public void lookupRegularEmployments() {

        /*
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * CURRENT PROBLEM THERE ARE SECONDMENT WITHOUT EMPLOYMENT (FROM OTHER PYSDE) AND DISPOSALS (NO AFFECTED EMPLOYMENT CAUSE THE 
         * DISPOSAL OVERRIDES A SECONDMENT).
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         */

        long started = System.currentTimeMillis(), finished;
        info("generating report school employment analysis");
        

        SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

        SchoolUniversalEmployments reportData = new SchoolUniversalEmployments(getCoreSearching()
                .getSchoolTeachingRequirement(getEntityManager(), schoolHome.getInstance(), activeSchoolYear),
                getCoreSearching().getSpecializationGroups(activeSchoolYear, getEntityManager()));

        /* ************************************************************************************ */
        /* REGULAR EMPLOYMENTS 																	*/
        /* ************************************************************************************ */

        Collection<Employment> schoolRegularEmployments = getCoreSearching().getSchoolEmploymentsOfType(
                getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR);

        for (Employment employment : schoolRegularEmployments) {
            reportData.add(constructRegularEmploymentInfoItem(employment));
        }

        setSchoolRegularEmployments(reportData);
        finished = System.currentTimeMillis();
        info("report has been generated in #0 [ms]", (finished - started));

    }

    /**
     * @param incomingDisposals the incomingDisposals to set
     */
    public void setIncomingDisposals(Collection<DisposalReportItem> incomingDisposals) {
        this.incomingDisposals = incomingDisposals;
    }

    /**
     * @param incomingSecondments the incomingSecondments to set
     */
    public void setIncomingSecondments(Collection<SecondmentItem> incomingSecondments) {
        this.incomingSecondments = incomingSecondments;
    }

    /**
     * @param incomingServiceAllocations the incomingServiceAllocations to set
     */
    public void setIncomingServiceAllocations(Collection<ServiceAllocationItem> incomingServiceAllocations) {
        this.incomingServiceAllocations = incomingServiceAllocations;
    }

    /**
     * @param outcomingDisposals the outcomingDisposals to set
     */
    public void setOutcomingDisposals(Collection<DisposalReportItem> outcomingDisposals) {
        this.outcomingDisposals = outcomingDisposals;
    }

    /**
     * @param outcomingSecondments the outcomingSecondments to set
     */
    public void setOutcomingSecondments(Collection<SecondmentItem> outcomingSecondments) {
        this.outcomingSecondments = outcomingSecondments;
    }

    /**
     * @param outcomingServiceAllocations the outcomingServiceAllocations to set
     */
    public void setOutcomingServiceAllocations(Collection<ServiceAllocationItem> outcomingServiceAllocations) {
        this.outcomingServiceAllocations = outcomingServiceAllocations;
    }

    /**
     * @param schoolChiefs the schoolChiefs to set
     */
    public void setSchoolChiefs(Collection<ServiceAllocationItem> schoolChiefs) {
        this.schoolChiefs = schoolChiefs;
    }

    /**
     * @param schoolDeputysEmployees the schoolDeputysEmployees to set
     */
    public void setSchoolDeputyEmployees(Collection<EmployeeReportItem> schoolDeputysEmployees) {
        this.schoolDeputyEmployees = schoolDeputysEmployees;
    }

    /**
     * @param schoolDeputyEmployments the schoolDeputyEmployments to set
     */
    public void setSchoolDeputyEmployments(Collection<EmploymentReportItem> schoolDeputyEmployments) {
        this.schoolDeputyEmployments = schoolDeputyEmployments;
    }

    /**
     * @param schoolEmployments the schoolEmployments to set
     */
    public void setSchoolEmployments(SchoolUniversalEmployments schoolEmployments) {
        this.schoolEmployments = schoolEmployments;
    }

    /**
     * @param schoolHourlyBasedEmployments the schoolHourlyBasedEmployments to set
     */
    public void setSchoolHourlyBasedEmployments(Collection<EmploymentReportItem> schoolHourlyBasedEmployments) {
        this.schoolHourlyBasedEmployments = schoolHourlyBasedEmployments;
    }

    /**
     * @param schoolLeaves the schoolLeaves to set
     */
    public void setSchoolLeaves(Collection<LeaveReportItem> schoolLeaves) {
        this.schoolLeaves = schoolLeaves;
    }

    /**
     * @param schoolRegularBasedEmployments the schoolRegularBasedEmployments to set
     */
    public void setSchoolRegularBasedEmployments(Collection<EmploymentReportItem> schoolRegularBasedEmployments) {
        this.schoolRegularBasedEmployments = schoolRegularBasedEmployments;
    }

    /**
     * @param schoolRegularEmployments the schoolRegularEmployments to set
     */
    public void setSchoolRegularEmployments(SchoolUniversalEmployments schoolRegularEmployments) {
        this.schoolRegularEmployments = schoolRegularEmployments;
    }

    /**
     * @param schoolRegularsEmployees the schoolRegularsEmployees to set
     */
    public void setSchoolRegularsEmployees(Collection<EmployeeReportItem> schoolRegularsEmployees) {
        this.schoolRegularsEmployees = schoolRegularsEmployees;
    }

    /**
     * @return the schoolGroupedEmployeeCDRs
     */
    public Collection<GroupedEmployeesCDRItem> getSchoolGroupedEmployeeCDRs() {
        return schoolGroupedEmployeeCDRs;
    }

    /**
     * @param schoolGroupedEmployeeCDRs the schoolGroupedEmployeeCDRs to set
     */
    public void setSchoolGroupedEmployeeCDRs(Collection<GroupedEmployeesCDRItem> schoolGroupedEmployeeCDRs) {
        this.schoolGroupedEmployeeCDRs = schoolGroupedEmployeeCDRs;
    }

}
