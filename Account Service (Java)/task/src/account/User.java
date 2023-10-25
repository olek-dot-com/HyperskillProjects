package account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("email")
    private String email;
    private String password;

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getUserInfo(){
        return String.format("{\n" +
                "\"name\": \"%s\",\n" +
                "\"lastname\": \"%s\",\n" +
                "\"email\": \"%s\"\n" +
                "}", getName(), getLastName() ,getEmail());
    }
    public boolean isPasswordSet(){
        if (this.password == null || this.password.isEmpty()){
            return false;
        }
        return true;
    }
}
