package com.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "submissionDate")
    private Date submissionDate;

    @NotNull
    private Date reservationDate;

    @NotNull
    @Embedded
    private TimeSpan reservationTime;

    @Size(max = 255)
    private String otherRequirements;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer relatedCustomer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tableId")
    private RestTable relatedTable;

    //</editor-fold>

    // <editor-fold desc="constructors">
    public Reservation() {

    }
    public Reservation(int id, Customer relatedCustomer, RestTable relatedTable, Date reservationDate, TimeSpan reservationTime, String otherRequirements) {
        this.id = id;
        this.relatedCustomer = relatedCustomer;
        this.relatedTable = relatedTable;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.otherRequirements = otherRequirements;
    }
    // </editor-fold>

    // <editor-fold desc="setters and getters">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public TimeSpan getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(TimeSpan reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Customer getRelatedCustomer() {
        return relatedCustomer;
    }

    public void setRelatedCustomer(Customer relatedCustomer) {
        this.relatedCustomer = relatedCustomer;
    }

    public RestTable getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(RestTable relatedTable) {
        this.relatedTable = relatedTable;
    }
    public boolean doesTimeSpanConflicts(TimeSpan ts) {
        int myTimeSpanEnd = Integer.parseInt(this.reservationTime.getEnd());
        int myTimeSpanStart = Integer.parseInt(this.reservationTime.getStart());

        int givenTimeSpanStart = Integer.parseInt(ts.getStart());
        int givenTimeSpanEnd = Integer.parseInt(ts.getEnd());

        boolean endTimeIntersection = givenTimeSpanEnd <= myTimeSpanEnd && givenTimeSpanEnd >= myTimeSpanStart;
        boolean startTimeIntersection = givenTimeSpanStart >= myTimeSpanStart && givenTimeSpanStart <= myTimeSpanEnd;

        return endTimeIntersection || startTimeIntersection;

    }
}
