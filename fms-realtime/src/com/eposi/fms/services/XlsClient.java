package com.eposi.fms.services;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.xls.reversegeocoding.client.ReverseGeocodingClient;
import com.eposi.xls.reversegeocoding.proto.ReverseGeocodingProto;
import org.apache.commons.lang3.StringUtils;

public class XlsClient extends AbstractBean {
    private static final long serialVersionUID = 8574029577262654813L;
	private FmsDao beanFmsDao;
	private ReverseGeocodingClient beanReverseGeocodingClient;

    public String reverseGeocode(double x, double y, String companyId){

		String address = null;
//		address = findPrivatePositionName(x, y, companyId);
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


	
	private String doreverseGeocode(double x, double y) {

		try{
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

//	private String findPrivatePositionName(double x, double y, String companyId) {
//
//		String address = "";
//		List<Position> lstPoi = getPositionByCompany(companyId);
//		if (lstPoi != null) {
//			double minDistance = Integer.MAX_VALUE;
//			Position poiClosest =null;
//			try {
//				WKTReader wkt = new WKTReader();
//				Geometry g = wkt.read("POINT(" + x + " " + y + ")");
//
//				for (int i = 0; i < lstPoi.size(); i++) {
//					Position poi = lstPoi.get(i);
//					double distance = poi.getGeometry().distance(g);
//					if (distance < minDistance) {
//						minDistance = distance;
//						poiClosest = poi;
//					}
//				}
//
//				if (poiClosest != null) {
//					if (minDistance <= 100d) {
//						return poiClosest.getName();
//					}
//				}
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//
//		return address;
//	}

//	private ConcurrentHashMap<String, List<Position>> mapPosition = new ConcurrentHashMap<String, List<Position>>();
//
//	private List<Position> getPositionByCompany(String compnanyId) {
//		return  mapPosition.get(compnanyId);
//	}
//
//	public synchronized void loadPosition() {
//		System.out.println("XlsClient.loadPosition");
//		List<Position> lstPosition =  beanFmsDao.listPosition();
//		if(lstPosition!=null) {
//			for (Position position : lstPosition) {
//				String compnanyId = position.getCompany().getId();
//				List<Position>  companyPois = mapPosition.get(compnanyId);
//				if(companyPois==null){
//					companyPois = new ArrayList<Position>();
//				}
//				if(!isExist(companyPois, position)) {
//					companyPois.add(position);
//					mapPosition.put(compnanyId, companyPois);
//				}
//			}
//		}
//	}
//
//	public boolean isExist(List<Position>  companyPois, Position position){
//		for(Position posi:companyPois){
//			if(posi.getId()==position.getId()){
//				return  true;
//			}
//		}
//
//		return  false;
//	}

	public  void  init(){
		beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
		beanReverseGeocodingClient = (ReverseGeocodingClient)this.getApplicationContext().getBean("beanReverseGeocodingClient");
	}
}
