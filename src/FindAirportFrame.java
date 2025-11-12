import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Collections;

/*import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;*/

public class FindAirportFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField cityTextField;
    private JButton findButton;
    private JButton visualizeNetworkButton;
    private JPanel infoPanel;
    private JPanel flightDetailsPanel;

    public FindAirportFrame() {
        super("Find Airport");
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();

        cityTextField = new JTextField(15);
        findButton = new JButton("Find");
        visualizeNetworkButton = new JButton("Visualize Network");

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = cityTextField.getText();
                if (CentralRegistry.getAirport(cityName) == null) {
                    String info = (cityName + " does not have an airport");
                    JOptionPane.showMessageDialog(FindAirportFrame.this, info, "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JFrame airportPageFrame = new JFrame("Airport Page");
                    airportPageFrame.setSize(500, 300);
                    airportPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    airportPageFrame.setLocationRelativeTo(FindAirportFrame.this);

                    JTextField nameField = new JTextField(CentralRegistry.getAirport(cityName).getAirportName());
                    JTextField codeField = new JTextField(CentralRegistry.getAirport(cityName).getCodeName());
                    JTextField cityField = new JTextField(cityName);
                    JTextField countryField = new JTextField(CentralRegistry.getAirport(cityName).getCountry());
                    java.util.List<String> airlinesList = CentralRegistry.getAirport(cityName).airlines;
                    Collections.sort(airlinesList);
                    StringBuilder airlineInfo = new StringBuilder("");
                    for (String airline : airlinesList) {
                        airlineInfo.append(airline).append(" ");
                    }
                    JLabel airlineLabel = new JLabel(airlineInfo.toString());

                    infoPanel = new JPanel(new GridLayout(2, 1));
                    JPanel topPanel = new JPanel(new FlowLayout());
                    topPanel.add(new JLabel());
                    topPanel.add(new JLabel());
                    topPanel.add(new JLabel());
                    topPanel.add(nameField);
                    topPanel.add(new JLabel());
                    topPanel.add(codeField);
                    topPanel.add(new JLabel());
                    topPanel.add(cityField);
                    topPanel.add(new JLabel());
                    topPanel.add(countryField);
                    topPanel.add(new JLabel());
                    topPanel.add(airlineLabel);
                    infoPanel.add(topPanel);

                    JPanel bottomPanel = new JPanel(new FlowLayout());
                    JTextField findFlightTextField = new JTextField(15);
                    bottomPanel.add(new JLabel());
                    bottomPanel.add(new JLabel());
                    bottomPanel.add(new JLabel());
                    bottomPanel.add(findFlightTextField);

                    JButton findFlightsButton = new JButton("Find Flights");
                    bottomPanel.add(findFlightsButton);

                    flightDetailsPanel = new JPanel(new GridLayout(1, 2));

                    findFlightsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String directFlightDetails = "";
                            String inDirectFlightDetails = "";
                            String flightInfo = findFlightTextField.getText();
                            if (cityName.equals(flightInfo)) {
                                String info2 = ("Arrival and departure city cannot be the same!");
                                JOptionPane.showMessageDialog(FindAirportFrame.this, info2, "Message", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            } else {
                                directFlightDetails = "DIRECT FLIGHT DETAILS\n" + CentralRegistry.getDirectFlightsDetails(
                                        CentralRegistry.getAirport(cityName),
                                        CentralRegistry.getAirport(flightInfo));
                                inDirectFlightDetails = "INDIRECT FLIGHT DETAILS through...\n" + CentralRegistry.getInDirectFlightsDetails(
                                        CentralRegistry.getAirport(cityName),
                                        CentralRegistry.getAirport(flightInfo));
                            }

                            JTextField directDetailsTextField = new JTextField(directFlightDetails);
                            directDetailsTextField.setEditable(false);

                            JTextField inDirectDetailsTextField = new JTextField(inDirectFlightDetails);
                            inDirectDetailsTextField.setEditable(false);

                            flightDetailsPanel.removeAll();
                            flightDetailsPanel.setLayout(new BoxLayout(flightDetailsPanel, BoxLayout.Y_AXIS));
                            flightDetailsPanel.add(directDetailsTextField);
                            flightDetailsPanel.add(inDirectDetailsTextField);

                            airportPageFrame.repaint();

                            // Create a text file with flight details
                            String fileName = cityName + "To" + flightInfo + ".txt";
                            createFlightDetailsFile(fileName, cityName, flightInfo, directFlightDetails, inDirectFlightDetails);
                        }
                    });

                    JButton backToMainScreenButton = new JButton("Back to Main Screen");
                    backToMainScreenButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            airportPageFrame.dispose();
                            new FindAirportFrame();
                        }
                    });

                    backToMainScreenButton.setPreferredSize(new Dimension(150, 30));

                    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    centerPanel.add(backToMainScreenButton);
                    bottomPanel.add(centerPanel);

                    bottomPanel.add(flightDetailsPanel);
                    infoPanel.add(bottomPanel);

                    airportPageFrame.setLayout(new GridLayout(3, 1));
                    airportPageFrame.add(infoPanel);

                    airportPageFrame.setVisible(true);
                }
            }
        });

       /* visualizeNetworkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizeNetwork();
            }
        });*/

        panel.add(cityTextField);
        panel.add(findButton);
        panel.add(visualizeNetworkButton);

        getContentPane().setLayout(new GridLayout(1, 1));
        getContentPane().add(panel);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*private void visualizeNetwork() {
        DirectedSparseGraph graph = new DirectedSparseGraph();
        // Add nodes (cities)
        for (String cityName : CentralRegistry.getCityNames()) {
            graph.addVertex(cityName);
        }

        // Add edges (connections)
        for (String cityName : CentralRegistry.getCityNames()) {
            Airport currentAirport = CentralRegistry.getAirport(cityName);
            for (String connectedCity : currentAirport.getConnectedCities()) {
                graph.addEdge(cityName + "-" + connectedCity, cityName, connectedCity);
            }
        }

        // Create JUNG visualization
        CircleLayout<String, String> layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(300, 300));
        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(350, 350));

        // Customize graph visualization
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());

        JFrame frame = new JFrame("City Airport Connections Network");
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }*/

    private void createFlightDetailsFile(String fileName, String departureCity, String destinationCity,
                                         String directDetails, String inDirectDetails) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // Write flight details to the file
            writer.println("CITY: " + departureCity + ", " + CentralRegistry.getAirport(departureCity).getCountry());
            writer.println("Airport: " + CentralRegistry.getAirport(departureCity).getAirportName() + " (" + CentralRegistry.getAirport(departureCity).getCodeName() + ")");
            writer.println("Destination: " + destinationCity);
            writer.println("DIRECT FLIGHT DETAILS:");
            writer.println(directDetails);
            writer.println("INDIRECT FLIGHT DETAILS through...");
            writer.println(inDirectDetails);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FindAirportFrame());
    }
}
