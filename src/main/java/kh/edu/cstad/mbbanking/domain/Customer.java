package kh.edu.cstad.mbbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 150)
    private String fullName;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true ,length = 15)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    // Relationship (HAS-A)
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer", optional = false)
    private KYC kyc;

    @ManyToOne(optional = false)
    private CustomerSegment customerSegment;
}
