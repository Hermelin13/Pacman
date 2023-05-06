/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Interface for play field
 */

package common;


public interface Field extends Observable {

    void setMaze(Maze maze, int Keys);

    boolean put(MazeObject object);

    boolean putBack(MazeObject object);

    boolean remove(MazeObject object);

    boolean removeBack(MazeObject object);

    Field nextField(Direction dirs);

    boolean isEmpty();

    MazeObject get();

    boolean canMove();

    boolean contains(MazeObject var1);

    public static enum Direction {
        L(0, -1),
        U(-1, 0),
        R(0, 1),
        D(1, 0);

        private final int r;
        private final int c;

        private Direction(int dr, int dc) {
            this.r = dr;
            this.c = dc;
        }

        public int deltaRow() {
            return this.r;
        }

        public int deltaCol() {
            return this.c;
        }
    }

}
