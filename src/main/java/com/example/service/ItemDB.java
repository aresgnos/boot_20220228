package com.example.service;

import com.example.entity.Item;

import org.springframework.stereotype.Service;

@Service
public interface ItemDB {

    public Item inserItem(Item item);
}
