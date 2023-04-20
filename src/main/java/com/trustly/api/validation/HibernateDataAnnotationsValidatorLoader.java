package com.trustly.api.validation;

public class HibernateDataAnnotationsValidatorLoader implements AnnotationsValidatorLoader {

  @Override
  public AnnotationsValidator create() {

    try {
      Class.forName("org.hibernate.validator.HibernateValidator");
      return new HibernateDataAnnotationsValidator();
    } catch (Throwable ignored) {
      return null;
    }
  }
}
