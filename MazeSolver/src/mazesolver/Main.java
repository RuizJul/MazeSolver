/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mazesolver;
import Model.*;
import MiniMax.*;
import solver.DFS;
import solver.BFS;

import java.util.List;

/**
 *
 * @author Julian
 */


public class Main {

    public static void main(String[] args) {

        // 1️⃣ Definir el laberinto
        char[][] grid = {
            {'#','#','#','#','#','#','#','#'},
            {'#','S','.','T','.','.','.','#'},
            {'#','.','#','.','#','T','.','#'},
            {'#','.','#','.','.','.','.','#'},
            {'#','T','.','#','.','T','G','#'},
            {'#','.','.','.','.','.','.','#'},
            {'#','#','#','#','#','#','#','#'}
        };

        Maze maze = new Maze(grid);

        // 2️⃣ Estado inicial
        int startX = 1;
        int startY = 1;
        int initialHealth = 100;

        Node root = new Node(startX, startY, initialHealth, 0, null);

        // 3️⃣ Construir árbol de estados
        int maxDepth = 15;
        TreeBuilder builder = new TreeBuilder(maze, maxDepth);
        builder.buildTree(root);

        // ================= DFS =================
        System.out.println("===== DFS SOLUTION =====");
        DFS dfs = new DFS(maze);
        Node dfsGoal = dfs.solve(root);

        if (dfsGoal != null) {
            List<Node> dfsPath = dfs.getPath(dfsGoal);
            printPath(dfsPath);
        } else {
            System.out.println("No solution found with DFS");
        }

        // ================= BFS =================
        System.out.println("\n===== BFS SOLUTION =====");
        BFS bfs = new BFS(maze);
        Node bfsGoal = bfs.solve(root);

        if (bfsGoal != null) {
            List<Node> bfsPath = bfs.getPath(bfsGoal);
            printPath(bfsPath);
        } else {
            System.out.println("No solution found with BFS");
        }
    }

    // Método auxiliar para imprimir un camino
    private static void printPath(List<Node> path) {
        System.out.println("Path length: " + (path.size() - 1));
        for (Node n : path) {
            System.out.println(
                "(x=" + n.getX() +
                ", y=" + n.getY() +
                ", hp=" + n.getHealth() + ")"
            );
        }
    }
}
