package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.SignInDto;
import com.lakesidehotel.dtos.UserDto;
import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.enums.UserStatus;
import com.lakesidehotel.enums.UserType;
import com.lakesidehotel.exceptions.RoleNotFoundException;
import com.lakesidehotel.exceptions.UserException;
import com.lakesidehotel.exceptions.UserNotFoundException;
import com.lakesidehotel.models.Role;
import com.lakesidehotel.models.User;
import com.lakesidehotel.repositories.RoleRepository;
import com.lakesidehotel.repositories.UserRepository;
import com.lakesidehotel.responses.TokenResponse;
import com.lakesidehotel.security.AuthorizationCurrentUserService;
import com.lakesidehotel.security.JwtProvider;
import com.lakesidehotel.security.UserDetailsImpl;
import com.lakesidehotel.services.UserService;
import com.lakesidehotel.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.lakesidehotel.constants.UserMessagesConstants.USERNAME_ALREADY_EXISTS_MESSAGE;
import static com.lakesidehotel.constants.UserMessagesConstants.USER_EMAIL_ALREADY_EXISTS_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserUtils userUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthorizationCurrentUserService authorizationCurrentUserService;

    @Override
    public List<UserDto> findAllUsers() {
        return userUtils.toListUserDto(userRepository.findAll());
    }

    @Override
    public User findUserById(UUID id) {
        var currentUserId = authorizationCurrentUserService.getCurrentUser().getId();
        if (currentUserId.equals(id)) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));
        } else {
            throw new AccessDeniedException(authorizationCurrentUserService.getCurrentUser().getFullName() + " doesn't have the correct authorities to perform this action");
        }
    }

    @Override
    public TokenResponse signIn(SignInDto request) throws BadCredentialsException {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwt = jwtProvider.generateToken(authentication);

        return TokenResponse.builder().authorization(jwt).build();
    }

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) throws IOException {
        var userRegistered = new User();

        if (userDto.getId() == null) {

            if (userRepository.existsByUsername(userDto.getUsername())) {
                throw new UserException(USERNAME_ALREADY_EXISTS_MESSAGE + userDto.getUsername());
            }
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new UserException(USER_EMAIL_ALREADY_EXISTS_MESSAGE + userDto.getEmail());
            }

            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            var user = new User();

            BeanUtils.copyProperties(userDto, user);

            user.setUserStatus(UserStatus.ACTIVE);
            user.setUserType(UserType.CUSTOMER);
            user.addRole(verifyRole(Roles.ROLE_CUSTOMER));
            user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            user.setCurrentPasswordDate(LocalDateTime.now(ZoneId.of("UTC")));
            log.debug("Method saveUser user created {} ", user);

            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userRegistered = userRepository.save(user);

        } else {
            UserDetailsImpl currentLoggedUser = (UserDetailsImpl) authorizationCurrentUserService.getAuthentication().getPrincipal();

            currentLoggedUser.getAuthorities().forEach(auth -> {
                if (auth.getAuthority().equals(Roles.ROLE_ADMIN.name())) {
                    userDto.setAuthorized(true);
                }
            });

            if (currentLoggedUser.getId().equals(userDto.getId()) || userDto.isAuthorized()) {
                var user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException(userDto.getId()));

                user.setUsername(userDto.getUsername());
                user.setEmail(userDto.getEmail());
                user.setFullName(userDto.getFullName());
                user.setUserType(UserType.valueOf(userDto.getType()));
                log.debug("Method saveUser user updated {} ", user.toString());

                userRegistered = user;
            } else {
                throw new AccessDeniedException(userDto.getId().toString());
            }
        }

        return userUtils.toUserDto(userRegistered);
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        var currentUserId = authorizationCurrentUserService.getCurrentUser().getId();
        if (currentUserId.equals(id)) {
            var user = findUserById(id);
            userRepository.delete(user);
        } else {
            throw new AccessDeniedException(id.toString());
        }
    }

    private Role verifyRole(Roles name) {
        return roleRepository.findByRoleName(name).orElseThrow(RoleNotFoundException::new);
    }

}
