package com.hexad.librarymanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    @Version
    private Long version;
}
