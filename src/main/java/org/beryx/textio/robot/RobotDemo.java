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

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class RobotDemo {
    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerm = textIO.getTextTerminal();
        textTerm.init();

        RoboTypist typist = new RoboTypist();

        typist.type("John");
        String name = textIO.newStringInputReader().read("Name");

        typist.type("21");
        int age = textIO.newIntInputReader().read("Age");

        typist.type("student");
        String occupation = textIO.newStringInputReader().read("Occupation");


        textTerm.printf("\nHello, %s! I just found out that you are a %d years old %s.\n", name, age, occupation);


        textIO.newStringInputReader().withMinLength(0).read("Press enter to terminate...");
        textIO.dispose();
    }
}
