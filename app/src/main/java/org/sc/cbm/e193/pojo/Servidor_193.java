package org.sc.cbm.e193.pojo;

public class Servidor_193 {

    private String ip_servidor;
    private String nm_servidor;

    public Servidor_193() {}

    public Servidor_193(String ip_servidor, String nm_servidor) {
        this.ip_servidor = ip_servidor;
        this.nm_servidor = nm_servidor;
    }

    public String getNm_servidor() {
        return nm_servidor;
    }

    public void setNm_servidor(String nm_servidor) {
        this.nm_servidor = nm_servidor;
    }

    public String getIp_servidor() {
        return ip_servidor;
    }

    public void setIp_servidor(String ip_servidor) {
        this.ip_servidor = ip_servidor;
    }

    @Override
    public String toString() {
        return getNm_servidor();
    }
}
