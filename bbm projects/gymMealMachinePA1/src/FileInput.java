import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileInput {
    /**
     * Reads the file at the given path and returns contents of it in a string array.
     *
     * @param path              Path to the file that is going to be read.
     * @param discardEmptyLines If true, discards empty lines with respect to trim; else, it takes all the lines from the file.
     * @param trim              Trim status; if true, trims (strip in Python) each line; else, it leaves each line as-is.
     * @return Contents of the file as a string array, returns null if there is not such a file or this program does not have sufficient permissions to read that file.
     */
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { //Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { //Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { //Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Formats the given line to an array of strings.
     *
     * @param line Line that is going to be formatted.
     * @return Array of strings that is created by splitting the line with respect to spaces.
     */
    public static String[] formatMachineInput(String line) {
        String[] lineComponents = line.split("\\s+");
        int arrayLength = lineComponents.length;

        String[] nameComponents = Arrays.copyOfRange(lineComponents,0,lineComponents.length-4);
        String name = String.join(" ",nameComponents);



        String price = lineComponents[arrayLength-4];
        String protein = lineComponents[arrayLength-3];
        String carbohydrate = lineComponents[arrayLength-2];
        String fat = lineComponents[arrayLength-1];

        return new String[]{name,price,protein,carbohydrate,fat};
    }

    /**
     * Formats the given line to an array of strings.
     *
     * @param line Line that is going to be formatted.
     * @return Array of strings that is created by splitting the line with respect to spaces.
     */
    public static String[] formatPurchaseInput(String line) {
        String[] lineComponents = line.split("\\s+");
        int arrayLength = lineComponents.length;

        int money = 0;
        for (int i = 1; i < arrayLength - 2; i++) {
            money += Integer.parseInt(lineComponents[i]);
        }
        String moneystr = String.valueOf(money);
        String nutritionType = lineComponents[arrayLength-2];
        String  nutritionAmount = lineComponents[arrayLength-1];
        return new String[]{moneystr,nutritionType,nutritionAmount};
    }
}