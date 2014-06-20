import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import de.progra.charting.swing.*;
import de.progra.charting.event.*;
import de.progra.charting.model.*;
import de.progra.charting.render.*;
import de.progra.charting.*;

//Code adapted from http://jopenchart.sourceforge.net/tutorial.html#eighth

public class GraphView extends JInternalFrame implements ActionListener, ChartDataModelListener {

    ChartPanel panel;
    EditableChartDataModel data;
    
    // Initialize the Timer:
    private Timer t = new Timer(1000, this);
    private double time = 3.0;
    
    
    public GraphView() {
        // Init some starting data
        double[][] model = {{25.0, 22.0, 23.0},
                            {13.0, 11.0, 12.0}};
        
        double[] columns = {0.0, 1.0, 2.0};
        String[] columnString = {"1998", "1999", "2000"};
        String[] rows = {"Int. Temp.", "Ext. Temp."};

        String title = "Viewing Internal & External Temperature";

        // Create an editable chart data model
        data = new EditableChartDataModel(model, columns, rows);
        
        // Creating the Swing ChartPanel instead of DefaultChart
        panel = new ChartPanel(data, title, DefaultChart.LINEAR_X_LINEAR_Y);
        // Adding ChartRenderer as usual
        panel.addChartRenderer(new LineChartRenderer(panel.getCoordSystem(), data), 1);
        // Register EventListener
        data.addChartDataModelListener(this);
        
        // Start the Timer
        t.start();
        setSize(640, 480);
        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt) {
        // The Timer generated an event -> update DataModel with random data
        data.insertValue(0, new Double(Math.random() * 10.0 + 20.0), new Double(time));
        data.insertValue(1, new Double(Math.random() * 7.0 + 10.0), new Double(time));
        
        time++;
    }
    
    public void chartDataChanged(ChartDataModelEvent evt) {
        // The DataModel changed -> update display
        panel.revalidate();
        repaint();
    }
}

