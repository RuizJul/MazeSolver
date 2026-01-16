package solver;
/**
 *
 * @author Julian
 */
import Model.*;

import java.util.*;

public class BFS {

    private Maze maze;

    public BFS(Maze maze) {
        this.maze = maze;
    }

    public Node solve(Node root, int goalX, int goalY) {

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(root);
        visited.add(key(root));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // ¿Llegamos a la meta?
            if (current.x == goalX && current.y == goalY) {
                return current;
            }

            int[] dx = { -1, 1, 0, 0 };
            int[] dy = { 0, 0, -1, 1 };

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (!maze.isInside(nx, ny)) continue;
                if (maze.isWall(nx, ny)) continue;

                int cellCost = maze.cellCost(nx, ny);
                int newLife = current.life - cellCost; // SE CALCULA...
                int newCost = current.cost + 1;

                Node child = new Node(nx, ny, newCost, newLife, current);

                // ⚠️ BFS NO verifica si está vivo
                if (newLife <= 0) {
                    System.out.println("⚠️ BFS pasó por un estado MUERTO en (" + nx + "," + ny + ")");
                }

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
