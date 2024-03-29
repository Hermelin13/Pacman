/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of all objects
 */

package game;

import common.Field;
import common.MazeObject;


import java.util.logging.FileHandler;

public class BasicObject implements MazeObject {
    public Field mainF;

    public BasicObject(Field f) {
        this.mainF = f;
    }

    public BasicObject(Field f, FileHandler fh) {


        this.mainF = f;
    }

    public void setField(Field field) {
        this.mainF = field;
    }

    @Override
    public boolean canMove(Field.Direction direction) {
        return this.mainF.nextField(direction).canMove();
    }

    @Override
    public boolean move(Field.Direction direction) {
        return false;
    }

    @Override
    public boolean moveBack(Field.Direction direction) {
        return false;
    }

    @Override
    public Field getField() {
        return this.mainF;
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public int getKeys() {
        return 0;
    }

    @Override
    public boolean getTarget() {
        return false;
    }

}
