
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//Code adapted from http://jopenchart.sourceforge.net/tutorial.html#eighth

public class GraphView extends JPanel {

	private XYSeries kinEnergy;
	private XYSeries potEnergy;
	private XYSeries mecEnergy;
	
	private JFreeChart sysChart;
	
	private XYSeriesCollection series;
	
    public GraphView() {
        kinEnergy = new XYSeries("Kinetic Energy");
        potEnergy = new XYSeries("Potential Energy");
        mecEnergy = new XYSeries("Mechanical Energy");
        
        series = new XYSeriesCollection(kinEnergy);
        series.addSeries(potEnergy);
        series.addSeries(mecEnergy);
        
        sysChart = ChartFactory.createXYLineChart("System Energy", "Time", "[J]",
        		series,PlotOrientation.VERTICAL,false,true,false);
        
        ChartPanel chart = new ChartPanel(sysChart);
        
        ValueAxis xAxis = sysChart.getXYPlot().getDomainAxis();
        xAxis.setRange(0.0, 10.0);
        xAxis.setAutoRange(true);
        xAxis.setAutoRangeMinimumSize(0.1);
        
        add(chart);
    }

    public void ClearData() {
    	kinEnergy.clear();
    	potEnergy.clear();
    	mecEnergy.clear();
    }
    
    public void addDataPoint(int id, double Y, double X) { 
    	ValueAxis xAxis = sysChart.getXYPlot().getDomainAxis();
    	xAxis.setFixedAutoRange(10.0);
    	switch (id) {
		case 0:
			//Kinetic
			kinEnergy.add(X, Y);
			break;
		case 1:
			//Potential
			potEnergy.add(X, Y);
			break;
		case 2:
			//Mechanical
			mecEnergy.add(X,Y);
			break;
		default:
			break;
		}
    }
    
}

