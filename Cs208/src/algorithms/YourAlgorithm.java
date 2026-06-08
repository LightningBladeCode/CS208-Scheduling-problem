package algorithms;
import java.util.ArrayList;
import java.util.List;

public class YourAlgorithm extends SchedulingAlgorithm {

    private String studentName = "Rayane Touileb";

    /**
     * Fill in this method for your submission. You have a maximum of 4.5 minutes of processing time.
     * If your submission exceeds this time, then your grade will be penalised.
     *
     * @param etcMatrix The Estimate To Compute Matrix (ETC Matrix) of the chosen file.
     * @return The total amount of time required for each processor
     */
    @Override
    public double[] runAlgorithm(double[][] etcMatrix) {
        int numberOfProcessors = etcMatrix.length; // The number of processors
        int numberOfTasks = etcMatrix[0].length; // The number of jobs
        double[] processorTimes = new double[numberOfProcessors]; // Stores the total time each processor takes
        int[] JobAssignment = new int[numberOfTasks]; // Stores which processor each job is assigned to
        boolean[] assignedJobs = new boolean[numberOfTasks]; // Tracks if a job has been assigned

        // MinMin section
        for (int i = numberOfTasks - 1; i > 0; i--) {
            // have these on -1 to show not selected yet
            int selectedJob = -1, selectedProcessor = -1;
            //just have this so it's a starting reference to find completion times faster than this
            double minCompletionTime = Double.MAX_VALUE;


            for (int job = 0; job < numberOfTasks; job++) {
                if (!assignedJobs[job]) {
                    for (int processor = 0; processor < numberOfProcessors; processor++) {
                        double completionTime = processorTimes[processor] + etcMatrix[processor][job];
                        if (completionTime < minCompletionTime) {
                            minCompletionTime = completionTime;
                            selectedJob = job;
                            selectedProcessor = processor;
                        }
                    }
                }
            }

            // Assign the best job found to the best processor provided we have selected one
            if (selectedJob != -1) {
                assignedJobs[selectedJob] = true;
                JobAssignment[selectedJob] = selectedProcessor;
                processorTimes[selectedProcessor] += etcMatrix[selectedProcessor][selectedJob];
            }
        }

        // Local Search section
        boolean improved;
        do {
            improved = false;
            int maxLoadProcessor = getMaxLoadProcessor(processorTimes); // Find the processor with the most work

            List<Integer> HardJobs = new ArrayList<>();
            for (int job = 0; job < numberOfTasks; job++) {
                if (JobAssignment[job] == maxLoadProcessor) {
                    HardJobs.add(job); // Get all jobs on the busiest processor
                }
            }

            // Try swapping jobs to balance the workload
            for (int job1 : HardJobs) {
                for (int job2 = 0; job2 < numberOfTasks; job2++) {
                    if (JobAssignment[job2] != maxLoadProcessor) { // Make sure we swap with a different processor
                        int processor1 = JobAssignment[job1];
                        int processor2 = JobAssignment[job2];

                        double oldMakespan = getMakespan(processorTimes); // Get the current worst-case workload
                        double newMakespan = simulateJobSwap(job1, job2, processor1, processor2, processorTimes, etcMatrix);

                        if (newMakespan < oldMakespan) { // If swapping improves the workload, apply the swap
                            applyJobSwap(job1, job2, processor1, processor2, processorTimes, etcMatrix, JobAssignment);
                            improved = true;
                            break; // Only swap one pair per round
                        }
                    }
                }
            }

        } while (improved); // Keep swapping while improvements can be found

        return processorTimes; // Return the final results with the jobs completed with the best processors
    }



   //finds the processor with the most workload
    private int getMaxLoadProcessor(double[] processorTimes) {
        int maxProcessor = 0;
        double maxTime = processorTimes[0];

        for (int i = 1; i < processorTimes.length; i++) {
            if (processorTimes[i] > maxTime) {
                maxProcessor = i;
                maxTime = processorTimes[i];
            }
        }
        return maxProcessor;
    }


    //function responsible for simulating if jobs were to be swapped around and return the worsecase
    private double simulateJobSwap(int job1, int job2, int processor1, int processor2, double[] processorTimes, double[][] etcMatrix) {
        double newTime1 = processorTimes[processor1] - etcMatrix[processor1][job1] + etcMatrix[processor1][job2];
        double newTime2 = processorTimes[processor2] - etcMatrix[processor2][job2] + etcMatrix[processor2][job1];
        return Math.max(newTime1, newTime2);
    }

  // responsible for actually swapping the job amongst the processors
    private void applyJobSwap(int job1, int job2, int processor1, int processor2, double[] processorTimes, double[][] etcMatrix, int[] jobAssignment) {
        processorTimes[processor1] = processorTimes[processor1] - etcMatrix[processor1][job1] + etcMatrix[processor1][job2];
        processorTimes[processor2] = processorTimes[processor2] - etcMatrix[processor2][job2] + etcMatrix[processor2][job1];
        jobAssignment[job1] = processor2;
        jobAssignment[job2] = processor1;
    }

    // Calculates the makespan which is the longest processing time among all processors.
    private double getMakespan(double[] processorTimes) {
        double maxTime = 0;
        for (double time : processorTimes) {
            maxTime = Math.max(maxTime, time);
        }
        return maxTime;
    }

// was here before just so actually displays my name when the Algorithm runs
    @Override
    public String getName() {
        return studentName;
    }
}
