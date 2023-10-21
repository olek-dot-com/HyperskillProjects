package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Cinema {
    @JsonProperty("total_rows")
    public int getRows() {
        return rows;
    }
@JsonProperty("total_columns")
    public int getColumns() {
        return columns;
    }
@JsonProperty("available_seats")
    public Seat[] getSeatsArray() {
        return seatsArray;
    }
    List<Integer> purchasedSeats = new ArrayList<>();
    private int rows;
    private int columns;

    private Seat[] seatsArray = new Seat[81];
    @JsonIgnore
    private final String password = "super_secret";

    public String getPassword() {
        return password;
    }

    public Cinema(){
        int price = 10;
        for (int i = 1; i<10; i++){
            for (int j = 1; j<10;j++){
                if(i>4){
                    price = 8;
                }
                Seat s = new Seat(i, j,price);
                seatsArray[(i-1)*9 + j-1] = s;
            }
        }
        rows = 9;
        columns = 9;
    }

    @JsonIgnore
    public Optional<Seat> findSeat(Token token) {
        return Arrays.stream(seatsArray)
                .filter(x -> x.getToken().equals(token.toString()))
                .findFirst();
    }
    @JsonIgnore
    public int currentIncome(){
        int income = 0;
        for (int e: this.purchasedSeats) {
            income += e;
        }
        return income;
    }
    @JsonIgnore
    public void removeTicket(int price) {
        for (int i = 0; i < purchasedSeats.size(); i++) {
            if (purchasedSeats.get(i) == price) {
                purchasedSeats.remove(i);
                break;
            }
        }
    }
    @JsonIgnore
    public int getNumberOfAvailableSeats(){
        return 81-this.purchasedSeats.size();
    }
    @JsonIgnore
    public int getNumberOfPurchasedSeats(){
        return this.purchasedSeats.size();
    }
    @JsonIgnore
    public String getStats() {
        return String.format("{\n \"current_income\": %s," +
                " \n \"number_of_available_seats\": %s," +
                " \n\"number_of_purchased_tickets\": %s \n}",currentIncome(),getNumberOfAvailableSeats(),getNumberOfPurchasedSeats());
    }

}
