package opt.test;

import java.util.Arrays;
import java.util.Random;

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
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.ga.UniformCrossOver;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

/**
 * A test of the knapsack problem
 *
 * Given a set of items, each with a weight and a value, determine the number of each item to include in a
 * collection so that the total weight is less than or equal to a given limit and the total value is as
 * large as possible.
 * https://en.wikipedia.org/wiki/Knapsack_problem
 *
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class KnapsackTestIterations {
    /** Random number generator */
    private static final Random random = new Random();
    /** The number of items */
    private static final int NUM_ITEMS = 40;
    /** The number of copies each */
    private static final int COPIES_EACH = 4;
    /** The maximum value for a single element */
    private static final double MAX_VALUE = 50;
    /** The maximum weight for a single element */
    private static final double MAX_WEIGHT = 50;
    /** The maximum weight for the knapsack */
    //private static final double MAX_KNAPSACK_WEIGHT =
         //MAX_WEIGHT * NUM_ITEMS * COPIES_EACH * .4;

    private static int[] iterations = {50, 100, 200, 250, 500, 1000, 2000, 3000, 4000, 5000, 7500, 10000, 12500};
    private static int[] number_items = {5, 10, 20, 25, 40, 80, 160, 200};

    /**
     * The test main
     * @param args ignored
     */
    public static void main(String[] args) {

        for (int k = 0; k < number_items.length; k++) {
            int num_item = number_items[k];

            for(int j = 0; j < iterations.length; j++) {
                int iter = iterations[j];

                double MAX_KNAPSACK_WEIGHT = MAX_WEIGHT * num_item * COPIES_EACH * .4;

                int[] copies = new int[num_item];
                Arrays.fill(copies, COPIES_EACH);
                double[] values = new double[num_item];
                double[] weights = new double[num_item];
                for (int i = 0; i < num_item; i++) {
                    values[i] = random.nextDouble() * MAX_VALUE;
                    weights[i] = random.nextDouble() * MAX_WEIGHT;
                }
                int[] ranges = new int[num_item];
                Arrays.fill(ranges, COPIES_EACH + 1);

                EvaluationFunction ef = new KnapsackEvaluationFunction(values, weights, MAX_KNAPSACK_WEIGHT, copies);
                Distribution odd = new DiscreteUniformDistribution(ranges);
                NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);

                MutationFunction mf = new DiscreteChangeOneMutation(ranges);
                CrossoverFunction cf = new UniformCrossOver();
                Distribution df = new DiscreteDependencyTree(.1, ranges);

                double starttime = System.nanoTime();;
                double endtime;

                HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
                GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
                
                RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
                FixedIterationTrainer fit = new FixedIterationTrainer(rhc, iter);
                fit.train();
                endtime = System.nanoTime();
                double time_elapsed = endtime - starttime;
                System.out.println("RHC,"+num_item+","+iter+","+ef.value(rhc.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                SimulatedAnnealing sa = new SimulatedAnnealing(100, .99, hcp);
                fit = new FixedIterationTrainer(sa, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("SA,"+num_item+","+iter+","+ef.value(sa.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(1500, 120, 15, gap);
                fit = new FixedIterationTrainer(ga, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("GA,"+num_item+","+iter+","+ef.value(ga.getOptimal())+","+time_elapsed);

                starttime = System.nanoTime();;
                
                MIMIC mimic = new MIMIC(2000, 500, pop);
                fit = new FixedIterationTrainer(mimic, iter);
                fit.train();
                endtime = System.nanoTime();
                time_elapsed = endtime - starttime;
                System.out.println("MIMIC,"+num_item+","+iter+","+ef.value(mimic.getOptimal())+","+time_elapsed);
            }
        }
    }

}