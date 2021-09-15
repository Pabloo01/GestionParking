/*
 * Copyright 2021 Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es.
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
package org.japo.java.libraries;

import java.util.Random;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public class UtilesAleatorio {
    
    //Objeto Rnd
    public static final Random RND = new Random();
    
    // Entero Random
    public static final int aleatorioEntero(int vMin, int vMax) {
        return RND.nextInt(vMax - vMin + 1) + vMin;
    }
    
    // Real Random
    public static final double aleatorioReal(double vMin, double vMax) {
        return RND.nextDouble() * (vMax - vMin) + vMin;
    }
    
    // Caracter Random
    public static final char aleatorioCaracter(char vMin, char vMax) {
        return (char) (RND.nextInt(vMax + vMin) + vMin);
    }

    // Lógico Random
    public static final boolean aleatorioLogico() {
        return RND.nextBoolean();
    }

}
