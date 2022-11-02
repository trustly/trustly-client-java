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

import com.trustly.api.commons.Currency;
import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.ChargeData;
import com.trustly.api.security.SignatureHandler;
import java.util.Map;
import java.util.TreeMap;

/**
 * Creates a Charge request ready to be sent to Trustly API.
 * The constructor contains the required fields of a Charge request.
 *
 * The API specifics of the request can be found on https://trustly.com/en/developer/
 *
 * Example use for a default Charge request:
 * Request charge = new Charge.Build(notificationURL, endUserID, messageID, amount, currency, shopperStatement, email).getRequest();
 */
public class Charge {
    private final Request request = new Request();

    private Charge(final Build builder) {
        final RequestParameters params = new RequestParameters();
        params.setUUID(SignatureHandler.generateNewUUID());
        params.setData(builder.data);

        request.setMethod(Method.CHARGE);
        request.setParams(params);
    }

    public Request getRequest() {
        return request;
    }

    public static class Build {
        private final ChargeData data = new ChargeData();
        private final Map<String, Object> attributes = new TreeMap<>();

        public Build(
            final String accountID,
            final String notificationURL,
            final String endUserID,
            final String messageID,
            final String amount,
            final Currency currency,
            final String shopperStatement,
            final String email
        ) {
            data.setAccountID(accountID);
            data.setNotificationURL(notificationURL);
            data.setEndUserID(endUserID);
            data.setMessageID(messageID);
            data.setAmount(amount);
            data.setCurrency(currency);

            attributes.put("ShopperStatement", shopperStatement);
            attributes.put("Email", email);
            data.setAttributes(attributes);
        }

        public Build externalReference(final String externalReference) {
            attributes.put("ExternalReference", externalReference);
            return this;
        }

        public Build paymentDate(final String paymentDate) {
            attributes.put("PaymentDate", paymentDate);
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
            return new Charge(this).getRequest();
        }
    }
}
