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
public class GradienteDescendiente {

    /*Clase GradienteDescendiente encargada de calcular y predecir los valores de X para el data set */
    private DataSet Benetton;
    private double learning_rate;
    private double beta_cero = 0;
    private double beta_uno = 0;
    private double n;
    private double x;
    

    public GradienteDescendiente(DataSet data, double alpha, double x) {
        /*Constructor de la clase GradienteDescendiente*/
        this.Benetton = data;
        this.learning_rate = alpha;
        this.n = (double)Benetton.getAdvertisingX().length;
        this.x = x;
    }

    public double loss_function() {
        double sumatoria = 0;
        var equis = Benetton.getAdvertisingX();
        var ygriega = Benetton.getSalesY();
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + (ygriega[i] - (beta_cero + beta_uno * equis[i]));
        }
        return (sumatoria * sumatoria) * 1.0/n;
    }

    public double derivada_b1() {
        double sumatoria = 0;
        var equis = Benetton.getAdvertisingX();
        var ygriega = Benetton.getSalesY();
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + (equis[i] * (ygriega[i] - (beta_cero + beta_uno * equis[i])));
        }
        return (-2.0/n) * sumatoria;
    }

    public double derivada_b0() {
        double sumatoria = 0;
        var equis = Benetton.getAdvertisingX();
        var ygriega = Benetton.getSalesY();
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + (ygriega[i] - (beta_cero + beta_uno * equis[i]));
        }
        return (-2.0/n) * sumatoria;
    }

    public void gradiente_descendente() {
        double gradiente_b1, gradiente_b0;
        DecimalFormat df = new DecimalFormat("#.00000");
        /*
        for (int i = 0; i < iteraciones; i++) {
            gradiente_b1 = derivada_b1();
            gradiente_b0 = derivada_b0();
            beta_uno = beta_uno - (learning_rate * gradiente_b1);
            beta_cero = beta_cero - (learning_rate * gradiente_b0);
            System.out.println("i=" + i);
            System.out.println("Loss function =" +  loss_function() + "\n");
            System.out.println("beta_uno =" +  beta_uno + "\n");
            System.out.println("beta_cero =" + beta_cero + "\n");
        }
        */
        while(loss_function() >= 0.00001){
            gradiente_b1 = derivada_b1();
            gradiente_b0 = derivada_b0();
            beta_uno = beta_uno - (learning_rate * gradiente_b1);
            beta_cero = beta_cero - (learning_rate * gradiente_b0);
            System.out.println("Loss function =" +  df.format(loss_function()) + "\n");
            System.out.println("beta_uno =" +  df.format(beta_uno) + "\n");
            System.out.println("beta_cero =" + df.format(beta_cero) + "\n");
        }
        System.out.println("Regresion lineal con gradiente");
        System.out.println("y = " + df.format(beta_cero) + " + " + df.format(beta_uno) + " * " + x + " = " + df.format((beta_cero + beta_uno * x)));
    }
}