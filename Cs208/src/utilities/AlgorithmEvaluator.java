package utilities;

import algorithms.SchedulingAlgorithm;

import java.util.concurrent.*;


public class AlgorithmEvaluator {

    private final static SchedulingResults RESULTS = new SchedulingResults();

    private final JobReader jobReader;
    private String pathToFile;

    public AlgorithmEvaluator() {
        jobReader = new JobReader();
        pathToFile = "resources/u_c_hihi.0";
    }

    public void evaluateAlgorithm(SchedulingAlgorithm algorithm, String pathToFile) {
        this.pathToFile = pathToFile;
        jobReader.readFileIntoETCMatrix(pathToFile);
        String grade = "0";

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<double[]> result = executorService.submit(() ->
                algorithm.runAlgorithm(jobReader.getEtcMatrix()));

        try {
            double[] processorTimes = result.get(270, TimeUnit.SECONDS);

            double makespan = calculateMakespan(processorTimes);

            grade = calculateGrade(makespan);
        } catch (TimeoutException e) {
            System.out.println("Algorithm time exceeded!");
            result.cancel(true);
            grade = "0";
        } catch (Exception e) {
            System.out.println("Error running algorithm. " + e.getMessage());
        }
        executorService.shutdown();
        printGradeEvaluation(grade, algorithm);
    }

    public String getGrade(double makespan) {
        return calculateGrade(makespan);
    }

    private double calculateMakespan(double[] processorTimes) {

        // Calculate the makespan of the solution
        double makespan = 0;
        for (double processorTime : processorTimes) {
            makespan = Math.max(makespan, processorTime);
        }
        return makespan;
    }

    private String calculateGrade(double makespan) {
        double grade;
        double baselineMakeSpan = RESULTS.getResults().get(pathToFile).get(0);
        double lowestMakeSpan = RESULTS.getResults().get(pathToFile).get(1);
        double highestMakeSpan = RESULTS.getResults().get(pathToFile).get(2);

        System.out.println("Makespan is : " + makespan);

        if (makespan > baselineMakeSpan) {
            return String.valueOf(Math.round((highestMakeSpan - makespan) / (highestMakeSpan - baselineMakeSpan) * 40));
        } else if (makespan > lowestMakeSpan) {
            grade = 100 - ((makespan - lowestMakeSpan) / (baselineMakeSpan - lowestMakeSpan)) * 60;
            return String.valueOf(Math.round(grade));
        }
        grade = 100;
        return String.valueOf(Math.round(grade));
    }

    private void printGradeEvaluation(String grade, SchedulingAlgorithm algorithm) {
        System.out.println("------------------------------");
        System.out.println("Hello " + algorithm.getName() + "!");
        System.out.println("File selected: " + pathToFile);
        System.out.println("Your algorithm would currently achieve a grade of " + grade + "%");
        System.out.println("NOTE: This is not an official grade, only an estimate.");
        System.out.println("------------------------------");
    }
}
