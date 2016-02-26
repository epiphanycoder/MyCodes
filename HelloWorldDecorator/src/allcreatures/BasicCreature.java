package allcreatures;

import mainpackage.Creature;
import mainpackage.CreatureDecorator;

/**
 * Created by opu on 2/26/2016.
 */
public class BasicCreature implements Creature {

    @Override public void speak() {
        System.out.print("All are creature.");
    }
}
