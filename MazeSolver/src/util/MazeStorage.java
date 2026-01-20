/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;

/**
 *
 * @author Julian
 */
public class MazeStorage {

    private static final String APP_FOLDER = ".mazesolver";
    private static final String MAZE_FOLDER = "mazes";

    public static File getMazeDirectory() {
        String userHome = System.getProperty("user.home");

        File appDir = new File(userHome, APP_FOLDER);
        File mazeDir = new File(appDir, MAZE_FOLDER);

        if (!mazeDir.exists()) {
            mazeDir.mkdirs(); // crea TODA la ruta si no existe
        }

        return mazeDir;
    }
}
