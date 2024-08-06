package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.model.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAll();

    Group find(String id);

    Group findByName(String name);
}
