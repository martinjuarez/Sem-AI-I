package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.text.DecimalFormat;
import java.util.Random;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class GradienteDescendente {

      private double objective(double x){
        return Math.pow(x,2);
      }
    
      private double derivative(double x){
        return x * 2;
      }
    
      private void gradiente_descendente(){
          Random rand = new Random();
          DecimalFormat df = new DecimalFormat("#.00000");
          double gradient, solution_eval;
          double step_size = 0.1;
          double solution = (0 + ( 2 - 0 ) * rand.nextFloat())-1;
          for(int x=0;x < 30;x++){
            gradient = derivative(solution);
            solution = solution - step_size * gradient;
            solution_eval = objective(solution);
            System.out.println(">"+x+" f("+df.format(solution)+") = "+df.format(solution_eval));
          }   
      }
  }

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
        System.out.println("Agent's action method executed");
        GradienteDescendente a = new GradienteDescendente();
        a.gradiente_descendente();
    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
