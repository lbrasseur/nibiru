package ar.com.oxen.nibiru.dynamicbundle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BundleReference {
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique=true)
	private String symbolicName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}
}
