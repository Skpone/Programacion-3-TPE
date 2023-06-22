package main;

import java.util.LinkedList;

public class Solucion<T> {

    LinkedList<T> listaSolucion;
    int valorTotal;
    int costo;

    public Solucion() {
        listaSolucion = new LinkedList<>();
        valorTotal = 0;
        costo = 0;
    }

    public boolean contieneElemento(T elemento) {
        return listaSolucion.contains(elemento);
    }

    public void agregarFinal(T elemento) {
        listaSolucion.add(elemento);
    }

    public void agregarPrincipio(T elemento) {
        listaSolucion.addFirst(elemento);
    }

    public void eliminarPrimero() {
        listaSolucion.removeFirst();
    }

    public void eliminarUltimo() {
        listaSolucion.removeLast();
    }

    public boolean eliminarElemento(T elemento) {
        return listaSolucion.remove(elemento);
    }

    public void sumarAValorTotal(int valor) {
        valorTotal = valorTotal + valor;
    }

    public void restarAValorTotal(int valor) {
        valorTotal = valorTotal - valor;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void aumentarCosto() {
        costo++;
    }
}
