import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandheldHalting {
    private static final String FILEPATH = "bootcode.txt";
    List<Integer> visited = new ArrayList<Integer>();
    public int acc = 0;

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
        System.out.println("ANSWER: " + solve(input));
    }

    private static int solve(ArrayList<String> in){
        int index = 0;
        ArrayList<Integer> results = new ArrayList<Integer>();
        for (int i = 0; i < in.size(); i++){
            String line = in.get(i);
            if (line.contains("nop")){
                String fixedLine = "jmp " + line.substring(4);
                ArrayList<String> fixed = new ArrayList<String>(in);
                fixed.remove(i);
                fixed.add(i, fixedLine);
                HandheldHalting h = new HandheldHalting();
                h.recurse(fixed, 0);
                results.add(h.acc);
            }
            else if (line.contains("jmp")){
                String fixedLine = "nop " + line.substring(4);
                ArrayList<String> fixed = new ArrayList<String>(in);
                fixed.remove(i);
                fixed.add(i, fixedLine);
                HandheldHalting h = new HandheldHalting();
                h.recurse(fixed, 0);
                results.add(h.acc);
            }
        }
        HandheldHalting h = new HandheldHalting();
        h.recurse(in, 0);
        results.add(h.acc);
        return max(results);
    }

    private void recurse(ArrayList<String> in, int index){
        if (index >= in.size()){
            return;
        }
        String command = in.get(index);
        System.out.println(index);
        String type = command.substring(0,3);
        String sign = command.substring(4,5);
        int number = Integer.parseInt(command.substring(5));
        if (visited.contains(index)){
            acc = -1;
            return;
        }
        visited.add(index);

        switch (type){
            case "nop":
                recurse(in, index+1);
                break;
            case "jmp":
                if (sign.equals("+")){
                    recurse(in, index+number);
                }
                else{
                    recurse(in, index-number);
                }
                break;
            case "acc":
                if (sign.equals("+")){
                    acc += number;
                }
                else {
                    acc -= number;
                }
                recurse(in, index + 1);
        }

    }

    private static int max(List<Integer> in){
        int max = Integer.MIN_VALUE;
        for (int i : in){
            if (i > max){
                max = i;
            }
        }
        return max;
    }
}
