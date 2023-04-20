package com.trustly.api.domain.notifications;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class PendingNotificationData extends AbstractCreditDebitPendingPayoutNotificationData {

}
