package com.trustly.api.domain.methods.settlementreport;

import com.trustly.api.domain.methods.settlementreport.SettlementReportResponseDataEntry.SettlementReportResponseDataEntryBuilder;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SettlementReportParser {

  @FunctionalInterface
  public interface Mapper {

    void map(SettlementReportResponseDataEntryBuilder row, String value);
  }

  private static final Mapper NOOP_MAPPER = (row, value) -> {
  };

  private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[]{

    new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(DateTimeFormatter.ISO_LOCAL_DATE)
      .appendLiteral(' ')
      .append(DateTimeFormatter.ISO_LOCAL_TIME)
      .appendOffsetId()
      .toFormatter(Locale.ROOT),

    DateTimeFormatter.ISO_DATE_TIME,
    DateTimeFormatter.ISO_INSTANT
  };

  private final Map<String, Mapper> mappers = new HashMap<>();

  public SettlementReportParser() {
    this.mappers.put("accountname", SettlementReportResponseDataEntryBuilder::accountName);
    this.mappers.put("currency", SettlementReportResponseDataEntryBuilder::currency);
    this.mappers.put("messageid", SettlementReportResponseDataEntryBuilder::messageId);
    this.mappers.put("orderid", SettlementReportResponseDataEntryBuilder::orderId);
    this.mappers.put("ordertype", SettlementReportResponseDataEntryBuilder::orderType);
    this.mappers.put("username", SettlementReportResponseDataEntryBuilder::username);
    this.mappers.put("fxpaymentcurrency", SettlementReportResponseDataEntryBuilder::fxPaymentCurrency);
    this.mappers.put("settlementbankwithdrawalid", SettlementReportResponseDataEntryBuilder::settlementBankWithdrawalId);
    this.mappers.put("externalreference", SettlementReportResponseDataEntryBuilder::externalReference);

    this.mappers.put("amount", (row, value) -> row.amount(Double.parseDouble(value)));
    this.mappers.put("fxpaymentamount", (row, value) -> row.fxPaymentAmount(Double.parseDouble(value)));
    this.mappers.put("total", (row, value) -> row.total(Double.parseDouble(value)));

    this.mappers.put("datestamp", (row, value) -> {

      List<DateTimeParseException> exceptions = new ArrayList<>();
      for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
        try {
          row.datestamp(formatter.parse(value, Instant::from));
          return;
        } catch (DateTimeParseException ex) {
          exceptions.add(ex);
        }
      }

      throw exceptions.stream().findFirst().orElseThrow(() -> new IllegalStateException("Unknown date format exception"));
    });
  }

  public List<SettlementReportResponseDataEntry> parse(String csv) {
    String[] lines = csv.replace("\r", "").trim().split("\n");
    List<SettlementReportResponseDataEntry> rows = new ArrayList<>();

    if (lines.length == 0) {
      return rows;
    }

    String[] headers = lines[0].split(",");

    List<Mapper> localMappers = new ArrayList<>();
    for (String header : headers) {
      String lowerCaseHeaderKey = header.toLowerCase(Locale.ROOT);
      if (this.mappers.containsKey(lowerCaseHeaderKey)) {
        localMappers.add(this.mappers.get(lowerCaseHeaderKey));
      } else {
        // We do not recognize this new header key.
        // This could count as an error, but we will let it go.
        // The preferred way would perhaps be to log about the lost data,
        // but we do not want to include a dependency on a logging library.
        localMappers.add(NOOP_MAPPER);
      }
    }

    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      if (line == null || line.isEmpty()) {
        continue;
      }

      String[] fieldsValues = this.getFieldValues(line);

      SettlementReportResponseDataEntryBuilder rowBuilder = SettlementReportResponseDataEntry.builder();
      for (int columnIndex = 0; columnIndex < fieldsValues.length; columnIndex++) {
        if (fieldsValues[columnIndex] != null && !fieldsValues[columnIndex].isEmpty()) {
          localMappers.get(columnIndex).map(rowBuilder, fieldsValues[columnIndex]);
        }
      }

      rows.add(rowBuilder.build());
    }

    return rows;
  }

  private String[] getFieldValues(String line) {
    List<String> tokens = new ArrayList<>();

    StringBuilder buffer = new StringBuilder();
    boolean insideQuote = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (insideQuote) {
        if (c == '"') {
          insideQuote = false;
        } else {
          buffer.append(c);
        }
      } else {
        if (c == '"') {
          insideQuote = true;
        } else if (c == ',') {
          tokens.add(buffer.toString());
          buffer.setLength(0);
        } else {
          buffer.append(c);
        }
      }
    }

    if (buffer.length() > 0) {
      tokens.add(buffer.toString());
      buffer.setLength(0);
    }

    return tokens.toArray(new String[0]);
  }
}
