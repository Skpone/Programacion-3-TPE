package servicios;

import estructuras.Arco;
import java.util.List;
import estructuras.Grafo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ServicioCaminos {

    private Grafo<?> grafo;
    private int origen;
    private int destino;
    private int lim;
    private HashSet<Arco> arcosVisitados;
    private List<List<Integer>> caminosPosibles;

    public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
        this.grafo = grafo;
        this.origen = origen;
        this.destino = destino;
        this.lim = lim;
        this.arcosVisitados = new HashSet<>();
        this.caminosPosibles = new ArrayList<>();
    }

    public List<List<Integer>> caminos() {
        this.arcosVisitados.clear();
        this.caminosPosibles.clear();

        //Configurar el estado inicial
        ArrayList<Integer> caminoAuxiliar = new ArrayList<>();
        caminoAuxiliar.add(this.origen);

        //Obtenemos todos los caminos posibles
        this.obtenerCaminos(this.origen, this.destino, this.lim, caminoAuxiliar);

        return this.caminosPosibles;
    }

    private void obtenerCaminos(Integer vertice, Integer destino, Integer limiteArcos, ArrayList<Integer> caminoParcial) {
        if (vertice.equals(destino)) {//si el destino fue encontrado
            this.caminosPosibles.add(new ArrayList<>(caminoParcial));//agregamos copia del camino a la lista de listas de caminos posibles
        } else {
            if (this.arcosVisitados.size() < limiteArcos) {
                Iterator<Arco<?>> iterator = (Iterator<Arco<?>>) this.grafo.obtenerArcos(vertice);
                while (iterator.hasNext()) {//recorremos todos los arcos del vertice
                    Arco arco = iterator.next();
                    if (!this.arcosVisitados.contains(arco)) {//si el arco no fue visitado
                        Integer verticeAdyacente = arco.getVerticeDestino();
                        caminoParcial.add(verticeAdyacente);
                        this.arcosVisitados.add(arco);
                        this.obtenerCaminos(verticeAdyacente, destino, limiteArcos, caminoParcial);//obtenemos caminos desde el adyacente recursivamente
                        caminoParcial.remove(verticeAdyacente);//luego al volver, se quita del caminoParcial para elaborar otro caminos desde otro adyacente
                        this.arcosVisitados.remove(arco);//quitamos el arco como visita para nuevos posibles caminos
                    }
                }
            }
        }
    }
}
