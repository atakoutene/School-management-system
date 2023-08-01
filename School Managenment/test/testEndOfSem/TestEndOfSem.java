/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testEndOfSem;

import Control.ManageProgramCourses;
import Model.Course;
import Model.Student;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author metch
 */
public class TestEndOfSem {

    public static void main(String[] args) {
        Set<Course> test = ManageProgramCourses.loadProgramCourses(1, 2008, "Spring", 5,3);
        for (Course c : test) {
            System.out.println(c.getId() + "   " + c.getCode() + "  " + c.getTitle() + "  " + c.getNbCreditsForReport() + "  " + c.getPassingGradeForReport());
        }
        Map<Student, Set<Course>> map = ManageProgramCourses.getStudentCoursesSem(6, 23, 5);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Student s = (Student) pair.getKey();
            Set<Course> courses = (Set<Course>)pair.getValue();
            
            System.out.print(s.getName() + "   " + s.getStuID() + "  ");
            for(Course c: courses){
                System.out.print(c.getGradeForReport() + "  " + c.getLetterGradeForReport() + " ");
            }
            System.out.println("");
        }
    }
}
