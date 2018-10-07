package com.bst.user.authentication.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acl_class", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "acl_object_identity", "ace_order" }) })
public class ACLEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;

	@Column(name = "acl_object_identity")
	private ACLObjectIdentity aclObjectIdentity;

	@Column(name = "object_id_identity")
	private Long objectIdIdentity;

	@Column(name = "ace_order")
	private Long aceOrder;

	@Column(name = "sid")
	private ACLSID sid;

	@Column(name = "mask")
	private Long mask;
	
	@Column(name = "granting")
	private Integer granting;
	
	@Column(name = "audit_success")
	private Integer auditSuccess;
	
	@Column(name = "sid")
	private Integer audit_failure;
}
