package org.sc.cbm.e193.pojo;

import java.math.BigInteger;

/**
 * Created by cbmsc on 14/10/15.
 */
public class Usuario {

    private BigInteger id_matricula;
    private String nm_login;
    private String nm_bombeiro;
    private String de_email;

    public Usuario() {
    }

    public String getDe_email() {
        return de_email;
    }

    public void setDe_email(String de_email) {
        this.de_email = de_email;
    }

    public String getNm_bombeiro() {
        return nm_bombeiro;
    }

    public void setNm_bombeiro(String nm_bombeiro) {
        this.nm_bombeiro = nm_bombeiro;
    }

    public String getNm_login() {
        return nm_login;
    }

    public void setNm_login(String nm_login) {
        this.nm_login = nm_login;
    }

    public BigInteger getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(BigInteger id_matricula) {
        this.id_matricula = id_matricula;
    }


}
