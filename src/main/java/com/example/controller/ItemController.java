package com.example.controller;

import com.example.entity.Item;
import com.example.service.ItemDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/item" })
public class ItemController {

    @Autowired
    private ItemDB itemDB;

    @GetMapping(value = { "/insert" })
    public String insertGET() {

        return "item/insert";
    }

    @PostMapping(value = { "/insert" })
    public String insertPOST(@ModelAttribute Item tem) {

        System.out.println(tem.toString());
        itemDB.inserItem(tem);
        return "redirect:/item/insert";
    }
}
