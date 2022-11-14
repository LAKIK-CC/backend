package com.backend.lakik.restcontroller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.rest.dto.BaseResponse;
import com.backend.lakik.service.UserRestService;
import com.backend.lakik.util.JWTUtils;

@RestController
@RequestMapping("/v1/token")
public class TokenController {

    @Autowired
    private UserRestService userRestService;
    
    @GetMapping("/refresh")
    public BaseResponse<String> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ". length());

                DecodedJWT decodedJWTRefresh = JWTUtils.decodeJWTToken(refreshToken);
                String username = decodedJWTRefresh.getSubject();
                UserModel user = userRestService.getUserByUsername(username);

                String accessToken = JWTUtils.generateAccessToken(user);

                return BaseResponse.<String>builder()
                    .status(200)
                    .message("Success")
                    .result(accessToken)
                    .build();

            } catch (Exception err) {

                return BaseResponse.<String>builder()
                    .status(500)
                    .message(err.toString())
                    .result(null)
                    .build();
            }
        }
        return BaseResponse.<String>builder()
                    .status(500)
                    .message("Refresh token tidak benar")
                    .result(null)
                    .build();
    }
}
