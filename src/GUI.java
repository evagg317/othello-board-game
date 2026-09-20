import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private static final int CELL_SIZE = 80;
    private static final int BOARD_SIZE = 8;
    private static final Color BOARD_COLOR = new Color(34, 139, 34);
    private static final Color GRID_COLOR = new Color(0, 100, 0);
    
    private Board board;
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private int h_color;
    private boolean waitingForHumanMove;
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    public GUI(Board board, int h_color) {
        this.board = board;
        this.h_color = h_color;
        this.waitingForHumanMove = false;
        
        setTitle("Othello Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create UI components
        createTopPanel();
        createBoardPanel();
        createBottomPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        statusLabel = new JLabel("Game Started", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        topPanel.add(statusLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }
    
    private void createBoardPanel() {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        
        boardPanel.setPreferredSize(new Dimension(
            CELL_SIZE * BOARD_SIZE + 1, 
            CELL_SIZE * BOARD_SIZE + 1
        ));
        boardPanel.setBackground(BOARD_COLOR);
        
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (waitingForHumanMove) {
                    handleBoardClick(e.getX(), e.getY());
                }
            }
        });
        
        add(boardPanel, BorderLayout.CENTER);
    }
    
    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        bottomPanel.add(scoreLabel);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw grid
        g2d.setColor(GRID_COLOR);
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
            g2d.drawLine(0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE, i * CELL_SIZE);
        }
        
        // Draw pieces and valid moves
        int[][] gameBoard = board.getGameBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                
                if (gameBoard[row][col] == Board.black) {
                    drawPiece(g2d, x, y, Color.BLACK);
                } else if (gameBoard[row][col] == Board.white) {
                    drawPiece(g2d, x, y, Color.WHITE);
                } else if (waitingForHumanMove && 
                           board.isValidMove(row, col, h_color)) {
                    // Show valid moves for human player
                    drawValidMoveIndicator(g2d, x, y);
                }
            }
        }
    }
    
    private void drawPiece(Graphics2D g2d, int x, int y, Color color) {
        int margin = 8;
        g2d.setColor(color);
        g2d.fillOval(x + margin, y + margin, 
                     CELL_SIZE - 2 * margin, CELL_SIZE - 2 * margin);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + margin, y + margin, 
                     CELL_SIZE - 2 * margin, CELL_SIZE - 2 * margin);
    }
    
    private void drawValidMoveIndicator(Graphics2D g2d, int x, int y) {
        int size = 10;
        int offset = (CELL_SIZE - size) / 2;
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillOval(x + offset, y + offset, size, size);
    }
    
    private void handleBoardClick(int x, int y) {
        int col = x / CELL_SIZE;
        int row = y / CELL_SIZE;
        
        if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            if (board.isValidMove(row, col, h_color)) {
                selectedRow = row;
                selectedCol = col;
                waitingForHumanMove = false;
                synchronized(this) {
                    this.notifyAll(); // Notify Main that a move has been selected
                }
            }
        }
    }
    
    // Methods for Main to call
    public void updateBoard() {
        boardPanel.repaint();
        updateScore();
    }
    
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }
    
    public void updateScore() {
        int blackCount = 0;
        int whiteCount = 0;
        int[][] gameBoard = board.getGameBoard();
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (gameBoard[row][col] == Board.black) {
                    blackCount++;
                } else if (gameBoard[row][col] == Board.white) {
                    whiteCount++;
                }
            }
        }
        
        scoreLabel.setText("Black: " + blackCount + "  |  White: " + whiteCount);
    }
    
    // Wait for human to click and return the move
    public Move getHumanMove() {
        waitingForHumanMove = true;
        selectedRow = -1;
        selectedCol = -1;
        boardPanel.repaint();
        
        // Wait until a valid move is clicked
        synchronized(this) {
            while (selectedRow == -1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return new Move(selectedRow, selectedCol);
    }
    
    public void showGameOver(String message) {
        JOptionPane.showMessageDialog(this, message, 
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}