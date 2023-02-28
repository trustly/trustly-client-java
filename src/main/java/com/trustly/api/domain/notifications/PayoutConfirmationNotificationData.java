package com.trustly.api.domain.notifications;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.domain.methods.getwithdrawals.GetWithdrawalsRequestData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * This is a notification that Trustly will send to the merchant's system in order to confirm that a payout has been sent.
 * <p>
 * This notification is not enabled by default. Please contact your Trustly integration manager in case you want to receive it.
 *
 * <p>
 * Note: In many cases, the {@code payoutconfirmation} will be sent within minutes after the AccountPayout request has been received by
 * Trustly. But in some cases there will be a delay of 1day or more,since Trustly relies on receiving statement files from banks.
 * <p>
 * If you have sent an AccountPayout request and haven’t received a {@code payoutconfirmation} within an hour or so, it does not necessarily
 * mean that something is wrong. If you experience this and need to know the actual status of the payout, you can either use Trustly’s
 * backoffice ({@code Transactions} &gt; {@code Withdrawals}), or use the {@link TrustlyApiClient#getWithdrawals(GetWithdrawalsRequestData)}
 * API method.
 * <p>
 * If you use the {@link TrustlyApiClient#getWithdrawals(GetWithdrawalsRequestData)} method and receive status {@code EXECUTING} or
 * {@code EXECUTED}, it means that the withdrawal is currently being processed or has been processed, but is not confirmed yet.
 * <p>
 * The {@link TrustlyApiClient#getWithdrawals(GetWithdrawalsRequestData)} method must not be used more than once every 15 minutes per
 * payout.
 * <p>
 * Please note that even if a {@code payoutconfirmation} has been sent,the payout can still fail afterwards. If that happens, Trustly will
 * send a credit notification to the merchant’s {@code NotificationURL}. This can happen for example if the funds are sent to a bank account
 * that is closed.
 */
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
@Jacksonized
public class PayoutConfirmationNotificationData extends AbstractCreditDebitPendingPayoutNotificationData {

}
