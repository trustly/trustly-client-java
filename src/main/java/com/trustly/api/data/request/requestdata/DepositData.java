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

package com.trustly.api.data.request.requestdata;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.data.request.RequestData;

public class DepositData extends RequestData {
    @SerializedName("NotificationURL")
    private String notificationURL;
    @SerializedName("EndUserID")
    private String endUserID;
    @SerializedName("MessageID")
    private String messageID;

    public String getNotificationURL() {
        return notificationURL;
    }

    public void setNotificationURL(final String notificationURL) {
        this.notificationURL = notificationURL;
    }

    public String getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(final String endUserID) {
        this.endUserID = endUserID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(final String messageID) {
        this.messageID = messageID;
    }
}
