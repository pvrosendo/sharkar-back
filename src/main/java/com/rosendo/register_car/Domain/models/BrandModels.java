package com.rosendo.register_car.Domain.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cars_brand_fipe")
public class BrandModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "BRAND_NAME")
    private String brandName;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BrandModels that = (BrandModels) o;
        return Objects.equals(brandId, that.brandId) && Objects.equals(brandName, that.brandName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(brandId);
        result = 31 * result + Objects.hashCode(brandName);
        return result;
    }
}
