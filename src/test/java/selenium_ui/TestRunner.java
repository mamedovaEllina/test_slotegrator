package selenium_ui;

import com.codeborne.selenide.Configuration;
import io.cucumber.testng.*;
import org.testng.annotations.*;

import static selenium_ui.DriverHelper.getDriver;
import static selenium_ui.DriverHelper.killDriver;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"steps"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        getDriver();
        Configuration.timeout = 5000;
    }

    @AfterSuite(description = "Закрываю сессию web-driver'а", alwaysRun = true)
    public void afterMethod() {
        killDriver();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}