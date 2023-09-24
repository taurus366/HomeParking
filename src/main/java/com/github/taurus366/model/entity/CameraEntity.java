package com.github.taurus366.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "camera")
public class CameraEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String ip;

    @Column(nullable = false)
    private String port;

    @Column()
    private boolean status = false;


    public CameraEntity() {
    }

    public String name() {
        return name;
    }

    public CameraEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String ip() {
        return ip;
    }

    public CameraEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String port() {
        return port;
    }

    public CameraEntity setPort(String port) {
        this.port = port;
        return this;
    }

    public boolean status() {
        return status;
    }

    public CameraEntity setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
