/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.trustly.api.requestbuilders;

import java.util.Map;
import java.util.TreeMap;

import com.trustly.api.commons.Currency;
import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.DepositData;
import com.trustly.api.data.request.requestdata.RecipientInformation;
import com.trustly.api.security.SignatureHandler;

/**
 * Creates a Deposit request ready to be sent to Trustly API.
 * The constructor contains the required fields of a Deposit request.
 *
 * Builder lets you add additional information if any is available for the given request.
 *
 * The API specifics of the request can be found on https://trustly.com/en/developer/
 *
 * Example use for a default Deposit request:
 * Request deposit = new Deposit.Build(notificationURL, endUserID, messageID, currency, firstName, lastName, email).getRequest();
 */
public class Deposit {
    private final Request request = new Request();

    private Deposit(final Build builder) {
        final RequestParameters params = new RequestParameters();
        params.setUUID(SignatureHandler.generateNewUUID());
        params.setData(builder.data);

        request.setMethod(Method.DEPOSIT);
        request.setParams(params);
    }

    public Request getRequest() {
        return request;
    }

    public static class Build {
        private final DepositData data = new DepositData();
        private final Map<String, Object> attributes = new TreeMap<>();

        public Build(final String notificationURL, final String endUserID, final String messageID, final Currency currency, final String firstName, final String lastName, final String email) {
            data.setNotificationURL(notificationURL);
            data.setEndUserID(endUserID);
            data.setMessageID(messageID);

            attributes.put("Currency", currency);
            attributes.put("Firstname", firstName);
            attributes.put("Lastname", lastName);
            attributes.put("Email", email);
            data.setAttributes(attributes);
        }

        public Build locale(final String locale) {
            attributes.put("Locale", locale);
            return this;
        }

        public Build suggestedMinAmount(final String suggestedMinAmount) {
            attributes.put("SuggestedMinAmount", suggestedMinAmount);
            return this;
        }

        public Build suggestedMaxAmount(final String suggestedMaxAmount) {
            attributes.put("SuggestedMaxAmount", suggestedMaxAmount);
            return this;
        }

        public Build amount(final String amount) {
            attributes.put("Amount", amount);
            return this;
        }

        public Build country(final String countryISOCode) {
            attributes.put("Country", countryISOCode);
            return this;
        }

        public Build ip(final String IP) {
            attributes.put("IP", IP);
            return this;
        }

        public Build successURL(final String successURL) {
            attributes.put("SuccessURL", successURL);
            return this;
        }

        public Build failURL(final String failURL) {
            attributes.put("FailURL", failURL);
            return this;
        }

        public Build templateURL(final String templateURL) {
            attributes.put("TemplateURL", templateURL);
            return this;
        }

        public Build urlTarget(final String urlTarget) {
            attributes.put("URLTarget", urlTarget);
            return this;
        }

        public Build mobilePhone(final String mobilePhone) {
            attributes.put("MobilePhone", mobilePhone);
            return this;
        }

        public Build nationalIdentificationNumber(final String nin) {
            attributes.put("NationalIdentificationNumber", nin);
            return this;
        }

        public Build recipientInformation(final RecipientInformation recipientInformation) {
            attributes.put("RecipientInformation", recipientInformation);
            return this;
        }

        public Build shopperStatement(final String shopperStatement) {
            attributes.put("ShopperStatement", shopperStatement);
            return this;
        }

        public Build shippingAddressCountry(final String shippingAddressCountry) {
            attributes.put("ShippingAddressCountry", shippingAddressCountry);
            return this;
        }

        public Build shippingAddressPostalCode(final String shippingAddressPostalCode) {
            attributes.put("ShippingAddressPostalCode", shippingAddressPostalCode);
            return this;
        }

        public Build shippingAddressCity(final String shippingAddressCity) {
            attributes.put("ShippingAddressCity", shippingAddressCity);
            return this;
        }

        public Build shippingAddressLine1(final String shippingAddressLine1) {
            attributes.put("ShippingAddressLine1", shippingAddressLine1);
            return this;
        }

        public Build shippingAddressLine2(final String shippingAddressLine2) {
            attributes.put("ShippingAddressLine2", shippingAddressLine2);
            return this;
        }

        public Build shippingAddress(final String shippingAddress) {
            attributes.put("ShippingAddress", shippingAddress);
            return this;
        }

        public Build requestDirectDebitMandate(final String requestDirectDebitMandate) {
            attributes.put("RequestDirectDebitMandate", requestDirectDebitMandate);
            return this;
        }

        public Build chargeAccountID(final String chargeAccountId) {
            attributes.put("ChargeAccountID", chargeAccountId);
            return this;
        }

        public Build quickDeposit(final String quickDeposit) {
            attributes.put("QuickDeposit", quickDeposit);
            return this;
        }

        public Request getRequest() {
            return new Deposit(this).getRequest();
        }
    }
}
