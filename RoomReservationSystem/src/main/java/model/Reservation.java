package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private String reservationNo;
    private Guest guest;
    private String roomType;     // STANDARD, DELUXE, SUITE
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String createdAt;    // ISO string

    public Reservation() {}

    public Reservation(String reservationNo, Guest guest, String roomType,
                       LocalDate checkIn, LocalDate checkOut, String createdAt) {
        this.reservationNo = reservationNo;
        this.guest = guest;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
