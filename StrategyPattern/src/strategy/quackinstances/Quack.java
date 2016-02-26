package strategy.quackinstances;

import strategy.QuackBehavior;

/**
 * Created by opu on 2/23/2016.
 */
public class Quack implements QuackBehavior {
    @Override public void quack() {
        System.out.println("Quack Quack Quack");
    }
}
