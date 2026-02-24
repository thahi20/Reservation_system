package model;

import java.io.Serializable;

public class Bill implements Serializable {

    private String reservationNo;
    private String guestName;
    private String roomType;
    private long nights;
    private int ratePerNight;
    private long total;

    public Bill() {
    }

    public Bill(String reservationNo, String guestName, String roomType,
            long nights, int ratePerNight, long total) {
        this.reservationNo = reservationNo;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.total = total;
    }

    public String getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public long getNights() {
        return nights;
    }

    public void setNights(long nights) {
        this.nights = nights;
    }

    public int getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(int ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
