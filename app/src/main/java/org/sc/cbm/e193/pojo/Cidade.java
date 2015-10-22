package org.sc.cbm.e193.pojo;

/**
 * Created by cbmsc on 28/09/15.
 */
public class Cidade {

    private long id_cidade;
    private String nm_cidade;

    public Cidade() {}

    public Cidade(long id_cidade, String nm_cidade) {
        this.id_cidade = id_cidade;
        this.nm_cidade = nm_cidade;
    }

    public long getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(long id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNm_cidade() {
        return nm_cidade;
    }

    public void setNm_cidade(String nm_cidade) {
        this.nm_cidade = nm_cidade;
    }

    @Override
    public String toString(){
        return this.nm_cidade;
    }
}
