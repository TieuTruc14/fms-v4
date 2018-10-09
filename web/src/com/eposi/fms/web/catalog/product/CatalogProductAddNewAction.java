package com.eposi.fms.web.catalog.product;

import com.eposi.fms.common.web.AbstractAction;
import com.eposi.fms.model.ProductType;


public class CatalogProductAddNewAction extends AbstractAction {
    private static final long serialVersionUID = -191060783291536947L;

    private ProductType item;

    public String execute() {

        return SUCCESS;
    }

    public ProductType getItem() {
        return item;
    }

    public void setItem(ProductType item) {
        this.item = item;
    }
}
