import java.util.HashMap;

public class Recursion {
    /**
     * Replaces non-terminal characters with their expansions each time the function is called.
     * if no replacement has been made (since stringConsistOfTerminals is true) returns the String.
     * @param input input String which non-terminals replaced.
     * @param nonTerminals Hashmap of non-terminals.
     * @return final String consist of terminals.
     */
    public static String recursion(String input, HashMap<String,String > nonTerminals){
        StringBuilder res= new StringBuilder(); // building output String;
        boolean stringConsistOfTerminals = true;
        for (int i = 0;i < input.length();i++) {
            char inpChar = input.charAt(i);
            if (Character.isUpperCase(inpChar)) {
                res
                        .append("(")
                        .append(nonTerminals.get(Character.toString(inpChar)))
                        .append(")");
                stringConsistOfTerminals = false;
            } else {
                res.append(inpChar);
            }
        }
        if (!stringConsistOfTerminals) {
            return recursion(res.toString(),nonTerminals);}
        else {
            return res.toString();
        }


    }
}
