package com.github.taurus366.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.taurus366.model.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column()
    private String username;
    @Column()
    private String name;
    @JsonIgnore
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleEnum> roleEnums;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;

    public UserEntity() {

    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.hashedPassword = passwordEncoder.encode(password);
        return this.hashedPassword;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public UserEntity setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public Set<RoleEnum> getRoleEnums() {
        return roleEnums;
    }

    public UserEntity setRoleEnums(Set<RoleEnum> roleEnums) {
        this.roleEnums = roleEnums;
        return this;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public UserEntity setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }


}
