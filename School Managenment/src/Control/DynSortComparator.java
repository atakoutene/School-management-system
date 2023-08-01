/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.Comparator;

/**
 *
 * @author metch
 */
public class DynSortComparator<T extends Comparable<T>> implements Comparator<T> {

    private boolean ascending = true;

    public DynSortComparator() {
    }
    
    public DynSortComparator(boolean _ascending) {
        this.ascending = _ascending;
    }

    @Override
    public int compare(T arg0, T arg1) {
        if (ascending) {
            if (arg0 == null) {
                return 1;
            }
            if (arg1 == null) {
                return -1;
            }
            return arg0.compareTo(arg1);
        }
        if (arg1 == null) {
            return 1;
        }
        if (arg0 == null) {
            return -1;
        }
        return arg1.compareTo(arg0);
    }

}
