import java.util.Scanner;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {   
        Scanner scanner = new Scanner(System.in);

        System.out.println("-------------------- GAME: OTHELLO --------------------");
        System.out.println("Do you want to play first?");
        System.out.print("Press 1 for YES or 0 for NO: ");
        int human_choice = scanner.nextInt();

        System.out.println("Enter a maximum depth: ");
        int maxDepth = scanner.nextInt();
        
        int h_colour, colour;
        if (human_choice == 1) {
            h_colour = Board.black;
            colour = Board.white;
            System.out.println("You are playing with black");
        }
        else {
            h_colour = Board.white;
            colour = Board.black;
            System.out.println("You are playing with white");
        }
        
        // Create the players and the board
        Player playerAI = new Player(maxDepth, colour);
        Board board = new Board();
        
        // Create and show GUI
        GUI gui = new GUI(board, h_colour);
        SwingUtilities.invokeLater(() -> {
            gui.setVisible(true);
            gui.updateBoard();
        });
        
        int currentPlayer = Board.black;

        while (!board.isTerminal())
        {
            System.out.println();
            
            if (board.getChildren(currentPlayer).isEmpty()) {
                System.out.println("No valid moves available for current player. Skipping turn...");                
                
                if (board.getChildren(-currentPlayer).isEmpty()) {
                    System.out.println("Both players have no moves. Game over.");
                    break;
                }
                
                board.setLastPlayer(currentPlayer);
                currentPlayer = -currentPlayer;
                continue;
            }

            if (currentPlayer == h_colour) {
                System.out.println("Your turn");
                
                // Get move from GUI
                Move humanMove = gui.getHumanMove();
                int row = humanMove.getRow();
                int col = humanMove.getCol();
                
                System.out.println("You played: row:" + row + " col:" + col);
                board.makeMove(row, col, h_colour);

                try {
                    Thread.sleep(300);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            else {
                System.out.println("AI's turn");

                try {
                    Thread.sleep(300);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                              
                Move aiMove = playerAI.MiniMax(board, colour);
                board.makeMove(aiMove.getRow(), aiMove.getCol(), colour);
                System.out.println("AI played: row:" + aiMove.getRow() + " col:" + aiMove.getCol());

                try {
                    Thread.sleep(300);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Update GUI
            SwingUtilities.invokeLater(() -> {
                gui.updateBoard();
            });
            
            board.print();
            currentPlayer = -currentPlayer;
        }
        
        System.out.println("-------------------- GAME OVER --------------------");
        
        int finalScore = board.evaluate();
                
        String resultMessage;
        if (finalScore > 0) {
            System.out.println("Black wins!");
            resultMessage = "Black wins for: " + Math.abs(finalScore) + " points";
        } else if (finalScore < 0) {
            System.out.println("White wins!");
            resultMessage = "White wins for: " + Math.abs(finalScore) + " points";
        } else {
            System.out.println("It's a tie!");
            resultMessage = "It's a tie!\n";
        }
        
        SwingUtilities.invokeLater(() -> {
            gui.setStatusMessage("Game Over!");
            gui.showGameOver(resultMessage);
        });

        scanner.close();
    }
}

