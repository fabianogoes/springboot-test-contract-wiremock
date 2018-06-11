package com.example.microservicebase.ws.contract;

import com.example.microservicebase.model.entity.CustomerEntity;
import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomerStub extends CustomerEntity {

    public byte[] buildStubOne() {
        return SerializationUtils.serialize(CustomerEntity.builder().id(1).name(UUID.randomUUID().toString()).build());
    }

    public byte[] buildStubList() {
        List<CustomerEntity> list = Arrays.asList(
                CustomerEntity.builder().id(1).name(UUID.randomUUID().toString()).build(),
                CustomerEntity.builder().id(2).name(UUID.randomUUID().toString()).build(),
                CustomerEntity.builder().id(3).name(UUID.randomUUID().toString()).build()
        );
        return SerializationUtils.serialize(list);
    }


}
