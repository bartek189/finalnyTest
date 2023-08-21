package pl.kurs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.User;
import pl.kurs.repository.UserRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User customer = repository.findByUserNameWithRoles(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User.withUsername(customer.getUserName())
                .password(customer.getPassword())
                .authorities(customer.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList()))
                .build();
    }

}
