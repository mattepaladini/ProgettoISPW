package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.HelloApplication;
import com.example.progettoispw.exception.BaseException;

public class ErrorHandler {
    public static void show(BaseException e) {
        if (HelloApplication.isCLI()) {
            System.out.println("Error: " + e.getMessage());
        } else {
            ToastManager.showErrorToast(HelloApplication.getPrimaryStage(), e.getMessage());
        }
    }
}
