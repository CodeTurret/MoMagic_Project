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
@Table(name = "outbox")
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sms_id", nullable = false)
    private Long smsId;

    @Column(name = "msisdn", length = 15, nullable = false)
    private String msisdn;

    @Column(name = "ins_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insDate;

    @Column(name = "sms_text", length = 160, nullable = false)
    private String smsText;

    @Column(name = "reply_addr", length = 11, nullable = false)
    private String replyAddress;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "CHAR DEFAULT 'N'")
    private char status;

    @Column(name = "send_date")
    private Timestamp sendDate;

    @ManyToOne
    @JoinColumn(name = "sms_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Inbox sms;

    @Override
    public String toString() {
        return "Outbox{" +
                "id=" + id +
                ", smsId=" + smsId +
                ", msisdn='" + msisdn + '\'' +
                ", insDate=" + insDate +
                ", smsText='" + smsText + '\'' +
                ", replyAddress='" + replyAddress + '\'' +
                ", status=" + status +
                ", sendDate=" + sendDate +
                ", sms=" + sms +
                '}';
    }
}

