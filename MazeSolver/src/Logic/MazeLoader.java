/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian
 */
public class MazeLoader {

    private static final String MAZE_FOLDER =
    System.getProperty("user.home") + File.separator +
    ".mazesolver" + File.separator + "mazes";

    public static CircularMazeList loadMazes() {

    CircularMazeList list = new CircularMazeList();
    File folder = new File(MAZE_FOLDER);

    if (!folder.exists()) {
        folder.mkdirs(); // crea carpetas si no existen
        return list;
    }

    File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

    if (files == null) return list;

    for (File file : files) {
        MazeData data = loadMazeFromFile(file);
        if (data != null) {
            list.add(data);
        }
    }

    return list;
}

    private static MazeData loadMazeFromFile(File file) {

        List<int[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                int[] row = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                rows.add(row);
            }

            int[][] matrix = rows.toArray(new int[0][]);
            return new MazeData(file.getName(), matrix);

        } catch (Exception e) {
            System.err.println("Error cargando " + file.getName());
            return null;
        }
    }
}
