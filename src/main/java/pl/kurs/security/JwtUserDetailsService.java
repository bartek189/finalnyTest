package pl.kurs.security;

import lombok.RequiredArgsConstructor;
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
        final User user = repository.findByUserNameWithRoles(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList()))
                .build();
    }

}
