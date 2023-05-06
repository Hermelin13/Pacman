/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Interface for move in replay
 */

package pacman.Replay;

public interface UiAction {

    public int objectId = 0;
    void run();
    void undo();
}
