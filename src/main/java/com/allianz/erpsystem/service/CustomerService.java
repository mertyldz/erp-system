package com.allianz.erpsystem.service;

import com.allianz.erpsystem.model.dto.UpdateCustomerDTO;
import com.allianz.erpsystem.model.entity.CustomerEntity;
import com.allianz.erpsystem.model.entity.OrderEntity;
import com.allianz.erpsystem.repository.CustomerRepository;
import com.allianz.erpsystem.model.dto.CreateCustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerEntity create(CreateCustomerDTO createCustomerDTO) {
        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setName(createCustomerDTO.getName());
        customerEntity1.setSurname(createCustomerDTO.getSurname());
        customerEntity1.setBirthDate(createCustomerDTO.getBirthDate());
        customerEntity1.setTc(createCustomerDTO.getTc());
        customerEntity1.setAddress(createCustomerDTO.getAddress());
        customerEntity1.setPhoneNumber(createCustomerDTO.getPhoneNumber());
        customerEntity1.setBillEntityList(new ArrayList<>());
        customerEntity1.setOrderEntityList(new ArrayList<>());

        customerRepository.save(customerEntity1);

        return customerEntity1;
    }

    public void updateOrderEntityList(UUID uuid, OrderEntity orderEntity) {
        CustomerEntity customerEntity = getCustomerByUuid(uuid);
        if (customerEntity != null) {
            if (customerEntity.getOrderEntityList() != null) {
                customerEntity.getOrderEntityList().add(orderEntity);
            } else {
                List<OrderEntity> orderEntityList = new ArrayList<>();
                orderEntityList.add(orderEntity);
                customerEntity.setOrderEntityList(orderEntityList);
            }
            customerRepository.save(customerEntity);
        }
    }

    public void saveCustomer(CustomerEntity customer){
        customerRepository.save(customer);
    }

    public CustomerEntity getCustomerByUuid(UUID uuid) {
        return customerRepository.findByUuid(uuid);
    }


    public List<CustomerEntity> getAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity getByUUID(UUID uuid) {
        return customerRepository.findByUuid(uuid);
    }

    public CustomerEntity updateByUUID(UUID uuid, UpdateCustomerDTO updateCustomerDTO) {
        CustomerEntity customer = getByUUID(uuid);
        if (customer != null) {
            customer.setName(updateCustomerDTO.getName());
            customer.setSurname(updateCustomerDTO.getSurname());
            customer.setAddress(updateCustomerDTO.getAddress());
            customer.setPhoneNumber(updateCustomerDTO.getPhoneNumber());
            customerRepository.save(customer);
            return customer;
        } else {
            return null;
        }
    }

    public void deleteByUUID(UUID uuid) {
        customerRepository.deleteCustomerEntityByUuid(uuid);
    }
}
