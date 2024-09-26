import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /**
         * Main method.
         * @param args Parameters
         * args[0] product file path.
         * args[1] purchase file path.
         * args[2] output file path.
         */
        String[] products = FileInput.readFile(args[0], true, true);
        String[] Purchases = FileInput.readFile(args[1],true,true);
        GymMealMachine GMMmain = new GymMealMachine();
        // Set the output path for writing to file.
        GymMealMachine.setOutputPath(args[2]);
        // Initialize the output file.
        FileOutput.writeToFile(args[2],"",false,false);

        for (String line : products) {
            // Format the product information
            String[] productAttributes = FileInput.formatMachineInput(line);
            String name = productAttributes[0];
            float price = Float.parseFloat(productAttributes[1]);
            float protein = Float.parseFloat(productAttributes[2]);
            float carbohydrate = Float.parseFloat(productAttributes[3]);
            float fat = Float.parseFloat(productAttributes[4]);
            // Fill the machine with the product.
            int n = GMMmain.fillMachine(name, price, protein, carbohydrate, fat);
            if (n == -1) {
                FileOutput.writeToFile(args[2],"INFO: There is no available place to put " + name,true,true);
                if (GMMmain.isMachineFull()) {//if the machine is full, break the loop.
                    FileOutput.writeToFile(args[2],"INFO: The machine is full!",true,true);
                    break;
                }
            }
        }


        GMMmain.writeSlots(); // Write the current state of the machine slots to the output file.
        for (String line:Purchases) {
            String[] purchaseAttributes = FileInput.formatPurchaseInput(line); // Format the purchase information
            float money = Float.parseFloat(purchaseAttributes[0]);
            String nutritionType = purchaseAttributes[1];
            float nutritionAmount = Float.parseFloat(purchaseAttributes[2]);
            FileOutput.writeToFile(args[2],"INPUT: "+line,true,true);
            // Process the purchase and get the new balance
            int newBalance = GMMmain.findAndPurchase(money,nutritionType,nutritionAmount);
            // Write the returned change to the output file
            FileOutput.writeToFile(args[2],"RETURN: Returning your change: "+newBalance+" TL",true,true);
        }
        // Write the final state of the machine slots to the output file
        GMMmain.writeSlots();

    }







}
 


