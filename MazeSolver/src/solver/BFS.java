package solver;

import model.Maze;
import model.Node;
import java.util.*;

public class BFS{

    private Maze maze;

    public BFS(Maze maze) {
        this.maze = maze;
    }

    public Node solve(Node root) {

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(root);
        visited.add(key(root));

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            // Si es meta
            if (maze.isGoal(current.getX(), current.getY())) {
                return current;
            }

            // Explorar hijos
            for (Node child : current.getChildren()) {

                String k = key(child);

                if (!visited.contains(k)) {
                    visited.add(k);
                    queue.add(child);
                }
            }
        }
        return null;
    }

    private String key(Node node) {
        return node.getX() + "," + node.getY() + "," + node.getHealth();
    }

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
