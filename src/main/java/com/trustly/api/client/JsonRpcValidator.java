package com.trustly.api.client;

import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.validation.AnnotationsValidator;
import com.trustly.api.validation.AnnotationsValidatorLoader;
import com.trustly.api.validation.HibernateDataAnnotationsValidatorLoader;
import com.trustly.api.validation.ValidationResult;
import java.util.List;

public class JsonRpcValidator {

  private static final AnnotationsValidatorLoader[] ANNOTATIONS_VALIDATORS = new AnnotationsValidatorLoader[]{
    new HibernateDataAnnotationsValidatorLoader()
  };

  private final AnnotationsValidator validator;

  public JsonRpcValidator() {

    AnnotationsValidator foundValidator = null;
    for (AnnotationsValidatorLoader loader : ANNOTATIONS_VALIDATORS) {

      foundValidator = loader.create();
      if (foundValidator != null) {
        break;
      }
    }

    this.validator = foundValidator;
  }

  public void validate(Object jsonRpcRequest) throws TrustlyValidationException {

    if (this.validator == null) {

      // There was no validator on the classpath, so we will not run any validation on the request bean.
      return;
    }

    List<ValidationResult> results = this.validator.validate(jsonRpcRequest);

    if (!results.isEmpty()) {

      String[] messages = results.stream().map(ValidationResult::getErrorMessage).toArray(String[]::new);
      throw new TrustlyValidationException(String.join(", ", messages));
    }
  }
}
