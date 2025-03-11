package com.rosendo.register_car.Domain.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name="TB_CARS")
public class CarModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MARCA", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private CarBrandsEnum brand;

    @Column(name = "Modelo", nullable = false)
    private String model;

    @Column(name = "ANO", nullable = false) //TODO: change for number
    private Integer year;

    @Column(name = "PRECO", nullable = false)
    private Double price;

    @Column(name = "DATA_REGISTRO", nullable = false)
    private String registerDate;

    @Column(name = "CILINDRADA", nullable = false)
    private String displacement;

    @Column(name = "CAMBIO", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarTypeEnum carType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarBrandsEnum getBrand() {
        return brand;
    }

    public void setBrand(CarBrandsEnum brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }
}
