package opt.test;

import java.util.Arrays;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;

import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.SingleCrossOver;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

/**
 * 
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class ContinuousPeaksTestIterations {
    /** The n value */
    //private static final int N = 60;
    private static int[] iterations = {50, 100, 200, 250, 500, 1000, 2000, 3000, 4000, 5000, 7500, 10000, 12500};
    private static int[] number_items = {250, 500}; //5, 10, 20, 25, 40, 80, 160, 200
    /** The t value */
    //private static final int T = N / 10;
    
    public static void main(String[] args) {

        for (int k = 0; k < number_items.length; k++) {
            int num_item = number_items[k];

            for(int j = 0; j < iterations.length; j++) {
                int iter = iterations[j];

                int T = num_item / 10;
                int[] ranges = new int[num_item];
                Arrays.fill(ranges, 2);
                EvaluationFunction ef = new ContinuousPeaksEvaluationFunction(T);
                Distribution odd = new DiscreteUniformDistribution(ranges);
                NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
                MutationFunction mf = new DiscreteChangeOneMutation(ranges);
                CrossoverFunction cf = new SingleCrossOver();
                Distribution df = new DiscreteDependencyTree(.1, ranges); 
                HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
                GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

                double starttime = System.nanoTime();;
                double endtime;
                
                RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
                FixedIterationTrainer fit = new FixedIterationTrainer(rhc, iter);
                fit.train();
                endtime = System.nanoTime();
                double time_elapsed = endtime - starttime;
                System.out.println("RHC,"+num_item+","+iter+","+ef.value(rhc.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                SimulatedAnnealing sa = new SimulatedAnnealing(1E15, .95, hcp);
                fit = new FixedIterationTrainer(sa, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("SA,"+num_item+","+iter+","+ef.value(sa.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(2500, 150, 150, gap);
                fit = new FixedIterationTrainer(ga, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("GA,"+num_item+","+iter+","+ef.value(ga.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                MIMIC mimic = new MIMIC(5000, 100, pop);
                fit = new FixedIterationTrainer(mimic, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("MIMIC,"+num_item+","+iter+","+ef.value(mimic.getOptimal())+","+time_elapsed);
            }
        }
    }
}