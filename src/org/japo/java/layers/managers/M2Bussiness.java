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
import java.util.List;
import java.util.Properties;
import org.japo.java.entities.Credencial;
import org.japo.java.entities.Vehiculo;
import org.japo.java.exceptions.ConnectivityException;
import org.japo.java.layers.services.S2Bussiness;
import org.japo.java.layers.services.S3Data;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public final class M2Bussiness implements S2Bussiness {

    //<editor-fold defaultstate="collapsed" desc="--- Bussiness Logic Manager ---">
    // Propiedades de la Aplicación
    private final Properties prp;

    // Servicios de Acceso a Datos
    private final S3Data ds;

    // Constructor Parametrizado
    public M2Bussiness(Properties prp, S3Data ds) {
        // Propiedades Aplicación
        this.prp = prp;

        // Gestor de Acceso a Datos + Iniciar conexión BD
        this.ds = ds;
    }

    // Conectar BD
    @Override
    public final void abrirAccesoDatos(Credencial c)
            throws ConnectivityException {
        ds.abrirAccesoDatos(c);
    }

    // Desconectar BD
    @Override
    public final void cerrarAccesoDatos() throws ConnectivityException {
        ds.cerrarAccesoDatos();
    }
    //</editor-fold>

    // Consultar Vehículo
    @Override
    public Vehiculo consultarVehiculo(Integer id) 
            throws NullPointerException, SQLException {
        return ds.consultarVehiculo(id);
    }
    
    // Listar Vehículos
    @Override
    public List<Vehiculo> listarVehiculo() 
            throws NullPointerException, SQLException {
        return ds.listarVehiculo();
    }
    
    // Insertar Vehículo - Manual
    @Override
    public boolean insertarVehiculo(Vehiculo v) 
            throws NullPointerException, SQLException {
        return ds.insertarVehiculo(v);
    }
    
    // Modificar Vehículo
    @Override
    public boolean modificarVehiculo(Vehiculo v) 
            throws NullPointerException, SQLException {
        return ds.modificarVehiculo(v);
    }
    
    // Borrar Vehículo
    @Override
    public int borrarAlumnos(String[] param) 
            throws NullPointerException, SQLException {
        return ds.borrarVehiculo(param);
    }
}
