package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CtRunner entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ICT_RUNNER", catalog = "MOLAB")
public class ICtRunner implements java.io.Serializable {

	// Fields

	private int id;
	private int dispatcherId;
	private int deviceId;
	private float averageCpu;
	private long averageMemory;
	private long installTime;
	private String installResult;
	private long loadTime;
	private String loadResult;
	private long uninstallTime;
	private Blob logcat;
	private int state;

	// Constructors

	/** default constructor */
	public ICtRunner() {
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

	@Column(name = "DISPATCHER_ID", nullable = false)
	public Integer getDispatcherId() {
		return this.dispatcherId;
	}

	public void setDispatcherId(Integer dispatcherId) {
		this.dispatcherId = dispatcherId;
	}

	@Column(name = "DEVICE_ID", nullable = false)
	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "AVERAGE_CPU", precision = 12, scale = 0)
	public Float getAverageCpu() {
		return this.averageCpu;
	}

	public void setAverageCpu(Float averageCpu) {
		this.averageCpu = averageCpu;
	}

	@Column(name = "AVERAGE_MEMORY")
	public Long getAverageMemory() {
		return this.averageMemory;
	}

	public void setAverageMemory(Long averageMemory) {
		this.averageMemory = averageMemory;
	}

	@Column(name = "INSTALL_TIME")
	public Long getInstallTime() {
		return this.installTime;
	}

	public void setInstallTime(Long installTime) {
		this.installTime = installTime;
	}

	@Column(name = "INSTALL_RESULT")
	public String getInstallResult() {
		return this.installResult;
	}

	public void setInstallResult(String installResult) {
		this.installResult = installResult;
	}

	@Column(name = "LOAD_TIME")
	public Long getLoadTime() {
		return this.loadTime;
	}

	public void setLoadTime(Long loadTime) {
		this.loadTime = loadTime;
	}

	@Column(name = "LOAD_RESULT")
	public String getLoadResult() {
		return loadResult;
	}

	public void setLoadResult(String loadResult) {
		this.loadResult = loadResult;
	}

	@Column(name = "UNINSTALL_TIME")
	public Long getUninstallTime() {
		return this.uninstallTime;
	}

	public void setUninstallTime(Long uninstallTime) {
		this.uninstallTime = uninstallTime;
	}

	@Column(name = "LOGCAT")
	public Blob getLogcat() {
		return this.logcat;
	}

	public void setLogcat(Blob logcat) {
		this.logcat = logcat;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}