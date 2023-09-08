package com.example.moMagic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inbox")
public class Inbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "msisdn", length = 15, nullable = false)
    private String msisdn;

    @Column(name = "ins_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insDate;

    @Column(name = "sms_text", length = 160, nullable = false)
    private String smsText;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "CHAR DEFAULT 'N'")
    private char status;

    @Column(name = "pro_date")
    private Date proDate;

    @Override
    public String toString() {
        return "Inbox{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", insDate=" + insDate +
                ", smsText='" + smsText + '\'' +
                ", status=" + status +
                ", proDate=" + proDate +
                '}';
    }
}

