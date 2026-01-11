/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Julian
 */
public class TreeBuilder {

    private Maze maze;
    private int maxDepth;

    // Movimientos: arriba, abajo, izquierda, derecha
    private static final int[][] MOVES = {
        {-1, 0}, // up
        {1, 0},  // down
        {0, -1}, // left
        {0, 1}   // right
    };

    public TreeBuilder(Maze maze, int maxDepth) {
        this.maze = maze;
        this.maxDepth = maxDepth;
    }

    public void buildTree(Node node) {

        // 1️⃣ Condiciones de parada
        if (node.getDepth() >= maxDepth) return;
        if (!node.isAlive()) return;
        if (maze.isGoal(node.getX(), node.getY())) return;

        // 2️⃣ Expandir hijos
        for (int[] move : MOVES) {

            int newX = node.getX() + move[0];
            int newY = node.getY() + move[1];

            if (!maze.isValidMove(newX, newY)) continue;

            // 3️⃣ Calcular nueva vida
            int newHealth = node.getHealth();

            char cell = maze.getCell(newX, newY);

            if (cell == 'T') {
                newHealth -= 20; // daño de trampa
            } else if (cell == 'H') {
                newHealth += 15; // curación
                if (newHealth > 100) newHealth = 100;
            }

            // 4️⃣ Crear nuevo nodo (estado)
            Node child = new Node(
                    newX,
                    newY,
                    newHealth,
                    node.getDepth() + 1,
                    node
            );

            node.addChild(child);

            // 5️⃣ Expansión recursiva (DFS)
            buildTree(child);
        }
    }
}
