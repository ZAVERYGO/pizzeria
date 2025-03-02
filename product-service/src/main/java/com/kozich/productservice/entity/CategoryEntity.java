package com.kozich.productservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category", schema = "app")
public class CategoryEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy="categoryId")
    private List<ProductEntity> products;

    @Column(name = "dt_Create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_Update", nullable = false)
    private LocalDateTime dtUpdate;

    public CategoryEntity() {
    }

    public CategoryEntity(UUID uuid, String name, List<ProductEntity> products, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.name = name;
        this.products = products;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CategoryEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public CategoryEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public CategoryEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public CategoryEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }
}
