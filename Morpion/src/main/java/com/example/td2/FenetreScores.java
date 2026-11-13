package com.example.td2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class FenetreScores {

    public static void afficher(Stage parentStage, GestionDesScores gestion, String joueurA, String joueurB) {
        Stage fenetreDesScores = new Stage();
        fenetreDesScores.initOwner(parentStage);
        fenetreDesScores.initStyle(StageStyle.UNDECORATED);

        // Titre
        Label titre = new Label("Tableau des Scores");
        titre.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 32px; " +
                "-fx-font-family: 'Segoe UI Black', 'Arial Black', sans-serif; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, #00ffff88, 12, 0.6, 0, 2); " +
                "-fx-alignment: center; " +
                "-fx-padding: 30 0 10 0;");

        // Scores
        Label scoreA = new Label(joueurA + " : " + gestion.getScore(joueurA));
        Label scoreB = new Label(joueurB + " : " + gestion.getScore(joueurB));
        String style = "-fx-text-fill: #dddddd;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family: 'Segoe UI Semibold';";
        scoreA.setStyle(style);
        scoreB.setStyle(style);


        // Bouton pour fermer
        Button fermer = new Button("Fermer");
        fermer.setStyle(
                "-fx-background-color: linear-gradient(to right, #252538, #343454);" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #5555ff;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-font-size: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, #5555ffaa, 8, 0.4, 0, 0);"
        );
        fermer.setOnAction(e -> fenetreDesScores.close());

        // Layout des scores
        GridPane scoreGrid = new GridPane();
        scoreGrid.setAlignment(Pos.CENTER);
        scoreGrid.setHgap(15);
        scoreGrid.setVgap(10);
        scoreGrid.add(scoreA, 0, 0);
        scoreGrid.add(scoreB, 0, 1);

        BorderPane racine = new BorderPane();
        racine.setStyle("-fx-background-color: #1e1e2f;");
        racine.setTop(titre);
        BorderPane.setAlignment(titre, Pos.CENTER);
        racine.setCenter(scoreGrid);
        StackPane boutonZone = new StackPane(fermer);
        boutonZone.setPadding(new Insets(15));
        racine.setBottom(boutonZone);

        Scene scene = new Scene(racine, 400, 250);

        fenetreDesScores.setScene(scene);

        // Position sur l'écran
        fenetreDesScores.setOnShown(e -> {
            fenetreDesScores.setX(parentStage.getX() + (parentStage.getWidth() - fenetreDesScores.getWidth()) / 2);
            fenetreDesScores.setY(parentStage.getY() + (parentStage.getHeight() - fenetreDesScores.getHeight()) / 2);
        });

        fenetreDesScores.showAndWait();
    }
}
