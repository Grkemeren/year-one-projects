
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class BookingSystem {
    public static void main(String[] args) {
        String[] inputAsArray;
        try {
            if (args.length != 2) { // wrong number of arguments.
                throw new IllegalArgumentException();
            }
            inputAsArray = FileIO.readFile(args[0],true,true);
            if (inputAsArray == null) {
                throw new NoSuchFileException("No such a file.");
            }
            FileIO.writeToFile(args[1],"",false,false);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: This program works exactly with two command line arguments, the first one is the" +
                    " path to the input file whereas the second one is the path to the output file. Sample usage can" +
                    " be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            return;
        } catch (NoSuchFileException e) {
            System.out.println("ERROR: This program cannot read from the \""+ args[0] +"\", either this program" +
                    " does not have read permission to read that file or file does not exist." +
                    " Program is going to terminate!");
            return;
        } catch (Exception e) {
            System.out.println("Something went wrong while reading the file. Program is going to terminate!");
            return;
        }

        Locale.setDefault(Locale.US);
        VoyageManager manager = new VoyageManager();
        CommandReader commandReader = new CommandReader();

        ArrayList<String> input = new ArrayList<>(Arrays.asList(inputAsArray));
        Iterator<String> it = input.iterator(); // iterator for the input commands.
        StringBuffer output = new StringBuffer(10000); // output buffer.
        String lastcommand ="";
        while (it.hasNext()) {
            lastcommand = it.next();
            output.append("COMMAND: " + lastcommand + "\n");
            output.append(commandReader.readcommand(lastcommand,manager) + "\n");
        }
        if (!lastcommand.equals("Z_REPORT")) { // if the last command is not Z_REPORT we add it to the output.
            output.append(manager.getZreport() + "\n");
        }
        output.deleteCharAt(output.length()-1); // delete the last newline character.
        FileIO.writeToFile(args[1],output.toString(),true,false);
    }
}