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

import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.SelectAccountData;
import com.trustly.api.security.SignatureHandler;
import java.util.Map;
import java.util.TreeMap;

/**
 * Creates a SelectAccount request ready to be sent to Trustly API.
 * The constructor contains the required fields of a SelectAccount request.
 *
 * Builder lets you add additional information if any is available for the given request.
 *
 * The API specifics of the request can be found on https://trustly.com/en/developer/
 *
 * Example use for a default SelectAccount request:
 * Request selectAccount = new SelectAccount.Build(notificationURL, endUserID, messageID).getRequest();
 */
public class SelectAccount {
    private Request request = new Request();

    private SelectAccount(final Build builder) {
        final RequestParameters params = new RequestParameters();
        params.setUUID(SignatureHandler.generateNewUUID());
        params.setData(builder.data);

        request.setMethod(Method.SELECT_ACCOUNT);
        request.setParams(params);
    }

    private Request getRequest() {
        return request;
    }

    public static class Build {
        private final SelectAccountData data = new SelectAccountData();
        private final Map<String, Object> attributes = new TreeMap<>();

        public Build(final String notificationURL, final String endUserID, final String messageID) {
            data.setNotificationURL(notificationURL);
            data.setEndUserID(endUserID);
            data.setMessageID(messageID);

            data.setAttributes(attributes);
        }

        public Build locale(final String locale) {
            attributes.put("Locale", locale);
            return this;
        }

        public Build country(final String country) {
            attributes.put("Country", country);
            return this;
        }

        public Build ip(final String ip) {
            attributes.put("IP", ip);
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

        public Build URLTarget(final String URLTarget) {
            attributes.put("URLTarget", URLTarget);
            return this;
        }

        public Build mobilePhone(final String mobilePhone) {
            attributes.put("MobilePhone", mobilePhone);
            return this;
        }

        public Build firstName(final String firstName) {
            attributes.put("Firstname", firstName);
            return this;
        }

        public Build lastName(final String lastName) {
            attributes.put("Lastname", lastName);
            return this;
        }

        public Build nationalIdentificationNumber(final String nationalIdentificationNumber) {
            attributes.put("NationalIdentificationNumber", nationalIdentificationNumber);
            return this;
        }

        public Build unchangeableNationalIdentificationNumber(final String unchangeableNationalIdentificationNumber) {
            attributes.put("UnchangeableNationalIdentificationNumber", unchangeableNationalIdentificationNumber);
            return this;
        }

        public Build dateOfBirth(final String dateOfBirth) {
            attributes.put("DateOfBirth", dateOfBirth);
            return this;
        }

        public Build email(final String email) {
            attributes.put("Email", email);
            return this;
        }

        public Build requestDirectDebitMandate(final String requestDirectDebitMandate) {
            attributes.put("RequestDirectDebitMandate", requestDirectDebitMandate);
            return this;
        }

        public Build shopperStatement(final String shopperStatement) {
            attributes.put("ShopperStatement", shopperStatement);
            return this;
        }

        public Build urlScheme(final String urlScheme) {
            attributes.put("URLScheme", urlScheme);
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

        public Request getRequest() {
            return new SelectAccount(this).getRequest();
        }
    }
}
