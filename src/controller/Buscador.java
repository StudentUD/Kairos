package controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
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

    public static final String AMZ ="http://unsia.unal.edu.co/buscador/service/action.pub";
    public static final String BOG ="http://www.sia.unal.edu.co/buscador/service/action.pub";
    public static final String CAR = "http://siafrontera.sia.unal.edu.co/buscador/service/action.pub";
    public static final String MAN= "http://sia.manizales.unal.edu.co/buscador/service/action.pub";
    public static final String MED= "http://sia1.medellin.unal.edu.co:9401/buscador/service/action.pub";
    public static final String ORI= "http://orinoquia.sia.unal.edu.co/buscador/service/action.pub";
    public static final String PLM ="http://www.sia.palmira.unal.edu.co/buscador/service/action.pub";
    private static final int waitForJavaScriptTime = 5000;
    private HtmlPage currentPage;
    private static String buscadorURL;
    private WebClient webClient;
    private ArrayList<Asignatura> asignaturas;
    private boolean planCriteria;
    private boolean nameCriteria;
    private static ArrayList<Plan> planesPre;
    private static ArrayList<Plan> planesPos;
    private Plan activePlanFilter;

     public Buscador() throws IOException {
        planesPre = null;
        planesPos = null;
        nameCriteria = false;
        planCriteria = false;
        activePlanFilter=Plan.NULL_PLAN;
        webClient = new WebClient(BrowserVersion.FIREFOX_10);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        //WARNINGS REMOVAL
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        //   
        initBuscadorAddress();
    }   
    
    private void initBuscadorAddress(){
        switch(Kairos.getSede()){
            case 0:{
                    buscadorURL=AMZ;break;
                }
            case 1:{
                    buscadorURL=BOG;break;
                }
            case 2:{
                    buscadorURL=CAR;break;
                }
            case 3:{
                    buscadorURL=MAN;break;
                }
            case 4:{
                    buscadorURL=MED;break;
                }
            case 5:{
                    buscadorURL=ORI;break;
                }
            case 6:{
                    buscadorURL=PLM;break;
                }
            default:{
                buscadorURL=BOG;
            }
        }
    }
     
    public ArrayList<Plan> getPlansPreg() throws Exception {
        if (planesPre == null) {            
            initPlans();            
        }
        return planesPre;
    }

    public ArrayList<Plan> getPlansPos() throws Exception {
        if (planesPos == null) {            
            initPlans();            
        }
        return planesPos;
    }

    public final void terminate() {
        final List<WebWindow> windows = webClient.getWebWindows();
        for (final WebWindow wd : windows) {
            wd.getJobManager().removeAllJobs();
        }
        webClient.closeAllWindows();
    }

    public void initPlans() throws Exception {
        DomNodeList<HtmlElement> pre;
        DomNodeList<HtmlElement> pos;
        try {
            currentPage = webClient.getPage(buscadorURL);
        } catch (Exception ex) {
            currentPage = null;
            throw ex;
        }
        HtmlElement leftBlock = getLeftSearchBlock(currentPage);
        pre = leftBlock.getElementById("valor_criterio_planestudio_PRE").getElementsByTagName("option");
        pos = leftBlock.getElementById("valor_criterio_planestudio_POS").getElementsByTagName("option");

        planesPre = new ArrayList<>();
        planesPos = new ArrayList<>();        
        planesPre.add(Plan.NULL_PLAN);
        planesPos.add(Plan.NULL_PLAN);
        for (int i = 1; i < pre.size(); i++) {
            String dat = pre.get(i).getTextContent();
            String code = dat.substring(1, 5);
            String name = dat.substring(7);            
            planesPre.add(new Plan(code, name, Plan.PRE));
        }
        for (int i = 1; i < pos.size(); i++) {
            String dat = pos.get(i).getTextContent();
            String code = dat.substring(1, 5);
            String name = dat.substring(7);
            planesPos.add(new Plan(code, name, Plan.POS));
        }
    }   

    private static HtmlElement getRightBlock(HtmlPage page) {
        return page.getBody().getElementById("bodyContent").getElementById("Content").getHtmlElementsByTagName("tr").get(1).getElementsByTagName("td").get(1).getElementById("blockright");
    }

    private static HtmlElement getLeftSearchBlock(HtmlPage page) {
        return page.getBody().getElementById("bodyContent").getElementById("Content").getHtmlElementsByTagName("tr").get(1).getOneHtmlElementByAttribute("div", "class", "blockleft");
    }

    private HtmlPage busqueda(String busqueda) throws IOException {
        HtmlElement asigTextField;
        HtmlElement searchButton;
        if (currentPage == null) {
            currentPage = webClient.getPage(buscadorURL);
        }
        if (!nameCriteria) {            
            HtmlElement subjectSearchBlock = getLeftSearchBlock(currentPage).getElementById("bloque_buscador_asignatura").getElementById("bloque_crear_criterio_asignatura");
            asigTextField = subjectSearchBlock.getElementById("valor_criterio_asignatura");
            searchButton = subjectSearchBlock.getOneHtmlElementByAttribute("a", "class", "btn");
        } else {
            HtmlElement editAsigCritBlock = getLeftSearchBlock(currentPage).getElementById("bloque_criterios_aplicados").getElementById("bloque_criterios_aplicados_visible").getElementById("criterios_aplicados_asig");
            editAsigCritBlock.getOneHtmlElementByAttribute("a", "title", "Editar").click();
            HtmlElement blockEdit = editAsigCritBlock.getElementById("contenedor_ventana_edicion_asig").getElementById("bloque_crear_criterio_asignatura").getOneHtmlElementByAttribute("div", "class", "item");
            asigTextField = blockEdit.getElementById("bloque_crear_criterio_asignatura").getElementById("valor_criterio_asignatura");
            searchButton = blockEdit.getOneHtmlElementByAttribute("div", "class", "area_btn").getOneHtmlElementByAttribute("a", "class", "btn");
        }
        asigTextField.click();
        asigTextField.type(busqueda);
        nameCriteria = true;
        return searchButton.click();
    }

    //Este método solamente retorna objetos Asignatura con sus atributos de nombre, creditos y código
    //para obtener una asignatura con todos sus atributos use el método asignaturaCompleta()
    public ArrayList<Asignatura> buscarAsignaturas(String nombreAsignatura) throws IOException {
        asignaturas = new ArrayList();
        busqueda(nombreAsignatura);
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        HtmlElement nextPag = null;

        while (nextPag == null || !nextPag.hasAttribute("style")) {

            HtmlElement rightBlock = getRightBlock(currentPage);
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

                int creditos = 0;
                try {
                    creditos = Integer.parseInt(credString.substring(credString.lastIndexOf(" ") + 1));
                } catch (Exception ex) {
                }
                Asignatura a = new Asignatura(name, codigo);
                a.setCreditos(creditos);
                asignaturas.add(a);
            }
            nextPag.click();
            webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        }
        return asignaturas;
    }

    public Asignatura asignaturaCompleta(Asignatura asig) throws IOException {
        busqueda(asig.getCodigo());
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        HtmlElement contentr = getRightBlock(currentPage).getElementById("content_r");
        if (contentr.getChildElementCount() < 1) {
            return asig;
        }
        HtmlElement asigPanel = contentr.getOneHtmlElementByAttribute("div", "class", "blockasign");

        asigPanel.getOneHtmlElementByAttribute("a", "class", "lineasig").click();
        webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);

        DomNodeList<HtmlElement> table = asigPanel.getElementsByTagName("tr");
        for (int i = 2; i < table.size(); i++) {
            StringBuilder str = new StringBuilder();
            ////////////////////////
            int pos = 0;
            List<HtmlElement> dat = table.get(i).getHtmlElementsByTagName("td");
            for (HtmlElement element : dat) {
                if (pos >= 3 && pos < 10 && !element.asText().equals("")) {
                    String r = "null";
                    if (!element.getAttribute("title").equals("")) {
                        r = element.getAttribute("title");
                    }
                    str.append(element.asText()).append("/").append(r).append("	");
                } else {
                    str.append(element.asText()).append("	");
                }
                pos++;
            }
            ///////////////            
            String group = new String(str);
            try {
                Group grup = Kairos.parseGroup(group);
                asig.getGrupos().add(grup);
                new Button(grup, asig);
            } catch (Exception ex) {
                System.out.println("Group parse exception: " + group);
            }
            webClient.waitForBackgroundJavaScript(waitForJavaScriptTime);
        }
        return asig;
    }

    public void setPlanFilter(Plan plan)throws IOException {        
        if(plan==activePlanFilter){
            return;
        }
        if(plan==Plan.NULL_PLAN){
            removePlanFilter();
            return;
        }
        
        HtmlElement window;
        HtmlElement lvlBtn;
        HtmlElement comboBox;
        HtmlElement searchBtn;
        if (currentPage == null) {
            currentPage = webClient.getPage(buscadorURL);
        }
        if (planCriteria) {
            removePlanFilter();
        }
        
            HtmlElement leftBlock = getLeftSearchBlock(currentPage);
            leftBlock.getElementsByAttribute("div", "class", "section").get(1).getOneHtmlElementByAttribute("div", "class", "module").getElementById("btn_criterio_plan").click();
            window = leftBlock.getElementById("contenedor_ventana_base_plan").getOneHtmlElementByAttribute("div", "class", "item");
        
        switch (plan.getLevel()) {

            case Plan.PRE: {
                lvlBtn = window.getOneHtmlElementByAttribute("input", "value", "PRE");
                comboBox = window.getElementById("valor_criterio_planestudio_PRE");
                break;
            }
            case Plan.POS: {
                lvlBtn = window.getOneHtmlElementByAttribute("input", "value", "POS");
                comboBox = window.getElementById("valor_criterio_planestudio_POS");
                break;
            }
            default: {
                lvlBtn = window.getOneHtmlElementByAttribute("input", "value", "PRE");
                comboBox = window.getElementById("valor_criterio_planestudio_PRE");
                break;
            }
        }

        searchBtn = window.getOneHtmlElementByAttribute("div", "class", "area_btn").getOneHtmlElementByAttribute("a", "class", "btn");

        lvlBtn.click();
        comboBox.click();
        comboBox.getOneHtmlElementByAttribute("option", "value", plan.getCode()).click();
        searchBtn.click();

        planCriteria = true;
        activePlanFilter=plan;
    }

    public void removePlanFilter() throws IOException {
        if (planCriteria) {
            HtmlElement editPlanCritBlock = getLeftSearchBlock(currentPage).getElementById("bloque_criterios_aplicados").getElementById("bloque_criterios_aplicados_visible").getElementById("criterios_aplicados_plan");
            editPlanCritBlock.getOneHtmlElementByAttribute("a", "class", "btn_delete").click();
            planCriteria = false;
            activePlanFilter=Plan.NULL_PLAN;
        }
    }
}