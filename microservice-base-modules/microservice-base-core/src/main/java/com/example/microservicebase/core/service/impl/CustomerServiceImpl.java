package com.example.microservicebase.core.service.impl;

import com.example.microservicebase.core.repository.CustomerRepository;
import com.example.microservicebase.core.service.CustomerService;
import com.example.microservicebase.model.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<CustomerEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CustomerEntity> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        return repository.save(customerEntity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
