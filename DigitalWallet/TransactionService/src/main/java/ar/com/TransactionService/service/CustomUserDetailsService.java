//package ar.com.TransactionService.service;
//
//import ar.com.util.CommonConstants;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    RestTemplate restTemplate;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String url="http://localhost:8090/onboarding-service/user/details/"+username;
//
//        String response=restTemplate.getForObject(url,String.class);
//        System.out.println("response"+response);
//        if(response==null){
//            throw new UsernameNotFoundException("user not found");
//
//        }
//        JSONObject responseObject=new JSONObject(response);
//        String password =responseObject.getString(CommonConstants.USER_PASSWORD);
//        UserDetails userDetails= User.builder().username(username).password(password).build();
//        return userDetails;
//    }
//}
package ar.com.TransactionService.service;

import ar.com.util.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = "http://localhost:8090/onboarding-service/user/details/" + username;

        String response;
        try {
            response = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User service error: " + e.getMessage());
        }

        if (response == null) {
            throw new UsernameNotFoundException("User not found");
        }

        JSONObject responseObject = new JSONObject(response);
        String password = responseObject.getString(CommonConstants.USER_PASSWORD);

        // ⚠️ Make sure this password is BCrypt-encoded!
        return User.builder()
                .username(username)
                .password(password)
                .roles("USER") // ✅ add at least one role
                .build();
    }
}

