package com.backend.lakik.restcontroller;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.rest.dto.BaseResponse;
import com.backend.lakik.service.KamarRestService;
import com.backend.lakik.service.UserRestService;
import com.backend.lakik.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/kamar")
public class KamarController {
    @Autowired
    private KamarRestService kamarRestService;

    @Autowired
    private UserRestService userRestService;

    @PostMapping(produces = "application/json")
    public BaseResponse<KamarModel> createKamar(@RequestBody KamarModel kamar,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        var decodedJWT = JWTUtils.decodeJWTToken(authHeader);
        var username = decodedJWT.getSubject();

        var createdKamar = kamarRestService.createKamar(kamar);

        userRestService.addKamar(username, createdKamar);

        return BaseResponse.<KamarModel>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .result(createdKamar)
                .build();
    }

    @PutMapping(value = "/{idKamar}", produces = "application/json")
    public BaseResponse<KamarModel> updateKamar(@PathVariable(value = "idKamar") Long idKamar,
            @RequestBody KamarModel kamar) {
        try {
            var updatedKamar = kamarRestService.updateKamar(idKamar, kamar);
            return BaseResponse.<KamarModel>builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .result(updatedKamar)
                    .build();

        } catch (IllegalStateException exception) {
            return BaseResponse.<KamarModel>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .result(null)
                    .message("not found").build();
        }
    }

    @GetMapping(produces = "application/json")
    public BaseResponse<UserModel> readKamar(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        var decodedJWT = JWTUtils.decodeJWTToken(authHeader);
        var username = decodedJWT.getSubject();

        var user = userRestService.getUserByUsername(username);
        return BaseResponse.<UserModel>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .result(user)
                .build();
    }

    @DeleteMapping(value = "/{idKamar}", produces = "application/json")
    public BaseResponse<String> deleteKamar(@PathVariable(value = "idKamar") Long idKamar) {
        try {
            kamarRestService.deleteKamar(idKamar);
            return BaseResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .result(null)
                    .build();

        } catch (IllegalStateException exception) {
            return BaseResponse.<String>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .result(null)
                    .message("not found").build();
        }
    }
}
