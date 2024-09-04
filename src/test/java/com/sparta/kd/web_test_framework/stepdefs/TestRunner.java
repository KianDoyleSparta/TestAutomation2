package com.sparta.kd.web_test_framework.stepdefs;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/resources/features"},
        tags= "@happy",
        plugin={"pretty", "html:target/testReport.html", "json:target/jsonReport.json"},
        publish=true
)
public class TestRunner {

}

