package main;

import java.util.LinkedList;

public class Backtracking {

    private Solucion mejorSolucion;

    public Backtracking() {
        mejorSolucion = new Solucion();
    }

    public Solucion back(LinkedList<Arco> arcos, int initNum) {
        mejorSolucion.setValorTotal(Integer.MAX_VALUE);
        mejorSolucion.setCosto(0);

        Solucion solucionParcial = new Solucion();
        UnionFind estadoSolucion = new UnionFind(initNum);

        back(arcos, solucionParcial, estadoSolucion, 0);
        return mejorSolucion;
    }

    private void back(LinkedList<Arco> arcos, Solucion solucionParcial, UnionFind estadoActual, int tunelActual) {
        solucionParcial.aumentarCosto();//cada vez que se entra a un nuevo estado se aumenta el costo
        System.out.println(solucionParcial);
        if (estadoActual.numberOfSets() == 1) {//si es solución
            if (solucionParcial.getValorTotal() < this.mejorSolucion.getValorTotal()) {//si ahora mi solución es la mejor
                this.mejorSolucion = solucionParcial.copy();
                System.out.println("mejor solucion = " + this.mejorSolucion);
            }
        } else {

            if (tunelActual < arcos.size()) {//si el túnel actual es válido

                Arco tunel = arcos.get(tunelActual);//lo obtenemos

                System.out.println(tunel.getVerticeOrigen());
                System.out.println(tunel.getVerticeDestino());

                if ( (solucionParcial.getValorTotal() + ((int) tunel.getEtiqueta())) < this.mejorSolucion.getValorTotal()) {
                    //si las estaciones no estaban ya conectadas por éste u otro túnel
                    if (!(estadoActual.find(tunel.getVerticeOrigen() - 1) == estadoActual.find(tunel.getVerticeDestino() - 1))) {

                        //segunda llamada al back considerándo éste túnel
                        solucionParcial.agregarFinal(tunel);
                        solucionParcial.sumarAValorTotal((int) tunel.getEtiqueta());
                        tunelActual++;
                        UnionFind estadoTemporal = estadoActual.copy();

                        //establecemos el túnel entre ambas estaciones
                        estadoTemporal.union(tunel.getVerticeOrigen() - 1, tunel.getVerticeDestino() - 1);
                        System.out.println("sets:" + estadoActual.numberOfSets());
                        System.out.println("sets:" + estadoTemporal.numberOfSets());

                        back(arcos, solucionParcial, estadoTemporal, tunelActual);

                        //rollback
                        solucionParcial.eliminarUltimo();
                        solucionParcial.restarAValorTotal((int) tunel.getEtiqueta());
                        tunelActual--;
                    }
                }

                //primera llamada al back sin agregar el túnel actual
                System.out.println("sin considerar el tunel");
                tunelActual++;
                back(arcos, solucionParcial, estadoActual, tunelActual);
            }
        }
    }
}
