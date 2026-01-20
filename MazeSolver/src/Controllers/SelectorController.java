/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author Julian
 */
import Logic.CircularMazeList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SelectorController {

    @FXML
    private Label mazeNameLabel;

    @FXML
    private GridPane previewGrid;

    private CircularMazeList mazeList;

    @FXML
    public void initialize() {
        mazeList = MazeLoader.loadMazes(); // los cargamos de archivos
        showCurrentMaze();
    }

    @FXML
    private void handleNext() {
        mazeList.next();
        showCurrentMaze();
    }

    @FXML
    private void handlePrev() {
        mazeList.prev();
        showCurrentMaze();
    }

    @FXML
    private void handlePlay() {
        MazeData selected = mazeList.getCurrent();
        GameState.setMaze(selected);
        goToGameView();
    }
}
