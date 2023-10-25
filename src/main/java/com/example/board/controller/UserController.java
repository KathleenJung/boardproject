package com.example.board.controller;

import com.example.board.config.AdminAuthorize;
import com.example.board.domain.User;
import com.example.board.dto.LoginDto;
import com.example.board.dto.TokenDto;
import com.example.board.dto.UserDto;
import com.example.board.jwt.JwtFilter;
import com.example.board.jwt.TokenProvider;
import com.example.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/admin-only")
    @AdminAuthorize
    public ResponseEntity<String> adminOnlyEndpoint() {
        return ResponseEntity.ok("This is accessible only by ADMIN");
    }

    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo(Authentication authentication) {
        // 현재 로그인한 사용자의 정보 가져오기
        String username = authentication.getName();

        return ResponseEntity.ok(userService.getUserWithAuthorities(username)
                .map(user -> new User(user.getUsername(), user.getName(), user.getAuthorities()))
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND).getBody()));
    }

    @GetMapping("/info/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserInfoForAdmin(@PathVariable String username) {
        return userService.getUserWithAuthorities(username)
                .map(user -> new ResponseEntity<>(new User(user.getId(), user.getUsername(), user.getName(), user.getAuthorities()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 로그인 후 토큰 발급
    @PostMapping("/auth")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // 인증 실패한 경우 로그에 기록
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}