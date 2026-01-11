/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solver;

/**
 *
 * @author Julian
 */
import model.Maze;
import model.Node;
import java.util.ArrayList;
import java.util.List;

public class DFS {

    private Maze maze;
    private Node solution;

    public DFS(Maze maze) {
        this.maze = maze;
        this.solution = null;
    }

    public Node solve(Node root) {
        dfs(root);
        return solution;
    }

    private void dfs(Node node) {

        // Si ya encontramos solución, detener
        if (solution != null) return;

        // Si el nodo es meta
        if (maze.isGoal(node.getX(), node.getY())) {
            solution = node;
            return;
        }

        // Recorrer hijos
        for (Node child : node.getChildren()) {
            dfs(child);
        }
    }

    // Reconstruir camino desde la solución
    public List<Node> getPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node current = goalNode;

        while (current != null) {
            path.add(0, current);
            current = current.getParent();
        }
        return path;
    }
}
