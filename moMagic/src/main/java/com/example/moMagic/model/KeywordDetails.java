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
@Table(name = "keyword_details")
public class KeywordDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "keyword", length = 20, nullable = false, columnDefinition = "CHARACTER SET latin1 COLLATE latin1_swedish_ci")
    private String keyword;

    @Column(name = "game_name", length = 200, nullable = false)
    private String gameName;

    @Column(name = "sms_spliter", length = 1, nullable = false)
    private String smsSplitter;

    @Column(name = "unlockurl", length = 1000, nullable = false)
    private String unlockUrl;

    @Column(name = "unlockurl_response_splitter", length = 1, nullable = false)
    private String unlockUrlResponseSplitter;

    @Column(name = "unlock_sms", length = 160, nullable = false)
    private String unlockSms;

    @Column(name = "chargeurl", length = 1000)
    private String chargeUrl;

    @Column(name = "ins_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insDate;

    @Override
    public String toString() {
        return "KeywordDetails{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", gameName='" + gameName + '\'' +
                ", smsSplitter='" + smsSplitter + '\'' +
                ", unlockUrl='" + unlockUrl + '\'' +
                ", unlockUrlResponseSplitter='" + unlockUrlResponseSplitter + '\'' +
                ", unlockSms='" + unlockSms + '\'' +
                ", chargeUrl='" + chargeUrl + '\'' +
                ", insDate=" + insDate +
                '}';
    }
}

