package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.kurs.entity.command.CreateUserCommand;
import pl.kurs.entity.dto.UserCreatedShapesDto;
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

    @GetMapping("/createdShapes") // TODO: moze /shapes po prostu
    public ResponseEntity<UserCreatedShapesDto> getShapes(String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.shapes(name));
    }
}
