package com.allianz.erpsystem.service;

import com.allianz.erpsystem.model.dto.CreateBillDTO;
import com.allianz.erpsystem.model.dto.UpdateBillDTO;
import com.allianz.erpsystem.model.entity.BillEntity;
import com.allianz.erpsystem.model.entity.CustomerEntity;
import com.allianz.erpsystem.model.entity.OrderEntity;
import com.allianz.erpsystem.model.entity.ProductEntity;
import com.allianz.erpsystem.repository.BillRepository;
import com.allianz.erpsystem.repository.CustomerRepository;
import com.allianz.erpsystem.repository.OrderRepository;
import com.allianz.erpsystem.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;


    // Take CustomerUUID and OrderUUID with DTO
    public BillEntity create(CreateBillDTO createBillDTO) {
        BillEntity billEntity = new BillEntity();

        CustomerEntity customerEntity = customerService.getCustomerByUuid(createBillDTO.getCustomerUUID());
        billEntity.setCustomerEntity(customerEntity);

        OrderEntity orderEntity = orderService.getByUUID(createBillDTO.getOrderUUID());
        billEntity.setOrderEntity(orderEntity);

        // Update price fields
        calculatePrices(billEntity);

        billRepository.save(billEntity);

        customerEntity.getBillEntityList().add(billEntity);

        orderEntity.setBillEntity(billEntity);
        orderRepository.save(orderEntity);
        customerRepository.save(customerEntity);
        return billEntity;
    }

    public List<BillEntity> getAll() {
        return billRepository.findAll();
    }

    public BillEntity getByUUID(UUID uuid) {
        return billRepository.findBillEntityByUuid(uuid).orElse(null);
    }

    public void updateByUUID(UUID uuid, UpdateBillDTO updateBillDTO) {
        BillEntity billEntity = billRepository.findBillEntityByUuid(uuid).orElse(null);
        if (billEntity != null) {
            billEntity.setOrderEntity(orderService.getByUUID(updateBillDTO.getOrderUUID()));
            calculatePrices(billEntity);
            billRepository.save(billEntity);
        }
    }

    public void deleteByUUID(UUID uuid) {
        billRepository.deleteByUuid(uuid);
    }

    public void calculatePrices(BillEntity billEntity) {
        BigDecimal totalTaxFreePrice = BigDecimal.ZERO;
        BigDecimal totalTaxPrice = BigDecimal.ZERO;

        for (ProductEntity product : billEntity.getOrderEntity().getProductEntityList()) {
            totalTaxFreePrice = totalTaxFreePrice.add(Helper.calculateTaxFreeTotalPrice(product.getPrice()));
            totalTaxPrice = totalTaxPrice.add(Helper.calculateTaxPrice(product.getPrice()));
        }
        BigDecimal totalPrice = totalTaxFreePrice.add(totalTaxPrice);

        billEntity.setTotalPrice(totalPrice);
        billEntity.setTotalTaxPrice(totalTaxPrice);
        billEntity.setTotalTaxFreePrice(totalTaxFreePrice);

    }
}
