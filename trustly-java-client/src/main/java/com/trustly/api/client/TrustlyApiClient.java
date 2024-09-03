package com.trustly.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.DefaultJsonRpcSigner;
import com.trustly.api.JsonRpcSigner;
import com.trustly.api.Serializer;
import com.trustly.api.TrustlyApiClientSettings;
import com.trustly.api.client.NotificationArgs.NotificationHandler;
import com.trustly.api.domain.Models;
import com.trustly.api.domain.notifications.UnknownNotification;
import com.trustly.api.domain.notifications.UnknownNotificationAckData;
import com.trustly.api.domain.notifications.UnknownNotificationResponse;
import com.trustly.api.exceptions.TrustlyErrorResponseException;
import com.trustly.api.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.exceptions.TrustlyRejectionException;
import com.trustly.api.exceptions.TrustlyRequestException;
import com.trustly.api.exceptions.TrustlySignatureException;
import com.trustly.api.exceptions.TrustlyValidationException;
import com.trustly.api.request.ApacheHttpClient3HttpRequesterLoader;
import com.trustly.api.request.ApacheHttpClient4HttpRequesterLoader;
import com.trustly.api.request.ApacheHttpClient5HttpRequesterLoader;
import com.trustly.api.request.HttpRequester;
import com.trustly.api.request.HttpRequesterLoader;
import com.trustly.api.request.JavaUrlConnectionHttpRequesterLoader;
import com.trustly.api.util.TrustlyStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.trustly.api.domain.Models.*;

public class TrustlyApiClient implements Closeable {

  private static final Logger log = LoggerFactory.getLogger(TrustlyApiClient.class);

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

  private static class NotificationMeta<
    TCallback extends JsonRpcNotification<? extends JsonRpcNotificationParams<TCallbackData>>,
//    TCallbackParams extends ,
    TCallbackData,

    TAckData extends Models.NotificationResponseDataBase<TAckStatus>,
    TAckStatus
    > {

    private final Class<TCallback> clazz;
    private final List<NotificationEvent<TCallbackData, TAckData>> listeners = new ArrayList<>();

    public Class<TCallback> getClazz() {
      return clazz;
    }

    public List<NotificationEvent<TCallbackData, TAckData>> getListeners() {
      return listeners;
    }

    public NotificationMeta(Class<TCallback> dataClass) {
      this.clazz = dataClass;
    }
  }

  private final TrustlyApiClientSettings settings;

  private final ObjectMapper objectMapper = TrustlyApiClientSettings.DEFAULT_OBJECT_MAPPER;
  private final JsonRpcFactory objectFactory = new JsonRpcFactory();
  private final JsonRpcSigner signer;
  private final JsonRpcValidator validator = new JsonRpcValidator();
  private final HttpRequester httpRequester;

  private final Map<String, NotificationMeta<?, ?, ?, ?>> onNotification = new HashMap<>();

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

