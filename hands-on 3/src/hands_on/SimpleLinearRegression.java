
package hands_on;

import java.util.Scanner;

public class SimpleLinearRegression {
    
    int advertisingX[] = new int[9];
    int salesY[] = new int[9];
    
    public SimpleLinearRegression() {
        advertisingX[0] = 23;
        advertisingX[1] = 26;
        advertisingX[2] = 30;
        advertisingX[3] = 34;
        advertisingX[4] = 43;
        advertisingX[5] = 48;
        advertisingX[6] = 52;
        advertisingX[7] = 57;
        advertisingX[8] = 58;
        salesY[0] = 651;
        salesY[1] = 762;
        salesY[2] = 856;
        salesY[3] = 1063;
        salesY[4] = 1190;
        salesY[5] = 1298;
        salesY[6] = 1421;
        salesY[7] = 1440;
        salesY[8] = 1518;
    }
    
    float B1(){
        float sum[] = new float[4] ;
        for(int i = 0; i < advertisingX.length; i++){
            sum[0] = sum[0] + advertisingX[i]*salesY[i];
        }
        for(int i = 0; i < advertisingX.length; i++){
            sum[1] = sum[1] + advertisingX[i];
        }
        for(int i = 0; i < advertisingX.length; i++){
            sum[2] = sum[2] + salesY[i];
        }
        for(int i = 0; i < advertisingX.length; i++){
            sum[3] = sum[3] + advertisingX[i]*advertisingX[i];
        }
        return (advertisingX.length*sum[0]-sum[1]*sum[2])/(advertisingX.length*sum[3]-sum[1]*sum[1]);
    }
    
    float B0(){
        float x=0,y=0;
        for(int i = 0; i < advertisingX.length; i++){
            x = x + advertisingX[i];
        }
        x = x/advertisingX.length;
        for(int i = 0; i < advertisingX.length; i++){
            y = y + salesY[i];
        }
        y = y/salesY.length;
        return y-B1()*x;
    }
       
    public static void main(String[] args) {
        SimpleLinearRegression a = new SimpleLinearRegression();
        Scanner consola = new Scanner(System.in);
        float b0=a.B0(),b1=a.B1();
        System.out.print("Escribe un valor de x = ");
        var x = consola.nextInt();
        System.out.println("y = " + b0 + " + " + b1+ " * " + x + " = " + (b0 + b1 * x));
    }
    
}
