package com.rosendo.register_car.Domain.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cars_model_fipe")
public class CarModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "MODEL_ID")
    private Long modelId;

    @Column(name = "MODEL_NAME")
    private String modelName;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CarModels carModels = (CarModels) o;
        return Objects.equals(brandId, carModels.brandId) && Objects.equals(modelId, carModels.modelId) && Objects.equals(modelName, carModels.modelName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(brandId);
        result = 31 * result + Objects.hashCode(modelId);
        result = 31 * result + Objects.hashCode(modelName);
        return result;
    }
}
