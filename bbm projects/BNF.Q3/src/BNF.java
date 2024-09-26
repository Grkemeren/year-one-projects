import java.util.Arrays;
import java.util.HashMap;


public class BNF {
    /**
     * Main method of the application.
     * @param args args[0] input file , args[1] output file.
     */
    public static void main(String[] args) {
        String[] input = FileIO.readFile(args[0],true,true);

        if (input == null) {
            System.out.println("cant access to input file ");
            return;
        }
        HashMap<String,String > nonTerminals = new HashMap<>();
        // I selected Hashmap due to easy accessing to non-terminal units with their expansion.
        Arrays.asList(input).forEach((s)-> {
            String[] arr = s.split("->");
            nonTerminals.put(arr[0],arr[1]);});
        String res = Recursion.recursion("S",nonTerminals);
        FileIO.writeToFile(args[1],res,false,false);

    }
}