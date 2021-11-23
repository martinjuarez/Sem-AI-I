/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hands_on;

/**
 *
 * @author marti
 */
public class DataSet {

    /*Clase DataSet encargada de crear el data set para su futura manipulacion*/
    private double advertisingX[] = {23, 26, 30, 34, 43, 48, 52, 57, 58};
    private double salesY[] = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

    public double[] getAdvertisingX() {
        /*Metodo getter advertisingX*/
        return advertisingX;
    }

    public void setAdvertisingX(double[] advertisingX) {
        /*Metodo setter advertisingX*/
        this.advertisingX = advertisingX;
    }

    public double[] getSalesY() {
        /*Metodo getter salesY*/
        return salesY;
    }

    public void setSalesY(double[] salesY) {
        /*Metodo setter salesY*/
        this.salesY = salesY;
    }
}
