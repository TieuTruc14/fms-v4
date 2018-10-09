package com.eposi.fms.web.admin.store.batch.device;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.math.ProductKeyUtil;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.services.QueueingConsumerReportClient;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class DeviceAddFromExcelAction extends AbstractAction {
    private static final long serialVersionUID = -6035957006511825244L;
    private File file;
    private String batchId;
    private String message="";
    static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        QueueingConsumerReportClient beanQueueingConsumerReportClient = (QueueingConsumerReportClient) this.getBean("beanQueueingConsumerReportClient");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_DEVICE_ADD)){
            message=getTitleText("fms.failed.authority");
            return SUCCESS;
        }
        try {
            Batch batch=beanFmsDao.getBatch(batchId);
            if(batch!=null) {
                if (file != null) {
                    FileInputStream myInput = new FileInputStream(file);
                    POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                    HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                    HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                    Iterator<Row> rowIter = mySheet.rowIterator();
                    if (rowIter.hasNext()) {
                        List<Device> lstDevice = new ArrayList<>();
                        HashMap<String,String> map=new HashMap<>();
                        List<String> lstIdCheckDB=new ArrayList<>();
                        String stringDuppecate="";
                        int countDuppercate=0;
                        String stringDeviceNull="";
                        int totalDevice=0;

                        //Pass First row meta data
                        rowIter.next();
                        //đếm nếu có nhiều lần ô nhập vào rỗng Id
                        int countNullId=0;
                        //Begin get device
                        while (rowIter.hasNext()&&countNullId<10) {
                            HSSFRow myRow = (HSSFRow) rowIter.next();
                            Cell cellDeviceId =myRow.getCell(0);
                            //xay ra khi đã duyệt hết file nhưng do những dòng cuối excel vẫn định dạng thì nó tính là giá trị null và duyệt tiếp, sẽ lỗi.
                            if(cellDeviceId==null){
                                countNullId++;
                                continue;
                            }
                            String deviceId="";
                            if(cellDeviceId.getCellType()==1){
                                deviceId= FormatUtil.removeSpecialCharacters(cellDeviceId.toString().trim());
                            }else{
                                deviceId=new java.text.DecimalFormat("0").format( cellDeviceId.getNumericCellValue() ).trim();//tránh bị biến dạng thành 1.234634353E9 khi nhập sim
                            }
                            //KIEM TRA TRUNG MÃ
                            if(map.get(deviceId)!=null){
                                stringDuppecate+=", "+deviceId;
                                countDuppercate++;
                                continue;
                            }
                            map.put(deviceId,deviceId);
                            Date dateStart = new Date();
                            try {
                                Cell celldateStart = myRow.getCell(1);
                                dateStart = celldateStart.getDateCellValue();
                            }catch (Exception e){}


                            Cell cellDescription =myRow.getCell(2);
                            String description="";
                            if(cellDescription!=null){
                                if(!cellDescription.getStringCellValue().equals("")) description = cellDescription.toString().trim();
                            }

                            Device device = new Device();
                            device.setBatch(batch);
                            device.setId(deviceId);
                            device.setDateStart(dateStart);
                            device.setDescription(description);
                            device.setDateCreated(new Date());
                            device.setDateUpdated(new Date());
                            device.setUserCreated(user.getUsername());
                            device.setCompany(user.getCompany());

                            lstIdCheckDB.add(deviceId);
                            lstDevice.add(device);
                            totalDevice++;
                        }

                        //kiem tra xem trong database da co chua, neu chua co moi bat dau tao product_key va konexyId
                        List<Device> lstCheckDB=beanFmsDao.searchDevicesByListId(lstIdCheckDB);
                        if(lstCheckDB!=null && lstCheckDB.size()>0){
                            for(Device de:lstCheckDB){
                                stringDeviceNull+=", "+de.getId();
                            }
                        }
                        //check trung ma hoac ko ton tai thi deu return
                        if(stringDuppecate.length()>0 || stringDeviceNull.length()>0){
                            if(stringDuppecate.length()>0){
                                message+=countDuppercate+"/"+totalDevice+" TB Trùng mã: "+stringDuppecate.substring(1,stringDuppecate.length());
                            }
                            if(stringDeviceNull.length()>0){
                                message+="--"+lstCheckDB.size()+"/"+totalDevice+" TB Đã tồn tại:"+stringDeviceNull.substring(1,stringDeviceNull.length());
                            }
                            return SUCCESS;
                        }


                        List<String> lstCheckErrorCreateKey=new ArrayList<>();
                        /**
                         * Generate  product_key and konexyId
                         */
                        for(Device item:lstDevice){
                            Device checkDeviceKey=null;
                            String key="";
                            int count=0;
                            while(count<30){
                                key= ProductKeyUtil.genKey(batch.getModel().getId(), batch.getCode());
                                checkDeviceKey=beanFmsDao.getDeviceByProductKey(key);
                                if(checkDeviceKey==null){
                                    break;
                                }
                                count++;
                            }

                            if(checkDeviceKey==null){
                                item.setProduct_key(key);
                                //Create KonexyID for logging activity
                                String descriptionKonexy = "{ \"name\":\"" + item.getId() + "\"}";
                                String konexyId = beanReportReader.newKonexyId(descriptionKonexy);
                                item.setKonexyId(konexyId);
                            }else{
                                lstCheckErrorCreateKey.add(item.getId());
                            }
                        }
                        if(lstDevice.size()>0){
                            beanFmsDao.saveOrUpdateDevices(lstDevice);

                            // Activity
                            Activity activity = new Activity();
                            activity.setId(user.getCompany().getId());
                            activity.setActionName("Update");
                            activity.setActorName(user.getUsername());
                            activity.setObjectId(batch.getId());
                            activity.setObjectName(Batch.class.getName());
                            activity.setIndirectObjectName("/store/batch/batch.detail.action?id" + batch.getId());
                            activity.setDate(new Date());
                            activity.setContext("Update lô");
                            activity.setPassive(true);
                            //Send message Activity to server Aggregate
                            byte[] bytes = conf.asByteArray(activity);
                            beanQueueingConsumerReportClient.put(bytes);

                            if(lstCheckErrorCreateKey.size()>0){
                                message+="\nHệ thống không tạo được mã sản phẩm cho một vài thiết bị,hãy thêm lại!(";
                                for(String deId:lstCheckErrorCreateKey){
                                    message+=deId+",";
                                }
                                message+=")";
                            }
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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
