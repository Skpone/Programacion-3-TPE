package main;

import java.util.List;

public class Backtracking {

    private Solucion mejorSolucion;

    public Backtracking() {
        mejorSolucion = new Solucion();
    }

    public Solucion back(List<Arco> arcos, int cantidadVertices) {
        mejorSolucion.setValorTotal(Integer.MAX_VALUE);
        mejorSolucion.setCosto(0);

        Solucion solucionParcial = new Solucion();
        UnionFind estadoSolucion = new UnionFind(cantidadVertices);

        back(arcos, solucionParcial, estadoSolucion, 0);
        return mejorSolucion;
    }

    private void back(List<Arco> arcos, Solucion solucionParcial, UnionFind estadoActual, int tunelActual) {
        solucionParcial.aumentarCosto();//cada vez que se entra a un nuevo estado se aumenta el costo
        if (estadoActual.numberOfSets() == 1) {//si es solución
            if (solucionParcial.getValorTotal() < this.mejorSolucion.getValorTotal()) {//si ahora mi solución es la mejor
                this.mejorSolucion = solucionParcial.copy();
            }
            
        } else {

            if (tunelActual < arcos.size()) {//si el túnel actual es válido

                Arco tunel = arcos.get(tunelActual);//lo obtenemos

                //PODA, si ya considerar agregar éste túnel supera la cantidad de kilómetros que tiene la mejor solución
                if ( (solucionParcial.getValorTotal() + ((int) tunel.getEtiqueta())) < this.mejorSolucion.getValorTotal()) {
                    //si las estaciones no estaban ya conectadas por éste u otro túnel
                    if (!(estadoActual.find(tunel.getVerticeOrigen() - 1) == estadoActual.find(tunel.getVerticeDestino() - 1))) {

                        //primera llamada al back considerándo éste túnel
                        solucionParcial.agregarFinal(tunel);
                        solucionParcial.sumarAValorTotal((int) tunel.getEtiqueta());
                        tunelActual++;
                        UnionFind estadoTemporal = estadoActual.copy();

                        //establecemos el túnel entre ambas estaciones
                        estadoTemporal.union(tunel.getVerticeOrigen() - 1, tunel.getVerticeDestino() - 1);
                        
                        back(arcos, solucionParcial, estadoTemporal, tunelActual);

                        //deshacemos
                        solucionParcial.eliminarUltimo();
                        solucionParcial.restarAValorTotal((int) tunel.getEtiqueta());
                        tunelActual--;
                    }
                }

                if (tunelActual + 1 < arcos.size()) {//si no hay más túneles siguientes, entonces no sirve crear un estado sin incluirlo
                    //segunda llamada al back sin agregar el túnel actual
                    tunelActual++;
                    back(arcos, solucionParcial, estadoActual, tunelActual);
                }
            }
        }
    }
}
