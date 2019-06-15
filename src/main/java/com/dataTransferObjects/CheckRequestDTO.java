package com.dataTransferObjects;

import com.model.Customer;
import com.model.TimeSpan;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "checkRequest")
public class CheckRequestDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    private Date date;

    @NotNull
    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    @NotNull
    @Column(name = "tableNumber")
    private Integer tableNumber;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customerId")
    private Customer relatedCustomer;

    @NotNull
    @Embedded
    private TimeSpan ts;
    // </editor-fold>

    // <editor-fold desc="constructor">
    public CheckRequestDTO() {

    }
    // </editor-fold>

    // <editor-fold desc="getters and setters">


    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Customer getRelatedCustomer() {
        return relatedCustomer;
    }

    public void setRelatedCustomer(Customer relatedCustomer) {
        this.relatedCustomer = relatedCustomer;
    }

    public TimeSpan getTs() {
        return ts;
    }

    public void setTs(TimeSpan ts) {
        this.ts = ts;
    }

    // </editor-fold>

    @Override
    public String toString() {
        return "CheckRequestDTO{" +
                "id=" + id +
                ", date=" + date +
                ", numberOfSeats=" + numberOfSeats+
                ", tableNumber=" + tableNumber +
                ", ts=" + ts +
                '}';
    }
}

