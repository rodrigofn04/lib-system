import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import modulos.Livro;

class BibliotecaVirtual{
    LinkedList<Livro> listaLivros;
    Queue<Livro> filaLivros;
    Stack<Livro> historicoNavegacao;
    LinkedList<Livro> historicoLivros;
    HashMap<String, List<Livro>> indicacaoLivros;
    HashMap<String, Integer> frequenciaGeneros;
    private arvoreLivros arvoreBinaria;
    Livro cabecalhoLivrosCadastrados;  // Lista encadeada dos livros cadastrados
    Livro cabecalhoLivrosEmprestados;  // Lista encadeada dos livros emprestados 
    Livro cabecalhoLivrosHistorico;    // Lista encadeada do histórico de livros do usuario
    private static HashMap<Livro, Set<Livro>> grafoLivros;
    
    public class GeradorDeNomesDeLivros {
    private static final int TAMANHO_MAXIMO_DO_NOME = 20;
    private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";

    public static String[] gerarNomes(int numeroDeLivros) {
        Random random = new Random();
        String[] nomesDeLivros = new String[numeroDeLivros];

        for (int i = 0; i < numeroDeLivros; i++) {
            nomesDeLivros[i] = gerarNomeAleatorio(random);
        }

        return nomesDeLivros;
    }

    public class BubbleSort {
        public static int comparacoes = 0;
    
        public static String[] ordenar(String[] arrOriginal) {
            String[] arr = arrOriginal.clone();
            int n = arr.length;
            comparacoes = 0;
    
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    comparacoes++;
                    if (arr[j].compareTo(arr[j + 1]) > 0) {
                        String temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
    
            return arr;
        }
    }

    
    public class MergeSort {
        public static int comparacoes = 0;
    
        public static String[] ordenar(String[] arrOriginal) {
            String[] arr = arrOriginal.clone();
            comparacoes = 0;
            return ordenarRecursivo(arr);
        }
    
        private static String[] ordenarRecursivo(String[] arr) {
            if (arr.length < 2) return arr;
    
            int meio = arr.length / 2;
            String[] esquerda = new String[meio];
            String[] direita = new String[arr.length - meio];
    
            System.arraycopy(arr, 0, esquerda, 0, meio);
            System.arraycopy(arr, meio, direita, 0, arr.length - meio);
    
            esquerda = ordenarRecursivo(esquerda);
            direita = ordenarRecursivo(direita);
    
            return merge(esquerda, direita);
        }
    
        private static String[] merge(String[] esquerda, String[] direita) {
            String[] resultado = new String[esquerda.length + direita.length];
            int i = 0, j = 0, k = 0;
    
            while (i < esquerda.length && j < direita.length) {
                comparacoes++;
                if (esquerda[i].compareTo(direita[j]) <= 0) {
                    resultado[k++] = esquerda[i++];
                } else {
                    resultado[k++] = direita[j++];
                }
            }
    
            while (i < esquerda.length) {
                resultado[k++] = esquerda[i++];
            }
    
            while (j < direita.length) {
                resultado[k++] = direita[j++];
            }
    
            return resultado;
        }
    }

    private static String gerarNomeAleatorio(Random random) {
        int tamanhoDoNome = random.nextInt(TAMANHO_MAXIMO_DO_NOME) + 1;
        StringBuilder sb = new StringBuilder(tamanhoDoNome);

        for (int i = 0; i < tamanhoDoNome; i++) {
            sb.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }

        return sb.toString();
    }
}


    public BibliotecaVirtual(){
        this.listaLivros = new LinkedList<>();
        this.filaLivros = new LinkedList<>();
        this.historicoLivros = new LinkedList<>();
        this.historicoNavegacao = new Stack<>();
        this.indicacaoLivros = new HashMap<>();
        this.frequenciaGeneros = new HashMap<>();
        this.arvoreBinaria = new arvoreLivros();
        this.grafoLivros = new HashMap<>();
    }

    public arvoreLivros getArvoreBinaria() {
        return arvoreBinaria;
    }

    public void setArvoreBinaria(arvoreLivros arvoreBinaria) {
        this.arvoreBinaria = arvoreBinaria;
    }

