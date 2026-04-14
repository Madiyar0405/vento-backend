package dev.madiyar.vento.security;


import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/// the packaging structure is not well defined,
///
/// better to have all internal application logic in one place
/// since it is related to application configuration it most probably better to move to config or security and
///
/// make it package private access level
/// it is general rule `interface` is `public`, `implementation` is `package level` at most
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new CustomUserDetails(user);
    }
}
