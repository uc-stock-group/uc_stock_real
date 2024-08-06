package com.ubio.stockdemo.api.config;

import com.ubio.stockdemo.api.repository.GroupRepository;
import com.ubio.stockdemo.api.repository.UserRepository;
import com.ubio.stockdemo.model.entity.Group;
import com.ubio.stockdemo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class DummyDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static final String[] NAMES = {"Alice", "Bob", "Charlie", "David", "Eve"};
    private static final String[] EMAIL_DOMAINS = {"example.com", "test.com", "dummy.com"};

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 3) {
            System.out.println("Dummy data is enough.");
            return;
        }
        Random random = new Random();

        // 그룹 생성
        Group group = new Group();
        group.setParentId(UUID.randomUUID().toString().substring(0, 6));
        group.setDepth(1);
        group.setPolicyId(UUID.randomUUID().toString().substring(0, 6));
        group.setKey(UUID.randomUUID().toString().substring(0, 6));
        group.setGroupName("Dummy Group " + random.nextInt(1000));
        group.setDescription("This is a dummy group");
        group.setOrder(1);
        group.setCreatedAt(LocalDateTime.now());
        group.setUpdatedAt(LocalDateTime.now());

        group = groupRepository.save(group);



        System.out.println("Dummy data loaded successfully.");
    }
}
