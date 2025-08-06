package kh.com.csx.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CustomerRequest {
    private String username; // Unique name for the customer
    private String phoneNumber;
    private String address;
    private String telegramId; // Telegram ID for customer communication
    private String telegramUsername; // Telegram username for customer communication
    private Date createdAt; // Timestamp for when the customer was created
    private Date updatedAt; // Timestamp for when the customer was last updated
    private Date deletedAt; // Timestamp for when the customer was deleted, if applicable
}
