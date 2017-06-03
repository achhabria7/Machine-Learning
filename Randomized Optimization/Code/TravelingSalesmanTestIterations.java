package opt.test;

import java.util.Arrays;
import java.util.Random;

import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.DiscreteUniformDistribution;
import dist.Distribution;

import opt.SwapNeighbor;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.SwapMutation;
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
public class TravelingSalesmanTestIterations {
    /** The n value */
    private static final int N = 50;

    private static int[] iterations = {5000, 10000}; //50, 100, 200, 250, 500, 1000, 2000, 3000, 4000, 5000, 7500, 10000, 12500
    private static int[] number_items = {100}; //5, 10, 20, 25, 40, 80, 160, 200
    /**
     * The test main
     * @param args ignored
     */
    public static void main(String[] args) {

        for (int k = 0; k < number_items.length; k++) {
            int num_item = number_items[k];

            for(int j = 0; j < iterations.length; j++) {
                int iter = iterations[j];

                Random random = new Random();
                // create the random points
                double[][] points = new double[num_item][2];
                for (int i = 0; i < points.length; i++) {
                    points[i][0] = random.nextDouble();
                    points[i][1] = random.nextDouble();   
                }
                // for rhc, sa, and ga we use a permutation based encoding
                TravelingSalesmanEvaluationFunction ef = new TravelingSalesmanRouteEvaluationFunction(points);
                Distribution odd = new DiscretePermutationDistribution(num_item);
                NeighborFunction nf = new SwapNeighbor();
                MutationFunction mf = new SwapMutation();
                CrossoverFunction cf = new TravelingSalesmanCrossOver(ef);
                HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
                GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);

                double starttime = System.nanoTime();;
                double endtime;
                
                RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
                FixedIterationTrainer fit = new FixedIterationTrainer(rhc, iter);
                fit.train();
                endtime = System.nanoTime();
                double time_elapsed = endtime - starttime;
                System.out.println("RHC,"+num_item+","+iter+","+ef.value(rhc.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                SimulatedAnnealing sa = new SimulatedAnnealing(1E5, .8, hcp);
                fit = new FixedIterationTrainer(sa, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("SA,"+num_item+","+iter+","+ef.value(sa.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(1500, 90, 90, gap);
                fit = new FixedIterationTrainer(ga, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("GA,"+num_item+","+iter+","+ef.value(ga.getOptimal())+","+time_elapsed);
                
                // for mimic we use a sort encoding
                ef = new TravelingSalesmanSortEvaluationFunction(points);
                int[] ranges = new int[num_item];
                Arrays.fill(ranges, num_item);
                odd = new  DiscreteUniformDistribution(ranges);
                Distribution df = new DiscreteDependencyTree(.1, ranges); 
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

                starttime = System.nanoTime();;
                
                MIMIC mimic = new MIMIC(5000, 500, pop);
                fit = new FixedIterationTrainer(mimic, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("MIMIC,"+num_item+","+iter+","+ef.value(mimic.getOptimal())+","+time_elapsed);
            }
        }
        
    }
}