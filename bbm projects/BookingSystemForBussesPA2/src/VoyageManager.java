import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

/**
 * This class is used to manage the voyages and their operations.
 * stores all voyages in a hashmap.
 * initializes, sells, refunds and cancels voyages. calculates total revenue of the voyages.
 * writes a z-report for showing current status of the voyages.
 */
public class VoyageManager {
    private HashMap<Integer,Voyage> voyages = new HashMap<>();
    private String writeToFilePath;

    public VoyageManager() {
    }

    public HashMap<Integer, Voyage> getVoyages() {
        return voyages;
    }

    /**
     * initializes a voyage with given parameters.
     * @param voyageType the type of the voyage.
     * @param voyageIdStr the id of the voyage.
     * @param fromWhere the starting point of the voyage.
     * @param toWhere the destination of the voyage.
     * @param rowNumbStr the number of seat rows of the voyage vehicle.
     * @param priceStr the price of the voyage.
     * @param refundCutStr the refund cut of the voyage.
     * @param premiumFeeStr the premium fee of the voyage.
     * @return a string that indicates the result of the initialization.
     */
    public String initVoyage(String voyageType, String voyageIdStr, String fromWhere, String toWhere, String rowNumbStr,
                             String priceStr, String refundCutStr, String premiumFeeStr) {

        if (!isNonNegativeInteger(true,voyageIdStr)) {
            return "ERROR: "+voyageIdStr+" is not a positive integer, ID of a voyage must be a positive integer!";
        } else if (!isNonNegativeInteger(true,rowNumbStr)) {
            return "ERROR: "+rowNumbStr+" is not a positive integer, number of seat rows of a voyage must be a " +
                    "positive integer!";
        } else if (!isNonNegativeFloat(true,priceStr)) {
            return "ERROR: "+priceStr+" is not a positive number, price must be a positive number!";
        } else if (!isNonNegativeInteger(false,refundCutStr)) {
            return "ERROR: "+refundCutStr+" is not an integer that is in range of [0, 100], refund cut must be an" +
                    " integer that is in range of [0, 100]!";
        } else if (!isNonNegativeInteger(false,premiumFeeStr)) {
            return "ERROR: "+premiumFeeStr+" is not a non-negative integer," +
                    " premium fee must be a non-negative integer!";
        }

        int voyageId = Integer.parseInt(voyageIdStr);
        int rowNumb = Integer.parseInt(rowNumbStr);
        float price = Float.parseFloat(priceStr);
        int refundCut = Integer.parseInt(refundCutStr);
        int premiumFee = Integer.parseInt(premiumFeeStr);

        if (voyages.containsKey(voyageId)) {
            return "ERROR: There is already a voyage with ID of "+voyageId+"!";
        }

        int seatCapacity;
        switch (voyageType) { // initializing the voyage according to the type of the voyage.
            case "Minibus":
                seatCapacity = rowNumb*2;
                // new minibus voyage with 2 seat configuration and no refund cut.
                voyages.put(voyageId,new VMinibus(voyageId,price,fromWhere,toWhere,seatCapacity));
                return ("Voyage "+voyageId+" was initialized as a minibus (2) voyage from "+fromWhere+" to "+
                        toWhere+" with "+String.format("%.2f",price)+" TL priced "+seatCapacity+
                        " regular seats. Note that minibus tickets are not refundable.");

            case "Standard":
                seatCapacity = rowNumb*4;
                // new standard voyage with 2+2 seat configuration and refund cut.
                voyages.put(voyageId,new VStandard(voyageId,price,fromWhere,toWhere,seatCapacity,refundCut));
                return ("Voyage "+voyageId+" was initialized as a standard (2+2) voyage from "+fromWhere+ " to " +
                        toWhere+ " with "+String.format("%.2f",price)+ " TL priced "+seatCapacity+
                        " regular seats. Note that refunds will be "+refundCut+"% less than the paid amount.");

            case "Premium":
                seatCapacity = rowNumb*3;
                // new premium voyage with 1+2 seat configuration, refund cut and premiumFee.
                voyages.put(voyageId,new VPremium(voyageId,price,fromWhere,
                        toWhere,rowNumb*3,refundCut,premiumFee));
                return ("Voyage "+voyageId+" was initialized as a premium (1+2) voyage from "+fromWhere+ " to " +
                        toWhere+ " with "+String.format("%.2f",price)+ " TL priced "+seatCapacity*2/3+
                        " regular seats and "+String.format("%.2f",(1+ ((float) premiumFee /100))*price)+" TL priced "+
                        seatCapacity/3+ " premium seats. Note that refunds will be "+refundCut+
                        "% less than the paid amount.");
            default:
               return "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!"; // if the voyage type is not valid.
        }
    }

