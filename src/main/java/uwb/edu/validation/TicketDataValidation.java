package uwb.edu.validation;

import java.util.HashMap;

public class TicketDataValidation {

    public boolean validateData(HashMap<String, String> facts) {

        for (String i : facts.keySet()) {

            if(i.toString().equals("klasa") && !isNumeric(facts.get(i).toString())) {
                System.out.println(i + " should be integer number");
                return false;
            }

            if((i.toString().equals("klasa") && isNumeric(facts.get(i).toString())) && (Integer.parseInt(facts.get(i)) < 1 || Integer.parseInt(facts.get(i)) > 2)) {
                System.out.println(i + " should be number between 1 and 2");
                return false;
            }

            if(i.toString().equals("odleglosc") && !isNumeric(facts.get(i).toString())) {
                System.out.println(i + " should be integer number");
                return false;
            }

            if(i.toString().equals("ilosc-osob") && !isNumeric(facts.get(i).toString())) {
                System.out.println(i + " should be integer number");
                return false;
            }

            if(i.toString().equals("happy-hours") && !isNumeric(facts.get(i).toString())) {
                System.out.println(i + " should be integer number");
                return false;
            }

            if((i.toString().equals("happy-hours") && isNumeric(facts.get(i).toString())) && (Integer.parseInt(facts.get(i)) < 0 || Integer.parseInt(facts.get(i)) > 24)) {
                System.out.println(i + " should be number between 0 and 24");
                return false;
            }

        }

        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
