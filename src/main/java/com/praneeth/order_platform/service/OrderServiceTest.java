package com.praneeth.order_platform.service;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.enums.OrderStatus;
import com.praneeth.order_platform.exception.OrderNotFoundException;
import com.praneeth.order_platform.kafka.OrderProducer;
import com.praneeth.order_platform.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProducer orderProducer;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {

        order = Order.builder()
                .id(1L)
                .customerId(1001L)
                .productName("Laptop")
                .amount(75000.0)
                .status(OrderStatus.CREATED)
                .build();
    }

    @Test
    void shouldCreateOrder() {

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order saved = orderService.createOrder(order);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getProductName());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProducer, times(1)).publishOrder(any(Order.class));
    }

    @Test
    void shouldReturnAllOrders() {

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals("Laptop", orders.get(0).getProductName());

        verify(orderRepository).findAll();
    }

    @Test
    void shouldUpdateStatus() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        Order updated =
                orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);

        assertEquals(OrderStatus.SHIPPED, updated.getStatus());

        verify(orderRepository).save(any(Order.class));
        verify(orderProducer).publishOrder(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {

        when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.updateOrderStatus(
                        99L,
                        OrderStatus.SHIPPED
                )
        );
    }

    @Test
    void shouldSearchByProduct() {

        when(orderRepository.findByProductNameContainingIgnoreCase("Lap"))
                .thenReturn(List.of(order));

        List<Order> orders =
                orderService.searchByProduct("Lap");

        assertEquals(1, orders.size());

        verify(orderRepository)
                .findByProductNameContainingIgnoreCase("Lap");
    }

    @Test
    void shouldSearchByCustomer() {

        when(orderRepository.findByCustomerId(1001L))
                .thenReturn(List.of(order));

        List<Order> orders =
                orderService.searchByCustomer(1001L);

        assertEquals(1, orders.size());

        verify(orderRepository)
                .findByCustomerId(1001L);
    }

    @Test
    void shouldFilterByStatus() {

        when(orderRepository.findByStatus(OrderStatus.CREATED))
                .thenReturn(List.of(order));

        List<Order> orders =
                orderService.filterByStatus(OrderStatus.CREATED);

        assertEquals(1, orders.size());

        verify(orderRepository)
                .findByStatus(OrderStatus.CREATED);
    }

}