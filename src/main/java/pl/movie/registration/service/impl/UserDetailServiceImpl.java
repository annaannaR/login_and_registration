package pl.movie.registration.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.movie.registration.model.Role;
import pl.movie.registration.model.User;
import pl.movie.registration.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User customer = userRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }

        List<String> roles = customer.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getEmail())
                .password(customer.getPassword())
                .roles(roles.toArray(new String[roles.size()]))
                .authorities("USER")
                .build();

    }
}
