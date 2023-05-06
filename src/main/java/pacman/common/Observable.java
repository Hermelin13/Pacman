/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Interface for observable objects
 */

package pacman.common;

public interface Observable {
    void addObserver(Observer var1);

    void removeObserver(Observer var1);

    void notifyObservers();

    public interface Observer {
        void update(Observable var1);
    }
}