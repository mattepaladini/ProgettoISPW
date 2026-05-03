package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.HelloApplication;
import com.example.progettoispw.exception.BaseException;
import javafx.stage.Stage;

public class ErrorHandler {

    private static Stage owner;

    public static void setOwner(Stage stage) {
        owner = stage;
    }

    private ErrorHandler() {}

    public static void show(BaseException e) {
        if (HelloApplication.isCLI()) {
            System.out.println("Error: " + e.getMessage());
        } else {

            if(owner != null) {
                ToastManager.showErrorToast(owner, e.getMessage());
            }

        }
    }
}
