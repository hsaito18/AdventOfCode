public class test {

    public static void main(String[] args){
        System.out.println("Hello World");
        System.out.println("Second print.");
        System.out.println(fibonacci(10));
    }

    private static int fibonacci(int n){
        if ((n == 0) || (n==1)){
            return n;
        }
        else{
            return fibonacci(n-1) + fibonacci(n-2);
        }
    }
}
