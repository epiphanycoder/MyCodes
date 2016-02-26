package allcreatures;

import mainpackage.Creature;
import mainpackage.CreatureDecorator;

public class Human extends CreatureDecorator{

    public Human(Creature creature) {
        super(creature);
    }

    @Override public void speak() {
        creature.speak();
        System.out.println("hello world!!!, life is beautiful and hectic.");
    }
}