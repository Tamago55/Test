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

public class TrafficLightExample implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TrafficLightExample());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new StopLightPanel();
        frame.add(panel);
        frame.setSize(250, 350);
        frame.setVisible(true);
    }

    public class StopLightPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        StopLightDrawing light = new StopLightDrawing();

        public StopLightPanel() {
            JButton changeButton = new JButton("Switch");
            ButtonListener listener = new ButtonListener();
            changeButton.addActionListener(listener);

            add(light);
            add(changeButton);
        }

        public class ButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                light.changeColor();
            }
        }
    }

    public class StopLightDrawing extends JComponent {

        private static final long serialVersionUID = 1L;
        private int activeLight = 2;
        private Color[] colors = {Color.red, Color.orange, Color.green};

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
            int y = 60;

            for (int i = 0; i < colors.length; i++) {
                setLightColor(g, i);
                g.fillOval(x - radius, y - radius + (i * 2 * radius), diameter, diameter);
            }
        }

        private void setLightColor(Graphics g, int lightPosition) {
            if (activeLight == lightPosition) {
                g.setColor(colors[lightPosition]);
            } else {
                g.setColor(Color.darkGray);
            }
        }

        public void changeColor() {
            activeLight = (activeLight + 1) % colors.length;
            repaint();
        }
    }
}
