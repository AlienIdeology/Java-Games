import java.util.Scanner;

public class TextClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TicTacToe game = new TicTacToe();
        
        int player = 1;
        System.out.println("The game of TicTacToe begins now.");
        
        do {
            
            System.out.println("Player #" + player
                + ", please type the coordinate of the cell");
            
            String line = scan.nextLine();
            String first = line.substring(0, 1);
            int y = Integer.valueOf(first);
            String second = line.substring(2);
            int x = Integer.valueOf(second);

            if (y > 2 || y < 0 || x > 2 || x < 0) {
                System.out.println("Index out of bounds!");
                continue;
            }
            
            if (game.getCell(y,x).isFilled()) {
                System.out.println("The cell at " + y + " " + x + " is already filled!");
                continue;
            }
            
            game.fillCell(y, x);
            
            System.out.println(game);
            
            if (game.isFinished()) {
                System.out.println("Player #" + player + " is the winner!");
                break;
            }
            
            if (game.isFilled()) {
                System.out.println("It's a tie!");
                break;
            }
            
            if (player == 1) player = 2;
            else player = 1;
        } while(true);
    }
}
