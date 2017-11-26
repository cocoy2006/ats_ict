package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CtDispatcher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ICT_DISPATCHER", catalog = "MOLAB")
public class ICtDispatcher implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer customerId;
	private Integer applicationId;
	private Integer holdTime;
	private Long oprTime;
	private Integer state;

	// Constructors

	/** default constructor */
	public ICtDispatcher() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "APPLICATION_ID", nullable = false)
	public Integer getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	@Column(name = "HOLD_TIME")
	public Integer getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Integer holdTime) {
		this.holdTime = holdTime;
	}

	@Column(name = "OPR_TIME")
	public Long getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(Long oprTime) {
		this.oprTime = oprTime;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}