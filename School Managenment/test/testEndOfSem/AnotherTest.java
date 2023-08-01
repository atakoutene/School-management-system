/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testEndOfSem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author metch
 */

class FX{
    public static void foo(){}
}
public class AnotherTest {

    public static void main(String[] args) throws InterruptedException {
//        List<Integer> ages = new ArrayList<>();
//        ages.add(Integer.parseInt("5"));
//
//        ages.add(Integer.valueOf("6"));
//        ages.add(7);
//        ages.add(null);
//        for (int age : ages) {
//            System.out.print(age);
//        }
        int x = 3;
        int y = ++x * 5 / x-- + --x;
        //Thread.sleep(5000);
        System.out.println("x is " + x);
        System.out.println("y is " + y);
        int z = 12 * x + 2 * x++ - 5 * x;
        System.out.println(z);
        System.out.println("abcabc".replace('c', 'A')); // AbcAbc
        Boolean bool = Boolean.valueOf("true");
        System.out.println(bool);
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(5, 15);
        LocalDateTime dateTime = LocalDateTime.of(date, time)
                .minusDays(1).minusHours(10).minusSeconds(30);
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        LocalDate start = LocalDate.of(2015, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2015, Month.MARCH, 30);
        performAnimalEnrichment(start, end);

    }

    private static void performAnimalEnrichment(LocalDate start, LocalDate end) {
        LocalDate upTo = start;
        while (upTo.isBefore(end)) { // check if still before end
            System.out.println("give new toy: " + upTo);
            upTo = upTo.plusMonths(1); // add a month
            String str = null;
        }
    }

    public static int howMany(boolean b, boolean... b2) {
        return b2.length;
    }
}
