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

    // Estado del problema (posición en el laberinto)
    int x, y;

    // Propiedades del árbol
    int depth;
    int value;
    Node parent;
    List<Node> children;

    public Node(int x, int y, int depth, Node parent) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.parent = parent;
        this.value = 0;
        this.children = new ArrayList<>();
    }
    // ---------- Getters ----------

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDepth() {
        return depth;
    }

    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // ---------- Métodos del árbol ----------
    public void addChild(Node child) {
        children.add(child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
