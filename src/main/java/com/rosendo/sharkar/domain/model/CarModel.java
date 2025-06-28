package com.rosendo.sharkar.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name="DC_CARS_USERS")
public class CarModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    @JsonAlias({"userId"})
    private Long userId;

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

    @Column(name = "REFERENCE_MONTH", nullable = false)
    @JsonAlias({"referenceMonth"})
    private String referenceMonth;


    public CarModel(Long userId, String model, String brandName, String modelYear, String fuel, String price, String referenceMonth) {
        this.userId = userId;
        this.model = model;
        this.brandName = brandName;
        this.modelYear = modelYear;
        this.fuel = fuel;
        this.price = price;
        this.referenceMonth = referenceMonth;
    }

    public CarModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CarModel carModel = (CarModel) o;
        return Objects.equals(id, carModel.id)
                && Objects.equals(userId, carModel.userId)
                && Objects.equals(model, carModel.model)
                && Objects.equals(brandName, carModel.brandName)
                && Objects.equals(modelYear, carModel.modelYear)
                && Objects.equals(fuel, carModel.fuel)
                && Objects.equals(price, carModel.price)
                && Objects.equals(referenceMonth, carModel.referenceMonth);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(model);
        result = 31 * result + Objects.hashCode(brandName);
        result = 31 * result + Objects.hashCode(modelYear);
        result = 31 * result + Objects.hashCode(fuel);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(referenceMonth);
        return result;
    }
}
