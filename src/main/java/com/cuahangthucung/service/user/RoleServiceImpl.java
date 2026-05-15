package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.repository.user.RoleRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer, RoleRepository> 
        implements RoleService {

    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return repository.findByRoleName(roleName);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return repository.existsByRoleName(roleName);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}