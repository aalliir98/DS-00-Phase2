package main;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        matrix matrix = read_matrix();
     matrix.insert(3, 2, 90);
        matrix.delete(5, 4);
    }

    static matrix read_matrix() throws IOException {
        FileReader fin = new FileReader("M(10,5).csv");
        Scanner sc = new Scanner(fin);
        ArrayList<node> nodes = new ArrayList<node>();
        node node2 = null;
        int j = -1;
        while (sc.hasNext()) {
            String[] arr = sc.nextLine().split(",");

            if (j == nodes.size()) {
                node2 = new node(-2, -2, null);
                nodes.add(node2);
            }
            if (nodes.size() > 0)
                node2.pointer = null;
            j = nodes.size();
            for (int i = 0; i < arr.length; i++)
                if (!arr[i].equals("0")) {
                    node node1 = new node(-1, -1, null);
                    node node = new node(i, Integer.parseInt(arr[i]), node1);

                    if (nodes.size() > 0 && node2.pointer != null)
                        node2.pointer = node;

                    if (nodes.size() > 0 && node2.pointer == null)
                        nodes.add(node);

                    if (nodes.size() == 0)
                        nodes.add(node);

                    node2 = node;
                }
        }
        nodes.get(nodes.size() - 1).pointer = null;
        return new matrix(nodes);
    }


}


class node {
    int index, value;
    node pointer;

    public node(int index, int value, node pointer) {
        this.index = index;
        this.value = value;
        this.pointer = pointer;
    }
}

class matrix {
    ArrayList<node> nodes = new ArrayList();

    public matrix(ArrayList<node> nodes) {
        this.nodes = nodes;
    }

    void insert(int row, int col, int value) {
        node node1 = nodes.get(row);
        node node2 = new node(col, value, node1.pointer);
        while (true) {
            if (node1.pointer == null) {
                nodes.remove(node1);
                nodes.add(row, node2);
                break;
            }
            if (node1.pointer.index > col && node1.index < col) {
                node1.pointer = node2;
                break;
            }
            node1 = node1.pointer;
        }

    }

    void delete(int row, int col) {
        node node1 = null;
        node node2 = nodes.get(row);
        boolean a = true;
        if (node2.pointer == null && node2.index == col) {
            node2.index = -2;
            node2.value = -2;
            a = false;
        }
        if (a)
            while (true) {
                if (node2.index == col && node2.pointer != null) {
                    if (node1 != null) {
                        node1.pointer = node2.pointer;
                        break;
                    } else {
                        nodes.add(row, node2.pointer);
                        nodes.remove(node2);
                        break;
                    }
                }
                if (node2.index == col && node2.pointer == null) {
                    node1.pointer = null;
                    break;
                }

                node1 = node2;
                node2 = node2.pointer;
            }
    }

    void search(int value) {

    }

    void update(int row, int col, int value) {

    }

    void print(boolean type) {

    }

    void show_2d() {

    }

    void show_zip() {

    }
}