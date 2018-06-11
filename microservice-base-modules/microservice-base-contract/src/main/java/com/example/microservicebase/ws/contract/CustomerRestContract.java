package com.example.microservicebase.ws.contract;

import com.example.microservicebase.model.entity.CustomerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CustomerRestContract {

    String CUSTOMER_DOMAIN = "/api/v1/customers";
    String CUSTOMER_WITH_ID = "{id}";

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping(CUSTOMER_WITH_ID)
    ResponseEntity<?> getOne(@PathVariable("id") Integer id);

    @PostMapping
    ResponseEntity<?> save(@RequestBody CustomerEntity customerEntity);

    @DeleteMapping(CUSTOMER_WITH_ID)
    ResponseEntity<?> delete(@PathVariable("id") Integer id);

}
