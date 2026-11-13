package com.example.td2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

import java.io.IOException;
import java.util.Random;

public class ControllerEnJeu {

    private String currentPlayer;
    private String nomJoueur1;
    private String nomJoueur2;
    private String symbolePlayer1 = "X";
    private String symbolePlayer2 = "O";
    public int nbTourJeu = 0;
    private String difficulty;
    private boolean isPlayer1Starting = true;
    private final GestionDesScores gestionDesScores = new GestionDesScores();
    private boolean isJ2AI = false;
    private String lastPlayer;

    @FXML
    private Button button00;

    @FXML
    private Button button01;

    @FXML
    private Button button02;

    @FXML
    private Button button10;

    @FXML
    private Button button11;

    @FXML
    private Button button12;

    @FXML
    private Button button20;

    @FXML
    private Button button21;

    @FXML
    private Button button22;

    @FXML
    private Label labelPlayer1;

    @FXML
    private Label labelPlayer2;

    @FXML
    private Label labelTourPlayer;

    public void updatePlayerLabels(String name1, String name2) {
        labelPlayer1.setText(name1);
        labelPlayer2.setText(name2);

    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        if (labelTourPlayer != null) {
            labelTourPlayer.setText("C'est au tour de " + currentPlayer);
        }
    }

    public void setNomJoueur1(String nomJoueur1) {

        this.nomJoueur1 = nomJoueur1;
    }

    public void setNomJoueur2(String nomJoueur2) {

        this.nomJoueur2 = nomJoueur2;
    }

    public void setDifficulty(String difficulty) {

        this.difficulty = difficulty;
    }

    public void setJ2AsAI(boolean value) {

        this.isJ2AI = value;
    }

    public void setIsPlayer1Starting(boolean isPlayer1Starting) {
        this.isPlayer1Starting = isPlayer1Starting;

    }


    public void startGame() {
        currentPlayer = isPlayer1Starting ? nomJoueur1 : nomJoueur2;
        labelTourPlayer.setText("C'est au tour de : " + currentPlayer);
        if (isJ2AI && currentPlayer.equals(nomJoueur2)) {
            nbTourJeu++;
            AIplay();
        }
    }


    @FXML
    void boutonClicked(ActionEvent event) {
        Button button = (Button) event.getSource();

        if (button.getText().isEmpty() && currentPlayer != null) {
            // Sauvegarde du joueur qui joue ce tour
            lastPlayer = currentPlayer;

            if (currentPlayer.equals(nomJoueur1)) {
                button.setText(symbolePlayer1);
                button.getStyleClass().add(symbolePlayer1);
                currentPlayer = nomJoueur2;
            } else {
                button.setText(symbolePlayer2);
                button.getStyleClass().add(symbolePlayer2);
                currentPlayer = nomJoueur1;
            }

            nbTourJeu++;

            // Vérifie s’il y a un gagnant
            if (checkWinner()) {
                return;
            }

            // Vérifie le match nul
            if (nbTourJeu == 9) {
                labelTourPlayer.setText("C'est un match nul !");
                return;
            }

            // Mise à jour de l’affichage
            labelTourPlayer.setText("C'est au tour de : " + currentPlayer);

            // Si c’est une IA qui doit jouer
            if (isJ2AI && currentPlayer.equals(nomJoueur2)) {
                AIplay();
            }
        }
    }


    private void highlightWinningButtons(Button... winningButtons) {
        for (int i = 0; i < winningButtons.length; i++) {
            Button btn = winningButtons[i];
            PauseTransition delay = new PauseTransition(Duration.millis(300 * i));

            delay.setOnFinished(e -> {
                btn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
            });

            delay.play();
        }
    }

    private boolean checkWinner() {
        // Lignes
        if (isWinningLine(button00, button01, button02)) {
            highlightWinningButtons(button00, button01, button02);
            updateScores();
            return true;
        }
        if (isWinningLine(button10, button11, button12)) {
            highlightWinningButtons(button10, button11, button12);
            updateScores();
            return true;
        }
        if (isWinningLine(button20, button21, button22)) {
            highlightWinningButtons(button20, button21, button22);
            updateScores();
            return true;
        }

        // Colonnes
        if (isWinningLine(button00, button10, button20)) {
            highlightWinningButtons(button00, button10, button20);
            updateScores();
            return true;
        }
        if (isWinningLine(button01, button11, button21)) {
            highlightWinningButtons(button01, button11, button21);
            updateScores();
            return true;
        }
        if (isWinningLine(button02, button12, button22)) {
            highlightWinningButtons(button02, button12, button22);
            updateScores();
            return true;
        }

        // Diagonales
        if (isWinningLine(button00, button11, button22)) {
            highlightWinningButtons(button00, button11, button22);
            updateScores();
            return true;
        }
        if (isWinningLine(button02, button11, button20)) {
            highlightWinningButtons(button02, button11, button20);
            updateScores();
            return true;
        }

        // Si la partie est terminée sans gagnant
        if (nbTourJeu == 9) {
            showEndGamePopup("C'est un match nul !");
        }

        return false;
    }

