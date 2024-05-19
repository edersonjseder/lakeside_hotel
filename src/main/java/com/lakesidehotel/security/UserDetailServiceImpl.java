package com.lakesidehotel.security;

import com.lakesidehotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.lakesidehotel.constants.UserMessagesConstants.USER_REMOVED_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_REMOVED_SUCCESS_MESSAGE));
        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserById(UUID id) throws AuthenticationCredentialsNotFoundException {
        var user = userRepository.findUserById(id).orElseThrow(() -> new AuthenticationCredentialsNotFoundException(USER_REMOVED_SUCCESS_MESSAGE));
        return UserDetailsImpl.build(user);
    }
}
