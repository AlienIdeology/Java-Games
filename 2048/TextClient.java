import java.util.Scanner;

public class TextClient {

    public static void main(String[] args) {
        G2048 game = new G2048(4, 128);
        Scanner scanner = new Scanner(System.in);
        System.out.println("The game of 2048 begins now!");

        while (!game.isLost()) {
            if (game.isWon()) {
                System.out.println("You won!");
                System.exit(0);
            }
            String line = scanner.nextLine();

            switch (line) {
                case "u":
                    game.up();
                    break;
                case "d":
                    game.down();
                    break;
                case "l":
                    game.left();
                    break;
                case "r":
                    game.right();
                    break;
            }
            System.out.println(game);
        }
        System.out.println("You lost!");
    }

}
