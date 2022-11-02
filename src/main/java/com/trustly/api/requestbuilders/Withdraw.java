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

import com.google.gson.JsonObject;
import java.util.Map;
import java.util.TreeMap;

import com.trustly.api.commons.Currency;
import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.WithdrawData;
import com.trustly.api.security.SignatureHandler;

/**
 * Creates a Withdraw request ready to be sent to Trustly API.
 * The constructor contains the required fields of a Withdraw request.
 *
 * Builder lets you add additional information if any is available for the given request.
 *
 * The API specifics of the request can be found on https://trustly.com/en/developer/
 *
 * Example use for a default Withdraw request:
 * Request withdraw = new Withdraw.Build(notificationUrl, enduser, msgid, currency, firstName, lastName, email, dateOfBirth).getRequest();
 */
public class Withdraw {
    private final Request request = new Request();

    private Withdraw(final Build builder) {
        final RequestParameters params = new RequestParameters();
        params.setUUID(SignatureHandler.generateNewUUID());
        params.setData(builder.data);

        request.setMethod(Method.WITHDRAW);
        request.setParams(params);
    }

    public Request getRequest() {
        return request;
    }

    public static class Build {
        private final WithdrawData data = new WithdrawData();
        private final Map<String, Object> attributes = new TreeMap<>();

        public Build(
            final String notificationURL,
            final String endUserID,
            final String messageID,
            final Currency currency,
            final String firstName,
            final String lastName,
            final String email,
            final String dateOfBirth
        ) {
            data.setNotificationURL(notificationURL);
            data.setEndUserID(endUserID);
            data.setMessageID(messageID);
            data.setCurrency(currency);

            attributes.put("Firstname", firstName);
            attributes.put("Lastname", lastName);
            attributes.put("Email", email);
            attributes.put("DateOfBirth", dateOfBirth);
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

        public Build clearingHouse(final String clearingHouse) {
            attributes.put("ClearingHouse", clearingHouse);
            return this;
        }

        public Build bankNumber(final String bankNumber) {
            attributes.put("BankNumber", bankNumber);
            return this;
        }

        public Build accountNumber(final String accountNumber) {
            attributes.put("AccountNumber", accountNumber);
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

        public Build addressCountry(final String addressCountry) {
            attributes.put("AddressCountry", addressCountry);
            return this;
        }

        public Build addressPostalCode(final String addressPostalCode) {
            attributes.put("AddressPostalcode", addressPostalCode);
            return this;
        }

        public Build addressCity(final String addressCity) {
            attributes.put("AddressCity", addressCity);
            return this;
        }

        public Build addressLine1(final String addressLine1) {
            attributes.put("AddressLine1", addressLine1);
            return this;
        }

        public Build addressLine2(final String addressLine2) {
            attributes.put("AddressLine2", addressLine2);
            return this;
        }

        public Build address(final String address) {
            attributes.put("Address", address);
            return this;
        }

        public Build suggestedAmount(final String suggestedAmount) {
            attributes.put("SuggestedAmount", suggestedAmount);
            return this;
        }

        public Build shopperStatement(final String shopperStatement) {
            attributes.put("ShopperStatement", shopperStatement);
            return this;
        }

        public Build unchangeableNationalIdentificationNumber(final short unchangeableNationalIdentificationNumber) {
            attributes.put("UnchangeableNationalIdentificationNumber", unchangeableNationalIdentificationNumber);
            return this;
        }

        public Build urlScheme(final String urlScheme) {
            attributes.put("URLScheme", urlScheme);
            return this;
        }

        public Build externalReference(final String externalReference) {
            attributes.put("ExternalReference", externalReference);
            return this;
        }

        public Build pspMerchant(final String pspMerchant) {
            attributes.put("PSPMerchant", pspMerchant);
            return this;
        }

        public Build pspMerchantURL(final String pspMerchantURL) {
            attributes.put("PSPMerchantURL", pspMerchantURL);
            return this;
        }

        public Build merchantCategoryCode(final String merchantCategoryCode) {
            attributes.put("MerchantCategoryCode", merchantCategoryCode);
            return this;
        }

        public Build senderInformation(final JsonObject senderInformation) {
            attributes.put("SenderInformation", senderInformation);
            return this;
        }

        public Request getRequest() {
            return new Withdraw(this).getRequest();
        }
    }
}
