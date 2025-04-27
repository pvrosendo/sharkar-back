package com.rosendo.register_car.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "car_info_fipe")
@JsonPropertyOrder({"brand, model, modelYear, fuel, price, referenceMonth"})
public class FipeInfoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MODEL_NAME", nullable = false)
    @JsonAlias({"model"})
    private String model;

    @Column(name = "BRAND_NAME", nullable = false)
    @JsonAlias({"brand"})
    private String brandName;

    @Column(name = "MODEL_YEAR", nullable = false)
    @JsonAlias({"modelYear"})
    private String modelYear;

    @Column(name = "FUEL_TYPE", nullable = false)
    @JsonAlias({"fuel"})
    private String fuel;

    @Column(name = "PRICE", nullable = false)
    @JsonAlias({"price"})
    private String price;

    @Column(name = "REFERENCE MONTH", nullable = false)
    @JsonAlias({"referenceMonth"})
    private String referenceMonth;

    @Column(name = "MODEL_ID", nullable = false)
    @JsonIgnore
    private Integer modelId;

    @Column(nullable = false)
    @JsonIgnore
    private Integer brandId;

    @JsonIgnore
    private Integer vehicleType;

    @JsonIgnore
    private String codeFipe;

    @JsonIgnore
    private Character fuelAcronym;


    public Character getFuelAcronym() {
        return fuelAcronym;
    }

    public void setFuelAcronym(Character fuelAcronym) {
        this.fuelAcronym = fuelAcronym;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCodeFipe() {
        return codeFipe;
    }

    public void setCodeFipe(String codeFipe) {
        this.codeFipe = codeFipe;
    }
}
