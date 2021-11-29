package com.smallcase.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/main/resources/features/scenario2.feature",
		glue= {"com.smallcase.glue"}
		)
public final class ScenarioTwoRunner {

}
