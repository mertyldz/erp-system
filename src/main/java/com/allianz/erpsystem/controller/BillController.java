package com.allianz.erpsystem.controller;

import com.allianz.erpsystem.model.dto.CreateBillDTO;
import com.allianz.erpsystem.model.dto.CreateOrderDTO;
import com.allianz.erpsystem.model.dto.UpdateBillDTO;
import com.allianz.erpsystem.model.entity.BillEntity;
import com.allianz.erpsystem.service.BillService;
import com.allianz.erpsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bill")
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    OrderService orderService;

    @PostMapping("add")
    public ResponseEntity<BillEntity> createBill(@RequestBody CreateBillDTO createBillDTO) {
        return new ResponseEntity<>(billService.create(createBillDTO), HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<BillEntity>> getAllBills() {
        return new ResponseEntity<>(billService.getAll(), HttpStatus.OK);
    }

    @GetMapping("get-by-uuid/{uuid}")
    public ResponseEntity<BillEntity> getBillByUUID(@PathVariable UUID uuid) {
        if (billService.getByUUID(uuid) != null) {
            return new ResponseEntity<>(billService.getByUUID(uuid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-by-uuid/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBillByUUID(@PathVariable UUID uuid) {
        billService.deleteByUUID(uuid);
    }

    @PutMapping("update-by-uuid/{uuid}")
    public void updateBillByUUID(@PathVariable UUID uuid, @RequestBody UpdateBillDTO updateBillDTO) {
        BillEntity billEntity = billService.getByUUID(uuid);
        if (billEntity != null) {
            billService.updateByUUID(uuid, updateBillDTO);
        }
    }

}
