package com.trustly.api.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.trustly.api.client.NotificationArgs.NotificationHandler;
import com.trustly.api.domain.Models;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.domain.exceptions.TrustlyRejectionException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.domain.notifications.UnknownNotificationAckData;
import com.trustly.api.request.ApacheHttpClient3HttpRequesterLoader;
import com.trustly.api.request.ApacheHttpClient4HttpRequesterLoader;
import com.trustly.api.request.ApacheHttpClient5HttpRequesterLoader;
import com.trustly.api.request.HttpRequester;
import com.trustly.api.request.HttpRequesterLoader;
import com.trustly.api.request.JavaUrlConnectionHttpRequesterLoader;
import com.trustly.api.util.TrustlyStringUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.trustly.api.domain.Models.*;

@Slf4j
public class TrustlyApiClient implements Closeable {

  private static final HttpRequesterLoader[] AVAILABLE_HTTP_REQUESTERS = new HttpRequesterLoader[]{
    new ApacheHttpClient5HttpRequesterLoader(),
    new ApacheHttpClient4HttpRequesterLoader(),
    new ApacheHttpClient3HttpRequesterLoader(),
    new JavaUrlConnectionHttpRequesterLoader()
  };

  private static HttpRequester getFirstAvailableHttpRequester() {

    HttpRequester foundHttpRequester = null;
    for (HttpRequesterLoader loader : AVAILABLE_HTTP_REQUESTERS) {

      foundHttpRequester = loader.create();
      if (foundHttpRequester != null) {
        break;
      }
    }

    if (foundHttpRequester == null) {
      throw new IllegalStateException("Could not find a suitable http requester factory");
    }

    return foundHttpRequester;
  }

  @Value
  private static class NotificationMeta<D> {

    Class<D> dataClass;
    List<NotificationEvent<D, Models.NotificationResponseDataBase<?>>> listeners = new ArrayList<>();
  }

  public static final ObjectMapper DEFAULT_OBJECT_MAPPER = JsonMapper.builder()
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
    .serializationInclusion(JsonInclude.Include.NON_EMPTY)
    .build();

  private final TrustlyApiClientSettings settings;

  private final ObjectMapper objectMapper = DEFAULT_OBJECT_MAPPER;
  private final JsonRpcFactory objectFactory = new JsonRpcFactory();
  private final JsonRpcSigner signer;
  private final JsonRpcValidator validator = new JsonRpcValidator();
  private final HttpRequester httpRequester;

  private final Map<String, NotificationMeta<?>> onNotification = new HashMap<>();

  public TrustlyApiClientSettings getSettings() {
    return settings;
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings) {
    this(settings, new DefaultJsonRpcSigner(new Serializer(), settings), TrustlyApiClient.getFirstAvailableHttpRequester());
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings, JsonRpcSigner signer) {
    this(settings, signer, TrustlyApiClient.getFirstAvailableHttpRequester());
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings, HttpRequester httpRequester) {
    this(settings, new DefaultJsonRpcSigner(new Serializer(), settings), httpRequester);
  }

  public TrustlyApiClient(TrustlyApiClientSettings settings, JsonRpcSigner signer, HttpRequester httpRequester) {
    this.settings = settings;
    this.signer = signer;
    this.httpRequester = httpRequester;
  }

  @Override
  public void close() {

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
  public List<AccountLedgerResponse.Result.DataEntry> accountLedger(AccountLedgerRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, AccountLedgerResponse.class, "AccountLedger", null);
  }

