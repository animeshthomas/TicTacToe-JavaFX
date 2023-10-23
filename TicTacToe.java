package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

public class TicTacToe extends Application {

    private char currentPlayer = 'X';
    private Button[][] board = new Button[3][3];
    private String playerXName = "Player X";
    private String playerOName = "Player O";

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialogX = new TextInputDialog("Player X");
        dialogX.setTitle("Player X Name");
        dialogX.setHeaderText(null);
        dialogX.setContentText("Enter Player X's Name:");
        Optional<String> resultX = dialogX.showAndWait();
        resultX.ifPresent(name -> playerXName = name);

        TextInputDialog dialogO = new TextInputDialog("Player O");
        dialogO.setTitle("Player O Name");
        dialogO.setHeaderText(null);
        dialogO.setContentText("Enter Player O's Name:");
        Optional<String> resultO = dialogO.showAndWait();
        resultO.ifPresent(name -> playerOName = name);

        GridPane grid = new GridPane();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = createCell();
                grid.add(cell, col, row);
                board[col][row] = cell;
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        scene.setFill(Color.BLACK); // Background color
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createCell() {
        Button cell = new Button();
        cell.setPrefSize(100, 100);
        cell.setStyle("-fx-font-size: 2em; -fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: black;");
        cell.setOnAction(event -> {
            if (cell.getText().isEmpty()) {
                cell.setText(String.valueOf(currentPlayer));
                if (currentPlayer == 'X') {
                    cell.setStyle("-fx-font-size: 2em; -fx-background-color: lightblue; -fx-text-fill: red; -fx-border-color: black;");
                } else {
                    cell.setStyle("-fx-font-size: 2em; -fx-background-color: lightblue; -fx-text-fill: green; -fx-border-color: black;");
                }
                if (checkForWin()) {
                    showWinDialog();
                    resetBoard();
                } else if (isBoardFull()) {
                    showDrawDialog();
                    resetBoard();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        });
        return cell;
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    board[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    board[i][2].getText().equals(String.valueOf(currentPlayer)) ||
                    board[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                            board[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                            board[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        if (board[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                board[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                board[2][2].getText().equals(String.valueOf(currentPlayer)) ||
                board[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                        board[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                        board[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (Button[] row : board) {
            for (Button cell : row) {
                if (cell.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tic-Tac-Toe");
        alert.setHeaderText(null);
        alert.setContentText(playerXName + " wins! Play again?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void showDrawDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tic-Tac-Toe");
        alert.setHeaderText(null);
        alert.setContentText("It's a draw! Play again?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void resetBoard() {
        currentPlayer = 'X';
        for (Button[] row : board) {
            for (Button cell : row) {
                cell.setText("");
                cell.setStyle("-fx-font-size: 2em; -fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: black;");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
