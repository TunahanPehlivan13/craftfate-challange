package com.craftgate.order.entitiy;

import com.craftgate.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long restaurantGroupId;

    private Long restaurantId;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orders")
    @Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
    private List<OrderItem> orderItems;
}
