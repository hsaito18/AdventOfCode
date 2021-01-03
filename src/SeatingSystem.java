import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SeatingSystem {

    private static final String FILE_PATH = "C:\\Users\\hsait\\IdeaProjects\\aoc\\seating.txt";
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File(FILE_PATH));
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();

        System.out.println("ANSWER: " + solve(list));
    }

    private static int solve (ArrayList<String> in){
        int length = in.get(0).length();
        int height = in.size();
        char[][] graph = new char[height][length];

        for(int i = 0; i < in.size(); i++){
            char[] row = in.get(i).toCharArray();
            graph[i] = row;
        }

        boolean hasChanged = true;
        while (hasChanged) {
            hasChanged = runRound(graph);
        }
        return countOccupied(graph);
    }

    private static boolean runRound(char[][] graph){
        boolean check = false;
        char[][] endGraph = makeCopy(graph);
        for (int i = 0; i < graph.length; i++){
            for (int j = 0; j < graph[i].length; j++){
                //i = row j = col
                if (graph[i][j] == 'L'){
                    if (nearOccupied(graph, i,j) == 0){
                        endGraph[i][j] = '#';
                        check = true;
                    }
                }
                else if (graph[i][j] == '#'){
                    if (nearOccupied(graph,i,j) >= 4){
                        endGraph[i][j] = 'L';
                        check = true;
                    }
                }

            }
        }
        System.out.println("RAN A ROUND");
        return check;
    }


    private static char[][] makeCopy(char[][] in){
        char[][] output = new char[in.length][in[0].length];
        for (int i = 0; i < in.length; i++){
            for (int ii = 0; ii < in[i].length; ii++){
                output[i][ii] = in[i][ii];
            }
        }
        return output;
    }


    private static int nearOccupied(char[][] graph, int row, int col){
        int counter = 0;
        for (int i = row - 1; i <= row + 1; i++){
            if (i >= 0 && i < graph.length){
                for (int j = col - 1; j <= col + 1; j++){
                    if (j >= 0 && j < graph[i].length){
                        if (graph[i][j] == '#'){
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    private static int countOccupied(char[][] graph){
        int counter = 0;
        for (int i = 0; i < graph.length; i++){
            for (int j = 0; j < graph[i].length; j++) {
             if (graph[i][j] == '#'){
                 counter++;
             }
            }
        }
        return counter;
    }
}
// test comment