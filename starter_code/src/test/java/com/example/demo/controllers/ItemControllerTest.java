package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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

public class ItemControllerTest {
    private static final Item SQUARE_ITEM = new Item(0L,"Square Widget", new BigDecimal("1.99"), "A widget that is square");;
    private static final Item ROUND_ITEM =new Item(1L, "Round Widget", new BigDecimal("2.99"), "A widget that is round");;
    private ItemController itemController;
    private final ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItems() {
        List<Item> items = new ArrayList<>();
        items.add(SQUARE_ITEM);
        items.add(ROUND_ITEM);
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity);
        List<Item> responseItems = responseEntity.getBody();
        assertNotNull(responseItems);
        assertEquals(responseItems.get(0), SQUARE_ITEM);
        assertEquals(responseItems.get(1), ROUND_ITEM);


    }

    @Test
    public void testGetItemById() {
        when(itemRepository.findById(0L)).thenReturn(Optional.of(SQUARE_ITEM));

        ResponseEntity<Item> responseEntity = itemController.getItemById(0L);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity);
        Item responseItem = responseEntity.getBody();
        assertNotNull(responseItem);
        assertEquals(responseItem,SQUARE_ITEM);

    }

    @Test
    public void testGetItemsByName() {
        List<Item> items = new ArrayList<>();
        items.add(SQUARE_ITEM);
        when(itemRepository.findByName(SQUARE_ITEM.getName())).thenReturn(items);

        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName(SQUARE_ITEM.getName());

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity);
        List<Item> responseItems = responseEntity.getBody();
        assertNotNull(responseItems);
        assertEquals(responseItems,items);
    }
}