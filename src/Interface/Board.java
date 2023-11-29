package Interface;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Board extends JPanel {

    private static int fromMoveSquare = -1, toMoveSquare = -1;
    private final int tileSize = 80;
    private final int boardSize = 8;
    private final GameLogic gl;
    private final String fen;
    private Piece[] squares;
    private int selectedX = -1, selectedY = -1;
    private boolean isSelected = false;
    private ArrayList<Integer> possibleMoves;
    private boolean gameOver = false;
    private boolean whiteWon = false;
    private boolean isStaleMate = false;
    private Piece selectedPiece = null;

    public Board(String startFen, GameLogic gl) {
        this.gl = gl;
        this.fen = startFen;
        setBackground(new Color(251, 211, 151));
        Dimension dim = new Dimension(boardSize * tileSize, boardSize * tileSize);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        squares = new Piece[boardSize * boardSize];
        possibleMoves = new ArrayList<>();
        readFromFen(startFen);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameOver) {
                    int prevSelectedX = selectedX;
                    int prevSelectedY = selectedY;

                    selectedX = e.getX() / tileSize;
                    selectedY = e.getY() / tileSize;

                    if (isSelected) {
                        if (squares[selectedX + selectedY * 8] == null) {
                            onClick(prevSelectedX, prevSelectedY);
                            isSelected = false;
                        } else {
                            if (squares[selectedX + selectedY * 8].isWhite() != selectedPiece.isWhite()) {
                                onClick(prevSelectedX, prevSelectedY);

                                isSelected = false;
                            } else {
                                if (selectedX == prevSelectedX && selectedY == prevSelectedY) {
                                    isSelected = false;
                                } else {
                                    selectedPiece = squares[selectedX + selectedY * 8];
                                    possibleMoves = selectedPiece.getMoves(squares, selectedX + selectedY * 8);
                                }
                            }
                        }
                    } else {
                        if (squares[selectedX + selectedY * 8] != null && squares[selectedX + selectedY * 8].isWhite() == gl.whiteToMove()) {
                            isSelected = true;
                            selectedPiece = squares[selectedX + selectedY * 8];
                            possibleMoves = selectedPiece.getMoves(squares, selectedX + selectedY * 8);
                        }
                    }
                }
                repaint();
            }
        });
    }

    public static void setFromMoveSquare(int fromMoveSquare) {
        Board.fromMoveSquare = fromMoveSquare;
    }

    public static void setToMoveSquare(int toMoveSquare) {
        Board.toMoveSquare = toMoveSquare;
    }

    public Piece[] getSquares() {
        return squares;
    }

    public boolean gameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean whiteWon() {
        return this.whiteWon;
    }

    public void setWhiteWon(boolean whiteWon) {
        this.whiteWon = whiteWon;
    }

    public boolean isStaleMate() {
        return this.isStaleMate;
    }

    public void restartGame() {
        squares = new Piece[boardSize * boardSize];
        possibleMoves = new ArrayList<>();
        readFromFen(fen);
        this.selectedX = -1;
        this.selectedY = -1;
        this.gameOver = false;
        fromMoveSquare = -1;
        toMoveSquare = -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setFont(g.getFont().deriveFont(15f));
        for (int j = 0; j < boardSize; j++) {
            for (int i = 0; i < boardSize; i++) {
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    gr.setColor(new Color(176, 110, 65));
                    gr.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }
            }
        }
        if (isSelected) {
            gr.setColor(new Color(82, 82, 82, 255));
            gr.fillRect(selectedX * tileSize, selectedY * tileSize, tileSize, tileSize);
        }

        for (int i = 0; i < boardSize; i++) {
            if (i % 2 == 0) {
                gr.setColor(new Color(176, 110, 65));
            } else {
                gr.setColor(new Color(253, 212, 152));
            }
            gr.drawString(String.valueOf(8 - i), 5, tileSize * i + 20);

            if (i % 2 == 0) {
                gr.setColor(new Color(253, 212, 152));
            } else {
                gr.setColor(new Color(176, 110, 65));
            }
            gr.drawString(String.valueOf((char) (97 + i)), tileSize * i + 70, 8 * tileSize - 5);
        }

        if (isSelected) {
            gr.setColor(new Color(128, 128, 255, 153));
            for (Integer possibleMove : possibleMoves) {
                if (gl.testMove(squares, selectedX + selectedY * 8, possibleMove)) {
                    gr.fillRect(possibleMove % 8 * tileSize, possibleMove / 8 * tileSize, tileSize + 1, tileSize);
                }

            }
        }

        gr.setColor(new Color(153, 255, 0, 100));
        gr.fillRect(fromMoveSquare % 8 * tileSize, fromMoveSquare / 8 * tileSize, tileSize + 1, tileSize);
        gr.fillRect(toMoveSquare % 8 * tileSize, toMoveSquare / 8 * tileSize, tileSize + 1, tileSize);

        for (int i = 0; i < boardSize * boardSize; i++) {
            if (squares[i] != null) {
                gr.drawImage(squares[i].getImg(), i % boardSize * tileSize, i / boardSize * tileSize, tileSize, tileSize, this);
            }
        }
    }

    private void onClick(int prevSelectedX, int prevSelectedY) {
        int from = prevSelectedX + prevSelectedY * 8;
        int to = selectedX + selectedY * 8;
        if (possibleMoves.contains(to) && gl.testMove(squares, from, to)) {
            gl.makeMove(squares, from, to);
            gl.makeTurn();
            fromMoveSquare = from;
            toMoveSquare = to;
            if (gl.checkMate(squares, gl.whiteToMove())) {
                gameOver = true;
                whiteWon = !gl.whiteToMove();
            } else if (gl.isStaleMate(squares, gl.whiteToMove())) {
                gameOver = true;
                isStaleMate = true;
            }
            if (gl.isBlackAI() && !gl.whiteToMove()) {
                repaint();
                SwingUtilities.invokeLater(() -> {
                    gl.makeAiMove(squares);
                    repaint();
                    if (gl.checkMate(squares, true)) {
                        gameOver = true;
                        whiteWon = false;
                    } else if (gl.isStaleMate(squares, true)) {
                        gameOver = true;
                        isStaleMate = true;
                    }
                });

            }
        }

    }

    public void readFromFen(String startFen) {
        int file = 0;
        int rank = 7;
        for (int i = 0; i < startFen.length(); i++) {
            char c = startFen.charAt(i);
            if (c == '/') {
                file = 0;
                rank--;
            } else if (Character.isDigit(c)) {
                file += Character.getNumericValue(c);
            } else {
                boolean uppercase = !Character.isUpperCase(c);
                switch (c) {
                    case 'r', 'R' -> squares[rank * 8 + file] = new Rook(uppercase);
                    case 'n', 'N' -> squares[rank * 8 + file] = new Knight(uppercase);
                    case 'b', 'B' -> squares[rank * 8 + file] = new Bishop(uppercase);
                    case 'q', 'Q' -> squares[rank * 8 + file] = new Queen(uppercase);
                    case 'k', 'K' -> squares[rank * 8 + file] = new King(uppercase);
                    case 'p', 'P' -> squares[rank * 8 + file] = new Pawn(uppercase);
                }
                file++;
            }
        }
    }
}
 