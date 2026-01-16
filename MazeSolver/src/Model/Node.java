/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian
 */

public class Node {
    public int x;
    public int y;
    public int cost;          // costo acumulado
    public int life;          // vida restante
    public Node parent;       // para reconstruir el camino

    public Node(int x, int y, int cost, int life, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.life = life;
        this.parent = parent;
    }
}

