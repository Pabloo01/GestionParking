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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public class UtilesFecha {
    //Calendar
    public static final Calendar CAL = Calendar.getInstance();

    // Constantes
    public static final String DATE_PATTERN_DEFAULT = "dd/MM/yyyy";
    public static final String DATE_PATTERN_JDBC = "yyyy-MM-dd";

    // Consola + Patrón + MsgUsr + MsgErr > Fecha
    public static final Date leerFecha(String pattern, String msgUsr, 
            String msgErr) {
        // Referencia para la fecha
        Date fecha = null;

        // Bucle de Introducción
        do {
            try {
                // Formateador de Fecha
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                // Modo Estricto
                sdf.setLenient(false);

                // Consola > Texto ( Fecha )
                String texto = UtilesEntrada.leerTexto(msgUsr);

                // Texto > Date
                fecha = sdf.parse(texto);
            } catch (ParseException ex) {
                System.out.println(msgErr);
            }
        } while (fecha == null);

        // Fecha Introducida
        return fecha;
    }

    // Consola > Fecha
    public static final Date leerFecha(String msgUsr) {
        return leerFecha(DATE_PATTERN_DEFAULT, msgUsr, UtilesEntrada.MSG_ERR);
    }
    
    //Método diferenciaDias
    public static final long diferenciasDias(Date fechaInicio, Date fechaFinal) {

        //Diferencia días
        long dias = (int) ((fechaFinal.getTime() - fechaInicio.getTime()) / 86400000);

        return dias;
    }
}
