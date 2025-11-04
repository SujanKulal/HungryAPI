package com.hungryapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {

    private Long customerId;
    private List<Long> dishIds;
}
