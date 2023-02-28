package com.trustly.api.domain.notifications;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
@Jacksonized
public class PendingNotificationData extends AbstractCreditDebitPendingPayoutNotificationData {

}
