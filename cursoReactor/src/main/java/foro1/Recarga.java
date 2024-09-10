package foro1;

public class Recarga {
    private String id;
    private String numero;
    private String valor;
    private String fecha;

    public Recarga(String id, String numero, String valor, String fecha) {
        this.id = id;
        this.numero = numero;
        this.valor = valor;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getValor() {
        return valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Recarga{" +
                "id='" + id + '\'' +
                ", numero='" + numero + '\'' +
                ", valor='" + valor + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
