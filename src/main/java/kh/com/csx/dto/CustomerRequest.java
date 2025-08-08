package kh.com.csx.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CustomerRequest {
    private String username; // Unique name for the customer
    @Column (name = "phone_number")
    private String phoneNumber;
    private String address;

    @Column (name = "telegram_id")
    private String telegramId; // Telegram ID for customer communication

    @Column (name = "telegram_username")
    private String telegramUsername; // Telegram username for customer communication

    @Column (name = "created_at")
    private Date createdAt; // Timestamp for when the customer was created

    @Column (name = "updated_at")
    private Date updatedAt; // Timestamp for when the customer was last updated

    @Column (name = "deleted_at")
    private Date deletedAt; // Timestamp for when the customer was deleted, if applicable
}
