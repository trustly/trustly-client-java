package com.trustly.api.domain.methods.getaccounttransactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class GetAccountTransactionsRequestDataAttributes extends AbstractRequestParamsDataAttributes {
  
  @JsonProperty(value = "Firstname")
  private String firstname;

  @JsonProperty(value = "Lastname")
  private String lastname;

  @JsonProperty(value = "Email")
  @Email
  private String email;

  @JsonProperty(value = "MobilePhone")
  private String mobilePhone;

  @JsonProperty(value = "NationalIdentificationNumber")
  private String nationalIdentificationNumber;
  
  @JsonProperty(value = "IP")
  private String ip;

  @JsonProperty(value = "DateOfBirth")
  private String dateOfBirth;
}
