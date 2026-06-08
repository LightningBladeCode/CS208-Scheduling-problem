package utilities;

import java.util.*;

public class SchedulingResults {

    private final Map<String, List<Double>> results;

    public SchedulingResults() {
        results = new HashMap<>();

        results.put("resources/u_c_hihi.0", new ArrayList<>(Arrays.asList(8460675.003243, 7524411.11682, 50616539.921875)));
        results.put("resources/u_c_hilo.0", new ArrayList<>(Arrays.asList(161805.433952, 154152.261104, 513236.09472700005)));
        results.put("resources/u_c_lohi.0", new ArrayList<>(Arrays.asList(275837.356047, 244798.105742, 1505601.3969749997)));
        results.put("resources/u_c_lolo.0", new ArrayList<>(Arrays.asList(5441.428197, 5181.085533, 17462.206042)));
        results.put("resources/u_i_hihi.0", new ArrayList<>(Arrays.asList(3513919.28125, 3022375.156798, 29878087.193359)));
        results.put("resources/u_i_hilo.0", new ArrayList<>(Arrays.asList(80755.679221, 74458.134001, 331874.164879)));
        results.put("resources/u_i_lohi.0", new ArrayList<>(Arrays.asList(120517.708863, 104309.124535, 986536.3619390001)));
        results.put("resources/u_i_lolo.0", new ArrayList<>(Arrays.asList(2785.645486, 2576.980456, 11519.224671999998)));
        results.put("resources/u_s_hihi.0", new ArrayList<>(Arrays.asList(5160342.819094, 4283543.027831, 45845697.226562)));
        results.put("resources/u_s_hilo.0", new ArrayList<>(Arrays.asList(104375.164308, 97290.831054, 368465.536317)));
        results.put("resources/u_s_lohi.0", new ArrayList<>(Arrays.asList(140284.488951, 126671.268039, 1611349.8615720002)));
        results.put("resources/u_s_lolo.0", new ArrayList<>(Arrays.asList(3806.827656, 3482.638234, 15085.517410999997)));
    }

    public Map<String, List<Double>> getResults() {
        return results;
    }
}
