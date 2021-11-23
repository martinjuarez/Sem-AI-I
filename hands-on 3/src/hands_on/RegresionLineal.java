/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hands_on;

import java.text.DecimalFormat;

/**
 *
 * @author marti
 */
public class RegresionLineal {
    /*Clase RegresionLineal encargada de calcular y predecir los valores de X para el data set*/
    private DataSet Benetton;
    private double n;
    private double x;
    public RegresionLineal(DataSet data, double x){
      /*Constructor de la clase RegresionLineal*/
      this.Benetton = data;
      this.n = (double)Benetton.getAdvertisingX().length;
      this.x = x;
    }
    
    public double b1(){
      /*Metodo B1 para el calculo de la Regresion Lineal*/
      double sum[] = new double[4];
        for (int i = 0; i < n ; i++) {
            sum[0] = sum[0] + Benetton.getAdvertisingX()[i] * Benetton.getSalesY()[i];
        }
        for (int i = 0; i < n; i++) {
            sum[1] = sum[1] + Benetton.getAdvertisingX()[i];
        }
        for (int i = 0; i < n; i++) {
            sum[2] = sum[2] + Benetton.getSalesY()[i];
        }
        for (int i = 0; i < n; i++) {
            sum[3] = sum[3] + Benetton.getAdvertisingX()[i] * Benetton.getAdvertisingX()[i];
        }
        return (n * sum[0] - sum[1] * sum[2]) / (n * sum[3] - sum[1] * sum[1]);
    }

    public double b0() {
      /*Metodo B0 para el calculo de la Regresion Lineal*/
      double x = 0, y = 0;
      for (int i = 0; i < n; i++) {
          x = x + Benetton.getAdvertisingX()[i];
      }
      x = x / n;
      for (int i = 0; i < n; i++) {
          y = y + Benetton.getSalesY()[i];
      }
      y = y / n;
      return y - b1() * x;
    }

    public void impresion_de_Regresion(){
      /*Metodo impresion_de_Regresion para imprimir la ecuacion y el resultado del calculo de la Regresion Lineal*/
      double b0 = b0(), b1 = b1();
      DecimalFormat df = new DecimalFormat("#.00000");
      System.out.println("Regresion lineal sola");
      System.out.println("y = " + df.format(b0) + " + " + df.format(b1) + " * " + x + " = " + df.format((b0 + b1 * x)));
    }
    
}
