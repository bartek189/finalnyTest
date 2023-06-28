package pl.kurs.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.kurs.entity.model.User;
import pl.kurs.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getUser() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(userName).orElseThrow();
    }
}
