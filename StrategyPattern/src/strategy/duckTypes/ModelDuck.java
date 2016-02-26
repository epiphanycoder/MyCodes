package strategy.duckTypes;

import strategy.Duck;
import strategy.flyinstances.NoFly;
import strategy.quackinstances.MuteQuack;

public class ModelDuck extends Duck {

    public ModelDuck() {
        fb = new NoFly();
        qb = new MuteQuack();
    }

    @Override public void display() {
        System.out.println("I'm a model duck");
    }
}
