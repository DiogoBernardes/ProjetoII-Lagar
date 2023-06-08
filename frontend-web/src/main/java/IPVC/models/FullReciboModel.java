package IPVC.models;

import IPVC.DAL.LinhaRecibo;
import IPVC.DAL.Recibo;

import java.util.List;

public class FullReciboModel {

    private Recibo recibo;
    private List<LinhaRecibo> linhasRecibo;

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public List<LinhaRecibo> getLinhasRecibo() {
        return linhasRecibo;
    }

    public void setLinhasRecibo(List<LinhaRecibo> linhasRecibo) {
        this.linhasRecibo = linhasRecibo;
    }
}
