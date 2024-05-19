package com.lakesidehotel.services.impl;

import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.exceptions.RoleNotFoundException;
import com.lakesidehotel.models.Role;
import com.lakesidehotel.repositories.RoleRepository;
import com.lakesidehotel.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> fetchAllByName(String name) {
        return roleRepository.findAllByRoleName(name)
                .orElseThrow(RoleNotFoundException::new);
    }

    @Override
    public Role fetchRoleByName(Roles name) {
        return roleRepository.findByRoleName(name)
                .orElseThrow(RoleNotFoundException::new);
    }
}
