package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

public class PassengerNotFoundByEmailException extends RuntimeException {
    public PassengerNotFoundByEmailException(String email) {
        super(String.format("Passenger with email: %s not found", email));
    }
}
