package com.trustly.api.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.engine.resolver.TraversableResolvers;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

public class HibernateDataAnnotationsValidator implements AnnotationsValidator {

  private static final List<ValidationResult> EMPTY_VALIDATION_RESULTS = Collections.unmodifiableList(new ArrayList<>());

  private Validator validator;

  @Override
  public boolean isAvailable() {

    try {
      Class.forName("org.hibernate.validator.HibernateValidator");
      return true;
    } catch (Throwable ignored) {
      return false;
    }
  }

  public <T> List<ValidationResult> validate(T obj) {

    if (this.validator == null) {
      this.validator = Validation.byDefaultProvider()
        .configure()
        .traversableResolver(TraversableResolvers.getDefault())
        .messageInterpolator(new ParameterMessageInterpolator())
        .buildValidatorFactory()
        .getValidator();
    }

    List<String> errorMessages = new ArrayList<>();
    for (ConstraintViolation<T> validation : this.validator.validate(obj)) {
      errorMessages.add(validation.getPropertyPath() + ": " + validation.getMessage());
    }

    if (!errorMessages.isEmpty()) {
      return errorMessages.stream().map(ValidationResult::new).collect(Collectors.toList());
    }

    return EMPTY_VALIDATION_RESULTS;
  }
}
