package com.craftgate.order.repository;

import com.craftgate.order.entitiy.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
