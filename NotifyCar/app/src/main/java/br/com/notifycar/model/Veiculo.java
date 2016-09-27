package br.com.notifycar.model;

/**
 * Created by Desenvolvimento on 25/09/2016.
 */
public class Veiculo {

    private String cor;
    private String ano;
    private String placa;
    private String modeloId;
    private String usuarioId;

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModeloId() {
        return modeloId;
    }

    public void setModeloId(String modeloId) {
        this.modeloId = modeloId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
