package by.ikrotsyuk.bsuir.passengerservice.service.validation;

public interface PassengerServiceValidationManager {
    void checkEmailIsUnique(String email);
    void checkPhoneIsUnique(String phone);
}
