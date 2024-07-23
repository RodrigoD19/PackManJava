package pt.isec.pa.tinypac.model.data;
/**
 * Enum usado para facilitar processos de movimento
 * */
public enum Direcoes {
    ArrowUp,ArrowDown,ArrowRight,ArrowLeft;
    /**
     * Metodo para descobrir a direçáo oposta
     * */

    public static Direcoes oposta(Direcoes d){
     return    switch (d){
         case ArrowUp -> ArrowDown;
         case ArrowDown -> ArrowUp;
         case ArrowRight -> ArrowLeft;
         case ArrowLeft -> ArrowRight;
     };
    }
}
