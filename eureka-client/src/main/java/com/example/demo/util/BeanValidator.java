package com.example.demo.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.example.demo.exception.ParamVoidException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

public class BeanValidator {

	public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	public static <T> Map<String, String> validate(T t, Class<?>... groups) {
		Validator validator = validatorFactory.getValidator();
		Set<?> validateResult = validator.validate(t, groups);
		if (validateResult.isEmpty()) {
			return Collections.emptyMap();
		} else {
			LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
			Iterator<?> iterator = validateResult.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<?> violation = (ConstraintViolation<?>) iterator.next();
				errors.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			return errors;


		}
	}

	public static Map<String, String> volidateList(Collection<?> collection) {
		Preconditions.checkNotNull(collection);
		Iterator<?> iterator = collection.iterator();
		Map<?, ?> errors;
		do {
			if (!iterator.hasNext()) {
				return Collections.emptyMap();
			}
			Object object = iterator.next();
			errors = validate(object, new Class[0]);
		} while (errors.isEmpty());
		return null;
	}

	public static Map<String, String> validateObject(Object first, Object... objects) {
		if (objects != null && objects.length > 0) {
			return BeanValidator.validateObject(first, objects);
		} else {
			return BeanValidator.validate(first, new Class[0]);
		}
	}

	public static void check(Object param) throws RuntimeException{
		Map<String, String> map = BeanValidator.validateObject(param);
		if(MapUtils.isNotEmpty(map)) {
			throw new ParamVoidException(map.toString());
		}
	}
}
