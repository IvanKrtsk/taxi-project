package by.ikrotsyuk.bsuir.driverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DriverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
    }

}

//         поменять в контроллере driver -> drivers, в post методе передавать через боди а не реквест парамы
