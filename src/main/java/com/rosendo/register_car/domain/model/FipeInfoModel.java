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
public class FipeInfoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer vehicleType;

    @Id
    @Column(nullable = false)
    private Integer brandId;

    @Column(name = "BRAND_NAME", nullable = false)
    @JsonAlias({"brand"})
    private String brandName;

    @Column(name = "PRICE", nullable = false)
    @JsonAlias({"price"})
    private Integer price;

    @Column(name = "MODEL_ID", nullable = false)
    @JsonIgnore
    private Integer modelId;

    @Column(name = "MODEL_NAME", nullable = false)
    @JsonAlias({"model"})
    private String model;

    @Column(name = "MODEL_YEAR", nullable = false)
    @JsonAlias({"modelYear"})
    private String modelYear;

    @Column(name = "FUEL_TYPE", nullable = false)
    @JsonAlias({"fuel"})
    private String fuel;

    @Column(name = "REFERENCE MONTH", nullable = false)
    @JsonAlias({"referenceMonth"})
    private String referenceMonth;



}
