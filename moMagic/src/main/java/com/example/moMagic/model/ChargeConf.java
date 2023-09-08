package com.example.moMagic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "charge_conf")
public class ChargeConf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "charge_code", length = 20, nullable = false)
    private String chargeCode;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "price_with_vat", nullable = false, precision = 10, scale = 2)
    private float priceWithVat;

    @Column(name = "validity", nullable = false, columnDefinition = "int DEFAULT 0")
    private int validity;

    @Override
    public String toString() {
        return "ChargeConf{" +
                "id=" + id +
                ", chargeCode='" + chargeCode + '\'' +
                ", price=" + price +
                ", priceWithVat=" + priceWithVat +
                ", validity=" + validity +
                '}';
    }
}
