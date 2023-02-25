package edu.app.api;


import java.lang.reflect.Array;

public class Handler {

    DB dataBase;


    public void getCommand(String input) {
        String[] arguments = input.split(" ");
        String command = arguments[0];

        switch (command) {
            case "adduser":
                break;

        }

    }
}