  public List<AccountLedgerResponse.Result.DataEntry> accountLedger(AccountLedgerRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new AccountLedgerRequest(), data, AccountLedgerResponse.class);
  }

  public AccountPayoutResponse.Result.Data accountPayout(AccountPayoutRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new AccountPayoutRequest(), data, AccountPayoutResponse.class);
  }

  public ApproveWithdrawalResponse.Result.Data approveWithdrawal(ApproveWithdrawalRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new ApproveWithdrawalRequest(), data, ApproveWithdrawalResponse.class);
  }

  /**
   * This method returns the current balance for all currencies available on the merchant's Trustly account.
   * <p>
   * 🚧 Please do not use this method more than once every 15 minutes.
   */
  public List<BalanceResponse.Result.DataEntry> balance(BalanceRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new BalanceRequest(), data, BalanceResponse.class);
  }

  public CancelChargeResponse.Result.Data cancelCharge(CancelChargeRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new CancelChargeRequest(), data, CancelChargeResponse.class);
  }

  public ChargeResponse.Result.Data charge(ChargeRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new ChargeRequest(), data, ChargeResponse.class);
  }

  public DenyWithdrawalResponse.Result.Data denyWithdrawal(DenyWithdrawalRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new DenyWithdrawalRequest(), data, DenyWithdrawalResponse.class);
  }

  public DepositResponse.Result.Data deposit(DepositRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new DepositRequest(), data, DepositResponse.class);
  }

  public List<GetWithdrawalsResponse.Result.DataEntry> getWithdrawals(GetWithdrawalsRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new GetWithdrawalsRequest(), data, GetWithdrawalsResponse.class);
  }

  public RefundResponse.Result.Data refund(RefundRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new RefundRequest(), data, RefundResponse.class);
  }

  public CreateAccountResponse.Result.Data createAccount(CreateAccountRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new CreateAccountRequest(), data, CreateAccountResponse.class);
  }

  public SelectAccountResponse.Result.Data selectAccount(SelectAccountRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new SelectAccountRequest(), data, SelectAccountResponse.class);
  }

  public RegisterAccountResponse.Result.Data registerAccount(RegisterAccountRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new RegisterAccountRequest(), data, RegisterAccountResponse.class);
  }

  public RegisterAccountPayoutResponse.Result.Data registerAccountPayout(RegisterAccountPayoutRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new RegisterAccountPayoutRequest(), data, RegisterAccountPayoutResponse.class);
  }

  public MerchantSettlementResponse.Result.Data merchantSettlement(MerchantSettlementRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new MerchantSettlementRequest(), data, MerchantSettlementResponse.class);
  }

  public SettlementReportResponse.Result.Data settlementReport(SettlementReportRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new SettlementReportRequest(), data, SettlementReportResponse.class);
  }

  public WithdrawResponse.Result.Data withdraw(WithdrawRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new WithdrawRequest(), data, WithdrawResponse.class);
  }

  public DirectDebitMandateResponse.Result.Data directDebitMandate(DirectDebitMandateRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new DirectDebitMandateRequest(), data, DirectDebitMandateResponse.class);
  }

  public CancelDirectDebitMandateResponse.Result.Data cancelDirectDebitMandate(CancelDirectDebitMandateRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new CancelDirectDebitMandateRequest(), data, CancelDirectDebitMandateResponse.class);
  }

  public ImportDirectDebitMandateResponse.Result.Data cancelDirectDebitMandate(ImportDirectDebitMandateRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new ImportDirectDebitMandateRequest(), data, ImportDirectDebitMandateResponse.class);
  }

  public DirectDebitResponse.Result.Data cancelDirectDebitMandate(CancelDirectDebitRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new CancelDirectDebitRequest(), data, DirectDebitResponse.class);
  }

  public CancelDirectDebitResponse.Result.Data cancelDirectDebit(CancelDirectDebitRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new CancelDirectDebitRequest(), data, CancelDirectDebitResponse.class);
  }

  public DirectCreditResponse.Result.Data directCredit(DirectCreditRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new DirectCreditRequest(), data, DirectCreditResponse.class);
  }

  public RefundDirectDebitResponse.Result.Data directCredit(RefundDirectDebitRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new RefundDirectDebitRequest(), data, RefundDirectDebitResponse.class);
  }

  public DirectPaymentBatchResponse.Result.Data directCredit(DirectPaymentBatchRequest.Params.Data data) throws TrustlyRequestException {
    return this.sendRequest(new DirectPaymentBatchRequest(), data, DirectPaymentBatchResponse.class);
  }

  // Notifications

  /**
   * Add a custom listener for a certain notification type.
   * <p>
   * This method should only be used if there is no existing {@code addOnXyzListener} method for the notification you want.
   */
  public <
    TCallback extends JsonRpcNotification<? extends JsonRpcNotificationParams<TCallbackData>>,
    TCallbackData,

    TAck extends Models.JsonRpcResponse<TAckRes>,
    TAckRes extends Models.ResponseResult<TAckData>,
    TAckData extends Models.NotificationResponseDataBase<TAckStatus>,
    TAckStatus

    > void addNotificationListener(
    String method,
    Class<TCallback> notificationClass,
    Class<TAck> ackClass,
    NotificationEvent<TCallbackData, TAckData> listener
  ) {

    var meta = (NotificationMeta) this.onNotification.computeIfAbsent(method, k -> new NotificationMeta<>(notificationClass));
    if (!meta.getClazz().equals(notificationClass)) {
      throw new IllegalArgumentException(
        String.format("Each notification method must be registered with the same type (%s vs %s)", notificationClass, meta.getClazz()));
    }

    meta.getListeners().add(listener);
  }

  public void addOnAccountListener(NotificationEvent<AccountNotification.Params.Data, AckData> listener) {
    this.addNotificationListener("account", AccountNotification.class, AccountNotificationResponse.class, listener);
  }

  public void addOnCancelListener(NotificationEvent<CancelNotification.Params.Data, AckData> listener) {
    this.addNotificationListener("cancel", CancelNotification.class, CancelNotificationResponse.class, listener);
  }

  public void addOnCreditListener(NotificationEvent<CreditNotification.Params.Data, AckData> listener) {
    this.addNotificationListener("credit", CreditNotification.class, CreditNotificationResponse.class, listener);
  }

  public void addOnDebitListener(NotificationEvent<DebitNotification.Params.Data, DebitNotificationResponseData> listener) {
    this.addNotificationListener("debit", DebitNotification.class, DebitNotificationResponse.class, listener);
  }

  public void addOnPayoutConfirmation(NotificationEvent<PayoutConfirmationNotification.Params.Data, AckData> listener) {
    this.addNotificationListener("payoutconfirmation", PayoutConfirmationNotification.class, PayoutConfirmationNotificationResponse.class, listener);
  }

  public void addOnPending(NotificationEvent<PendingNotification.Params.Data, AckData> listener) {
    this.addNotificationListener("pending", PendingNotification.class, PendingNotificationResponse.class, listener);
  }

  public void addOnKYC(NotificationEvent<KYCNotification.Params.Data, KYCNotificationResponse.Result.Data> listener) {
    this.addNotificationListener("kyc", KYCNotification.class, KYCNotificationResponse.class, listener);
  }

  public void addOnUnknownNotification(NotificationEvent<UnknownNotification.Params.Data, UnknownNotificationAckData> listener) {
    this.addNotificationListener("", UnknownNotification.class, UnknownNotificationResponse.class, listener);
  }

  // Base functionality

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

    var rpcResponse = JsonRpcResponse.<ResponseResult<D>>builder()
      .result(
        ResponseResult.<D>builder()
          .data(responseData)
          .method(method)
          .UUID(uuid)
          .build()
      )
      .build();

    rpcResponse.getResult().setSignature(this.signer.sign(
      rpcResponse.getResult().getData(),
      rpcResponse.getResult().getMethod(),
      rpcResponse.getResult().getUUID()
    ));

    this.validator.validate(rpcResponse);

    return rpcResponse;
  }

  public <
    TReqData extends AbstractRequestData<TReqAttributes>,
    TReqAttributes extends AbstractRequestDataAttributes,
    TReqParams extends JsonRpcRequestParams<TReqData>,
    TReq extends JsonRpcRequest<TReqParams>,
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  TResData sendRequest(
    TReq rpcRequest,
    Class<TRes> resClass
  ) throws TrustlyRequestException {

    Objects.requireNonNull(rpcRequest, "The request must not be null");
    Objects.requireNonNull(rpcRequest.getParams(), "If giving just the request, you have to set the params yourself");
    Objects.requireNonNull(rpcRequest.getParams().getData(), "If giving just the request, you have to set the data yourself");

    return this.sendRequest(rpcRequest, rpcRequest.getParams().getData(), resClass);
  }

  public <
    TReqData extends AbstractRequestData<TReqAttributes>,
    TReqAttributes extends AbstractRequestDataAttributes,
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  TResData sendRequest(
    TReqData data,
    Class<TRes> resClass,
    String method,
    String uuid
  ) throws TrustlyRequestException {

    final var rpcRequest = this.objectFactory.create(data, method, uuid);
    return this.sendRequest(rpcRequest, rpcRequest.getParams().getData(), resClass);
  }

  /**
   * Manually send a request to Trustly with the specified data and method and uuid.
   * <p>
   * Should only be used if you need to call an undocumented/newly released method that is not yet added to this library.
   */
  public <
    TReqData extends AbstractRequestData<TReqAttributes>,
    TReqAttributes extends AbstractRequestDataAttributes,
    TReqParams extends JsonRpcRequestParams<TReqData>,
    TReq extends JsonRpcRequest<TReqParams>,
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  TResData sendRequest(
    TReq rpcRequest,
    TReqData requestData,
    Class<TRes> resClass
  ) throws TrustlyRequestException {

    if (rpcRequest.getParams() == null) {
      rpcRequest.setParams((TReqParams) new JsonRpcRequestParams<TReqData>());
    }

    if (rpcRequest.getParams().getData() == null) {
      rpcRequest.getParams().setData(requestData);
    }

    if (TrustlyStringUtils.isBlank(rpcRequest.getParams().getUuid())) {
      rpcRequest.getParams().setUuid(UUID.randomUUID().toString());
    }

    requestData = rpcRequest.getParams().getData();
    if (TrustlyStringUtils.isBlank(requestData.getUsername())) {
      requestData.setUsername(this.settings.getUsername());
      requestData.setPassword(this.settings.getPassword());
    }

    rpcRequest.getParams().setSignature(this.signer.sign(requestData, rpcRequest.getMethod(), rpcRequest.getParams().getUuid()));

    try {
      this.validator.validate(rpcRequest);
    } catch (TrustlyValidationException ex) {
      throw new TrustlyRequestException(ex);
    }

    try {
      var requestString = this.objectMapper.writeValueAsString(rpcRequest);
      var responseString = this.httpRequester.request(this.settings, requestString);

      var rpcNodeResponse = this.objectMapper.readTree(responseString);

      if (rpcNodeResponse.has("error") || !rpcNodeResponse.has("result")) {

        var rpcErrorResponse = this.objectMapper.convertValue(rpcNodeResponse, JsonRpcErrorResponse.class);

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

      this.signer.verify(
        rpcResponse.getResult().getMethod(),
        rpcResponse.getResult().getUUID(),
        rpcNodeResponse.path("result").path("data"),
        rpcResponse.getResult().getSignature()
      );

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
      WithRejection<?, ?> rejectionResult = (WithRejection<?, ?>) rpcResponse.getResult().getData();

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

  public <
    TAckData extends NotificationResponseDataBase<TAckStatus>,
    TAckStatus
    >
  void handleNotification(
    String jsonString,
    NotificationHandler<TAckData> handler
  ) throws IOException, TrustlyNoNotificationListenerException, TrustlyValidationException, TrustlySignatureException {

    var jsonToken = this.objectMapper.readTree(jsonString);
    var methodValue = jsonToken.path("method").asText().toLowerCase(Locale.ROOT);

    // Should be typesafe enough, unless user has registered a listener very incorrectly.
    var mapper = (NotificationMeta) this.onNotification.get(methodValue);

    if (mapper == null || mapper.getListeners().isEmpty()) {
      log.warn("There is no listener for incoming notification '{}'. Will fallback on 'unknown' listener", methodValue);
      mapper = this.onNotification.get("");
      if (mapper == null || mapper.getListeners().isEmpty()) {
        throw new TrustlyNoNotificationListenerException(String.format("There is no listener for incoming notification '%s' nor unknown", methodValue));
      }
    }

    this.handleNotification(jsonString, mapper, handler);
  }

  private <
    TCallback extends JsonRpcNotification<? extends JsonRpcNotificationParams<TCallbackData>>,
    TCallbackData,

    TMeta extends NotificationMeta<TCallback, TCallbackData, TAckData, TAckStatus>,
    THandler extends NotificationHandler<TAckData>,

    TAckData extends NotificationResponseDataBase<TAckStatus>,
    TAckStatus
    >
  void handleNotification(
    String jsonString,
    TMeta meta,
    THandler handler
  ) throws IOException, TrustlyValidationException, TrustlySignatureException {

    var requestNode = this.objectMapper.readTree(jsonString);

    // Verify the notification (RpcRequest from Trustly) signature.
    try {
      this.signer.verify(
        requestNode.path("method").asText(),
        requestNode.path("params").path("uuid").asText(),
        requestNode.path("params").path("data"),
        requestNode.path("params").path("signature").asText()
      );
    } catch (TrustlySignatureException ex) {
      throw new TrustlySignatureException(
        "Could not validate signature of notification from Trustly. Is the public key for Trustly the correct one, for test or production?",
        ex
      );
    }

    var notificationRequest = this.objectMapper.treeToValue(requestNode, meta.getClazz());

    // Validate the incoming request instance.
    // Most likely this will do nothing, since we are lenient on things sent from Trustly server.
    // But we do this in case anything is needed to be validated on the local domain classes in the future.
    this.validator.validate(notificationRequest);

    var args = new NotificationArgs<>(
      notificationRequest.getParams().getData(),
      notificationRequest.getMethod(),
      notificationRequest.getParams().getUUID(),
      handler
    );

    for (var listener : meta.getListeners()) {
      listener.onNotification(args);
    }
  }
}
