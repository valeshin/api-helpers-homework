package helpers;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.runner.AbstractTestBehavior;

public abstract class Behavior extends AbstractTestBehavior {

    public TestContext context;

    public Behavior(TestContext context) {
        this.context = context;
    }
}
