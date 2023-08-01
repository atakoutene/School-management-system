/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitaires.reports;

import Control.CRUDSemester;
import Control.ManageProgramCourses;
import Control.Tools;
import Model.Course;
import Model.Program;
import Model.SchoolFaculty;
import Model.SchoolLevel;
import Model.Semester;
import Model.Student;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author Berenger
 */
public class FinalResultsReport {

    private static Set<Course> courses;
    private static Map<Student, Set<Course>> elements;

    private static final String FIELD_NAME = "aField";
    private static final String PARAMETER_DATA = "tableData";

    private static final String CURRENT_PAGE_NUMBER = "pageNumber";
    private static final String TOTAL_PAGE_NUMBER = "pageTotal";

    private static final int BORDER_WIDTH = 10;
    private static final int NAME_COLUMN_WIDTH = 170;
    private static final int REG_NUM_COLUMN_WIDTH = 70;
    private static final int DATA_COLUMN_WIDTH = 92;

    /* User home directory location */
    static String userHomeDirectory = System.getProperty("user.home");
    /* Output file location */
    static String outputFile = userHomeDirectory + File.separatorChar + "JasperTableExample.pdf";

//     public static Map<Student, Set<Course>> getStudentCoursesSem(int idProgram, int idSemester, String level)
    public static void main(String[] args) throws JRException, FileNotFoundException, IOException {

        displayReport(23);

        /* Map to hold Jasper report Parameters */
//        Map<String, Object> parameters = fillParameters(courses, elements);
//
//        System.out.println("Working Directory = "
//                + System.getProperty("user.dir"));
//
//        /* Using compiled version(.jasper) of Jasper report to generate PDF */
//        JasperPrint jasperPrint = JasperFillManager.
//                fillReport("src/Resource/reports/reportReleve.jasper",
//                        parameters, new JREmptyDataSource());
//        jasperPrint.setPageWidth(NAME_COLUMN_WIDTH + REG_NUM_COLUMN_WIDTH + 
//                DATA_COLUMN_WIDTH * courses.size() + BORDER_WIDTH);
//
//        JasperViewer.viewReport(jasperPrint, false);
        System.out.println("Utilitaires.reports.FinalResultsReport.main()");
    }

