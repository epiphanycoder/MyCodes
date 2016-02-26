package strategy.duckTypes;

import strategy.Duck;
import strategy.flyinstances.FlyWithWings;
import strategy.quackinstances.Squack;

/**
 * Created by opu on 2/23/2016.
 */
public class FlySquackDuck extends Duck {

    public FlySquackDuck() {
        fb = new FlyWithWings();
        qb = new Squack();
    }

    @Override public void display() {
        System.out.println("I am Right Duck");
    }
}
