import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterArray {
    private static final String FILEPATH = "joltage.txt";
    private static int one = 0;
    private static int three = 0;

    public static void main(String[] args) throws IOException {
        one = 0;
        three = 0;
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
        System.out.println("ANSWER: " + solve(input));
    }

    private static int solve(ArrayList<String> in) {
        int max = findMax(in);
        System.out.println("MAX : " + max);
        ArrayList<Integer> adapters = new ArrayList<Integer>();
        for (String s : in){
            adapters.add(Integer.parseInt(s));
        }
        adapters.add(max);
        recurseAdapter(0, adapters, max);
        return one * three;
    }

    private static void recurseAdapter(int start, ArrayList<Integer> adapters, int max){
        if (start == max){
            return;
        }
        int minJump = 4;
        int next = -1;
        for (int i = 0; i < adapters.size(); i++){
            if (adapters.get(i) - start < minJump){
                minJump = adapters.get(i) - start;
                next = i;
            }
        }
        if (minJump == 1){
            one++;
        }
        else if (minJump == 3){
            three++;
        }
        int joltage = adapters.remove(next);
        System.out.println("START: " + start + " NEXT: " + joltage);
        recurseAdapter(joltage, adapters, max);
    }

    private static int findMax(ArrayList<String> in){
        int max = Integer.MIN_VALUE;
        for (String s : in){
            int curr = Integer.parseInt(s);
            if (curr > max){
                max = curr;
            }
        }
        return max + 3;
    }
}
