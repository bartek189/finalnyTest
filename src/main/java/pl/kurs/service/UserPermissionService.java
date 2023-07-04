package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.kurs.entity.model.Role;
import pl.kurs.entity.model.User;
import pl.kurs.util.SecurityUtil;

import static pl.kurs.entity.model.ERole.ROLE_CREATOR;


@RequiredArgsConstructor
@Service
@RequestScope
public class UserPermissionService {
    private final SecurityUtil securityUtil;

    public void validateRequest() {

        User user = securityUtil.getUser();
        Role role = new Role(ROLE_CREATOR);

        if (user.getRoles().stream().noneMatch(u -> u.getName() == role.getName())) {
            throw new IllegalStateException("UNAUTHORIZED");
        }
    }
}
