/**
 * Esta clase se encarga de aplicar la lógica del método de ordenamiento y
 * realizar los intercambios necesarios, generados de la clase
 * "Interpolación".
 *
 * También subraya los objetos con los cuales se está interactuando en el
 * momento al igual que desplegar sus valores en un momento específico.
 */

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Point;

public class Ordenamiento {

 private int array[]; //  Arreglo a ordenar
 private Objeto ObjPrimero; //  Imprime Valor de variable "primero"
 private Objeto ObjCentro; //  Imprime Valor de variable "centro"
 private Objeto ObjUltimo; //  Imprime Valor de variable "ultimo"
 private Objeto ObjAuxiliar; //  Imprime Valor de variable "auxiliar"

 private ArrayList < Objeto > obj; // Vector que almacena los component graficos

 private Point p0; // Almacena Punto Inicial del componente que se desea animar.
 private Point p1; // Almacena Punto medio entre punto inicial y final con incremento constante en eje Y.
 private Point p2; // Almacena Punto en el que se desea terminar la animación.

 private Thread t1; // Objeto para iniciar hilos de animaciones

 public Ordenamiento(Objeto p, Objeto c, Objeto u, Objeto a, ArrayList < Objeto > objts, int[] arr) {
  this.obj = objts;
  this.ObjPrimero = p;
  this.ObjCentro = c;
  this.ObjUltimo = u;
  this.ObjAuxiliar = a;
  this.array = arr;
  insercionBinaria(array);
 }

 private void decolorar() {
  for (Objeto object: obj) {
   object.setBackground(null);
  }
 }

 /*
  * Este metodo contiene la logica de el metodo de ordenamiento incluyecto
  * la logica de transición.
  */
 public void insercionBinaria(int[] A) {
   int k;
   int j;
   int ultimo;
   int primero;
   int aux = 0;
   int centro = 0;

   Objeto auxObj; //  Se requiere para logica de corrimiento en el ArrayList

   ObjAuxiliar.setDato(aux);
   ObjAuxiliar.setText("Auxiliar = " + ObjAuxiliar.getDato());

   for (k = 1; k < A.length; k++) {
    decolorar();
    aux = A[k];
    ObjAuxiliar.setDato(aux);
    ObjAuxiliar.setText("Auxiliar = " + ObjAuxiliar.getDato() + "");
    primero = 0;
    ObjPrimero.setDato(primero);
    ObjPrimero.setText("Inicio = " + ObjPrimero.getDato());
    ultimo = k - 1;
    ObjUltimo.setDato(ultimo);
    ObjUltimo.setText("Ultimo = " + ObjUltimo.getDato());

    ObjCentro.setDato(centro);
    ObjCentro.setText("Centro = " + ObjCentro.getDato());

    obj.get(primero).setBackground(Color.RED);
    obj.get(ultimo).setBackground(Color.YELLOW);
    obj.get(centro).setBackground(Color.PINK);

    JOptionPane.showMessageDialog(null, "Animando");

    // Busqueda binaria de la posicion de interseccion
    while (primero <= ultimo) {
     obj.get(centro).setBackground(null);
     centro = (int)((primero + ultimo) / 2); // Calculo de Centro
     ObjCentro.setDato(centro);
     ObjCentro.setText("Centro = " + ObjCentro.getDato());
     obj.get(centro).setBackground(Color.PINK);

     /*
      * Las condiciones verifican hacia que orientacion del arreglo hara
      * la comparacion y posteriormente generar los intercambios necesarios
      * Si es que los numeros son intercambiables
      */
     if (aux <= A[centro]) {
      obj.get(ultimo).setBackground(null);
      ultimo = centro - 1;
      ObjUltimo.setDato(ultimo);
      ObjUltimo.setText("Ultimo = " + ObjUltimo.getDato());
      try {
       obj.get(ultimo).setBackground(Color.YELLOW);
      } catch (Exception e1) {}
     } else {
      obj.get(primero).setBackground(null);
      primero = centro + 1;
      ObjPrimero.setDato(primero);
      ObjPrimero.setText("Inicio = " + ObjPrimero.getDato());
      obj.get(primero).setBackground(Color.RED);
     }
    }
    j = k - 1; // Cantidad de veces que se recorrera el valor a intercambiar

    while (j >= primero) {
     /*
      * try catch esta validando que no haya intercambios entre el mismo elemento
      * ya que si se intenta hacer el intercambio, las posiciones en X serian
      * las mismas y por lo tanto habria una divicion entre cero
      */
     try {
      traslado(obj.get(j + 1), obj.get(j));
      traslado(obj.get(j), obj.get(j + 1));
      JOptionPane.showMessageDialog(null, "Animacion");
      /* intercambio de indices en ArrayList que contiene los objetos */
      auxObj = obj.get(j + 1);
      obj.set(j + 1, obj.get(j));
      obj.set(j, auxObj);
     } catch (Exception e) {
      /* Error aritmetico */
     }

     A[j + 1] = A[j]; // Logica de intercambio
     j--;
     for (int z = 0; z <= A.length - 1; z++) {
      System.out.print("[" + A[z] + "] ");
     }
     System.out.println("");
    }

    if (A[j + 1] > A[primero]); // Valida ultimo intercambio
    try {
     traslado(obj.get(j + 1), obj.get(primero));
     traslado(obj.get(primero), obj.get(j + 1));
     /* JOptionPane se ultiliza para dar tiempo a la animacion */
     JOptionPane.showMessageDialog(null, "Auxiliar traslado");
     auxObj = obj.get(primero);
     obj.set(primero, obj.get(j + 1));
     obj.set(j + 1, auxObj);
    } catch (Exception e) {}

    A[primero] = aux;
   }
   for (int z = 0; z <= A.length - 1; z++) { // Imprime Resultados en consola
    System.out.print("[" + A[z] + "] ");
   }
   System.out.println("");
   decolorar();
  }
  /* Metodo que realiza animacion de los intercambios */
 public void traslado(Objeto obj0, Objeto fin) {
  int medioX = ((fin.getLocation().x) + (obj0.getLocation().x)) / 2;
  p0 = obj0.getLocation();
  p1 = new Point(medioX, 10);
  p2 = fin.getLocation();
  t1 = new Thread(new Interpolacion(obj0, p0, p1, p2));
  t1.start();
 }

}
