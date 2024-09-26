import java.util.ArrayList;

public class VPremium extends Voyage implements Refundable{
    int refundCut;
    int premiumFee;
    float premiumPrice;
    public VPremium(int voyageId, float price, String fromWhere, String toWhere,
                    int seatCapacity,int refundCut, int premiumFee) {
        super(voyageId,price,fromWhere,toWhere,seatCapacity);
        this.refundCut = refundCut;
        this.premiumFee = premiumFee;
        this.premiumPrice = (1+ ((float) premiumFee /100))*price;
    }

    /**
     * sells the ticket and increases the revenue with premium price if the seat is premium.
     * @param seatNumb seat number of ticket which is going to be sold.
     * @return the amount of money that is earned.
     */
    @Override
    public float sellTicket(int seatNumb) {
        super.getSeats()[seatNumb-1] = 1;
        if ((seatNumb-1)%3 == 0) {
            super.increaseRevenue(premiumPrice);
            return premiumPrice;
        } else {
            super.increaseRevenue(super.getPrice());
            return super.getPrice();
        }
    }
    /**
     * Cancels the ticket with applying the refund cut. premium seats have more refund cut.
     * @param seatNumb seat number of ticket which is going to be cancelled.
     * @return the amount of money that is refunded.
     */
    @Override
    public float refundTicket(int seatNumb) {
        super.getSeats()[seatNumb-1] = 0;
        if ((seatNumb-1)%3 == 0) {
            super.decreaseRevenue(premiumPrice*(1-((float) refundCut /100)));
            return premiumPrice*(1-((float) refundCut /100));
        } else {
            super.decreaseRevenue(super.getPrice()*(1-((float) refundCut /100)));
            return super.getPrice()*(1- (float) refundCut /100);
        }
    }
    /**
     * Cancels the ticket without applying the refund cut.
     * @param seatNumb seat number of ticket which is going to be cancelled.
     * @return the amount of money that is refunded.
     */
    public float cancelTicket(int seatNumb) {
        super.getSeats()[seatNumb-1] = 0;
        if ((seatNumb-1)%3 == 0) {
            super.decreaseRevenue(premiumPrice);
            return premiumPrice;
        } else {
            super.decreaseRevenue(super.getPrice());
            return super.getPrice();
        }
    }


    /**
     * constructs a string that represents the seats of the vehicle.
     * if seats is sold, it is represented by "X", otherwise it is represented by "*"
     * @return formatted string representing the seats of the vehicle.
     */
    @Override
    public String getFormattedVehicleSeats() {
        int[] seats = super.getSeats();
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0 ; i < seats.length ; i++) {
            if (seats[i] == 1 ) {
                output.add("X");
            } else {
                output.add("*");
            }
            if(i%3 == 0) {
                output.add(" | ");
            } else if ((i+1)%3 == 0) {
                output.add("\n");
            } else {
                output.add(" ");
            }
        }
        return String.join("",output);
    }
}
