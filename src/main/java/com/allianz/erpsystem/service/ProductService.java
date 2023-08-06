package com.allianz.erpsystem.service;

import com.allianz.erpsystem.model.dto.UpdateProductDTO;
import com.allianz.erpsystem.model.entity.ProductEntity;
import com.allianz.erpsystem.repository.ProductRepository;
import com.allianz.erpsystem.model.dto.CreateProductDTO;
import com.allianz.erpsystem.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductEntity create(CreateProductDTO productDTO) {
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName(productDTO.getName());
        productEntity1.setIsTaxIncluded(productDTO.isTaxIncluded());
        if (!productEntity1.getIsTaxIncluded()) {
            productEntity1.setPrice(Helper.calculateNewPriceForTaxFreePrice(productDTO.getPrice()));
            productEntity1.setIsTaxIncluded(true);
        } else {
            productEntity1.setPrice(productDTO.getPrice());
        }
        productEntity1.setStock(productDTO.getStock());

        productRepository.save(productEntity1);
        return productEntity1;
    }

    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    public ProductEntity getByUUID(UUID uuid) {
        ProductEntity productEntity = productRepository.findProductEntityByUuid(uuid);
        return productEntity;
    }

    public ProductEntity updateByUUID(UUID uuid, UpdateProductDTO updateProductDTO) {
        ProductEntity productEntity1 = getByUUID(uuid);
        productEntity1.setStock(updateProductDTO.getStock());
        productEntity1.setPrice(updateProductDTO.getPrice());
        productEntity1.setIsTaxIncluded(updateProductDTO.isTaxIncluded());

        productRepository.save(productEntity1);

        return productEntity1;
    }

    public void deleteByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    public boolean updateStock(UUID uuid, Integer newStock) {
        ProductEntity product = getByUUID(uuid);
        if (product != null) {
            product.setStock(newStock);
            productRepository.save(product);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

    public boolean updatePrice(UUID uuid, BigDecimal newPrice) {
        ProductEntity product = getByUUID(uuid);
        if (product != null) {
            product.setPrice(newPrice);
            productRepository.save(product);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
