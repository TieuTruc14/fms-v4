package com.eposi.fms.reporting.company;

import com.eposi.common.persitence.DaoUtil;
import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.model.Province;
import com.eposi.fms.persitence.ReportReader;
import com.eposi.fms.persitence.FmsDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.struts2.ServletActionContext;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.io.*;
import java.util.*;

public class SummaryProvinceAction extends AbstractAction {
    private static final long serialVersionUID = -4185642069158756954L;

    private String provinceId;
    private String format = "html"; // pdf, cvs
    private String start; // start date
    private String end; // end date
    private List<CompanySummary> items = new ArrayList<CompanySummary>();
    private Province province;

    public String execute() throws Exception {
        ReportReader beanReportReader = (ReportReader) this.getBean("beanReportReader");
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        DaoUtil beanDaoUtil = (DaoUtil) this.getBean("beanDaoUtil");
//        DataVersionControl beanDataVersionControl = (DataVersionControl) applicationContext.getBean("beanDataVersionControl");

        Date dateStart = null;
        Date dateEnd = null;

        // date
        if (StringUtils.isEmpty(start)) {
            start = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }
        if (StringUtils.isEmpty(end)) {
            end = FormatUtil.formatDate(new Date(), "dd/MM/yyyy");
        }

        try {
            dateStart = FormatUtil.parseDate(start, "dd/MM/yyyy");
        } catch(Exception e)  {
        }
        try {
            dateEnd = FormatUtil.parseDate(end, "dd/MM/yyyy");
        } catch(Exception e)  {
        }

        int days = Days.daysBetween(LocalDate.fromDateFields(dateStart), LocalDate.fromDateFields(dateEnd)).getDays();

        if (days >= 32) {
            this.addActionError("Khoảng thời gian xem báo cáo (từ ngày - đến ngày) phải ngắn hơn 1 tháng (31 ngày).");
            return SUCCESS;
        }

        province = (Province) beanDaoUtil.get(Province.class, provinceId);
        if (province != null) {
            //////////////////////////////////////////////////////////////////////////////////////////////
            // Tìm các công ty trong khoảng thời gian đó dựa trên dữ liệu lưu vết
//            List<Company> companies = beanFmsDao.searchCompanyByProvince(province);
            List<Company> companies = new ArrayList<Company>();
            Set<String> companyIds = new HashSet<String>();

            Date dateEndEndOfDay = LocalDateTime.fromDateFields(dateEnd).plusSeconds(86399).toDate();

            Map<Date, Set<String>> setModifies = null;//beanReportReader.searchParentChildSnapshotProvinceCompany(province.getId(), null, dateEndEndOfDay);
            if ((setModifies != null) && (setModifies.size() > 0)) {
                Collection<Set<String>> collectionChanges = setModifies.values();
                for (Set<String> setCompanyChange : collectionChanges) {
                    companyIds.addAll(setCompanyChange);
                }

                companies = beanFmsDao.getCompanyInListId(companyIds);
            } else {
                companies = beanFmsDao.searchCompanyByProvince(province);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////

//            Map<String, Set<DataVersionControl.Range<Date>>> stringSetMap = beanDataVersionControl.findOwnerDateRange(setModifies, dateStart, dateEndEndOfDay);

            for (Company company : companies) {
                CompanySummary companySummary = null;

                for (int i=0; i <= days; i++) {
                    LocalDate d = LocalDate.fromDateFields(dateStart).withFieldAdded(DurationFieldType.days(), i);
                    Date date = d.toDate();

                    boolean inRange = false;

//                    Set<DataVersionControl.Range<Date>> dateRanges = stringSetMap.get(company.getId());
//                    if (dateRanges != null) {
//                        Iterator<DataVersionControl.Range<Date>> iteratorDateRanges = dateRanges.iterator();
//
//                        while (iteratorDateRanges.hasNext()) {
//                            DataVersionControl.Range<Date> itemDateRange = iteratorDateRanges.next();
//
//                            try {
//                                if (!date.before(itemDateRange.getFrom()) && !date.after(itemDateRange.getTo())) {
//                                    inRange = true;
//                                    break;
//                                }
//                            } catch(Exception eCompareDate) {
////                                eCompareDate.printStackTrace();
//                            }
//                        }
//                    } else {
//                        inRange = true;
//                    }

                    if (inRange) {
                        CompanySummary item = beanReportReader.getCompanySummary(company.getId(), date);
                        if (companySummary == null) {
                            item.setCompanyName(company.getName());
                            item.setVehicleCount(company.getVehicleCount());
                            item.setDriverCount(company.getDriverCount());

                            companySummary = item;
                        } else {
                            companySummary.setSumTripDistance(companySummary.getSumTripDistance() + item.getSumTripDistance());
                            companySummary.setCountTrip(companySummary.getCountTrip() + item.getCountTrip());
                            companySummary.setStopDuration(companySummary.getStopDuration() + item.getStopDuration());
                            companySummary.setCountStop(companySummary.getCountStop() + item.getCountStop());
                            companySummary.setCountDoor(companySummary.getCountDoor() + item.getCountDoor());
                            companySummary.setCountDoorOpenWhileMoving(companySummary.getCountDoorOpenWhileMoving() + item.getCountDoorOpenWhileMoving());

                            companySummary.setCountOverSpeed(companySummary.getCountOverSpeed() + item.getCountOverSpeed());
                            companySummary.setSumOverSpeedDistance(companySummary.getSumOverSpeedDistance() + item.getSumOverSpeedDistance());

                            companySummary.setCount4h(companySummary.getCount4h() + item.getCount4h());
                            companySummary.setCount10h(companySummary.getCount10h() + item.getCount10h());
                        }
                    }

                }

                if (companySummary != null) {
                    items.add(companySummary);
                }
            }
        }

        if (!format.equals("html")) {
            if (format.equals("xlsx")) {
                return "xlsx";
            }
        }

        return SUCCESS;
    }

    public InputStream getStream() throws IOException, InvalidFormatException {
        try {
            String filePath = ServletActionContext.getServletContext().getRealPath("/") + "/WEB-INF/html/reporting/summary.province.xls";

            File file = new File(filePath);
            Workbook wb = WorkbookFactory.create(new FileInputStream(file));
            Sheet sheet = wb.getSheetAt(0);

            final int colummNum = 14;

            sheet.getRow(2).getCell(0).setCellValue("Sở GTVT: " + province.getName());
            if(end.equals(start)){
                sheet.getRow(3).getCell(0).setCellValue("Ngày: " + end);
            } else {
                sheet.getRow(3).getCell(0).setCellValue("Từ " + start + " tới " + end);
            }
            sheet.getRow(3).getCell(11).setCellValue("Ngày xuất báo cáo: " + FormatUtil.formatDate(new Date(), "dd/MM/yyyy"));

            Row rowFirst = sheet.getRow(7);
            int rowcounter = 0;

            for (CompanySummary item : items) {
                Row row = null;

                if (rowcounter == 0) {
                    row = rowFirst;
                } else {
                    // copy style
                    row = sheet.createRow(7 + rowcounter);
                    row.setHeight(rowFirst.getHeight());

                    for (int j = 0; j < colummNum; j++) {
                        row.createCell(j).setCellStyle(rowFirst.getCell(j).getCellStyle());
                    }
                }

                int rowIndex = 8 + rowcounter;
                row.getCell(0).setCellValue(rowcounter + 1);
                row.getCell(1).setCellValue(item.getCompanyName());

                row.getCell(2).setCellValue(item.getVehicleCount());
                row.getCell(3).setCellValue(item.getDriverCount());

                row.getCell(4).setCellValue(item.getSumTripDistance());
                row.getCell(5).setCellValue(item.getCountTrip());

                row.getCell(6).setCellValue(item.getStopDuration()/3600);//hour unit
                row.getCell(7).setCellValue(item.getCountStop());

                row.getCell(8).setCellValue(item.getCountDoorOpenWhileMoving());
                row.getCell(9).setCellValue(item.getCountDoor());

                row.getCell(10).setCellValue(item.getCountOverSpeed());
                row.getCell(11).setCellValue( item.getSumOverSpeedDistance());

                row.getCell(12).setCellValue(item.getCount4h());
                row.getCell(13).setCellValue(item.getCount10h());

                rowcounter++;
            }

            // last row
            Row lastRow = sheet.createRow(7 + rowcounter);
            // copy style
            lastRow.setHeight((short) (rowFirst.getHeight() * 1.2));
            Font font = wb.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 8);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            for (int j = 0; j < colummNum; j++) {
                CellStyle style = wb.createCellStyle();
                style.cloneStyleFrom(rowFirst.getCell(j).getCellStyle());
                style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                style.setFont(font);
                lastRow.createCell(j).setCellStyle(style);
            }

            int rowIndex = 7 + rowcounter;
            lastRow.getCell(1).setCellValue("Tổng");
            lastRow.getCell(2).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(2).setCellFormula("SUM(C8:C" + rowIndex + ")");
            lastRow.getCell(3).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(3).setCellFormula("SUM(D8:D" + rowIndex + ")");

            lastRow.getCell(4).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(4).setCellFormula("SUM(E8:E" + rowIndex + ")");
            lastRow.getCell(5).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(5).setCellFormula("SUM(F8:F" + rowIndex + ")");

            lastRow.getCell(6).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(6).setCellFormula("SUM(G8:G" + rowIndex + ")");
            lastRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(7).setCellFormula("SUM(H8:H" + rowIndex + ")");

            lastRow.getCell(8).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(8).setCellFormula("SUM(I8:I" + rowIndex + ")");
            lastRow.getCell(9).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(9).setCellFormula("SUM(J8:J" + rowIndex + ")");

            lastRow.getCell(10).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(10).setCellFormula("SUM(K8:K" + rowIndex + ")");
            lastRow.getCell(11).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(11).setCellFormula("SUM(L8:K" + rowIndex + ")");

            lastRow.getCell(12).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(12).setCellFormula("SUM(M8:M" + rowIndex + ")");
            lastRow.getCell(13).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            lastRow.getCell(13).setCellFormula("SUM(N8:N" + rowIndex + ")");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);
            byte[] data = baos.toByteArray();

            return new ByteArrayInputStream(data);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<CompanySummary> getItems() {
        return items;
    }

    public static class CompanySummary implements Serializable {
        private static final long serialVersionUID = 4777561783718197792L;

        private String id;
        private String companyId;
        private String companyName;

        private Date date;

        private int vehicleCount;
        private int driverCount;

        private double sumTripDistance = 0; // in km
        private int countTrip;

        private long stopDuration = 0;
        private int countStop = 0;

        private int countDoor = 0;
        private int countDoorOpenWhileMoving = 0;

        private int countCompanyOverSpeed; // dem so doanh nghiep vi pham
        private int countVehicleOverSpeed; // dem so xe vi pham
        private int countOverSpeed;
        private double sumOverSpeedDistance = 0; // in km

        private int count4h;
        private int count10h;

        //
//        private int xmitVehicleCount;

        public CompanySummary() {

        }

        public int getCountDoorOpenWhileMoving() {
            return countDoorOpenWhileMoving;
        }

        public void setCountDoorOpenWhileMoving(int countDoorOpenWhileMoving) {
            this.countDoorOpenWhileMoving = countDoorOpenWhileMoving;
        }

        public int getCount4h() {
            return count4h;
        }

        public void setCount4h(int count4h) {
            this.count4h = count4h;
        }

        public int getCount10h() {
            return count10h;
        }

        public void setCount10h(int count10h) {
            this.count10h = count10h;
        }

        public int getCountDoor() {
            return countDoor;
        }

        public void setCountDoor(int countDoor) {
            this.countDoor = countDoor;
        }

        public long getStopDuration() {
            return stopDuration;
        }

        public void setStopDuration(long stopDuration) {
            this.stopDuration = stopDuration;
        }

        public int getCountStop() {
            return countStop;
        }

        public void setCountStop(int countStop) {
            this.countStop = countStop;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public int getVehicleCount() {
            return vehicleCount;
        }

        public void setVehicleCount(int vehicleCount) {
            this.vehicleCount = vehicleCount;
        }

        public int getDriverCount() {
            return driverCount;
        }

        public void setDriverCount(int driverCount) {
            this.driverCount = driverCount;
        }

        public int getCountCompanyOverSpeed() {
            return countCompanyOverSpeed;
        }

        public void setCountCompanyOverSpeed(int countCompanyOverSpeed) {
            this.countCompanyOverSpeed = countCompanyOverSpeed;
        }

        public int getCountVehicleOverSpeed() {
            return countVehicleOverSpeed;
        }

        public void setCountVehicleOverSpeed(int countVehicleOverSpeed) {
            this.countVehicleOverSpeed = countVehicleOverSpeed;
        }

        public int getCountOverSpeed() {
            return countOverSpeed;
        }

        public void setCountOverSpeed(int countOverSpeed) {
            this.countOverSpeed = countOverSpeed;
        }

        public double getSumOverSpeedDistance() {
            return sumOverSpeedDistance;
        }

        public void setSumOverSpeedDistance(double sumOverSpeedDistance) {
            this.sumOverSpeedDistance = sumOverSpeedDistance;
        }

        public double getSumTripDistance() {
            return sumTripDistance;
        }

        public void setSumTripDistance(double sumTripDistance) {
            this.sumTripDistance = sumTripDistance;
        }

        public int getCountTrip() {
            return countTrip;
        }

        public void setCountTrip(int countTrip) {
            this.countTrip = countTrip;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

//        public int getXmitVehicleCount() {
//            return xmitVehicleCount;
//        }
//
//        public void setXmitVehicleCount(int xmitVehicleCount) {
//            this.xmitVehicleCount = xmitVehicleCount;
//        }

        public double getRatio1000km() {
            double ratio = 0;

            try {
                ratio = countOverSpeed * 1000.0d / sumTripDistance;
            }catch(Exception e) {

            }

            if (Double.isNaN(ratio)) {
                return 0;
            }

            return ratio;
        }
    }

}
