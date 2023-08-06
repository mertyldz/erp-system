package com.allianz.erpsystem.model.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateProductDTO {
    private String name;
    private BigDecimal price;
    private boolean isTaxIncluded;
    private int stock;
}
