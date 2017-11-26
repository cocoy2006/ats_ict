package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ICtScreenshot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ICT_SCREENSHOT", catalog = "MOLAB")
public class ICtScreenshot implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer runnerId;
	private String image;
	private String note;
	private Long oprTime;
	private int state;

	// Constructors

	/** default constructor */
	public ICtScreenshot() {
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

	@Column(name = "RUNNER_ID", nullable = false)
	public Integer getRunnerId() {
		return this.runnerId;
	}

	public void setRunnerId(Integer runnerId) {
		this.runnerId = runnerId;
	}

	@Column(name = "IMAGE", nullable = false)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "OPR_TIME")
	public Long getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(Long oprTime) {
		this.oprTime = oprTime;
	}
	
	@Column(name = "STATE")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}