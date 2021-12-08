package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.text.DecimalFormat;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class DataSet {

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

  private class r_square {

    /*Clase r_square encargada de calcular y predecir los valores de X para el data set */
    private final DataSet Benetton;
    private final double n;

    public r_square(DataSet data) {
        /*Constructor de la clase r_square*/
        this.Benetton = data;
        this.n = (double) Benetton.getAdvertisingX().length;
    }

    public double y_(int beta_cero, int beta_uno) {
        double sumatoria = 0;
        var ygriega = Benetton.getSalesY();
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + ygriega[i];
        }
        return sumatoria / 9.0;

    }

    public double r_2(int beta_cero, int beta_uno) {
        double sumatoria = 0, SSres = 0, SStot = 0, y_ = this.y_(beta_cero, beta_uno);
        var equis = Benetton.getAdvertisingX();
        var ygriega = Benetton.getSalesY();
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + Math.pow(ygriega[i] - (beta_cero + beta_uno * equis[i]), 2);

        }
        SSres = sumatoria;
        sumatoria = 0;
        for (int i = 0; i < n; i++) {
            sumatoria = sumatoria + Math.pow(ygriega[i] - y_, 2);

        }
        SStot = sumatoria;
        return 1 - (SSres / SStot);
    }
  }

  private class Individuo {

    private char[][] chromosomes;
    private double fitness;
    private double probability;
    private double cumulativeProbabilty;
    private int beta_cero;
    private int beta_uno;

    public char[][] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(char[][] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getCumulativeProbabilty() {
        return cumulativeProbabilty;
    }

    public void setCumulativeProbabilty(double cumulativeProbabilty) {
        this.cumulativeProbabilty = cumulativeProbabilty;
    }

    public int getBeta_cero() {
        return beta_cero;
    }

    public void setBeta_cero(int beta_cero) {
        this.beta_cero = beta_cero;
    }

    public int getBeta_uno() {
        return beta_uno;
    }

    public void setBeta_uno(int beta_uno) {
        this.beta_uno = beta_uno;
    }

  }

  private class AlgoritmoGenetico {

    private final int individuos;
    private final int tamCrom;
    private final int tamVar;
    private final double factorMutacion;
    private final DataSet ayuda = new DataSet();
    private final r_square ayuda2 = new r_square(ayuda);
    private Individuo[] ejemplos;
    private Individuo[] hijos;
    private Individuo[] offspring;
    DecimalFormat df = new DecimalFormat("#.00000");

    public AlgoritmoGenetico(int individuos, int tamCrom, double factorMutacion, int tamVar) {
        this.individuos = individuos;
        this.tamCrom = tamCrom;
        this.factorMutacion = factorMutacion;
        this.tamVar = tamVar;
    }

    public void GA() {
        this.crearIndiviudos();
        this.fitness();
        Individuo padre1, padre2;
        this.hijos = new Individuo[this.individuos];
        this.offspring = new Individuo[2];
        int generacion = 1;
        int x = 0;
        Individuo mejor = this.evaluarIndividuos();
        while (criterianSatisfied(mejor) == false) {
            while (x < this.individuos) {
                padre1 = this.ruleta();
                padre2 = this.ruleta();
                while (padre1 == padre2) {
                    padre2 = this.ruleta();
                }
                this.crossOver(padre1, padre2);
                this.hijos[x] = this.offspring[0];
                x++;
                this.hijos[x] = this.offspring[1];
                x++;
            }
            this.mutacion();
            this.ejemplos = this.hijos;
            this.ejemplos[(int) (Math.random() * this.individuos)] = mejor;
            this.fitness();
            mejor = this.evaluarIndividuos();
            System.out.println("Generacion: " + generacion + " Mejor historico: ["
                    + mejor.getBeta_cero() + "," + mejor.getBeta_uno() + "] = "
                    + df.format(mejor.getFitness()));
            generacion++;

        }

    }

    public void crearIndiviudos() {
        this.ejemplos = new Individuo[this.individuos];
        char[][] temp = new char[this.tamVar][this.tamCrom];
        char temp2;
        int numero;

        int x = 0, y = 0, z = 0;
        while (x < this.individuos) {
            while (y < this.tamVar) {
                while (z < this.tamCrom) {
                    numero = (int) (Math.random() * 2);
                    temp2 = String.valueOf(numero).charAt(0);
                    temp[y][z] = temp2;
                    z++;
                }
                z = 0;
                y++;
            }
            y = 0;
            this.ejemplos[x] = new Individuo();
            this.ejemplos[x].setChromosomes(temp);
            temp = new char[this.tamVar][this.tamCrom];
            x++;
        }
    }

    public void fitness() {
        int x = 0, y = 0;
        int[] suma = new int[this.tamVar];
        while (x < this.individuos) {
            while (y < this.tamVar) {
                suma[y] = Integer.parseInt(this.imprimir_cromosoma(this.ejemplos[x].getChromosomes()[y]), 2);
                y++;
            }
            y=0;
            this.ejemplos[x].setBeta_cero(suma[0]);
            this.ejemplos[x].setBeta_uno(suma[1]);
            
            this.ejemplos[x].setFitness(ayuda2.r_2(suma[0], suma[1]));
            x++;
        }
    }

    public Individuo evaluarIndividuos() {
        int x = 0, pos = 0;
        double temp = 0;
        while (x < this.individuos) {
            if (this.ejemplos[x].getFitness() > temp) {
                temp = this.ejemplos[x].getFitness();
                pos = x;
            }
            x++;
        }
        return this.ejemplos[pos];
    }

    public boolean criterianSatisfied(Individuo mejor) {
        boolean band = false;
        if (mejor.getFitness() >= .97313 && mejor.getFitness() <=1) {
            band = true;
        }
        return band;
    }

    public void crossOver(Individuo padre1, Individuo padre2) {
        int puntoCruza;
        char[][] temp = new char[this.tamVar][this.tamCrom];
        this.offspring[0] = new Individuo();
        this.offspring[1] = new Individuo();
        puntoCruza = (int) (Math.random() * this.tamCrom);
        int x = 0, y = 0;
        while (x < this.tamVar) {
            while (y < this.tamCrom) {
                if (y < puntoCruza) {
                    temp[x][y] = padre1.getChromosomes()[x][y];
                } else {
                    temp[x][y] = padre2.getChromosomes()[x][y];
                }
                y++;
            }
            y = 0;
            x++;
        }
        this.offspring[0].setChromosomes(temp);
        x = 0;
        temp = new char[this.tamVar][this.tamCrom];
        while (x < this.tamVar) {
            while (y < this.tamCrom) {
                if (x < puntoCruza) {
                    temp[x][y] = padre2.getChromosomes()[x][y];
                } else {
                    temp[x][y] = padre1.getChromosomes()[x][y];
                }
                y++;
            }
            y = 0;
            x++;
        }
        this.offspring[1].setChromosomes(temp);
    }

    public void mutacion() {
        int x = 0, y = 0, z = 0;
        while (x < this.individuos) {
            while (y < this.tamVar) {
                while (z < this.tamCrom) {
                    if (Math.random()  > this.factorMutacion) {
                        if (this.hijos[x].getChromosomes()[y][z] == '1') {
                            this.hijos[x].getChromosomes()[y][z] = '0';
                        } else {
                            this.hijos[x].getChromosomes()[y][z] = '1';
                        }
                    }
                    z++;
                }
                z = 0;
                y++;
            }
            y = 0;
            x++;
        }
    }

    public Individuo ruleta() {
        int x = 0;
        double fitnessGlobal = 0;
        double comulative;
        double numeroRandom = this.numero_random();
        while (x < this.individuos) {
            fitnessGlobal = fitnessGlobal + this.ejemplos[x].getFitness();
            x++;
        }
        x = 0;
        while (x < this.individuos) {
            this.ejemplos[x].setProbability(this.ejemplos[x].getFitness() / fitnessGlobal);
            x++;
        }
        comulative = this.ejemplos[0].getProbability();
        this.ejemplos[0].setCumulativeProbabilty(comulative);
        x = 1;
        while (x < this.individuos) {
            comulative = comulative + this.ejemplos[x].getProbability();
            this.ejemplos[x].setCumulativeProbabilty(comulative);
            x++;
        }
        x = 0;
        while (numeroRandom > this.ejemplos[x].getCumulativeProbabilty()) {
            x++;
        }
        if (x != 0) {
            x--;
        }
        return this.ejemplos[x];
    }

    public String imprimir_cromosoma(char[] chromosomes) {
        int x = 0;
        String temp = "";
        while (x < this.tamCrom) {
            temp = temp + chromosomes[x];
            x++;
        }
        return temp;
    }

    public double numero_random() {
        return Math.random();
    }
  }

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
        System.out.println("Agent's action method executed");
        System.out.println("Hands-on 5");
        System.out.println("Simple Linear Regression Caso de Benetton ");
        AlgoritmoGenetico bennetton = new AlgoritmoGenetico(6, 8,0.01,2);
        bennetton.GA();

    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
