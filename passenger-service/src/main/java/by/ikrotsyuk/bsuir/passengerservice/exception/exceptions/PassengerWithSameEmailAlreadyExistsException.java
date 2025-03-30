package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

public class PassengerWithSameEmailAlreadyExistsException extends RuntimeException {
    public PassengerWithSameEmailAlreadyExistsException(String email) {
        super(String.format("Passenger with email: %s already exists", email));
    }
}
