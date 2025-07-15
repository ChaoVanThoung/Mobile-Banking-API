package kh.edu.cstad.mbbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String actNo;
    private BigDecimal balance;
    private BigDecimal overLimit;
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cust_id", referencedColumnName = "id")
    private Customer customer;  // cust_id

//    @ManyToOne(optional = false)
//    private AccountType accountTypes;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactions;
}
