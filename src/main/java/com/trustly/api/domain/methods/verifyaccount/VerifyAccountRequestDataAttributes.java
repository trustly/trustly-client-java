package com.trustly.api.domain.methods.verifyaccount;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.Email;
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
public class VerifyAccountRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  @JsonProperty(value = "Locale", required = true)
  @NotBlank
  private String locale;

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

  @JsonProperty(value = "IP")
  private String ip;

  @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
  private Integer unchangeableNationalIdentificationNumber;
}
