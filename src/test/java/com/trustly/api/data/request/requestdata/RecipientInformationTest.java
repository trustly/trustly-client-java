package com.trustly.api.data.request.requestdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecipientInformationTest {

    private RecipientInformation recipientInformation;

    @Before
    public void setUp() {
        recipientInformation = new RecipientInformation("PERSON", "Tester", "McTester", "TE");
    }

    @Test
    public void toStringPrintsMandatoryFieldsInCorrectOrder() {
        final String toStringResult = recipientInformation.toString();
        final String expectedResult = "CountryCodeTEFirstnameTesterLastnameMcTesterPartytypePERSON";

        Assert.assertEquals(expectedResult, toStringResult);
    }

    @Test
    public void toStringPrintsMandatoryAndOptionalFieldsInCorrectOrder() {
        recipientInformation.setAddress("TesterStreet 14, 12345, TesterCity")
                            .setCustomerId("123456789")
                            .setDateOfBirth("1987-07-27");

        final String toStringResult = recipientInformation.toString();
        final String expectedResult = "AddressTesterStreet 14, 12345, TesterCityCountryCodeTECustomerID123456789DateOfBirth1987-07-27FirstnameTesterLastnameMcTesterPartytypePERSON";

        Assert.assertEquals(expectedResult, toStringResult);
    }

    @Test
    public void toStringPrintsMandatoryFieldsWhenNull() {
        recipientInformation = new RecipientInformation(null, null, null, null);

        final String toStringResult = recipientInformation.toString();
        final String expectedResult = "CountryCodenullFirstnamenullLastnamenullPartytypenull";

        Assert.assertEquals(expectedResult, toStringResult);
    }
}
