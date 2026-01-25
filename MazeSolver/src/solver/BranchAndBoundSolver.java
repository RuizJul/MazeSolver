package solver;

import Model.Maze;
import Model.Node;

import java.util.PriorityQueue;
import java.util.Comparator;

public class BranchAndBoundSolver {

    private Maze maze;
    private int goalX;
    private int goalY;

    private int bestSolutionCost = Integer.MAX_VALUE;
    private Node bestNode = null;

    private int[][] bestLife; // PODA CLAVE

    public BranchAndBoundSolver(Maze maze, int goalX, int goalY) {
        this.maze = maze;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public Node solve(Node root) {

        bestLife = new int[maze.getRows()][maze.getCols()];
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                bestLife[i][j] = -1;
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingInt(this::lowerBound)
        );

        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (lowerBound(current) >= bestSolutionCost) continue;

            if (bestLife[current.x][current.y] >= current.life) continue;
            bestLife[current.x][current.y] = current.life;

            if (current.x == goalX && current.y == goalY) {
                bestSolutionCost = current.cost;
                bestNode = current;
                continue;
            }

            expandNode(current, queue);
        }

        return bestNode;
    }

    private void expandNode(Node node, PriorityQueue<Node> queue) {

        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];

            if (!maze.isInside(nx, ny)) continue;
            if (maze.isWall(nx, ny)) continue;

            int cellCost = maze.cellCost(nx, ny);
            
            // la salida no consume vida
            if (nx == goalX && ny == goalY) {
                cellCost = 0;
            }
            
            int newLife = node.life - cellCost;
            if (newLife <= 0) continue;

            int newCost = node.cost + 1 + cellCost;

            Node child = new Node(nx, ny, newCost, newLife, node);
            queue.add(child);
        }
    }

    private int lowerBound(Node node) {
        int h = manhattan(node.x, node.y, goalX, goalY);
        int lifePenalty = (100 - node.life) * 2;
        return node.cost + h + lifePenalty;
    }

    private int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}