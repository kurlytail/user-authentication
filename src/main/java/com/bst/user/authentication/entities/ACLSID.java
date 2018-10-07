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
@Table(name = "ACL_SID", uniqueConstraints = { @UniqueConstraint(columnNames = { "SID", "PRINCIPAL" }) })
public class ACLSID {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "ID")
	private Long id;

	@Column(name = "SID")
	private String sid;

	@Column(name = "PRINCIPAL")
	private int principal;
}
