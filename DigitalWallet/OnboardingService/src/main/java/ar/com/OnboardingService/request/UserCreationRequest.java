package ar.com.OnboardingService.request;

import ar.com.enums.UserIdentifier;
//import ar.com.enums.UserStatus;
//import jakarta.persistence.Column;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class UserCreationRequest {
    private String name;
    private String email;
    private String mobileNo;
    private String password;
    private String dob;
    private UserIdentifier userIdentifier;
    private String userIdentifierValue;


}

