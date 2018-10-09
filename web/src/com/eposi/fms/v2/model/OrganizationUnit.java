package com.eposi.fms.v2.model;

public class OrganizationUnit extends Owner {
	private static final long serialVersionUID = 1443682526297960391L;
	
//	private List<OrganizationUnit> chidren = new ArrayList<OrganizationUnit>();
//	private List<Customer> customers = new ArrayList<Customer>();
//	private List<Device> devices = new ArrayList<Device>();
	
	private Boolean allowedChild = false;

	public boolean isAllowedChild() {
		return allowedChild;
	}

	public void setAllowedChild(boolean allowedChild) {
		this.allowedChild = allowedChild;
	}

	
	
//	public List<OrganizationUnit> getChidren() {
//		return chidren;
//	}
//
//	public void setChidren(List<OrganizationUnit> chidren) {
//		this.chidren = chidren;
//	}
//
//	public List<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(List<Customer> customers) {
//		this.customers = customers;
//	}
//
//	public List<Device> getDevices() {
//		return devices;
//	}
//
//	public void setDevices(List<Device> devices) {
//		this.devices = devices;
//	}
}