    private boolean isWinningLine(Button b1, Button b2, Button b3) {
        return !b1.getText().isEmpty() && b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText());
    }

    private void updateScores() {
        if (isJ2AI) {
            if (lastPlayer.equals(nomJoueur2)) {
                gestionDesScores.ajoutVictoire(nomJoueur2); // L'IA gagne
                showEndGamePopup("Le bot " + difficulty + " a gagné !");
            } else if (lastPlayer.equals(nomJoueur1)) {
                gestionDesScores.ajoutVictoire(nomJoueur1); // Le joueur gagne
                showEndGamePopup("Le joueur " + nomJoueur1 + " a gagné !");
            }
        } else {
            if (lastPlayer.equals(nomJoueur1)) {
                gestionDesScores.ajoutVictoire(nomJoueur1);
            } else {
                gestionDesScores.ajoutVictoire(nomJoueur2);
            }
            String winner = lastPlayer;
            showEndGamePopup("Le joueur " + winner + " a gagné !");
        }
    }


    private void AIplay() {
        if (difficulty.equals("Normal")) {
            playNormalDifficulty();
        } else if (difficulty.equals("Difficile")) {
            playStrategicalDifficulty();
        }
    }

    private void playNormalDifficulty() {
        Button[] ButtonTab = {button00, button01, button02, button10, button11, button12, button20, button21, button22};
        Random rand = new Random();

        for (Button b : ButtonTab) {
            Button randomButton = ButtonTab[rand.nextInt(ButtonTab.length)];
            if (randomButton.getText().isEmpty()) {
                randomButton.setText(symbolePlayer2);
                randomButton.getStyleClass().add(symbolePlayer2);
                break;
            }
        }
        finishAITurn();
    }

    private void playStrategicalDifficulty() {
        Button[][] grid = {
                {button00, button01, button02},
                {button10, button11, button12},
                {button20, button21, button22}
        };

        if (tryWinningMove(grid)) return;
        if (tryBlockingMove(grid)) return;

        if (button11.getText().isEmpty()) {
            button11.setText(symbolePlayer2);
            button11.getStyleClass().add(symbolePlayer2);
            finishAITurn();
            return;
        }

        Button[] corners = {button00, button02, button20, button22};
        for (Button corner : corners) {
            if (corner.getText().isEmpty()) {
                corner.setText(symbolePlayer2);
                corner.getStyleClass().add(symbolePlayer2);
                finishAITurn();
                return;
            }
        }

        Button[] sides = {button01, button10, button12, button21};
        for (Button side : sides) {
            if (side.getText().isEmpty()) {
                side.setText(symbolePlayer2);
                side.getStyleClass().add(symbolePlayer2);
                finishAITurn();
                return;
            }
        }
    }

    private boolean tryWinningMove(Button[][] grid) {
        return tryCompletingLine(grid, symbolePlayer2);
    }

    private boolean tryBlockingMove(Button[][] grid) {
        return tryCompletingLine(grid, symbolePlayer1);
    }

    private boolean tryCompletingLine(Button[][] grid, String symbole) {
        for (int i = 0; i < 3; i++) {
            if (checkAndPlay(grid[i][0], grid[i][1], grid[i][2], symbole)) return true;
            if (checkAndPlay(grid[0][i], grid[1][i], grid[2][i], symbole)) return true;
        }
        if (checkAndPlay(grid[0][0], grid[1][1], grid[2][2], symbole)) return true;
        if (checkAndPlay(grid[0][2], grid[1][1], grid[2][0], symbole)) return true;

        return false;
    }

    private boolean checkAndPlay(Button b1, Button b2, Button b3, String symbole) {
        Button[] buttons = {b1, b2, b3};
        int count = 0;
        Button empty = null;

        for (Button b : buttons) {
            if (b.getText().equals(symbole)) {
                count++;
            } else if (b.getText().isEmpty()) {
                empty = b;
            }
        }

        if (count == 2 && empty != null) {
            empty.setText(symbolePlayer2);
            empty.getStyleClass().add(symbolePlayer2);
            finishAITurn();
            return true;
        }

        return false;
    }

    private void finishAITurn() {
        lastPlayer = nomJoueur2;
        nbTourJeu++;

        if (checkWinner()) {
            if (isJ2AI && lastPlayer.equals(nomJoueur2)) {
                labelTourPlayer.setText("Le bot " + difficulty + " a gagné !");
            } else {
                labelTourPlayer.setText("Le joueur " + (lastPlayer.equals(nomJoueur1) ? nomJoueur1 : nomJoueur2) + " a gagné !");
            }
        } else if (nbTourJeu == 9) {
            labelTourPlayer.setText("C'est un match nul !");
        } else {
            currentPlayer = nomJoueur1;
            labelTourPlayer.setText("C'est au tour de : " + currentPlayer);
        }
    }


    @FXML
    private void reinitialiserPartie() {
        nbTourJeu = 0;

        // Réinitialise tous les boutons
        Button[] buttons = {
                button00, button01, button02,
                button10, button11, button12,
                button20, button21, button22
        };

        for (Button btn : buttons) {
            btn.setText("");
            btn.getStyleClass().removeAll(symbolePlayer1, symbolePlayer2);
            btn.setStyle("");
            btn.setDisable(false);
        }

        // Réinitialise le joueur courant selon qui commence
        currentPlayer = isPlayer1Starting ? nomJoueur1 : nomJoueur2;
        labelTourPlayer.setText("C'est au tour de : " + currentPlayer);

        if (isJ2AI && currentPlayer.equals(nomJoueur2)) {
            AIplay();
        }
    }


    @FXML
    void quitterPartie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderMenuJeu = new FXMLLoader(Morpion.class.getResource("Morpion1.fxml"));
        Scene sceneMenuJeu = new Scene(fxmlLoaderMenuJeu.load());
        Button btn1 = (Button) event.getSource();
        Stage stg1 = (Stage) btn1.getScene().getWindow();
        stg1.setScene(sceneMenuJeu);
    }


    @FXML
    void regles(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Morpion.class.getResource("regles.fxml"));
            VBox root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage newFenetre = new Stage();
            newFenetre.initModality(Modality.APPLICATION_MODAL);
            Stage parentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            newFenetre.initOwner(parentStage);
            newFenetre.setTitle("Règles du jeu");
            newFenetre.setScene(scene);
            newFenetre.setResizable(false);

            newFenetre.setOnShown(e -> {
                newFenetre.setX(parentStage.getX() + (parentStage.getWidth() - newFenetre.getWidth()) / 2);
                newFenetre.setY(parentStage.getY() + (parentStage.getHeight() - newFenetre.getHeight()) / 2);
            });

            newFenetre.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AfficheScore(ActionEvent event) {
        Stage parentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FenetreScores.afficher(parentStage, gestionDesScores, nomJoueur1, nomJoueur2);
    }

    private String buttonStyle() {
        return """
                -fx-background-color: linear-gradient(to right, #252538, #343454);
                -fx-text-fill: white;
                -fx-border-color: #5555ff;
                -fx-border-width: 2px;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-font-size: 14px;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, #5555ffaa, 8, 0.4, 0, 0);
                """;
    }

    private void showEndGamePopup(String message) {
        Stage fenetreScores = new Stage();
        fenetreScores.initModality(Modality.APPLICATION_MODAL);
        fenetreScores.initOwner((Stage) labelTourPlayer.getScene().getWindow());
        fenetreScores.initStyle(StageStyle.TRANSPARENT);

        Label endGameLabel = new Label(message);
        endGameLabel.setFont(new Font("Arial", 16));
        endGameLabel.setTextFill(Color.WHITE);
        endGameLabel.setStyle("-fx-font-weight: bold;");

        Button replayButton = new Button("Rejouer");
        replayButton.setStyle(buttonStyle());
        replayButton.setOnAction(e -> {
            reinitialiserPartie();
            fenetreScores.close();
        });

        Button menuButton = new Button("Menu");
        menuButton.setStyle(buttonStyle());
        menuButton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Morpion.class.getResource("MenuJouer.fxml"));
                Scene menuScene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) labelTourPlayer.getScene().getWindow();
                currentStage.setScene(menuScene);
                fenetreScores.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button scoreButton = new Button("Scores");
        scoreButton.setStyle(buttonStyle());
        scoreButton.setOnAction(e -> {
            FenetreScores.afficher((Stage) labelTourPlayer.getScene().getWindow(), gestionDesScores, nomJoueur1, nomJoueur2);
        });

        HBox buttonBox = new HBox(10, replayButton, menuButton, scoreButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, endGameLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));
        layout.setStyle("""
                    -fx-background-color: rgba(0, 0, 0, 0.8);
                    -fx-background-radius: 15;
                    -fx-border-radius: 15;
                """);

        Scene scene = new Scene(layout, 320, 140);
        scene.setFill(Color.TRANSPARENT);
        fenetreScores.setScene(scene);

        Stage owner = (Stage) labelTourPlayer.getScene().getWindow();
        fenetreScores.setX(owner.getX() + (owner.getWidth() - 320) / 2);
        fenetreScores.setY(owner.getY() + (owner.getHeight() - 140) / 2);

        fenetreScores.showAndWait();
    }

}