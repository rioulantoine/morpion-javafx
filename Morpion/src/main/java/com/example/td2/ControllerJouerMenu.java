package com.example.td2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ControllerJouerMenu {
    private ControllerEnJeu newControllerEnJeu;

    @FXML
    private ToggleGroup QuiCommence;

    @FXML
    private TextField textFieldPlayer1;

    @FXML
    private TextField textFieldPlayer2;

    @FXML
    private Button HardDifficulty;

    @FXML
    private RadioButton RadioBtnAleatoire;

    @FXML
    private RadioButton RadioBtnJUn;

    @FXML
    private RadioButton RadioBtnJDeux;

    @FXML
    private Button NormalDifficulty;

    private String Difficulty = "Normale";
    private int NbPlayer;

    @FXML
    void OnePlayer(ActionEvent event) {
        HardDifficulty.setDisable(false);
        NormalDifficulty.setDisable(false);
        textFieldPlayer2.setDisable(true);
        NbPlayer = 1;
    }

    @FXML
    void SelecDifficile(ActionEvent event) {

        Difficulty = "Difficile";
    }

    @FXML
    void SelecNormal(ActionEvent event) {

        Difficulty = "Normal";
    }

    @FXML
    void TwoPlayer(ActionEvent event) {
        textFieldPlayer2.setDisable(false);
        HardDifficulty.setDisable(true);
        NormalDifficulty.setDisable(true);
        NbPlayer = 2;
    }

    @FXML
    void PrecStage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderMenuJeu = new FXMLLoader(Morpion.class.getResource("Morpion1.fxml"));
        Scene sceneMenuJeu = new Scene(fxmlLoaderMenuJeu.load());
        Button btn1 = (Button) event.getSource();
        Stage stg1 = (Stage) btn1.getScene().getWindow();
        stg1.setScene(sceneMenuJeu);
    }

    @FXML
    void LancementPartie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderMenuJeu = new FXMLLoader(Morpion.class.getResource("JeuMorpion.fxml"));
        Scene sceneJeu = new Scene(fxmlLoaderMenuJeu.load());
        newControllerEnJeu = fxmlLoaderMenuJeu.getController();

        String name1 = textFieldPlayer1.getText();
        String name2 = NbPlayer == 1 ? "Bot" : textFieldPlayer2.getText();

        newControllerEnJeu.setNomJoueur1(name1);
        newControllerEnJeu.setNomJoueur2(name2);
        newControllerEnJeu.updatePlayerLabels(name1, name2);
        newControllerEnJeu.setDifficulty(Difficulty);

        RadioButton selected = (RadioButton) QuiCommence.getSelectedToggle();
        String currentPlayer;

        if (NbPlayer == 1) {
            if (selected == RadioBtnJUn) {
                currentPlayer = name1;
            } else if (selected == RadioBtnJDeux) {
                currentPlayer = name2;
            } else {
                boolean alea = new Random().nextBoolean();
                currentPlayer = alea ? name1 : name2;
            }
            newControllerEnJeu.setJ2AsAI(true);
        } else if (selected == RadioBtnJUn) {
            currentPlayer = name1;
        } else if (selected == RadioBtnJDeux) {
            currentPlayer = name2;
        } else {
            boolean alea = new Random().nextBoolean();
            currentPlayer = alea ? name1 : name2;
        }
        if (currentPlayer.equals(name1)) {
            newControllerEnJeu.setIsPlayer1Starting(true);
        } else {
            newControllerEnJeu.setIsPlayer1Starting(false);
        }
        newControllerEnJeu.startGame();

        Button btn1 = (Button) event.getSource();
        Stage stg1 = (Stage) btn1.getScene().getWindow();
        stg1.setScene(sceneJeu);
    }

}