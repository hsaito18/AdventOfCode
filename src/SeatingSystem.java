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
                    if (seeOccupied(graph, i,j) == 0){
                        endGraph[i][j] = '#';
                        hasChanged = true;
                    }
                }
                else if (graph[i][j] == '#'){
                    if (seeOccupied(graph,i,j) >= 5){
                        endGraph[i][j] = 'L';
                        hasChanged = true;
                    }
                }
            }
        }
        printGraph(endGraph);
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

    private static int seeOccupied(char[][] graph, int row, int col){
        int counter = 0;
        //up
        for (int i = row - 1; i >= 0; i--){
            switch (graph[i][col]){
                case '#':
                    counter++;
                    i = -1;
                    break;
                case 'L':
                    i = -1;
                    break;
                case '.':
                    break;
            }
        }
        //down
        for (int i = row + 1; i < graph.length; i++){
            switch (graph[i][col]){
                case '#':
                    counter++;
                    i = graph.length;
                    break;
                case 'L':
                    i = graph.length;
                    break;
                case '.':
                    break;
            }
        }
        //left
        for (int i = col - 1; i >= 0; i--){
            switch (graph[row][i]){
                case '#':
                    counter++;
                    i = -1;
                    break;
                case 'L':
                    i = -1;
                    break;
                case '.':
                    break;
            }
        }
        //right
        for (int i = col + 1; i < graph[row].length; i++){
            switch (graph[row][i]){
                case '#':
                    counter++;
                    i = graph[row].length;
                    break;
                case 'L':
                    i = graph[row].length;
                    break;
                case '.':
                    break;
            }
        }
        int i = 1;
        //upright
        while (row - i >= 0 && col + i < graph[0].length){
            switch (graph[row-i][col+i]) {
                case '#':
                    counter++;
                    i = graph.length+1;
                    break;
                case 'L':
                    i = graph.length+1;
                    break;
                case '.':
                    break;
            }
            i++;
        }
        //upleft
        i = 1;
        while (row - i >= 0 && col - i >= 0){
            switch (graph[row-i][col-i]) {
                case '#':
                    counter++;
                    i = graph.length + 1;
                    break;
                case 'L':
                    i = graph.length + 1;
                    break;
                case '.':
                    break;
            }
            i++;
        }

        //downleft
        i = 1;
        while (row + i < graph.length && col - i >= 0){
            switch (graph[row+i][col-i]) {
                case '#':
                    counter++;
                    i = graph.length;
                    break;
                case 'L':
                    i = graph.length;
                    break;
                case '.':
                    break;
            }
            i++;
        }

        //downright
        i = 1;
        while (row + i < graph.length && col + i < graph[0].length){
            switch (graph[row+i][col+i]) {
                case '#':
                    counter++;
                    i = graph.length+1;
                    break;
                case 'L':
                    i = graph.length+1;
                    break;
                case '.':
                    break;
            }
            i++;
        }
        return counter;
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