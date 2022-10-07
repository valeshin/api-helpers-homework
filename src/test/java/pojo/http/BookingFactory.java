package pojo.http;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BookingFactory {

    public Booking create() {
        Random rand = new Random();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Bookingdates bookingdates = Bookingdates.builder()
                .checkin(LocalDate.parse(LocalDate.now().toString(), dtf).toString())
                .checkout(LocalDate.parse(LocalDate.now().plusDays(rand.nextInt(365)).toString(), dtf).toString())
                .build();

        return Booking.builder()
                .firstname("Valerii")
                .lastname("Pupkin")
                .totalprice(rand.nextInt(100000))
                .depositpaid(rand.nextBoolean())
                .bookingdates(bookingdates)
                .additionalneeds("Otus")
                .build();
    }
}
