public interface Refundable {
    /**
     * Refunds the ticket with applying the refund cut.
     * @param seatNumb seat number of ticket which is going to be refunded.
     * @return the amount of money that is refunded.
     */
    float refundTicket(int seatNumb);
}
