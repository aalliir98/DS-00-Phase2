package main;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        matrix matrix = read_matrix();
    }

    static matrix read_matrix() throws IOException {
        FileReader fin = new FileReader("./M(300,200).csv");
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
    ArrayList nodes = new ArrayList();

    public matrix(ArrayList nodes) {
        this.nodes = nodes;
    }

    void insert(int row, int col, int value) {

    }

    void delete(int row, int col) {

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