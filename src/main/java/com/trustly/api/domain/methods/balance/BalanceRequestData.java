package com.trustly.api.domain.methods.balance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class BalanceRequestData extends AbstractToTrustlyRequestData<EmptyRequestDataAttributes> {

}


