package com.study.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {
    List<OrderOptionGroupRequest> orderOptionGroups;
    private String address;
}
