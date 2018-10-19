package com.bst.user.authentication.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "acl_object_identity", uniqueConstraints = {
		// TODO need to add object_id_class to unique_constraint
		@UniqueConstraint(columnNames = { "object_id_identity" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ACLObjectIdentity {
	@JoinColumn(name = "object_id_class")
	private ACLClass aclClass;

	@Column(name = "entries_inheriting")
	private Integer entriesInheriting;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;

	@Column(name = "object_id_identity")
	private Long objectIdIdentity;

	@JoinColumn(name = "owner_sid")
	private ACLSID ownerSID;

	@JoinColumn(name = "parent_object")
	private ACLObjectIdentity parentObject;

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
		final ACLObjectIdentity other = (ACLObjectIdentity) obj;
		if (this.aclClass == null) {
			if (other.aclClass != null) {
				return false;
			}
		} else if (!this.aclClass.equals(other.aclClass)) {
			return false;
		}
		if (this.entriesInheriting == null) {
			if (other.entriesInheriting != null) {
				return false;
			}
		} else if (!this.entriesInheriting.equals(other.entriesInheriting)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.objectIdIdentity == null) {
			if (other.objectIdIdentity != null) {
				return false;
			}
		} else if (!this.objectIdIdentity.equals(other.objectIdIdentity)) {
			return false;
		}
		if (this.ownerSID == null) {
			if (other.ownerSID != null) {
				return false;
			}
		} else if (!this.ownerSID.equals(other.ownerSID)) {
			return false;
		}
		if (this.parentObject == null) {
			if (other.parentObject != null) {
				return false;
			}
		} else if (!this.parentObject.equals(other.parentObject)) {
			return false;
		}
		return true;
	}

	public ACLClass getAclClass() {
		return this.aclClass;
	}

	public Integer getEntriesInheriting() {
		return this.entriesInheriting;
	}

	public Long getId() {
		return this.id;
	}

	public Long getObjectIdIdentity() {
		return this.objectIdIdentity;
	}

	public ACLSID getOwnerSID() {
		return this.ownerSID;
	}

	public ACLObjectIdentity getParentObject() {
		return this.parentObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.aclClass == null) ? 0 : this.aclClass.hashCode());
		result = (prime * result) + ((this.entriesInheriting == null) ? 0 : this.entriesInheriting.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.objectIdIdentity == null) ? 0 : this.objectIdIdentity.hashCode());
		result = (prime * result) + ((this.ownerSID == null) ? 0 : this.ownerSID.hashCode());
		result = (prime * result) + ((this.parentObject == null) ? 0 : this.parentObject.hashCode());
		return result;
	}

	public void setAclClass(final ACLClass aclClass) {
		this.aclClass = aclClass;
	}

	public void setEntriesInheriting(final Integer entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

	public void setObjectIdIdentity(final Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public void setOwnerSID(final ACLSID ownerSID) {
		this.ownerSID = ownerSID;
	}

	public void setParentObject(final ACLObjectIdentity parentObject) {
		this.parentObject = parentObject;
	}

	@Override
	public String toString() {
		return "ACLObjectIdentity [id=" + this.id + ", aclClass=" + this.aclClass + ", parentObject="
				+ this.parentObject + ", ownerSID=" + this.ownerSID + ", entriesInheriting=" + this.entriesInheriting
				+ ", objectIdIdentity=" + this.objectIdIdentity + "]";
	}

}