  /**
   * This method is used by merchants to transfer money to their customer's bank accounts.
   * <p>
   * The merchant specifies the receiving bank account in {@link AccountPayoutRequestData#setAccountId}, which is a unique identifier
   * generated by Trustly.
   * <p>
   * The merchant can get the {@code AccountID} from {@link JsonRpcNotification}&lt;{@link AccountNotificationData}&gt; which is sent after
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
   *   <li>Trustly sends an {@link JsonRpcNotification}&lt;{@link AccountNotificationData}&gt; to the merchant's system with an {@code AccountID} for the selected account.</li>
   *   <li>The merchant makes an API-call using this method with the {@link AccountPayoutRequestData#setAmount} and {@link AccountPayoutRequestData#setCurrency} to transfer.</li>
   *   <li>Trustly's API replies with a synchronous response to let the merchant know that the AccountPayout request was received.</li>
   *   <li>
   *     A {@link JsonRpcNotification}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   * <p>
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact person if you want to have it enabled.
   * <p>
   *     If the payout fails, a {@link JsonRpcNotification}&lt;{@link CreditNotificationData}&gt; is sent (see more details <a href="https://eu.developers.trustly.com/doc/docs/accountpayout#failed-payouts">here</a>).
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
   *     A {@link JsonRpcNotification}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   * <p>
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact person if you want to have it enabled.
   * <p>
   *     If the payout fails, a {@link JsonRpcNotification}&lt;{@link CreditNotificationData}&gt; is sent (see more details <a href="https://eu.developers.trustly.com/doc/docs/accountpayout#failed-payouts">here</a>).
   * <p>
   *     An {@code AccountID} does not expire in Trustly's system, so it can be used for multiple AccountPayout requests.
   *   </li>
   * </ol>
   */
  public AccountPayoutResponse.Result.Data accountPayout(AccountPayoutRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, AccountPayoutResponse.class, "AccountPayout", null);
  }

  /**
   * Approves a withdrawal prepared by the user. Please contact your integration manager at Trustly if you want to enable automatic approval
   * of the withdrawals.
   */
  public ApproveWithdrawalResponse.Result.Data approveWithdrawal(ApproveWithdrawalRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, ApproveWithdrawalResponse.class, "ApproveWithdrawal", null);
  }

  /**
   * This method returns the current balance for all currencies available on the merchant's Trustly account.
   * <p>
   * 🚧 Please do not use this method more than once every 15 minutes.
   */
  public List<BalanceResponse.Result.DataEntry> balance(BalanceRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(data, BalanceResponse.class, "Balance", null);
  }

  /**
   * For {@link TrustlyApiClient#charge} requests that have a future {@link ChargeRequestDataAttributes#setPaymentDate}, it’s possible to
   * cancel the Charge up until 18:30 on the {@code PaymentDate}.
   * <p>
   * A {@code Charge} request that doesn’t have any {@code PaymentDate} specified cannot be canceled. It’s also not possible to cancel a
   * {@code Charge} request if the {@code PaymentDate} is equal to the date when {@code Charge} request was sent.
   */
  public CancelChargeResponse.Result.Data cancelCharge(CancelChargeRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(data, CancelChargeResponse.class, "CancelCharge", null);
  }

  /**
   * Charges a specific {@link ChargeRequestData#setAccountId} using direct debit.
   * <p>
   * A previously approved direct debit mandate must exist on the {@link ChargeRequestData#setAccountId} (see
   * {@link TrustlyApiClient#selectAccount} for details).
   */
  public ChargeResponse.Result.Data charge(ChargeRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(data, ChargeResponse.class, "Charge", null);
  }

  /**
   * Denies a withdrawal prepared by the user.
   * <p>
   * Please contact your integration manager at Trustly if you want to enable automatic approval of the withdrawals.
   */
  public DenyWithdrawalResponse.Result.Data denyWithdrawal(DenyWithdrawalRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, DenyWithdrawalResponse.class, "DenyWithdrawal", null);
  }

  /**
   * This method returns {@link DepositResponse.Result.Data#getURL()} where the end-user can make a payment from their bank account.
   * <p>
   * A typical Deposit flow is:
   * <ol>
   *   <li>The merchant sends a Deposit API call and receives a {@link DepositResponse.Result.Data#getURL()} back from Trustly's API.</li>
   *   <li>The merchant displays the {@link DepositResponse.Result.Data#getURL()} to the end-user (you can find more information about how to display the Trustly URL <a href="https://eu.developers.trustly.com/doc/docs/presentation-of-trustly-url">here</a>).</li>
   *   <li>The end-user selects their bank and completes the payment (in case the payment is not completed, a {@link CancelDefaultNotification} is sent).</li>
   *   <li>
   *     Trustly sends a {@link PendingDefaultNotification} to the {@link DepositRequest.Params.Data#setNotificationUrl} when the end-user has completed the payment process,
   *     and a {@link CreditDefaultNotification} is sent when the payment is confirmed.
   *     When the funds have settled, they will be credited to the merchant's Trustly account balance.
   *   </li>
   *   <li>
   *     (Optional) An {@link AccountDefaultNotification}; is sent to provide the merchant with more information about the account that was used to make the payment.
   * <p>
   *     This notification is not enabled by default, please reach out to your Trustly contact if you want to receive it.
   *   </li>
   *   <li>
   *     In case the Deposit fails, a {@link DebitDefaultNotification} is sent
   *     (see more information <a href="https://eu.developers.trustly.com/doc/docs/deposit#failed-deposits">here</a>).
   *   </li>
   * </ol>
   */
  public DepositResponse.Result.Data deposit(DepositRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(data, DepositResponse.class, "Deposit", null);
  }

  /**
   * This method returns the details of a payout (works for the {@link TrustlyApiClient#withdraw}, {@link TrustlyApiClient#accountPayout}
   * and {@link TrustlyApiClient#refund} methods).
   */
  public List<GetWithdrawalsResponse.Result.DataEntry> getWithdrawals(GetWithdrawalsRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, GetWithdrawalsResponse.class, "GetWithdrawals", null);
  }

  /**
   * Refunds the customer on a previous {@link TrustlyApiClient#deposit} or {@link TrustlyApiClient#charge}.
   * <p>
   * The Refund will always be made to the same bank account that was used in the original payment.
   * <p>
   * You must have sufficient funds on your merchant account to make the refund. No credit is given. If the deposit has not yet been settled
   * when the refund request is received, the refund will be queued and executed once the money for the deposit has been received.
   */
  public RefundResponse.Result.Data refund(RefundRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, RefundResponse.class, "Refund", null);
  }

  public CreateAccountResponse.Result.Data createAccount(CreateAccountRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, CreateAccountResponse.class, "CreateAccount", null);
  }

  /**
   * Initiates a new order where the end-user can select and verify one of his/her bank accounts.
   * <p>
   * You can find more information about how to display the Trustly URL <a href="https://eu.developers.trustly.com/doc/docs/service-presentation">here</a>.
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
  public SelectAccountResponse.Result.Data selectAccount(SelectAccountRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, SelectAccountResponse.class, "SelectAccount", null);
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
  public RegisterAccountResponse.Result.Data registerAccount(RegisterAccountRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, RegisterAccountResponse.class, "RegisterAccount", null);
  }

  public RegisterAccountPayoutResponse.Result.Data registerAccountPayout(RegisterAccountPayoutRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, RegisterAccountPayoutResponse.class, "RegisterAccountPayout", null);
  }

  /**
   * Initiate a settlement of funds, which will always be sent to the last settlement account for that specific currency.
   * <p>
   * The first settlement is required to be done through Trustly Back Office as the receiving bank account needs to be registered before a settlement can be processed.
   */
  public MerchantSettlementResponse.Result.Data merchantSettlement(MerchantSettlementRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, MerchantSettlementResponse.class, "MerchantSettlement", null);
  }

  public SettlementReportResponse.Result.Data settlementReport(SettlementReportRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, SettlementReportResponse.class, "ViewAutomaticSettlementDetailsCSV", null);
  }

  /**
   * Initiates a new withdrawal, returning the URL where the end-user can complete the withdrawal process.
   * <p>
   * You can find more information about how to display the Trustly URL <a href="https://eu.developers.trustly.com/doc/docs/presentation-of-trustly-url">here</a>.
   * <p>
   * A typical withdrawal flow is:
   *
   * <ol>
   *   <li>The merchant sends a Withdraw API call and receives a {@link WithdrawResponseData#getUrl()} back from Trustly's API.</li>
   *   <li>The merchant displays {@link WithdrawResponseData#getUrl()} to the end-user (you can find more information about how to display it <a href="https://eu.developers.trustly.com/doc/docs/presentation-of-trustly-url">here</a>).</li>
   *   <li>
   *     <span>The end-user selects the amount to withdraw and provides his/her bank account details.</span>
   *     <ul>
   *       <li>If the Withdrawal process is not completed, a {@link JsonRpcNotification}&lt;{@link CancelNotificationData}&gt; is sent.</li>
   *     </ul>
   *   </li>
   *   <li>
   *     <span>
   *       When the end-user has completed the withdrawal process using the {@link WithdrawResponseData#getUrl()},
   *       Trustly sends a {@link JsonRpcNotification}&lt;{@link DebitNotificationData}&gt; to {@link WithdrawRequestData#getNotificationUrl()}.
   *       The merchant should try to deduct the specified {@link DebitNotificationData#getAmount()} from the end-user's balance in the merchant's system.
   *      </span>
   *     <ul>
   *       <li>If the merchant is able to deduct {@link DebitNotificationData#getAmount()} from the user's balance, the debit notification response should be sent with {@code "status": "OK"}.</li>
   *       <li>
   *         If the merchant is NOT able to deduct {@link DebitNotificationData#getAmount()} from the user's balance, the debit notification response should be sent with {@code "status": "FAILED"}.
   *         The withdrawal is then aborted on Trustly's side and an error message is shown to the end-user. A {@link JsonRpcNotification}&lt;{@link CancelNotificationData}&gt; is sent to the merchant.
   *       </li>
   *     </ul>
   *   </li>
   *   <li>
   *     (Optional) An {@link JsonRpcNotification}&lt;{@link AccountNotificationData}&gt; is sent to provide the merchant with more information about the account that was selected by the end user.
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
   *       <li>If {@link TrustlyApiClient#denyWithdrawal} is sent, the withdrawal is aborted on Trustly's side and a {@link JsonRpcNotification}&lt;{@link CancelNotificationData}&gt; and {@link JsonRpcNotification}&lt;{@link CreditNotificationData}&gt; is sent to the merchant.</li>
   *     </ul>
   *   </li>
   *   <li>If the Withdrawal is approved, Trustly will process the withdrawal.</li>
   *   <li>
   *     (Optional) A {@link JsonRpcNotification}&lt;{@link PayoutConfirmationNotificationData}&gt; is sent to the merchant when the transfer has been confirmed.
   *     Note: this notification is not enabled by default. Please speak to your Trustly contact if you want to have it enabled.
   *   </li>
   *   <li>If the withdrawal fails, Trustly will send a {@link JsonRpcNotification}&lt;{@link CreditNotificationData}&gt; notification and a {@link JsonRpcNotification}&lt;{@link CancelNotificationData}&gt;
   *   (see more details <a href="https://eu.developers.trustly.com/doc/docs/withdraw#failed-withdrawals">here</a>).</li>
   * </ol>
   */
  public WithdrawResponse.Result.Data withdraw(WithdrawRequest.Params.Data request) throws TrustlyRequestException {
    return this.sendRequest(request, WithdrawResponse.class, "Withdraw", null);
  }

  // Notifications

  /**
   * Add a custom listener for a certain notification type.
   * <p>
   * This method should only be used if there is no existing {@code addOnXyzListener} method for the notification you want.
   */
  public <TCallbackData, TAckData extends NotificationResponseDataBase<?>> void addNotificationListener(
    String method,
    Class<TCallbackData> dataClass,
    NotificationEvent<TCallbackData, TAckData> listener
  ) {

    NotificationMeta<TCallbackData> meta = (NotificationMeta<TCallbackData>) this.onNotification.computeIfAbsent(method, k -> new NotificationMeta<>(dataClass));
    if (!meta.getDataClass().equals(dataClass)) {
      throw new IllegalArgumentException(
        String.format("Each notification method must be registered with the same type (%s vs %s)", dataClass, meta.getDataClass()));
    }

    meta.getListeners().add((NotificationEvent<TCallbackData, NotificationResponseDataBase<?>>) listener);
  }

  public void addOnAccountListener(NotificationEvent<AccountDefaultNotification.Params.Data, GeneralNotificationResponseData> listener) {
    this.addNotificationListener("account", AccountDefaultNotification.Params.Data.class, listener);
  }

  public void addOnCancelListener(NotificationEvent<CancelDefaultNotification.Params.Data, GeneralNotificationResponseData> listener) {
    this.addNotificationListener("cancel", CancelDefaultNotification.Params.Data.class, listener);
  }

  public void addOnCreditListener(NotificationEvent<CreditDefaultNotification.Params.Data, GeneralNotificationResponseData> listener) {
    this.addNotificationListener("credit", CreditDefaultNotification.Params.Data.class, listener);
  }

  public void addOnDebitListener(NotificationEvent<DebitDefaultNotification.Params.Data, DebitNotificationResponseData> listener) {
    this.addNotificationListener("debit", DebitDefaultNotification.Params.Data.class, listener);
  }

  public void addOnPayoutConfirmation(NotificationEvent<PayoutConfirmationNotification.Params.Data, GeneralNotificationResponseData> listener) {
    this.addNotificationListener("payoutconfirmation", PayoutConfirmationNotification.Params.Data.class, listener);
  }

  public void addOnPending(NotificationEvent<PendingDefaultNotification.Params.Data, GeneralNotificationResponseData> listener) {
    this.addNotificationListener("pending", PendingDefaultNotification.Params.Data.class, listener);
  }

  public void addOnKYC(NotificationEvent<KYCNotification.Params.Data, KYCNotificationResponse.Result.Data> listener) {
    this.addNotificationListener("pending", KYCNotification.Params.Data.class, listener);
  }

  public void addOnUnknownNotification(NotificationEvent<Any, UnknownNotificationAckData> listener) {
    this.addNotificationListener("", Any.class, listener);
  }

  // Base functionality

  /**
   * Used internally to create a request package. You usually do not need to directly call this method unless you are creating a custom
   * request that exist in the documentation but not as a managed type in this class.
   *
   * @param requestData The request data that will be used for the request
   * @param method      The method of the JsonRpc package
   * @param uuid        The UUID for the message, if null one will be generated for you.
   * @param <TParams>   The type of the request data
   * @return The JsonRpc response data
   * @throws TrustlyValidationException Thrown if the request does not pass proper validations
   */
  public <TData extends AbstractRequestData> JsonRpcRequest<JsonRpcRequestParams<TData>> createRequestPackage(
    TData requestData,
    String method,
    String uuid
  ) throws TrustlyValidationException {

    var request = this.objectFactory.create(requestData, method, uuid);
    var signedRequest = this.signer.sign(request);

    this.validator.validate(signedRequest);

    return signedRequest;
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
  public <D> JsonRpcResponse<ResponseResult<D>> createResponsePackage(
    String method,
    String uuid,
    D responseData
  ) throws TrustlyValidationException {

    JsonRpcResponse<ResponseResult<D>> rpcResponse = JsonRpcResponse.<ResponseResult<D>>builder()
      .result(
        ResponseResult.<D>builder()
          .data(responseData)
          .method(method)
          .UUID(uuid)
          .build()
      )
      .build();

    JsonRpcResponse<ResponseResult<D>> signedResponse = this.signer.sign(rpcResponse);

    this.validator.validate(signedResponse);

    return signedResponse;
  }

  /**
   * Manually send a request to Trustly with the specified data and method and uuid.
   * <p>
   * Should only be used if you need to call an undocumented/newly released method that is not yet added to this library.
   */
  public <
    TReqData extends AbstractRequestData,
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  TResData sendRequest(
    TReqData requestData,
    Class<TRes> resClass,
    String method,
    String uuid
  ) throws TrustlyRequestException {

    requestData.setUsername(this.settings.getUsername());
    requestData.setPassword(this.settings.getPassword());

    try {
      return sendRequest(this.createRequestPackage(requestData, method, uuid), resClass);
    } catch (TrustlyValidationException ex) {
      throw new TrustlyRequestException(ex);
    }
  }

  private <
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  TResData sendRequest(JsonRpcRequest<?> rpcRequest, Class<TRes> resClass) throws TrustlyRequestException {
    Objects.requireNonNull(rpcRequest, "The request must not be null");

    try {
      String requestString = this.objectMapper.writeValueAsString(rpcRequest);
      String responseString = this.httpRequester.request(this.settings, requestString);

      JsonNode rpcNodeResponse = this.objectMapper.readTree(responseString);

      if (rpcNodeResponse.has("error") || !rpcNodeResponse.has("result")) {

        JsonRpcErrorResponse rpcErrorResponse = this.objectMapper.convertValue(rpcNodeResponse, JsonRpcErrorResponse.class);

        String message = null;
        if (rpcErrorResponse.getError() != null) {
          message = rpcErrorResponse.getError().getMessage();
          if (TrustlyStringUtils.isBlank(message)) {
            message = rpcErrorResponse.getError().getName();
            if (TrustlyStringUtils.isBlank(message)) {
              message = ("" + rpcErrorResponse.getError().getCode());
            }
          }
        }

        throw new TrustlyErrorResponseException(String.format("Received an error response from the Trustly API: %s", message), null,
          rpcErrorResponse.getError()
        );
      }

      TRes rpcResponse = this.objectMapper.convertValue(rpcNodeResponse, resClass);

      assertWithoutRejection(rpcResponse);

      this.signer.verify(rpcResponse, rpcNodeResponse);

      final var responseResult = rpcResponse.getResult();
      final var requestParams = rpcRequest.getParams();

      if (!Objects.equals(responseResult.getUUID(), requestParams.getUuid())) {
        throw new TrustlyValidationException(
          String.format("Incoming UUID is not valid. Expected %s but got back %s", rpcRequest.getParams().getUuid(), rpcResponse.getResult().getUUID())
        );
      }

      return rpcResponse.getResult().getData();
    } catch (Exception ex) {
      throw new TrustlyRequestException(ex);
    }
  }

  private static <TData, TResParams extends ResponseResult<TData>> void assertWithoutRejection(JsonRpcResponse<TResParams> rpcResponse)
    throws TrustlyRejectionException {

    if (rpcResponse.getResult().getData() instanceof WithRejection) {
      WithRejection<?> rejectionResult = (WithRejection<?>) rpcResponse.getResult().getData();

      if (rejectionResult.getRejected() != null) {

        String message = Objects.toString(rejectionResult.getRejected());
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

//  private static <TResData, TResParams extends AbstractResponseResult<TResData>> void assertSuccessful(JsonRpcResponse<TResParams> rpcResponse)
//    throws TrustlyErrorResponseException {
//
//    if (!rpcResponse.isSuccessfulResult()) {
//
//      String message = null;
//      if (rpcResponse.getError() != null) {
//        message = rpcResponse.getError().getMessage();
//        if (TrustlyStringUtils.isBlank(message)) {
//          message = rpcResponse.getError().getName();
//          if (TrustlyStringUtils.isBlank(message)) {
//            message = ("" + rpcResponse.getError().getCode());
//          }
//        }
//      }
//
//      throw new TrustlyErrorResponseException(String.format("Received an error response from the Trustly API: %s", message), null,
//        rpcResponse.getError()
//      );
//    }
//  }

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
   * @param onOK       The callback which will be executed if a listener calls {@link NotificationArgs#respondWithOk()}.
   * @param onFailed   The callback which will be executed if a listener calls {@link NotificationArgs#respondWithFailed(String)}.
   * @throws IOException                            If the JSON string could not be deserialized or the response could not be sent.
   * @throws TrustlyNoNotificationListenerException If there was no listener for the notification, nor one for unknown ones.
   * @throws TrustlyValidationException             If the response data could not be properly validated.
   * @throws TrustlySignatureException              If the signature of the response could not be properly verified.
   */
  public <S, TAckData extends NotificationResponseDataBase<S>> void handleNotification(
    String jsonString,
    NotificationHandler<TAckData> onOK
  ) throws IOException, TrustlyNoNotificationListenerException, TrustlyValidationException, TrustlySignatureException {

    var jsonToken = this.objectMapper.readTree(jsonString);
    var methodValue = jsonToken.at("/method").asText("").toLowerCase(Locale.ROOT);

    var mapper = this.onNotification.get(methodValue);

    if (mapper == null || mapper.getListeners().isEmpty()) {
      log.warn("There is no listener for incoming notification '{}'. Will fallback on 'unknown' listener", methodValue);
      mapper = this.onNotification.get("");
      if (mapper == null || mapper.getListeners().isEmpty()) {
        throw new TrustlyNoNotificationListenerException(String.format("There is no listener for incoming notification '%s' nor unknown", methodValue));
      }
    }

    this.handleNotification(jsonString, mapper, onOK);
  }

  private <D, S, TAckData extends NotificationResponseDataBase<S>> void handleNotification(
    String jsonString,
    NotificationMeta<D> meta,
    NotificationHandler<TAckData> onOK
  ) throws IOException, TrustlyValidationException, TrustlySignatureException {

    var requestNode = this.objectMapper.readTree(jsonString);

    // final var params = request.getParams();
    //    final var dataNode = TrustlyApiClient.DEFAULT_OBJECT_MAPPER.valueToTree(params.getData()); // TODO: WRONG! SHOULD GIVE UNTOUCHED NODE
    //    this.verify(request.getMethod(), params.getUUID(), dataNode, params.getSignature());

    // Verify the notification (RpcRequest from Trustly) signature.
    try {
      this.signer.verify(
        requestNode.path("method").asText(null),
        requestNode.path("params").path("uuid").asText(null),
        requestNode.path("params").path("data"),
        requestNode.path("params").path("signature").asText(null)
      );
    } catch (TrustlySignatureException ex) {
      throw new TrustlySignatureException(
        "Could not validate signature of notification from Trustly. Is the public key for Trustly the correct one, for test or production?",
        ex
      );
    }

    var javaParamsType = this.objectMapper.getTypeFactory().constructParametricType(JsonRpcNotificationParams.class, meta.getDataClass());
    var javaRequestType = this.objectMapper.getTypeFactory().constructParametricType(JsonRpcNotification.class, javaParamsType);

    JsonRpcNotification<JsonRpcNotificationParams<D>> notificationRequest = this.objectMapper.treeToValue(requestNode, javaRequestType);

    // Validate the incoming request instance.
    // Most likely this will do nothing, since we are lenient on things sent from Trustly server.
    // But we do this in case anything is needed to be validated on the local domain classes in the future.
    this.validator.validate(notificationRequest);

    var args = new NotificationArgs<>(
      notificationRequest.getParams().getData(),
      notificationRequest.getMethod(),
      notificationRequest.getParams().getUUID(),
      onOK
    );

//    try {

    for (var listener : meta.getListeners()) {
      listener.onNotification((NotificationArgs<D, NotificationResponseDataBase<?>>) args);
    }
//    } catch (Exception ex) {
//      String message = this.settings.isIncludeExceptionMessageInNotificationResponse() ? ex.getMessage() : null;
////      onFailed.handle(notificationRequest.getMethod(), notificationRequest.getParams().getUuid(), message);
//    }
  }
}
