package com.study.ecommerce.delivery;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.delivery.event.DeliveredEvent;
import com.study.ecommerce.delivery.event.DeliveryStartedEvent;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.SellerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Delivery register(Order order, SellerOrder sellerOrder) {
        Delivery delivery = Delivery.of(order, sellerOrder, "trackingNumber");
        return deliveryRepository.save(delivery);
    }

    @Transactional
    public void start(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deliveryId"));

        delivery.start();

        eventPublisher.publishEvent(new DeliveryStartedEvent(delivery));
    }

    @Transactional
    public void deliver(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deliveryId"));

        delivery.deliver();

        eventPublisher.publishEvent(new DeliveredEvent(delivery));
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> getAllDelivery(Long deliveryWorkerId) {
        return deliveryRepository.findAllByDeliveryWorkerId(deliveryWorkerId).stream()
                .map(DeliveryResponse::of)
                .toList();
    }
}
