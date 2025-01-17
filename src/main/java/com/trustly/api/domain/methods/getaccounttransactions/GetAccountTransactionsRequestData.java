package com.trustly.api.domain.methods.getaccounttransactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import com.trustly.api.domain.common.GetAccountTransactionsValidationGroup;
import com.trustly.api.domain.common.VerifyAccountValidationGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.ConvertGroup;
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
public class GetAccountTransactionsRequestData extends AbstractToTrustlyRequestData<GetAccountTransactionsRequestDataAttributes> {

  @JsonProperty("NotificationURL")
  @NotBlank
  @URL
  private String notificationUrl;

  @JsonProperty("EndUserID")
  @NotBlank
  private String endUserId;

  @JsonProperty("MessageID")
  @NotBlank
  private String messageId;

  @JsonProperty(value = "SuccessURL", required = true)
  @NotBlank
  @URL
  private String successUrl;

  @JsonProperty(value = "FailURL", required = true)
  @NotBlank
  @URL
  private String failUrl;

  @JsonProperty(value = "Country", required = true)
  @NotBlank
  private String country;

  @JsonProperty(value = "Locale", required = true)
  @NotBlank
  private String locale;


  @JsonProperty(value = "Attributes", required = true)
  @JsonInclude(Include.NON_NULL)
  @Valid
  @ConvertGroup(to = GetAccountTransactionsValidationGroup.class)
  @Override
  public GetAccountTransactionsRequestDataAttributes getAttributes() {
    return super.getAttributes();
  }
}
