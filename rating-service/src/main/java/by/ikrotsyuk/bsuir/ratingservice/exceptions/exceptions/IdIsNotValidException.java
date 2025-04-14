package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class IdIsNotValidException extends ExceptionTemplate {
  public IdIsNotValidException(String id) {
    super(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), id);
  }
}
