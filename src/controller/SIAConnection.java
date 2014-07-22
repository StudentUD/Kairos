package controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class SIAConnection {

    private static final SIAWeb AMZ = new SIAWeb("unsia.unal.edu.co");
    private static final SIAWeb BOG =  new SIAWeb("sia.unal.edu.co","http://www.","http://www3.","http://www2.");
    private static final SIAWeb CAR =  new SIAWeb("siafrontera.sia.unal.edu.co");
    private static final SIAWeb MAN =  new SIAWeb("sia.manizales.unal.edu.co");
    private static final SIAWeb MED =  new SIAWeb("medellin.unal.edu.co:9401","http://sia.","http://sia1.","http://sia2.");
    private static final SIAWeb ORI =  new SIAWeb("orinoquia.sia.unal.edu.co");
    private static final SIAWeb PLM =  new SIAWeb("www.sia.palmira.unal.edu.co");
    private static SIAWeb urlGenerator = BOG;
    private static List<Plan> planesPre;
    private static List<Plan> planesPos;

    public SIAConnection() throws IOException {        
        initBuscadorAddress();
        planesPre = retrievePlans(true);
        planesPos = retrievePlans(false);        
    }

    private static String retrieveHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        StringBuilder responseBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(),
                        "ISO-8859-1"))) {
            String buffer;
            while ((buffer = in.readLine()) != null) {
                responseBuilder.append(buffer);
            }
        }

        return responseBuilder.toString();
    }

    private static List<Plan> retrievePlans(boolean undergraduate) throws IOException {
        String url=urlGenerator.getNextSIAAlternative();
        String html;
        
        html = retrieveHtml(url);
           

        // Regular expressions should not be used to parse HTML, but we're 
        // badass
        // http://stackoverflow.com/questions/1732348/regex-match-open-tags-except-xhtml-self-contained-tags
        // http://www.codinghorror.com/blog/2009/11/parsing-html-the-cthulhu-way.html

        // Arguably, we're "just" processing some strings.

        String startTag = undergraduate
                ? "<select id=\"valor_criterio_planestudio_PRE\">"
                : "<select style=\"display:none\" id=\"valor_criterio_planestudio_POS\">";

        String endTag = "</select>";

        int startIndex = html.indexOf(startTag);
        if (startIndex == -1) {
            throw new IllegalStateException("Couldn't find start tag");
        }

        int endIndex = html.indexOf(endTag, startIndex + 1);
        if (endIndex == -1) {
            throw new IllegalStateException("Couldn't find end tag");
        }

        String content = html.substring(startIndex + startTag.length(),
                endIndex + endTag.length());

        Pattern pattern =
                Pattern.compile("<option value=\"(\\d+)\">\\((\\d+)\\) ([^<]+)</option>");
        Matcher matcher = pattern.matcher(content);

        List<Plan> result = new ArrayList<>();
        result.add(Plan.NULL_PLAN);

        while (matcher.find()) {
            if (!matcher.group(1).equals(matcher.group(2))) {
                throw new IllegalStateException("Unexpected code value");
            }

            Plan plan = new Plan(matcher.group(1), matcher.group(3), undergraduate ? "PRE" : "POS");
            result.add(plan);            
        }
        return result;
    }

    public List<Plan> getPlansPreg() throws Exception {
        if (planesPre == null) {
            planesPre = retrievePlans(true);
        }
        return planesPre;
    }

    public List<Plan> getPlansPos() throws Exception {
        if (planesPos == null) {
            planesPos = retrievePlans(false);
        }
        return planesPos;
    }

    private void initBuscadorAddress() {
        switch (Kairos.getSede()) {
            case 0: {
                urlGenerator = AMZ;
                break;
            }
            case 1: {
                urlGenerator = BOG;
                break;
            }
            case 2: {
                urlGenerator = CAR;
                break;
            }
            case 3: {
                urlGenerator = MAN;
                break;
            }
            case 4: {
                urlGenerator = MED;
                break;
            }
            case 5: {
                urlGenerator = ORI;
                break;
            }
            case 6: {
                urlGenerator = PLM;
                break;
            }
            default: {
                urlGenerator = BOG;
            }
        }
    }
    
    public static int getAlternativeSize(){
        return urlGenerator.prefixAlternatives.length;
    }

    public static void setPreferedAlternative(int p){
        if(p<urlGenerator.prefixAlternatives.length){
            urlGenerator.setAlternative(p);        
        }
    }
    
    public ArrayList<Asignatura> buscarAsignaturas(String sub, Plan planFilter) throws MalformedURLException, IOException {
        String subjectMatch = cleanSubjectMatch(sub);
        ArrayList<Asignatura> subjects = new ArrayList<>();


        JSONObject requestContent = new JSONObject();
        requestContent.put("method", "buscador.obtenerAsignaturas");
        requestContent.put("params", new Object[]{subjectMatch, "PRE", "", planFilter.getLevel(), planFilter.getCode(), "", 1, Integer.MAX_VALUE});        
        JSONObject responseJSON = performSIARequest(requestContent);
        JSONArray subjectArray = responseJSON.getJSONObject("result").getJSONObject("asignaturas").getJSONArray("list");
        for (int i = 0; i < subjectArray.length(); ++i) {
            JSONObject subjectJSON = subjectArray.getJSONObject(i);
            int credits = subjectJSON.getInt("creditos");
            String type = subjectJSON.getString("tipologia");
            String name = subjectJSON.getString("nombre");
            name = name.charAt(0) + name.substring(1).toLowerCase();
            String code = subjectJSON.getString("codigo");
            Asignatura subject = new Asignatura(name, code);
            subject.setCreditos(credits);
            subjects.add(subject);
        }

        return subjects;
    }

    public Asignatura asignaturaCompleta(Asignatura asig) throws MalformedURLException, IOException, InterruptedException {
          
        Color[] aColor= asig.getColor();
        
        Button g = asig.getSelected();                        
        
        JSONArray groupArray = null;
        int count =1;
        while (groupArray == null || groupArray.length() < 1) {
            if(count++>=10){
                continue;
            }
            JSONObject requestContent = new JSONObject();
            requestContent.put("method", "buscador.obtenerGruposAsignaturas");
            requestContent.put("params", new Object[]{asig.getCodigo(), ""});
            JSONObject responseJSON = performSIARequest(requestContent);            
            groupArray = responseJSON.getJSONObject("result").getJSONArray("list");
            Thread.sleep(500);            
        }

        final String[][] SCHEDULE_KEYS = {
            {"horario_lunes", "aula_lunes"},
            {"horario_martes", "aula_martes"},
            {"horario_miercoles", "aula_miercoles"},
            {"horario_jueves", "aula_jueves"},
            {"horario_viernes", "aula_viernes"},
            {"horario_sabado", "aula_sabado"},
            {"horario_domingo", "aula_domingo"}
        };

        ArrayList<Group> grupos= new ArrayList<>();
        
        for (int i = 0; i < groupArray.length(); ++i) {

            JSONObject groupJSON = groupArray.getJSONObject(i);
            JSONArray restrictionsArray =
                    groupJSON.getJSONObject("planlimitacion").getJSONArray("list");

            boolean isAvailable = true;
            for (int j = 0; j < restrictionsArray.length(); j++) {
                String code = asig.getPlan().getCode();
                if (code.equals("")) {
                    isAvailable = true;
                    break;
                }
                String limitCode = restrictionsArray.getJSONObject(j).getString("plan");
                String limitType = restrictionsArray.getJSONObject(j).getString("tipo_limitacion");

                if (code.equals(limitCode)) {
                    if (limitType.equals("N")) {
                        isAvailable = false;
                        break;
                    }
                    if (limitType.equals("A")) {
                        isAvailable = true;
                        break;
                    }
                }
                if (j == restrictionsArray.length() - 1) {
                    if (limitType.equals("N")) {
                        isAvailable = true;
                        break;
                    }
                    if (limitType.equals("A")) {
                        isAvailable = false;
                        break;
                    }
                }
            }
            if (!isAvailable) {
                continue;
            }

            int available = groupJSON.getInt("cuposdisponibles");
            String code = groupJSON.getString("codigo");
            String teacher = groupJSON.getString("nombredocente");
            if (teacher.equals("  ")) {
                teacher = "[Información pendiente]";
            }
            int total = groupJSON.getInt("cupostotal");

            StringBuilder str = new StringBuilder(code).append("	").append(available).append("	").append(total).append("	");


            StringBuilder scheduleBuilder = new StringBuilder();
            for (String[] key : SCHEDULE_KEYS) {
                String value;
                if (!(value = groupJSON.getString(key[0])).equals("--")) {
                    scheduleBuilder.append(value);
                    scheduleBuilder.append("/");
                    if (!(value = groupJSON.getString(key[1])).equals("--")) {
                        scheduleBuilder.append(value);
                    }
                }
                scheduleBuilder.append("	");
            }

            str.append(scheduleBuilder).append(teacher);

            try {
                Group grup = Kairos.parseGroup(str.toString());
                grup.setAsignatura(asig);
                grup.setColors(aColor);
                grupos.add(grup);
                
            } catch (Exception ex) {
                System.out.println("Group parse exception: " + str.toString());
            }

        }
        asig.getButtons().clear();
        asig.setGrupos(grupos);
        for(Group grup: grupos){
            Button bot =new Button(grup, asig);
            
            if (g != null&&grup.getNumero().equals(g.getGrupo().getNumero())) {
                asig.setSelected(bot);
            }
        }                   
        return asig;
    }

    private static JSONObject performSIARequest(JSONObject requestContent)
            throws MalformedURLException, IOException {        
        String url=urlGenerator.getNextJSONAlternative();
        
        URL siaUrl = new URL(url);
        HttpURLConnection connection;        
      
        connection =(HttpURLConnection) siaUrl.openConnection();
        
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        try (OutputStreamWriter out =
                        new OutputStreamWriter(connection.getOutputStream(),"UTF-8")) {
            out.write(requestContent.toString());
        }

        StringBuilder responseBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(),"UTF-8"))) {
            String buffer;
            while ((buffer = in.readLine()) != null) {
                responseBuilder.append(buffer);
            }
        }

        return new JSONObject(responseBuilder.toString());
    }

    private static String cleanSubjectMatch(String sm) {
        String res = sm.toUpperCase();
        res = res.replaceAll("Á", "A");
        res = res.replaceAll("É", "E");
        res = res.replaceAll("Í", "I");
        res = res.replaceAll("Ó", "O");
        res = res.replaceAll("Ú", "U");
        return res;
    }
}


class SIAWeb{

    private final String SIArootURL;    
    protected final String[] prefixAlternatives;
    private int nextAlternative=0;   
    
    protected void setAlternative(int a){
        nextAlternative=a;
    }
        
    protected SIAWeb(String SIArootURL, String... prefixAlternatives) {
        this.SIArootURL = SIArootURL;        
        this.prefixAlternatives = prefixAlternatives;
    }   
    
    protected SIAWeb(String SIA_URL) {
        this(SIA_URL,"http://");
    }   
    
    protected String getNextSIAAlternative(){        
        return prefixAlternatives[(nextAlternative)%prefixAlternatives.length]+SIArootURL+"/buscador/service/action.pub";
    }
    
    protected String getNextJSONAlternative(){        
        return prefixAlternatives[(nextAlternative++)%prefixAlternatives.length]+SIArootURL+"/buscador/JSON-RPC";
    }    
}
