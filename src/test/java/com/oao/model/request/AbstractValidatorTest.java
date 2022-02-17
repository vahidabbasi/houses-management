package com.oao.model.request;

import org.junit.Before;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class AbstractValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected <T extends Object> boolean isRequestValid(final T request) {
        final Set<ConstraintViolation<Object>> violations = validator.validate(request);
        return violations.isEmpty();
    }

}