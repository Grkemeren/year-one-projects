public class GMMSlot {
    private int slotNumber;
    private Product sampleProduct;
    private int numberOfProducts;
    private boolean isEmpty;


    public GMMSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.sampleProduct = null;
        this.numberOfProducts = 0;
        this.isEmpty = true;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }


    public void decreaseNumberOfProducts() {
        numberOfProducts -= 1;
        if (numberOfProducts == 0) {
            sampleProduct = null;
            isEmpty = true;
        }
    }
    public void increaseNumberOfProducts(){

        numberOfProducts += 1;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Product getSampleProduct() {
        return sampleProduct;
    }

    public void setSampleProduct(Product sampleProduct) {
        this.sampleProduct = sampleProduct;
    }

}