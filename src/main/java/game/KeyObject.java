/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of Keys
 */

package game;

import common.Field;

public class KeyObject extends BasicObject {
    public KeyObject(Field f) {
        super(f);
    }

    public boolean isKey() {
        return true;
    }
}
