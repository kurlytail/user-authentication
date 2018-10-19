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
@Table(name = "acl_entry", uniqueConstraints = {
		// TODO acl_object_identity needs to be added to unique_constraint
		@UniqueConstraint(columnNames = { "ace_order" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ACLEntry {
	@Column(name = "ace_order")
	private Long aceOrder;

	@JoinColumn(name = "acl_object_identity")
	private ACLObjectIdentity aclObjectIdentity;

	@Column(name = "audit_failure")
	private Integer auditFailure;

	@Column(name = "audit_success")
	private Integer auditSuccess;

	@Column(name = "granting")
	private Integer granting;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;

	@Column(name = "mask")
	private Long mask;

	@Column(name = "object_id_identity")
	private Long objectIdIdentity;

	@JoinColumn(name = "sid")
	private ACLSID sid;

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
		final ACLEntry other = (ACLEntry) obj;
		if (this.aceOrder == null) {
			if (other.aceOrder != null) {
				return false;
			}
		} else if (!this.aceOrder.equals(other.aceOrder)) {
			return false;
		}
		if (this.aclObjectIdentity == null) {
			if (other.aclObjectIdentity != null) {
				return false;
			}
		} else if (!this.aclObjectIdentity.equals(other.aclObjectIdentity)) {
			return false;
		}
		if (this.auditSuccess == null) {
			if (other.auditSuccess != null) {
				return false;
			}
		} else if (!this.auditSuccess.equals(other.auditSuccess)) {
			return false;
		}
		if (this.auditFailure == null) {
			if (other.auditFailure != null) {
				return false;
			}
		} else if (!this.auditFailure.equals(other.auditFailure)) {
			return false;
		}
		if (this.granting == null) {
			if (other.granting != null) {
				return false;
			}
		} else if (!this.granting.equals(other.granting)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.mask == null) {
			if (other.mask != null) {
				return false;
			}
		} else if (!this.mask.equals(other.mask)) {
			return false;
		}
		if (this.objectIdIdentity == null) {
			if (other.objectIdIdentity != null) {
				return false;
			}
		} else if (!this.objectIdIdentity.equals(other.objectIdIdentity)) {
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

	public Long getAceOrder() {
		return this.aceOrder;
	}

	public ACLObjectIdentity getAclObjectIdentity() {
		return this.aclObjectIdentity;
	}

	public Integer getAuditFailure() {
		return this.auditFailure;
	}

	public Integer getAuditSuccess() {
		return this.auditSuccess;
	}

	public Integer getGranting() {
		return this.granting;
	}

	public Long getMask() {
		return this.mask;
	}

	public Long getObjectIdIdentity() {
		return this.objectIdIdentity;
	}

	public ACLSID getSid() {
		return this.sid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.aceOrder == null) ? 0 : this.aceOrder.hashCode());
		result = (prime * result) + ((this.aclObjectIdentity == null) ? 0 : this.aclObjectIdentity.hashCode());
		result = (prime * result) + ((this.auditSuccess == null) ? 0 : this.auditSuccess.hashCode());
		result = (prime * result) + ((this.auditFailure == null) ? 0 : this.auditFailure.hashCode());
		result = (prime * result) + ((this.granting == null) ? 0 : this.granting.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.mask == null) ? 0 : this.mask.hashCode());
		result = (prime * result) + ((this.objectIdIdentity == null) ? 0 : this.objectIdIdentity.hashCode());
		result = (prime * result) + ((this.sid == null) ? 0 : this.sid.hashCode());
		return result;
	}

	public void setAceOrder(final Long aceOrder) {
		this.aceOrder = aceOrder;
	}

	public void setAclObjectIdentity(final ACLObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public void setAuditFailure(final Integer audit_failure) {
		this.auditFailure = audit_failure;
	}

	public void setAuditSuccess(final Integer auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public void setGranting(final Integer granting) {
		this.granting = granting;
	}

	public void setMask(final Long mask) {
		this.mask = mask;
	}

	public void setObjectIdIdentity(final Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public void setSid(final ACLSID sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "ACLEntry [id=" + this.id + ", aclObjectIdentity=" + this.aclObjectIdentity + ", objectIdIdentity="
				+ this.objectIdIdentity + ", aceOrder=" + this.aceOrder + ", sid=" + this.sid + ", mask=" + this.mask
				+ ", granting=" + this.granting + ", auditSuccess=" + this.auditSuccess + ", auditFailure="
				+ this.auditFailure + "]";
	}
}
