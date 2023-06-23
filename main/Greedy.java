package main;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Greedy {

    public Greedy() {
    
    }

    public Solucion greedy(List<Arco> arcos, int cantidadVertices){
        Solucion solucion = new Solucion();
        UnionFind estadoSolucion = new UnionFind(cantidadVertices);
        boolean encontroSolucion = false;
        
        ordenarArcosMenorAMayor(arcos);
        
        Iterator<Arco> it = arcos.iterator();
        while(it.hasNext() && !encontroSolucion){
            //aumentamos la métrica
            solucion.aumentarCosto();
            
            Arco tunel = it.next();
            //si agregar éste túnel no es redundante, porque no existe ya uno que los conecte directa o indirectamente
            if (!(estadoSolucion.find(tunel.getVerticeOrigen() - 1) == estadoSolucion.find(tunel.getVerticeDestino() - 1))) {
                //lo agregamos a la solución
                solucion.agregarFinal(tunel);
                solucion.sumarAValorTotal((int) tunel.getEtiqueta());
                //hago la unión
                estadoSolucion.union(tunel.getVerticeOrigen() - 1, tunel.getVerticeDestino() - 1);
            }
            
            if (estadoSolucion.numberOfSets() == 1) {//es solución
                encontroSolucion = true;
            }
        }
        
        if(encontroSolucion){
            return solucion;
        }
        return null;
    }
    
    private void ordenarArcosMenorAMayor(List<Arco> arcos){
        Collections.sort(arcos, (o1, o2) -> {
            return (int) o1.getEtiqueta() - (int) o2.getEtiqueta();
        });
    }
}
