package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.kurs.entity.model.Role;
import pl.kurs.entity.model.User;
import pl.kurs.exception.UnauthorizedException;
import pl.kurs.util.SecurityUtil;

import static pl.kurs.entity.model.ERole.ROLE_CREATOR;


@RequiredArgsConstructor
@Service
@RequestScope
public class UserPermissionService {
    private final SecurityUtil securityUtil;

    public void validateRequest() {

        User user = securityUtil.getUser();

        if (user.getRoles().stream().noneMatch(u -> u.getName().equals(ROLE_CREATOR))) {
            throw new UnauthorizedException();
        }
    }
}
