package com.eposi.fms.web.admin.user;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.common.PermissionDefine;
import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.Company;
import com.eposi.fms.persitence.PagingResult;
import com.eposi.fms.model.Province;
import com.eposi.fms.model.User;
import com.eposi.fms.persitence.FmsDao;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserListingAction extends AbstractAction {
    private static final long serialVersionUID = 203659251585418953L;

    private PagingResult page = new PagingResult();
    private String filterUserName;
    private String format = "html";

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!beanFmsDao.isPermision(user, PermissionDefine.ROLE_USER_VIEW)){
            addActionError("action.error.permission");
            return ERROR;
        }
        try {
            if (page.getPageNumber() > 0) {
                page.setPageNumber(page.getPageNumber() -1 );
            }

            beanFmsDao.pageUser(page, filterUserName, user.getCompany());

            // get Owner Name (Province or Company)
            List<UserDisplay> userDisplayList = new ArrayList<UserDisplay>();

            List<?> items = page.getItems();
            for (Object obj : items) {
                User usr = (User) obj;
                UserDisplay userDisplay = new UserDisplay();
                userDisplay.setUsername(usr.getUsername());
                userDisplay.setPassword(usr.getPassword());
                userDisplay.setName(usr.getName());
                Company company = usr.getCompany();
                if(company!=null){
                    userDisplay.setCompany(company);
                    Province province = company.getProvince();
                    if (province != null) {
                        userDisplay.setProvince(province);
                    }
                }

                userDisplayList.add(userDisplay);
            }
            page.setItems(userDisplayList);
        } catch (Exception e){
            System.out.println("com.eposi.fms.web.admin.user.UserListingAction.execute: " + e.getMessage());
        }

        if (!format.equals("html")) {
            if (format.equals("xlsx")) {
                return "xlsx";
            }
        }
        return SUCCESS;
    }

    public PagingResult getPage() {
        return page;
    }


    public String getFilterUserName() {
        return filterUserName;
    }

    public void setFilterUserName(String filterUserName) {
        this.filterUserName = filterUserName;
    }

    public static class UserDisplay implements Serializable {
        private static final long serialVersionUID = 8926563197077663230L;

        private String username;
        private String password;
        private String name;
        private Company  company;
        private Province province;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Province getProvince() {
            return province;
        }

        public void setProvince(Province province) {
            this.province = province;
        }
    }

    public InputStream getStream() throws IOException, InvalidFormatException {
        try {
            FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user!=null){
                List<User> items = beanFmsDao.searchAllUserManagerByCompany(user.getCompany());
                String filePath = ServletActionContext.getServletContext().getRealPath("/") + "/WEB-INF/html/user/user.list.xls";

                File file = new File(filePath);
                Workbook wb = WorkbookFactory.create(new FileInputStream(file));
                Sheet sheet = wb.getSheetAt(0);

                sheet.getRow(3).getCell(0).setCellValue("Ngày xuất báo cáo: " + FormatUtil.formatDate(new Date(), "dd/MM/yyyy"));
                Row rowFirst = sheet.getRow(7);
                int rowcounter = 0;
                for (User item : items) {
                    Row row = null;
                    if (rowcounter == 0) {
                        row = rowFirst;
                    } else {
                        // copy style
                        row = sheet.createRow(7 + rowcounter);
                        row.setHeight(rowFirst.getHeight());
                        Font font = wb.createFont();
                        font.setFontName("Arial");
                        font.setFontHeightInPoints((short) 8);
                        for (int j = 0; j < 4; j++) {
                            CellStyle style = wb.createCellStyle();
                            style.cloneStyleFrom(rowFirst.getCell(j).getCellStyle());
                            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                            style.setFont(font);
                            row.createCell(j).setCellStyle(style);

                        }
                    }

                    int rowIndex = 8 + rowcounter;
                    row.getCell(0).setCellValue(rowcounter + 1);
                    row.getCell(1).setCellValue(item.getUsername());
                    row.getCell(2).setCellValue(item.getPassword());
                    row.getCell(3).setCellValue(item.getLevel());
                    rowcounter++;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                wb.write(baos);
                byte[] data = baos.toByteArray();

                return new ByteArrayInputStream(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
