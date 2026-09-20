# Othello (Reversi) - Java & AI implementation

A complete implementation of the classic Othello (Reversi) board game in **Java**, featuring a Graphical User Interface (GUI) and an Artificial Intelligence (AI) opponent. 

## Features
* **Artificial Intelligence (AI):** The computer uses the **Minimax** algorithm with **Alpha-Beta Pruning** to optimize its decision-making process.
* **Dynamic Difficulty:** The player can select the maximum search depth for the AI at the start of the game, setting the difficulty level.
* **Color Selection:** The user can choose to play as Black (plays first) or White (plays second).
* **Graphical User Interface (GUI):** Through **Java Swing**, providing an interactive 8x8 game board.
* **Player Assists:** The GUI indicates valid moves for the human player using translucent circles and displays the current score in real-time.

## Code Structure
The project is organized into the following files:
* `Main.java`: The primary entry point of the program. It handles initial user configuration via the terminal and controls the main game loop.
* `GUI.java`: Manages the window, board, and pieces, processes mouse clicks, and updates the live score.
* `Board.java`: Maintains the game state, validates moves, handles piece flipping, and calculates the heuristic evaluation.
* `Player.java`: Contains the core AI logic, executing the `max` and `min` Minimax functions along with Alpha-Beta pruning.
* `Move.java` & `Point.java`: Helper classes to store board coordinates (row, col) and the evaluated value of individual moves.

## How to Run

To run the game on your local machine:

1. **Compile:** Open your terminal in the directory and compile all Java files:
   ```bash
   javac *.java

2. **Run:**
```
  javac Main.java
  java Main
```