    public static void displayReport(int semId) throws JRException {

        Semester s = CRUDSemester.findSemById(semId);
        String accY = Tools.getAcademicYear(s);
//        courses = ManageProgramCourses.loadProgramCourses(6, s.getSYear(), s.getName(), 5);
////        elements = ManageProgramCourses.getStudentCoursesSem(6, semId, 5);
//        courses = ManageProgramCourses.loadProgramCourses(1, s.getSYear(), s.getName(), 4);
//        elements = ManageProgramCourses.getStudentCoursesSem(1, semId, 4);
        String deptEcon = "DEPARTMENT OF ECONOMICS";
        String deptComp = "DEPARTMENT OF COMPUTING AND SOFTWARE ENGINEERING";
        String deptTel = "DEPARTMENT OF ELECTRICAL AND TELECOMMUNICATION ENGINEERING";
        String deptMecha = "DEPARTMENT OF MECHANICAL ENGINEERING ENGINEERING";
        String deptPrepaEc = "PREPARATORY SEMESTER ECONOMICS";
        String deptPrepaIng = "PREPARATORY SEMESTER ENGINEERING";
        InputStream stream = null;
        try {
            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            FinalResultsReport obj = new FinalResultsReport();
            stream = obj.getClass().getResource("/Resource/reports/reportReleve.jasper").openStream();
            BufferedImage image = ImageIO.read(obj.getClass().getResource("/Resource/images/logopkf.PNG"));
            List<JasperPrint> jasperPrints = new ArrayList<>();
            //Generating reports per level for undergraduate students
            //1. Generate report for preparatory semester
            /*1.1 For Economics*/
            SchoolLevel level = Tools.getSchoolLevel(1);
            String facultyIng = "FACULTY OF SCIENCES AND TECHNOLOGY";
            String facultyEcon = "FACULTY OF ECONOMICS AND MANAGEMENT SCIENCES";
            JasperPrint jasperPrint = generateRecap(s, 5, accY, facultyEcon, deptPrepaEc, image, level);
            if (jasperPrint != null) {
                jasperPrints.add(jasperPrint);
            }
            /*1.1 For Engineers*/
            jasperPrint = generateRecap(s, 1, accY, facultyIng, deptPrepaIng, image, level);
            if (jasperPrint != null) {
                jasperPrints.add(jasperPrint);
            }
            for (int i = 2; i <= 7; i++) {
                level = Tools.getSchoolLevel(i);
                jasperPrint = generateRecap(s, 5, accY, facultyEcon, deptEcon, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                jasperPrint = generateRecap(s, 1, accY, facultyIng, deptComp, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                jasperPrint = generateRecap(s, 2, accY, facultyIng, deptComp, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                jasperPrint = generateRecap(s, 3, accY, facultyIng, deptTel, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                jasperPrint = generateRecap(s, 4, accY, facultyIng, deptMecha, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                jasperPrint = generateRecap(s, 6, accY, facultyIng, deptTel, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }

            }
            int totPageNumber = 0;
            for (JasperPrint jp : jasperPrints) {
                totPageNumber += jp.getPages().size();
            }
            //Second loop all reports to replace our markers with current and total number
            int currentPage = 1;
            for (JasperPrint jp : jasperPrints) {
                List<JRPrintPage> pages = jp.getPages();
                //Loop all pages of report
                for (JRPrintPage jpp : pages) {
                    List<JRPrintElement> elements = jpp.getElements();
                    //Loop all elements on page
                    for (JRPrintElement jpe : elements) {
                        //Check if text element
                        if (jpe instanceof JRPrintText) {
                            JRPrintText jpt = (JRPrintText) jpe;
                            //Check if current page marker
                            if (CURRENT_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText("Page " + currentPage + " of"); //Replace marker
                                continue;
                            }
                            //Check if totale page marker
                            if (TOTAL_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText(" " + totPageNumber); //Replace marker
                            }
                        }
                    }
                    currentPage++;
                }
            }

            JRPdfExporter exporter = new JRPdfExporter();
            //Create new FileOutputStream or you can use Http Servlet Response.getOutputStream() to get Servlet output stream
            // Or if you want bytes create ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes = out.toByteArray();
            //Use stamper to set viewer prederence 
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(bytes));

            PdfStamper pdfStamper;
            try {
                pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("jury.pdf"));
                pdfStamper.getWriter().setViewerPreferences(PdfWriter.PageModeUseOutlines);
                pdfStamper.close();
            } catch (DocumentException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            pdfReader.close();
            Tools.openPDFFile("jury.pdf");
            //JasperViewer.viewReport(jasperPrint, false);
        } catch (IOException ex) {
            Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static JasperPrint generateRecap(Semester s, int progId, String accY, String faculty, String department,
            BufferedImage image, SchoolLevel sl) throws JRException {

        Set<Course> courses = new HashSet<>();
        Map<Student, Set<Course>> elements = new HashMap<>();
        if (department.equals("PREPARATORY SEMESTER ENGINEERING")) {
            courses = ManageProgramCourses.loadProgramCoursesPIng(s.getSYear(), s.getName(), s.getId());
            elements = ManageProgramCourses.getStudentCoursesSemIng(s.getId(), sl.getId());
            for (Map.Entry entry : elements.entrySet()) {
                Iterator<Course> it = ((Set<Course>) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!courses.contains(it.next())) {
                        it.remove();
                    }
                }
            }

        } else {
            courses = ManageProgramCourses.loadProgramCourses(progId, s.getSYear(), s.getName(), sl.getId(), s.getId());
            elements = ManageProgramCourses.getStudentCoursesSem(progId, s.getId(), sl.getId());
            for (Course c : courses) {
                ObservableList<Student> studentList = Tools.getStudentForRel(s.getId(), c.getId_co(), c.getId(), progId);
                for (Student st : studentList) {
                    Course cc = (Course) c.clone();
                    if (elements.containsKey(st)) {
                        if (!elements.get(st).contains(cc)) {

                            cc.setLetterGradeForReport(Tools.letterGrade(st.getId(), cc.getId_co(), s.getId()).getLetterGrade());
                            cc.setGradeForReport(Tools.letterGrade(st.getId(), cc.getId_co(), s.getId()).getGrade());
                            elements.get(st).add(cc);
                        }
                    } else {
                        Set<Course> scs = new HashSet<>();
                        cc.setLetterGradeForReport(Tools.letterGrade(st.getId(), cc.getId_co(), s.getId()).getLetterGrade());
                        cc.setGradeForReport(Tools.letterGrade(st.getId(), cc.getId_co(), s.getId()).getGrade());
                        scs.add(cc);
                        elements.put(st, scs);
                    }
                }
            }
        }
        if (elements.isEmpty()) {
            return null;
        }
        InputStream stream = null;
        try {

            System.out.println("Working Directory = "
                    + System.getProperty("user.dir"));
            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            FinalResultsReport obj = new FinalResultsReport();
            stream = obj.getClass().getResource("/Resource/reports/reportReleve.jasper").openStream();
            /* Map to hold Jasper report Parameters */

            Map<Student, Set<Course>> treeMap = new TreeMap<>(elements);
            Map<String, Object> parameters = fillParameters(courses, treeMap, s, image, accY, faculty, department, sl.getName());
            JasperPrint jasperPrint = JasperFillManager.
                    fillReport(stream, parameters, new JREmptyDataSource());
            if (courses.size() > 8) {
                jasperPrint.setPageWidth(NAME_COLUMN_WIDTH + REG_NUM_COLUMN_WIDTH
                        + DATA_COLUMN_WIDTH * courses.size() + BORDER_WIDTH);
            }

            return jasperPrint;

        } catch (IOException ex) {
            Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private static Map<String, Object> fillParameters(Set<Course> courses, Map<Student, Set<Course>> elements, Semester s, BufferedImage image,
            String accY, String faculty, String department, String level) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        String prmName = "";
        int i = 1;
        for (Course course : courses) {
//            prmCourse1Name 
            prmName = "prmCourse" + i;
            parameters.put(prmName + "Name", course.getCode() + " \n\n" + course.getTitle());
            parameters.put(prmName + "ReqGrade", course.getPassingGradeForReport());
            parameters.put(prmName + "Credits", course.getNbCreditsForReport());
            i++;
        }

        /* List to hold Items */
        List<ResultsData> listItems = computeResultsDatas(courses, elements);

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
        parameters.put("ItemDataSource", itemsJRBean);
        parameters.put("imagesDir", image);
        parameters.put("reportTitle", "FINAL RESULTS - UNDERGRADUATE");
        String sem = "Semester ";
        switch (s.getName()) {
            case "Spring":
                sem += 2;
                break;
            case "Fall":
                sem += 1;
                break;
        }
        parameters.put("semester", sem);
        parameters.put("semesterName", s.getName() + " " + s.getSYear());
        parameters.put("accademicY", accY);
        parameters.put("faculty", faculty);
        parameters.put("department", department);
        parameters.put("level", level);
        return parameters;
    }

    private static List<ResultsData> computeResultsDatas(Set<Course> courses, Map<Student, Set<Course>> elements) {
        List<ResultsData> resultsDatas = new ArrayList<ResultsData>();
        Iterator it = elements.entrySet().iterator();
        int ordre = 1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Student s = (Student) pair.getKey();
            Set<Course> sCourses = (Set<Course>) pair.getValue();
            resultsDatas.add(getStudentResultDatas(s, sCourses, courses, ordre));
            ordre++;
//            System.out.print(s.getName() + "   " + s.getStuID() + "  ");
//            for(Course c: courses){
//                System.out.print(c.getGradeForReport() + "  " + c.getLetterGradeForReport() + " ");
//            }
//            System.out.println("");
        }

        return resultsDatas;
    }

    private static ResultsData getStudentResultDatas(Student s, Set<Course> sCourses,
            Set<Course> courses, int ordre) {
        ResultsData result = new ResultsData();

        result.setNumOrdre(String.valueOf(ordre));
        result.setNameNick(s.getName());
        result.setRegNum(s.getStuID());
        int i = 1;
        String score, grade;
        for (Course course : courses) {
            score = "";
            grade = "";
            for (Course c : sCourses) {
                if (c.getCode().equals(course.getCode())) {
                    score = String.valueOf(c.getGradeForReport());
                    grade = String.valueOf(c.getLetterGradeForReport());
                    break;
                }
            }
            ScannerHelper.setFieldValue(result, "score" + i, score);
            ScannerHelper.setFieldValue(result, "grade" + i, grade);
            i++;
        }

        return result;
    }

    public static void displayReport(int semId, SchoolFaculty faculty) throws JRException {
        List<JasperPrint> jasperPrints = new ArrayList<>();
        InputStream stream = null;
        try {
            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            FinalResultsReport obj = new FinalResultsReport();
            stream = obj.getClass().getResource("/Resource/reports/reportReleve.jasper").openStream();
            BufferedImage image = ImageIO.read(obj.getClass().getResource("/Resource/images/logopkf.PNG"));

            if (faculty.getTitle().equals("Sciences and Technology")) {
                String facultyIng = "FACULTY OF SCIENCES AND TECHNOLOGY";
                Semester s = CRUDSemester.findSemById(semId);
                String accY = Tools.getAcademicYear(s);
                String deptComp = "DEPARTMENT OF COMPUTING AND SOFTWARE ENGINEERING";
                String deptTel = "DEPARTMENT OF ELECTRICAL AND TELECOMMUNICATION ENGINEERING";
                String deptMecha = "DEPARTMENT OF MECHANICAL ENGINEERING ENGINEERING";
                String deptPrepaIng = "PREPARATORY SEMESTER ENGINEERING";
                /*1.1 For Engineers*/
                SchoolLevel level = Tools.getSchoolLevel(1);
                JasperPrint jasperPrint = generateRecap(s, 1, accY, facultyIng, deptPrepaIng, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                for (int i = 2; i <= 7; i++) {
                    level = Tools.getSchoolLevel(i);
                    jasperPrint = generateRecap(s, 1, accY, facultyIng, deptComp, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                    jasperPrint = generateRecap(s, 2, accY, facultyIng, deptComp, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                    jasperPrint = generateRecap(s, 3, accY, facultyIng, deptTel, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                    jasperPrint = generateRecap(s, 4, accY, facultyIng, deptMecha, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                    jasperPrint = generateRecap(s, 6, accY, facultyIng, deptTel, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                }
            } else if (faculty.getTitle().equals("Economics and Management")) {
                String facultyEcon = "FACULTY OF ECONOMICS AND MANAGEMENT";
                Semester s = CRUDSemester.findSemById(semId);
                String accY = Tools.getAcademicYear(s);
                String deptEcon = "DEPARTMENT OF ECONOMICS";
                String deptPrepaEc = "PREPARATORY SEMESTER ECONOMICS";
                SchoolLevel level = Tools.getSchoolLevel(1);
                JasperPrint jasperPrint = generateRecap(s, 5, accY, facultyEcon, deptPrepaEc, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                for (int i = 2; i <= 7; i++) {
                    level = Tools.getSchoolLevel(i);
                    jasperPrint = generateRecap(s, 5, accY, facultyEcon, deptEcon, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                }
            } else {
                return;
            }

            int totPageNumber = 0;
            for (JasperPrint jp : jasperPrints) {
                totPageNumber += jp.getPages().size();
            }
            //Second loop all reports to replace our markers with current and total number
            int currentPage = 1;
            for (JasperPrint jp : jasperPrints) {
                List<JRPrintPage> pages = jp.getPages();
                //Loop all pages of report
                for (JRPrintPage jpp : pages) {
                    List<JRPrintElement> elements = jpp.getElements();
                    //Loop all elements on page
                    for (JRPrintElement jpe : elements) {
                        //Check if text element
                        if (jpe instanceof JRPrintText) {
                            JRPrintText jpt = (JRPrintText) jpe;
                            //Check if current page marker
                            if (CURRENT_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText("Page " + currentPage + " of"); //Replace marker
                                continue;
                            }
                            //Check if totale page marker
                            if (TOTAL_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText(" " + totPageNumber); //Replace marker
                            }
                        }
                    }
                    currentPage++;
                }
            }

            JRPdfExporter exporter = new JRPdfExporter();
            //Create new FileOutputStream or you can use Http Servlet Response.getOutputStream() to get Servlet output stream
            // Or if you want bytes create ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes = out.toByteArray();
            //Use stamper to set viewer prederence 
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(bytes));

            PdfStamper pdfStamper;
            try {
                pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("jury.pdf"));
                pdfStamper.getWriter().setViewerPreferences(PdfWriter.PageModeUseOutlines);
                pdfStamper.close();
            } catch (DocumentException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            pdfReader.close();
            Tools.openPDFFile("jury.pdf");
        } catch (IOException ex) {
            Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void displayReport(int semId, SchoolFaculty faculty, Program program) throws JRException {
        List<JasperPrint> jasperPrints = new ArrayList<>();
        InputStream stream = null;
        try {
            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            FinalResultsReport obj = new FinalResultsReport();
            stream = obj.getClass().getResource("/Resource/reports/reportReleve.jasper").openStream();
            BufferedImage image = ImageIO.read(obj.getClass().getResource("/Resource/images/logopkf.PNG"));

            if (faculty.getTitle().equals("Sciences and Technology")) {
                String facultyIng = "FACULTY OF SCIENCES AND TECHNOLOGY";
                Semester s = CRUDSemester.findSemById(semId);
                String accY = Tools.getAcademicYear(s);
                String deptComp = program.getTitle().toUpperCase();
                String deptPrepaIng = "PREPARATORY SEMESTER ENGINEERING";
                /*1.1 For Engineers*/
                SchoolLevel level = Tools.getSchoolLevel(1);
                JasperPrint jasperPrint = generateRecap(s, 1, accY, facultyIng, deptPrepaIng, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                for (int i = 2; i <= 7; i++) {
                    level = Tools.getSchoolLevel(i);
                    jasperPrint = generateRecap(s, program.getId(), accY, facultyIng, deptComp, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                }
            } else if (faculty.getTitle().equals("Economics and Management")) {
                String facultyEcon = "FACULTY OF ECONOMICS AND MANAGEMENT";
                Semester s = CRUDSemester.findSemById(semId);
                String accY = Tools.getAcademicYear(s);
                String deptEcon = "DEPARTMENT OF ECONOMICS";
                String deptPrepaEc = "PREPARATORY SEMESTER ECONOMICS";
                SchoolLevel level = Tools.getSchoolLevel(1);
                JasperPrint jasperPrint = generateRecap(s, program.getId(), accY, facultyEcon, deptPrepaEc, image, level);
                if (jasperPrint != null) {
                    jasperPrints.add(jasperPrint);
                }
                for (int i = 2; i <= 7; i++) {
                    level = Tools.getSchoolLevel(i);
                    jasperPrint = generateRecap(s, program.getId(), accY, facultyEcon, deptEcon, image, level);
                    if (jasperPrint != null) {
                        jasperPrints.add(jasperPrint);
                    }
                }
            } else {
                return;
            }

            int totPageNumber = 0;
            for (JasperPrint jp : jasperPrints) {
                totPageNumber += jp.getPages().size();
            }
            //Second loop all reports to replace our markers with current and total number
            int currentPage = 1;
            for (JasperPrint jp : jasperPrints) {
                List<JRPrintPage> pages = jp.getPages();
                //Loop all pages of report
                for (JRPrintPage jpp : pages) {
                    List<JRPrintElement> elements = jpp.getElements();
                    //Loop all elements on page
                    for (JRPrintElement jpe : elements) {
                        //Check if text element
                        if (jpe instanceof JRPrintText) {
                            JRPrintText jpt = (JRPrintText) jpe;
                            //Check if current page marker
                            if (CURRENT_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText("Page " + currentPage + " of"); //Replace marker
                                continue;
                            }
                            //Check if totale page marker
                            if (TOTAL_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText(" " + totPageNumber); //Replace marker
                            }
                        }
                    }
                    currentPage++;
                }
            }

            JRPdfExporter exporter = new JRPdfExporter();
            //Create new FileOutputStream or you can use Http Servlet Response.getOutputStream() to get Servlet output stream
            // Or if you want bytes create ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes = out.toByteArray();
            //Use stamper to set viewer prederence 
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(bytes));

            PdfStamper pdfStamper;
            try {
                pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("jury.pdf"));
                pdfStamper.getWriter().setViewerPreferences(PdfWriter.PageModeUseOutlines);
                pdfStamper.close();
            } catch (DocumentException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            pdfReader.close();
            Tools.openPDFFile("jury.pdf");
        } catch (IOException ex) {
            Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void displayReport(int semId, SchoolFaculty faculty, Program program, SchoolLevel level) throws JRException{
        Semester s = CRUDSemester.findSemById(semId);
        List<JasperPrint> jasperPrints = new ArrayList<>();
        InputStream stream = null;
        try {
            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            FinalResultsReport obj = new FinalResultsReport();
            stream = obj.getClass().getResource("/Resource/reports/reportReleve.jasper").openStream();
            BufferedImage image = ImageIO.read(obj.getClass().getResource("/Resource/images/logopkf.PNG"));
            String accY = Tools.getAcademicYear(s);
            String dept = program.getTitle().toUpperCase();

            JasperPrint jasperPrint = generateRecap(s, program.getId(), accY, faculty.getTitle().toUpperCase(), dept, image, level);
            if (jasperPrint != null) {
                jasperPrints.add(jasperPrint);
            }
            int totPageNumber = 0;
            for (JasperPrint jp : jasperPrints) {
                totPageNumber += jp.getPages().size();
            }
            //Second loop all reports to replace our markers with current and total number
            int currentPage = 1;
            for (JasperPrint jp : jasperPrints) {
                List<JRPrintPage> pages = jp.getPages();
                //Loop all pages of report
                for (JRPrintPage jpp : pages) {
                    List<JRPrintElement> elements = jpp.getElements();
                    //Loop all elements on page
                    for (JRPrintElement jpe : elements) {
                        //Check if text element
                        if (jpe instanceof JRPrintText) {
                            JRPrintText jpt = (JRPrintText) jpe;
                            //Check if current page marker
                            if (CURRENT_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText("Page " + currentPage + " of"); //Replace marker
                                continue;
                            }
                            //Check if totale page marker
                            if (TOTAL_PAGE_NUMBER.equals(jpt.getValue())) {
                                jpt.setText(" " + totPageNumber); //Replace marker
                            }
                        }
                    }
                    currentPage++;
                }
            }

            JRPdfExporter exporter = new JRPdfExporter();
            //Create new FileOutputStream or you can use Http Servlet Response.getOutputStream() to get Servlet output stream
            // Or if you want bytes create ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes = out.toByteArray();
            //Use stamper to set viewer prederence 
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(bytes));

            PdfStamper pdfStamper;
            try {
                pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("jury.pdf"));
                pdfStamper.getWriter().setViewerPreferences(PdfWriter.PageModeUseOutlines);
                pdfStamper.close();
            } catch (DocumentException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            pdfReader.close();
            Tools.openPDFFile("jury.pdf");
        } catch (IOException ex) {
            Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(FinalResultsReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
