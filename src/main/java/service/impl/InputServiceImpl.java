package service.impl;

import service.InputService;

import java.util.Scanner;

public class InputServiceImpl implements InputService {
    private final Scanner scanner;

    public InputServiceImpl() {
       scanner = new Scanner(System.in);
    }

    @Override
    public String inputString() {
        String string;
        while(true) {
            if(scanner.hasNext()){
                string = scanner.next();
                break;
            }
        }
        return string;
    }

    @Override
    public int inputNumber() {
        int number;
        while(true) {
            if(scanner.hasNextInt()){
                number = scanner.nextInt();
                break;
            }
        }
        return number;
    }
}
