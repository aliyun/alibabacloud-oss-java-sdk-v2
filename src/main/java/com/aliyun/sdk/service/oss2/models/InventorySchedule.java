package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * Contains the frequency that inventory lists are exported
 */
 @JacksonXmlRootElement(localName = "InventorySchedule")
public final class InventorySchedule {  
    @JacksonXmlProperty(localName = "Frequency")
    private String frequency;
 
    @JacksonXmlProperty(localName = "DayOfMonth")
    private Integer dayOfMonth;
 
    @JacksonXmlProperty(localName = "AutoDelete")
    private Boolean autoDelete;

    public InventorySchedule() {}

    private InventorySchedule(Builder builder) { 
        this.frequency = builder.frequency; 
        this.dayOfMonth = builder.dayOfMonth; 
        this.autoDelete = builder.autoDelete; 
    }

    /**
    * The frequency at which the inventory list is exported. Valid values: Daily, Weekly, Monthly, Once.
    */
    public String frequency() {
        return this.frequency;
    }

    /**
    * The day of the month on which the inventory list is exported each month. Valid only when Frequency is set to Monthly. Valid values: 1 to 31.
    */
    public Integer dayOfMonth() {
        return this.dayOfMonth;
    }

    /**
    * Specifies whether to automatically delete the Once inventory configuration after the inventory list is exported. Valid only when Frequency is set to Once.
    */
    public Boolean autoDelete() {
        return this.autoDelete;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private String frequency;
        private Integer dayOfMonth;
        private Boolean autoDelete;
        
        /**
        * The frequency at which the inventory list is exported. Valid values: Daily, Weekly, Monthly, Once.
        */
        public Builder frequency(String value) {
            requireNonNull(value);
            this.frequency = value;
            return this;        
        }
        
        /**
        * The day of the month on which the inventory list is exported each month. Valid only when Frequency is set to Monthly. Valid values: 1 to 31.
        */
        public Builder dayOfMonth(Integer value) {
            requireNonNull(value);
            this.dayOfMonth = value;
            return this;        
        }
        
        /**
        * Specifies whether to automatically delete the Once inventory configuration after the inventory list is exported. Valid only when Frequency is set to Once.
        */
        public Builder autoDelete(Boolean value) {
            this.autoDelete = value;
            return this;        
        }
        

        private Builder() {
            super();
        }

        private Builder(InventorySchedule from) { 
            this.frequency = from.frequency; 
            this.dayOfMonth = from.dayOfMonth; 
            this.autoDelete = from.autoDelete; 
        }

        public InventorySchedule build() {
            return new InventorySchedule(this);
        }
    }
}
