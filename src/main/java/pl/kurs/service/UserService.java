package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kurs.entity.command.CreateUserCommand;
import pl.kurs.entity.dto.UserCreatedShapesDto;
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


        Role role = roleRepository.findByName(userCommand.getRoleName()).orElseThrow(); // TODO jakis custom wyjatek
        user.setRoles(Set.of(role));

        return repository.save(user);
    }

    public UserCreatedShapesDto shapes(String name) {
        User user = repository.findByIdWithShapes(name).get(); // TODO i tu tez
        Integer shapes = user.getCreatedShape().size();
        UserCreatedShapesDto userCreatedShapes = new UserCreatedShapesDto(user.getUserName(), shapes);
        return userCreatedShapes;
    }
}
