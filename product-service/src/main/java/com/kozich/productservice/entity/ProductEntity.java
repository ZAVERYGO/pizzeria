package com.kozich.productservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product", schema = "app")
public class ProductEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "composition", nullable = false)
    private String composition;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne
    @JoinColumn(name="category_uuid")
    private CategoryEntity categoryId;

    @Column(name = "dt_Create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_Update", nullable = false)
    private LocalDateTime dtUpdate;

    public ProductEntity() {
    }

    public ProductEntity(UUID uuid, String name, String composition, Integer price, String currency, CategoryEntity categoryId, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.name = name;
        this.composition = composition;
        this.price = price;
        this.currency = currency;
        this.categoryId = categoryId;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProductEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public ProductEntity setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public ProductEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public ProductEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public String getComposition() {
        return composition;
    }

    public ProductEntity setComposition(String composition) {
        this.composition = composition;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductEntity setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public ProductEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
