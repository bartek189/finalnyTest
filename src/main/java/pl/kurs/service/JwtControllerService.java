package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.entity.request.JwtRequestDto;
import pl.kurs.entity.response.JwtResponseDto;
import pl.kurs.security.JwtTokenUtil;
import pl.kurs.security.JwtUserDetailsService;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@CrossOrigin
@Service
public class JwtControllerService {

    private final JwtTokenUtil jwtTokenUtil;


    private final AuthenticationManager authenticationManager;

    public JwtResponseDto createAuthenticationToken(@RequestBody JwtRequestDto requestDto) throws Exception {
        UserDetails userDetails = authenticate(requestDto.getUserName(), requestDto.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponseDto(token);
    }

    private UserDetails authenticate(String userName, String password) throws Exception {
        try {
            return (UserDetails) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password)).getPrincipal();
        } catch (DisabledException | BadCredentialsException exc) {
            throw new Exception(exc.getMessage());
        }
    }
}
