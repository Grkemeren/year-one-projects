import java.util.ArrayList;

public class VStandard  extends Voyage implements Refundable{
    int refundCut;

    public VStandard(int voyageId, float price, String fromWhere, String toWhere,
                    int seatCapacity,int refundCut) {
        super(voyageId,price,fromWhere,toWhere,seatCapacity);
        this.refundCut = refundCut;
    }

    /**
     * Cancels the ticket with applying the refund cut.
     * @param seatNumb seat number of ticket which is going to be cancelled.
     * @return the amount of money that is refunded.
     */
    public float refundTicket(int seatNumb) {
        super.getSeats()[seatNumb-1] = 0;
        super.decreaseRevenue(super.getPrice()*(1-(float)refundCut/100));
        return super.getPrice()*(1-(float)refundCut/100);

    }
    /**
     * constructs a string that represents the seats of the vehicle.
     * if seats is sold, it is represented by "X", otherwise it is represented by "*"
     * @return formatted string representing the seats of the vehicle.
     */
    @Override
    public String  getFormattedVehicleSeats() {
        int[] seats = super.getSeats();
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0 ; i < seats.length ; i++) {
            if (seats[i] == 1 ) {
                output.add("X");
            } else {
                output.add("*");
            }
            if((i+1)%2 == 0) {
                if((i+1)%4 == 0) {
                    output.add("\n");
                } else {
                    output.add(" | ");
                }
            } else {
                output.add(" ");
            }
        }
        return String.join("",output);
    }
}
