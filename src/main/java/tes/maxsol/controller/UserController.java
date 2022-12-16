package tes.maxsol.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tes.maxsol.repository.UserRepository;
import tes.maxsol.response.ResponseHandler;

@RestController
@RequestMapping("/maxsol")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("user")
    public ResponseEntity<?> login(@RequestParam("nama") String username, @RequestParam("password") String pwd) {
        try {
            String token = null;
            String nama = userRepository.findNama(username);
            String password = userRepository.findPassword(username);
            if (username.equalsIgnoreCase(nama) && pwd.equalsIgnoreCase(password)) {
                token = getJWTToken(username);
            }
            userRepository.updateToken(token, nama);
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Token Berhasil Diupdate");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, "Nama atau password Anda salah!", null);
        }

    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        String role = userRepository.findRole(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        String token = Jwts
                .builder()
                .setId("myJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
