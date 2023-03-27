package com.craftgate.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(columnList = "menu_item_id,restaurant_id", unique = true)
})
public class RestaurantSpecificDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal price;

    private Boolean available;

    @ManyToOne
    private MenuItem menuItem;

    @ManyToOne
    private Restaurant restaurant;
}
