package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "P4ssword";

    private CartController cartController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddTocart() {
        Item item = new Item(0L,"Square Widget", new BigDecimal("1.99"), "A widget that is square");

        List<Item> items = new ArrayList<>();
        items.add(item);

        User user = new User();

        Cart cart = new Cart(0L,items,user,item.getPrice());

        user.setId(0L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCart(cart);

        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(USERNAME);

        ResponseEntity <Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        Cart responseCart = responseEntity.getBody();
        Item responseItem = responseCart.getItems().get(0);
        User responseUser = responseCart.getUser();

        assertNotNull(responseEntity);
        assertNotNull(responseCart);
        assertNotNull(responseItem);
        assertNotNull(responseUser);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(item,responseItem);
        assertEquals(user,responseUser);

    }


    @Test
    public void testRemoveFromcart() {
        Item squareItem = new Item(0L,"Square Widget", new BigDecimal("1.99"), "A widget that is square");
        Item roundItem = new Item(1L,"Round Widget", new BigDecimal("2.99"), "A widget that is round");

        List<Item> items = new ArrayList<>();
        items.add(squareItem);
        items.add(roundItem);

        BigDecimal total = squareItem.getPrice().add(roundItem.getPrice());

        User user = new User();
        Cart cart = new Cart(0L,items,user,total);


        user.setId(0L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCart(cart);

        when(itemRepository.findById(0L)).thenReturn(Optional.of(squareItem));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(roundItem));
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(USERNAME);
        ResponseEntity <Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        Cart responseCart = responseEntity.getBody();
        List<Item> responseItems = responseCart.getItems();
        User responseUser = responseCart.getUser();

        assertNotNull(responseEntity);
        assertNotNull(responseCart);
        assertNotNull(responseItems);
        assertNotNull(responseUser);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(cart.getItems(),responseItems);
        assertEquals(items.size(),responseItems.size());
        assertEquals(user,responseUser);

        //item removal part
        total = responseCart.getTotal();
        responseCart.removeItem(responseItems.get(0));
        List<Item> responseItemsAfter = responseCart.getItems();

        assertNotNull(responseItemsAfter);
        assertEquals(total.subtract(squareItem.getPrice()),responseCart.getTotal());

    }
}