import java.util.ArrayList;

public class Board
{
    public static final int black = 1;
    public static final int white = -1;
    public static final int EMPTY = 0;


    private int[][] gameBoard;

    private int lastPlayer;

    private Move lastMove;

    
    Board()
    {
        this.lastMove = new Move();
        this.lastPlayer = white;
        this.gameBoard = new int[8][8];
        for(int i = 0; i < this.gameBoard.length; i++)
        {
            for(int j = 0; j < this.gameBoard.length; j++)
            {
                this.gameBoard[i][j] = EMPTY;
            }
        }
        this.gameBoard[3][3]= -1 ;
        this.gameBoard[3][4]= 1 ;
        this.gameBoard[4][3]= 1;
        this.gameBoard[4][4]= -1;
    }


    Board(Board board)
    {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[8][8];
        for(int i = 0; i < this.gameBoard.length; i++)
        {
            for(int j = 0; j < this.gameBoard.length; j++)
            {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
    }


    //Checks whether a move is valid; whether a square is empty
    boolean isValidMove(int row, int col, int colour)
    {
        if((row > 7) || (col > 7) || (row < 0) || (col < 0))
            return false;
        if(this.gameBoard[row][col] != EMPTY)
            return false;

        
        int i , j , c; //i, j index variables, c how many pieces of the opposite colour found between the current move and the first piece of the same colour found

        //move up
        i = row - 1;
        j = col;
        c = 0;
        while(i > 0 && this.gameBoard[i][j] == (-colour)){
            i--;
            c++;
        }
        if(i>=0 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move down
        i = row + 1;
        j = col;
        c = 0;
        while(i<7 && this.gameBoard[i][j] == (-colour)){
            i++;
            c++;
        }
        if(i<=7 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move left
        i = row;
        j = col - 1;
        c = 0;
        while(j>0 && this.gameBoard[i][j] == (-colour)){
            j--;
            c++;
        }
        if(j>=0 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move right
        i = row;
        j = col + 1;
        c = 0;
        while(j<7 && this.gameBoard[i][j] == (-colour)){
            j++;
            c++;
        }
        if(j<=7 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move up left
        i = row - 1;
        j = col - 1;
        c = 0;
        while(i>0 && j>0 && this.gameBoard[i][j] == (-colour)){
            i--;
            j--;
            c++;
        }
        if(i>=0 && j>=0 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move up right
        i = row - 1;
        j = col + 1;
        c = 0;
        while(i>0 && j<7 && this.gameBoard[i][j] == (-colour)){
            i--;
            j++;
            c++;
        }
        if(i>=0 && j<=7 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move down left
        i = row + 1;
        j = col - 1;
        c = 0;
        while(i<7 && j>0 && this.gameBoard[i][j] == (-colour)){
            i++;
            j--;
            c++;
        }
        if(i<=7 && j>=0 && this.gameBoard[i][j] == colour && c>0)
            return true;

        //move down right
        i = row + 1;
        j = col + 1;
        c = 0;
        while(i<7 && j<7 && this.gameBoard[i][j] == (-colour)){
            i++;
            j++;
            c++;
        }
        if(i<=7 && j<=7 && this.gameBoard[i][j] == colour && c>0)
            return true;

        
        return false;
    }


    public ArrayList<Point> getReversePoints(int row, int col, int colour){

        ArrayList<Point> allReversePoints = new ArrayList<>(); //array list to store all the pieces that are going to reverse

        int i, j;

        //move up
        ArrayList<Point> upp = new ArrayList<>();
        i = row - 1;
        j = col;
        while(i>0 && this.gameBoard[i][j] == (-colour)){
            upp.add(new Point(i, j));
            i--;
        }
        if(i>=0 && this.gameBoard[i][j] == colour && upp.size()>0){
            allReversePoints.addAll(upp);
        }


        //move down
        ArrayList<Point> downp = new ArrayList<>();
        i = row + 1;
        j = col;
        while(i<7 && this.gameBoard[i][j] == (-colour)){
            downp.add(new Point(i, j));
            i++;
        }
        if(i<=7 && this.gameBoard[i][j] == colour && downp.size()>0){
            allReversePoints.addAll(downp);
        }

        //move left
        ArrayList<Point> leftp = new ArrayList<>();
        i = row;
        j = col - 1;
        while(j>0 && this.gameBoard[i][j] == (-colour)){
            leftp.add(new Point(i, j));
            j--;
        }
        if(j>=0 && this.gameBoard[i][j] == colour && leftp.size()>0){
            allReversePoints.addAll(leftp);
        }

        //move right
        ArrayList<Point> rightp = new ArrayList<>();
        i = row;
        j = col + 1;
        while(j<7 && this.gameBoard[i][j] == (-colour)){
            rightp.add(new Point(i, j));
            j++;
        }
        if(j<=7 && this.gameBoard[i][j] == colour && rightp.size()>0){
            allReversePoints.addAll(rightp);
        }

        //move up left
        ArrayList<Point> ulp = new ArrayList<>();
        i = row - 1;
        j = col - 1;
        while(i>0 && j>0 && this.gameBoard[i][j] == (-colour)){
            ulp.add(new Point(i, j));
            i--;
            j--;
        }
        if(i>=0 && j>=0 && this.gameBoard[i][j] == colour && ulp.size()>0){
            allReversePoints.addAll(ulp);
        }

        //move up right
        ArrayList<Point> urp = new ArrayList<>();
        i = row - 1;
        j = col + 1;
        while(i>0 && j<7 && this.gameBoard[i][j] == (-colour)){
            urp.add(new Point(i, j));
            i--;
            j++;
        }
        if(i>=0 && j<=7 && this.gameBoard[i][j] == colour && urp.size()>0){
            allReversePoints.addAll(urp);
        }

        //move down left
        ArrayList<Point> dlp = new ArrayList<>();
        i = row + 1;
        j = col - 1;
        while(i<7 && j>0 && this.gameBoard[i][j] == (-colour)){
            dlp.add(new Point(i, j));
            i++;
            j--;
        }
        if(i<=7 && j>=0 && this.gameBoard[i][j] == colour && dlp.size()>0){
            allReversePoints.addAll(dlp);
        }

        //move down right
        ArrayList<Point> drp = new ArrayList<>();
        i = row + 1;
        j = col + 1;
        while(i<7 && j<7 && this.gameBoard[i][j] == (-colour)){
            drp.add(new Point(i, j));
            i++;
            j++;
        }
        if(i<=7 && j<=7 && this.gameBoard[i][j] == colour && drp.size()>0){
            allReversePoints.addAll(drp);
        }

        return allReversePoints;
    }


    void makeMove(int row, int col, int colour)
    {
        //first call isValidmove
        if (!isValidMove(row, col, colour)){
            System.out.println("Invalid move");
            return ;
        }

        //then update the cell, lastmove, player
        this.gameBoard[row][col] = colour;
        this.lastMove = new Move(row, col);
        this.lastPlayer = colour;

       ArrayList<Point> pointsToFlip = this.getReversePoints(row, col, colour);

        for (Point p : pointsToFlip) {
            this.gameBoard[p.x][p.y] = colour;
        }
    }
            

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
    ArrayList<Board> getChildren(int colour)
    {
        ArrayList<Board> children = new ArrayList<>();
        for(int row = 0; row < this.gameBoard.length; row++)
        {
            for(int col = 0; col < this.gameBoard.length; col++)
            {
                if(this.isValidMove(row, col, colour))
                {
                    Board child = new Board(this);
                    child.makeMove(row, col, colour);
                    children.add(child);
                }
            }
        }
        return children;
    }


    int evaluate()
    {
        int score_black = 0;
        int score_white = 0;

        //checking rows
        for(int row = 0; row < this.gameBoard.length; row++){
        
            for(int col = 0; col < this.gameBoard.length; col++){
                if (this.gameBoard[row][col] == 1)
                    score_black += 1 ;
                else if ((this.gameBoard[row][col] == -1))
                    score_white+=1;

            }
        }
        return score_black - score_white ;
            
    }


    boolean isTerminal()
    {
        // check for empty
        for(int row = 0; row < this.gameBoard.length; row++)
        {
            for(int col = 0; col < this.gameBoard.length; col++)
            {
                if(this.gameBoard[row][col] == EMPTY) 
                    return false;
            }
        }
        // check for valid moves
        boolean black_move = getChildren(black).isEmpty();
        boolean white_move = getChildren(white).isEmpty();
        if (black_move && white_move)
            return true;


        return false;
    }


    void print()
    {
        System.out.println("*********");
        for(int row=0; row<8; row++)
        {
            System.out.print("* ");
            for(int col=0; col<8; col++)
            {
                switch (this.gameBoard[row][col]) {
                    case black -> System.out.print("b ");
                    case white -> System.out.print("w ");
                    case EMPTY -> System.out.print("- ");
                    default -> {
                    }
                }
            }
            System.out.println("*");
        }
        System.out.println("*********");
    }

    Move getLastMove()
    {
        return this.lastMove;
    }


    int getLastPlayer()
    {
        return this.lastPlayer;
    }


    int[][] getGameBoard()
    {
        return this.gameBoard;
    }


    void setGameBoard(int[][] gameBoard)
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }


    void setLastMove(Move lastMove)
    {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }


    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }
}
