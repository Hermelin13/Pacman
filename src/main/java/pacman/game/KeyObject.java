/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of Keys
 */

package pacman.game;

import pacman.common.Field;

public class KeyObject extends BasicObject {
    public KeyObject(Field f) {
        super(f);
    }

    public boolean isKey() {
        return true;
    }
}
