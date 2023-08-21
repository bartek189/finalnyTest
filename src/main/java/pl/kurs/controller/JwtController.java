package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.entity.request.JwtRequestDto;
import pl.kurs.entity.response.JwtResponseDto;
import pl.kurs.service.JwtControllerService;


@RequiredArgsConstructor
@RestController
@CrossOrigin
public class JwtController {
    private final JwtControllerService service;


    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDto> createAuthenticationToken(@RequestBody JwtRequestDto requestDto) throws Exception {
        return ResponseEntity.ok(service.createAuthenticationToken(requestDto));
    }


}
