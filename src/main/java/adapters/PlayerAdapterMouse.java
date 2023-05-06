/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of Mouse listener
 */

package adapters;

import a_star.Node;
import a_star.SquareGraph;
import common.Field;
import game.Game;
import game.PacmanObject;
import game.PathField;
import view.FieldView;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayerAdapterMouse extends MouseAdapter {
    private final PacmanObject pacman;
    private final Game maze;

    private static boolean lock=false;

    public PlayerAdapterMouse(PacmanObject pacman, Game maze) {
        this.pacman = pacman;
        this.maze = maze;
        this.lock = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(PlayerAdapterMouse.lock)
        {
            return;
        }
        new Thread(() -> {
            if(PlayerAdapterMouse.lock)
            {
                return;
            }
            PlayerAdapterMouse.lock=true;
            FieldView fv = (FieldView) e.getSource();
            PathField field = (PathField) fv.getField();
            int fieldCol = field.getMainCol();
            int fieldRow = field.getMainRow();
            int pacmanRow = ((PathField) pacman.getField()).getMainRow();
            int pacmanCol = ((PathField) pacman.getField()).getMainCol();


            SquareGraph graph;
            graph = new SquareGraph(maze.numRows(), maze.numCols());
            Point point;
            for (int rows=0;rows< maze.numRows();rows++) {
                for (int cols=0;cols<maze.numCols();cols++)
                {
                    point = new Point(rows,cols);
                    maze.getGraph().getMapCell(point);
                    Node n = new Node(rows, cols,maze.getGraph().getMapCell(point).isObstacle()? "OBSTACLE" : "NORMAL");
                    graph.setMapCell(point,n);
                }
            }
            Point target = new Point(fieldRow, fieldCol);
            graph.setTargetPosition(target);
            Point start = new Point(pacmanRow, pacmanCol);
            graph.setStartPosition(start);
            ArrayList<Node> nodes = graph.executeAStar();

            while (fieldCol != pacmanCol || fieldRow != pacmanRow) {
                try {
                    for (int i = 0; i < nodes.size(); i++) {

                        int row = nodes.get(i).getX();
                        int col = nodes.get(i).getY();
                        int moveRow = row - pacmanRow;
                        int moveCol = col - pacmanCol;


                        if (moveRow > 0) {
                            this.pacman.move(Field.Direction.D);
                        }
                        if (moveRow < 0) {
                            this.pacman.move(Field.Direction.U);
                        }
                        if (moveCol < 0) {
                            this.pacman.move(Field.Direction.L);
                        }
                        if (moveCol > 0) {
                            this.pacman.move(Field.Direction.R);
                        }

                        pacmanRow = ((PathField) this.pacman.getField()).getMainRow();
                        pacmanCol = ((PathField) this.pacman.getField()).getMainCol();
                        Thread.sleep(400);
                    }
                    graph.clean();
                    PlayerAdapterMouse.lock=false;
                }
                catch (Exception er) {
                    System.err.println(er);
                    graph.clean();
                    PlayerAdapterMouse.lock=false;
                }
            }
        }).start();

    }
}
