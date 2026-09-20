import java.util.ArrayList;
import java.util.Random;

public class Player
{
    private int maxDepth;
    private int playercolour;

    Player(int maxDepth, int playercolour)
    {
        this.maxDepth = maxDepth;
        this.playercolour = playercolour;
    }

    Move MiniMax(Board board, int colour)
    {   
        if(this.playercolour == Board.black)
        {
            //If the black plays then it wants to maximize the heuristics value
            return max(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            //If the white plays then it wants to minimize the heuristics value
            return min(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    // The max and min functions are called one after another until a max depth is reached
    // We create a tree using backtracking DFS.
    Move max(Board board, int depth, int alpha, int beta)
    {
        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if(board.isTerminal() || (depth == this.maxDepth))
        {
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }

        //The children-moves of the state are calculated
        ArrayList<Board> children = board.getChildren(Board.black);
        
        if(children.isEmpty())
        {
            return min(board, depth + 1, alpha, beta);
        }

        Move maxMove = new Move(Integer.MIN_VALUE); // put max node initially to smallest value.
        for(Board child:children )
        {
            //And for each child, min is called, on a lower depth
            Move move = min(child, depth + 1, alpha, beta);

            //The child-move with the greatest value is selected and returned by max
            if(move.getValue() > maxMove.getValue())
            {
                maxMove.setRow(child.getLastMove().getRow());
                maxMove.setCol(child.getLastMove().getCol());
                maxMove.setValue(move.getValue());
            }

            //If the heuristic has the same value then we randomly choose one of the two moves
            else if(move.getValue() == maxMove.getValue())
            {
                if(r.nextInt(2) == 0)
                {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }

            alpha = Math.max(alpha, maxMove.getValue());
            if (beta <= alpha)
                break; // Alpha-beta pruning
        }
        return maxMove;
    }


    //Min works similarly to max
    Move min(Board board, int depth ,int alpha, int beta)
    {
        Random r = new Random();

        if(board.isTerminal() || (depth == this.maxDepth))
        {
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }

        ArrayList<Board> children = board.getChildren(Board.white);
        
        if(children.isEmpty())
        {
            return max(board, depth + 1, alpha, beta);
        }

        Move minMove = new Move(Integer.MAX_VALUE);
        
        for(Board child: children)
        {
            Move move = max(child, depth + 1, alpha , beta);
            if(move.getValue() < minMove.getValue())
            {
                minMove.setRow(child.getLastMove().getRow());
                minMove.setCol(child.getLastMove().getCol());
                minMove.setValue(move.getValue());
            }
            else if(move.getValue() == minMove.getValue())
            {
                
                if(r.nextInt(2) == 0)
                {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }

            }

            beta=Math.min(beta, minMove.getValue());
            if (beta<=alpha)
                break;
        }
        return minMove;
    }

}
