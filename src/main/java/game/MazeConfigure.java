/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of check validity and creates maze
 */

package game;

import common.Maze;

import java.io.IOException;

public class MazeConfigure {
    private int configRows;
    private int configCols;
    private boolean inputValid;
    private boolean Read;
    private int inputLines;
    private String[] Map;
    boolean pacman;
    boolean finish;
    boolean bubak;

    public MazeConfigure() {
        inputValid = true;
        Read = false;
        inputLines = 0;
        pacman = false;
        finish = false;
        bubak = false;
    }

    public void startReading(int rows, int cols) {
        this.configRows = rows;
        this.configCols = cols;
        this.Read = true;
        this.Map = new String[rows];

    }

    public boolean processLine(String line) {
        if (Read) {
            if (line.length() == this.configCols) {
                char c;
                for (int i = 0; i < this.configCols; i++) {
                    c = line.charAt(i);
                    if (c != '.' && c != 'S' && c != 'X' && c != 'G' && c != 'K' && c != 'T') {
                        this.inputValid = false;
                        return false;
                    }
                    if (c == 'S') {
                        pacman = true;
                    }
                    if (c == 'T') {
                        finish = true;
                    }
                    if (c == 'G') {
                        bubak = true;
                    }
                }

                Map[inputLines++] = line;
                return true;
            } else {
                this.inputValid = false;
                return false;
            }
        } else {
            this.inputValid = false;
            return false;
        }
    }

    public boolean stopReading() {
        if (!pacman || !finish || !bubak) {
            this.inputValid = false;
            return false;
        }
        this.Read = false;
        return true;
    }

    public Maze createMaze() {
        Game X = new Game(this.configRows, this.configCols);
        try {
            X.createGame(this.Map, this.configRows, this.configCols);
        } catch (IOException e) {
        }
        return (Maze) X;
    }

    public boolean isInputValid() {
        return this.inputValid;
    }

}