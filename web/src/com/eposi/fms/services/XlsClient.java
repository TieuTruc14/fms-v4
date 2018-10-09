package com.eposi.fms.services;


import com.eposi.common.util.AbstractBean;
import com.eposi.fms.model.Company;
import org.apache.axis.utils.StringUtils;
import com.eposi.xls.reversegeocoding.client.ReverseGeocodingClient;
import com.eposi.xls.reversegeocoding.proto.ReverseGeocodingProto;

public class XlsClient extends AbstractBean {
    private static final long serialVersionUID = -1156378177712024524L;

    public String reverseGeocode(double x, double y){
		String address = null;
		
		int i=0;	
		while ((StringUtils.isEmpty(address)) && (i<3)){
			address = doreverseGeocode(x, y);
			i++;
		}
		
		return address;	
	}

	public String reverseGeocode(double x, double y, Company company){
		String address = null;

//		address = findPrivatePositionName(x, y, company);
//		if(address != ""){
//			return address;
//		}
		
		int i=0;
		while ((StringUtils.isEmpty(address)) && (i<3)){
			address = doreverseGeocode(x, y);
			i++;
		}
		
		return address;	
	}
	
//	private String findPrivatePositionName(double x, double y, Company company) {
//
//		String address = "";
//
//		FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
//		List<Position> lstPoi = beanFmsDao.searchPositionByCompany(company);
//
//		if (lstPoi != null) {
//			double minDistance = Integer.MAX_VALUE;
//			Position poiClosest =null;
//			for (int i = 0; i < lstPoi.size(); i++) {
//				Position poi = lstPoi.get(i);
//				double distance = GeoUtil.distanceInMeters(poi.getX(), poi.getY(), x, y);
//				if(distance<minDistance){
//					minDistance = distance;
//					poiClosest = poi;
//				}
//			}
//			if(poiClosest!=null){
//				if (minDistance <= poiClosest.getRadius()) {
//					address = poiClosest.getName();
//					return address;
//				}
//			}
//		}
//
//		return address;
//	}
	
	private String doreverseGeocode(double x, double y) {
		try{
			ReverseGeocodingClient beanReverseGeocodingClient = (ReverseGeocodingClient)this.getApplicationContext().getBean("beanReverseGeocodingClient");
			ReverseGeocodingProto.ReverseGeocodingRequest msgRequest = ReverseGeocodingProto.ReverseGeocodingRequest.newBuilder().setX(x).setY(y).build();
			ReverseGeocodingProto.ReverseGeocodingStreetAddressResponse msgResponse = beanReverseGeocodingClient.executeStreetAddress(msgRequest);
			
			if(msgResponse!=null){
				StringBuffer sbAddress = new StringBuffer();
				if(msgResponse.hasBuilding()){
					if(!msgResponse.getBuilding().equals("0")){
						sbAddress.append((new StringBuilder(String.valueOf(msgResponse.getBuilding()))).append(" ").toString());
					}
				}
				if(msgResponse.hasStreet()){
				if(!msgResponse.getStreet().equals(""))
	                sbAddress.append((new StringBuilder(String.valueOf(msgResponse.getStreet()))).append(", ").toString());
				}
				
				sbAddress.append((new StringBuilder(String.valueOf(msgResponse.getCommune()))).append(", ").append(msgResponse.getDistrict()).append(", ").append(msgResponse.getProvince()).toString());
				
				return sbAddress.toString();
			}
			
		}catch (Exception e) {
			System.out.println("Eposi.XlsHelper.ex:"+e.getMessage());
		}
		
		java.text.DecimalFormat format = new java.text.DecimalFormat("##0.000000");
		
		//Nếu không tìm thấy địa chỉ, thì trả lại tọa độ
		return "( " + format.format(x) + ", " + format.format(y) + " )";
	}
	
//	public static void main(String args[]) {
//		Company company = new Company();
//        company.setId("3966");
//
//		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/applicationContextApp.xml");
//		XlsClient beanXlsClient =(XlsClient)ctx.getBean("beanXlsClient");
//		String name = beanXlsClient.findPrivatePositionName(107.955437, 16.331188, company);
//		System.out.print(name);
//	}
}
