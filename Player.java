package scrap;

public class Player {
    private int health;
    private int hunger;
    private int hydr; // hydration
    private String name;
    private String[][] inv;

    public Player(int health, int hunger, int hydr, String name) {

        this.health = health;
        this.hunger = hunger;
        this.hydr = hydr;
        this.name = name;
        inv = new String[][] { { "", "", "" },
                { "", "", "" },
                { "", "", "" } };
    }

    public void consume(String item, int amt) {
        int found = 0;

        for (int i = 0; i < inv.length; i++) {
            for (int j = 0; j < inv[0].length; j++) {
                if (inv[i][j].equals(item) && found < amt) {
                    inv[i][j] = "";
                    if (item.equals("food can")) {
                        hunger += 20;
                        checkExtreme();
                        System.out.println("Hunger is now " + getHunger() + "%");
                    }
                    if (item.equals("water bottle")) {
                        hydr += 30;
                        checkExtreme();
                        System.out.println("Hydration is now " + getHydr() + "%");
                    }
                    found++;
                }
            }
        }

        System.out.println("You have consumed " + found + " " + item + "s.");
    }

    public int freeSpace() {
        int free = 0;

        for (int i = 0; i < inv.length; i++) {
            for (int j = 0; j < inv[0].length; j++) {
                if (inv[i][j].equals("")) {
                    free++;
                }
            }
        }

        return free;
    }

    public void equidItem(String item) {
        boolean found = false;

        for (int i = 0; i < inv.length; i++) {
            for (int j = 0; j < inv[0].length; j++) {
                if (inv[i][j].equals("") && !found) {
                    inv[i][j] = item;
                    found = true;
                }
            }
        }
        if (found) {
            System.out.println("Stored " + item);
        } else {
            System.out.println("Inventory full");
        }
    }

    public void checkExtreme() {
        if (hunger > 100) {
            hunger = 100;
        }
        if (hunger < 0) {
            hunger = 0;
        }
        if (hydr > 100) {
            hydr = 100;
        }
        if (hydr < 0) {
            hydr = 0;
        }
    }

    // SETTERS FOR INSTANCE
    public void setInv(String[][] arr) {
        inv = arr;
    }

    public void setHealth(int h) {
        health = h;
    }

    public void setHunger(int h) {
        hunger = h;
    }

    public void setHydr(int h) {
        hydr = h;
    }

    // GETTERS FOR INSTANCE
    public void getInv() {
        for (int i = 0; i < inv.length; i++) {
            for (int j = 0; j < inv[i].length; j++) {
                if (inv[i][j].length() > 0) {
                    System.out.print("| " + inv[i][j] + " |");
                } else {
                    System.out.print("| " + "           " + " |");
                }
            }
            System.out.println();
        }
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHydr() {
        return hydr;
    }

    public String getName() {
        return name;
    }

}
