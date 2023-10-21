package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Token {
    @JsonProperty("token")
    private String token;
    public Token() {
        token = UUID.randomUUID().toString();
    }

    public String toString() {
        return token;
    }
}
