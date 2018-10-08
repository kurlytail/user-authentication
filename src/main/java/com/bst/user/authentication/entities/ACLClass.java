package com.bst.user.authentication.entities;

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
@Table(name = "acl_class", uniqueConstraints = { @UniqueConstraint(columnNames = { "class" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ACLClass {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;

	@Column(name = "class")
	private String aclClass;

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
		final ACLClass other = (ACLClass) obj;
		if (this.aclClass == null) {
			if (other.aclClass != null) {
				return false;
			}
		} else if (!this.aclClass.equals(other.aclClass)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getAclClass() {
		return this.aclClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.aclClass == null) ? 0 : this.aclClass.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public void setAclClass(final String aclClass) {
		this.aclClass = aclClass;
	}

	@Override
	public String toString() {
		return "ACLClass [id=" + this.id + ", aclClass=" + this.aclClass + "]";
	}
}
