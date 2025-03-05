package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/user.feature",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:target/pet-tests-report.html"}
)
public class CucumberUserTests extends AbstractTestNGCucumberTests {
}
