package com.trustly.api.domain.methods.selectaccount;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class SelectAccountRequestData extends AbstractToTrustlyRequestData<SelectAccountRequestDataAttributes> {

  /**
   * The URL to which notifications for this order should be sent to.This URL should be hard to guess and not contain a? ("question mark").
   *
   * <pre>{@code https://example.com/trustly/notification/a2b63j23dj23883jhfhfh}</pre>
   */
  @JsonProperty(value = "NotificationURL", required = true)
  @NotBlank
  @URL
  String notificationUrl;

  /**
   * ID, username, hash or anything uniquely identifying the end-user to be identified. Preferably the same ID/username as used in the
   * merchant's own backoffice in order to simplify for the merchant's support department
   */
  @JsonProperty(value = "EndUserID", required = true)
  @NotBlank
  String endUserId;

  /**
   * Your unique ID for the account selection order. Each order you create must have an unique MessageID.
   */
  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  String messageId;
}

