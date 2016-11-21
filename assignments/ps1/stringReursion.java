
public class stringReursion {

    public static void printReverse(String str) {
        if (str==null || str=="")
            return;
        
        int length=str.length();
        
        if (length>1)
            printReverse(str.substring(1,length)); 
        
        System.out.print(str.charAt(0)); 
        
        if (length==1)
            return;
    }
        
    public static String trim(String str) {
        
        if (str == null) 
            return null;
        else if (str.equals(' '))
            return str;
        else if (str.length()==0)
            return "";
        
        char first = str.charAt(0);
        int length = str.length()-1;
        char last = str.charAt(length);
        String st = "";
        
        if (first == ' ')
           st = trim(str.substring(1));
        else {
           st = str; 
           if (last == ' ')
              st = trim(str.substring(0,length-1));
           else
               st = str;
        }
        
        return st;
    }
    
    public static void main(String[] args) {
        
        System.out.println("Testing printReverse method - ");
        System.out.println("input : harvard");
        System.out.print("output : ");
        printReverse("harvard");
        System.out.println();
        System.out.println("input : Hello World");
        System.out.print("output : ");
        printReverse("Hello World");
        System.out.println();
        
        System.out.println();
        
        System.out.println("Testing trim method - ");
        System.out.println("input :      harvard       ");
        String result1 = trim("     harvard       ");
        System.out.println(result1);
        System.out.println("input :          Star Trek Beyond          ");
        String result2 = trim("          Star Trek Beyond          ");
        System.out.println(result2);
        
 
    }
}