package main;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        String path = "src/datasets/dataset1.txt";
        CSVReader reader = new CSVReader(path);

        //arcos que representan a los túneles, hay dos por cada túnel, pq un túnel tiene doble mano
        LinkedList<Arco> arcos = reader.read();

        UnionFind conjuntosDisjuntos = new UnionFind(obtenerCantidadDeEstaciones(arcos));

    }

    private static int obtenerCantidadDeEstaciones(LinkedList<Arco> arcos) {
        LinkedList<Integer> visitados = new LinkedList<>();
        for (Arco arco : arcos) {
            Integer verticeOrigen = arco.getVerticeOrigen();
            //como existen dos arcos para representar un tunel, siempre vamos (usando el origen o destino) encontrar todas las estaciones
            if (!visitados.contains(verticeOrigen)) {
                visitados.add(verticeOrigen);
            }
        }
        return visitados.size();
    }

}
