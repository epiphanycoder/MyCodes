package strategy.flyinstances;

import strategy.FlyBehavior;

/**
 * Created by opu on 2/23/2016.
 */
public class FlyWithWings implements FlyBehavior {

    @Override public void fly() {
        System.out.println("I can fly with wings");
    }
}
