package com.allianz.erpsystem.service;

import com.allianz.erpsystem.exceptions.StockException;
import com.allianz.erpsystem.model.dto.CreateBillDTO;
import com.allianz.erpsystem.model.dto.UpdateOrderDTO;
import com.allianz.erpsystem.model.entity.OrderEntity;
import com.allianz.erpsystem.model.entity.ProductEntity;
import com.allianz.erpsystem.repository.BillRepository;
import com.allianz.erpsystem.repository.OrderRepository;
import com.allianz.erpsystem.model.dto.CreateOrderDTO;
import com.allianz.erpsystem.model.enums.OrderStatus;
import com.allianz.erpsystem.utils.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    BillService billService;

    // While creating OrderEntity, CreateOrderDTO requires productUUIDList and customerUUID!
    @Transactional
    @Modifying
    public OrderEntity create(CreateOrderDTO createOrderDTO) {
        OrderEntity orderEntity1 = new OrderEntity();

        orderEntity1.setProductEntityList(new ArrayList<>());

        orderEntity1.setTotalPriceTaxIncluded(BigDecimal.ZERO);

        orderEntity1.setOrderStatus(OrderStatus.PENDING.getName());

        for (UUID productUUID : createOrderDTO.getProductEntityUUIDList()) {
            orderEntity1.getProductEntityList().add(productService.getByUUID(productUUID));
            productService.getByUUID(productUUID).getOrderEntity().add(orderEntity1);
        }

        for (ProductEntity p : orderEntity1.getProductEntityList()) {
            if (p.getIsTaxIncluded()) {
                orderEntity1.setTotalPriceTaxIncluded(orderEntity1.getTotalPriceTaxIncluded().add(p.getPrice()));
            } else {
                BigDecimal newPrice = p.getPrice().add(
                        p.getPrice().multiply(Helper.KDV).divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP)
                );
                orderEntity1.setTotalPriceTaxIncluded(orderEntity1.getTotalPriceTaxIncluded().add(newPrice));
            }
        }
        // Set customer to order
        orderEntity1.setCustomerEntity(customerService.getCustomerByUuid(createOrderDTO.getCustomerEntityUUID()));
        // Set order to customer
        customerService.getCustomerByUuid(createOrderDTO.getCustomerEntityUUID()).
                getOrderEntityList().add(orderEntity1);

        orderRepository.save(orderEntity1);
        customerService.updateOrderEntityList(createOrderDTO.getCustomerEntityUUID(), orderEntity1);
        return orderEntity1;
    }

    public List<OrderEntity> getAll() {
        return orderRepository.findAll();
    }

    public OrderEntity getByUUID(UUID uuid) {
        return orderRepository.findByUuid(uuid).orElse(null);
    }

    public OrderEntity updateByUUID(UUID uuid, UpdateOrderDTO updateOrderDTO) {
        OrderEntity orderEntity1 = getByUUID(uuid);
        if (orderEntity1 != null) {

            List<ProductEntity> productEntityList =
                    updateOrderDTO.getProductEntityIdList().stream().
                            map(productId -> productService.getByUUID(productId)).toList();

            orderEntity1.setProductEntityList(productEntityList);

            orderEntity1.setTotalPriceTaxIncluded(BigDecimal.ZERO);

            for (ProductEntity p : orderEntity1.getProductEntityList()) {
                if (p.getIsTaxIncluded()) {
                    orderEntity1.setTotalPriceTaxIncluded(orderEntity1.getTotalPriceTaxIncluded().add(p.getPrice()));
                } else {
                    BigDecimal newPrice = p.getPrice().add(
                            p.getPrice().multiply(Helper.KDV).divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP)
                    );

                    orderEntity1.setTotalPriceTaxIncluded(orderEntity1.getTotalPriceTaxIncluded().add(newPrice));
                }
            }

            return orderRepository.save(orderEntity1);
        } else {
            return null;
        }
    }

    public void deleteByUUID(UUID uuid) {
        orderRepository.deleteByUuid(uuid);
    }

    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    // Bill will be created after order approved.
    public void approveOrder(UUID uuid) throws StockException {
        OrderEntity orderEntity = orderRepository.findByUuid(uuid).orElse(null);
        if (orderEntity != null) {
            for (ProductEntity product : orderEntity.getProductEntityList()) {
                if (product.getStock() < 1) {
                    orderEntity.setOrderStatus(OrderStatus.DENIED.getName());
                    throw new StockException();
                } else {
                    orderEntity.setOrderStatus(OrderStatus.APPROVED.getName());
                }
            }
        }

        if (orderEntity.getOrderStatus().equals(OrderStatus.APPROVED.getName())) {
            // decrease all products stock.
            for (ProductEntity product : orderEntity.getProductEntityList()) {
                product.setStock(product.getStock() - 1);
            }

            // Generate bill.
            CreateBillDTO createBillDTO = new CreateBillDTO(); // Requires order uuid & customer uuid.
            createBillDTO.setOrderUUID(uuid);
            createBillDTO.setCustomerUUID(orderEntity.getCustomerEntity().getUuid());

            orderRepository.save(orderEntity);
            billService.create(createBillDTO);

        }
    }
}
