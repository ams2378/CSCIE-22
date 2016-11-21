
public class generatesum {
    
    static int num=0;

    public static void generateSums(int n) {
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 1; j <= i; j++) {
                sum = sum + j; // how many times is this executed?
            }
            System.out.println(sum);
        }
    }
    
    
    public static void generateSums2(int n) {
        int sum=0;
        int i=1;
        
        do {
            sum = sum + i;
            System.out.println(sum);
            i++;
        } while (i<=n);
    }
    
    public static void main(String[] arr) { 
        //int[] orig = new int[NUM_ELEMENTS];
        //for (int i = 0; i < NUM_ELEMENTS; i++) {
        //    orig[i] = (int)(50 * Math.random());
        //}
        //printArray(orig);
        
        generateSums(25);
        generateSums2(25);
        

    }
}