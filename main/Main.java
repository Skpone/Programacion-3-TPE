package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String path = "src/datasets/dataset1.txt";
        CSVReader reader = new CSVReader(path);

        //cada arco representa un túnel
        ArrayList<Arco> arcos = reader.read();

        Backtracking back = new Backtracking();
        Solucion solucionBack = back.back(arcos, obtenerCantidadDeEstaciones(arcos));
        System.out.println(solucionBack);
        
        

    }

    private static int obtenerCantidadDeEstaciones(List<Arco> arcos) {//BUSCAR UNA MANERA MENOS COSTOSA
        HashSet<Integer> visitados = new HashSet<>();
        for (Arco arco : arcos) {
            Integer verticeOrigen = arco.getVerticeOrigen();
            Integer verticeDestino = arco.getVerticeDestino();
            if (!visitados.contains(verticeOrigen)) {
                visitados.add(verticeOrigen);
            }
            if (!visitados.contains(verticeDestino)) {
                visitados.add(verticeDestino);
            }
        }
        return visitados.size();
    }

}
