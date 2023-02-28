package com.trustly.api.validation;

import java.util.List;

public interface AnnotationsValidator {

  boolean isAvailable();

  <T> List<ValidationResult> validate(T obj);
}
