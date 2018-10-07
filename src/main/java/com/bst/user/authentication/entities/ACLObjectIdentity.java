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
@Table(name = "acl_object_identity", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "object_id_class", "object_id_identity" }) })
public class ACLObjectIdentity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;

	@Column(name = "object_id_class")
	private ACLClass aclClass;

	@Column(name = "parent_object")
	private ACLObjectIdentity parentObject;

	@Column(name = "owner_sid")
	private ACLSID ownerSID;

	@Column(name = "entries_inheriting")
	private Integer entriesInheriting;
	
	@Column (name = "object_id_identity")
	private Long objectIdIdentity;

}
