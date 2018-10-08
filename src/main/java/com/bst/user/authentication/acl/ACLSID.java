package com.bst.user.authentication.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "acl_sid", uniqueConstraints = { @UniqueConstraint(columnNames = { "sid", "principal" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ACLSID {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "id")
	private Long id;

	@Column(name = "sid")
	private String sid;

	@Column(name = "principal")
	private int principal;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final ACLSID other = (ACLSID) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.principal != other.principal) {
			return false;
		}
		if (this.sid == null) {
			if (other.sid != null) {
				return false;
			}
		} else if (!this.sid.equals(other.sid)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return this.id;
	}

	public int getPrincipal() {
		return this.principal;
	}

	public String getSid() {
		return this.sid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + this.principal;
		result = prime * result + ((this.sid == null) ? 0 : this.sid.hashCode());
		return result;
	}

	public void setPrincipal(final int principal) {
		this.principal = principal;
	}

	public void setSid(final String sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "ACLSID [id=" + this.id + ", sid=" + this.sid + ", principal=" + this.principal + "]";
	}
}
