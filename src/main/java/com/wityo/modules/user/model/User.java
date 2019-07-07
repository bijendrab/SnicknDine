package com.wityo.modules.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 2681531852204068105L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String firstName;
	private String lastName;
	private Long phoneNumber;
	private boolean enabled;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Customer customer;

	public User() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return firstName;
	}

	public void setEmailId(String emailId) {
		this.firstName = emailId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLastName(String password) {
		this.lastName = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLastName() {
		return lastName;
	}
	
	@PrePersist
	protected void onUserCreation() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUserUpdation() {
		this.updatedAt = LocalDateTime.now();
	}

	/*-------------SPRING SECURITY METHODS------------------------*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return this.lastName;
	}

	@Override
	public String getUsername() {
		return this.firstName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	/*-------------SPRING SECURITY METHODS------------------------*/
}
