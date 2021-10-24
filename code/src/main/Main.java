package main;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

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