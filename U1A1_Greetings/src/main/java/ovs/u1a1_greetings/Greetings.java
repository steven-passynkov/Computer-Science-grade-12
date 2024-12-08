package ovs.u1a1_greetings;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;

/**
 * File Name:	Greetings 
 * Programmer:	Steven Passynkov 
 * Date:	2 Jan 2024
 * Description:	This program creates a JFrame that displays information about
 * me.
 */
public class Greetings extends JFrame {

    /**
     * Constructor of Greetings frame with Person
     *
     * @param person
     */
    public Greetings(Person person) {
        // call JFrame constractor with title
        super("About Me.");
        // define width and high 
        setSize(500, 250);
        // Close program on frame exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel for all app
        JPanel mainPanel = new JPanel();
        // Set BoxLayout to layout all elements on top of each other
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Detail panel for details with GridBagLayout
        JPanel dPanel = new JPanel(new GridBagLayout());
        // Add border with title
        dPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        // Add Detail panel to main panel
        mainPanel.add(dPanel);

        // create name label with person name
        JLabel nameLabel = new JLabel(String.format("Hi, my name is %s.", person.name()));
        // create age label with person age
        JLabel ageLabel = new JLabel(String.format("I am %d years old.", person.age()));
        // Create music label with person music pref
        JLabel favoriteMusicLabel = new JLabel(String.format("I like to listen to %s.", person.favoriteMusic()));
        // Create show label with person show pref
        JLabel favoriteShowLabel = new JLabel(String.format("My favorite TV show is %s.", person.favoriteShow()));
        // Create show label with person color pref
        JLabel favoriteColorLabel = new JLabel(String.format("My favorite color is %s.", person.favoriteColor()));

        // Create GridBagConstraints to center labels. Each label takes full width 
        GridBagConstraints gbc = new GridBagConstraints();
        //  Resize the component horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // one label per row
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // add name label
        dPanel.add(nameLabel, gbc);
        // add age label
        dPanel.add(ageLabel, gbc);
        // add music label
        dPanel.add(favoriteMusicLabel, gbc);
        // add show label
        dPanel.add(favoriteShowLabel, gbc);
        // add color label
        dPanel.add(favoriteColorLabel, gbc);

        // Technologies panel with FlowLayout - flow row
        JPanel tPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // add  border with title
        tPanel.setBorder(BorderFactory.createTitledBorder("My Technologies"));
        //Add Technologies panel to main panel
        mainPanel.add(tPanel);

        // Loop over person technologies
        for (String tech : person.techs()) {
            // create a button for each person technology
            JButton btn = new JButton(tech);
            // Disable it to make not clickable
            btn.setEnabled(false);
            // add to Technologies panel
            tPanel.add(btn);
        }

        // GitHub panel and layout to add content to the center
        JPanel gPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Create a button
        JButton button = new JButton("My Github Link");
        // add the button to GitHub panel
        gPanel.add(button);
        // add listener when the button is clicked
        button.addActionListener(new ActionListener() {
            @Override
            /**
             * Implement method for click event
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    // open browser to show person github page
                    Desktop.getDesktop().browse(new URI(person.gitHub()));
                } catch (IOException | URISyntaxException ex) {
                    // in case of eception - print stack
                    ex.printStackTrace();
                }
            }
        });
        // Add github panel to main panel
        mainPanel.add(gPanel);

        // Add manin panel to the frame
        add(mainPanel);
        // make frame visible
        setVisible(true);
    }

    /**
     * Main method of the program
     *
     * @param args
     */
    public static void main(String[] args) {
        // Use drak theme - since I use dark for all my apps
        LafManager.installTheme(new DarculaTheme());

        // Create a person with my preferences
        String techs[] = {"React", "Typescript", "Python", "Ionic", "Supabase", "Cv2"};
        Person person = new Person("Steven", 16, "Hip-Hop and R&B", "Lost", "black",
                "https://github.com/steven-passynkov",
                techs);

        // Create a frame with information about me
        new Greetings(person);
    }
}
