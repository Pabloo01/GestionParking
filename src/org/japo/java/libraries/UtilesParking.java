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

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public class UtilesParking {
    
    // Constante
    public static final int IMPORTE = 20;
    
    // Mensaje
    public static final String MSG_VEHICULO_CONSULTA
            = "Escriba el ID del vehículo que desea consultar: ";
    public static final String MSG_INSERCION_ID = "ID ...............: ";
    public static final String MSG_INSERCION_MAT = "Matricula ........: ";
    public static final String MSG_INSERCION_TV = "Tipo de vehículo .: ";
    public static final String MSG_INSERCION_FEN = "Fecha de entrada .: ";
    public static final String MSG_MODIFICAR_FSA = "Fecha de salida ..: ";
    public static final String MSG_MODIFICAR_IMP = "Importe ..........:";
    
    // Mensaje de Error Genérico
    public static final String MNU_ERROR = "ERROR: Entrada Incorrecta";
    
    // Menú Parking
    public static final String MNU_PARKING_OPC = "01234";
    public static final String MNU_PARKING_MSG
            = "PARKING CAMP DE MORVEDRE" + '\n'
            + "========================" + '\n'
            + "" + '\n'
            + "[ 1 ] Insertar   - Nuevo/s Vehículo/s" + '\n'
            + "[ 2 ] Eliminar   - Eliminar Vehículo" + '\n'
            + "[ 3 ] Consultar  - Ver Vehículo Específico" + '\n'
            + "[ 4 ] Listar     - Ver todos los Vehículos" + '\n'
            + "" + '\n'
            + "[ 0 ] Salir" + '\n'
            + "" + '\n'
            + "Opción: ";
    
    // Parámetros del Menú Inserción Vehículo
    public static final String MNU_INSERCION_OPC = "012";
    public static final String MNU_INSERCION_MSG
            = "Agenda Escolar - Menú de Inserción de Vehículos" + '\n'
            + "===============================================" + '\n'
            + "" + '\n'
            + "[ 1 ] Manual     - Datos por Consola" + '\n'
            + "[ 2 ] Importar   - Datos Archivo CSV" + '\n'
            + "" + '\n'
            + "[ 0 ] Menú Anterior" + '\n'
            + "" + '\n'
            + "Opción: ";
}
