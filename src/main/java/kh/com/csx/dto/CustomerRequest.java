package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CustomerRequest {
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "username can't be empty!")
    @JsonProperty("username")
    private String username; // Unique name for the customer

    @NotNull(message = "phone_number can't be empty!")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("telegram_id")
    private String telegramId; // Telegram ID for customer communication

    @JsonProperty("telegram_username")
    private String telegramUsername; // Telegram username for customer communication

    @JsonProperty("profile_picture")
    private String profilePicture; // Optional field for customer profile picture

    @JsonProperty("created_at")
    private Date createdAt; // Timestamp for when the customer was created

    @JsonProperty("updated_at")
    private Date updatedAt; // Timestamp for when the customer was last updated

    @JsonProperty("deleted_at")
    private Date deletedAt; // Timestamp for when the customer was deleted, if applicable
}
