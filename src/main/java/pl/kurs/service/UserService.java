package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kurs.entity.command.CreateUserCommand;
import pl.kurs.entity.model.ERole;
import pl.kurs.entity.model.Role;
import pl.kurs.entity.model.User;
import pl.kurs.repository.RoleRepository;
import pl.kurs.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public User saveNewCreator(CreateUserCommand userCommand) {
        userCommand.setPassword(encoder.encode(userCommand.getPassword()));
        User user = modelMapper.map(userCommand, User.class);
        if (userCommand.getRoles().contains(ERole.ROLE_CREATOR)) {
            roleRepository.findByName(ERole.ROLE_CREATOR).ifPresentOrElse(role -> user.setRoles(Set.of(role)), () -> {
                Role role = roleRepository.save(new Role(ERole.ROLE_CREATOR));
                user.setRoles(Set.of(role));
            });
        }
        if (userCommand.getRoles().contains(ERole.ROLE_USER)) {
            roleRepository.findByName(ERole.ROLE_USER).ifPresentOrElse(role -> user.setRoles(Set.of(role)), () -> {
                Role role = roleRepository.save(new Role(ERole.ROLE_USER));
                user.setRoles(Set.of(role));
            });
        }
        return repository.save(user);
    }
}
