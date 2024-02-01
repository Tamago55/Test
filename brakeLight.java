import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class brakeLight implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TrafficLightExample());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Traffic Light Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new StopLightPanel();
        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class StopLightPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        StopLightDrawing light = new StopLightDrawing();

        public StopLightPanel() {
            JButton changeButton = new JButton("Switch");
            changeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    light.changeColor();
                }
            });

            this.add(light);
            this.add(changeButton);
        }
    }

    public class StopLightDrawing extends JComponent {

        private static final long serialVersionUID = 1L;
        private boolean isOrange = true;

        public StopLightDrawing() {
            this.setPreferredSize(new Dimension(160, 250));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.black);
            g.fillRect(0, 0, 160, 250);

            g.setColor(Color.gray);
            g.fillRect(5, 5, 150, 240);

            int radius = 30;
            int diameter = radius * 2;
            int x = getWidth() / 2;
            int y = 120;

            g.setColor(isOrange ? Color.red : Color.black);
            g.fillOval(x - radius, y - radius, diameter, diameter);
        }

        public void changeColor() {
            isOrange = !isOrange;
            repaint();
        }
    }
}
