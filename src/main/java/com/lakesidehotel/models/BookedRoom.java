package com.lakesidehotel.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "BOOKED_ROOM")
public class BookedRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "check_in")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime checkInDate;

    @Column(nullable = false, name = "check_out")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime checkOutDate;

    @Column(nullable = false, name = "guest_fullname")
    private String guestFullName;

    @Column(nullable = false, name = "guest_email")
    private String guestEmail;

    @Column(nullable = false, name = "adults")
    private Integer numOfAdults;

    @Column(nullable = false, name = "children")
    private Integer numOfChildren;

    @Column(nullable = false, name = "total_guests")
    private Integer totalNumOfGuests;

    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Room.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "room_fk"))
    private Room room;

    public void calculateTotalNumGuests() {
        this.totalNumOfGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumGuests();
    }

    public void setNumOfChildren(Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumGuests();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        BookedRoom bookedRoom = (BookedRoom) o;
        if (id == null) {
            return bookedRoom.id == null;
        } else return id.equals(bookedRoom.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
