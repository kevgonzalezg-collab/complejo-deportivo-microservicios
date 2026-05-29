package com.canchas.mscanchas.dto; // 👈 Revisa que este paquete sea idéntico al de tus otros DTOs

public class EstadisticasEspejoDTO {

    private Integer idCancha;
    private String nombreCancha;
    private String tipoCancha;
    private Integer cantidadUsosTotal;
    private Integer minutesJugadosAcumulados;
    private Double ingresosGenerados;

    // Constructor vacío
    public EstadisticasEspejoDTO() {
    }

    // Constructor completo
    public EstadisticasEspejoDTO(Integer idCancha, String nombreCancha, String tipoCancha,
                                 Integer cantidadUsosTotal, Integer minutesJugadosAcumulados,
                                 Double ingresosGenerados) {
        this.idCancha = idCancha;
        this.nombreCancha = nombreCancha;
        this.tipoCancha = tipoCancha;
        this.cantidadUsosTotal = cantidadUsosTotal;
        this.minutesJugadosAcumulados = minutesJugadosAcumulados;
        this.ingresosGenerados = ingresosGenerados;
    }

    // Getters y Setters
    public Integer getIdCancha() { return idCancha; }
    public void setIdCancha(Integer idCancha) { this.idCancha = idCancha; }
    public String getNombreCancha() { return nombreCancha; }
    public void setNombreCancha(String nombreCancha) { this.nombreCancha = nombreCancha; }
    public String getTipoCancha() { return tipoCancha; }
    public void setTipoCancha(String tipoCancha) { this.tipoCancha = tipoCancha; }
    public Integer getCantidadUsosTotal() { return cantidadUsosTotal; }
    public void setCantidadUsosTotal(Integer cantidadUsosTotal) { this.cantidadUsosTotal = cantidadUsosTotal; }
    public Integer getMinutesJugadosAcumulados() { return minutesJugadosAcumulados; }
    public void setMinutesJugadosAcumulados(Integer minutesJugadosAcumulados) { this.minutesJugadosAcumulados = minutesJugadosAcumulados; }
    public Double getIngresosGenerados() { return ingresosGenerados; }
    public void setIngresosGenerados(Double ingresosGenerados) { this.ingresosGenerados = ingresosGenerados; }
}