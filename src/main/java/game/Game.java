/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation creation of maze
 */

package game;

import a_star.Node;
import a_star.SquareGraph;
import common.Field;
import common.Maze;
import Replay.MyFrmatter;
import common.MazeObject;


import java.awt.*;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Game implements Maze {

    private int numKeys;
    private int mainRows;
    private int mainCols;
    private Field[][] mainArrayF;
    private List<PathField> pathFields;

    private FileHandler fh;

    SquareGraph graph;
    Logger logger = Logger.getLogger("MyLog");

    public List<MazeObject> ghosts;

    public Game(Field[][] arrayF, int rows, int cols) {
        this.mainArrayF = arrayF;
        this.mainRows = rows;
        this.mainCols = cols;
        this.graph = new SquareGraph(rows + 2, cols + 2);
        this.ghosts = new ArrayList<>();
        this.numKeys = 0;
    }

    public PacmanObject pacman;
    public TargetObject Target;


    public Game(int rows, int cols) {
        this.mainRows = rows;
        this.mainCols = cols;
        this.mainArrayF = new Field[rows + 2][cols + 2];
        this.ghosts = new ArrayList<>();
        this.graph = new SquareGraph(rows + 2, cols + 2);
        this.numKeys = 0;
        this.pathFields = new ArrayList<>();
    }

    public Maze createGame(String[] maze, int rows, int cols) throws IOException {
        rows += 2;
        cols += 2;
        int countK = 0;


        this.graph = new SquareGraph(rows, cols);
        fh = new FileHandler("data/MyLogFile.log");
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
                        pathFields.add((PathField) arrayF[x][y]);
                        Node n = new Node(x, y, "NORMAL");
                        graph.setMapCell(new Point(x, y), n);
                        if (maze[x - 1].charAt(y - 1) == 'S') {
                            PacmanObject P = new PacmanObject(arrayF[x][y], logger);
                            logger.info("created " + P.hashCode() + " true " + x + " " + y);
                            pacman = P;
                            arrayF[x][y].put(P);
                        } else if (maze[x - 1].charAt(y - 1) == 'G') {
                            GhostObject G = new GhostObject(arrayF[x][y], logger);
                            logger.info("created " + G.hashCode() + " false " + x + " " + y);
                            arrayF[x][y].put(G);
                            this.ghosts.add(G);
                        } else if (maze[x - 1].charAt(y - 1) == 'K') {
                            KeyObject K = new KeyObject(arrayF[x][y]);
                            arrayF[x][y].put(K);
                            countK++;
                        } else if (maze[x - 1].charAt(y - 1) == 'T') {
                            TargetObject T = new TargetObject(arrayF[x][y]);
                            arrayF[x][y].put(T);
                            Target = T;
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

    public TargetObject target() {
        return this.Target;
    }

    public SquareGraph getGraph() {
        return this.graph;
    }


    public PathField getRandomPathField() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(pathFields.size()-1);
        return pathFields.get(rand_int1);
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
        return new ArrayList<>(this.ghosts);
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
