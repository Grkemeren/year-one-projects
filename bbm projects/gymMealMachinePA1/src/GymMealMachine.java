import java.util.ArrayList;

public class GymMealMachine {
    private GMMSlot[] slots;
    private static String outputPath; // the path of the output file.

    /**
     * Constructs a new GymMealMachine with 24 empty slots.
     */
    public GymMealMachine() {

        GMMSlot[] allslots = new GMMSlot[24];
        for (int i = 0; i < 24; i++) {
            allslots[i] = new GMMSlot(i);
        }
        this.slots = allslots;
    }

    /**
     * Fills the machine with a new product or increases the quantity of an existing product.
     * @return 1 if the product was successfully added, -1 if the machine is full.
     */
    public int fillMachine(String name, float price, float protein, float carbohydrate, float fat) {
        for (GMMSlot slot : slots) {
            if (slot.isEmpty()) {
                slot.setEmpty(false);
                slot.increaseNumberOfProducts();
                slot.setSampleProduct(new Product(name, price, protein, carbohydrate, fat));
                return 1;
            }
            if ((slot.getSampleProduct().getName().equals(name)) && slot.getNumberOfProducts() < 10) {
                slot.increaseNumberOfProducts();
                return 1;
            }
        }
        return -1;
    }

    /**
     * checks if the machine is full.
     */
    public boolean isMachineFull() {
        for (GMMSlot slot:slots) {
            if (slot.getNumberOfProducts() != 10) {
                return false;
            }
        }
        return true;
    }

    public static void setOutputPath(String outputPath) {
        GymMealMachine.outputPath = outputPath;
    }
    /**
     * Finds a suitable product based on the given nutrition type and amount.
     * @param nutritionType (PROTEIN, CARB, FAT, CALORIE).
     * @return suitable slot number if found, -1 if not found.
     */
    private int findSuitableProduct(String nutritionType , float nutritionAmount) {
        for (GMMSlot slot:slots) {
            if (!slot.isEmpty()) { // if the slot is not empty
                float nutritionAtSlotProduct;  // initialize nutrition amount of the product.
                switch (nutritionType) {
                    case "PROTEIN":
                        nutritionAtSlotProduct = slot.getSampleProduct().getProtein();
                        break;
                    case "CARB":
                        nutritionAtSlotProduct = slot.getSampleProduct().getCarbohydrate();
                        break;
                    case "FAT":
                        nutritionAtSlotProduct = slot.getSampleProduct().getFat();
                        break;
                    case "CALORIE":
                        nutritionAtSlotProduct = slot.getSampleProduct().getCalorie();
                        break;
                    default:
                        nutritionAtSlotProduct = -10;
                }
                if ((nutritionAtSlotProduct - 5 <= nutritionAmount ) && (nutritionAmount <= nutritionAtSlotProduct+5)){
                    return slot.getSlotNumber(); // if founds suitable product, return the slot number.
                }
            }
        }
        return -1;
    }

    /**
     * Purchases a product from the specified slot.
     * @param money money from the customer.
     * @param slotNumber the slot number of the product.
     * @return The remaining balance after the purchase, or -1 if the purchase was not successful.
     */
    private float purchaseProduct(float money, int slotNumber) {
        if ((slotNumber < 0 || 23 < slotNumber )) { // if the slot number is not valid.
            FileOutput.writeToFile(outputPath,"INFO: Number cannot be accepted. Please try again with another number.",true,true);
            return -1;
        }

        if (slots[slotNumber].isEmpty()) { // if the slot is empty.
            FileOutput.writeToFile(outputPath,"INFO: This slot is empty, your money will be returned.",true,true);
            return -1;
        }

        float newBalance = money-slots[slotNumber].getSampleProduct().getPrice();
        if (newBalance < 0) { // if newBalance returns -1, it means money is insufficient.
            FileOutput.writeToFile(outputPath,"INFO: Insufficient money, try again with more money.",true,true);
            return -1;
        }
        FileOutput.writeToFile(outputPath,"PURCHASE: You have bought one "+slots[slotNumber].getSampleProduct().getName(),true,true);
        slots[slotNumber].decreaseNumberOfProducts(); // decrease the numberOfProducts in the slot.
        return newBalance;
    }

    /**
     * Finds a suitable product and purchases it.
     * @param initialMoney The initial amount of money.
     * @param nutritionType (PROTEIN, CARB, FAT, CALORIE, NUMBER).
     * @return if purchase successful returns the remaining money,else returns the initial money.
     */
    public int findAndPurchase(float initialMoney,String nutritionType, float nutritionAmount) {
        int suitableSlot; // finds the suitable slot for given nutrition type and amount.
        if (nutritionType.equals("NUMBER")) {
            suitableSlot = (int)nutritionAmount;
        } else {
            suitableSlot = findSuitableProduct(nutritionType,nutritionAmount);
        }

        if (suitableSlot == -1) { //cannot find suitable product.
            FileOutput.writeToFile(outputPath,"INFO: Product not found, your money will be returned.",true,true);
            return (int)initialMoney;
        }

        float newBalance = purchaseProduct(initialMoney,suitableSlot);
        if (newBalance == -1) {
            return(int)initialMoney;
        }
        return (int)newBalance;
    }

    /**
     * Writes the current state of the machine slots to the output file.
     */
    public void writeSlots() {
        ArrayList<String> outputText = new ArrayList<>();
        outputText.add("-----Gym Meal Machine-----\n");
        for (GMMSlot slot : slots) {
            if (slot.isEmpty()) {
                outputText.add("___(0, 0)___");
            } else {
                Product product = slot.getSampleProduct();
                outputText.add(product.getName() + "(" + Math.round(product.getCalorie()) + ", " + slot.getNumberOfProducts() + ")___");
            }
            if ((slot.getSlotNumber()+1) % 4 == 0){
                outputText.add("\n");
            }
        }
        outputText.add("----------");
        FileOutput.writeToFile(outputPath,String.join("",outputText),true,true);

    }
}



