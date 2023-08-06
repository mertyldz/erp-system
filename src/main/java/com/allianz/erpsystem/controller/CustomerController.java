package com.allianz.erpsystem.controller;

import com.allianz.erpsystem.model.dto.CreateCustomerDTO;
import com.allianz.erpsystem.model.dto.UpdateCustomerDTO;
import com.allianz.erpsystem.model.entity.CustomerEntity;
import com.allianz.erpsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("add")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        CustomerEntity customerEntity1 = customerService.create(createCustomerDTO);
        return new ResponseEntity<>(customerEntity1, HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("get-by-uuid/{uuid}")
    public ResponseEntity<CustomerEntity> getCustomerByUUID(@PathVariable UUID uuid) {
        CustomerEntity customerEntity = customerService.getByUUID(uuid);
        if (customerEntity != null) {
            return new ResponseEntity<>(customerEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-by-uuid/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerByUUID(@PathVariable UUID uuid) {
        customerService.deleteByUUID(uuid);
    }

    @PutMapping("update-by-uuid/{uuid}")
    public ResponseEntity<CustomerEntity> updateCustomerByUUID(@PathVariable UUID uuid,
                                                               @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        CustomerEntity customer = customerService.updateByUUID(uuid, updateCustomerDTO);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
