package TankGame;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.JOptionPane;

public class TankGame extends Frame {

    private static final long serialVersionUID = 1L;
    public static final int Fram_width = 850;
    public static final int Fram_length = 650;
    public static boolean printable = true;
    static int enemytank_count = 8;

    Image screenImage = null;

    TankGame tc = null;
    Tank homeTank = new Tank(300, 560, true, Direction.STOP, this);

    List<Tank> tanks = new ArrayList<Tank>();
    List<Bullets> bullets = new ArrayList<Bullets>();

    public static void main(String[] args) {
        new TankGame();
    }

    public void update(Graphics g) {
        screenImage = this.createImage(Fram_width, Fram_length);
        Graphics gps = screenImage.getGraphics();
        gps.setColor(Color.DARK_GRAY);
        gps.fillRect(0, 0, Fram_width, Fram_length - 50);
        framPaint(gps);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void framPaint(Graphics g) {

        homeTank.draw(g);

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            t.collideWithTanks(tanks);
            t.draw(g);
        }


        homeTank.collideWithTanks(tanks);


        for (int i = 0; i < bullets.size(); i++) {
            Bullets m = bullets.get(i);
            m.hitTanks(tanks);
            m.hitTank(homeTank);
            m.draw(g);
        }
    }

    public TankGame() {
        this.setVisible(true);


        for (int i = 0; i < enemytank_count; i++) {
            if (i < 9)
                tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this));
            else if (i < 15)
                tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D, this));
            else
                tanks.add(new Tank(10, 50 * (i - 12), false, Direction.D, this));
        }

        this.setSize(Fram_width, Fram_length);
        this.setLocation(500, 250);
        this.setTitle("Tank");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).start();
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (printable) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
                if (tanks.size() == 0 && homeTank.isLive()) {
                    JOptionPane.showMessageDialog(tc, "Win!");
                    break;
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) { // ���������ͷ�
            homeTank.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) { // �������̰���
            homeTank.keyPressed(e);
        }
    }

}