    class livroNode {
        Livro livro;
        livroNode esquerda, direita;
    
        public livroNode(Livro livro) {
            this.livro = livro;
            esquerda = null;
            direita = null;
        }
    }

    public class arvoreLivros{
        private livroNode raiz;

        public void inserir(Livro livro) {
            raiz = inserirRecursivo(raiz, livro);
        }

        private livroNode inserirRecursivo(livroNode atual, Livro livro){
            if(atual == null){
                return new livroNode(livro);
            }

            if(livro.getTitulo().compareToIgnoreCase(atual.livro.getTitulo()) < 0){
                atual.esquerda = inserirRecursivo(atual.esquerda, livro);
            } 
            else {
                atual.direita = inserirRecursivo(atual.direita, livro);
            }
            return atual;
        }

       public void exibirLivrosOrdenados(Stack<Livro> historicoNavegacao){
            if (raiz == null){
                System.out.println("Nenhum livro encontrado..");
                return;
            }

            exibirRecursivo(raiz, historicoNavegacao);

            if (!historicoNavegacao.isEmpty()) {
                System.out.println();
                System.out.println("Histórico atualizado, último livro acessado: " + historicoNavegacao.peek().getTitulo());
            }
       }

       private void exibirRecursivo(livroNode node, Stack<Livro> historicoNavegacao){
            if (node != null){
                exibirRecursivo(node.esquerda, historicoNavegacao);

                System.out.println(
                    "Título: " + node.livro.getTitulo() +
                    "\n Autor: " + node.livro.getAutor() +
                    "\n Ano: " + node.livro.getAnoPublicacao() +
                    "\n Gênero: " + node.livro.getGenero() + "\n"
                );
                historicoNavegacao.push(node.livro);
                exibirRecursivo(node.direita, historicoNavegacao);
            } 
       }
    }
    
    public void adicionarLivro(String titulo, String autor, int anoPublicacao, String genero){
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, genero);
        listaLivros.add(novoLivro);
        arvoreBinaria.inserir(novoLivro);
        grafoLivros.putIfAbsent(novoLivro, new HashSet<>());

        for (Livro outroLivro : grafoLivros.keySet()) {
                if (!novoLivro.equals(outroLivro) && novoLivro.getGenero().equals(outroLivro.getGenero())) {
                    grafoLivros.get(novoLivro).add(outroLivro);
                    grafoLivros.get(outroLivro).add(novoLivro);
                }
            }

        if (cabecalhoLivrosCadastrados == null){
            cabecalhoLivrosCadastrados = novoLivro;
        } else{
            Livro atual = cabecalhoLivrosCadastrados;
            while (atual.getProximo() != null) {  // Usando o getter para acessar 'proximo'
                atual = atual.getProximo();
            }
            atual.setProximo(novoLivro);  // Usando o setter para definir 'proximo'
        }

        if (!indicacaoLivros.containsKey(genero)) {
            indicacaoLivros.put(genero, new ArrayList<>());
        }
        
        indicacaoLivros.get(genero).add(novoLivro);
    }

    public void exibirLivrosOrdenados() {
        arvoreBinaria.exibirLivrosOrdenados(historicoNavegacao);
    }

    public void adicionarLivroEmprestado(String titulo, String autor, int anoPublicacao, String genero){
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, genero);
        filaLivros.add(novoLivro);

