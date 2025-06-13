package org.dante.springboot.springbootjwtserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dante.springboot.springbootjwtserver.dto.JwtAuthReqDTO;
import org.dante.springboot.springbootjwtserver.dto.JwtAuthRespDTO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 登录，获取JWT Token
     */
    @PostMapping(value = "${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthReqDTO reqDTO) throws AuthenticationException{
        final String token = authService.login(reqDTO.getUsername(), reqDTO.getPassword());
        return ResponseEntity.ok(new JwtAuthRespDTO(token));
    }
    
    /**
     * 刷新Token
     */
    @GetMapping("${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthRespDTO(refreshedToken));
        }
    }
    
    /**
     * 用户注册
     */
    @PostMapping("${jwt.route.authentication.register}")
    public UserPO register(@RequestBody UserPO addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }
}
