package scrap;

import java.util.Scanner;

public class Display {
    private static Player p;
    private static Game game;
    public static boolean endGame;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("| DAY " + game.getDay() + " |     Type cmd for commands");
        command("stats");
        System.out.println("------------------------------------------");
    }

    public static void intro(Scanner scan) {
        clearScreen();
        System.out.println("SURVIVE THE APOCALYPSE");
        System.out.println("Type 'play'");
        String input = scan.nextLine().toLowerCase();
        if (!input.equals("play")) {
            System.out.println(input);
            intro(scan);
        } else {
            startGame(scan);
        }

    }

    public static void startGame(Scanner scan) {
        clearScreen();
        System.out
                .println("You have been alerted about zombies, you go into your basement.");
        while (endGame == false) {
            String inp = scan.nextLine();
            if (inp.equals("quit")) {
                endGame = true;
            }

            command(inp);
            if (game.getDay() > 20) {
                endGame = true;
            }
        }
        if (game.getDay() > 20) {
            clearScreen();
            System.out.println("You Won! You survived 20 days!");
            command("stats");
        }
    }

    public static void command(String input) {
        String inp = input.toLowerCase();

        if (inp.equals("cmd")) {
            System.out.println(
                    "sleep - Pass the day \n" +
                            "eat [number] / drink [number] - obviously\n" +
                            "explore - find consumables in the wasteland \n" +
                            "stats - statistics on your health\n" +
                            "inv / inventory - view your stored items\n" +
                            "clear - clear the screen\n" +
                            "quit - exit the game");
        } else if (inp.length() >= 4 && inp.substring(0, 3).equals("eat")) {
            int num = 0;
            try {
                num = Integer.parseInt(inp.substring(4));
            } catch (NumberFormatException e) {
                System.out.println("A number wasn't inputed, use: \neat [number]");
            }
            p.consume("food can", num);
        } else if (inp.length() >= 6 && inp.substring(0, 5).equals("drink")) {
            int num = 0;
            try {
                num = Integer.parseInt(inp.substring(6));
            } catch (NumberFormatException e) {
                System.out.println("A number wasn't inputed, use: \ndrink [number]");
            }
            p.consume("water bottle", num);
        } else if (inp.equals("sleep")) {
            game.dayPass();
        } else if (inp.equals("explore")) {
            game.explore();
        } else if (inp.equals("stats")) {
            System.out.println("---------- STATS ----------");
            System.out.println("SURVIVOR : " + p.getName());
            System.out.println("Health : " + p.getHealth());
            System.out.println("Hunger : " + p.getHunger() + "%");
            System.out.println("Hydration : " + p.getHydr() + "%");
        } else if (inp.equals("inv") || inp.equals("inventory")) {
            p.getInv();
        } else if (inp.equals("clear")) {
            clearScreen();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String named;
        endGame = false;

        System.out.println("What's your name?");
        named = scan.nextLine();
        p = new Player(100, 100, 100, named);
        game = new Game(p);
        intro(scan); // play introduction by passing the scanner object

    }
}
