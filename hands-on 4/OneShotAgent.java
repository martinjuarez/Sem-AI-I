package examples.behaviours;

import jade.core.behaviours.*;
//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
import jade.core.Agent;

public class OneShotAgent extends Agent {
    
    int advertisingX[] = new int[9];
    int salesY[] = new int[9];
    
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        OneShotAgentGui myGui = new OneShotAgentGui(this);
        myGui.showGui();
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
        addBehaviour(new MyOneShotBehaviour());    
    }

    public float B1() {
        float sum[] = new float[4];
        for (int i = 0; i < advertisingX.length; i++) {
            sum[0] = sum[0] + advertisingX[i] * salesY[i];
        }
        for (int i = 0; i < advertisingX.length; i++) {
            sum[1] = sum[1] + advertisingX[i];
        }
        for (int i = 0; i < advertisingX.length; i++) {
            sum[2] = sum[2] + salesY[i];
        }
        for (int i = 0; i < advertisingX.length; i++) {
            sum[3] = sum[3] + advertisingX[i] * advertisingX[i];
        }
        return (advertisingX.length * sum[0] - sum[1] * sum[2]) / (advertisingX.length * sum[3] - sum[1] * sum[1]);
    }

    public float B0() {
        float x = 0, y = 0;
        for (int i = 0; i < advertisingX.length; i++) {
            x = x + advertisingX[i];
        }
        x = x / advertisingX.length;
        for (int i = 0; i < advertisingX.length; i++) {
            y = y + salesY[i];
        }
        y = y / salesY.length;
        return y - B1() * x;
    }

    public void calcular(int x){
        float b0 = B0(), b1 = B1();
        System.out.println("y = " + b0 + " + " + b1 + " * " + x + " = " + (b0 + b1 * x));
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {
        /*
        int advertisingX[] = new int[9];
        int salesY[] = new int[9];
        int x ;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        MyOneShotBehaviour() {
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

        public float B1() {
            float sum[] = new float[4];
            for (int i = 0; i < advertisingX.length; i++) {
                sum[0] = sum[0] + advertisingX[i] * salesY[i];
            }
            for (int i = 0; i < advertisingX.length; i++) {
                sum[1] = sum[1] + advertisingX[i];
            }
            for (int i = 0; i < advertisingX.length; i++) {
                sum[2] = sum[2] + salesY[i];
            }
            for (int i = 0; i < advertisingX.length; i++) {
                sum[3] = sum[3] + advertisingX[i] * advertisingX[i];
            }
            return (advertisingX.length * sum[0] - sum[1] * sum[2]) / (advertisingX.length * sum[3] - sum[1] * sum[1]);
        }

        public float B0() {
            float x = 0, y = 0;
            for (int i = 0; i < advertisingX.length; i++) {
                x = x + advertisingX[i];
            }
            x = x / advertisingX.length;
            for (int i = 0; i < advertisingX.length; i++) {
                y = y + salesY[i];
            }
            y = y / salesY.length;
            return y - B1() * x;
        }
        */
        public void action() {
            /*
            float b0 = B0(), b1 = B1();
            System.out.println("y = " + b0 + " + " + b1 + " * " + x + " = " + (b0 + b1 * x));
             */
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }    // END of inner class ...Behaviour
}
