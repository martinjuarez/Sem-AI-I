package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  }
  
  public class Individuo {

    private char[] chromosomes;
    private double fitness;
    private double probability;
    private double cumulativeProbabilty;

    public char[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(char[] chromosomes) {
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
    private final double factorMutacion;
    private Individuo[] ejemplos;
    private Individuo[] hijos;
    private Individuo[] offspring;

    public AlgoritmoGenetico(int individuos, int tamCrom, double factorMutacion) {
        this.individuos = individuos;
        this.tamCrom = tamCrom;
        this.factorMutacion = factorMutacion;
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
            System.out.println("Generacion: " + generacion + " Mejor historico: [" + this.imprimir_cromosoma(mejor.getChromosomes()) + "] = " + mejor.getFitness());
            generacion++;
        }

    }

    public void crearIndiviudos() {
        this.ejemplos = new Individuo[this.individuos];
        char[] temp = new char[this.tamCrom];
        int numero;
        int x = 0, y = 0;
        while (x < this.individuos) {
            while (y < this.tamCrom) {
                numero = (int) (Math.random() * 2);
                temp[y] = String.valueOf(numero).charAt(0);
                y++;
            }
            y = 0;
            this.ejemplos[x] = new Individuo();
            this.ejemplos[x].setChromosomes(temp);
            temp = new char[this.tamCrom];
            x++;
        }
    }

    public void fitness() {
        int x = 0, y = 0, z = 0;
        while (x < this.individuos) {
            while (y < this.tamCrom) {
                if (this.ejemplos[x].getChromosomes()[y] == '1') {
                    z++;
                }
                y++;
            }
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
        if (mejor.getFitness() == this.tamCrom) {
            band = true;
        }
        return band;
    }

    public void crossOver(Individuo padre1, Individuo padre2) {
        int puntoCruza;
        char[] temp = new char[this.tamCrom];
        this.offspring[0] = new Individuo();
        this.offspring[1] = new Individuo();
        puntoCruza = (int) (Math.random() * 9);
        int x = 0;
        while (x < this.tamCrom) {
            if (x < puntoCruza) {
                temp[x] = padre1.getChromosomes()[x];
            } else {
                temp[x] = padre2.getChromosomes()[x];
            }
            x++;
        }
        this.offspring[0].setChromosomes(temp);
        x = 0;
        temp = new char[this.tamCrom];
        while (x < this.tamCrom) {
            if (x < puntoCruza) {
                temp[x] = padre2.getChromosomes()[x];
            } else {
                temp[x] = padre1.getChromosomes()[x];
            }
            x++;
        }
        this.offspring[1].setChromosomes(temp);
    }

    public void mutacion() {
        int x = 0, y = 0;
        while (x < this.individuos) {
            while (y < this.tamCrom) {
                if (this.numero_random() < this.factorMutacion) {
                    if (this.hijos[x].getChromosomes()[y] == '1') {
                        this.hijos[x].getChromosomes()[y] = '0';
                    } else {
                        this.hijos[x].getChromosomes()[y] = '1';
                    }
                }
                y++;
            }
            x++;
        }
    }

    public Individuo ruleta() {
        int x = 0;
        double fitnessGlobal = 0;
        double probability;
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
        System.out.println("Hands-on 6");
        System.out.println("The MAXONE problem");
        AlgoritmoGenetico maxo = new AlgoritmoGenetico(6, 10,0.1);
        maxo.GA();
    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
