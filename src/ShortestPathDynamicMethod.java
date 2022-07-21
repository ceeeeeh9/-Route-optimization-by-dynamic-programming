import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ShortestPathDynamicMethod {  

    public static int[][] readDistancesFromFile() throws FileNotFoundException {   

        File f1 = new File("D:\\Routes.txt");
        BufferedReader input = new BufferedReader(new FileReader(f1));
        BufferedReader input1 = new BufferedReader(new FileReader(f1));
        int NUMBER_CITIES = 0;
        try {
            while ((input1.readLine()) != null) {
                NUMBER_CITIES++;
            }
            input1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] is = new int[NUMBER_CITIES++][NUMBER_CITIES++];
        int[][] array = is;
        int i = 0;
        try {
            String line = null;
            while ((line = input.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                int j = 0;
                while (st.hasMoreTokens()) {
                    String tkn = st.nextToken();
                    array[i][j] = Integer.parseInt(tkn);
                    j = j + 1;
                }
                i = i + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static int getShortestDistance(int[][] dist) {    

        List<String> cityList = new ArrayList<String>();
        cityList.add("Гродно");
        cityList.add("Скидель");
        cityList.add("Радунь");
        cityList.add("Вороново");
        cityList.add("Ивье");
        cityList.add("Лида");
        cityList.add("Щучин");
        cityList.add("Мосты");
        cityList.add("Дятлово");
        cityList.add("Новогрудок");
        cityList.add("Кореличи");
        cityList.add("Козловщина");
        cityList.add("Новоельня");
        cityList.add("Желудок");
        
        int n = dist.length;
        int[][] dp = new int[1 << n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE / 2);
        dp[1][0] = 0;
        for (int mask = 1; mask < 1 << n; mask += 2) {
            for (int i = 1; i < n; i++) {
                if ((mask & 1 << i) != 0) {
                    for (int j = 0; j < n; j++) {
                        if ((mask & 1 << j) != 0) {
                            dp[mask][i] = Math.min(dp[mask][i], dp[mask ^ (1 << i)][j] + dist[j][i]);
                        }
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            res = Math.min(res, dp[(1 << n) - 1][i] + dist[i][0]);
        }
        int cur = (1 << n) - 1;
        int[] order = new int[n];
        int last = 0;
        for (int i = n - 1; i >= 1; i--) {
            int bj = -1;
            for (int j = 1; j < n; j++) {
                if ((cur & 1 << j) != 0 && (bj == -1 || dp[cur][bj] + dist[bj][last] > dp[cur][j] + dist[j][last])) {
                    bj = j;
                }
            }
            order[i] = bj;
            cur ^= 1 << bj;
            last = bj;
        }
        System.out.println("Порядок обхода городов: ");
        for (int i = 0; i < order.length; i++)
            System.out.println((i + 1) + " " + cityList.get(order[i]));
        return res;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Минимальное расстояние: " + getShortestDistance(ShortestPathDynamicMethod.readDistancesFromFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ShortestPathDynamicMethod []";
    }
}