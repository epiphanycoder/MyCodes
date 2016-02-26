package mainpackage;

import allcreatures.BasicCreature;
import allcreatures.Bird;
import allcreatures.Human;

/**
 * Created by opu on 2/26/2016.
 */
public class Main {
    public static void main(String[] args) {

        Creature creature = new Human(new Bird(new BasicCreature()));
        creature.speak();
    }
}
