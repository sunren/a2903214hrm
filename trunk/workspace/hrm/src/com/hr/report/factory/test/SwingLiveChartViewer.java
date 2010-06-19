package com.hr.report.factory.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.IDisplayServer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.core.framework.PlatformConfig;

public final class SwingLiveChartViewer extends JPanel {
    private static final long serialVersionUID = 1L;
    private Chart cm = null;

    private IDeviceRenderer dRenderer = null;

    private GeneratedChartState gcState = null;

    private boolean bFirstPaint = true;

    private boolean bDisposed = false;

    public static void main(String[] args) {
        final SwingLiveChartViewer lcViewer = new SwingLiveChartViewer();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(2);
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(lcViewer, "Center");

        Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dApp = new Dimension(600, 400);
        frame.setSize(dApp);
        frame.setLocation((dScreen.width - dApp.width) / 2, (dScreen.height - dApp.height) / 2);

        frame.setTitle(lcViewer.getClass().getName() + " [device="
                + lcViewer.dRenderer.getClass().getName() + "]");

        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
//                SwingLiveChartViewer.access$002(lcViewer, true);
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }
        });
    }

    SwingLiveChartViewer() {
        try {
            PlatformConfig config = new PlatformConfig();
            config.setProperty("STANDALONE", "true");
            config.setProperty("BIRT_HOME",
                               "D:/wxz/birt 安装/birt-runtime-2_2_1/birt-runtime-2_2_1/ReportEngine");
            this.dRenderer = ChartEngine.instance(config).getRenderer("dv.SWING");
        } catch (ChartException ex) {
            ex.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        this.dRenderer.setProperty("device.output.context", g2d);
        Dimension d = getSize();
        Bounds bo = BoundsImpl.create(0.0D, 0.0D, d.width, d.height);
        bo.scale(72.0D / this.dRenderer.getDisplayServer().getDpiResolution());

        Generator gr = Generator.instance();
        try {
            this.gcState = gr.build(this.dRenderer.getDisplayServer(), this.cm, bo, null, null,
                                    null);
        } catch (ChartException ex) {
            ex.printStackTrace();
        }
        try {
            gr.render(this.dRenderer, this.gcState);
        } catch (ChartException ex) {
            ex.printStackTrace();
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.factory.test.SwingLiveChartViewer JD-Core Version: 0.5.4
 */