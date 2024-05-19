package com.lakesidehotel.utils;

import com.lakesidehotel.dtos.UserDto;
import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.enums.UserStatus;
import com.lakesidehotel.enums.UserType;
import com.lakesidehotel.exceptions.RoleNotFoundException;
import com.lakesidehotel.models.Role;
import com.lakesidehotel.models.User;
import com.lakesidehotel.repositories.RoleRepository;
import com.lakesidehotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Log4j2
@Component
@RequiredArgsConstructor
public class UserUtils {
    private final DateUtils dateUtils;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    public UserDto toUserDto(User user) {
        log.debug("Method toUserDto user saved {} ", user.toString());
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .status(user.getUserStatus().name())
                .type(user.getUserType().name())
                .creationDate(dateUtils.parseDate(user.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .build();
    }

    public List<UserDto> toListUserDto(List<User> userList) {
        return userList.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .status(user.getUserStatus().name())
                .type(user.getUserType().name())
                .creationDate(dateUtils.parseDate(user.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .build()).collect(Collectors.toList());
    }

    public User createAdminUser(String username, String email, String password) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFullName("Ederson Santos");
        user.addRole(verifyRole(Roles.ROLE_ADMIN));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.ADMIN);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setCurrentPasswordDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return user;
    }

    private Role verifyRole(Roles name) {
        return roleRepository.findByRoleName(name).orElseThrow(RoleNotFoundException::new);
    }
}
