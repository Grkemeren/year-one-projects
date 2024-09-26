public class CommandReader {
    /**
     * This method reads the command and calls the appropriate method of the manager.
     * if command is not valid it returns an error message.
     * @param command command to be read.
     * @param manager the VoyageManager who sets up voyages.
     * @return the output of the command.
     */
    public static String readcommand(String command, VoyageManager manager) {
        String[] managerArguments = command.split("\t");
        try {
            String voyageType;
            String voyageId;
            String fromWhere;
            String toWhere;
            String rows;
            String price;
            String refundCut;
            String premiumFee;
            String[] strSeatnumbs;
            switch (managerArguments[0]) {
                case "INIT_VOYAGE":
                    if (managerArguments.length > 9) {
                        throw new Exception();
                    }

                    voyageType = managerArguments[1];
                    voyageId = managerArguments[2];
                    fromWhere = managerArguments[3];
                    toWhere = managerArguments[4];
                    rows = managerArguments[5];
                    price = managerArguments[6];
                    refundCut = "1";
                    premiumFee = "1";

                    if (managerArguments.length >= 8) {
                        refundCut = managerArguments[7];
                        if (managerArguments.length >= 9) {
                            premiumFee = managerArguments[8];
                        }
                    }
                    return manager.initVoyage(voyageType, voyageId, fromWhere, toWhere, rows, price, refundCut, premiumFee);

                case "Z_REPORT":
                    if (managerArguments.length != 1) {
                        throw new Exception();
                    }
                    return manager.getZreport();

                case "PRINT_VOYAGE":
                    if (managerArguments.length != 2) {
                        throw new Exception();
                    }
                    return manager.printVoyage(managerArguments[1]);

                case "SELL_TICKET":
                    if (managerArguments.length != 3) {
                        throw new Exception();
                    }
                    voyageId = managerArguments[1];
                    strSeatnumbs = managerArguments[2].split("_");

                    return manager.sellTicket(voyageId,strSeatnumbs);
                case "REFUND_TICKET":
                    if (managerArguments.length != 3) {
                        throw new Exception();
                    }
                    voyageId =managerArguments[1];
                    strSeatnumbs = managerArguments[2].split("_");
                    return manager.refundTicket(voyageId,strSeatnumbs);
                case "CANCEL_VOYAGE":
                    if (managerArguments.length != 2) {
                        throw new Exception();
                    }
                    voyageId =managerArguments[1];
                    return manager.cancelVoyage(voyageId);
                default:
                    return "ERROR: There is no command namely "+managerArguments[0]+"!";
            }
        } catch (Exception e) {
            return "ERROR: Erroneous usage of \""+managerArguments[0]+"\" command!";

        }
    }
}
