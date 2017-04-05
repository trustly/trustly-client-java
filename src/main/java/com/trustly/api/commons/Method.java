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

package com.trustly.api.commons;

import com.google.gson.annotations.SerializedName;

public enum Method {
    @SerializedName("Deposit")
    DEPOSIT("Deposit"),

    @SerializedName("Withdraw")
    WITHDRAW("Withdraw"),

    @SerializedName("ApproveWithdrawal")
    APPROVE_WITHDRAWAL("ApproveWithdrawal"),

    @SerializedName("DenyWithdrawal")
    DENY_WITHDRAWAL("DenyWithdrawal"),

    @SerializedName("AccountLedger")
    ACCOUNT_LEDGER("AccountLedger"),

    @SerializedName("ViewAutomaticSettlementDetailsCSV")
    VIEW_AUTOMATIC_SETTLEMENT_DETAILS_CSV("ViewAutomaticSettlementDetailsCSV"),

    @SerializedName("Balance")
    BALANCE("Balance"),

    @SerializedName("GetWithdrawals")
    GET_WITHDRAWALS("GetWithdrawals"),

    @SerializedName("Refund")
    REFUND("Refund"),

    @SerializedName("credit")
    CREDIT("credit"),

    @SerializedName("debit")
    DEBIT("debit"),

    @SerializedName("pending")
    PENDING("pending"),

    @SerializedName("cancel")
    CANCEL("cancel"),

    @SerializedName("account")
    ACCOUNT("account");

    private final String jsonName;

    Method(final String s) {
        jsonName = s;
    }

    public boolean equalsName(final String otherName) {
        return otherName != null && jsonName.equals(otherName);
    }

    public String toString() {
        return jsonName;
    }
}
