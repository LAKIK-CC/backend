package com.backend.lakik.restcontroller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.rest.dto.BaseResponse;
import com.backend.lakik.rest.dto.RegisterRequest;
import com.backend.lakik.service.RoleRestService;
import com.backend.lakik.service.UserRestService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserRestService userRestService;

    @Autowired
    private RoleRestService roleRestService;

    @PostMapping("/register")
    public BaseResponse<UserModel> register(@Valid @RequestBody RegisterRequest userData, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Body has invalid type or missing field");
        } else {
            try {
                RoleModel role = roleRestService.getRoleByRoleName(userData.getRole());
                UserModel user = userRestService.createUser(userData,role);
                
                return BaseResponse.<UserModel>builder()
                    .status(200)
                    .message("success")
                    .result(user)
                    .build();
                    
            } catch (DataIntegrityViolationException e){
                return BaseResponse.<UserModel>builder()
                    .status(400)
                    .message("Username telah terdaftar")
                    .result(null)
                    .build();
            } catch (Exception e) {
                return BaseResponse.<UserModel>builder()
                    .status(500)
                    .message(e.toString())
                    .result(null)
                    .build();
            }
        }
    }
}
