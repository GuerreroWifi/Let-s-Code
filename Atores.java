package br.com.letscode.java;

import lombok.Value;

@Value
public class Atores {

    Integer index;
    Integer ano;
    Integer idade;
    String nome;
    String filme;

    public static Atores fromLine(String line){
        String[] split = line.split("; ");
        return new Atores(Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]),
                split[3],
                split[4]);
    }
}
