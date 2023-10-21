package machine;


import java.util.Scanner;

public class CoffeeMachine {

    private static int money = 550;
    private static int water = 400;
    private static int milk = 540;
    private static int coffeeBeans = 120;
    private static int disposableCups = 9;
    private static void espresso(){
        if(water >= 250 && coffeeBeans >=16 && disposableCups >=1) {
            System.out.println("I have enough resources, making you a coffee!");
            water -= 250;
            coffeeBeans -= 16;
            money += 4;
            disposableCups -= 1;
        } else {
            if (water < 250) System.out.println("Sorry, not enough water!");
            if (coffeeBeans < 16) System.out.println("Sorry, not enough coffeeBeans!");
            if(disposableCups < 1) System.out.println("Sorry, not enough disposableCups!");
        }
    }
   private static void latte(){
       if(water >= 350 && coffeeBeans >=20 && milk>=75 && disposableCups >=1) {
           System.out.println("I have enough resources, making you a coffee!");
           water -= 350;
           coffeeBeans -= 20;
           milk -=75;
           money += 7;
           disposableCups -= 1;
       } else {
           if (water < 250) System.out.println("Sorry, not enough water!");
           if (coffeeBeans < 16) System.out.println("Sorry, not enough coffeeBeans!");
           if(disposableCups < 1) System.out.println("Sorry, not enough disposableCups!");
           if(milk < 75) System.out.println("Sorry, not enough milk!");
       }
    }
    private static void cappuccino(){
        if((water >= 200) && (coffeeBeans >= 12) && (milk >= 100) && (disposableCups >= 1)) {
            System.out.println("I have enough resources, making you a coffee!");
            water -= 200;
            coffeeBeans -= 12;
            money += 6;
            disposableCups -= 1;
            milk -=100;
        } else {
            if (water < 250) System.out.println("Sorry, not enough water!");
            if (coffeeBeans < 16) System.out.println("Sorry, not enough coffeeBeans!");
            if(disposableCups < 1) System.out.println("Sorry, not enough disposableCups!");
            if(milk < 100) System.out.println("Sorry, not enough milk!");
        }
    }
    private static void buy(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        Scanner scan = new Scanner(System.in);
        String type = scan.nextLine();
        switch(type){
            case "1":{
                espresso();
                break;
            }
            case "2":{
                latte();
                break;
            }
            case "3": {
                cappuccino();
                break;
            }
            default:
                break;
        }

    }
    private static void fill(){
        Scanner scanner = new Scanner(System.in);
        int addedWater;
        int addedMilk;
        int addedCoffeeBeans;
        int addedDisposableCups;
        System.out.println("Write how many ml of water you want to add: ");
        addedWater = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        addedMilk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        addedCoffeeBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add: ");
        addedDisposableCups = scanner.nextInt();
        water+=addedWater;
        milk+=addedMilk;
        coffeeBeans+=addedCoffeeBeans;
        disposableCups+=addedDisposableCups;
        scanner.close();
    }
    private static void take(){
        System.out.println("I gave you $" + money);
        money = 0;
    }
    private static void printState(){
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }
    public static void main(String[] args) {

        String menu = " ";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            menu = scanner.nextLine();
            switch (menu) {
                case "buy": {
                    buy();
                    break;
                }

                case "fill": {
                    fill();
                    break;
                }
                case "take": {
                    take();
                    break;
                }
                case "remaining":{
                    printState();
                    break;
                }
                default:
                   break;
            }

        }while (!"exit".equals(menu));
scanner.close();
    }


}
