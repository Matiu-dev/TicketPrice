package uwb.edu.service;



import net.sf.clipsrules.jni.*;
import uwb.edu.model.Ticket;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

public class ClipsService {
    private boolean isExecuting = true;

    public void summary(DefaultListModel<String> summary, Environment clips) throws CLIPSException {

        String evalStr = "(get-ticket-info)";
        MultifieldValue pv = (MultifieldValue) clips.eval(evalStr);

        Ticket ticket = new Ticket();

        for (int i = 0; i < pv.size(); i++) {
            FactAddressValue fv = (FactAddressValue) pv.get(i);

            String basePrice = fv.getSlotValue("kwota-bazowa").toString();
            summary.addElement("kwota bazowa: " + basePrice);
            ticket.setBasePrice(new BigDecimal(basePrice));

            String priceForDog = fv.getSlotValue("oplata-za-psa").toString();
            summary.addElement("Opłata za psa: " + priceForDog);
            ticket.setPriceForDog(new BigDecimal(priceForDog));

            String bigFamily = fv.getSlotValue("znizka-duza-rodzina").toString();
            summary.addElement("Zniżka za karte duza rodzina: " + bigFamily);
            ticket.setBigFamily(new BigDecimal(bigFamily));

            String numberOfTickets = fv.getSlotValue("ilosc-biletow").toString();
            summary.addElement("Ilość biletów: " + numberOfTickets);
            ticket.setNumberOfTickets(new BigDecimal(numberOfTickets));

            String happyHours = fv.getSlotValue("znizka-happy-hours").toString();
            summary.addElement("Zniżka happy hours: " + happyHours);
            ticket.setHappyHours(new BigDecimal(happyHours));
        }

        summary.addElement("Cena biletu: " + countTicketPrice(ticket).toString());
    }

    private BigDecimal countTicketPrice(Ticket ticket) {
        return (ticket.getBasePrice()
                .subtract(ticket.getBasePrice().multiply(ticket.getBigFamily()))
                .subtract(ticket.getBasePrice().multiply(ticket.getHappyHours())))
                .multiply(ticket.getNumberOfTickets())
                .add(ticket.getPriceForDog());
    }

    public void allFacts(DefaultListModel<String> allFacts, Environment clips) {
        for(FactInstance fact: clips.getFactList()) {
            List<SlotValue> sv = fact.getSlotValues();
            for(SlotValue value: sv) {
                allFacts.addElement(fact.getRelationName() + ": " + value.getSlotValue());
            }
        }
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
//    public void readFacts(Environment clips) throws CLIPSException {
//        for(FactInstance fact: clips.getFactList()) {
//            List<SlotValue> sv = fact.getSlotValues();
//
//            for(SlotValue value: sv) {
//                System.out.println(value.getSlotValue());
//
//            }
//        }
//
//        String evalStr = "(get-ticket-info)";
//        MultifieldValue pv = (MultifieldValue) clips.eval(evalStr);
//
//
//        for (int i = 0; i < pv.size(); i++) {
//            FactAddressValue fv = (FactAddressValue) pv.get(i);
//
//            String kwotaBazowa = fv.getSlotValue("kwota-bazowa").toString(); //fv.getFactAddress()[1].toString();
//
//        }
//    }

    public void writeFacts(HashMap<String, String> facts, Environment clips) throws CLIPSException {

        for (String i : facts.keySet()) {
            clips.assertString("(" + i.toString() + " " + facts.get(i).toString() + ")");
        }
    }

    public void runEngine(Environment clips) throws InterruptedException, CLIPSException {
        Runnable runThread = new Runnable() {
            @Override
            public void run() {
                try {
                    clips.run(); //startuje
                    isExecuting = false;

                } catch (CLIPSException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        isExecuting = true;
        Thread executionThread = new Thread(runThread);
        executionThread.start();

        while (isExecuting) {
            Thread.sleep(1000);
        }

    }
}
