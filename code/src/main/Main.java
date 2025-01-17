package main;

import java.io.*;
import java.util.*;

import com.opencsv.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int p = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name:");
        String file_name = sc.next();
        matrix matrix = read_matrix(file_name);
        do {
            System.out.println("what do you want to do?(Enter a number)\n1.insert\n2.delete\n3.search\n4.update\n5.show 2d\n6.show zip\n7.save\n8.exit");
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println("Enter row,col and value of element");
                    matrix.insert(sc.nextInt(), sc.nextInt(), sc.nextInt());
                }
                case 2 -> {
                    System.out.println("Enter row and col of element");
                    matrix.delete(sc.nextInt(), sc.nextInt());
                }
                case 3 -> {
                    System.out.println("Enter value of element");
                    matrix.search(sc.nextInt());
                }
                case 4 -> {
                    System.out.println("Enter row,col and value of element");
                    matrix.update(sc.nextInt(), sc.nextInt(), sc.nextInt());
                }
                case 5 -> matrix.show_2d();
                case 6 -> matrix.show_zip();
                case 7 -> matrix.save();
                case 8 -> p = 1;
            }
        } while (p != 1);

    }

    static matrix read_matrix(String file_name) throws IOException {
        FileReader fin = new FileReader(file_name);
        Scanner sc = new Scanner(fin);
        ArrayList<node> row_nodes = new ArrayList();

        node node2 = null;
        int j = -1;
        int o = 0;
        while (sc.hasNext()) {
            String[] arr = sc.nextLine().split(",");
            o = arr.length;
            if (j == row_nodes.size()) {
                node2 = new node(-2, -2, null);
                row_nodes.add(node2);
            }
            if (row_nodes.size() > 0)
                node2.pointer = null;
            j = row_nodes.size();
            for (int i = 0; i < arr.length; i++)
                if (!arr[i].equals("0")) {
                    node node1 = new node(-1, -1, null);
                    node node = new node(i, Integer.parseInt(arr[i]), node1);

                    if (row_nodes.size() > 0 && node2.pointer != null)
                        node2.pointer = node;

                    if (row_nodes.size() > 0 && node2.pointer == null)
                        row_nodes.add(node);

                    if (row_nodes.size() == 0)
                        row_nodes.add(node);

                    node2 = node;
                }
        }
        row_nodes.get(row_nodes.size() - 1).pointer = null;
        fin.close();
        //-------------------------------------------------------
        FileReader fin2 = new FileReader(file_name);
        Scanner sc2 = new Scanner(fin);
        ArrayList<node> col_nodes = new ArrayList();
        int[][] matrix = make_matrix(row_nodes, row_nodes.size(), o);
        j = -1;
        for (int i = 0; i < o; i++) {
            if (j == col_nodes.size()) {
                node2 = new node(-2, -2, null);
                col_nodes.add(node2);
            }
            if (col_nodes.size() > 0)
                node2.pointer = null;
            j = col_nodes.size();
            for (int u = 0; u < row_nodes.size(); u++) {
                if (matrix[u][i] != 0) {
                    node node1 = new node(-1, -1, null);
                    node node = new node(u, matrix[u][i], node1);

                    if (col_nodes.size() > 0 && node2.pointer != null)
                        node2.pointer = node;

                    if (col_nodes.size() > 0 && node2.pointer == null)
                        col_nodes.add(node);

                    if (col_nodes.size() == 0)
                        col_nodes.add(node);
                    node2 = node;
                }
            }
        }
        col_nodes.get(col_nodes.size() - 1).pointer = null;
        fin2.close();
        return new matrix(row_nodes, col_nodes, row_nodes.size(), o);
    }

    static int[][] make_matrix(ArrayList<node> row_nodes, int row, int col) {
        int[][] matrix = new int[row][col];
        node a;
        for (int i = 0; i < row; i++) {
            a = row_nodes.get(i);
            for (int j = 0; j < col; j++) {
                if (a.index == j) {
                    matrix[i][j] = a.value;
                    if (a.pointer != null)
                        a = a.pointer;
                } else
                    matrix[i][j] = 0;
            }
        }
        return matrix;
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
    ArrayList<node> row_nodes, col_nodes;
    int row, col;
    int[][] matrix;

    public matrix(ArrayList<node> row_nodes, ArrayList<node> col_nodes, int row, int col) {
        this.row_nodes = row_nodes;
        this.col_nodes = col_nodes;
        this.row = row;
        this.col = col;

        int[][] matrix = new int[row][col];
        node a;
        for (int i = 0; i < row; i++) {
            a = row_nodes.get(i);
            for (int j = 0; j < col; j++) {
                if (a.index == j) {
                    matrix[i][j] = a.value;
                    if (a.pointer != null)
                        a = a.pointer;
                } else
                    matrix[i][j] = 0;
            }

        }
        this.matrix = matrix;
    }

    void insert(int row, int col, int value) {
        node node1 = row_nodes.get(row);
        while (true) {
            node node2 = new node(col, value, node1.pointer);
            if (node1.index == -2) {
                node1.value = node2.value;
                node1.index = node2.index;
                break;
            }
            if (node2.index > node1.index && node1.pointer == null) {
                node1.pointer = node2;
                break;
            }
            if (node2.index<node1.index) {
                node2.pointer = node1;
                row_nodes.remove(row_nodes.get(row));
                row_nodes.add(row,node2);
                break;
            }
            if (node1.pointer.index > col && node1.index < col) {
                node1.pointer = node2;
                break;
            }
            node1 = node1.pointer;
        }

        //-----------------------------------------------------

        node1 = col_nodes.get(col);
        while (true) {
            node node2 = new node(row, value, node1.pointer);
            if (node1.index == -2) {
                node1.value = node2.value;
                node1.index = node2.index;
                break;
            }
            if (node2.index > node1.index && node1.pointer == null) {
                node1.pointer = node2;
                break;
            }
            if (node2.index<node1.index) {
                node2.pointer = node1;
                col_nodes.remove(col_nodes.get(col));
                col_nodes.add(col,node2);
                break;
            }
            if (node1.pointer.index > row && node1.index < row) {
                node1.pointer = node2;
                break;
            }
            node1 = node1.pointer;
        }

    }

    void delete(int row, int col) {
        node node1 = null;
        node node2 = row_nodes.get(row);
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
                    } else {
                        row_nodes.add(row, node2.pointer);
                        row_nodes.remove(node2);
                    }
                    break;
                }
                if (node2.index == col && node2.pointer == null) {
                    node1.pointer = null;
                    break;
                }
                node1 = node2;
                node2 = node2.pointer;
            }

