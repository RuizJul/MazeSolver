/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

/**
 *
 * @author Julian
 */
public class TestMazeLoader {
    
    public static void main(String[] args) {

        System.out.println("=== CARGANDO LABERINTOS ===");

        CircularMazeList list = MazeLoader.loadMazes();

        if (list.isEmpty()) {
            System.out.println("❌ No se cargaron laberintos");
            return;
        }

        // Mostrar laberinto actual
        MazeData current = list.getCurrent();
        System.out.println("✔ Actual: " + current.getName());
        printMatrix(current.getMatrix());

        // Probar siguiente
        MazeData next = list.next();
        System.out.println("\n➡ Siguiente: " + next.getName());
        printMatrix(next.getMatrix());

        // Probar anterior
        MazeData prev = list.prev();
        System.out.println("\n⬅ Anterior: " + prev.getName());
        printMatrix(prev.getMatrix());
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(String.format("%3d", matrix[i][j]));
            }
            System.out.println();
        }
    }
}

