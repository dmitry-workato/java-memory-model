package org.bufistov;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@State
@Outcome.Outcomes({
        @Outcome(id = "0, 0", expect = ACCEPTABLE, desc = "None are initialized"),
        @Outcome(id = "1, 0", expect = ACCEPTABLE_INTERESTING, desc = "Re-ordered!!"),
        @Outcome(id = "0, 1", expect = ACCEPTABLE, desc = "Y goes after X"),
        @Outcome(id = "1, 1", expect = ACCEPTABLE, desc = "Both are initialized")
})
public class MemoryModelTest {
    /*volatile*/ int x, y;

    @Actor
    void actor() {
        /*synchronized (this)*/ {
            x = 1;
        }
        /*synchronized (this)*/ {
            y = 1;
        }
    }

    @Actor
    void observer(II_Result r) {
        r.r1 = y;
        r.r2 = x;
    }
}
