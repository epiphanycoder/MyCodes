package allcreatures;

import mainpackage.Creature;
import mainpackage.CreatureDecorator;

/**
 * Created by opu on 2/26/2016.
 */
public class Bird extends CreatureDecorator {

    public Bird(Creature creature) {
        super(creature);
    }

    @Override public void speak() {
        creature.speak();
        System.out.println("KO KO KO");
    }
}
