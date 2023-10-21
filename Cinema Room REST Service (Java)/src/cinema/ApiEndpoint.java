package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.stream;


@RestController
public class ApiEndpoint {
    private final Cinema cinema = new Cinema();
    @GetMapping("/seats")
    public Cinema getCinema() {
        return cinema;
    }

    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> buySeat(@RequestBody Seat seat) {
       if(seat.getRow()>9 || seat.getRow() < 1 || seat.getColumn() < 1 || seat.getColumn() > 9) {
            String errorMessage = "{ \"error\": \"The number of a row or a column is out of bounds!\" }";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
      }
        Seat[] seats = cinema.getSeatsArray();
       if(seats[(seat.getRow()-1)*9 + (seat.getColumn()-1)].isBusy()){
           String errorMessage = "{ \"error\": \"The ticket has been already purchased!\" }";
           return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
       }
        cinema.purchasedSeats.add(seats[(seat.getRow()-1)*9 + (seat.getColumn()-1)].getPrice());
        return new ResponseEntity<>(seats[(seat.getRow()-1)*9 + (seat.getColumn()-1)].bookSeat(), HttpStatus.OK);
    }

    @PostMapping(path = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnTicket(@RequestBody Token token) {
        Optional <Seat> seat = cinema.findSeat(token);

        if(seat.isEmpty()){
            String response = "{ \n\"error\": \"Wrong token!\" \n}";
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        Seat s = seat.get();
        cinema.removeTicket(s.getPrice());
        return new ResponseEntity<>(s.returnSeat(), HttpStatus.OK);
    }
    @GetMapping(path = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnStats(@RequestParam(name = "password", required = false) String psswd) {
        try {
            if (psswd.equals(cinema.getPassword())) {
                return new ResponseEntity<>(cinema.getStats(), HttpStatus.OK);
            }
        } catch (NullPointerException e){

        }
            String message = "{\n\"error\": \"The password is wrong!\"\n}";
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);



    }
}


