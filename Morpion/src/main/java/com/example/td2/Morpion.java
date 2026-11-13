package com.example.td2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;

public class Morpion extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Lancement
        FXMLLoader fxmlLoader = new FXMLLoader(Morpion.class.getResource("Morpion1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Morpion");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("TicTacToeTitle.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

