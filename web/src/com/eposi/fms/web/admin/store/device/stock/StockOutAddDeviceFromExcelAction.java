package com.eposi.fms.web.admin.store.device.stock;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class StockOutAddDeviceFromExcelAction extends AbstractAction {
    private static final long serialVersionUID = 5602578045893012019L;

    private File file;
    private String id;
    private String message="";

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_STOCK_DEVICE_ADD)){
            message=getTitleText("fms.failed.authority");
            return INPUT;
        }

        try {
            Stock stock= beanFmsDao.getStock(Long.parseLong(id));
            if(stock!=null) {
                if (file != null) {
                    FileInputStream myInput = new FileInputStream(file);
                    POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                    HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                    HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                    Iterator<Row> rowIter = mySheet.rowIterator();
                    if (rowIter.hasNext()) {
                        List<Device> lstDevice = new ArrayList<>();
                        List<StockDetail> stockDetailList = new ArrayList<StockDetail>();
                        Company company = user.getCompany();
                        HashMap<String,String> map=new HashMap<>();

                        String stringDuppecate="";//Trùng mã TB từ file excels
                        String stringDeviceNull="";//TB ko tồn tại trên hệ thống
                        String stringPermission="";//Thiết bị không thuộc công ty này
                        String stringStock="";//những TB đã thuộc phiếu xuất khác
                        int countDuppercate=0,countDeviceNull=0,countPermission=0,countStock=0,totalDevice=0;

                        //Pass First row meta data
                        rowIter.next();
                        //khoi tao bien count
                        int count=0;
                        //Begin get device
                        while (rowIter.hasNext()&& count<10) {
                            HSSFRow myRow = (HSSFRow) rowIter.next();
                            Cell cellDeviceId =myRow.getCell(0);
                            //xay ra khi đã duyệt hết file nhưng do những dòng cuối excel vẫn định dạng thì nó tính là giá trị null và duyệt tiếp, sẽ lỗi.
                            if(cellDeviceId==null){
                                count++;
                                continue;
                            }
                            totalDevice++;
                            String deviceId="";
                            if(cellDeviceId.getCellType()==1){
                                deviceId= FormatUtil.removeSpecialCharacters(cellDeviceId.toString().trim());
                            }else{
                                deviceId=new java.text.DecimalFormat("0").format( cellDeviceId.getNumericCellValue() ).trim();//tránh bị biến dạng thành 1.234634353E9 khi nhập sim
                            }
                            if(map.get(deviceId)!=null){
                                stringDuppecate+=", "+deviceId;countDuppercate++;
                                continue;
                            }
                            map.put(deviceId,deviceId);
                            Device device=beanFmsDao.getDevice(deviceId);
                            if(device==null){
                                stringDeviceNull+=", "+deviceId;countDeviceNull++;
                                continue;
                            }
                            if(device.getCompany().getId().equals(company.getId())) {
                                if(!device.isStock()) {
                                    device.setStock(true);//xac nhan thiet bi da thuoc phieu xuat
                                    lstDevice.add(device);
                                    StockDetail stockDetail = new StockDetail();
                                    stockDetail.setStock(stock);
                                    stockDetail.setDevice(device);
                                    stockDetail.setDateCreated(new Date());
                                    stockDetail.setUserCreated(user);
                                    stockDetailList.add(stockDetail);
                                }else{
                                    stringStock+=", "+deviceId;countStock++;
                                }
                            }else{
                                stringPermission+=", "+deviceId;countPermission++;
                               continue;
                            }

                        }

                        if(stringDuppecate.length()>0 || stringDeviceNull.length()>0 || stringPermission.length()>0|| stringStock.length()>0){
                            if(stringDuppecate.length()>0){
                                message+=" *Có "+countDuppercate+"/"+totalDevice+" TB Trùng mã: "+stringDuppecate.substring(1,stringDuppecate.length());
                            }
                            if(stringDeviceNull.length()>0){
                                message+=" *Có "+countDeviceNull+"/"+totalDevice+" TB không tồn tại: "+stringDeviceNull.substring(1,stringDeviceNull.length());
                            }
                            if(stringPermission.length()>0){
                                message+=" *Có "+countPermission+"/"+totalDevice+" TB không thuộc quản lý: "+stringPermission.substring(1,stringPermission.length());
                            }
                            if(stringStock.length()>0){
                                message+=" *Có "+countStock+"/"+totalDevice+" TB đã thuộc phiếu khác: "+stringStock.substring(1,stringStock.length());
                            }

                            return INPUT;
                        }

                        if(stockDetailList.size()>0){
                            beanFmsDao.saveOrUpdateDevices(lstDevice);
                            beanFmsDao.saveOrUpdateStockDetal(stockDetailList);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }

    public String getEditMode() {
        return "addnew.save";
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
