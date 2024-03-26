package com.study.ecommerce.delivery;

import com.study.ecommerce.auth.token.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PutMapping("/deliveries/{deliveryId}/start")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<Void> start(@PathVariable("deliveryId") Long deliveryId) {
        deliveryService.start(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deliveries/{deliveryId}/deliver")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<Void> deliver(@PathVariable("deliveryId") Long deliveryId) {
        deliveryService.deliver(deliveryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deliveries/all")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<List<DeliveryResponse>> showAll(@AuthenticationPrincipal MemberDetails member) {
        return ResponseEntity.ok(deliveryService.getAllDelivery(member.getId()));
    }
}
