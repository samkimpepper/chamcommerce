package com.study.ecommerce.member;

import com.study.ecommerce.member.dto.DeliveryAddressCreateRequest;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;

    @Transactional
    public DeliveryAddressResponse createDeliveryAddress(Long memberId, DeliveryAddressCreateRequest request) {
        DeliveryAddress deliveryAddress = request.toEntity(memberId);

        if (deliveryAddress.isMain()) {
            deliveryAddressRepository.findAllByRecipientId(memberId).forEach(DeliveryAddress::unmarkMain);
        }

        deliveryAddressRepository.save(deliveryAddress);

        return DeliveryAddressResponse.of(deliveryAddress);
    }

    @Transactional(readOnly = true)
    public List<DeliveryAddressResponse> getDeliveryAddresses(Long memberId) {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByRecipientId(memberId);
        return DeliveryAddressResponse.listOf(deliveryAddresses);
    }
}
