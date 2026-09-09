package BookingHomeStay.BookingHomeStay;

import BookingHomeStay.BookingHomeStay.config.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingHomeStayApplication {
    public static void main(String[] args) {
        DotenvLoader.load();
        SpringApplication.run(BookingHomeStayApplication.class, args);
    }
}