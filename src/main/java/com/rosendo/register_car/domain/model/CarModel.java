package com.rosendo.register_car.domain.model;

import com.rosendo.register_car.domain.enums.CarBrandsEnum;
import com.rosendo.register_car.domain.enums.CarTypeEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name="tb_cars")
public class CarModel implements Serializable {

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

    @Column(name = "ANO", nullable = false)
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


    public void setBrand(CarBrandsEnum brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }
}
