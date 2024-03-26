package com.study.ecommerce.order;

import com.study.ecommerce.auth.token.MemberDetails;
import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal MemberDetails member,
            @RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok().body(orderService.createOrder(request, member.getId()));
    }

    @PostMapping("/orders/{orderId}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.cancelOrder(orderId));
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> showOrderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.getOrderDetail(orderId));
    }

    @PutMapping("/orders/{orderId}/complete")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.completeOrder(orderId));
    }
}
