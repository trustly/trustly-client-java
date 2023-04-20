package com.trustly.api.validation;

import java.util.List;

public interface AnnotationsValidator {

  <T> List<ValidationResult> validate(T obj);
}
