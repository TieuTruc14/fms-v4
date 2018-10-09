package com.eposi.fms.web.admin.log;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Report;
import com.eposi.fms.model.Vehicle;
import com.eposi.fms.persitence.FmsDao;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.web.model.ItemBatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogAction extends AbstractAction {
    private static final long serialVersionUID = 4031378376652109355L;

    private List<ItemBatch> items = new ArrayList<ItemBatch>();
    private String vehicleId;
    private String day;

	public String execute() {
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        FmsDao beanFmsDao             = (FmsDao) this.getBean("beanFmsDao");
        if(vehicleId==null||vehicleId.isEmpty()){
            return SUCCESS;
        }
        if(day==null||day.isEmpty()){
            return SUCCESS;
        }

        vehicleId = FormatUtil.removeSpecialCharacters(vehicleId);
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = FormatUtil.parseDate(day, "yyyy/MM/dd");
        } catch(Exception e)  {
        }
        try {
            dateEnd = FormatUtil.parseDate(day+"-23:59:59", "yyyy/MM/dd-HH:mm:ss");
        } catch(Exception e)  {
        }

        //////////////////////////////////////////////////
        // track log
        try {
            Report report = beanFmsDao.getReport(vehicleId);
            if(report!=null) {
                if(report.getLog()!=null) {
                    items = beanReportReader.loadItemBatch(report.getLog(), vehicleId, dateStart, dateEnd);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return SUCCESS;
	}

    public List<ItemBatch> getItems() {
        return items;
    }

    public void setItems(List<ItemBatch> items) {
        this.items = items;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



}
