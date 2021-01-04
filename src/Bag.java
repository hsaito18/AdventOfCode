import java.util.HashMap;

public class Bag {

    private String name;
    private HashMap<Bag, Integer> contains;


    public Bag(String input){
        this.name = input;
        contains = new HashMap<>();
    }

    public boolean addBag(Bag b, int number){
        if (contains.containsKey(b)){
            return false;
        }
        else{
            contains.put(b,number);
            return true;
        }
    }

    /**
     * returns the number of bags that go inside this bag.
     * @param b
     * @return
     */
    public int numberOfBag(Bag b){
        if (!contains.containsKey(b)){
            return 0;
        }
        return contains.get(b);
    }

    public String getName(){ return this.name; }

    public int numBags(){
        int counter = 0;
        for (Bag b : contains.keySet()){
            counter += contains.get(b);
        }
        return counter;
    }

    public HashMap<Bag, Integer> getMap() {
        HashMap<Bag, Integer> output = new HashMap<>();
        for (Bag b : this.contains.keySet()){
            output.put(b, contains.get(b));
        }
        return output;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Bag)){
            return false;
        }
        Bag b = (Bag) o;
        if (this.getName().equals(b.getName())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }



}
