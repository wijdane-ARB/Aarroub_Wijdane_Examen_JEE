package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.web;

import lombok.RequiredArgsConstructor;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AuthRequest;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AuthResponse;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String username = authenticate.getName();
        var roles = authenticate.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = jwtUtil.generateToken(username, roles);
        return new AuthResponse(token);
    }
}