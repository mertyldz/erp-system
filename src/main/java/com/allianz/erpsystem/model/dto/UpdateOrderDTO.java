package com.allianz.erpsystem.model.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateOrderDTO {
    private List<UUID> productEntityIdList;

}
