package com.ubio.stockdemo.api.repository;

import com.ubio.stockdemo.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository  extends JpaRepository<Group, String> {
    Group findByGroupName(String name);
}
