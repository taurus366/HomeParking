package com.parking.www.model.entity;

import com.parking.www.model.RoleEnum;
import jakarta.persistence.*;

@Entity(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Long getId() {
        return id;
    }

    public RoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public RoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
