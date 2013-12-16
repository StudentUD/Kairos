package controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Buscador {

    private final int waitForJavaScriptTime = 5000;
    private String criterioAsignatura;
    private static String BUSCADOR_URL = "http://www.sia.unal.edu.co/buscador/service/action.pub";
    private WebClient webClient;
    private ArrayList<Asignatura> asignaturas;

    public Buscador() {
        criterioAsignatura = "none";
        webClient = new WebClient(BrowserVersion.FIREFOX_10);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        //WARNINGS REMOVAL
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        //
    }

    private void terminate() {
        webClient.closeAllWindows();
    }

    private HtmlElement getRightBlock(HtmlPage page){
        return page.getBody().getElementById("bodyContent").getElementById("Content").getHtmlElementsByTagName("tr").get(1).getElementsByTagName("td").get(1).getElementById("blockright");
    }
    private HtmlElement getLeftSearchBlock(HtmlPage page){
        return page.getBody().getElementById("bodyContent").getElementById("Content").getHtmlElementsByTagName("tr").get(1).getOneHtmlElementByAttribute("div", "class", "blockleft");
    }
    
    private HtmlPage busqueda(String busqueda) throws IOException {
        HtmlPage mainPage = webClient.getPage(BUSCADOR_URL);
        HtmlElement subjectSearchBlock= getLeftSearchBlock(mainPage).getElementById("bloque_buscador_asignatura").getElementById("bloque_crear_criterio_asignatura");
        HtmlElement asigTextField = subjectSearchBlock.getElementById("valor_criterio_asignatura");
        asigTextField.click();
        asigTextField.type(busqueda);
        HtmlElement searchButton = subjectSearchBlock.getOneHtmlElementByAttribute("a", "class", "btn");

        return searchButton.click();
    }

    //Este método solamente retorna objetos Asignatura con sus atributos de nombre, creditos y código
    //para obtener una asignatura con todos sus atributos use el método asignaturaCompleta()
    public ArrayList<Asignatura> buscarAsignaturas(String nombreAsignatura) throws IOException {
        criterioAsignatura = nombreAsignatura;
        asignaturas = new ArrayList();

        HtmlPage searchResults = busqueda(criterioAsignatura);
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        HtmlElement nextPag = null;

        while (nextPag == null || !nextPag.hasAttribute("style")) {

            HtmlElement rightBlock= getRightBlock(searchResults);
            List<HtmlElement> paginas = rightBlock.getElementById("paginacion1").getHtmlElementsByTagName("a");

            if (paginas.size() <= 0) {
                break;
            }
            nextPag = paginas.get(paginas.size() - 1);

            HtmlElement content = rightBlock.getElementById("content_r");
            List<HtmlElement> asigElems = content.getElementsByAttribute("a", "class", "lineasig");

            for (HtmlElement e : asigElems) {

                String title = e.getAttribute("title").replaceAll("-", " ");
                String name = title.charAt(0) + title.substring(1).toLowerCase();

                String codigo = e.getElementsByAttribute("div", "class", "bar00").get(0).getTextContent();
                String credString = e.getElementsByAttribute("div", "class", "bar02").get(0).getTextContent();

                int creditos=0;
                try{
                creditos = Integer.parseInt(credString.substring(credString.lastIndexOf(" ") + 1));}
                catch(Exception ex){}
                Asignatura a = new Asignatura(name, codigo);
                a.setCreditos(creditos);
                asignaturas.add(a);

            }
            searchResults = nextPag.click();
            webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        }
        terminate();
        return asignaturas;
    }

    public Asignatura asignaturaCompleta(Asignatura asig) throws IOException {
        HtmlPage searchResults = busqueda(asig.getCodigo());
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        HtmlElement asigPanel = getRightBlock(searchResults).getElementById("content_r").getOneHtmlElementByAttribute("a", "class", "lineasig");
        HtmlPage newPage = asigPanel.click();
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        DomNodeList<HtmlElement> table = getRightBlock(newPage).getElementById("content_r").getOneHtmlElementByAttribute("div", "class", "blockasign").getElementsByTagName("tr");

        for (int i = 2; i < table.size(); i++) {
             StringBuilder str= new StringBuilder();
            ////////////////////////
            int pos=0;
            List<HtmlElement> dat = table.get(i).getHtmlElementsByTagName("td");
            for (HtmlElement element : dat) {
                if (pos >= 3 && pos < 10 && !element.asText().equals("") ) {
                    String r= "null";
                    if(!element.getAttribute("title").equals("")){
                        r=element.getAttribute("title");
                    }       
                    str.append(element.asText()).append("/").append(r).append("	");
                } else {
                    str.append(element.asText()).append("	");
                }
                pos++;
            }     
            ///////////////            
            String group= new String(str);                   
            try {
                Group grup= Kairos.parseGroup(group);
                asig.getGrupos().add(grup);
                new Button(grup,asig);
            } catch (Exception ex) {
                System.out.println("Group parse error");
            }
        }
        terminate();
        return asig;
    }
}