/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of target
 */

package game;

import common.Field;

public class TargetObject extends BasicObject {
    public TargetObject(Field f) {
        super(f);
    }

    public boolean isTarget() {
        return true;
    }
}
