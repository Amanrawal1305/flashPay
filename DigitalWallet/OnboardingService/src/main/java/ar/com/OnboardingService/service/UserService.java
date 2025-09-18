package ar.com.OnboardingService.service;


import ar.com.OnboardingService.modal.User;
import ar.com.OnboardingService.repository.UserRepository;
import ar.com.OnboardingService.request.UserCreationRequest;
import ar.com.enums.UserStatus;
import ar.com.util.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    public User createNewUser(UserCreationRequest userCreationRequest){
        User user=new User();
        user.setName(userCreationRequest.getName());
        user.setEmail(userCreationRequest.getEmail());
        user.setMobileNo(userCreationRequest.getMobileNo());
        user.setDob(userCreationRequest.getDob());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserIdentifier(userCreationRequest.getUserIdentifier());
        user.setUserIdentifierValue(userCreationRequest.getUserIdentifierValue());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(CommonConstants.USER_NAME,user.getName());
        jsonObject.put(CommonConstants.USER_EMAIL,user.getEmail());
        jsonObject.put(CommonConstants.USER_MOBILE,user.getMobileNo());
        jsonObject.put(CommonConstants.USER_IDENTIFIER,user.getUserIdentifier());
        jsonObject.put(CommonConstants.USER_IDENTIFIER_VALUE,user.getUserIdentifierValue());
        try {
            User dbUser=userRepository.save(user);
            jsonObject.put(CommonConstants.USER_ID,user.getId());

            Thread thread=new Thread(()->{
                kafkaTemplate.send(CommonConstants.USER_DETAILS_QUEUE_TOPIC,jsonObject.toString());
                System.out.println("data sent to kafka");

            });
            thread.start();
            return dbUser;
        }
        catch (Exception ex) {
            System.out.print("exception"+ex);

        }
        return null;

    }
    public String findUserByUsername(String username){
        User user=userRepository.findByMobileNo(username);
        if(user==null){
            return null;

        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(CommonConstants.USER_NAME,user.getMobileNo());
        jsonObject.put(CommonConstants.USER_PASSWORD,user.getPassword());
        return jsonObject.toString();
    }
}
