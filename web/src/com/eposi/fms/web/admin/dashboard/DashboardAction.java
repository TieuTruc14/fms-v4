package com.eposi.fms.web.admin.dashboard;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.*;
import com.eposi.fms.persitence.FmsDao;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class DashboardAction  extends AbstractAction{
    private static final long serialVersionUID = 375984164126582496L;
    private Dashboard item;
    private List<Item> pies = new LinkedList<>();
    private List<Item> months = new LinkedList<>();
    private List<ItemDown> itemDowns = new LinkedList<>();

    private int dayCount;
    private int weekCount;
    private int monthCount;
    private int yearCount;

    private int year;
    private String[] monthOfYear ={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    public String execute() {
        FmsDao beanFmsDao = (FmsDao) this.getBean("beanFmsDao");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Company company = user.getCompany();
            item = new Dashboard();
            item.setBranchs(beanFmsDao.getAllOrganizationByParent(company).size());
            item.setCompanys(company.getCompanyCount());
            item.setVehicles(company.getVehicleCount());
            item.setDrivers(company.getDriverCount());

            //Set Pie Branch
            Company root = beanFmsDao.getCompany("TS0000000001");
            if(root.getVehicleCount()>0) {
                List<Company> lstBranch = beanFmsDao.searchOrganizationByParent(root);
                for (Company branch : lstBranch) {
                    Item pie = new Item();
                    pie.setName(branch.getName());
                    int value = (int) ((branch.getVehicleCount() * 100) / root.getVehicleCount());
                    pie.setValue(value);
                    pies.add(pie);
                }
            }

            //Set VAggregation
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int day =calendar.get(Calendar.DAY_OF_MONTH);
            int week =calendar.get(Calendar.WEEK_OF_YEAR);
            int month =calendar.get(Calendar.MONTH);
            int year =calendar.get(Calendar.YEAR);
            //Set year
            this.year = year;

            dayCount   = beanFmsDao.getVehicleCountByDay(company.getId(), day, month, year);
            weekCount  = beanFmsDao.getVehicleCountByWeek(company.getId(), week, year);
            monthCount = beanFmsDao.getVehicleCountByMonth(company.getId(), month, year);
            yearCount  = beanFmsDao.getVehicleCountByYear(company.getId(), year);

            for(int i=0;i<=month;i++){
                Item item = new Item();
                item.setName(monthOfYear[i]);
                List<VehicleAggregation> vehicleAggregations =beanFmsDao.getVehicleByMonth(company.getId(), i, year);
                if(vehicleAggregations!=null) {
                    int vehicleInMonth = 0;
                    for (VehicleAggregation vehicleAggregation : vehicleAggregations) {
                        vehicleInMonth += vehicleAggregation.getVehicleCount();
                    }
                    item.setValue(vehicleInMonth);
                    months.add(item);
                    List<Item> lstItem = new LinkedList<>();
                    SortedMap <Integer, Item> map = new TreeMap<Integer, Item>();
                    for (VehicleAggregation vehicleAggregation : vehicleAggregations) {
                        Item obj = new Item();
                        obj.setName(vehicleAggregation.getInDay() + "");
                        obj.setValue(vehicleAggregation.getVehicleCount());
                        map.put(vehicleAggregation.getInDay(), obj);
                    }
                    Iterator<Integer> iterator = map.keySet().iterator();
                    while (iterator.hasNext()) {
                        lstItem.add(map.get(iterator.next()));
                    }
                    ItemDown itemDown = new ItemDown();
                    itemDown.setName(monthOfYear[i]);
                    itemDown.setItems(lstItem);
                    itemDowns.add(itemDown);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("com.eposi.fms.web.admin.dashboard:" + e.getMessage());
        }
        return SUCCESS;
    }



    public Dashboard getItem() {
        return item;
    }

    public void setItem(Dashboard item) {
        this.item = item;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void setYearCount(int yearCount) {
        this.yearCount = yearCount;
    }

    public List<Item> getPies() {
        return pies;
    }

    public void setPies(List<Item> pies) {
        this.pies = pies;
    }

    public List<Item> getMonths() {
        return months;
    }

    public void setMonths(List<Item> months) {
        this.months = months;
    }

    public List<ItemDown> getItemDowns() {
        return itemDowns;
    }

    public void setItemDowns(List<ItemDown> itemDowns) {
        this.itemDowns = itemDowns;
    }


    public static class ItemDown{
        private String name;
        private List<Item> items;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }


    public static class Item{
        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
