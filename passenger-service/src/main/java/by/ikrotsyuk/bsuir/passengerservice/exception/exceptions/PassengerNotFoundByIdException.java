package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

public class PassengerNotFoundByIdException extends RuntimeException {
    public PassengerNotFoundByIdException(Long id) {
        super(String.format("Passenger with id: %d not found", id));
    }
}
