package account;

import java.util.ArrayList;
import java.util.Objects;

public class Bank {
    public ArrayList<User> users = new ArrayList<>();
    public boolean emailVerification(User user){
        String usersEmail = user.getEmail();
        String usersEmailDomain = "@acme.com";
        int i = 1;
        while(usersEmail.charAt(i) != '@'){
            i++;
            if(i == usersEmail.length()-1){
                return false;
            }
        }
        return usersEmail.substring(i).equals(usersEmailDomain);
    }
}
