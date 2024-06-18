public class PizzaTestDrive {
    public static void main (String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Adarsh ordered a " + pizza.getName());

        pizza = chicagoStore.orderPizza("pepperoni");
        System.out.println("Aman ordered a " + pizza.getName());
    }
}