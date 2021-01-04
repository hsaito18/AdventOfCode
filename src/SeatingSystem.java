import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SeatingSystem {

    private static final String FILE_PATH = "C:\\Users\\hsait\\IdeaProjects\\AdventOfCode\\seating.txt";
    private boolean hasChanged;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File(FILE_PATH));
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        SeatingSystem ss = new SeatingSystem();
        System.out.println("ANSWER: " + ss.solve(list));
    }

    private int solve(ArrayList<String> in){
        int length = in.get(0).length();
        int height = in.size();
        char[][] graph = new char[height][length];

        for(int i = 0; i < in.size(); i++){
            char[] row = in.get(i).toCharArray();
            graph[i] = row;
        }
        this.hasChanged = true;
        while (this.hasChanged) {
            graph = runRound(graph);
        }
        return countOccupied(graph);
    }

    private char[][] runRound(char[][] graph){
        this.hasChanged = false;
        char[][] endGraph = makeCopy(graph);
        for (int i = 0; i < graph.length; i++){
            for (int j = 0; j < graph[i].length; j++){
                //i = row j = col
                if (graph[i][j] == 'L'){
                    if (nearOccupied(graph, i,j) == 0){
                        endGraph[i][j] = '#';
                        hasChanged = true;
                    }
                }
                else if (graph[i][j] == '#'){
                    if (nearOccupied(graph,i,j) >= 4){
                        endGraph[i][j] = 'L';
                        hasChanged = true;
                    }
                }
            }
        }
        //printGraph(endGraph);
        //System.out.println("RAN A ROUND");
        return endGraph;
    }


    private static char[][] makeCopy(char[][] in){
        char[][] output = new char[in.length][in[0].length];
        for (int i = 0; i < in.length; i++){
            System.arraycopy(in[i], 0, output[i], 0, in[i].length);
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
                            if (j != col || i != row) {
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    private static int countOccupied(char[][] graph){
        int counter = 0;
        for (char[] chars : graph) {
            for (char aChar : chars) {
                if (aChar == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    private void printGraph(char[][] graph){
        for (int i = 0; i < graph.length; i++){
            for (int j = 0; j < graph[i].length; j++){
                System.out.print(graph[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
// test comment