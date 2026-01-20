/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Logic.MazeData;
import Model.Algorithm;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import Model.Maze;
import Model.Node;
import java.util.ArrayList;
import java.util.Collections;
import solver.BFS;
import solver.DFS;
import solver.BranchAndBoundSolver;
import java.util.List;
import javafx.event.ActionEvent;

/**
 *
 * @author Julian
 */
public class GameController {

    @FXML
    private GridPane gameGrid;

    private MazeData mazeData;

    private Algorithm currentAlgorithm = Algorithm.BFS;

    private Rectangle[][] cells;
    
    @FXML
    public void handleSolve(ActionEvent event) {
        solveAndAnimate();
    }

    public void setMaze(MazeData maze) {
        this.mazeData = maze;
        drawMaze(maze.getMatrix());
    }

    private void drawMaze(int[][] m) {

        gameGrid.getChildren().clear();
        cells = new Rectangle[m.length][m[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {

                Rectangle cell = new Rectangle(40, 40);

                switch (m[i][j]) {
                    case -1 ->
                        cell.setFill(Color.GRAY);
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
//SUENOOOOOOO AAAAAAAAAAAAA

    private Maze buildMaze() {
        return new Maze(mazeData.getMatrix());
    }

    private int[] findCell(int value) {
        int[][] m = mazeData.getMatrix();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == value) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    //SOLVERS
    private List<Node> solveWithBFS() {

        Maze maze = buildMaze();

        int[] start = findCell(1);
        int[] goal = findCell(2);

        Node root = new Node(
                start[0],
                start[1],
                0, // cost
                100, // vida inicial
                null
        );

        BFS bfs = new BFS(maze);
        Node goalNode = bfs.solve(root, goal[0], goal[1]);

        if (goalNode == null) {
            System.out.println("❌ No hay solución");
            return null;
        }

        return bfs.getPath(goalNode);
    }

    private List<Node> solveWithDFS() {

        Maze maze = buildMaze();

        int[] start = findCell(1);
        int[] goal = findCell(2);

        Node root = new Node(
                start[0],
                start[1],
                0,
                100,
                null
        );

        DFS dfs = new DFS(maze, goal[0], goal[1]);
        Node goalNode = dfs.solve(root);

        if (goalNode == null) {
            System.out.println("❌ DFS: no hay solución");
            return null;
        }

        return dfs.getPath(goalNode);
    }

    private List<Node> solveWithBranchAndBound() {

        Maze maze = buildMaze();

        int[] start = findCell(1);
        int[] goal = findCell(2);

        Node root = new Node(
                start[0],
                start[1],
                0,
                100,
                null
        );

        BranchAndBoundSolver solver
                = new BranchAndBoundSolver(maze, goal[0], goal[1]);

        Node goalNode = solver.solve(root);

        if (goalNode == null) {
            System.out.println("❌ B&B: no hay solución");
            return null;
        }

        return buildPathFromNode(goalNode);
    }

    private List<Node> buildPathFromNode(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    //PATH
    private int[][] applyPathToMatrix(List<Node> path) {

        int[][] original = mazeData.getMatrix();
        int[][] visual = new int[original.length][original[0].length];

        // copiar
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, visual[i], 0, original[i].length);
        }

        // marcar camino
        for (Node n : path) {
            if (visual[n.x][n.y] == 0) { // no pintar start ni goal
                visual[n.x][n.y] = 9;
            }
        }

        return visual;
    }

    private List<Node> solve() {
        return switch (currentAlgorithm) {
            case BFS ->
                solveWithBFS();
            case DFS ->
                solveWithDFS();
            case BRANCH_AND_BOUND ->
                solveWithBranchAndBound();
        };
    }

    public void solveAndShow() {
        List<Node> path = solve();
        if (path == null) {
            return;
        }

        int[][] visualMaze = applyPathToMatrix(path);
        drawMaze(visualMaze);
    }

    //PAINT
    private void animatePath(List<Node> path) {

        Timeline timeline = new Timeline();
        Duration frameGap = Duration.millis(300);

        for (int i = 0; i < path.size(); i++) {

            Node node = path.get(i);
            int step = i;

            KeyFrame frame = new KeyFrame(
                    frameGap.multiply(step),
                    e -> paintStep(node, step, path)
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }

    private void paintStep(Node node, int index, List<Node> path) {

        int x = node.x;
        int y = node.y;

        // pintar camino recorrido
        if (index > 0) {
            Node prev = path.get(index - 1);
            cells[prev.x][prev.y].setFill(Color.LIGHTBLUE);
        }

        // pintar posición actual IA
        cells[x][y].setFill(Color.DODGERBLUE);
    }

    public void solveAndAnimate() {

        List<Node> path = solve();

        if (path == null || path.isEmpty()) {
            System.out.println("❌ No hay camino para animar");
            return;
        }

        // Redibujamos el laberinto limpio
        drawMaze(mazeData.getMatrix());

        animatePath(path);
    }

}
