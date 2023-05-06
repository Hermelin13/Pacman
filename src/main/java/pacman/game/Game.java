/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation creation of maze
 */

package pacman.game;

import pacman.a_star.Node;
import pacman.a_star.SquareGraph;
import pacman.common.Field;
import pacman.common.Maze;
import pacman.Replay.MyFrmatter;
import pacman.common.MazeObject;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Game implements Maze {

    private int numKeys;
    private int mainRows;
    private int mainCols;
    private Field[][] mainArrayF;

    private FileHandler fh;

    SquareGraph graph;
    Logger logger = Logger.getLogger("MyLog");

    public Game(Field[][] arrayF, int rows, int cols) {
        this.mainArrayF = arrayF;
        this.mainRows = rows;
        this.mainCols = cols;
        this.graph = new SquareGraph(rows + 2, cols + 2);
        this.Ghosts = new ArrayList<>();
        this.numKeys = 0;
    }

    public List<MazeObject> Ghosts;

    private int fuck_keys;

    public PacmanObject pacman;


    public Game(int rows, int cols) {
        this.mainRows = rows;
        this.mainCols = cols;
        this.mainArrayF = new Field[rows + 2][cols + 2];
        this.Ghosts = new ArrayList<>();
        this.graph = new SquareGraph(rows + 2, cols + 2);
        this.numKeys = 0;
    }

    public Maze createGame(String[] maze, int rows, int cols) throws IOException {
        rows += 2;
        cols += 2;
        int countK = 0;


        this.graph = new SquareGraph(rows, cols);
        fh = new FileHandler("MyLogFile.log");
        fh.setFormatter(new MyFrmatter());
        try {
            // This block configure the logger with handler and formatter
            logger.addHandler(fh);
            // the following statement is used to log any messages
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        Field[][] arrayF = new Field[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (x == 0 || y == 0 || x == rows - 1 || y == cols - 1) {
                    arrayF[x][y] = new WallField(x, y);
                    Node n = new Node(x, y, "OBSTACLE");
                    graph.setMapCell(new Point(x, y), n);
                } else {
                    if (maze[x - 1].charAt(y - 1) == 'X') {
                        arrayF[x][y] = new WallField(x, y);
                        Node n = new Node(x, y, "OBSTACLE");
                        graph.setMapCell(new Point(x, y), n);
                    } else {
                        arrayF[x][y] = new PathField(x, y);
                        Node n = new Node(x, y, "NORMAL");
                        graph.setMapCell(new Point(x, y), n);
                        if (maze[x - 1].charAt(y - 1) == 'S') {
                            PacmanObject P = new PacmanObject(arrayF[x][y], logger);
                            System.out.println("in here");
                            logger.info("created " + P.hashCode() + " true " + x + " " + y);
                            pacman = P;
                            arrayF[x][y].put(P);
                        } else if (maze[x - 1].charAt(y - 1) == 'G') {
                            GhostObject G = new GhostObject(arrayF[x][y], logger);
                            logger.info("created " + G.hashCode() + " false " + x + " " + y);
                            arrayF[x][y].put(G);
                            this.Ghosts.add(G);
                        } else if (maze[x - 1].charAt(y - 1) == 'K') {
                            KeyObject K = new KeyObject(arrayF[x][y]);
                            arrayF[x][y].put(K);
                            countK++;
                        } else if (maze[x - 1].charAt(y - 1) == 'T') {
                            TargetObject T = new TargetObject(arrayF[x][y]);
                            arrayF[x][y].put(T);
                        }
                    }
                }
            }
        }

        this.numKeys = countK;
        Game GameMaze = new Game(arrayF, rows, cols);
        for (int x = 0; x < rows; x++)
            for (int y = 0; y < cols; y++)
                arrayF[x][y].setMaze(GameMaze, numKeys);

        this.mainArrayF = arrayF;
        this.mainRows = rows;
        this.mainCols = cols;

        return GameMaze;
    }

    public PacmanObject pacman() {
        return this.pacman;
    }

    public SquareGraph getGraph() {
        return this.graph;
    }


    public FileHandler getFh(){
        return fh;
    }
    @Override
    public int numRows() {
        return this.mainRows;
    }

    @Override
    public int numCols() {
        return this.mainCols;
    }

    @Override
    public List<MazeObject> ghosts() {
        return new ArrayList<>(this.Ghosts);
    }

    @Override
    public Field getField(int row, int col) {
        if (row >= this.numRows() || col >= this.numCols() || row < 0 || col < 0) {
            return null;
        }
        Field[][] f = mainArrayF;
        return (Field) f[row][col];
    }


}
