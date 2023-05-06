/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of ghost moves
 */

package pacman.game;

import pacman.a_star.SquareGraph;

import java.awt.*;

public class GhostMoving {
    PathField from,to;
    GhostObject ghost;
    Game maze;
    Thread executeThread;
    boolean gameover = false;

    public GhostMoving(PathField to,GhostObject ghost,Game maze)
    {
        this.from=(PathField) ghost.getField();
        this.to=to;
        this.ghost=ghost;
        this.maze=maze;
    }

    public void start(){
        System.out.println("kokot");
        executeThread = new Thread(() -> {
            while (!gameover) {
                ObjectMove move = new ObjectMove(from, to, ghost, maze);
                move.execute();
                move = new ObjectMove(to, from, ghost, maze);
                move.execute();
            }

        });
        executeThread.start();
    }

    public void overgame (){
        this.gameover = true;
    }
}
