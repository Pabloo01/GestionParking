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
package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author Pablo Claramunt Hernandis - pablo.claramunt.alum@iescamp.es
 */
public class Vehiculo {
    
    // Campos
    private int id;
    private String matricula;
    private String tipoVehiculo;
    private Date fechaEntrada;
    private Date fechaSalida;
    private int importe;
    
    // Constructor Parametrizado
    public Vehiculo(int id, String matricula, String tipoVehiculo, 
            Date fechaEntrada, Date fechaSalida, int importe) {
        this.id = id;
        this.matricula = matricula;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.importe = importe;
    }
    
    // Accesores
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }
    
    // Método toString
    @Override
    public String toString() {
        return String.format("%1$d %2$s %3$s %4$te/%4$tm/%4$tY %5$te/%5$tm/%5$tY %6$d", 
                id, matricula, tipoVehiculo, fechaEntrada, fechaSalida, importe);
    }
    
    
}
