/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of target
 */

package pacman.game;

import pacman.common.Field;

public class TargetObject extends BasicObject {
    public TargetObject(Field f) {
        super(f);
    }

    public boolean isTarget() {
        return true;
    }
}
