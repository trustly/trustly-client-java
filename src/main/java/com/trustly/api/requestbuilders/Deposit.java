/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Trustly Group AB
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
import com.trustly.api.data.request.requestdata.DepositData;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.security.SignatureHandler;
/**
* Creates a Deposit request ready to be sent to Trustly API.
* The constructor contains the required fields of a deposit request
*
* Builder let you add additional information if any is available for the given request.
*
* The api specifics of the request can be found on https://trustly.com/en/developer/
*
* Example use for a default deposit request:
* Request deposit = new Deposit.Build(notificationUrl, enduser, msgid, currency).getRequest();
*
*/
public class Deposit {
    private final Request request = new Request();

    private Deposit(Build builder) {

        RequestParameters params = new RequestParameters();
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
        private final Map<String, Object> attributes = new TreeMap<String, Object>();

        public Build(String notificationURL, String endUserID, String messageID, Currency currency) {
            data.setNotificationURL(notificationURL);
            data.setEndUserID(endUserID);
            data.setMessageID(messageID);
            this.attributes.put("Currency", currency);
            data.setAttributes(attributes);
        }

        public Build locale(String locale) {
            attributes.put("Locale", locale);
            return this;
        }

        public Build suggestedMinAmount(String suggestedMinAmount) {
            attributes.put("SuggestedMinAmount", suggestedMinAmount);
            return this;
        }

        public Build suggestedMaxAmount(String suggestedMaxAmount) {
            attributes.put("SuggestedMaxAmount", suggestedMaxAmount);
            return this;
        }

        public Build amount(String amount) {
            attributes.put("Amount", amount);
            return this;
        }

        public Build ip(String IP) {
            attributes.put("IP", IP);
            return this;
        }

        public Build successURL(String successURL) {
            attributes.put("SuccessURL", successURL);
            return this;
        }

        public Build failURL(String failURL) {
            attributes.put("FailURL", failURL);
            return this;
        }

        public Build templateURL(String templateURL) {
            attributes.put("TemplateURL", templateURL);
            return this;
        }

        public Build urlTarget(String urlTarget) {
            attributes.put("URLTarget", urlTarget);
            return this;
        }

        public Build mobilePhone(String mobilePhone) {
            attributes.put("MobilePhone", mobilePhone);
            return this;
        }

        public Build firstname(String firstname) {
            attributes.put("Firstname", firstname);
            return this;
        }

        public Build lastname(String lastname) {
            attributes.put("Lastname", lastname);
            return this;
        }

        public Build nationalIdentificationNumber(String nin) {
            attributes.put("NationalIdentificationNumber", nin);
            return this;
        }

        public Build shopperStatement(String shopperStatement) {
            attributes.put("ShopperStatement", shopperStatement);
            return this;
        }

        public Request getRequest() {
            return new Deposit(this).getRequest();
        }
    }
}
