package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.kurs.entity.command.CreateUserCommand;
import pl.kurs.entity.dto.UserDto;
import pl.kurs.entity.model.User;
import pl.kurs.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register/users")
    public ResponseEntity<UserDto> addCreator(@RequestBody CreateUserCommand userCommand) {
        User user = service.saveNewCreator(userCommand);
        return ResponseEntity.status(201).body(new UserDto(user));
    }

}
