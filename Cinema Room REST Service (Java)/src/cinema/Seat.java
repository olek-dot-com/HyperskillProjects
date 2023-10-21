package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Seat {
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }
    private int price;
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonIgnore
    private String token = new Token().toString();
    public String getToken() {
        return token;
    }

    @JsonIgnore
    private boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }
    public Seat(){
    }
    public String bookSeat() {
setBusy(true);
return String.format("{\n" +
                "    \"token\": \"%s\",\n" +
                "    \"ticket\": {\n" +
                "        \"row\": %s,\n" +
                "        \"column\": %s,\n" +
                "        \"price\": %s\n" +
                "    }\n" +
                "}",
        getToken(), getRow(), getColumn(), getPrice());
    }
    public String returnSeat(){
        this.token = new Token().toString();
        setBusy(false);
        return String.format("{\n" +
                "    \"returned_ticket\": {\n" +
                "        \"row\": %s,\n" +
                "        \"column\": %s,\n" +
                "        \"price\": %s\n" +
                "    }\n" +
                "}", getRow(),getColumn(),getPrice());
    }
}
