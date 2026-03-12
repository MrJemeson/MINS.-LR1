package service;

import java.util.List;

public interface OutputService {
    void displayMainMenu();
    void displayList(List<?> list);
    void displayWrongInputMessage();
    void displayUserNameInputMessage();
    void displayExceptionMessage(String exceptionMessage);
    void displayBookSearchInputMessage();
    void displayPositionNumber();
}
