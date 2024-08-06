package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.GroupService;
import com.ubio.stockdemo.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/all")
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public Group find(@PathVariable String id) {
        return groupService.find(id);
    }

    @GetMapping("/name/{name}")
    public Group findByName(@PathVariable String name) {
        return groupService.findByName(name);
    }





}
