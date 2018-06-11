package com.example.microservicebase.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerEntity implements Serializable {
    static final long serialVersionUID = -7034897190745766939L;

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
