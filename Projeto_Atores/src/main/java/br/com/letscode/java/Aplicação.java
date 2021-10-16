testepackage br.com.letscode.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicação {
    private List<Atores> atores;
    private List<Atores> atrizes;

    public static void main(String[] args) {
        Aplicação app = new Aplicação();
        app.leituraCSVAtores();
        app.leituraCSVAtrizes();
        app.atorMaisJovem();
        app.atrizMaisVencedora();
        app.atrizVTmaisVencedora();
        app.atoresMaisUm();
        app.atorInfo("Tom Hanks");
        app.atorInfo("Luise Rainer");
    }

    private void atorInfo(String p) {
        System.out.println("5.) Informações do Ator/Atriz: ");
        this.atores.stream()
                .filter(x -> Objects.equals(x.getNome(), p))
                .forEach(System.out::println);
        this.atrizes.stream()
                .filter(x -> Objects.equals(x.getNome(), p))
                .forEach(System.out::println);
        System.out.println(" ");

    }

    private void atoresMaisUm() {
        System.out.println("4.) Lista de Atores e Atrizes que conquistaram o Oscar mais de uma vez: ");
        System.out.println(" ");
        System.out.println("Atrizes: ");
        Map<String, Long> nomeAtriz = this.atrizes.stream()
                .map(Atores::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtriz.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .forEach(System.out::println);
        System.out.println(" ");
        System.out.println("Atores: ");
        Map<String, Long> nomeAtor = this.atores.stream()
                .map(Atores::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtor.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .forEach(System.out::println);
        System.out.println(" ");
    }

    private void atrizVTmaisVencedora() {
        System.out.print("3.) A atriz entre 20 e 30 mais vencedora do Oscar foi: ");
        Map<String, Long> nome = this.atrizes.stream()
                .filter(x -> x.getIdade() >= 20 && x.getIdade() <=30)
                .map(Atores::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nome.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(c -> System.out.println(c.getKey()+" é a que possui mais Oscar, ao todo: "+ c.getValue()));
        System.out.println(" ");
    }

    private void atorMaisJovem() {
        System.out.print("1.) O ator mais jovem a ganhar um Oscar foi: ");
        this.atores.stream()
                .min(Comparator.comparingInt(Atores::getIdade))
                .ifPresent(System.out::println);
        System.out.println(" ");
    }

    private void atrizMaisVencedora(){
        System.out.print("2.) A atriz mais vencedora do Oscar foi: ");
        Map<String, Long> nome = this.atrizes.stream()
                .map(Atores::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nome.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(c -> System.out.println(c.getKey()+" é a que possui mais Oscar, ao todo: "+ c.getValue()));
        System.out.println(" ");
    }

    private void leituraCSVAtores() {
        String docAtor = getFilepathFromResourceAsStream("Atores.csv");
        try (Stream<String> linesAtor = Files.lines(Path.of(docAtor))) {
            this.atores = linesAtor.skip(1)
                    .map(Atores::fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leituraCSVAtrizes() {
        String docAtor = getFilepathFromResourceAsStream("Atrizes.csv");
        try (Stream<String> linesAtor = Files.lines(Path.of(docAtor))) {
            this.atrizes = linesAtor.skip(1)
                    .map(Atores::fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilepathFromResourceAsStream(String arquivo) {
        URL url = Aplicação.class.getClassLoader().getResource(arquivo);
        File file = new File(url.getFile());
        return file.getPath();
    }
}