    /**
     * sells ticket/tickets for the given voyage and seat numbers. if voyage id or seat numbers is not valid,
     * returns an error message.
     * @param voyageIdStr id of the voyage.
     * @param strSeatNumbs seat numbers to be sold as a string array.
     * @return a string that shows the result of the selling.
     */
    public String sellTicket(String voyageIdStr,String[] strSeatNumbs) {
        if (!isNonNegativeInteger(true,voyageIdStr)) {
            return "ERROR: "+voyageIdStr+" is not a positive integer, ID of a voyage must be a positive integer!";
        }
        int voyageId = Integer.parseInt(voyageIdStr);
        if (!voyages.containsKey(voyageId)) {
            return"ERROR: There is no voyage with ID of "+voyageId+"!";
        }
        Voyage selectedVoyage = voyages.get(voyageId);
        int[] voyagesSeats = selectedVoyage.getSeats();
        int[] seatNumbs = new int[strSeatNumbs.length];
        for (int i = 0 ; i < strSeatNumbs.length ; i++) {
            if (isNonNegativeInteger(true,strSeatNumbs[i])) { // checking if the seat number is valid.
                seatNumbs[i] = Integer.parseInt(strSeatNumbs[i]);
            } else{
                return "ERROR: "+strSeatNumbs[i]+" is not a positive integer, seat number must be a positive integer!";
            }
        }
        for (int seatNumb : seatNumbs) { // checking if the seat number is valid.
            if (seatNumb > voyagesSeats.length) {
                return "ERROR: There is no such a seat!";
            } else if (voyagesSeats[seatNumb-1] == 1) {
                return "ERROR: One or more seats already sold!";
            }
        }
        float revenue = 0;
        for (int seatNumb : seatNumbs) {
            revenue += selectedVoyage.sellTicket(seatNumb); // increase revenue of the voyage.
        }
        return "Seat "+String.join("-",strSeatNumbs)+" of the Voyage "+voyageId+" from "+
                selectedVoyage.getFromWhere()+" to "+selectedVoyage.getToWhere()+
                " was successfully sold for "+String.format("%.2f",revenue)+" TL.";
    }

    /**
     * refunds ticket/tickets for the given voyage and seat numbers. if voyage id or seat numbers is not valid,
     * returns an error message.
     * if voyage isn't refundable, returns an error message.
     * refunds are made according to the refund cut of the voyage.
     * @param voyageIDStr id of the voyage.
     * @param strSeatNumbs seat numbers to be refunded as a string array.
     * @return a string that shows the result of the refunding.
     */
    public String  refundTicket(String voyageIDStr,String[] strSeatNumbs) {
        if (!isNonNegativeInteger(true,voyageIDStr)) {
            return "ERROR: "+voyageIDStr+" is not a positive integer, ID of a voyage must be a positive integer!";
        }
        int voyageID = Integer.parseInt(voyageIDStr);

        if (!voyages.containsKey(voyageID)) {
            return "ERROR: There is no voyage with ID of "+voyageID+"!";
        }
        Voyage selectedVoyage = voyages.get(voyageID);

        if (!(selectedVoyage instanceof Refundable)) {
            return "ERROR: Minibus tickets are not refundable!";
        }

        int[] voyagesSeats = selectedVoyage.getSeats();
        int[] seatNumbs = new int[strSeatNumbs.length];
        for (int i = 0 ; i < strSeatNumbs.length ; i++) {
            if (isNonNegativeInteger(true,strSeatNumbs[i])) {
                seatNumbs[i] = Integer.parseInt(strSeatNumbs[i]);
            } else{
                return "ERROR: "+strSeatNumbs[i]+" is not a positive integer, seat number must be a positive integer!";
            }
        }
        for (int seatNumb : seatNumbs) {
            if (seatNumb > voyagesSeats.length) {
                return "ERROR: There is no such a seat!";
            }else if (voyagesSeats[seatNumb-1] == 0) {
                return "ERROR: One or more seats are already empty!";
            }
        }
        float totalRefund = 0;
        for (int seatNumb : seatNumbs) {
            totalRefund += ((Refundable) selectedVoyage).refundTicket(seatNumb); // cast to Refundable for refunding.
        }
        return "Seat "+String.join("-",strSeatNumbs)+" of the Voyage "+voyageID+" from "+
                selectedVoyage.getFromWhere()+" to "+selectedVoyage.getToWhere()+" was successfully refunded for "+
                String.format("%.2f",totalRefund)+" TL.";

    }

