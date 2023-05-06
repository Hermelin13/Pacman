/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00), Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of path
 */

package pacman.game;

import pacman.common.Field;
import pacman.common.Maze;
import pacman.common.MazeObject;
import pacman.common.AbstractObservable;

import java.util.ArrayList;
import java.util.List;

public class PathField extends AbstractObservable implements Field {
    private final int mainRow;
    private final int mainCol;
    private MazeObject obj;
    private Maze mapMaze;
    public List<MazeObject> pole;
    private KeyObject key;

    private int numK;

    public PathField(int row, int col) {
        this.mapMaze = null;
        this.mainRow = row;
        this.mainCol = col;
        this.obj = null;
        this.key = null;
        this.pole = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PathField && ((PathField) obj).mainCol == this.mainCol && ((PathField) obj).mainRow == this.mainRow;
    }

    public void setMaze(Maze maze, int Keys) {
        this.mapMaze = maze;
        this.numK = Keys;
    }

    @Override
    public Field nextField(Direction dirs) {
        if (!this.canMove()) {
            throw new UnsupportedOperationException("Cannot find next field from this field\n");
        }
        if (this.mapMaze == null) {
            return null;
        }
        return switch (dirs) {
            case D -> this.mapMaze.getField(this.mainRow + 1, this.mainCol);
            case L -> this.mapMaze.getField(this.mainRow, this.mainCol - 1);
            case R -> this.mapMaze.getField(this.mainRow, this.mainCol + 1);
            case U -> this.mapMaze.getField(this.mainRow - 1, this.mainCol);
        };
    }


    public boolean put(MazeObject object) {
        if (!this.canMove()) {
            throw new UnsupportedOperationException("Cannot put object on this type of field\n");
        }
        if (object.isPacman()) {
            if (this.get() instanceof BasicObject basic) {
                if (basic.isTarget()) {
                    if (object.getKeys() == numK) {
                        ((PacmanObject) object).gotTarget();
                    }
                }
                if (basic.isKey()) {
                    ((PacmanObject) object).gotKey();
                    remove(basic);
                }
                if (basic.isGhost()) {
                    ((PacmanObject) object).hit();
                }
            }
        } else {
            if (this.get() instanceof PacmanObject pacman) {
                pacman.hit();
            }
            if(object.isKey())
            {
                System.out.println("key");
                this.key=(KeyObject) object;
            }
        }
        pole.removeIf(o -> o.getClass().equals(object.getClass()));
        this.pole.add(object);
        notifyObservers();
        return true;
    }

    public boolean putBack(MazeObject object){
        if (!this.canMove()) {
            throw new UnsupportedOperationException("Cannot put object on this type of field\n");
        }

        pole.removeIf(o -> o.getClass().equals(object.getClass()));
        this.pole.add(object);
        notifyObservers();
        return true;
    }


    public boolean remove(MazeObject object) {
        if (!this.canMove()) {
            throw new UnsupportedOperationException("Cannot remove object from this type of field\n");
        }

        if (this.contains(object)) {
            pole.remove(object);
            notifyObservers();
            return true;
        }
        if (object != this.obj) {
            notifyObservers();
            return false;
        }
        notifyObservers();
        return true;
    }

    @Override
    public boolean removeBack(MazeObject object) {
        if (this.contains(object)) {
            if(object.isPacman())
            {
                pole.remove(object);

                if(!this.pole.isEmpty())
                {
                 MazeObject temp =this.get();
                 if(temp.isGhost())
                 {
                     ((PacmanObject) object).noHit();
                 }
                }
                if(this.key!=null) {
                    ((PacmanObject) object).notGotKey();
                    pole.add(this.key);
                }
            } else if (object.isGhost()) {
                pole.remove(object);
                if(!this.pole.isEmpty())
                {
                    MazeObject temp =this.get();
                    if(temp.isPacman())
                    {
                        ((PacmanObject)temp).noHit();
                    }
                }

            }

            pole.remove(object);
            notifyObservers();
            return true;
        }
        if (object != this.obj) {
            notifyObservers();
            return false;
        }

        notifyObservers();
        return true;
    }


    public int getMainRow() {
        return mainRow;
    }

    public int getMainCol() {
        return mainCol;
    }

    @Override
    public boolean isEmpty() {
        return pole.isEmpty();
    }

    @Override
    public MazeObject get() {
        if (!pole.isEmpty()) {
            for (MazeObject i : pole) {
                if (i.isPacman()) {

                    return i;
                }
            }
            return pole.get(0);
        }
        return null;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean contains(MazeObject MazeObject) {
        for (MazeObject i : pole) {
            if (i.equals(MazeObject)) {
                return true;
            }
        }
        return false;
    }

}
