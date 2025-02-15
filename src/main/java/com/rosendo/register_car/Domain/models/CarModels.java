package com.rosendo.register_car.Domain.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name="TB_CARS")
public class CarModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MARCA")
    @Enumerated(EnumType.STRING)
    private CarBrandsEnum brand;

    @Column(name = "Modelo")
    private String model;

    @Column(name = "ANO", length = 4)
    private String year;

    @Column(name = "PRECO")
    private Double price;

    @Column(name = "DATA_REGISTRO")
    private String registerDate;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CarModels carModels = (CarModels) o;
        return Objects.equals(id, carModels.id) && brand == carModels.brand && Objects.equals(year, carModels.year) && Objects.equals(price, carModels.price) && Objects.equals(registerDate, carModels.registerDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(brand);
        result = 31 * result + Objects.hashCode(year);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(registerDate);
        return result;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
