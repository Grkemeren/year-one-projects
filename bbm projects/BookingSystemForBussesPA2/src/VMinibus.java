import java.util.ArrayList;
import java.util.Arrays;

public class VMinibus extends Voyage{

    public VMinibus(int voyageId, float price, String fromWhere, String toWhere,int seatCapacity) {
        super(voyageId,price,fromWhere,toWhere,seatCapacity);

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
            if((i+1)%2 == 0) {
                output.add("\n");
            } else {
                output.add(" ");
            }
        }
        return String.join("",output);
    }






}
