package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.api.repository.GroupRepository;
import com.ubio.stockdemo.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group find(String id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Group findByName(String name) {
        return groupRepository.findByGroupName(name);
    }
}
