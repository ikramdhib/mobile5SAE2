package com.example.bookingapp.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ReservationCar",
        foreignKeys = {
                @ForeignKey(
                        entity = Car.class,
                        parentColumns = "id",
                        childColumns = "carId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class ReservationCar {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String reservationDate;
    private String startDate;
    private String endDate;
    private String paymentMethod;
    private String status;

    private int carId;

    // Constructor



    public ReservationCar(String reservationDate, String startDate, String endDate, String paymentMethod, String status, int carId) {
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.carId = carId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }



    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
}
