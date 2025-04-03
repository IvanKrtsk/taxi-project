package by.ikrotsyuk.bsuir.driverservice.service.validation;

public interface DriverServiceValidationManager {
    void checkEmailIsUnique(String email);
    void checkPhoneIsUnique(String phone);
}
