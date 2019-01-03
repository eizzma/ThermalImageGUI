package thermalimage;

import java.io.*;

/**
 * This class is intended to be used with the SystemCommandExecutor
 * class to let users execute system commands from Java applications.
 * <p>
 * This class is based on work that was shared in a JavaWorld article
 * named "When System.exec() won't". That article is available at this
 * url:
 * <p>
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * <p>
 * Documentation for this class is available at this URL:
 * <p>
 * http://devdaily.com/java/java-processbuilder-process-system-exec
 * <p>
 * <p>
 * Copyright 2010 alvin j. alexander, devdaily.com.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Please ee the following page for the LGPL license:
 * http://www.gnu.org/licenses/lgpl.txt
 */
class ThreadedStreamHandler extends Thread {
    InputStream inputStream;
    PrintWriter printWriter;
    StringBuilder outputBuffer = new StringBuilder();

    ThreadedStreamHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() {


    }

    private void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public StringBuilder getOutputBuffer() {
        return outputBuffer;
    }

}









