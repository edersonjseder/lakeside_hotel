package com.lakesidehotel.services;

import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> fetchAllByName(String name);
    Role fetchRoleByName(Roles name);
}
