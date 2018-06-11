package com.example.microservicebase.ws.contract;

import com.example.microservicebase.core.service.CustomerService;
import com.example.microservicebase.model.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(CustomerRestContract.CUSTOMER_DOMAIN)
public class CustomerRestController implements CustomerRestContract {

    @Autowired
    private CustomerService service;

    @Override
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id){
        Optional<CustomerEntity> optionalEntity = service.findOne(id);
        if(!optionalEntity.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalEntity.get());
    }

    @Override
    public ResponseEntity<?> save(@RequestBody CustomerEntity customerEntity){
        if(customerEntity.getId() == null)
            return new ResponseEntity(service.save(customerEntity), HttpStatus.ACCEPTED);

        return new ResponseEntity(service.save(customerEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Optional<CustomerEntity> optionalEntity = service.findOne(id);
        if(!optionalEntity.isPresent())
            return ResponseEntity.notFound().build();

        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
