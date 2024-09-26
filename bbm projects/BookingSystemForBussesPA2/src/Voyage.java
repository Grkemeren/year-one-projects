import java.util.ArrayList;

public abstract class Voyage {
    private int voyageId;
    private float price;
    private String fromWhere;
    private String toWhere;
    private float revenue;
    private int[] seats;
    public Voyage(int voyageId, float price, String fromWhere, String toWhere,int seatCapacity) {
        this.voyageId = voyageId;
        this.price = price;
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
        this.revenue = 0;
        this.seats = new int[seatCapacity];
    }
    public float getRevenue() {
        return revenue;
    }
    public void increaseRevenue(float amount) {
        this.revenue += amount;
    }
    public void decreaseRevenue(float amount) {
        this.revenue -= amount;
    }
    public float getPrice() {
        return price;
    }
    public String getFromWhere() {
        return fromWhere;
    }
    public String getToWhere() {
        return toWhere;
    }
    public int[] getSeats() {
        return seats;
    }

    /**
     * sells the ticket and increases the revenue.
     * @param seatNumb seat number of ticket which is going to be sold.
     * @return the amount of money that is earned.
     */
    public float sellTicket(int seatNumb) {
        seats[seatNumb-1] = 1; // seats start from 0 index at array. "1" means sold.
        revenue += price;
        return price;
    }

    /**
     * Cancels the ticket without applying the refund cut.
     * @param seatNumb seat number of ticket which is going to be cancelled.
     * @return the amount of money that is refunded.
     */
    public float cancelTicket(int seatNumb) {
        seats[seatNumb-1] = 0;
        revenue -= price;
        return price;
    }

    public abstract String getFormattedVehicleSeats();




}
