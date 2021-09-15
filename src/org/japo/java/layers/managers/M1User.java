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
package org.japo.java.layers.managers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Vehiculo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S1User;
import org.japo.java.libraries.UtilesEntrada;
import org.japo.java.libraries.UtilesFecha;
import org.japo.java.libraries.UtilesParking;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public final class M1User implements S1User {

    //<editor-fold defaultstate="collapsed" desc="--- User Interface Manager ---">
    // Propiedades Credencial
    public static final String PRP_CONN_MODE = "jdbc.conn.mode";
    public static final String PRP_CONN_USER = "jdbc.conn.user";
    public static final String PRP_CONN_PASS = "jdbc.conn.pass";

    // Propiedades Aplicación
    private final Properties prp;

    // Gestor Lógica de Negocio
    private final S2Bussiness bs;

    // Constructor Parametrizado
    public M1User(Properties prp, S2Bussiness bs) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor Lógica de Negocio
        this.bs = bs;
    }

    // Validación de Usuario
    @Override
    public final void loginApp() throws ConnectivityException {
        // Usuario > Credenciales
        String user = prp.getProperty(PRP_CONN_USER);
        String pass = prp.getProperty(PRP_CONN_PASS);

        // Modo de Conexión
        String mode = prp.getProperty(PRP_CONN_MODE);

        // Evaluación del modo de conexión
        if (mode != null && mode.equals("login")) {
            // Cabecera
            System.out.println("Acceso a la Aplicación");
            System.out.println("======================");

            // Entrada de Campos
            user = UtilesEntrada.leerTexto("Usuario ..............: ");
            pass = UtilesEntrada.leerTexto("Contraseña ...........: ");

            // Separador
            System.out.println("---");
        }

        // Creación de Entidad Credencial
        Credencial c = new Credencial(user, pass);

        // Conexión de Credenciales
        bs.abrirAccesoDatos(c);

        // Mensaje - Bitácora ( Comentar en producción )
//        System.out.println("Plantilla de Patrón Estructural por Capas Funcionales");
//        System.out.println("=====================================================");
//        System.out.println("Acceso Establecido");
//        System.out.println("---");
    }

    // Cierre de la Aplicación
    @Override
    public final void closeApp() throws ConnectivityException {
        // Cierre Base de Datos
        bs.cerrarAccesoDatos();

        // Mensaje - Bitácora ( Comentar en producción )
//        System.out.println("Acceso Finalizado");
        // Despedida
//        System.out.println("---");
        System.out.println("Copyright JAPO Labs - Servicios Informáticos");
    }
    //</editor-fold>

    // Ejecución de la Aplicación
    @Override
    public final void launchApp() {
        // Llamada al método gestionParking
        gestionParking();
    }

    // Menú Parking
    private void gestionParking() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener opción
            opc = UtilesEntrada.leerCaracter(UtilesParking.MNU_PARKING_MSG,
                    UtilesParking.MNU_ERROR, UtilesParking.MNU_PARKING_OPC);

            // Separador
            System.out.println("---");

            //Opciones 
            switch (opc) {
                case '1':
                    procesarMenuInsercionVehiculo();
                    break;
                case '2':
                    eliminarVehiculo();
                    break;
                case '3':
                    consultarVehiculo();
                    break;
                case '4':
                    listarVehiculo();
            }

        } while (opc != '0');
    }

    // Menú Inserción
    private void procesarMenuInsercionVehiculo() {
        // Opción Elegida Menú
        char opc;

        // Bucle Gestión Menú
        do {
            // Obtener Opción
            opc = UtilesEntrada.leerCaracter(UtilesParking.MNU_INSERCION_MSG,
                    UtilesParking.MNU_ERROR, UtilesParking.MNU_INSERCION_OPC);

            // Separador
            System.out.println("---");

            // Procesar Opción
            switch (opc) {
                case '1':
                    insercionManualVehiculos();
                    break;
                case '2':
                    insercionLotesVehiculos();
            }
        } while (opc != '0');
    }

    // Inserción Vehiculos - Manual
    private void insercionManualVehiculos() {
        // Cabecera
        System.out.println("INSERCIÓN VEHÍCULO");
        System.out.println("==================");
        System.out.println("Introduce los datos correspondienes del vehículo que desea insertar ...");

        // Datos por consola
        int id = UtilesEntrada.leerEntero(UtilesParking.MSG_INSERCION_ID,
                UtilesParking.MNU_ERROR);
        String matricula
                = UtilesEntrada.leerTexto(UtilesParking.MSG_INSERCION_MAT);
        String tipoVehiculo
                = UtilesEntrada.leerTexto(UtilesParking.MSG_INSERCION_TV);
        Date fechaEntrada = UtilesFecha.leerFecha(UtilesParking.MSG_INSERCION_FEN);

        // Separador
        System.out.println("---");

        try {
            // Datos a insertar
            Vehiculo v = new Vehiculo(
                    id,
                    matricula,
                    tipoVehiculo,
                    fechaEntrada,
                    null,
                    0);

            // Llamada a la capa de Negocio
            boolean insercionOk = bs.insertarVehiculo(v);

            // Resultado de la Operación
            System.out.printf("OPERACIÓN: Inserción %s realizada%n",
                    insercionOk ? "SI" : "NO");
        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operación de Inserción cancelada");
        }

        // Hacer Pausa
        UtilesEntrada.hacerPausa();
    }

    // Inserción Vehiculos - Lotes
    private void insercionLotesVehiculos() {
        System.out.println("Disponible proximamente");
    }

    // Eliminar Vehiculo
    private void eliminarVehiculo() {
        // Cabecera
        System.out.println("QUITAR VEHÍCULO");
        System.out.println("===============");

        // Datos por consola - ID
        int id = UtilesEntrada.leerEntero(UtilesParking.MSG_VEHICULO_CONSULTA,
                UtilesParking.MNU_ERROR);

        try {
            // Extaer Datos
            Vehiculo v = bs.consultarVehiculo(id);

            // Insertar Fecha de Salida dd/MM/yyyy
            Date fechaSalida = UtilesFecha.leerFecha(UtilesParking.MSG_MODIFICAR_FSA);

            // Diferencia de día
            long diferencia = UtilesFecha.diferenciasDias(fechaSalida,
                    v.getFechaEntrada());

            // Calcular Importe
            int importe = (int) (diferencia * UtilesParking.IMPORTE) * -1;

            // Datos a modificar
            v = new Vehiculo(
                    v.getId(),
                    v.getMatricula(),
                    v.getTipoVehiculo(),
                    v.getFechaEntrada(),
                    fechaSalida,
                    importe);

            // Ejecutar Modificación
            boolean modificacionOk = bs.modificarVehiculo(v);

            // Resultado de la Operación
            System.out.printf("OPERACIÓN: Modificación %s realizada%n",
                    modificacionOk ? "SI" : "NO");
            
            // Separador
            System.out.println("---");
            
            // Importe 
            System.out.printf("Importe a pagar ...: %d%n", importe);
            
            // Separador
            System.out.println("---");
            
            // Conversión int > String
            String idStr = String.valueOf(id);
            
            // Parámetros de borrado
            String[] param = {idStr};
            
            // Llamada a la capa bussiness
            int filas = bs.borrarAlumnos(param);

            // Salida por pantalla
            System.out.printf("Operación realizada - Gracias por su visita%n",
                    filas);
            
        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operación cancelada " + e.getMessage());
        }
        
        // Hacer pausa
        UtilesEntrada.hacerPausa();

    }

    // Consultar Vehículo
    private void consultarVehiculo() {
        // Cabecera
        System.out.println("CONSULTA VEHÍCULO");
        System.out.println("=================");

        // Datos por consola
        int id = UtilesEntrada.leerEntero(UtilesParking.MSG_VEHICULO_CONSULTA,
                UtilesParking.MNU_ERROR);

        // Separador
        System.out.println("---");

        try {
            // Llamada a la capa de Negocio - Obtener Vehículo
            Vehiculo v = bs.consultarVehiculo(id);

            // Mensaje
            System.out.printf("ID ...............: %d%n", v.getId());
            System.out.printf("Matricula ........: %s%n", v.getMatricula());
            System.out.printf("Tipo de vehículo .: %s%n", v.getTipoVehiculo());
            System.out.printf("Fecha de entrada .: %1$te/%1$tm/%1$tY%n",
                    v.getFechaEntrada());
            System.out.printf("Fecha de salida ..: %1$te/%1$tm/%1$tY%n",
                    v.getFechaSalida());
            System.out.printf("Importe ..........: %d%n", v.getImporte());

        } catch (NullPointerException | SQLException e) {
            System.out.println("ERROR: Operación cancelada" + e.getMessage());
        }

        // Hacer Pausa
        UtilesEntrada.hacerPausa();
    }

    // Listar Vehícula
    private void listarVehiculo() {
        try {
            //Base de Datos - Lista de Alumnos
            List<Vehiculo> lista = bs.listarVehiculo();

            // Lista Alumnos > Pantalla
            listarVehiculos(lista);

        } catch (SQLException | NullPointerException e) {
            System.out.println("ERROR: Datos delvehículo no disponibles");
        }

        // Hacer Pausa
        UtilesEntrada.hacerPausa();
    }

    // Listar Vehículos > Mostrar por pantalla
    private void listarVehiculos(List<Vehiculo> lista)
            throws NullPointerException, SQLException {

        //Mensaje de inicio
        System.out.println("LISTADO DE VEHÍCULOS");
        System.out.println("====================");

        //Proceso de listado
        if (lista.size() > 0) {
            System.out.printf(" #   Matrícula     Tipo de Vehículo     Fecha de Entrada   Fecha de Salida       Importe%n", lista);
            System.out.println("=== ==========   ================    ======================  ==============   ==========");

            ///Recorre lista de Registros
            for (int i = 0; i < lista.size(); i++) {
                //Imprime los datos del registro actual
                System.out.printf("%03d %s%n", i + 1, lista.get(i));
            }
        } else {
            System.out.println("Datos de Vehículos no disponibles");
        }
        
    }

}
