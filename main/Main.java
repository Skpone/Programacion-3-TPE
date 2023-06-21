package main;

public class Main {

    public static void main(String[] args) {
        
        String path = "src/datasets/dataset1.txt";
        CSVReader reader = new CSVReader(path);
        reader.read();
        UnionFind conjuntos = new UnionFind(/*cantidad de estaciones q tenga el dataset*/);
        
    }

}
