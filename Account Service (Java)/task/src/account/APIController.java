package account;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
    private final Bank bank = new Bank();
    @PostMapping(path = "api/auth/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@RequestBody User user){
        if(user.getName()!= null && user.getLastName() != null && user.getEmail() != null && user.isPasswordSet()) {
            if (!(user.getName().isEmpty()) && !(user.getLastName().isEmpty()) && !(user.getEmail().isEmpty()) && bank.emailVerification(user)) {
                return new ResponseEntity<>(user.getUserInfo(), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
