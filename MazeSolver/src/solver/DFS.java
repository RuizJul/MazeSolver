/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solver;

/**
 *
 * @author Julian
 */
import Model.Maze;
import Model.Node;

import java.util.*;

public class DFS {

    private Maze maze;
    private Set<String> visited;
    private Node solution;
    private int goalX, goalY;

    public DFS(Maze maze, int goalX, int goalY) {
        this.maze = maze;
        this.goalX = goalX;
        this.goalY = goalY;
        this.visited = new HashSet<>();
    }

    public Node solve(Node root) {
        dfs(root);
        return solution;
    }

    private void dfs(Node current) {

        if (solution != null) return;

        visited.add(key(current));

        if (current.x == goalX && current.y == goalY) {
            solution = current;
            return;
        }

        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int nx = current.x + dx[i];
            int ny = current.y + dy[i];

            if (!maze.isInside(nx, ny)) continue;
            if (maze.isWall(nx, ny)) continue;

            int cellCost = maze.cellCost(nx, ny);
            
            // la salida no consume vida
            if (nx == goalX && ny == goalY) {
                cellCost = 0;
            }
            
            int newLife = current.life - cellCost;
            int newCost = current.cost + 1;

            Node child = new Node(nx, ny, newCost, newLife, current);

            // ⚠️ DFS TAMPOCO valida vida
            if (newLife <= 0) {
                System.out.println("⚠️ DFS pasó por un estado MUERTO en (" + nx + "," + ny + ")");
            }

            String k = key(child);
            if (!visited.contains(k)) {
                dfs(child);
            }
        }
    }

    private String key(Node node) {
        return node.x + "," + node.y;
    }

    public List<Node> getPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }
}
