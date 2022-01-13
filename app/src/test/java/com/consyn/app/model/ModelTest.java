package com.consyn.app.model;

import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.rule.impl.TestClassMustBeProperlyNamedRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class ModelTest {
	
	private  static  final  String  POJO_PACKAGE ="com.consyn.app.model";
	
	public void testPojoStructureAndBehaviour()
	{
		 final Validator validator =
		            ValidatorBuilder.create()
		                    .with(new GetterMustExistRule())
		                    .with(new SetterMustExistRule())
		                    .with(new SetterTester())
		                    .with(new GetterTester())
		                    .with(new NoNestedClassRule())
		                    .with(new NoStaticExceptFinalRule())
		                    .with(new TestClassMustBeProperlyNamedRule())
		                    .with(new NoPublicFieldsExceptStaticFinalRule())
		                    .build();
		 validator.validate(POJO_PACKAGE, new FilterPackageInfo());
		
	}
}
