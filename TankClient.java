
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public static final int Fram_width = 800;
    public static final int Fram_length = 600;
    public static boolean printable = true;
    MenuBar jmb = null;
    Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null, jm5 = null;
    MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null,
            jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null, jmi10 = null;
    Image screenImage = null;

    Tank homeTank = new Tank(000, 260, true, Direction.STOP, this, 1);
    Tank homeTank2 = new Tank(800, 260, true, Direction.STOP, this, 2);
    Boolean Player2 = false;
    Life life = new Life();
    Boolean win = false, lose = false;
    List<Tank> tanks = new ArrayList<Tank>();
    List<BombTank> bombTanks = new ArrayList<BombTank>();
    List<Bullets> bullets = new ArrayList<Bullets>();
    List<BreakableWall> homeWall = new ArrayList<BreakableWall>();
    List<BreakableWall> otherWall = new ArrayList<BreakableWall>();
    List<Wall> unbreakableWall = new ArrayList<Wall>();

    public void update(Graphics g) {

        screenImage = this.createImage(Fram_width, Fram_length);

        Graphics gps = screenImage.getGraphics();
        Color c = gps.getColor();
        gps.setColor(Color.BLACK);
        gps.fillRect(0, 0, Fram_width, Fram_length);
        gps.setColor(c);
        framPaint(gps);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void framPaint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.MAGENTA);

        Font f1 = g.getFont();

        if (!Player2) {
            g.drawString("" + tanks.size(), 400, 70);
        } else {
            g.drawString("" + tanks.size(), 300, 70);
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (!Player2) {
            g.drawString("Life: ", 580, 70);
        } else {
            g.drawString("Life: ", 95, 70);
        }
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        if (!Player2) {
            g.drawString("" + homeTank.getLife(), 650, 70);
        } else {
            g.drawString("Player 1: " + homeTank.getLife() + "    Player 2:" + homeTank2.getLife(), 350, 70);
        }
        g.setFont(f1);
        if (!Player2) {
            if (tanks.size() == 0 && homeTank.isLive() && lose == false) {
                Font f = g.getFont();
                g.setFont(new Font("Arial", Font.BOLD, 60));
                this.otherWall.clear();
                g.setFont(f);
                win = true;
            }

            if (homeTank.isLive() == false && win == false) {
                Font f = g.getFont();
                g.setFont(new Font("Arial", Font.BOLD, 40));
                tanks.clear();
                bullets.clear();
                g.drawString("Sorry. You lose!", 200, 300);
                lose = true;
                g.setFont(f);
            }
        } else {
            if (tanks.size() == 0 && (homeTank.isLive() || homeTank2.isLive()) && lose == false) {
                Font f = g.getFont();
                g.setFont(new Font("Arial", Font.BOLD, 60));
                this.otherWall.clear();
                g.setFont(f);
                win = true;
            }

            if (homeTank.isLive() == false && homeTank2.isLive() == false && win == false) {
                Font f = g.getFont();
                g.setFont(new Font("Arial", Font.BOLD, 40));
                tanks.clear();
                bullets.clear();
                g.drawString("Sorry. You lose!", 200, 300);
                System.out.println("2");
                g.setFont(f);
                lose = true;
            }
        }
        g.setColor(c);

        homeTank.draw(g);
      
        if (Player2) {
            homeTank2.draw(g);
            
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullets m = bullets.get(i);
            m.hitTank(homeTank);
            m.hitTank(homeTank2);
            for (int j = 0; j < bullets.size(); j++) {
                if (i == j) {
                    continue;
                }
                Bullets bts = bullets.get(j);
                m.hitBullet(bts);
            }
            for (int j = 0; j < unbreakableWall.size(); j++) {
                Wall mw = unbreakableWall.get(j);
                m.hitWall(mw);
            }

            for (int j = 0; j < otherWall.size(); j++) {
                BreakableWall w = otherWall.get(j);
                m.hitWall(w);
            }

            for (int j = 0; j < homeWall.size(); j++) {
                BreakableWall cw = homeWall.get(j);
                m.hitWall(cw);
            }
            m.draw(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);

            for (int j = 0; j < homeWall.size(); j++) {
                BreakableWall cw = homeWall.get(j);
                t.collideWithWall(cw);
                cw.draw(g);
            }
            for (int j = 0; j < otherWall.size(); j++) {
                BreakableWall cw = otherWall.get(j);
                t.collideWithWall(cw);
                cw.draw(g);
            }
            for (int j = 0; j < unbreakableWall.size(); j++) {
                Wall mw = unbreakableWall.get(j);
                t.collideWithWall(mw);
                mw.draw(g);
            }

            t.collideWithTanks(homeTank2);

            t.draw(g);
        }

        for (int i = 0; i < bombTanks.size(); i++) {
            BombTank bt = bombTanks.get(i);
            bt.draw(g);
        }

        for (int i = 0; i < otherWall.size(); i++) {
            BreakableWall cw = otherWall.get(i);
            cw.draw(g);
        }

        for (int i = 0; i < unbreakableWall.size(); i++) {
            Wall mw = unbreakableWall.get(i);
            mw.draw(g);
        }

        homeTank.collideWithTanks(homeTank2);

        if (Player2) {
            homeTank2.collideWithTanks(homeTank);
        }

        for (int i = 0; i < unbreakableWall.size(); i++) {
            Wall w = unbreakableWall.get(i);
            homeTank.collideWithWall(w);
            if (Player2) {
                homeTank2.collideWithWall(w);
            }
            w.draw(g);
        }

        for (int i = 0; i < otherWall.size(); i++) {
            BreakableWall cw = otherWall.get(i);
            homeTank.collideWithWall(cw);
            if (Player2) {
                homeTank2.collideWithWall(cw);
            }
            cw.draw(g);
        }

        for (int i = 0; i < homeWall.size(); i++) {
            BreakableWall w = homeWall.get(i);
            homeTank.collideWithWall(w);
            if (Player2) {
                homeTank2.collideWithWall(w);
            }
            w.draw(g);
        }

    }

    public TankClient() {

        jmb = new MenuBar();
        jm1 = new Menu("Game");
        jm2 = new Menu("Pause/Continue");
        jm4 = new Menu("Level");
        jm5 = new Menu("Player 2");
        jm1.setFont(new Font("Arial", Font.BOLD, 15));
        jm2.setFont(new Font("Arial", Font.BOLD, 15));
        jm4.setFont(new Font("Arial", Font.BOLD, 15));
        jmi1 = new MenuItem("New Game");
        jmi2 = new MenuItem("Exit");
        jmi3 = new MenuItem("Stop");
        jmi4 = new MenuItem("Continue");
        jmi6 = new MenuItem("Level1");
        jmi7 = new MenuItem("Level2");
        jmi8 = new MenuItem("Level3");
        jmi9 = new MenuItem("Level4");
        jmi10 = new MenuItem("Add Player 2");
        jmi1.setFont(new Font("Arial", Font.BOLD, 15));
        jmi2.setFont(new Font("Arial", Font.BOLD, 15));
        jmi3.setFont(new Font("Arial", Font.BOLD, 15));
        jmi4.setFont(new Font("Arial", Font.BOLD, 15));

        jm1.add(jmi1);
        jm1.add(jmi2);
        jm2.add(jmi3);
        jm2.add(jmi4);

        jm4.add(jmi6);
        jm4.add(jmi7);
        jm4.add(jmi8);
        jm4.add(jmi9);
        jm5.add(jmi10);

        jmb.add(jm1);
        jmb.add(jm2);

        jmb.add(jm4);
        jmb.add(jm5);

        jmi1.addActionListener(this);
        jmi1.setActionCommand("NewGame");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("Exit");
        jmi3.addActionListener(this);
        jmi3.setActionCommand("Stop");
        jmi4.addActionListener(this);
        jmi4.setActionCommand("Continue");
        jmi6.addActionListener(this);
        jmi6.setActionCommand("level1");
        jmi7.addActionListener(this);
        jmi7.setActionCommand("level2");
        jmi8.addActionListener(this);
        jmi8.setActionCommand("level3");
        jmi9.addActionListener(this);
        jmi9.setActionCommand("level4");
        jmi10.addActionListener(this);
        jmi10.setActionCommand("Player2");

        this.setMenuBar(jmb);
        this.setVisible(true);

        for (int i = 0; i < 32; i++) {
            if (i < 16) {
                otherWall.add(new BreakableWall(200 + 21 * i, 300, this));
                otherWall.add(new BreakableWall(500 + 21 * i, 180, this));
                otherWall.add(new BreakableWall(200, 400 + 21 * i, this));
                otherWall.add(new BreakableWall(500, 400 + 21 * i, this));
            } else if (i < 32) {
                otherWall.add(new BreakableWall(200 + 21 * (i - 16), 320, this));
                otherWall.add(new BreakableWall(500 + 21 * (i - 16), 220, this));
                otherWall.add(new BreakableWall(222, 400 + 21 * (i - 16), this));
                otherWall.add(new BreakableWall(522, 400 + 21 * (i - 16), this));
            }
        }

        for (int i = 0; i < 20; i++) {
            if (i < 10) {
                unbreakableWall.add(new Wall(140 + 30 * i, 150, this));
                unbreakableWall.add(new Wall(600, 400 + 20 * (i), this));
            } else if (i < 20) {
                unbreakableWall.add(new Wall(140 + 30 * (i - 10), 180, this));
            }

        }

        this.setSize(Fram_width, Fram_length);
        this.setLocation(280, 50);
        this
                .setTitle("CSC413 TANK GAME");

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.MAGENTA);
        this.setVisible(true);

        this.addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).start();
    }

    public static void main(String[] args) {
        new TankClient();
    }

    private class PaintThread implements Runnable {

        public void run() {

            while (printable) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            homeTank.keyReleased(e);
            homeTank2.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            homeTank.keyPressed(e);
            homeTank2.keyPressed(e);
        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("NewGame")) {
            printable = false;
            Object[] options = {"Confirm", "Cancel"};
            int response = JOptionPane.showOptionDialog(this, "New game?", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {

                printable = true;
                this.dispose();
                new TankClient();
            } else {
                printable = true;
                new Thread(new PaintThread()).start();
            }

        } else if (e.getActionCommand().endsWith("Stop")) {
            printable = false;
        } else if (e.getActionCommand().equals("Continue")) {

            if (!printable) {
                printable = true;
                new Thread(new PaintThread()).start();
            }
        } else if (e.getActionCommand().equals("Exit")) {
            printable = false;
            Object[] options = {"Confirm", "Cancel"};
            int response = JOptionPane.showOptionDialog(this, "Confirm to exit?", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                System.out.println("break down");
                System.exit(0);
            } else {
                printable = true;
                new Thread(new PaintThread()).start();

            }

        } else if (e.getActionCommand().equals("Player2")) {
            printable = false;
            Object[] options = {"Confirm", "Cancel"};
            int response = JOptionPane.showOptionDialog(this, "Confirm to add player2?", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                printable = true;
                this.dispose();
                TankClient Player2add = new TankClient();
                Player2add.Player2 = true;
            } else {
                printable = true;
                new Thread(new PaintThread()).start();
            }
        } else if (e.getActionCommand().equals("level1")) {
            Tank.count = 12;
            Tank.speedX = 6;
            Tank.speedY = 6;
            Bullets.speedX = 10;
            Bullets.speedY = 10;
            this.dispose();
            new TankClient();
        } else if (e.getActionCommand().equals("level2")) {
            Tank.count = 12;
            Tank.speedX = 10;
            Tank.speedY = 10;
            Bullets.speedX = 12;
            Bullets.speedY = 12;
            this.dispose();
            new TankClient();

        } else if (e.getActionCommand().equals("level3")) {
            Tank.count = 20;
            Tank.speedX = 14;
            Tank.speedY = 14;
            Bullets.speedX = 16;
            Bullets.speedY = 16;
            this.dispose();
            new TankClient();
        } else if (e.getActionCommand().equals("level4")) {
            Tank.count = 20;
            Tank.speedX = 16;
            Tank.speedY = 16;
            Bullets.speedX = 18;
            Bullets.speedY = 18;
            this.dispose();
            new TankClient();
        }

    }
}
