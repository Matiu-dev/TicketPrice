package uwb.edu.service;

import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import uwb.edu.validation.TicketDataValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUIService {

    private  JTextField length;
    private  JTextField ticketClass;//1 lub 2
    private  JTextField numberOfTickets;//1 lub 2
    private JTextField happyHours;
    private ButtonGroup dogButtonGroup;
    private ButtonGroup bigFamilyButtonGroup;
    private DefaultListModel<String> summary;
    private DefaultListModel<String> allFacts;

    private TicketDataValidation ticketDataValidation;
    private ClipsService clipsService;
    public GUIService() {
        clipsService = new ClipsService();
        ticketDataValidation = new TicketDataValidation();
    }

    public JButton stworzObliczPrzycisk(Environment clips) {
        JButton calculateButton = new JButton("Oblicz");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> facts = new HashMap<>();
                facts.put("odleglosc", length.getText());
                facts.put("klasa", ticketClass.getText());
                facts.put("przewoz-psa", clipsService.getSelectedButtonText(dogButtonGroup));
                facts.put("ilosc-osob", numberOfTickets.getText());
                facts.put("karta-duzej-rodziny", clipsService.getSelectedButtonText(bigFamilyButtonGroup));
                facts.put("happy-hours", happyHours.getText());

                if(ticketDataValidation.validateData(facts)) {
                    try {
                        clipsService.writeFacts(facts, clips);
                    } catch (CLIPSException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        clipsService.runEngine(clips);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (CLIPSException ex) {
                        throw new RuntimeException(ex);
                    }

                    //summary
                    try {
                        clipsService.summary(summary, clips);
                    } catch (CLIPSException ex) {
                        throw new RuntimeException(ex);
                    }

                    clipsService.allFacts(allFacts, clips);
                }

            }
        });

        return calculateButton;
    }

    public JButton createResetButton(Environment clips) {
        JButton jButton = new JButton("Reset");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clips.reset();
                    allFacts.clear();
                    summary.clear();
                } catch (CLIPSException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return jButton;
    }

    public JPanel createChoosePanel(Environment clips) {
        JPanel panel = new JPanel(new GridLayout(0, 2)) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(250, 250);
            }
        };

//        -----------------------------
        panel.add(new JLabel("Wybór klasy", JLabel.RIGHT));
        ticketClass = new JTextField(10);
        panel.add(ticketClass);
//        -----------------------------
        panel.add(new JLabel("Wpisz długość trasy w km ", JLabel.RIGHT));
        length = new JTextField(10);
        panel.add(length);
//        -----------------------------
        panel.add(new JLabel("Przewóz psa ", JLabel.RIGHT));
        dogButtonGroup = new ButtonGroup();
        JRadioButton haveDog = new JRadioButton("TAK");
        haveDog.setSelected(true);
        JRadioButton niePosiadaPsa = new JRadioButton("NIE");
        dogButtonGroup.add(haveDog);
        dogButtonGroup.add(niePosiadaPsa);
        JPanel dogPanel = new JPanel();
        dogPanel.add(haveDog);
        dogPanel.add(niePosiadaPsa);
        panel.add(dogPanel);
//        -----------------------------
        panel.add(new JLabel("Ilość osób", JLabel.RIGHT));//za kazda osoba bilet x ilosc osob
        numberOfTickets = new JTextField(10);
        panel.add(numberOfTickets);
        //        -----------------------------
        panel.add(new JLabel("Karta duzej rodziny", JLabel.RIGHT));//w przypadku od 2 do 4 osob jest 30%
        bigFamilyButtonGroup = new ButtonGroup();
        JRadioButton haveBigFamilyCard = new JRadioButton("TAK");
        haveBigFamilyCard.setSelected(true);
        JRadioButton dontHaveBigFamilyCard = new JRadioButton("NIE");
        bigFamilyButtonGroup.add(haveBigFamilyCard);
        bigFamilyButtonGroup.add(dontHaveBigFamilyCard);
        JPanel bigFamilyPanel = new JPanel();
        bigFamilyPanel.add(haveBigFamilyCard);
        bigFamilyPanel.add(dontHaveBigFamilyCard);
        panel.add(bigFamilyPanel);
        //        -----------------------------
        panel.add(new JLabel("Godzina kursu", JLabel.RIGHT));//od 19-24 znizka
        happyHours = new JTextField(10);
        panel.add(happyHours);
        //        -----------------------------
        //        -----------------------------
        panel.add(stworzObliczPrzycisk(clips));
        panel.add(createResetButton(clips));

        return panel;

    }

    public JList createSummaryPanel(Environment clips) {
        summary = new DefaultListModel<>();
        JList<String> list = new JList<>(summary);

        list.setVisible(true);
        return list;
    }

    public JList<String> createPanelWithFacts(Environment clips) {
        allFacts = new DefaultListModel<>();
        JList<String> list = new JList<>(allFacts);

        list.setVisible(true);
        return list;
    }
}
