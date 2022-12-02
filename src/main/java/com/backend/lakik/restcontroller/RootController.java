package com.backend.lakik.restcontroller;

import com.backend.lakik.rest.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping(produces = "application/json")
    public BaseResponse<?> healthCheck() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "service is healthy");

        return BaseResponse.<Map<String, Object>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .result(map)
                .build();
    }
}
