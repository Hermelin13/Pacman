/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of ghost moves
 */

package game;

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
        executeThread = new Thread(() -> {
            while (!gameover) {
                from=(PathField) ghost.getField();
                ObjectMove move = new ObjectMove(from, maze.getRandomPathField(), ghost, maze);
                move.execute();
            }
        });
        executeThread.start();
    }

    public void overgame (){
        this.gameover = true;
    }
}
