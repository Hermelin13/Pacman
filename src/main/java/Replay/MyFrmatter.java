/**************************************************
 * AUTHORS: Martin Pentrák (xpentr00)
 * Description: Implementation of logger format
 */

package Replay;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFrmatter extends Formatter {
    @Override
    public String format(LogRecord logRecord) {
        return logRecord.getMessage() + "\n";
    }
}
