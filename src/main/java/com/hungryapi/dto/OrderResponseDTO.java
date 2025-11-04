package com.hungryapi.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private List<String> dishNames;
    private double totalAmount;
    private LocalDateTime timestamp;
}
