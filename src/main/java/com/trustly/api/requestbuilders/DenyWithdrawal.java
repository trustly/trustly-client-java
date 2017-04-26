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

import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.DenyWithdrawalData;
import com.trustly.api.security.SignatureHandler;

/**
 * Creates a DenyWithdrawal request ready to be sent to Trustly API.
 * The constructor contains the required fields of a DenyWithdrawal request
 *
 * Builder let you add additional information if any is available for the given request.
 *
 * The API specifics of the request can be found on https://trustly.com/en/developer/
 *
 * Example use for a default DenyWithdrawal request:
 * Request denyWithdrawal = new DenyWithdrawal.Build(orderID).getRequest();
 */
public class DenyWithdrawal {
    private final Request request = new Request();

    private DenyWithdrawal(final Build builder) {
        final RequestParameters params = new RequestParameters();
        params.setUUID(SignatureHandler.generateNewUUID());
        params.setData(builder.data);

        request.setMethod(Method.DENY_WITHDRAWAL);
        request.setParams(params);
    }

    public Request getRequest() {
        return request;
    }

    public static class Build {
        private final DenyWithdrawalData data = new DenyWithdrawalData();

        public Build(final String orderID) {
            data.setOrderID(orderID);
        }

        public Request getRequest() {
            return new DenyWithdrawal(this).getRequest();
        }
    }
}
