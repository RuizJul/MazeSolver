/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Julian
 */
public class DFS {

    private Maze maze;
    private boolean[][] visited;

    public DFS(Maze maze) {
        this.maze = maze;
        this.visited = new boolean[maze.getRows()][maze.getCols()];
    }

    public Node solve(Node node) {
        int x = node.getX();
        int y = node.getY();

        if (!maze.isValidMove(x, y) || visited[x][y]) {
            return null;
        }

        visited[x][y] = true;

        if (maze.isGoal(x, y)) {
            return node; // objetivo encontrado
        }

        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //up , down, lwft. rigth
        for (int[] move : moves) {
            Node child = new Node(
                    x + move[0],
                    y + move[1],
                    node.getDepth() + 1,
                    node
            );

            Node result = solve(child);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

}
