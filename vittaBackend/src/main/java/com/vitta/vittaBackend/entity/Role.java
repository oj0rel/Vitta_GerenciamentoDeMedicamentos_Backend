package com.vitta.vittaBackend.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.vitta.vittaBackend.enums.security.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName name;

    /**
     * Construtor exigido pelo JPA.
     */
    public Role() {
    }

    /**
     * Construtor para facilitar a criação de novas roles no código.
     */
    public Role(RoleName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
