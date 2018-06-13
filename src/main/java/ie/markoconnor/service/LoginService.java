package ie.markoconnor.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public boolean validateUser(String userid, String password){
       return userid.equalsIgnoreCase(("in28Minutes")) && password.equalsIgnoreCase("1234");
    }
}
