/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beryx.textio.robot;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RoboTypist {
    private final Robot robot;
    private long startDelayMillis = 1000;
    private int charTypeDelayMillis = 200;
    private boolean autoEnter = true;

    public RoboTypist() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStartDelayMillis(long startDelayMillis) {
        this.startDelayMillis = startDelayMillis;
    }

    public void setCharTypeDelayMillis(int charTypeDelayMillis) {
        this.charTypeDelayMillis = charTypeDelayMillis;
    }

    public void setAutoEnter(boolean autoEnter) {
        this.autoEnter = autoEnter;
    }

    public void type(String text) {
        Executors.newSingleThreadScheduledExecutor(r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                })
                .schedule(() -> typeNow(text), startDelayMillis, TimeUnit.MILLISECONDS);
    }

    public void typeNow(String text) {
        String s = autoEnter ? (text + "\n") : text;
        for(int i = 0; i <s.length(); i++) {
            paste("" + s.charAt(i));
            robot.delay(charTypeDelayMillis);
        }
    }

    public void paste(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
