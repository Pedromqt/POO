public class Exercicio1 {
    public static void main(String[] argv) {
        String[] URLs = {
                "https://www.dei.uc.pt/poao/exames",
                "http://www.scs.org/index.html",
                "https://www.nato.int/events",
                "https://www.btu.de/",
                "https://www.dei.uc.pt/poao/exames",
                "http://www.eth.ch/index.html",
                "http://www.osu.edu/",
                "http://",
        };
        String[][] paises = {
                {"pt", "Portugal"},
                {"org", "EUA"},
                {"fr", "França"},
                {"uk", "Reino Unido"},
                {"de", "Alemanha"},
                {"edu", "EUA"}
        };
        String[][] contagem = criarEstrutura(paises); /* Cria array de strings com estrutura "Pais" : 0 */
        /* Array com a contagem de cada pais cujos links sao validos(verif) e junta as contagem quando existem designacoes diferentes do mesmo pais (alterarEstruturaPrefixosRepetidos)*/
        String[][] contagemSemRepetidos=alterarEstruturaPrefixosRepetidos(verif(contagem,URLs,paises));
        imprimirEstrutura(contagemSemRepetidos,contagemOutros(contagemSemRepetidos,contagemLinksValidos(URLs)));
    }
    private static int contagemLinksValidos(String[] URLs){
        int linksValidos=0;
        for (int i = 0; i < URLs.length; i++) {
            String[] UrlSeparadoPorPontos = URLs[i].split("\\."); /* Array de strings que separa cada URL por '.' */
            if (UrlSeparadoPorPontos[0].equals("https://www") || UrlSeparadoPorPontos[0].equals("http://www")) {
                linksValidos++;
            }
        }
        return linksValidos;
    }
    private static String[][] verif(String[][] contagem,String[] URLs,String[][] paises){
        for (int i = 0; i < URLs.length; i++) {
            String[] UrlSeparadoPorPontos = URLs[i].split("\\."); /* Array de strings que separa o URLs em cada ponto '.' */
            if (UrlSeparadoPorPontos[0].equals("https://www") || UrlSeparadoPorPontos[0].equals("http://www")) { /* VERIFICAR QUE É UM LINK VÁLIDO*/
                for (int j = 1; j < UrlSeparadoPorPontos.length; j++) { /* Percorrer o array de strings separadas por '.' */
                    String[] UrlSeparadoPorPontosBarras = UrlSeparadoPorPontos[j].split("/"); /* Separar cada string do array por '/' */
                    for (int k = 0; k < UrlSeparadoPorPontosBarras.length; k++) { /* Percorrer cada string separada por '/' que foi anteriormente separa por '.' de modo a conseguirmos separar o url por pontos e barras para obtermos por exemplo "pt" ou outra designacao */
                        for (int l = 0; l < paises.length; l++) {
                            if (paises[l][0].equals(UrlSeparadoPorPontosBarras[k])){
                                contagem[l][1] = String.valueOf(Integer.parseInt(contagem[l][1]) + 1);
                            }
                        }
                    }
                }
            }
        }
        return contagem;
    }
    private static String[][] criarEstrutura(String[][] paises){
        String[][] contagem = new String[paises.length][2];
        for (int i = 0; i < paises.length; i++) {
            contagem[i][0] = paises[i][1];
            contagem[i][1] = "0";
        }
        return contagem;
    }
    private static String[][] alterarEstruturaPrefixosRepetidos(String[][] contagem){
        /* Quando existem prefixos repetidos, iria aparecer por exemplo EUA 2 vezes o que nao e o desejado */
        for(int i=0;i<contagem.length;i++){
            for(int k=i+1;k<contagem.length;k++){
                if(contagem[i][0].equals(contagem[k][0])){
                    /* Quando encontra um repetido junta as contagens */
                    contagem[i][1]=String.valueOf(Integer.parseInt(contagem[i][1])+Integer.parseInt(contagem[k][1]));
                    contagem[k][1]="0";
                }
            }
        }
        return contagem;
    }
    private static int contagemOutros(String[][] contagem,int linksValidos){
        /* Conta os links(validos) com designacoes de outros paises nao existentes no array "paises" */
        int outros=0;
        for(int i=0;i < contagem.length;i++){
            outros+=Integer.parseInt(contagem[i][1]);
        }
        outros=linksValidos-outros;
        return outros;
    }
    private static void imprimirEstrutura(String[][] contagem,int outros){
        /* Imprimir a contagem feita, apenas imprime quando diferente de 0 */
        for (int i = 0; i < contagem.length; i++){
            if(Integer.parseInt(contagem[i][1])!=0){
                System.out.println(contagem[i][0] + " : " + contagem[i][1]);
            }
        }
        if(outros>0){
            System.out.println("Outros(s) : " + outros);
        }
    }
}