import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EncodingError {
    private static final String FILEPATH = "encoding.txt";
    private static final int invalidNum = 15690279;
    public static void main(String[] args) throws IOException {
        File file = new File(FILEPATH);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.out.println("File not found.");
            return;
        }
        ArrayList<String> input = new ArrayList<>();
        String curr;
        while ((curr = br.readLine()) != null) {
            input.add(curr);
        }
        System.out.println("ANSWER: " + solve2(input));
    }

    private static int solve(ArrayList<String> in) {
        for (int i = 25; i < in.size(); i++) {
            boolean valid = false;
            for (int j = i - 25; j < i; j++) {
                for (int k = i - 24; k < i; k++) {
                    if (Integer.parseInt(in.get(j)) + Integer.parseInt(in.get(k)) ==
                        Integer.parseInt(in.get(i)) &&
                        Integer.parseInt(in.get(j)) != Integer.parseInt(in.get(k))) {
                        valid = true;
                    }
                }
            }
            if (!valid){
                return Integer.parseInt(in.get(i));
            }
        }
        return -1;
    }

    private static int solve2(ArrayList<String> in){
        for (int i = 0; i < in.size(); i++){
            for (int j = i + 1; j < in.size(); j++){
                List<String> currList = in.subList(i,j);
                if (sumArray(currList) == invalidNum){
                    return getMinArray(currList) + getMaxArray(currList);
                }
            }
        }
        return -1;
    }

    private static double sumArray(List<String> in){
        double counter = 0;
        for (String s : in){
            double i = Double.parseDouble(s);
            counter += i;
        }
        return counter;
    }

    private static int getMinArray(List<String> in){
        int min = Integer.MAX_VALUE;
        for (String s : in){
            int i = Integer.parseInt(s);
            if (i < min){
                min = i;
            }
        }
        return min;
    }

    private static int getMaxArray(List<String> in){
        int max = Integer.MIN_VALUE;
        for (String s : in){
            int i = Integer.parseInt(s);
            if (i > max){
                max = i;
            }
        }
        return max;
    }


}