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

package com.trustly.api.data.request.requestdata;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.data.request.AttributeData;

public class RecipientInformation extends AttributeData {
    @SerializedName("Partytype")
    private String partyType;
    @SerializedName("Firstname")
    private String firstName;
    @SerializedName("Lastname")
    private String lastName;
    @SerializedName("CountryCode")
    private String countryCode;
    @SerializedName("CustomerID")
    private String customerId;
    @SerializedName("Address")
    private String address;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;

    public RecipientInformation(final String partyType, final String firstName, final String lastName, final String countryCode) {
        this.partyType = partyType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
    }

    public RecipientInformation setCustomerId(final String customerId) {
        this.customerId = customerId;
        return this;
    }

    public RecipientInformation setAddress(final String address) {
        this.address = address;
        return this;
    }

    public RecipientInformation setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
