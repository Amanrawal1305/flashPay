package ar.com.OnboardingService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationResponse extends Response{
    String message;
    String code;
    String Name;
    String Email;


//    public void setName(String name) {
//    }
}
