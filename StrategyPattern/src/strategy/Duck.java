package strategy;

/**
 * Created by opu on 2/23/2016.
 */
public abstract class Duck {

    protected FlyBehavior fb;
    protected QuackBehavior qb;

    public abstract void display();

    public void swim() {
        System.out.print("I can swim \n");
    }

    public void performFly() {
        fb.fly();
    }

    public void performQuack() {
        qb.quack();
    }

    public void setFb(FlyBehavior fb) {
        this.fb = fb;
    }

    public void setQb(QuackBehavior qb) {
        this.qb = qb;
    }
}
