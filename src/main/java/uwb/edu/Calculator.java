package uwb.edu;

import net.sf.clipsrules.jni.*;
import uwb.edu.service.GUIService;

import javax.swing.*;
import java.awt.*;

public class Calculator {

    private GUIService guiService;

    public Calculator() throws CLIPSException {
        guiService = new GUIService();
    }
    public void start() throws CLIPSException{

        Environment clips = new Environment();
        clips.load("ticket.clp");
        clips.reset();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame frame = new JFrame("Kalkulator ceny biletów");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new GridLayout(3,1));

                final JPanel c1 = new JPanel(new CardLayout());
                c1.add(new JScrollPane(guiService.createChoosePanel(clips)), "Card1");

                JPanel c2 = new JPanel();
                c2.add(new JScrollPane(guiService.createSummaryPanel(clips)), "Card2");

                JPanel c3 = new JPanel();
                c3.add(new JScrollPane(guiService.createPanelWithFacts(clips)), "Card3");

                frame.add(c1);
                frame.add(c2);
                frame.add(c3);
                frame.pack();
                frame.setVisible(true);
            }});
    }
}
