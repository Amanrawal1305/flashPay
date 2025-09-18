package ar.com.WalletService.modal;
import ar.com.enums.UserIdentifier;
import ar.com.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor

@Builder
@Entity(name="wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private int userId;

    private String name;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobileNo;

    private double balance;

    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    private String userIdentificationNumber;

    @Enumerated(EnumType.STRING)
    private UserIdentifier userIdentifier;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
