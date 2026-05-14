package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.user.UserRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public String generateNextUsername() {
        String prefix = "USER" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        
        return repository.findLastUserByPrefix(prefix)
                .map(lastUser -> {
                    String lastUsername = lastUser.getUsername();
                    int lastNumber = Integer.parseInt(lastUsername.substring(7)); // USER2605XX
                    return String.format("%s%03d", prefix, lastNumber + 1);
                })
                .orElse(prefix + "001");
    }

    @Override
    public User save(User entity) {
        if (entity.getUsername() == null || entity.getUsername().isEmpty()) {
            entity.setUsername(generateNextUsername());
        }
        return super.save(entity);
    }
}