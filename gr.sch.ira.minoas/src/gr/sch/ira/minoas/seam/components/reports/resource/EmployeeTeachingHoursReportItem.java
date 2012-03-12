package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeTeachingHoursReportItem extends EmployeeReportItem {

    public final class TeachinngHourCDRGroup {
        
        private List<TeachingHourCDR> cdrs = new ArrayList<TeachingHourCDR>();
        
        private Unit unit;
        
        private Integer sum;
        
        private TeachingHourCDRType groupMainCDRType;

        /**
         * 
         */
        public TeachinngHourCDRGroup(Unit unit) {
            super();
            this.unit = unit;
            this.sum = new Integer(0);
        }
        
        public void updateWithCDR(TeachingHourCDR cdr) {
            cdrs.add(cdr);
            sum += cdr.getHours();
            setGroupMainCDRType(cdr.getCdrType());
        }
        
        public List<TeachingHourCDR> getCDRs() {
            return new ArrayList<TeachingHourCDR>(cdrs);
        }
        
        public Unit getUnit() {
            return this.unit;
        }
        
        public Integer getSum() {
            return this.sum;
        }

        /**
         * @return the groupMainCDRType
         */
        public TeachingHourCDRType getGroupMainCDRType() {
            return groupMainCDRType;
        }

        /**
         * @param groupMainCDRType the groupMainCDRType to set
         */
        public void setGroupMainCDRType(TeachingHourCDRType groupMainCDRType) {
            this.groupMainCDRType = groupMainCDRType;
        }
    }
    
    private Map<Unit, TeachinngHourCDRGroup> cache = new LinkedHashMap<Unit, TeachinngHourCDRGroup>();
    
    private Integer totalSum;
    /**
     * 
     */
    public EmployeeTeachingHoursReportItem() {
        super();
    }

    /**
     * @param employee
     */
    public EmployeeTeachingHoursReportItem(Employee employee) {
        super(employee);
        totalSum = new Integer(0);
    }

    public void updateWith(TeachingHourCDR cdr) {
        TeachinngHourCDRGroup group = cache.get(cdr.getUnit());
        if(group==null) {
            group = new TeachinngHourCDRGroup(cdr.getUnit());
            cache.put(cdr.getUnit(), group);
        }
        group.updateWithCDR(cdr);
        totalSum = totalSum+cdr.getHours();
    }
    
    public List<Unit> getCDRUnitGroups() {
        return new ArrayList<Unit>(cache.keySet());
    }
    
    public TeachinngHourCDRGroup getCDRGroup(Unit unit) {
        return cache.get(unit);
    }

    /**
     * @return the totalSum
     */
    public Integer getTotalSum() {
        return totalSum;
    }

    /**
     * @param totalSum the totalSum to set
     */
    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }
}
