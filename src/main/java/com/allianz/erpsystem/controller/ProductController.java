package com.allianz.erpsystem.controller;

import com.allianz.erpsystem.model.dto.UpdateProductDTO;
import com.allianz.erpsystem.model.entity.ProductEntity;
import com.allianz.erpsystem.model.dto.CreateProductDTO;
import com.allianz.erpsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("add")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody CreateProductDTO productDTO) {
        ProductEntity productEntity1 = productService.create(productDTO);
        return new ResponseEntity<>(productEntity1, HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("get-by-uuid/{uuid}")
    public ResponseEntity<ProductEntity> getByUUID(@PathVariable UUID uuid) {
        ProductEntity productEntity = productService.getByUUID(uuid);
        if (productEntity != null) {
            return new ResponseEntity<>(productEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update-by-uuid/{uuid}")
    public ResponseEntity<ProductEntity> updateProductByUUID(@PathVariable UUID uuid,
                                                             @RequestBody UpdateProductDTO updateProductDTO) {
        ProductEntity productEntity1 = productService.updateByUUID(uuid, updateProductDTO);
        return new ResponseEntity<>(productEntity1, HttpStatus.OK);
    }

    @PutMapping("update-price-by-uuid/{uuid}/price/{newPrice}")
    public ResponseEntity<Boolean> updateProductPriceByUUID(@PathVariable UUID uuid,
                                                            @PathVariable BigDecimal newPrice) {
        boolean booleanResponse = productService.updatePrice(uuid, newPrice);
        if (booleanResponse) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update-price-by-uuid/{uuid}/stock/{newStock}")
    public ResponseEntity<Boolean> updateProductStockByUUID(@PathVariable UUID uuid,
                                                            @PathVariable Integer newStock) {
        boolean booleanResponse = productService.updateStock(uuid, newStock);

        if (booleanResponse) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-by-uuid/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductByUUID(@PathVariable UUID uuid) {
        productService.deleteByUUID(uuid);
    }


}
