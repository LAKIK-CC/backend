package com.backend.lakik.restcontroller;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.rest.dto.BaseResponse;
import com.backend.lakik.service.KamarRestService;
import com.backend.lakik.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/kamar")
public class KamarController {
    @Autowired
    private KamarRestService kamarRestService;

    @PostMapping
    public BaseResponse<KamarModel> createKamar(@RequestBody KamarModel kamar,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        var decodedJWT = JWTUtils.decodeJWTToken(authHeader);
        var username = decodedJWT.getSubject();

        var createdKamar = kamarRestService.createKamar(username, kamar);

        return BaseResponse.<KamarModel>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("success")
                    .result(createdKamar)
                    .build();
    }

    @PutMapping("/{idKamar}")
    public BaseResponse<KamarModel> updateKamar(@PathVariable(value = "idKamar") Long idKamar,
                                                @RequestBody KamarModel kamar) {
        try {
            var updatedKamar = kamarRestService.updateKamar(idKamar, kamar);
            return BaseResponse.<KamarModel>builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("success")
                    .result(updatedKamar)
                    .build();

        } catch(IllegalStateException exception) {
            return BaseResponse.<KamarModel>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .result(null)
                    .message("not found").build();
        }
    }

    @GetMapping("/")
    public BaseResponse<List<KamarModel>> readKamar(@RequestParam String username) {
        var Kamars = kamarRestService.readKamar(username);
        return BaseResponse.<List<KamarModel>>builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("success")
                .result(Kamars)
                .build();
    }

    @DeleteMapping("/{idKamar}")
    public BaseResponse<String> deleteKamar(@PathVariable(value = "idKamar") Long idKamar) {
        try {
            kamarRestService.deleteKamar(idKamar);
            return BaseResponse.<String>builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("success")
                .result(null)
                .build();

        } catch(IllegalStateException exception) {
            return BaseResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .result(null)
                .message("not found").build();
        }
    }
}
