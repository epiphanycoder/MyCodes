package strategy.test;

import strategy.Duck;
import strategy.duckTypes.ModelDuck;
import strategy.flyinstances.FlyWithWings;
import strategy.quackinstances.Squack;

/**
 * Created by opu on 2/23/2016.
 */
public class Simulator {
    public static void main(String[] args) {

        Duck modelDuck = new ModelDuck();
        modelDuck.display();

        modelDuck.performFly();
        modelDuck.performQuack();

        System.out.println("::After behavior change::");
        modelDuck.display();
        modelDuck.setFb(new FlyWithWings());
        modelDuck.setQb(new Squack());

        modelDuck.performQuack();
        modelDuck.performFly();

    }
}
