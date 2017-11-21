
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public static final int Fram_width = 800;
    public static final int Fram_length = 750;
    public static boolean printable = true;
    MenuBar jmb = null;
    Menu jm1 = null, jm2 = null, jm3 = null;
    MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null;
    
    Image screenImage = null;

    Tank homeTank = new Tank(000, 288, true, Direction.INITIAL, this, 1);
    Tank homeTank2 = new Tank(800, 288, true, Direction.INITIAL, this, 2);
    Boolean Player2 = false;
    Life life = new Life();
    Boolean win = false, loseP1 = false, loseP2 = false;
    List<BombTank> bombTanks = new ArrayList<BombTank>();
    List<Bullets> bullets = new ArrayList<Bullets>();
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
        g.setColor(Color.WHITE);

        Font f1 = g.getFont();

        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.setFont(new Font("Arial", Font.PLAIN, 25));

        g.drawString("Player 1 HP: " + homeTank.getLife(), 50, 715);
        g.drawString("Player 2 HP:" + homeTank2.getLife(), 500, 715);

        g.setFont(f1);

        if (homeTank2.isLive() == false && homeTank.isLive() == true) {
            Font f = g.getFont();
            g.setFont(new Font("Arial", Font.BOLD, 40));
            bullets.clear();
            g.drawString("PLAYER 1 WINS!!", 200, 300);
            g.setFont(f);
            loseP2 = true;
        }
        if (homeTank2.isLive() == true && homeTank.isLive() == false) {
            Font f = g.getFont();
            g.setFont(new Font("Arial", Font.BOLD, 40));
            bullets.clear();
            g.drawString("PLAYER 2 WINS!!", 200, 300);
            g.setFont(f);
            loseP1 = true;
        }

        g.setColor(c);

        homeTank.draw(g);
        homeTank2.draw(g);
        

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
            m.draw(g);
           
        }

            for (int j = 0; j < otherWall.size(); j++) {
                BreakableWall cw = otherWall.get(j);
                cw.draw(g);
            }
            for (int j = 0; j < unbreakableWall.size(); j++) {
                Wall mw = unbreakableWall.get(j);
                mw.draw(g);
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
        homeTank2.collideWithTanks(homeTank);
        

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

    }

    public TankClient() {

        jmb = new MenuBar();
        jm1 = new Menu("Game");
        jm2 = new Menu("Pause/Continue");
        
        jm1.setFont(new Font("Arial", Font.BOLD, 15));
        jm2.setFont(new Font("Arial", Font.BOLD, 15));
        jmi1 = new MenuItem("New Game");
        jmi2 = new MenuItem("Exit");
        jmi3 = new MenuItem("Stop");
        jmi4 = new MenuItem("Continue");

        jmi1.setFont(new Font("Arial", Font.BOLD, 15));
        jmi2.setFont(new Font("Arial", Font.BOLD, 15));
        jmi3.setFont(new Font("Arial", Font.BOLD, 15));
        jmi4.setFont(new Font("Arial", Font.BOLD, 15));

        jm1.add(jmi1);
        jm1.add(jmi2);
        jm2.add(jmi3);
        jm2.add(jmi4);

        jmb.add(jm1);
        jmb.add(jm2);

        jmi1.addActionListener(this);
        jmi1.setActionCommand("NewGame");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("Exit");
        jmi3.addActionListener(this);
        jmi3.setActionCommand("Stop");
        jmi4.addActionListener(this);
        jmi4.setActionCommand("Continue");

        this.setMenuBar(jmb);
        this.setVisible(true);

        for (int i = 0; i < 32; i++) {
            if (i < 2) {
                otherWall.add(new BreakableWall(125 + 75 * i, 480, this));
                otherWall.add(new BreakableWall(275 + 75 * i, 400, this));
                otherWall.add(new BreakableWall(275 + 75 * i, 555, this));
                otherWall.add(new BreakableWall(420 + 75 * i, 480, this));
                otherWall.add(new BreakableWall(570 + 75 * i, 400, this));
                otherWall.add(new BreakableWall(570 + 75 * i, 555, this));

            }
                        if (i < 7) {
                otherWall.add(new BreakableWall(100 + 30 * i, 285, this));
                otherWall.add(new BreakableWall(490 + 30 * i, 285, this));

            }
            if (i < 8) {
                otherWall.add(new BreakableWall(125 + 75 * i, 100, this));
                otherWall.add(new BreakableWall(125 + 75 * i, 175, this));

            }

        }

        for (int i = 0; i < 20; i++) {
            if (i < 2) {
                unbreakableWall.add(new Wall(125 + 75 * i, 400, this));
                unbreakableWall.add(new Wall(125 + 75 * i, 555, this));
                unbreakableWall.add(new Wall(275 + 75 * i, 480, this));
                unbreakableWall.add(new Wall(420 + 75 * i, 400, this));
                unbreakableWall.add(new Wall(420 + 75 * i, 555, this));
                unbreakableWall.add(new Wall(570 + 75 * i, 480, this));

            }
            if (i < 9) {
                unbreakableWall.add(new Wall(100 + 30 * i, 250, this));
                unbreakableWall.add(new Wall(430 + 30 * i, 250, this));
                unbreakableWall.add(new Wall(100 + 30 * i, 325, this));
                unbreakableWall.add(new Wall(430 + 30 * i, 325, this));
            }

        }

        this.setSize(Fram_width, Fram_length);
        this.setLocation(280, 50);
        this.setTitle("CSC413 TANK GAME");

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
        TankClient startGame = new TankClient();
        startGame.Player2 = true;
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
            printable = true;
            this.dispose();
            TankClient newGame = new TankClient();
            newGame.Player2 = true;
            new Thread(new PaintThread()).start();

        } else if (e.getActionCommand().endsWith("Stop")) {
            printable = false;
        } else if (e.getActionCommand().equals("Continue")) {
            if (!printable) {
                printable = true;
                new Thread(new PaintThread()).start();
            }
        } else if (e.getActionCommand().equals("Exit")) {
            printable = false;
            System.out.println("break down");
            System.exit(0);

        } 

    }
}
