package com.example.moMagic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_conf")
public class AppConf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "app_name", length = 50)
    private String appName;

    @Column(name = "number_of_thread", nullable = false)
    private int numberOfThread;

    @Column(name = "number_of_row")
    private Integer numberOfRows;

    @Column(name = "status", nullable = false, columnDefinition = "int DEFAULT 0")
    private int status;

    @Column(name = "last_start_time", nullable = false)
    private Date lastStartTime;

    @Column(name = "last_stop_time", nullable = false)
    private Date lastStopTime;

    @Column(name = "stop_by", length = 20, nullable = false)
    private String stopBy;

    @Column(name = "data_reload", nullable = false, columnDefinition = "int DEFAULT 0")
    private int dataReload;

    @Column(name = "last_reload_time", nullable = false)
    private Date lastReloadTime;

    @Override
    public String toString() {
        return "AppConf{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", numberOfThread=" + numberOfThread +
                ", numberOfRows=" + numberOfRows +
                ", status=" + status +
                ", lastStartTime=" + lastStartTime +
                ", lastStopTime=" + lastStopTime +
                ", stopBy='" + stopBy + '\'' +
                ", dataReload=" + dataReload +
                ", lastReloadTime=" + lastReloadTime +
                '}';
    }
}
