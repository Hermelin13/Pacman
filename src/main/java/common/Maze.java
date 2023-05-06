/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Interface for attributes of Maze
 */

package common;


import java.util.List;

public interface Maze {

    Field getField(int row, int col);

    List<MazeObject> ghosts();

    int numRows();

    int numCols();
}
