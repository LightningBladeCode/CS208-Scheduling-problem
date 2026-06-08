import algorithms.YourAlgorithm;
import utilities.AlgorithmEvaluator;

public class Main {
    public static void main(String[] args) {
        AlgorithmEvaluator algorithmEvaluator = new AlgorithmEvaluator();
        int num = 1;

        String[] instanceFiles = {
                "resources/u_c_hihi.0", "resources/u_c_hilo.0", "resources/u_c_lohi.0",
                "resources/u_c_lolo.0", "resources/u_i_hihi.0", "resources/u_i_hilo.0",
                "resources/u_i_lohi.0", "resources/u_c_lolo.0", "resources/u_s_hihi.0",
                "resources/u_s_hilo.0", "resources/u_s_lohi.0", "resources/u_s_lolo.0"
        };
        // for each file in the instance files it goes through increments as it goes through each file and goes through the algoirthm with it
        for (String file : instanceFiles) {
            System.out.println("\nInstance:" + num);
            num = num + 1;
            algorithmEvaluator.evaluateAlgorithm(new YourAlgorithm(), file);
        }
    }
}
