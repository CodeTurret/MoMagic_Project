package com.example.moMagic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "charge_success_log")
public class ChargeSuccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sms_id", nullable = false)
    private Long smsId;

    @Column(name = "msisdn", length = 15, nullable = false)
    private String msisdn;

    @Column(name = "keyword_id", nullable = false)
    private int keywordId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "amount_with_vat", nullable = false, precision = 10, scale = 2)
    private float amountWithVat;

    @Column(name = "validity", nullable = false)
    private int validity;

    @Column(name = "charge_id", nullable = false)
    private int chargeId;

    @Column(name = "ins_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insDate;

    @Column(name = "tid_ref", columnDefinition = "TEXT", nullable = false)
    private String tidRef;

    @Column(name = "response", columnDefinition = "TEXT", nullable = false)
    private String response;

    @ManyToOne
    @JoinColumn(name = "sms_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Inbox sms;

    @ManyToOne
    @JoinColumn(name = "charge_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ChargeConf charge;

    @ManyToOne
    @JoinColumn(name = "keyword_id", referencedColumnName = "id", insertable = false, updatable = false)
    private KeywordDetails keyword;

    @Override
    public String toString() {
        return "ChargeSuccessLog{" +
                "id=" + id +
                ", smsId=" + smsId +
                ", msisdn='" + msisdn + '\'' +
                ", keywordId=" + keywordId +
                ", amount=" + amount +
                ", amountWithVat=" + amountWithVat +
                ", validity=" + validity +
                ", chargeId=" + chargeId +
                ", insDate=" + insDate +
                ", tidRef='" + tidRef + '\'' +
                ", response='" + response + '\'' +
                ", sms=" + sms +
                ", charge=" + charge +
                ", keyword=" + keyword +
                '}';
    }
}

