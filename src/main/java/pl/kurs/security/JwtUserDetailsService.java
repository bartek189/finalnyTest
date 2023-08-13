package pl.kurs.security;

import io.swagger.annotations.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kurs.entity.model.User;
import pl.kurs.repository.UserRepository;
import pl.kurs.util.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//      User user = repository.findByUserNameWithRoles(username).orElseThrow(() -> new UsernameNotFoundException(username));


//      List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

//      return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
//  }

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
