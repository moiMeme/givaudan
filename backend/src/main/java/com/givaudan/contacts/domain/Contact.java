package com.givaudan.contacts.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "t_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "contact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    @CsvBindByName(column = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    @CsvBindByName(column = "last_name")
    private String lastName;

    @Column(name = "email")
    @Email
    @CsvBindByName(column = "email")
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    @CsvBindByName(column = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "address", nullable = false)
    @CsvBindByName(column = "address")
    private String address;

    @NotNull
    @Min(value = 10000)
    @Max(value = 99999)
    @Column(name = "zip_code", nullable = false)
    @CsvBindByName(column = "zip_code")
    private Integer zipCode;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    @CsvBindByName(column = "birth_date")
    @CsvDate("yyyy-MM-dd")
    private LocalDate birthDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", zipCode=" + getZipCode() +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
