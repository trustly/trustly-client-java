package com.trustly.api.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.client.NotificationArgs.NotificationFailHandler;
import com.trustly.api.client.NotificationArgs.NotificationOkHandler;
import com.trustly.api.domain.base.IFromTrustlyRequestData;
import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.base.IResponseResultData;
import com.trustly.api.domain.base.IToTrustlyRequestParams;
import com.trustly.api.domain.base.IWithRejectionResult;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.base.NotificationRequest;
import com.trustly.api.domain.base.ResponseResult;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.domain.exceptions.TrustlyRejectionException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.domain.methods.accountledger.AccountLedgerRequestData;
import com.trustly.api.domain.methods.accountledger.AccountLedgerResponseData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutResponseData;
import com.trustly.api.domain.methods.approvewithdrawal.ApproveWithdrawalRequestData;
import com.trustly.api.domain.methods.approvewithdrawal.ApproveWithdrawalResponseData;
import com.trustly.api.domain.methods.balance.BalanceRequestData;
import com.trustly.api.domain.methods.balance.BalanceResponseData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeRequestData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeResponseData;
import com.trustly.api.domain.methods.charge.ChargeRequestData;
import com.trustly.api.domain.methods.charge.ChargeRequestDataAttributes;
import com.trustly.api.domain.methods.charge.ChargeResponseData;
import com.trustly.api.domain.methods.createaccount.CreateAccountRequestData;
import com.trustly.api.domain.methods.createaccount.CreateAccountResponseData;
import com.trustly.api.domain.methods.denywithdrawal.DenyWithdrawalRequestData;
import com.trustly.api.domain.methods.denywithdrawal.DenyWithdrawalResponseData;
import com.trustly.api.domain.methods.deposit.DepositRequestData;
import com.trustly.api.domain.methods.deposit.DepositResponseData;
import com.trustly.api.domain.methods.getwithdrawals.GetWithdrawalsRequestData;
import com.trustly.api.domain.methods.getwithdrawals.GetWithdrawalsResponseData;
import com.trustly.api.domain.methods.refund.RefundRequestData;
import com.trustly.api.domain.methods.refund.RefundResponseData;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountRequestData;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountResponseData;
import com.trustly.api.domain.methods.registeraccountpayout.RegisterAccountPayoutRequestData;
import com.trustly.api.domain.methods.registeraccountpayout.RegisterAccountPayoutResponseData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestDataAttributes;
import com.trustly.api.domain.methods.selectaccount.SelectAccountResponseData;
import com.trustly.api.domain.methods.settlementreport.SettlementReportRequestData;
import com.trustly.api.domain.methods.settlementreport.SettlementReportResponseData;
import com.trustly.api.domain.methods.withdraw.WithdrawRequestData;
import com.trustly.api.domain.methods.withdraw.WithdrawResponseData;
import com.trustly.api.domain.notifications.AccountNotificationData;
import com.trustly.api.domain.notifications.CancelNotificationData;
import com.trustly.api.domain.notifications.CreditNotificationData;
import com.trustly.api.domain.notifications.DebitNotificationData;
import com.trustly.api.domain.notifications.PayoutConfirmationNotificationData;
import com.trustly.api.domain.notifications.PendingNotificationData;
import com.trustly.api.domain.notifications.UnknownNotificationData;
import com.trustly.api.request.ApacheHttpClient3HttpRequester;
import com.trustly.api.request.ApacheHttpClient4HttpRequester;
import com.trustly.api.request.HttpRequester;
import com.trustly.api.request.JavaUrlConnectionHttpRequester;
import com.trustly.api.util.TrustlyStringUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrustlyApiClient implements Closeable {

  private static final List<TrustlyApiClient> STATIC_REGISTERED_CLIENTS = new ArrayList<>();

  private static final HttpRequester[] AVAILABLE_HTTP_REQUESTERS = new HttpRequester[]{
    new ApacheHttpClient4HttpRequester(),
    new ApacheHttpClient3HttpRequester(),
    new JavaUrlConnectionHttpRequester()
  };

  private static HttpRequester getFirstAvailableHttpRequester() {

    HttpRequester foundHttpRequester = null;
    for (HttpRequester requester : AVAILABLE_HTTP_REQUESTERS) {
      if (requester.isAvailable()) {
        foundHttpRequester = requester;
        break;
      }
    }

    if (foundHttpRequester == null) {
      throw new IllegalStateException("Could not find a suitable http requester factory");
    }

    return foundHttpRequester;
  }

  @Value
  private static class NotificationMeta<D extends IFromTrustlyRequestData> {

    Class<D> dataClass;
    List<NotificationEvent<D>> listeners = new ArrayList<>();
  }

  private final TrustlyApiClientSettings settings;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final JsonRpcFactory objectFactory = new JsonRpcFactory();
  private final JsonRpcSigner signer;
  private final JsonRpcValidator validator = new JsonRpcValidator();
  private final HttpRequester httpRequester;

  private final Map<String, NotificationMeta<? extends IFromTrustlyRequestData>> onNotification = new HashMap<>();

  public TrustlyApiClientSettings getSettings() {
    return settings;
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings) {
    this(settings, new DefaultJsonRpcSigner(new Serializer(), settings), TrustlyApiClient.getFirstAvailableHttpRequester());
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings, JsonRpcSigner signer) {
    this(settings, signer, TrustlyApiClient.getFirstAvailableHttpRequester());
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings, JsonRpcSigner signer, HttpRequester httpRequester) {
    this.settings = settings;
    this.signer = signer;
    this.httpRequester = httpRequester;

    TrustlyApiClient.STATIC_REGISTERED_CLIENTS.add(this);
  }

  @Override
  public void close() {
    TrustlyApiClient.STATIC_REGISTERED_CLIENTS.remove(this);
  }

  public static Iterable<TrustlyApiClient> getRegisteredClients() {
    return STATIC_REGISTERED_CLIENTS;
  }

  // Methods

  /**
   * Fetches the account ledger for the specified time period.
   * <p>
   * This report includes all the transactions (both incoming and outgoing transactions) that affect the merchant's Trustly account
   * balance.
   * <p>
   * Only settled transactions are included.
   */
  public AccountLedgerResponseData accountLedger(AccountLedgerRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, AccountLedgerResponseData.class, "AccountLedger", null);
  }

  /**
   * This method is used by merchants to transfer money to their customer's bank accounts.
   * <p>
   * The merchant specifies the receiving bank account in {@link AccountPayoutRequestData#setAccountId}, which is a unique identifier
   * generated by Trustly.
   * <p>
   * The merchant can get the {@code AccountID} from {@link NotificationRequest}&lt;{@link AccountNotificationData}&gt; which is sent after
   * a {@link TrustlyApiClient#selectAccount} or {@link TrustlyApiClient#deposit} order has been completed.
   * <p>
   * Alternatively, the {@link TrustlyApiClient#registerAccount} method can be used to get the {@code AccountID}, if the merchant already
   * has the bank account details and want to register them in Trustly's system.
   * <p>
   * Funds must be transferred to the merchant's Trustly account before the payout can be made. No credit is given. To see how much money
   * you have on your Trustly account you can use the {@link TrustlyApiClient#balance} method or simply log in to the Trustly backoffice.
   * <p>
   * <h2>Example flow 1: SelectAccount + AccountPayout</h2>
   * <ol>
   *   <li>The merchant makes an API-call to {@link TrustlyApiClient#selectAccount} and redirects the end-user to {@link SelectAccountResponseData#getUrl()}.</li>
   *   <li>The end-user logs in to their bank and selects their bank account.</li>
   *   <li>Trustly sends an {@link NotificationRequest}&lt;{@link AccountNotificationData}&gt; to the merchant's system with an {@code AccountID} for the selected account.</li>
   *   <li>The merchant makes an API-call using this method with the {@link AccountPayoutRequestData#setAmount} and {@link AccountPayoutRequestData#setCurrency} to transfer.</li>
   *   <li>Trustly's API replies with a synchronous response to let the merchant know that the AccountPayout request was received.</li>
   *   <li>
   *     A {@link NotificationRequest}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   * <p>
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact person if you want to have it enabled.
   * <p>
   *     If the payout fails, a {@link NotificationRequest}&lt;{@link CreditNotificationData}&gt; is sent (see more details <a href="https://eu.developers.trustly.com/doc/docs/accountpayout#failed-payouts">here</a>).
   *   </li>
   * </ol>
   *
   * <h2>Example flow 2: RegisterAccount + AccountPayout</h2>
   * <ol>
   *   <li>The merchant makes an API-call to {@link TrustlyApiClient#registerAccount} method with the recipient's bank account details.</li>
   *   <li>Trustly's {@link TrustlyApiClient#registerAccount} API responds with {@link RegisterAccountResponseData#getAccountId()} of the recipient's account.</li>
   *   <li>The merchant makes an API-call to this method with the {@link AccountPayoutRequestData#setAmount} and {@link AccountPayoutRequestData#setCurrency} to transfer.</li>
   *   <li>Trustly's API replies with a synchronous response to let the merchant know that the AccountPayout request was received.</li>
   *   <li>
   *     A {@link NotificationRequest}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   * <p>
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact person if you want to have it enabled.
   * <p>
   *     If the payout fails, a {@link NotificationRequest}&lt;{@link CreditNotificationData}&gt; is sent (see more details <a href="https://eu.developers.trustly.com/doc/docs/accountpayout#failed-payouts">here</a>).
   * <p>
   *     An {@code AccountID} does not expire in Trustly's system, so it can be used for multiple AccountPayout requests.
   *   </li>
   * </ol>
   */
  public AccountPayoutResponseData accountPayout(AccountPayoutRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, AccountPayoutResponseData.class, "AccountPayout", null);
  }

  /**
   * Approves a withdrawal prepared by the user. Please contact your integration manager at Trustly if you want to enable automatic approval
   * of the withdrawals.
   */
  public ApproveWithdrawalResponseData approveWithdrawal(ApproveWithdrawalRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, ApproveWithdrawalResponseData.class, "ApproveWithdrawal", null);
  }

  /**
   * This method returns the current balance for all currencies available on the merchant's Trustly account.
   * <p>
   * 🚧 Please do not use this method more than once every 15 minutes.
   */
  public BalanceResponseData balance(BalanceRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, BalanceResponseData.class, "Balance", null);
  }

  /**
   * For {@link TrustlyApiClient#charge} requests that have a future {@link ChargeRequestDataAttributes#setPaymentDate}, it’s possible to
   * cancel the Charge up until 18:30 on the {@code PaymentDate}.
   * <p>
   * A {@code Charge} request that doesn’t have any {@code PaymentDate} specified cannot be canceled. It’s also not possible to cancel a
   * {@code Charge} request if the {@code PaymentDate} is equal to the date when {@code Charge} request was sent.
   */
  public CancelChargeResponseData cancelCharge(CancelChargeRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, CancelChargeResponseData.class, "CancelCharge", null);
  }

  /**
   * Charges a specific {@link ChargeRequestData#setAccountId} using direct debit.
   * <p>
   * A previously approved direct debit mandate must exist on the {@link ChargeRequestData#setAccountId} (see
   * {@link TrustlyApiClient#selectAccount} for details).
   */
  public ChargeResponseData charge(ChargeRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, ChargeResponseData.class, "Charge", null);
  }

  /**
   * Denies a withdrawal prepared by the user.
   * <p>
   * Please contact your integration manager at Trustly if you want to enable automatic approval of the withdrawals.
   */
  public DenyWithdrawalResponseData denyWithdrawal(DenyWithdrawalRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, DenyWithdrawalResponseData.class, "DenyWithdrawal", null);
  }

  /**
   * This method returns {@link DepositResponseData#getUrl()} where the end-user can make a payment from their bank account.
   * <p>
   * A typical Deposit flow is:
   * <ol>
   *   <li>The merchant sends a Deposit API call and receives a {@link DepositResponseData#getUrl()} back from Trustly's API.</li>
   *   <li>The merchant displays the {@link DepositResponseData#getUrl()} to the end-user (you can find more information about how to display the Trustly URL <a href="https://eu.developers.trustly.com/doc/docs/presentation-of-trustly-url">here</a>).</li>
   *   <li>The end-user selects their bank and completes the payment (in case the payment is not completed, a {@link NotificationRequest}&lt;{@link CancelNotificationData}&gt; is sent).</li>
   *   <li>
   *     Trustly sends a {@link NotificationRequest}&lt;{@link PendingNotificationData}&gt; to the {@link DepositRequestData#setNotificationUrl} when the end-user has completed the payment process,
   *     and a {@link NotificationRequest}&lt;{@link CreditNotificationData}&gt; is sent when the payment is confirmed.
   *     When the funds have settled, they will be credited to the merchant's Trustly account balance.
   *   </li>
   *   <li>
   *     (Optional) An {@link NotificationRequest}&lt;{@link AccountNotificationData}&gt; is sent to provide the merchant with more information about the account that was used to make the payment.
   * <p>
   *     This notification is not enabled by default, please reach out to your Trustly contact if you want to receive it.
   *   </li>
   *   <li>
   *     In case the Deposit fails, a {@link NotificationRequest}&lt;{@link DebitNotificationData}&gt; is sent
   *     (see more information <a href="https://eu.developers.trustly.com/doc/docs/deposit#failed-deposits">here</a>).
   *   </li>
   * </ol>
   */
  public DepositResponseData deposit(DepositRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, DepositResponseData.class, "Deposit", null);
  }

  /**
   * This method returns the details of a payout (works for the {@link TrustlyApiClient#withdraw}, {@link TrustlyApiClient#accountPayout}
   * and {@link TrustlyApiClient#refund} methods).
   */
  public GetWithdrawalsResponseData getWithdrawals(GetWithdrawalsRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, GetWithdrawalsResponseData.class, "GetWithdrawals", null);
  }

  /**
   * Refunds the customer on a previous {@link TrustlyApiClient#deposit} or {@link TrustlyApiClient#charge}.
   * <p>
   * The Refund will always be made to the same bank account that was used in the original payment.
   * <p>
   * You must have sufficient funds on your merchant account to make the refund. No credit is given. If the deposit has not yet been settled
   * when the refund request is received, the refund will be queued and executed once the money for the deposit has been received.
   */
  public RefundResponseData refund(RefundRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, RefundResponseData.class, "Refund", null);
  }

  public CreateAccountResponseData createAccount(CreateAccountRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, CreateAccountResponseData.class, "CreateAccount", null);
  }

  /**
   * Initiates a new order where the end-user can select and verify one of his/her bank accounts.
   * <p>
   * You can find more information about how to display the Trustly URL here.
   * <p>
   * When the account has been verified an account notification is immediately sent to the
   * {@link SelectAccountRequestData#setNotificationUrl}.
   * <p>
   * A typical flow is:
   * <ol>
   *   <li>The merchant makes an API-call to this method and redirects the end-user to {@link SelectAccountResponseData#getUrl()}.</li>
   *   <li>The end-user selects his/her bank and completes the identification process.</li>
   *   <li>The end-user is redirected back to the merchant at {@link SelectAccountRequestDataAttributes#setSuccessUrl}. Note that the account might not be verified yet at this point.</li>
   *   <li>When the account is verified, Trustly sends an account notification to the merchant's system with information about the selected account</li>
   * </ol>
   */
  public SelectAccountResponseData selectAccount(SelectAccountRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, SelectAccountResponseData.class, "SelectAccount", null);
  }

  /**
   * Registers and verifies the format of an account to be used in {@link TrustlyApiClient#accountPayout}.
   * <p>
   * A typical payout flow is:
   * <ol>
   *   <li>The merchant makes an API-call to this method and receives an {@link RegisterAccountResponseData#getAccountId()} in response. </li>
   *   <li>The merchant saves the {@code accountid} as a valid payout option for the end user.</li>
   *   <li>
   *     When it's time to actually do a payout the merchant makes an API-call to
   *     {@link TrustlyApiClient#accountPayout} with the {@link AccountPayoutRequestData#setAmount},
   *     {@link AccountPayoutRequestData#setCurrency} and saved {@link RegisterAccountResponseData#getAccountId()}.
   *   </li>
   * </ol>
   * Multiple calls to this method with the same bank account details will result in the same {@link RegisterAccountResponseData#getAccountId()} being returned.
   */
  public RegisterAccountResponseData registerAccount(RegisterAccountRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, RegisterAccountResponseData.class, "RegisterAccount", null);
  }

  public RegisterAccountPayoutResponseData registerAccountPayout(RegisterAccountPayoutRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, RegisterAccountPayoutResponseData.class, "RegisterAccountPayout", null);
  }

  public SettlementReportResponseData settlementReport(SettlementReportRequestData request) throws TrustlyRequestException {
    return this.sendRequest(
      request, SettlementReportResponseData.class, "ViewAutomaticSettlementDetailsCSV", null
    );
  }

  /**
   * Initiates a new withdrawal, returning the URL where the end-user can complete the withdrawal process.
   * <p>
   * You can find more information about how to display the Trustly URL here.
   * <p>
   * A typical withdrawal flow is:
   *
   * <ol>
   *   <li>The merchant sends a Withdraw API call and receives a {@link WithdrawResponseData#getUrl()} back from Trustly's API.</li>
   *   <li>The merchant displays {@link WithdrawResponseData#getUrl()} to the end-user (you can find more information about how to display it <a href="https://eu.developers.trustly.com/doc/docs/presentation-of-trustly-url">here</a>).</li>
   *   <li>
   *     <span>The end-user selects the amount to withdraw and provides his/her bank account details.</span>
   *     <ul>
   *       <li>If the Withdrawal process is not completed, a {@link NotificationRequest}&lt;{@link CancelNotificationData}&gt; is sent.</li>
   *     </ul>
   *   </li>
   *   <li>
   *     <span>
   *       When the end-user has completed the withdrawal process using the {@link WithdrawResponseData#getUrl()},
   *       Trustly sends a {@link NotificationRequest}&lt;{@link DebitNotificationData}&gt; to {@link WithdrawRequestData#getNotificationUrl()}.
   *       The merchant should try to deduct the specified {@link DebitNotificationData#getAmount()} from the end-user's balance in the merchant's system.
   *      </span>
   *     <ul>
   *       <li>If the merchant is able to deduct {@link DebitNotificationData#getAmount()} from the user's balance, the debit notification response should be sent with {@code "status": "OK"}.</li>
   *       <li>
   *         If the merchant is NOT able to deduct {@link DebitNotificationData#getAmount()} from the user's balance, the debit notification response should be sent with {@code "status": "FAILED"}.
   *         The withdrawal is then aborted on Trustly's side and an error message is shown to the end-user. A {@link NotificationRequest}&lt;{@link CancelNotificationData}&gt; is sent to the merchant.
   *       </li>
   *     </ul>
   *   </li>
   *   <li>
   *     (Optional) An {@link NotificationRequest}&lt;{@link AccountNotificationData}&gt; is sent to provide the merchant with more information about the account that was selected by the end user.
   *     This notification is not enabled by default, please reach out to your Trustly contact if you want to receive it.
   *     This information can be used by the merchant to determine if the Withdrawal should be approved or not (see next step).
   *   </li>
   *   <li>
   *     <span>
   *       If manual approval is required, Trustly does nothing with the withdrawal request until it has been approved or denied by the merchant with {@link TrustlyApiClient#approveWithdrawal} / {@link TrustlyApiClient#denyWithdrawal}.
   *       (it is also possible for the merchant to approve or deny the withdrawal in Trustly's backoffice).
   *       Auto-approval can be enabled if requested.
   *     </span>
   *     <ul>
   *       <li>If {@link TrustlyApiClient#denyWithdrawal} is sent, the withdrawal is aborted on Trustly's side and a {@link NotificationRequest}&lt;{@link CancelNotificationData}&gt; and {@link NotificationRequest}&lt;{@link CreditNotificationData}&gt; is sent to the merchant.</li>
   *     </ul>
   *   </li>
   *   <li>If the Withdrawal is approved, Trustly will process the withdrawal.</li>
   *   <li>
   *     (Optional) A {@link NotificationRequest}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact if you want to have it enabled.
   *   </li>
   *   <li>If the withdrawal fails, Trustly will send a {@link NotificationRequest}&lt;{@link CreditNotificationData}&gt; notification and a {@link NotificationRequest}&lt;{@link CancelNotificationData}&gt;
   *   (see more details <a href="https://eu.developers.trustly.com/doc/docs/withdraw#failed-withdrawals">here</a>).</li>
   * </ol>
   */
  public WithdrawResponseData withdraw(WithdrawRequestData request) throws TrustlyRequestException {
    return this.sendRequest(request, WithdrawResponseData.class, "Withdraw", null);
  }

  // Notifications

  /**
   * Add a custom listener for a certain notification type.
   * <p>
   * This method should only be used if there is no existing {@code addOnXyzListener} method for the notification you want.
   */
  public <D extends IFromTrustlyRequestData> void addNotificationListener(String method, Class<D> dataClass,
    NotificationEvent<D> listener) {

    NotificationMeta<D> meta = (NotificationMeta<D>) this.onNotification.computeIfAbsent(method, k -> new NotificationMeta<>(dataClass));
    if (!meta.getDataClass().equals(dataClass)) {
      throw new IllegalArgumentException(
        String.format("Each notification method must be registered with the same type (%s vs %s)", dataClass, meta.getDataClass()));
    }

    meta.getListeners().add(listener);
  }

  public void addOnAccountListener(NotificationEvent<AccountNotificationData> listener) {
    this.addNotificationListener("account", AccountNotificationData.class, listener);
  }

  public void addOnCancelListener(NotificationEvent<CancelNotificationData> listener) {
    this.addNotificationListener("cancel", CancelNotificationData.class, listener);
  }

  public void addOnCreditListener(NotificationEvent<CreditNotificationData> listener) {
    this.addNotificationListener("credit", CreditNotificationData.class, listener);
  }

  public void addOnDebitListener(NotificationEvent<DebitNotificationData> listener) {
    this.addNotificationListener("debit", DebitNotificationData.class, listener);
  }

  public void addOnPayoutConfirmation(NotificationEvent<PayoutConfirmationNotificationData> listener) {
    this.addNotificationListener("payoutconfirmation", PayoutConfirmationNotificationData.class, listener);
  }

  public void addOnPending(NotificationEvent<PendingNotificationData> listener) {
    this.addNotificationListener("pending", PendingNotificationData.class, listener);
  }

  public void addOnUnknownNotification(NotificationEvent<UnknownNotificationData> listener) {
    this.addNotificationListener("", UnknownNotificationData.class, listener);
  }

  // Base functionality

  /**
   * Used internally to create a request package. You usually do not need to directly call this method unless you are creating a custom
   * request that exist in the documentation but not as a managed type in this class.
   *
   * @param requestData The request data that will be used for the request
   * @param method      The method of the JsonRpc package
   * @param uuid        The UUID for the message, if null one will be generated for you.
   * @param <T>         The type of the request data
   * @return The JsonRpc response data
   * @throws TrustlyValidationException Thrown if the request does not pass proper validations
   */
  public <T extends IRequestParamsData> JsonRpcRequest<T> createRequestPackage(
    T requestData,
    String method,
    String uuid
  ) throws TrustlyValidationException {

    JsonRpcRequest<T> rpcRequest = this.objectFactory.create(requestData, method, uuid);
    JsonRpcRequest<T> signedRpcRequest = this.signer.sign(rpcRequest);

    this.validator.validate(signedRpcRequest);

    return signedRpcRequest;
  }

  /**
   * Used internally to create a response package.
   *
   * @param method       The method of the JsonRpc package
   * @param uuid         The UUID for the message, if null one will be generated for you
   * @param responseData The response data that was received remotely
   * @param <R>          The type of the response data
   * @return A signed and validated JsonRpc response package
   * @throws TrustlyValidationException Thrown if the response does not pass proper validations
   */
  public <R extends IResponseResultData> JsonRpcResponse<R> createResponsePackage(
    String method,
    String uuid,
    R responseData
  ) throws TrustlyValidationException {

    JsonRpcResponse<R> rpcResponse = JsonRpcResponse.<R>builder()
      .version("1.1")
      .result(
        ResponseResult.<R>builder()
          .data(responseData)
          .method(method)
          .uuid(uuid)
          .build()
      )
      .build();

    JsonRpcResponse<R> signedResponse = this.signer.sign(rpcResponse);

    this.validator.validate(signedResponse);

    return signedResponse;
  }

  /**
   * Manually send a request to Trustly with the specified data and method and uuid.
   * <p>
   * Should only be used if you need to call an undocumented/newly released method that is not yet added to this library.
   */
  public <T extends IToTrustlyRequestParams, R extends IResponseResultData> R sendRequest(
    T requestData,
    Class<R> clazz,
    String method,
    String uuid
  ) throws TrustlyRequestException {

    try {
      return this.sendRequestWithSpecificExceptions(requestData, clazz, method, uuid);
    } catch (IOException
             | TrustlyValidationException
             | TrustlyErrorResponseException
             | TrustlyRejectionException
             | TrustlySignatureException e) {

      throw new TrustlyRequestException(e);
    }
  }

  /**
   * Sends given request to Trustly.
   *
   * @param requestData Request to send to Trustly API
   * @param clazz       Type of the JsonRpc response data
   * @param method      The RPC method name of the request
   * @param uuid        Optional UUID for the request. If not specified, one will be generated
   * @param <T>         The outgoing JsonRpc request data type
   * @param <R>         The expected JsonRpc response data type
   * @return Response generated from the request
   * @throws IOException                   If the remote end could not be contacted
   * @throws TrustlyErrorResponseException If the response from Trustly contains an error body
   * @throws TrustlyRejectionException     If the request was rejected by Trustly from their server
   * @throws TrustlySignatureException     If the signature of the request or response could not be verified
   * @throws TrustlyValidationException    If the request or response could not be properly validated
   */
  private <T extends IToTrustlyRequestParams, R extends IResponseResultData> R sendRequestWithSpecificExceptions(
    T requestData,
    Class<R> clazz,
    String method,
    String uuid
  ) throws TrustlyErrorResponseException, IOException, TrustlyRejectionException, TrustlySignatureException, TrustlyValidationException {

    requestData.setUsername(this.settings.getUsername());
    requestData.setPassword(this.settings.getPassword());

    JsonRpcRequest<T> rpcRequest = this.createRequestPackage(requestData, method, uuid);

    String requestString = this.objectMapper.writeValueAsString(rpcRequest);

    String responseString = this.httpRequester.request(this.settings, requestString);

    JsonNode rpcNodeResponse = this.objectMapper.readTree(responseString);
    JavaType javaResponseType = this.objectMapper.getTypeFactory().constructParametricType(JsonRpcResponse.class, clazz);
    JsonRpcResponse<R> rpcResponse = this.objectMapper.readValue(responseString, javaResponseType);

    assertSuccessful(rpcResponse);
    assertWithoutRejection(rpcResponse);

    this.signer.verify(rpcResponse, rpcNodeResponse);

    if (TrustlyStringUtils.isBlank(rpcResponse.getUUID()) || !rpcResponse.getUUID().equals(rpcRequest.getParams().getUuid())) {
      throw new TrustlyValidationException(
        String.format("Incoming UUID is not valid. Expected %s but got back %s", rpcRequest.getParams().getUuid(), rpcResponse.getUUID())
      );
    }

    return rpcResponse.getResult().getData();
  }

  private static <R extends IResponseResultData> void assertWithoutRejection(JsonRpcResponse<R> rpcResponse)
    throws TrustlyRejectionException {

    if (rpcResponse.getResult().getData() instanceof IWithRejectionResult) {
      IWithRejectionResult rejectionResult = (IWithRejectionResult) rpcResponse.getResult().getData();

      if (!rejectionResult.isResult()) {

        String message = rejectionResult.getRejected();
        if (TrustlyStringUtils.isBlank(message)) {
          message = "The request was rejected for an unknown reason";
        }

        throw new TrustlyRejectionException(
          "Received a rejection response from the Trustly API: " + message,
          rejectionResult.getRejected()
        );
      }
    }
  }

  private static <R extends IResponseResultData> void assertSuccessful(JsonRpcResponse<R> rpcResponse)
    throws TrustlyErrorResponseException {

    if (!rpcResponse.isSuccessfulResult()) {

      String message = null;
      if (rpcResponse.getError() != null) {
        message = rpcResponse.getError().getMessage();
        if (TrustlyStringUtils.isBlank(message)) {
          message = rpcResponse.getError().getName();
          if (TrustlyStringUtils.isBlank(message)) {
            message = ("" + rpcResponse.getError().getCode());
          }
        }
      }

      throw new TrustlyErrorResponseException(String.format("Received an error response from the Trustly API: %s", message), null,
                                              rpcResponse.getError()
      );
    }
  }

  /**
   * Will deserialize, verify and validate the incoming payload for you.
   * <p>
   * It will then call the appropriate notification listeners for this client only. If the incoming notification method does not have a
   * listener, the {@code Unknown} notification listener will be called.
   * <p>
   * It is up to your listener to call the appropriate {@link NotificationArgs#respondWithOk()} or
   * {@link NotificationArgs#respondWithFailed} methods, which will callback to your here given {@code onOK} or {@code onFailed} arguments.
   * <p>
   * It is recommended to <strong>not use this method directly</strong> if possible, and instead use
   * {@link TrustlyApiClientExtensions#handleNotificationRequest} which will call all registered {@link TrustlyApiClient} notification
   * listeners, and handle the servlet request reading and response writing.
   * <p>
   * If you want to handle the reading and writing yourself, then call this method from your own controller or servlet to help with the
   * handling of an incoming notification.
   *
   * @param jsonString The incoming notification as a JSON string
   * @param onOK The callback which will be executed if a listener calls {@link NotificationArgs#respondWithOk()}.
   * @param onFailed The callback which will be executed if a listener calls {@link NotificationArgs#respondWithFailed(String)}.
   *
   * @throws IOException If the JSON string could not be deserialized or the response could not be sent.
   * @throws TrustlyNoNotificationListenerException If there was no listener for the notification, nor one for unknown ones.
   * @throws TrustlyValidationException If the response data could not be properly validated.
   * @throws TrustlySignatureException If the signature of the response could not be properly verified.
   */
  public void handleNotification(
    String jsonString,
    NotificationOkHandler onOK,
    NotificationFailHandler onFailed
  ) throws IOException, TrustlyNoNotificationListenerException, TrustlyValidationException, TrustlySignatureException {

    JsonNode jsonToken = this.objectMapper.readTree(jsonString);
    String methodValue = jsonToken.at("/method").asText("").toLowerCase(Locale.ROOT);

    NotificationMeta<? extends IFromTrustlyRequestData> mapper = this.onNotification.get(methodValue);

    if (mapper == null || mapper.getListeners().isEmpty()) {
      log.warn(String.format("There is no listener for incoming notification '%s'. Will fallback on 'unknown' listener", methodValue));
      mapper = this.onNotification.get("");
      if (mapper == null || mapper.getListeners().isEmpty()) {
        throw new TrustlyNoNotificationListenerException(String.format("There is no listener for incoming notification '%s' nor unknown", methodValue));
      }
    }

    this.handleNotification(jsonString, mapper, onOK, onFailed);
  }

  private <D extends IFromTrustlyRequestData> void handleNotification(
    String jsonString,
    NotificationMeta<D> meta,
    NotificationOkHandler onOK,
    NotificationFailHandler onFailed
  ) throws IOException, TrustlyValidationException, TrustlySignatureException {

    JavaType javaRequestType = this.objectMapper.getTypeFactory().constructParametricType(NotificationRequest.class, meta.getDataClass());
    NotificationRequest<D> rpcRequest = this.objectMapper.readValue(jsonString, javaRequestType);

    // Verify the notification (RpcRequest from Trustly) signature.
    try {
      this.signer.verify(rpcRequest);
    } catch (TrustlySignatureException ex) {
      throw new TrustlySignatureException(
        "Could not validate signature of notification from Trustly. Is the public key for Trustly the correct one, for test or production?",
        ex
      );
    }

    // Validate the incoming request instance.
    // Most likely this will do nothing, since we are lenient on things sent from Trustly server.
    // But we do this in case anything is needed to be validated on the local domain classes in the future.
    this.validator.validate(rpcRequest);

    NotificationArgs<D> args = new NotificationArgs<>(
      rpcRequest.getParams().getData(),
      rpcRequest.getMethod(),
      rpcRequest.getParams().getUuid(),
      onOK, onFailed
    );

    try {

      for (NotificationEvent<D> listener : meta.getListeners()) {
        listener.onNotification(args);
      }
    } catch (Exception ex) {
      String message = this.settings.isIncludeExceptionMessageInNotificationResponse() ? ex.getMessage() : null;
      onFailed.handle(rpcRequest.getMethod(), rpcRequest.getParams().getUuid(), message);
    }
  }
}
