/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.CellType;
import java.io.File;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.MazeStorage;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
/**
 *
 * @author Julian
 */
public class MazeEditorController {

    @FXML
    private GridPane grid;

    private static final int ROWS = 10;
    private static final int COLS = 10;

    private CellType[][] mazeMatrix;

    @FXML
    public void initialize() {
        mazeMatrix = new CellType[ROWS][COLS];
        buildGrid();
        
        Platform.runLater(this::enableEsc);
    }

    private void enableEsc() {
        grid.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                changeView("Menu.fxml");
            }
        });
    }

    private void buildGrid() {
        grid.getChildren().clear();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                Pane cell = new Pane();
                cell.setPrefSize(40, 40);

                mazeMatrix[row][col] = CellType.WALL;
                paintCell(cell, CellType.WALL);

                final int r = row;
                final int c = col;

                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    rotateCell(r, c, cell);
                });

                grid.add(cell, col, row);
            }
        }
    }

    private void rotateCell(int row, int col, Pane cell) {
        CellType current = mazeMatrix[row][col];
        CellType next = nextType(current);

        mazeMatrix[row][col] = next;
        paintCell(cell, next);
    }

    /**
     * Define el orden de rotación de las casillas
     */
    private CellType nextType(CellType current) {
        switch (current) {
            case WALL:
                return CellType.EMPTY;
            case EMPTY:
                return CellType.TRAP;
            case TRAP:
                return CellType.START;
            case START:
                return CellType.GOAL;
            case GOAL:
            default:
                return CellType.WALL;
        }
    }

    /**
     * Pinta la celda según su tipo
     */
    private void paintCell(Pane cell, CellType type) {

        switch (type) {

            case EMPTY:
                // BLANCO → camino normal
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
                break;

            case WALL:
                // GRIS → muro
                cell.setStyle("-fx-border-color: black; -fx-background-color: gray;");
                break;

            case TRAP:
                // NARANJA → trampa (reduce vida)
                cell.setStyle("-fx-border-color: black; -fx-background-color: orange;");
                break;

            case START:
                // VERDE → inicio
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightgreen;");
                break;

            case GOAL:
                // ROJO → meta
                cell.setStyle("-fx-border-color: black; -fx-background-color: tomato;");
                break;
        }
    }

    private boolean validateStartAndGoal() {
        int startCount = 0;
        int goalCount = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mazeMatrix[i][j] == CellType.START) {
                    startCount++;
                }
                if (mazeMatrix[i][j] == CellType.GOAL) {
                    goalCount++;
                }
            }
        }

        if (startCount != 1 || goalCount != 1) {
            showError(
                    "El laberinto debe tener EXACTAMENTE:\n"
                    + "- 1 casilla START\n"
                    + "- 1 casilla GOAL"
            );
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error en el laberinto");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int[][] convertToIntMatrix() {
        int[][] matrix = new int[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                switch (mazeMatrix[i][j]) {
                    case WALL:
                        matrix[i][j] = -1;
                        break;
                    case EMPTY:
                        matrix[i][j] = 0;
                        break;
                    case START:
                        matrix[i][j] = 1;
                        break;
                    case GOAL:
                        matrix[i][j] = 2;
                        break;
                    case TRAP:
                        matrix[i][j] = 3;
                        break;
                }
            }
        }
        return matrix;
    }

    // =========================
    // SAVE
    // =========================
    @FXML
    private void handleSaveMaze() {
        if (!validateStartAndGoal()) {
            return;
        }

        saveMazeToFile(convertToIntMatrix());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("Laberinto guardado correctamente");
        alert.showAndWait();

        goBackToMenu();
        
    }

    private void saveMazeToFile(int[][] maze) {

        File dir = MazeStorage.getMazeDirectory();

        String fileName = "maze_" + System.currentTimeMillis() + ".txt";
        File file = new File(dir, fileName);

        try (PrintWriter writer = new PrintWriter(file)) {

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    writer.print(maze[i][j] + " ");
                }
                writer.println();
            }

        } catch (Exception e) {
            showError("Error al guardar el laberinto");
        }
    }

    private void goBackToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Views/Menu.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) grid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            showError("No se pudo volver al menú");
            e.printStackTrace();
        }
    }


    private void changeView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Views/" + fxml)
            );
            Parent root = loader.load();

            Stage stage = (Stage) grid.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
