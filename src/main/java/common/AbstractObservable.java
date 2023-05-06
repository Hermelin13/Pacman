/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Abstract class for observable objects
 */

package common;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservable implements Field {
    private final Set<Observer> observers = new HashSet();

    public AbstractObservable() {
    }

    public void addObserver(Observable.Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observable.Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }
}