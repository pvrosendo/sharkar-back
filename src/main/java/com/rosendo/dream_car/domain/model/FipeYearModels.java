package com.rosendo.dream_car.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "DC_CARS_YEARS_FIPE")
public class FipeYearModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MODEL_ID")
    @JsonIgnore
    private Integer modelId;

    @Column(name = "MODEL_NAME")
    @JsonIgnore
    private String modelName;

    @Column(name = "YEAR_ID")
    @JsonAlias({"code"})
    private String yearId;

    @Column(name = "YEAR_VALUE")
    @JsonAlias({"name"})
    private String yearValue;

    public FipeYearModels(Integer modelId, String modelName, String yearId, String yearValue) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.yearId = yearId;
        this.yearValue = yearValue;
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

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getYearValue() {
        return yearValue;
    }

    public void setYearValue(String yearValue) {
        this.yearValue = yearValue;
    }

}
