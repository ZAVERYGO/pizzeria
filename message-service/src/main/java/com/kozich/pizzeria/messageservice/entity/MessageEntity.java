package com.kozich.pizzeria.messageservice.entity;

import com.kozich.projectrepository.core.enums.MessageStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message", schema = "app")
public class MessageEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "email_to")
    private String toEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    public MessageEntity() {
    }

    public MessageEntity(UUID uuid, String toEmail, String subject, String text,
                         MessageStatus status, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.toEmail = toEmail;
        this.subject = subject;
        this.text = text;
        this.status = status;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public MessageEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getToEmail() {
        return toEmail;
    }

    public MessageEntity setToEmail(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MessageEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageEntity setText(String text) {
        this.text = text;
        return this;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public MessageEntity setStatus(MessageStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public MessageEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public MessageEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }
}
