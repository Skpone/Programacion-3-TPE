package main;

import java.util.LinkedList;

public class Backtracking<T> {

    private Solucion mejorSolucion;

    public Backtracking(T elemento) {
        mejorSolucion = new Solucion();
    }

    public Solucion back(LinkedList<Arco> arcos, int initNum) {
        Solucion solucionParcial = new Solucion();
        UnionFind estadoSolucion = new UnionFind(initNum);
        back(arcos, solucionParcial, estadoSolucion);
        return mejorSolucion;
    }

    private void back(LinkedList<Arco> arcos, Solucion solucionParcial, UnionFind estadoActual) {
        solucionParcial.aumentarCosto();//cada vez que se entra a un nuevo estado se aumenta el costo
        if (estadoActual.numberOfSets() == 1) {//si es solución
            if (solucionParcial.getValorTotal() < this.mejorSolucion.getValorTotal()) {//si ahora mi solución es la mejor
                this.mejorSolucion = solucionParcial;
            }
        } else {
            for (Arco arco : arcos) {
                //PODA, si ya no importa que luego sea válido o no, agregar éste túnel implica una peor solución que la mejor, entonces no avanzamos
                if (solucionParcial.getValorTotal() + (Integer) arco.getEtiqueta() > mejorSolucion.getValorTotal()) {
                    //si el túnel no está en la solución parcial
                    if (!solucionParcial.contieneElemento(arco)) {
                        //si las estaciones no estaban ya conectadas por otro túnel
                        if (!(estadoActual.find(arco.getVerticeOrigen()) == estadoActual.find(arco.getVerticeDestino()))) {
                            solucionParcial.agregarFinal(arco);
                            solucionParcial.sumarAValorTotal((Integer) arco.getEtiqueta());

                            UnionFind estadoTemporal = estadoActual.copy();//creamos un estado copia para generar un nuevo estado
                            estadoTemporal.union(arco.getVerticeOrigen(), arco.getVerticeDestino());//establecemos que ya existe un túnel entre ambas estaciones

                            back(arcos, solucionParcial, estadoTemporal);

                            solucionParcial.restarAValorTotal((Integer) arco.getEtiqueta());
                            solucionParcial.eliminarElemento(arco);
                        }
                    }
                }
            }
        }
    }
}
