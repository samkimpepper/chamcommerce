package com.study.ecommerce.order;

import com.study.ecommerce.product.domain.ProductItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Order order;

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
