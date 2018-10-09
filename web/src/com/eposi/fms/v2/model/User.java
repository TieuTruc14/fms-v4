package com.eposi.fms.v2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	private static final long serialVersionUID = -7524354897646119560L;
	private long id;
	private String name;
	private String note;
	private String username;
	private String password;
	private Boolean isSuperUser;
	private Boolean realtionVehicle;// using for vehicle have tow device 
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	private int soType;// 0 Default , 1 tau , 2 pha
	private boolean enabled;
	private int view;
	private Owner owner;

	public void addRole(String newRole) throws Exception {
		if (newRole != null) {
			if (newRole.trim().length() > 0) {
				GrantedAuthority role = new SimpleGrantedAuthority(newRole);

				if (!this.authorities.contains(role))
					this.authorities.add(role);
			} else {
				throw new Exception("Role name is invalid");
			}
		} else
			throw new Exception("Role name is null");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isEnable() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public Boolean getIsSuperUser() {
		return isSuperUser;
	}

	public void setIsSuperUser(Boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public Boolean getRealtionVehicle() {
		return realtionVehicle;
	}

	public void setRealtionVehicle(Boolean realtionVehicle) {
		this.realtionVehicle = realtionVehicle;
	}

	public int getSoType() {
		return soType;
	}

	public void setSoType(int soType) {
		this.soType = soType;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}
}