/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Phil
 */
public class Main3 {

    static List<Unit> weightedUnits = new ArrayList<Unit>();
    static List<Unit> unWeightedUnits = new ArrayList<Unit>();

    public static void main(String[] args) {
        Main3 m = new Main3();
        m.sort();
    }

    public void sort() {
        List<Unit> allUnits = new ArrayList<Unit>();
        allUnits.add(new Unit("Programming in C", new LinkedList<String>(Arrays.asList(""))));
        allUnits.add(new Unit("Distributed Computing", new LinkedList<String>(Arrays.asList("Programming in C", "Advanced Programming in Java", "Scalable Machine Learning"))));
        allUnits.add(new Unit("Database System", new LinkedList<String>(Arrays.asList("Programming in C", "Programming in Java", "Advanced Programming in Java", "Big Data with Apache Spark", "Data Structures"))));
        allUnits.add(new Unit("Algorithm 1", new LinkedList<String>(Arrays.asList("Programming in C", "Programming in Perl", "Database System"))));
        allUnits.add(new Unit("Algorithm 2", new LinkedList<String>(Arrays.asList("Programming in C", "Algorithm 1", "Database System", "Data Structures"))));
        allUnits.add(new Unit("Programming in Java", new LinkedList<String>(Arrays.asList("Programming in C"))));
        allUnits.add(new Unit("Advanced Programming in Java", new LinkedList<String>(Arrays.asList("Programming in Java"))));
        allUnits.add(new Unit("Big Data with Apache Spark", new LinkedList<String>(Arrays.asList("Programming in Java", "Advanced Programming in Java", "Probability", "Data Structures"))));
        allUnits.add(new Unit("Programming in Perl", new LinkedList<String>(Arrays.asList("Algorithm 2"))));
        allUnits.add(new Unit("Probability", new LinkedList<String>(Arrays.asList(""))));
        allUnits.add(new Unit("Scalable Machine Learning", new LinkedList<String>(Arrays.asList("Probability", "Big Data with Apache Spark"))));
        allUnits.add(new Unit("Data Structures", new LinkedList<String>(Arrays.asList(""))));


        for (Unit u : allUnits) {
            unWeightedUnits.add(u);
        }
        int last = 0;
        do {
            last = unWeightedUnits.size();
            List<Unit> found = new ArrayList();
            for (Unit u : unWeightedUnits) {
                if (u.getRequireUnits().size() > 0) {
                    if (u.getRequireUnits().get(0).equals("")) {
                        u.setScore(1);
                        weightedUnits.add(u);
                        found.add(u);
                    } else {
                        for (Unit wu : weightedUnits) {
                            if (u.getRequireUnits().contains(wu.getName())) {
                                u.getRequireUnits().remove(wu.getName());
                                u.setScore(u.getScore() + wu.getScore());
                            }
                        }
                    }
                } else {
                    weightedUnits.add(u);
                    found.add(u);
                }
            }
            unWeightedUnits.removeAll(found);
        } while (last != unWeightedUnits.size());

        Collections.sort(weightedUnits);


        System.out.println("one possible order");
        for (Unit tmp : weightedUnits) {
            System.out.println(tmp.getName());
        }

        System.out.println("infinitive loop");
        for (Unit tmp : unWeightedUnits) {
            System.out.println(tmp.getName());
        }
    }

    class Unit implements Comparable {

        private int score;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getRequireUnits() {
            return requireUnits;
        }

        public void setRequireUnits(List<String> requireUnits) {
            this.requireUnits = requireUnits;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
        private List requireUnits;

        public Unit(String name, List list) {
            score = 0;
            this.name = name;
            this.requireUnits = list;
        }

        public int compareTo(Object o) {
            Unit u = (Unit) o;
            return this.score - u.getScore();
        }
    }
}
