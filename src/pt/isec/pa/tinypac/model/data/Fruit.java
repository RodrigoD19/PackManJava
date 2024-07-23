package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;
/**
 * Fruta
 * */
public class Fruit implements IMazeElement, Serializable {
    private static final long serialVersionUID = 1L;
    private static final char symbol='F';
    private static Fruit instance;
    private Boolean visible;
    private int linha,coluna;
    private  final int multiplier=25;
    private int nivel,contador;

    private Fruit(int x,int y,int nivel) {
        this.visible = false;
        this.linha=x;
        this.coluna=y;
        this.nivel=nivel;
        contador=0;
        instance=this;
    }
    /**
     * Metodo singleton
     * */

    public static Fruit getInstance(int x,int y,int nivel) {

        if(instance==null)
            instance=new Fruit(x,y,nivel);
        return instance;
    }
    /**
     * Conta pontos para dps tornar a fruta visivel
     * */
    public void addcontador(){
        contador++;
        if(contador==20){
            visible=true;
            contador=0;
        }
    }
    /**
     * Fruta comida
     * */
    public void eaten(){
        contador=0;
        visible=false;
    }


    /**
     * Metodo para calcular quantos pontos a fruta dara
     * */
    public int getMultiplier() {
        return multiplier*nivel;
    }

    @Override
    public char getSymbol() {
        if(visible)
            return symbol;
        return ' ';
    }
}
