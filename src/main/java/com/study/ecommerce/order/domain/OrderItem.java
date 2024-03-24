package com.study.ecommerce.order.domain;

import com.study.ecommerce.product.domain.ProductItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Setter
    private Order order;

    @ManyToOne
    @Setter
    private SellerOrder sellerOrder;

    @ManyToOne
    private ProductItem productItem;

    private String productName;

    private String optionName;

    private int quantity;

    private int price;

    public int getTotalPrice() {
        return price * quantity;
    }
}
