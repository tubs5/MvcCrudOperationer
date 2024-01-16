package com.example.mvccrudoperationer.model;

import java.sql.Date;
import java.util.HashMap;

/**
 * @author Tobias Heidlund
 */
public class InvoiceEntry {
    private boolean hasId = false;
    private int id;
    private String title;
    private Date date;
    private String description;
    private Category category;
    private double price;
    private final int owner;

    private InvoiceEntry(int id, String title, Date date, String description, Category category, double price, int owner) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.category = category;
        this.price = price;
        this.owner = owner;
        this.hasId = true;
    }
    private InvoiceEntry(String title, Date date, String description, Category category, double price, int owner) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.category = category;
        this.price = price;
        this.owner = owner;
        this.hasId = false;
    }

    public int getId() {
        return id;
    }
    public int getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return title + "\t" +
                date + "\t" +
                description + "\t" +
                category.label +"\t"+
                price;
    }

    public HashMap<String,Object> getMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("date",date.toString());
        map.put("description",description);
        map.put("category",category.label);
        map.put("price",price);
        return map;

    }

    public static class InvoiceEntryBuilder{
        private boolean hasId = false;
        private String title;
        private Date date;
        private String description;
        private Category category;
        private double price;
        private int owner;
        private int id;

        public InvoiceEntryBuilder(String title, Date date, String description, Category category, double price, int owner) {
            this.title = title;
            this.date = date;
            this.description = description;
            this.category = category;
            this.price = price;
            this.owner = owner;
        }

        public InvoiceEntryBuilder(int id,String title, Date date, String description, Category category, double price, int owner) {
            this.title = title;
            this.date = date;
            this.description = description;
            this.category = category;
            this.price = price;
            this.owner = owner;
            this.id = id;
            hasId = true;
        }
        public InvoiceEntryBuilder() {

        }

        public InvoiceEntryBuilder setId(int id) {
            this.id = id;
            hasId = true;
            return this;
        }

        public InvoiceEntryBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public InvoiceEntryBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public InvoiceEntryBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public InvoiceEntryBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public InvoiceEntryBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public InvoiceEntryBuilder setOwner(int owner) {
            this.owner = owner;
            return this;
        }

        public InvoiceEntry build(){
            if(hasId){
                return new InvoiceEntry(id,title,date,description,category,price,owner);
            } else return  new InvoiceEntry(title,date,description,category,price,owner);
        }
    }



}

