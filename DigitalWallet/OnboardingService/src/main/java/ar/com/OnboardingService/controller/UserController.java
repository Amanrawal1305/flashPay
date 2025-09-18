package ar.com.OnboardingService.controller;

import ar.com.OnboardingService.modal.User;
import ar.com.OnboardingService.request.UserCreationRequest;
import ar.com.OnboardingService.response.UserCreationResponse;
import ar.com.OnboardingService.service.UserService;
import ar.com.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding-service")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/create/user")
    public ResponseEntity<UserCreationResponse> onBoardUser(@RequestBody UserCreationRequest userCreationRequest){
        System.out.println(userCreationRequest);
        UserCreationResponse userCreationResponse=new UserCreationResponse();
        userCreationResponse.setCode("02");
        if(StringUtil.isBlank(userCreationRequest.getEmail())){
            userCreationResponse.setMessage("Email can not be blank");
            return new ResponseEntity<>(userCreationResponse,HttpStatus.OK);
        }else if(StringUtil.isBlank(userCreationRequest.getMobileNo())) {
            userCreationResponse.setMessage("MobileNO can not be blank");
            return new ResponseEntity<>(userCreationResponse,HttpStatus.OK);
        }else if(StringUtil.isBlank(userCreationRequest.getUserIdentifierValue())|| StringUtil.isBlank(userCreationRequest.getPassword())){
            userCreationResponse.setMessage("Invalid Request");
            return new ResponseEntity<>(userCreationResponse,HttpStatus.BAD_REQUEST);
        }else{
            User users= userService.createNewUser(userCreationRequest);
            if(users==null){
                userCreationResponse.setMessage("user not created");
                userCreationResponse.setCode("02");
            }
            userCreationResponse.setEmail(users.getEmail());
            userCreationResponse.setName(users.getName());
            userCreationResponse.setCode("00");

        }
        return new ResponseEntity<>(userCreationResponse,HttpStatus.CREATED);
    }
    @GetMapping("/user/details/{userId}")
    public String getUserDetails(@PathVariable("userId")String username){
        return userService.findUserByUsername(username);


    }
}
