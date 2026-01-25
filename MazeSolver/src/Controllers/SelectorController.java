/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Logic.CircularMazeList;
import Logic.MazeData;
import Logic.MazeLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
/**
 *
 * @author Julian
 */
public class SelectorController {

    @FXML
    private GridPane mazeGrid;

    private static final int CELL_SIZE = 40;

    private CircularMazeList mazes;

    @FXML
    public void initialize() {
        mazes = MazeLoader.loadMazes();

        if (mazes.isEmpty()) {
            System.out.println("⚠ No hay laberintos cargados");
            return;
        }

        drawMaze(mazes.getCurrent());
        
        Platform.runLater(this::enableEsc);
    }

    private void enableEsc() {
        mazeGrid.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                changeView("Menu.fxml");
            }
        });
    }

    private void drawMaze(MazeData maze) {
        mazeGrid.getChildren().clear();

        int[][] m = maze.getMatrix();

        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[row].length; col++) {

                Pane cell = new Pane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);

                paintCell(cell, m[row][col]);

                mazeGrid.add(cell, col, row);
            }
        }
    }

    private void paintCell(Pane cell, int value) {
        switch (value) {
            case -1: // WALL
                cell.setStyle("-fx-border-color: black; -fx-background-color: gray;");
                break;
            case 0: // EMPTY
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
                break;
            case 1: // START
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightgreen;");
                break;
            case 2: // GOAL
                cell.setStyle("-fx-border-color: black; -fx-background-color: tomato;");
                break;
            case 3: // TRAP
                cell.setStyle("-fx-border-color: black; -fx-background-color: orange;");
                break;
            default:
                cell.setStyle("-fx-border-color: black; -fx-background-color: black;");
        }
    }

    // 🔽 BOTONES 🔽
    @FXML
    private void nextMaze() {
        drawMaze(mazes.next());
    }

    @FXML
    private void prevMaze() {
        drawMaze(mazes.prev());
    }

    @FXML
    private void playMaze() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Views/Game.fxml")
            );

            Parent root = loader.load();

            GameController controller = loader.getController();

            controller.setMaze(mazes.getCurrent());
            System.out.println(mazeGrid);

            Stage stage = (Stage) mazeGrid.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void changeView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Views/" + fxml)
            );
            Parent root = loader.load();

            Stage stage = (Stage) mazeGrid.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
