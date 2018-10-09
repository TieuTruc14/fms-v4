package com.eposi.fms.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class User implements UserDetails {
    private static final long serialVersionUID = -3109047237666029521L;
    private String username;
	private String password;
    private String name;
    private String phone;
    private int     level;
    private int     view;
    private Company company;
    private transient List<GrantedAuthority> grantedAuths;
    private transient HashSet<String> vehicles = new HashSet<String>(); // list of vehicle accessible
    private transient HashSet<String> companies = new HashSet<String>(); // list of companies accessible
    private boolean supperUser;//tai khoan chính
    private boolean enable;
    private String konexyId;
	
	public User() {
		super();
	}

	public String getUsername() {
		return username;
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

    public void setUsername(String username) {
		this.username = username;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuths;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<GrantedAuthority> getGrantedAuths() {
        return grantedAuths;
    }

    public void setGrantedAuths(List<GrantedAuthority> grantedAuths) {
        this.grantedAuths = grantedAuths;
    }

    public HashSet<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(HashSet<String> vehicles) {
        this.vehicles = vehicles;
    }

    public HashSet<String> getCompanies() {
        return companies;
    }

    public void setCompanies(HashSet<String> companies) {
        this.companies = companies;
    }

    /**
     * check if this user can access to a specific vehicle
     * @param vehicleId
     * @return
     */
    public boolean hasPermissionVehicle(String vehicleId) {
        if (level == 0) {
            return  true;
        }

        return vehicles.contains(vehicleId);
    }

    /**
     * check if this user can access to a specific company
     * @param companyId
     * @return
     */
    public boolean hasPermissionCompany(String companyId) {
        if (level == 0) {
            return  true;
        }

        return companies.contains(companyId);
    }

    public boolean isSupperUser() {
        return supperUser;
    }

    public void setSupperUser(boolean supperUser) {
        this.supperUser = supperUser;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getKonexyId() {
        return konexyId;
    }

    public void setKonexyId(String konexyId) {
        this.konexyId = konexyId;
    }
}