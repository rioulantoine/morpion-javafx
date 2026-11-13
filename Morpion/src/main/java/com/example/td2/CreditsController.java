package com.example.td2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditsController {

    @FXML
    void PrecPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderMenuJeu = new FXMLLoader(Morpion.class.getResource("Morpion1.fxml"));
        Scene sceneMenuJeu = new Scene(fxmlLoaderMenuJeu.load());
        Button btn1 = (Button) event.getSource();
        Stage stg1 = (Stage) btn1.getScene().getWindow();
        stg1.setScene(sceneMenuJeu);
    }
}
