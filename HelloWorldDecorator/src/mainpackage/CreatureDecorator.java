package mainpackage;

/**
 * Created by opu on 2/26/2016.
 */
public class CreatureDecorator implements Creature {
    protected Creature creature;

    public CreatureDecorator(Creature creature) {
        this.creature = creature;
    }

    @Override public void speak() {
        creature.speak();
    }
}
