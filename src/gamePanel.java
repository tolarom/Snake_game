import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGTH = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGTH)*UNIT_SIZE;
    static int DELAY = 200;
        final int x[] = new int[GAME_UNITS];
        final int y[] = new int[GAME_UNITS];
        int bodyParts = 6;
        int appleEaten;
        int appleX;
        int appleY;
        char direction = 'd';
        boolean running = false;
        Timer timer;
        Random random;
    
        gamePanel () {
    
            random = new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
            this.setBackground(Color.BLACK);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            startGame();
    
        }
        public void startGame () {
            newApple();
            running = true;
            timer = new Timer (DELAY, this);
            timer.start();
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }
        public void draw(Graphics g) {
             
            if(running) {
                for(int i = 0; i < SCREEN_HEIGTH/UNIT_SIZE; i++) {
                    g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGTH);
                    g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
                }
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    
                for(int i = 0; i < bodyParts; i++){
                    if(i==0) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }else {
                        g.setColor(Color.BLUE);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten))/2, g.getFont().getSize());
            }else{
                gameOver(g);
            }
        }
        public void newApple() {
            appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
            appleY = random.nextInt((int)(SCREEN_HEIGTH/UNIT_SIZE))*UNIT_SIZE;
        }
        public void move() {
            for(int i = bodyParts; i>0; i--){
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
    
            switch (direction) {
                case 'u':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'd':
                    y[0] = y[0] + UNIT_SIZE;
                    break;       
                case 'l':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'r':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }
        }
        public void checkApple(){
            if((x[0] == appleX) && (y[0] == appleY)){
                bodyParts++;
                appleEaten++;
                if(appleEaten%2 == 0){
                    DELAY = DELAY - 50;
                }
            newApple();
        }
    }
    public void checkCollision(){
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        if(x[0] < 0){
            running = false;
        }if(x[0] > SCREEN_WIDTH){
            running = false;
        }if(y[0] < 0){
            running = false;
        }if(y[0] > SCREEN_HEIGTH){
            running = false;
        }
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleEaten))/2, g.getFont().getSize());
    
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, (SCREEN_HEIGTH/2));
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed (KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction !='r'){
                        direction = 'l';
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction !='l'){
                        direction = 'r';
                    }
                break;
                case KeyEvent.VK_UP:
                    if(direction !='d'){
                        direction = 'u';
                    }
                break;
                case KeyEvent.VK_DOWN:
                    if(direction !='u'){
                        direction = 'd';
                    }
                break;
            }
        
        }
    }
    
}
