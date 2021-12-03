package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  }
  
  private class Individuo {

    private char[][] chromosomes;
    private double fitness;
    private double probability;
    private double cumulativeProbabilty;

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
  }

  public class AlgoritmoGenetico {

    private final int individuos;
    private final int tamCrom;
    private final int tamVar;
    private final double factorMutacion;
    private Individuo[] ejemplos;
    private Individuo[] hijos;
    private Individuo[] offspring;

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
                    + Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[0]), 2) + ","
                    + +Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[1]), 2) + ","
                    + +Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[2]), 2) + ","
                    + +Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[3]), 2) + ","
                    + +Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[4]), 2) + ","
                    + +Integer.parseInt(this.imprimir_cromosoma(mejor.getChromosomes()[5]), 2)
                    + "] = " + mejor.getFitness());
            generacion++;
        }

    }

    public void crearIndiviudos() {
        this.ejemplos = new Individuo[this.individuos];
        char[][] temp = new char[this.tamVar][this.tamCrom];
        String temp2 = "";
        int numero;
        int x = 0, y = 0, z = 0;
        while (x < this.individuos) {
            while (y < this.tamVar) {
                numero = (int) (Math.random() * 11);
                if (numero >= 4 && numero <= 7) {
                    temp2 = "0";
                    temp2 = temp2 + Integer.toBinaryString(numero);
                } else {
                    temp2 = Integer.toBinaryString(numero);
                }
                if (numero >= 2 && numero <= 3) {
                    temp2 = "00";
                    temp2 = temp2 + Integer.toBinaryString(numero);
                }
                if (numero >= 0 && numero <= 1) {
                    temp2 = "000";
                    temp2 = temp2 + Integer.toBinaryString(numero);
                }
                while (z < this.tamCrom) {
                    temp[y][z] = temp2.charAt(z);
                    z++;
                }
                temp2 = "0";
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
        int x = 0, y = 0, z = 0, w=0;
        int[] suma = new int[this.tamVar];
        while (x < this.individuos) {
            while (y < this.tamVar) {
                suma[y] = Integer.parseInt(this.imprimir_cromosoma(this.ejemplos[x].getChromosomes()[y]), 2);
                y++;
            }
            w = suma[0] + (2 * suma[1]) - (3 * suma[2]) + suma[3] + (4 * suma[4]) + suma[5];
            z = 100-Math.abs(w-30);
            this.ejemplos[x].setFitness(z);
            z = 0;
            y = 0;
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
        if (mejor.getFitness() == 100) {
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
                    if (this.numero_random() < this.factorMutacion) {
                        if (this.hijos[x].getChromosomes()[y][z] == '1') {
                            this.hijos[x].getChromosomes()[y][z] = '0';
                        } else {
                            this.hijos[x].getChromosomes()[y][z] = '1';
                        }
                        if (Integer.parseInt(this.imprimir_cromosoma(this.hijos[x].getChromosomes()[y]), 2) > 10) {
                            this.hijos[x].getChromosomes()[y][0] = '0';
                            this.hijos[x].getChromosomes()[y][1] = '0';
                            this.hijos[x].getChromosomes()[y][2] = '0';
                            this.hijos[x].getChromosomes()[y][3] = '0';
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
        System.out.println("Hands-on 7");
        System.out.println("Solving Equations: a + 2b - 3c + d + 4e + f = 30");
        AlgoritmoGenetico solving = new AlgoritmoGenetico(6, 4,0.01,6);
        solving.GA();
    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
