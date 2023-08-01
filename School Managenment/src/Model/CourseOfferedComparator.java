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
public class CourseOfferedComparator implements Comparator<CourseOffered>{

    @Override
    public int compare(CourseOffered o1, CourseOffered o2) {
        return o1.getC().getTitle().compareToIgnoreCase(o2.getC().getTitle());
    }
    
}
