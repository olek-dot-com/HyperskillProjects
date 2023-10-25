import java.time.LocalDate;

class Product {
    private long id;
    private String model;
    private int price;
    private LocalDate dateOfArrival;
    private String vendor;

    public Product(long id, String model, int price, LocalDate dateOfArrival, String vendor) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.dateOfArrival = dateOfArrival;
        this.vendor = vendor;
    }

    public Product() {
    }
}