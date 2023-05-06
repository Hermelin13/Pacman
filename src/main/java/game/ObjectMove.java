/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of objects move
 */

package game;

import a_star.Node;
import a_star.SquareGraph;
import common.Field;

import java.awt.*;
import java.util.ArrayList;

public class ObjectMove {

    private Point start;
    private Point target;
    private ArrayList<Node> nodes;
    private GhostObject ghost;
    private SquareGraph graph;
    private int ghostRow;
    private int ghostCol;
    private boolean ismoving = false;

    public ObjectMove(PathField start,PathField target,GhostObject ghost,Game maze)
    {
        this.ghost=ghost;
        this.start=new Point(start.getMainRow(), start.getMainCol());
        this.target=new Point(target.getMainRow(),target.getMainCol());
        graph = maze.getGraph();
        graph.setTargetPosition(this.target);
        graph.setStartPosition(this.start);
        nodes= graph.executeAStar();
        this.ghostCol=((PathField)ghost.getField()).getMainCol();
        this.ghostRow=((PathField)ghost.getField()).getMainRow();
    }

    public void execute(){
        ismoving = true;
        int moveRow=-1;
        int moveCol=-1;
            try {

                for(int i=0;i< nodes.size();i++)
                {
                    System.out.println(nodes.get(i));

                    int row = nodes.get(i).getX();
                    int col = nodes.get(i).getY();
                    moveRow = row - ghostRow;
                    moveCol = col - ghostCol;

                    System.out.println(nodes);
                    System.out.println(row);
                    System.out.println(ghostRow);
                    System.out.println(col);
                    System.out.println(ghostCol);


                    if(moveRow>0)
                    {
                        this.ghost.move(Field.Direction.D);
                    }
                    if(moveRow<0)
                    {
                        this.ghost.move(Field.Direction.U);
                    }
                    if(moveCol<0)
                    {
                        this.ghost.move(Field.Direction.L);
                    }
                    if(moveCol>0)
                    {
                        this.ghost.move(Field.Direction.R);
                    }

                    ghostRow = ((PathField) this.ghost.getField()).getMainRow();
                    ghostCol = ((PathField) this.ghost.getField()).getMainCol();
                    Thread.sleep(350);
                }
                graph.clean();
                ismoving = false;
            }
            catch (Exception er) {
                System.err.println(er);
                graph.clean();
                ismoving = false;
            }

    }

    public boolean isIsmoving (){
        return this.ismoving;
    }

    public void setIsmoving (boolean moving){
        this.ismoving = moving;
    }
}