        if (cabecalhoLivrosEmprestados == null){
            cabecalhoLivrosEmprestados = novoLivro;
        } else{
            Livro atual = cabecalhoLivrosEmprestados;
            while (atual.getProximo() != null) { 
                atual = atual.getProximo();
            }
            atual.setProximo(novoLivro);  
        }
    }

    public void adicionarLivroHistorico(String titulo, String autor, int anoPublicacao, String genero){
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, genero);
        historicoLivros.add(novoLivro);
        /*grafoLivros.putIfAbsent(novoLivro, new HashSet<>());

        for (Livro outroLivro : grafoLivros.keySet()) {
                if (!novoLivro.equals(outroLivro) && novoLivro.getGenero().equals(outroLivro.getGenero())) {
                    grafoLivros.get(novoLivro).add(outroLivro);
                    grafoLivros.get(outroLivro).add(novoLivro);
                }
            }*/

        if (cabecalhoLivrosHistorico == null){
            cabecalhoLivrosHistorico = novoLivro;
        } else{
            Livro atual = cabecalhoLivrosHistorico;
            while (atual.getProximo() != null) { 
                atual = atual.getProximo();
            }
            atual.setProximo(novoLivro);  
        }
    }

    public Livro retornarLivro(){
        if(filaLivros.isEmpty()){
            System.out.println("Nenhum livro retornado.");
            return null;
        }
        Livro livroRetornado = filaLivros.poll();
        livroRetornado.setProximo(null);

        System.out.println("------------------");
        System.out.println("Livro retornado: " + livroRetornado.getTitulo());
        System.out.println("------------------");

        if (cabecalhoLivrosCadastrados == null){
            cabecalhoLivrosCadastrados = livroRetornado;
        } else {
            Livro atual = cabecalhoLivrosCadastrados;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(livroRetornado);
            arvoreBinaria.inserir(livroRetornado);
        }
        return livroRetornado;
    }

    
    public void indicarLivros(){
        if (historicoLivros.isEmpty()) {
            System.out.println("Sem histórico de leitura para recomendar.");
            return;
        }

        Map<String, Integer> frequenciaGeneros = new HashMap<>();
        for (Livro livro : historicoLivros) {
            frequenciaGeneros.put(livro.getGenero(), frequenciaGeneros.getOrDefault(livro.getGenero(), 0) + 1);
        }

        String generoComum = Collections.max(frequenciaGeneros.entrySet(), Map.Entry.comparingByValue()).getKey();

        Livro livroBase = null;
        for (int i = historicoLivros.size() - 1; i >= 0; i--) {
            if (historicoLivros.get(i).getGenero().equals(generoComum)) {
                livroBase = historicoLivros.get(i);
                break;
            }
        }

        if (livroBase == null) {
            System.out.println("Nenhum livro base encontrado no gênero mais lido.");
            return;
        }

        Map<Livro, Integer> distancias = djikstraSimples(grafoLivros, livroBase);

        System.out.println("");
        System.out.println("Recomendações automáticas com base no gênero mais lido (" + generoComum + "):");
        System.out.println("");

        distancias.entrySet().stream()
            .filter(entry -> !historicoLivros.contains(entry.getKey()) && entry.getKey().getGenero().equals(generoComum))
            .sorted(Map.Entry.comparingByValue())
            .limit(5)
            .forEach(entry -> {
                Livro livro = entry.getKey();
                System.out.println("- " + livro.getTitulo() + " por " + livro.getAutor() + " (distância: " + entry.getValue() + ")");
            });
    }


    public void removerLivro(String titulo) {
        if (cabecalhoLivrosCadastrados == null) return;

        if (cabecalhoLivrosCadastrados.getTitulo() == null ? titulo == null : cabecalhoLivrosCadastrados.getTitulo().equals(titulo)) {
            cabecalhoLivrosCadastrados = cabecalhoLivrosCadastrados.getProximo();  
            return;
        }

        Livro atual = cabecalhoLivrosCadastrados;
        while (atual.getProximo() != null) {  
            if (atual.getProximo().getTitulo() == null ? titulo == null : atual.getProximo().getTitulo().equals(titulo)) {
                atual.setProximo(atual.getProximo().getProximo()); 
                return;
            }
            atual = atual.getProximo();
        }
    }

    /*public void exibirLivros() {
        Livro atual = cabecalhoLivrosCadastrados;
        while (atual != null) {
            System.out.println(
                "Título: " + atual.getTitulo() +
                "\n Autor: " + atual.getAutor() +
                "\n Ano: " + atual.getAnoPublicacao()+
                "\n Gênero: " + atual.getGenero());

            historicoNavegacao.push(atual);

            atual = atual.getProximo();
        }
        System.out.println("");
        System.out.println(" Histórico atualizado, último livro acessado: " + historicoNavegacao.peek().getTitulo());
    }*/

    public void desfazerAcesso(){
        while(!historicoNavegacao.isEmpty()){
            Livro ultimoAcessado = historicoNavegacao.pop();
            System.out.println("Voltando para: " + ultimoAcessado.getTitulo());
        }  
        if(historicoNavegacao.isEmpty()){
            System.out.println("Nenhum histórico encontrado");
        }
    }

    public void exibirLivrosEmprestados(){
        if (filaLivros.isEmpty()){
            System.out.println("Nenhum livro encontrado.");
            return;
        }
        for (Livro livro : filaLivros){
            System.out.println("- " + livro.getTitulo() + "     |Autor: " +livro.getAutor());
        }
    }

    public void exibirLivrosHistorico(){
        if (historicoLivros.isEmpty()){
            System.out.println("Nenhum livro encontrado.");
            return;
        }
        for (Livro livro : historicoLivros){
            System.out.println("- " + livro.getTitulo() + "     |Autor: " +livro.getAutor());
        }
    }


    public static Map<Livro, Integer> djikstraSimples(HashMap<Livro, Set<Livro>> grafo, Livro origem) {
        Map<Livro, Integer> distancias = new HashMap<>();
        Queue<Livro> fila = new LinkedList<>();
    
        distancias.put(origem, 0);
        fila.add(origem);
    
        while (!fila.isEmpty()) {
            Livro atual = fila.poll();
            int distanciaAtual = distancias.get(atual);
    
            for (Livro vizinho : grafo.getOrDefault(atual, new HashSet<>())) {
                if (!distancias.containsKey(vizinho)) {
                    distancias.put(vizinho, distanciaAtual + 1);
                    fila.add(vizinho);
                }
            }
        }
    
        return distancias;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        for (Livro livro : listaLivros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                Map<Livro, Integer> distancias = djikstraSimples(grafoLivros, livro);
                System.out.println("Calculando distâncias a partir do livro: " + livro.getTitulo());
                System.out.println("");
    
                for (Map.Entry<Livro, Integer> entry : distancias.entrySet()) {
                    System.out.println("Livro: " + entry.getKey().getTitulo() + " | Distância: " + entry.getValue());
                }
    
                return livro;
            }
        }
        return null;
    }



    public static void main(String[] args) {
        BibliotecaVirtual biblioteca = new BibliotecaVirtual();
        biblioteca.adicionarLivro("A Revolução dos Bichos", "George Orwell", 1945, "Sátira");
        biblioteca.adicionarLivro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881, "Romance");
        biblioteca.adicionarLivro("Viagem ao Centro da Terra", "Jules Verne", 1864, "Aventura");
        biblioteca.adicionarLivro("Cem Anos de Solidão", "Gabriel García Márquez", 1967, "Romance");
        biblioteca.adicionarLivro("A Ilha do Tesouro", "Robert Louis Stevenson", 1883, "Aventura");
        biblioteca.adicionarLivro("Tokyo Ghoul", "Sui Ishida", 2011, "Drama");
        biblioteca.adicionarLivro("As Aventuras de Tom Sawyer", "Mark Twain", 1876, "Aventura");
        biblioteca.adicionarLivro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943, "Fábula");
        biblioteca.adicionarLivro("As Aventuras de Huckleberry Finn", "Mark Twain", 1884, "Aventura");
        biblioteca.adicionarLivro("A Menina que Roubava Livros", "Markus Zusak", 2005, "Drama");
        biblioteca.adicionarLivro("JOJO Steel Ball Run", "Hirohiko Araki", 1987, "Aventura");
        biblioteca.adicionarLivro("One Piece", "Eiichiro Oda", 1997, "Aventura");
        biblioteca.adicionarLivro("Haikyuu", "Haruichi Furudate", 2012, "Esporte");
        biblioteca.adicionarLivro("Kingdom", "Yasuhisa Hara", 2006, "Aventura");
        biblioteca.adicionarLivro("Hunter x Hunter", "Yoshihiro Togashi", 1998, "Aventura");
        biblioteca.adicionarLivro("Frieren e a Jornada para o Além", "Kanehito Yamada", 2020, "Aventura");
        
        biblioteca.adicionarLivroEmprestado("O Hobbit", "J.R.R. Tolkien", 1937, "Fantasia");
        biblioteca.adicionarLivroEmprestado("1984", "George Orwell", 1949, "Ficção científica");
        biblioteca.adicionarLivroEmprestado("Dom Casmurro", "Machado de Assis", 1899, "Romance");
        biblioteca.adicionarLivroEmprestado("O Código Da Vinci", "Dan Brown", 2003, "Suspense");
        biblioteca.adicionarLivroEmprestado("It: A Coisa", "Stephen King", 1986, "Terror");
        biblioteca.adicionarLivroEmprestado("Jogos Vorazes", "Suzanne Collins", 2008, "Ficção científica");

        biblioteca.adicionarLivroHistorico("Haikyuu", "Haruichi Furudate", 2012, "Esporte");
        biblioteca.adicionarLivroHistorico("Kingdom", "Yasuhisa Hara", 2006, "Aventura");
        biblioteca.adicionarLivroHistorico("Magi", "Shinobu Ohtaka", 2009, "Aventura");
        biblioteca.adicionarLivroHistorico("Fullmetal Alchemist", "Arakawa Hiromu", 2001, "Aventura");
        biblioteca.adicionarLivroHistorico("One Piece", "Eiichiro Oda", 1997, "Aventura");

        
        System.out.println("");
        System.out.println("Livros disponíveis para empréstimo na biblioteca(em ordem alfabética): "); 
        System.out.println("");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        
        //biblioteca.exibirLivros();
        biblioteca.exibirLivrosOrdenados();
        System.out.println("");
        biblioteca.retornarLivro();
        biblioteca.removerLivro("O Pequeno Príncipe");

        System.out.println("Lista de livros atualizada: "); 
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        biblioteca.exibirLivrosOrdenados();
        //biblioteca.exibirLivros();
        System.out.println("");
        
        biblioteca.desfazerAcesso();

        
        System.out.println("");
        System.out.println("Livros no seu histórico de empréstimos: ");
        System.out.println("");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        biblioteca.exibirLivrosHistorico();


        System.out.println("");
        System.out.println("Livros indisponíveis para empréstimo: "); 
        System.out.println("");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        biblioteca.exibirLivrosEmprestados();

        System.out.println("");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        biblioteca.indicarLivros();
        System.out.println("");
        
        biblioteca.buscarLivroPorTitulo("One Piece");
        System.out.println("");

        System.out.println("\n----------------------");
        System.out.println("Ordenando títulos dos livros cadastrados:");
        System.out.println("----------------------");
        
        String[] titulosLivros = biblioteca.listaLivros.stream()
            .map(Livro::getTitulo)
            .toArray(String[]::new);
        
        // Ordenação com Bubble Sort
        long inicioBubbleLivros = System.currentTimeMillis();
        String[] ordenadoBubble = GeradorDeNomesDeLivros.BubbleSort.ordenar(titulosLivros);
        long fimBubbleLivros = System.currentTimeMillis();
        
        System.out.println("\nTítulos ordenados com Bubble Sort:");
        System.out.println("");
        for (int i = 0; i < Math.min(10, ordenadoBubble.length); i++) {
            System.out.println("- " + ordenadoBubble[i]);
        }
        System.out.println("");
        System.out.println("Comparações: " + GeradorDeNomesDeLivros.BubbleSort.comparacoes);
        System.out.println("Tempo: " + (fimBubbleLivros - inicioBubbleLivros) + "ms");

        
        // Ordenação com Merge Sort
        long inicioMergeLivros = System.currentTimeMillis();
        String[] ordenadoMerge = GeradorDeNomesDeLivros.MergeSort.ordenar(titulosLivros);
        long fimMergeLivros = System.currentTimeMillis();
        
        System.out.println("\nTítulos ordenados com Merge Sort:");
        System.out.println("");
        for (int i = 0; i < Math.min(10, ordenadoMerge.length); i++) {
            System.out.println("- " + ordenadoMerge[i]);
        }
        
        System.out.println("");
        System.out.println("Comparações: " + GeradorDeNomesDeLivros.MergeSort.comparacoes);
        System.out.println("Tempo: " + (fimMergeLivros - inicioMergeLivros) + "ms");
        System.out.println("");
    }
}















//valorant >>> cs