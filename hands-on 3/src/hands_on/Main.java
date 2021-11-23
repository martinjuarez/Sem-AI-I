/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hands_on;

import java.util.Scanner;


/**
 *
 * @author marti
 */
public class Main {
    public static void main(String[] args) {
        Scanner consola = new Scanner(System.in);
        System.out.print("Escribe un valor de x = ");
        double x = consola.nextDouble();
        DataSet data_uno = new DataSet();
        GradienteDescendiente gradiente_uno = new GradienteDescendiente(data_uno, 0.0001, x);
        RegresionLineal regresion_uno = new RegresionLineal(data_uno, x);
        gradiente_uno.gradiente_descendente();
        regresion_uno.impresion_de_Regresion();
    }
}
