/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of ghosts
 */

package game;

import common.Field;

import java.util.logging.Logger;

public class GhostObject extends BasicObject {

    Logger logger;

    public GhostObject(Field f, Logger logger) {
        super(f);
        this.logger = logger;
    }

    @Override
    public boolean canMove(Field.Direction direction) {
        return true;
    }

    public boolean move(Field.Direction direction) {
        try {
            logger.info(this.hashCode() + " " + isPacman() + " " + direction);
            Field tmp = (Field) this.mainF.nextField(direction);
            tmp.put(this);
            this.mainF.remove(this);
            setField(tmp);

        } catch (UnsupportedOperationException exception) {
            System.err.println((exception.getMessage()));
            return false;
        }
        return true;
    }

    @Override
    public boolean moveBack(Field.Direction direction) {
        PathField tmp = (PathField) this.mainF.nextField(direction);
        tmp.putBack(this);
        this.mainF.removeBack(this);
        return true;
    }

    public boolean isGhost () {
        return true;
    }
}
