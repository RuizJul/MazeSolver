/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Logic.MazeData;
import Model.Maze;
import Model.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import solver.BFS;
import solver.DFS;
import solver.BranchAndBoundSolver;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    /* ================= FXML ================= */
    @FXML
    private GridPane gameGrid;
    @FXML
    private Label lifeLabel;
    @FXML
    private ComboBox<String> solverSelector;

    /* ================= MODEL ================= */
    private MazeData mazeData;
    private Maze mazeModel;
    private Node startNode;
    private Node goalNode;

    /* ================= UI ================= */
    private Rectangle[][] cells;
    private static final int CELL_SIZE = 40;
    private int currentLife = 10;

    /* ================= INIT ================= */
    @FXML
    public void initialize() {
        solverSelector.getItems().addAll(
                "BFS",
                "DFS",
                "Branch and Bound"
        );

        // Resolver automáticamente al cambiar algoritmo
        solverSelector.setOnAction(e -> solveAndAnimate());

        // ESC global (Scene-level, correcto)
        gameGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        handleBack();
                    }
                });
            }
        });
    }

    /* ================= SET MAZE ================= */
    public void setMaze(MazeData maze) {
        this.mazeData = maze;
        this.mazeModel = convertToMaze(maze);
        locateStartAndGoal();
        drawMaze(maze.getMatrix());
        updateLife(10);
    }

    /* ================= CONVERSION ================= */
    private Maze convertToMaze(MazeData data) {
        int[][] m = data.getMatrix();
        Maze maze = new Maze(m); // ✔️ constructor correcto
        return maze;
    }

    private void locateStartAndGoal() {
        int[][] m = mazeData.getMatrix();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == 1) {
                    startNode = new Node(i, j, 0, 10, null);
                }
                if (m[i][j] == 2) {
                    goalNode = new Node(i, j, 0, 10, null);
                }
            }
        }
    }

    /* ================= DRAW ================= */
    private void drawMaze(int[][] m) {
        gameGrid.getChildren().clear();
        cells = new Rectangle[m.length][m[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                switch (m[i][j]) {
                    case -1 ->
                        cell.setFill(Color.DARKGRAY);
                    case 0 ->
                        cell.setFill(Color.WHITE);
                    case 1 ->
                        cell.setFill(Color.LIGHTGREEN);
                    case 2 ->
                        cell.setFill(Color.TOMATO);
                    case 3 ->
                        cell.setFill(Color.ORANGE);
                }

                cell.setStroke(Color.BLACK);
                cells[i][j] = cell;
                gameGrid.add(cell, j, i);
            }
        }
    }

    private void resetGameView() {
        drawMaze(mazeData.getMatrix());
        updateLife(100);
    }

    /* ================= SOLVE ================= */
    private void solveAndAnimate() {
        if (mazeModel == null || startNode == null || goalNode == null) {
            return;
        }
        resetGameView();
        
        List<Node> path;

        String algo = solverSelector.getValue();

        switch (algo) {
            case "DFS" -> {
                DFS dfs = new DFS(mazeModel, goalNode.x, goalNode.y);
                Node sol = dfs.solve(startNode);
                path = dfs.getPath(sol);
            }
            case "Branch and Bound" -> {
                BranchAndBoundSolver bb = new BranchAndBoundSolver(
                        mazeModel, goalNode.x, goalNode.y
                );
                Node sol = bb.solve(startNode);
                path = reconstructPath(sol);
            }
            default -> {
                BFS bfs = new BFS(mazeModel);
                Node sol = bfs.solve(startNode, goalNode.x, goalNode.y);
                path = bfs.getPath(sol);
            }
        }

        animatePath(path);
    }

    private List<Node> reconstructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        java.util.Collections.reverse(path);
        return path;
    }

    /* ================= ANIMATION ================= */
    private void animatePath(List<Node> path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        Timeline timeline = new Timeline();
        int step = 0;

        for (Node node : path) {
            KeyFrame kf = new KeyFrame(
                    Duration.seconds(step * 0.3),
                    e -> {
                        highlightCell(node.x, node.y, Color.DODGERBLUE);
                        updateLife(node.life);
                    }
            );
            timeline.getKeyFrames().add(kf);
            step++;
        }
        timeline.play();
    }

    private void highlightCell(int x, int y, Color color) {
        Rectangle cell = cells[x][y];
        if (cell != null && cell.getFill() != Color.DARKGRAY) {
            cell.setFill(color);
        }
    }

    /* ================= LIFE ================= */
    private void updateLife(int life) {
        currentLife = life;
        lifeLabel.setText(String.valueOf(life));
    }

    /* ================= BACK ================= */
    @FXML
    private void handleBack() {
        changeView("Selector.fxml");
    }

    private void changeView(String fxml) {
        try {
            Stage stage = (Stage) gameGrid.getScene().getWindow();
            Scene scene = new Scene(
                    javafx.fxml.FXMLLoader.load(
                            getClass().getResource("/Views/" + fxml)
                    )
            );
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
