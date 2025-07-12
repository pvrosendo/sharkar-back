package com.rosendo.sharkar.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "SK_CARS_MODELS_FIPE")
public class FipeCarModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MODEL_ID")
    @JsonAlias({ "modelId", "code" })
    private Integer modelId;

    @Column(name = "BRAND_ID")
    private Integer brandId;

    @Column(name = "MODEL_NAME")
    @JsonAlias({ "modelName", "name" })
    private String modelName;


    public FipeCarModels(Integer brandId, Integer modelId, String modelName) {
        this.brandId = brandId;
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public FipeCarModels() {}

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