    /**
     * cancels the voyage with the given id. if the voyage id is not valid, returns an error message.
     * cancels tickets ot the voyage doesn't apply refund cut.
     * deletes the voyage from the voyages.
     * @param voyageIDStr id of the voyage.
     * @return a string that shows the result of the cancelling.
     */
    public String cancelVoyage(String voyageIDStr) {
        if (!isNonNegativeInteger(true,voyageIDStr)) {
            return "ERROR: "+voyageIDStr+" is not a positive integer, ID of a voyage must be a positive integer!";
        }
        int voyageID = Integer.parseInt(voyageIDStr);
        if (!voyages.containsKey(voyageID)) {
            return "ERROR: There is no voyage with ID of " + voyageID + "!";
        }
        Voyage voyage = voyages.get(voyageID);
        String formattedVehicleSeats = voyage.getFormattedVehicleSeats(); // formatted vehicle seats according to type.
        int[] seats = voyage.getSeats();
        for (int i = 0 ; i < seats.length ; i++) {
            if (seats[i] == 1 ) {
                voyage.cancelTicket(i+1);
            }
        }
        String output= "Voyage "+voyageID+" was successfully cancelled!\n" +
                "Voyage details can be found below:\n" +
                "Voyage "+voyageID+"\n" +voyage.getFromWhere()+"-"+voyage.getToWhere()+"\n"+
                formattedVehicleSeats+
                "Revenue: "+ String.format("%.2f",voyage.getRevenue());
        voyages.remove(voyageID); // deletes the voyage.
        return output;
    }





    /**
     * concentrates a report from voyage information.
     * shows each voyage with its id, starting position, destination, vehicle seats and revenue.
     * @return z-report as a string.
     */
    public String getZreport() {
        StringBuffer zreport = new StringBuffer();  // used as buffer for complete z-report;
        zreport.append("Z Report:\n");
        if (voyages.isEmpty()) {
            zreport.append("----------------\nNo Voyages Available!\n----------------");
            return zreport.toString();
        }
        for (int key : voyages.keySet()) {
            Voyage voyage = voyages.get(key);
            zreport.append("----------------\n"+
                    "Voyage "+key+"\n"+
                    voyage.getFromWhere()+"-"+voyage.getToWhere()+"\n"+
                    voyage.getFormattedVehicleSeats()+
                    "Revenue: "+String.format("%.2f",voyage.getRevenue())+"\n");
        }
        zreport.append("----------------");
        return zreport.toString();
    }

    /**
     * prints the voyage with the given id. if the voyage id is not valid, returns an error message.
     * @param voyageIDStr id of the voyage.
     * @return a string that shows the voyages situation.
     */
    public String  printVoyage(String voyageIDStr) {
        if (!isNonNegativeInteger(true,voyageIDStr)) {
            return "ERROR: "+voyageIDStr+" is not a positive integer, ID of a voyage must be a positive integer!";
        }
        int voyageID = Integer.parseInt(voyageIDStr);
        if (!voyages.containsKey(voyageID)) {
            return "ERROR: There is no voyage with ID of "+voyageID+"!";
        }
        Voyage voyage = voyages.get(voyageID);
        return "Voyage "+voyageID+"\n"+
                voyage.getFromWhere()+"-"+voyage.getToWhere()+"\n"+
                voyage.getFormattedVehicleSeats()+
                "Revenue: "+String.format("%.2f",voyage.getRevenue());

    }

    /**
     * checks if the given number as a string is a non-negative İNTEGER or not.
     * if positive is true checks if the number is positive or not.
     * @param positive number is positive or not.
     * @param number number as a string.
     * @return boolean value that indicates numbers positivity.
     */
    private static boolean isNonNegativeInteger(boolean positive,String number) {
        try {
            int numb = Integer.parseInt(number);
            if (positive ? numb <=0 : numb <0) {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * checks if the given number as a string is a non-negative FLOAT or not.
     * if positive is true checks if the number is positive or not.
     * @param positive number is positive or not.
     * @param number number as a string.
     * @return boolean value that indicates numbers positivity.
     */
    private static boolean isNonNegativeFloat(boolean positive,String number) {
        try {
            float numb = Float.parseFloat(number);
            if (positive ? numb <=0 : numb <0) {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}