import java.util.Scanner;

public class Game {
    private int days;
    private int percHunger;
    private Player p;
    private Scanner scan;
    private int exploreNum;
    private boolean died;

    public Game(Player p) {
        this.p = p;
        days = 0;
        percHunger = 30;
        scan = new Scanner(System.in);
        exploreNum = 1;
        died = false;
    }

    public int getDay() {
        return days;
    }

    public int healthCheck() {
        int hurt = 0;
        if ((p.getHunger() <= 0 && p.getHydr() <= 0)) {
            hurt = ((int) (Math.random() * 31) + 10);
            p.setHealth(p.getHealth() - hurt);
        } else if (p.getHunger() <= 0 || p.getHydr() <= 0) {
            hurt = ((int) (Math.random() * 21) + 5);
            p.setHealth(p.getHealth() - hurt);
        }
        if (p.getHealth() <= 0) {
            died = true;
        }
        return hurt;
    }

    public void dayPass() {
        days++;
        exploreNum = 0;
        int hurt = 0;
        int hungy = (int) (Math.random() * percHunger) + 5;
        int thisty = (int) (Math.random() * percHunger) + 5;
        p.setHunger(p.getHunger() - hungy);
        p.setHydr(p.getHydr() - thisty);
        p.checkExtreme();
        hurt = healthCheck();

        Display.clearScreen();
        if (hurt > 0) {
            System.out.println("You lost " + hurt + " HP due to your health");
        }
        System.out.println("Your hunger decreased by " + hungy + "%\nYour hydration decreased by " + thisty + "%");
        if (died) {
            end();
        }
    }

    public void explore() {
        if (exploreNum >= 1) {
            System.out.println("Your tired...");
            return;
        }
        int rewardF = (int) (Math.random() * 101);
        int rewardW = (int) (Math.random() * 101);
        int attacked = (int) (Math.random() * 101);
        // found counter
        int food = 0;
        int water = 0;

        if (rewardF >= 95) {
            food += 4;
        } else if (rewardF >= 90) {
            food += 3;
        } else if (rewardF >= 80) {
            food += 2;
        } else if (rewardF >= 50) {
            food += 1;
        } else {
            food += 0;
        }
        if (rewardW >= 99) {
            water += 4;
        } else if (rewardW >= 95) {
            water += 3;
        } else if (rewardW >= 90) {
            water += 2;
        } else if (rewardW >= 65) {
            water += 1;
        } else {
            water += 0;
        }

        System.out.println("You found " + food + " food cans and " + water + " water bottles!");
        int foodkeep = 0;
        int waterkeep = 0;

        if (p.freeSpace() <= 0) {
            System.out.println("NO MORE SPACE!");
        } else if (p.freeSpace() < food + water) {
            System.out.println("There isn't enough space to carry all that you found. You have " + p.freeSpace()
                    + " free spaces.");
            if (food > 0) {
                System.out.println("How much FOOD do you want to keep?");
                boolean valid = false;
                while (!valid) {
                    try {
                        foodkeep = Integer.parseInt(scan.nextLine());
                        valid = true;
                    } catch (NumberFormatException e) {
                        System.out.println("A number wasn't inputed, try again \nHow much FOOD do you want to keep?");
                    }

                }

            }
            if (water > 0) {
                System.out.println("How much WATER do you want to keep?");

                boolean valid = false;
                while (!valid) {
                    try {
                        waterkeep = Integer.parseInt(scan.nextLine());
                        valid = true;
                    } catch (NumberFormatException e) {
                        System.out.println("A number wasn't inputed, try again \nHow much WATER do you want to keep?");
                    }
                }
            }
            int com = foodkeep + waterkeep;
            if (foodkeep + waterkeep <= p.freeSpace()) {
                for (int i = 0; i < com; i++) {
                    if (foodkeep > 0) {
                        p.equidItem("food can");
                        foodkeep--;
                    }
                    if (waterkeep > 0) {
                        p.equidItem("water bottle");
                        waterkeep--;
                    }
                }
            } else {
                System.out.println("you did your math wrong so you just prioritize food");
                int loops = p.freeSpace();
                for (int i = 0; i < loops; i++) {
                    if (food > 0) {
                        p.equidItem("food can");
                        food--;
                    } else if (waterkeep > 0) {
                        p.equidItem("water bottle");
                        water--;
                    }
                }
            }
        } else {
            int loops = food + water;
            for (int i = 0; i < loops; i++) {
                if (food > 0) {
                    p.equidItem("food can");
                    food--;
                } else if (water > 0) {
                    p.equidItem("water bottle");
                    water--;
                }
            }
        }
        exploreNum++;

        // if you've been attacked
        if (attacked <= 5) {
            int hurt = (int) (Math.random() * 11) + 5;
            p.setHealth(p.getHealth() - hurt);
            System.out.println("You've been attacked while exploring.\nYou take " + hurt + " Damage");
        }
    }

    public void end() {
        p.checkExtreme();
        Display.clearScreen();
        System.out.println("YOU HAVE DIED");
        Display.endGame = true;
    }
}
