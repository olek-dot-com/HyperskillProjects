import java.time.LocalDate;
import java.time.Month;

class Solution {    
    Product convertProductDTOToProduct(ProductDTO dto) {
        Product product = new Product(dto.getId(), dto.getModel(), dto.getPrice(), LocalDate.of(2023, Month.JANUARY, 15), "SuperVendor");
    return product;
    }
}