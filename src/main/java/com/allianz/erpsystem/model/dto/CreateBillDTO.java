package com.allianz.erpsystem.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateBillDTO {
    private UUID customerUUID;
    private UUID orderUUID;
}