//-----------------------------------------------------------------

        node1 = null;
        node2 = col_nodes.get(col);
         a = true;
        if (node2.pointer == null && node2.index == row) {
            node2.index = -2;
            node2.value = -2;
            a = false;
        }
        if (a)
            while (true) {
                if (node2.index == row && node2.pointer != null) {
                    if (node1 != null) {
                        node1.pointer = node2.pointer;
                    } else {
                        col_nodes.add(col, node2.pointer);
                        col_nodes.remove(node2);
                    }
                    break;
                }
                if (node2.index == row && node2.pointer == null) {
                    node1.pointer = null;
                    break;
                }
                node1 = node2;
                node2 = node2.pointer;
            }



    }

    void search(int value) {
        boolean u = false;
        for (node a : row_nodes) {
            while (true) {
                if (a.value == value) {
                    u = true;
                    System.out.println(u);
                    break;
                }
                node b = a;
                a = a.pointer;
                if (b.pointer == null)
                    break;
            }
            if (u)
                break;
        }
        if (!u)
            System.out.println(u);

    }

    void update(int row, int col, int value) {
        node node1 = row_nodes.get(row);
        while (true) {
            if (node1.index == col) {
                node1.value = value;
                break;
            }
            node1 = node1.pointer;
        }

        //---------------------------------------

         node1 = col_nodes.get(col);
        while (true) {
            if (node1.index == row) {
                node1.value = value;
                break;
            }
            node1 = node1.pointer;
        }
    }

    void show_2d() {

        matrix = Main.make_matrix(row_nodes, row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                if (j == 0)
                    System.out.print(matrix[i][j]);
                else
                    System.out.print("," + matrix[i][j]);

            System.out.println();
        }


    }

    void show_zip() {
        int i = 0;
        for (node a : row_nodes) {
            while (true) {
                if (a.index != -2)
                    System.out.println(i + " " + a.index + " " + a.value);
                if (a.pointer == null)
                    break;
                a = a.pointer;
            }
            i++;
        }
    }

    void save() throws IOException {
        FileWriter fw = new FileWriter("matrix.txt");
        CSVWriter writer = new CSVWriter(fw);
        String[] inf = new String[col];
        ArrayList<String[]> f = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                inf[j] = String.valueOf(matrix[i][j]);
            f.add(inf);
            inf = new String[col];
        }

        writer.writeAll(f);
        writer.close();
        fw.close();
    }
}