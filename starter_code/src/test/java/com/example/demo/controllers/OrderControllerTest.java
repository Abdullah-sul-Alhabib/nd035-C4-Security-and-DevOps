package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "P4ssword";
    private static final String SALT = "Salt";
    private static final Item SQUARE_ITEM = new Item(0L,"Square Widget", new BigDecimal("1.99"), "A widget that is square");;;


    private OrderController orderController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void testSubmit() {
        List<Item> items = new ArrayList<>();
        items.add(SQUARE_ITEM);
        Cart cart = new Cart(0L,items,new User(),SQUARE_ITEM.getPrice());
        User user = new User(0L,USERNAME,SALT,PASSWORD,new Cart());
        user.setCart(cart);
        cart.setUser(user);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> responseEntity = orderController.submit(USERNAME);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        UserOrder userOrder = responseEntity.getBody();
        assertNotNull(userOrder);
        assertEquals(user,userOrder.getUser());
        assertEquals(items,userOrder.getItems());
        assertEquals(cart.getTotal(),userOrder.getTotal());
    }

    @Test
    public void testGetOrdersForUser() {
        List<Item> items = new ArrayList<>();
        items.add(SQUARE_ITEM);
        Cart cart = new Cart(0L,items,new User(),SQUARE_ITEM.getPrice());
        User user = new User(0L,USERNAME,SALT,PASSWORD,new Cart());
        user.setCart(cart);
        cart.setUser(user);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> submitResponseEntity = orderController.submit(USERNAME);
        assertNotNull(submitResponseEntity);
        assertTrue(submitResponseEntity.getStatusCode().is2xxSuccessful());

        UserOrder submittedOrder = submitResponseEntity.getBody();
        List<UserOrder> submittedOrders = new ArrayList<>();
        submittedOrders.add(submittedOrder);
        when(orderRepository.findByUser(user)).thenReturn(submittedOrders);

        ResponseEntity<List<UserOrder>> getOrdersResponseEntity = orderController.getOrdersForUser(USERNAME);
        assertNotNull(getOrdersResponseEntity);
        assertTrue(getOrdersResponseEntity.getStatusCode().is2xxSuccessful());

        List<UserOrder> userOrders = getOrdersResponseEntity.getBody();
        assertNotNull(userOrders);
        assertEquals(submittedOrders,userOrders);
    }
}