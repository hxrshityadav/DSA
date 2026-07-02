import java.util.Scanner;

class GFG {
    
    static void printGFG(int n){
        if(n==0){
            return;
        }
        
        System.out.print("GFG ");
        printGFG(n-1);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        printGFG(n);
        
    }
}