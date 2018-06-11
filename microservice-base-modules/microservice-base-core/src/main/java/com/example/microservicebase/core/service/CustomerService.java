package com.example.microservicebase.core.service;

import com.example.microservicebase.model.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerEntity> findAll();
    Optional<CustomerEntity> findOne(Integer id);
    CustomerEntity save(CustomerEntity customerEntity);
    void delete(Integer id);

}
