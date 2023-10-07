package org.dante.springboot.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.dante.springboot.prop.GenderVal;

public class GenderValConstraintValidator implements ConstraintValidator<GenderVal, Integer> {

	private Set<Integer> set = new HashSet<>();

	/**
	 * 初始化方法
	 */
	@Override
	public void initialize(GenderVal constraintAnnotation) {

		int[] vals = constraintAnnotation.vals();
		for (int val : vals) {
			set.add(val);
		}

	}

	/**
	 * 判断是否校验成功
	 *
	 * @param value 需要校验的值
	 * @param context
	 * @return
	 */
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return set.contains(value);
	}

}
