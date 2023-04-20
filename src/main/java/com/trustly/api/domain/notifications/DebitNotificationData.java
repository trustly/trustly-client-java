package com.trustly.api.domain.notifications;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class DebitNotificationData extends AbstractCreditDebitPendingPayoutNotificationData {

}
