import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class ExpenseReport {

    private static final String FILEPATH = "bags.txt";


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

    private static int solve(ArrayList<String> in) {
        int counter = 0;
        Graph<Bag> bags = makeGraph(in);
        Vertex<Bag> vGold = null;
        List<Vertex<Bag>> vertices = bags.getVertices();
        for (Vertex<Bag> v : vertices){
            if (v.getLabel().equals("shinygold")){
                vGold = v;
            }
        }

        /*HashMap<List<Vertex<Bag>>, String> jesus = Algorithms.DFSLabels(bags);
       // System.out.println(jesus.size());
        for (List<Vertex<Bag>> l : jesus.keySet()){
            if (l.contains(vGold)){
                counter++;
                //System.out.println(jesus.get(l));
            }
        }*/
        return bagSearch(vGold, bags) - 1;
    }

    private static int bagSearch(Vertex<Bag> b, Graph<Bag> g){
        List<Vertex<Bag>> inside = g.getNeighbors(b);
        if (inside.size() == 0){
            return 1;
        }
        int sum = 0;
        for (Vertex<Bag> bag : inside){
            sum += b.getContent().getMap().get(bag.getContent()) * bagSearch(bag, g);
        }
        sum++;
        return sum;
    }

    private static Graph<Bag> makeGraph(ArrayList<String> in){
        Graph<Bag> bags = new AdjacencyListGraph<Bag>();
        Set<String> pastBagNames = new HashSet<>();
        Bag gold = new Bag("shinygold");
        pastBagNames.add("shinygold");
        Vertex<Bag> vGold = new Vertex<>("shinygold", gold);
        bags.addVertex(vGold);
        for (String line : in){
            String[] parts = line.split(",");
            String[] words = parts[0].split("\\W");
            List<Vertex<Bag>> others = bags.getVertices();
            String masterLabel = words[0] + words[1];
            Vertex<Bag> masterV = null;
            Vertex<Bag> firstV = null;
            Bag masterBag = null;
            Bag firstBag = null;
            if (pastBagNames.contains(masterLabel)){
                for (Vertex<Bag> b : others) {
                    if (b.getLabel().equals(masterLabel)) {
                        masterBag = b.getContent();
                        masterV = b;

                    }
                }
            }
            else{
                masterBag = new Bag(words[0] + words[1]);
                masterV = new Vertex<Bag>(words[0] + words[1], masterBag);
                pastBagNames.add(masterLabel);
                bags.addVertex(masterV);
            }
            int firstN = 0;
            if(isNumeric(words[4])){
                firstN = Integer.parseInt(words[4]);
            }
            if(words[4].equals("no")){

            }
            else {
                String firstLabel = words[5] + words[6];
                if (pastBagNames.contains(firstLabel)) {
                    for (Vertex<Bag> b : others) {
                        if (b.getLabel().equals(firstLabel)) {
                            firstBag = b.getContent();
                            firstV = b;

                        }
                    }
                } else {
                    firstBag = new Bag(firstLabel);
                    firstV = new Vertex<Bag>(words[5] + words[6], firstBag);
                    pastBagNames.add(firstLabel);
                    bags.addVertex(firstV);
                }
                bags.addEdge(masterV, firstV);
                assert masterBag != null;
                masterBag.addBag(firstBag, firstN);
                if (parts.length > 1) {
                    for (int i = 1; i < parts.length; i++) {
                        String[] s = parts[i].split("\\W");
                        int iN = 0;
                        if (isNumeric(s[1])) {
                            iN = Integer.parseInt(s[1]);
                        }
                        Bag nBag = null;
                        Vertex<Bag> nV = null;
                        String nLabel = s[2] + s[3];
                        if (pastBagNames.contains(nLabel)) {
                            for (Vertex<Bag> b : others) {
                                if (b.getLabel().equals(nLabel)) {
                                    nBag = b.getContent();
                                    nV = b;

                                }
                            }
                        } else {
                            nBag = new Bag(nLabel);
                            nV = new Vertex<Bag>(nLabel, nBag);
                            pastBagNames.add(nLabel);
                            bags.addVertex(nV);
                        }
                        bags.addEdge(masterV, nV);
                        masterBag.addBag(nBag, iN);
                    }
                }
                if (!bags.edgeExists(masterV, firstV)) {
                    System.out.println("LOLOLOLOL!");
                }
            }

        }
        return bags;
    }

    private static int findRow(String s, int min, int max){
        if (s.equals("F")){
            return min;
        }
        if (s.equals("B")){
            return max;
        }
        int mid = (max-min)/2;
        if (s.substring(0,1).equals("F")){
            return findRow(s.substring(1),min,max-mid-1);
        }
        if (s.substring(0,1).equals("B")){
            return findRow(s.substring(1),min+mid+1, max);
        }
        return -1;
    }

    private static int findCol(String s, int min, int max){
        if (s.equals("L")){
            return min;
        }
        if (s.equals("R")){
            return max;
        }
        int mid = (max-min)/2;
        if (s.substring(0,1).equals("L")){
            return findCol(s.substring(1),min,max-mid-1);
        }
        if (s.substring(0,1).equals("R")){
            return findCol(s.substring(1),min+mid+1, max);
        }
        return -1;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
