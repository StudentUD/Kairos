package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Kairos {

    public static final int MAX_CREDITS = 20;
    private static final FileNameExtensionFilter filter = new FileNameExtensionFilter("Kairos session file (*.KSF)", "KSF");
    private static final FileNameExtensionFilter filterX = new FileNameExtensionFilter("Libro de excel (*.xls)", "xls");
    private static Date sesionDate = null;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy_HH-mm");
    private static File dataFile = new File("none");
    private static boolean savedSesion = false;
    public static final String VERSION = "2.0";
    private static LinkedHashSet<Asignatura> subjects = new LinkedHashSet<>();

    public static boolean isSavedSesion() {
        return savedSesion;
    }

    public static void setSavedSesion(boolean savedSesion) {
        Kairos.savedSesion = savedSesion;
    }

    public static Date getSesionDate() {
        return sesionDate;
    }

    public static void setSesionDate(Date sesionDate) {
        Kairos.sesionDate = sesionDate;
    }

    public static void setDataFile(File dataFile) {
        Kairos.dataFile = dataFile;
    }

    public static LinkedHashSet<Asignatura> getSubjects() {
        return subjects;
    }

    private static ArrayList<String> getData(File file) throws Exception {
        ArrayList<String> data = new ArrayList<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        reader.close();
        return data;
    }

    public static Group parseGroup(String data) throws Exception {
        String[] dat = data.split("	");
        Group group = new Group(dat[0], dat[10], Integer.parseInt(dat[1]), Integer.parseInt(dat[2]));
        group.setText(data);
        for (int i = 1; i < 8; i++) {
            int hora;
            int horaFin;
            if (!dat[i + 2].equals("")) {
                String[] infoBloque = dat[i + 2].split("/");
                String[] bloques = infoBloque[0].split(" ");
                String[] salones = null;
                boolean clsRoom = false;
                if (infoBloque.length > 1) {
                    salones = infoBloque[1].split(" ");
                    if (salones.length > bloques.length) {
                        int j = 0;
                        String[] t = new String[bloques.length];
                        for (int k = 0; k < salones.length && j < t.length; k++) {
                            if (salones[k].length() < 2) {
                                t[j - 1] += " " + salones[k];
                            } else {
                                t[j++] = salones[k];
                            }
                        }
                        salones = t;
                    }
                    clsRoom = true;
                }
                for (int j = 0; j < bloques.length; j++) {
                    String[] h = bloques[j].split("-");
                    hora = Integer.parseInt(h[0]);
                    horaFin = Integer.parseInt(h[h.length - 1]);
                    if (clsRoom) {
                        group.getHorario().add(new Block(hora, horaFin, i, salones[j]));
                    } else {
                        group.getHorario().add(new Block(hora, horaFin, i));
                    }
                }
            }
        }
        return group;
    }

    private static void actualizarEstado() throws Exception {
        LinkedHashSet<Asignatura> sub = new LinkedHashSet<>();
        ArrayList<String> datos = getData(dataFile);
        Asignatura materia = null;

        String fecha = datos.get(0);
        datos.remove(0);
        try {
            sesionDate = sdf.parse(fecha);
        } catch (ParseException ex) {
            sesionDate = null;
        }
        for (String dat : datos) {
            if (!(dat.length() < 2)) {
                if (dat.charAt(0) == '*') {

                    String[] asig = dat.substring(1).split("-");
                    if (asig.length > 1) {
                        materia = new Asignatura(asig[0], asig[1]);
                    } else {
                        materia = new Asignatura(asig[0]);
                    }
                    sub.add(materia);
                } else {
                    Group grupo = parseGroup(dat);
                    materia.getGrupos().add(grupo);
                }
            }
        }
        subjects = sub;
    }

    public static void guardarSesion() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Guardar sesión");
        String date;
        if (sesionDate != null) {
            date = sdf.format(sesionDate);
        } else {
            date = "NoDate";
        }

        chooser.setSelectedFile(new File(date + "_KairosSession.KSF"));
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(archivo);
                writer.write(date + System.getProperty("line.separator"));
                for (Asignatura asig : subjects) {
                    writer.write("*" + asig.getNombre() + "-" + asig.getCodigo() + System.getProperty("line.separator"));
                    for (Group grupo : asig.getGrupos()) {
                        writer.write(grupo.getAsText() + System.getProperty("line.separator"));
                    }
                }
                writer.close();
                savedSesion = true;
            } catch (IOException ex) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "ERROR: Imposible guardar el archivo " + dataFile.getName(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    public static void saveExcel(javax.swing.JTable table) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Guardar horario");
        chooser.setSelectedFile(new File("Schedule.xls"));
        chooser.setFileFilter(filterX);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(archivo);
                writer.close();
                WritableWorkbook workbook = Workbook.createWorkbook(archivo);
                workbook.createSheet("schedule", 0);
                WritableSheet sheet = workbook.getSheet("schedule");
                writeScheduleTemplate(sheet);
                for (Asignatura asig : subjects) {
                    for (Button boton : asig.getButtons()) {
                        if (boton.isSelected()) {
                            Group grupo = boton.getGrupo();
                            for (Block bloque : grupo.getHorario()) {
                                jxl.write.Label cel;
                                for (int j = bloque.getHora(); j < bloque.getHoraFin(); j++) {
                                    cel = new jxl.write.Label(bloque.getDia(), j - 5, bloque.getSalon() + ": " + boton.getMateria().getNombre());
                                    sheet.addCell(cel);
                                }                                
                            }
                        }
                    }
                }
                workbook.write();
                workbook.close();
            } catch (Exception ex) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "ERROR: Imposible guardar el archivo", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    private static void writeScheduleTemplate(WritableSheet sheet) throws Exception {
        for (int i = 0; i < 16; i++) {
            jxl.write.Label cel = new jxl.write.Label(0, i + 1, (6 + i) + ":00-" + (7 + i) + ":00");
            sheet.addCell(cel);
        }
        jxl.write.Label cel = new jxl.write.Label(0, 0, "Hora");
        sheet.addCell(cel);
        cel = new jxl.write.Label(0, 0, "Hora");
        sheet.addCell(cel);
        cel = new jxl.write.Label(1, 0, "Lunes");
        sheet.addCell(cel);
        cel = new jxl.write.Label(2, 0, "Martes");
        sheet.addCell(cel);
        cel = new jxl.write.Label(3, 0, "Miércoles");
        sheet.addCell(cel);
        cel = new jxl.write.Label(4, 0, "Jueves");
        sheet.addCell(cel);
        cel = new jxl.write.Label(5, 0, "Viernes");
        sheet.addCell(cel);
        cel = new jxl.write.Label(6, 0, "Sábado");
        sheet.addCell(cel);
        cel = new jxl.write.Label(7, 0, "Domingo");
        sheet.addCell(cel);
    }

    public static boolean showConfirmDialog() {
        boolean cont = true;
        if (!savedSesion) {
            JFrame frame = new JFrame();
            Object[] options = {"Si", "No", "Cancelar"};
            int n = JOptionPane.showOptionDialog(frame,
                    "¿Desea guardar la sesión?",
                    "Kairos",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
            if (n == 0) {
                guardarSesion();
            } else if (n == 2) {
                cont = false;
            }
        }
        return cont;
    }

    public static boolean cargarSesion() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Cargar sesión");

        chooser.setFileFilter(filter);

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            setDataFile(archivo);
            try {
                actualizarEstado();
                savedSesion = true;
            } catch (Exception ex) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "ERROR: El archivo " + dataFile.getName() + " no tiene un formato válido", "Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        return false;
    }
}
