package com.rosendo.dream_car.domain.enums;

import java.util.List;

public enum CarBrandsEnum {
    Audi(6),
    BMW(7),
    Citroen(13),
    Dodge(17),
    Ferrari(20),
    Fiat(21),
    Chevrolet(23),
    Honda(25),
    Hyundai(26),
    Jaguar(28),
    Jeep(29),
    KiaMotors(31),
    LandRover(33),
    Lexus(34),
    MercedesBenz(39),
    Mitsubishi(41),
    Nissan(43),
    Peugeot(44),
    Porsche(47),
    Renault(48),
    Subaru(54),
    Toyota(56),
    Volvo(58),
    Volkswagen(59),
    Lamborghini(171),
    JAC(177),
    RAM(185),
    Mclaren(211),
    BYD(238),
    GWM(240);

    private final Integer carBrandId;

    CarBrandsEnum(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    private final List<Integer> carsBrandList = List.of(
            6, 7, 13, 17, 20, 21, 23, 25, 26, 28, 29, 31, 33, 34, 39, 41, 43, 44, 47, 48, 54,
            56, 58, 59, 171, 177, 185, 211, 238, 240);

    public List<Integer> getCarsBrandList() {
        return carsBrandList;
    }

}
