package com.trustly.api.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Path.Node;
import jakarta.validation.TraversableResolver;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.lang.annotation.ElementType;
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
    } catch (Throwable e) {
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

//    private <T> List<ValidationResult> TryValidateObjectRecursive(T obj, Set<Object> validatedObjects) {
//        // We never validate null values, or the same value more than once.
//        if (obj == null || validatedObjects.contains(obj)) {
//            return EMPTY_VALIDATION_RESULTS;
//        }
//
//        validatedObjects.add(obj);
//        List<ValidationResult> list = new ArrayList<>();
//        var result = Validator.TryValidateObject(obj, new ValidationContext(obj, null), list, true);
//
//        foreach(var property in obj.GetType().GetProperties().Where(this.IsSupportedProperty))
//        {
//            if (property.PropertyType == typeof(string) || property.PropertyType.IsValueType) {
//                continue;
//            }
//
//            var value = property.GetValue(obj, null);
//            if (value is IEnumerable asEnumerable)
//            {
//                foreach(var v in asEnumerable)
//                {
//                    list.AddRange(this.WrapValidationResults(v, property, validatedObjects));
//                }
//            }
//                else
//            {
//                list.AddRange(this.WrapValidationResults(value, property, validatedObjects));
//            }
//        }
//
//        return list;
//    }

//    private boolean IsSupportedProperty(PropertyInfo prop) {
//        return prop.CanRead && prop.GetIndexParameters().Length == 0;
//    }

//    private List<ValidationResult> WrapValidationResults(object entry, PropertyInfo property, Set<Object> validatedObjects) {
//        return this.TryValidateObjectRecursive(entry, validatedObjects)
//                   .Select(vr = > new ValidationResult(vr.ErrorMessage, vr.MemberNames.Select(x = > property.Name + '.' + x)));
//    }
}
