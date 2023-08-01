/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Comparator;

/**
 *
 * @author CNC
 */
public class CourseComparator implements Comparator<Course>{

    @Override
    public int compare(Course o1, Course o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
    
}
