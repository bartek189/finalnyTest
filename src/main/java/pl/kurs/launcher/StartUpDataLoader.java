package pl.kurs.launcher;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.Role;
import pl.kurs.repository.RoleRepository;

@RequiredArgsConstructor
@Component
public class StartUpDataLoader {
    private final RoleRepository repository;


    @EventListener(ApplicationReadyEvent.class)
    public void saveRole() {
        Role creator = repository.save(new Role(ERole.ROLE_CREATOR));
        Role user = repository.save(new Role(ERole.ROLE_USER));

    }
}
