public class MiniDuckSimulator {
    public static void main (String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();
        mallard.setQuackBehavior(new Squeak());
        mallard.setFlyBehavior(new FlyNoWay());
        mallard.performFly();
        mallard.performQuack();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
        model.display();
    }
}