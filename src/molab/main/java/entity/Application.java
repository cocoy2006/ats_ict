package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Application entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APPLICATION", catalog = "MOLAB")
public class Application implements java.io.Serializable {

	// Fields

	private Integer id;
	private String md5;
	private String name;
	private String aliasname;
	private Long size;
	private String packagename;
	private String version;
	private String os;
	private String startactivity;
	private String label;

	// Constructors

	/** default constructor */
	public Application() {
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

	@Column(name = "MD5", nullable = false, length = 128)
	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "NAME", length = 500)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ALIASNAME", length = 500)
	public String getAliasname() {
		return this.aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}

	@Column(name = "SIZE")
	public Long getSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Column(name = "PACKAGENAME", length = 100)
	public String getPackagename() {
		return this.packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "OS", length = 20)
	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "STARTACTIVITY", length = 200)
	public String getStartactivity() {
		return this.startactivity;
	}

	public void setStartactivity(String startactivity) {
		this.startactivity = startactivity;
	}

	@Column(name = "LABEL", length = 255)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}