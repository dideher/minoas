/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "MINOAS_EMPLOYEE_HPAID")
@DiscriminatorValue("EMPLOYEE_HPAID")
@PrimaryKeyJoinColumn(name = "EMPLOYEE_ID")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HourlyPaidEmployee extends Employee {

	private static final long serialVersionUID = 1L;

}
