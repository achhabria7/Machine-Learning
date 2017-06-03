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
//import java.text.DecimalFormat;

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
public class KnapsackTest {
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
    private static final double MAX_KNAPSACK_WEIGHT =
         MAX_WEIGHT * NUM_ITEMS * COPIES_EACH * .4;

    private static double[] temps = {1e2, 1e5, 1E8, 1E10, 1E12, 1E15, 1E17, 1E20};
    private static double[] coolingRates = {0.8, 0.9, 0.95, 0.99, 0.999};
    //private static DecimalFormat df = new DecimalFormat("0.000");

    private static int[] populationRatios = {100, 200, 300, 400, 500, 750, 1000, 1500};
    private static double[] mateRatios = {0.01, 0.02, 0.04, 0.06, 0.08, 0.1};
    private static double[] mutateRatios = {0.01, 0.02, 0.04, 0.06, 0.08, 0.1};

    private static int[] samples = {100, 200, 400, 500, 1000, 2000, 5000};
    private static int[] toKeep = {50, 100, 200, 500, 1000, 2500};

    private static double[] iterations ={50, 100, 200, 250, 500, 1000, 2000, 3000, 4000, 5000, 7500, 10000};

    /**
     * The test main
     * @param args ignored
     */
    public static void main(String[] args) {
        int[] copies = new int[NUM_ITEMS];
        Arrays.fill(copies, COPIES_EACH);
        double[] values = new double[NUM_ITEMS];
        double[] weights = new double[NUM_ITEMS];
        for (int i = 0; i < NUM_ITEMS; i++) {
            values[i] = random.nextDouble() * MAX_VALUE;
            weights[i] = random.nextDouble() * MAX_WEIGHT;
        }
        int[] ranges = new int[NUM_ITEMS];
        Arrays.fill(ranges, COPIES_EACH + 1);

        EvaluationFunction ef = new KnapsackEvaluationFunction(values, weights, MAX_KNAPSACK_WEIGHT, copies);
        Distribution odd = new DiscreteUniformDistribution(ranges);
        NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);

        MutationFunction mf = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction cf = new UniformCrossOver();
        Distribution df = new DiscreteDependencyTree(.1, ranges);

        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);


        System.out.println ("===========RandomizedHillClimbing=========");
        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
        FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 1000);
        fit.train();
        System.out.println(ef.value(rhc.getOptimal()));


        System.out.println ("===========SimulatedAnnealing=========");

        for (int x = 0; x < temps.length; x++) {
            double temp = temps[x];

            for (int y = 0; y < coolingRates.length; y++) {
                double cooling = coolingRates[y];
        
                SimulatedAnnealing sa = new SimulatedAnnealing(temp, cooling, hcp);
                fit = new FixedIterationTrainer(sa, 1000);
                fit.train();
                System.out.println(temp+","+cooling+","+ef.value(sa.getOptimal()));
            }
        }

        System.out.println("===========GeneticAlgorithmProblem=========");

        for (int x = 0; x < populationRatios.length; x++) {
            int population = populationRatios[x];

            for (int y = 0; y < mateRatios.length; y++) {
                int mate = (int) (Math.max(mateRatios[y] * population, 10));

                for (int z = 0; z < mutateRatios.length; z++) {
                    int mutate = (int) (Math.max(mutateRatios[z] * population, 10));
    
                    StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(population, mate, mutate, gap);
                    fit = new FixedIterationTrainer(ga, 1000);
                    fit.train();
                    System.out.println(population+","+mate+","+mutate+","+ef.value(ga.getOptimal()));
                }
            }
        }

        System.out.println("===========MIMIC=========");

        for(int x = 0; x < samples.length; x++) {
            int sample = samples[x];

            for (int y = 0; y < toKeep.length; y++) {

                int keeping = toKeep[y];
                if (keeping < sample) {
        
                    MIMIC mimic = new MIMIC(sample, keeping, pop);
                    fit = new FixedIterationTrainer(mimic, 1000);
                    fit.train();
                    System.out.println(sample+","+keeping+","+ef.value(mimic.getOptimal()));
                }
            }
        }
    }
}