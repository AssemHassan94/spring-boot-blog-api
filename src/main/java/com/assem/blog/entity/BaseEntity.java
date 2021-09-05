package com.assem.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime updatedAt;
}
