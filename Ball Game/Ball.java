import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/*<applet code = "Ball" width=600 height = 800>
</applet>
*/

public class Ball extends Applet implements Runnable, KeyListener
{
        int kx=250, ky=250;//initial coordinates of ball
        int r1= 30;//initial radius of ball
        Thread t=null;
        int c=3;// this is used for direction
        boolean stopFlag;
        boolean flag=false;//flag represents if the ball grabbed the seed or not
        int x1=30, y1=60;//intial coordinates of the seed
        int life= 5;//initial number of lives
        long score = 0;
        int sc=1;//determines the actual speed number and is thus used for score calculation
        long high=0;//initial highscore
        int u=6;//this is used for time delay
                //the greater the value, the slower the ball
        boolean f2=true;

        public void init()
        {
                addKeyListener(this);
                requestFocus();
        }

        public void start()
        {
                t = new Thread(this);
                stopFlag=false;
                if(!f2)t.start();

        }
        public void keyPressed (KeyEvent ke)
        {
                int key = ke.getKeyCode();
                switch(key)
                {       case KeyEvent.VK_F2: start();break;
                        case KeyEvent.VK_UP: c=1;showStatus("UP");break; //going up
                        case KeyEvent.VK_DOWN: c=2;showStatus("DOWN");break; //going down
                        case KeyEvent.VK_LEFT: c=3;showStatus("LEFT");break; //going left
                        case KeyEvent.VK_RIGHT: c=4;showStatus("RIGHT");break; //going right
                        case KeyEvent.VK_F1: life=5;score=0;r1=30;start();u=6;break; //F1 is restart
                        case KeyEvent.VK_SHIFT: sc=sc+1;u--;if(u==0) u=6;       //u-- means increasing speed by
                                                                                //decreasing sleep time
                }
        }

        public void run()
        {
                for(;;)
                {       try{
                        repaint();
                        switch(c)
                        {
                                case 1: ky--; break;
                                case 2: ky++; break;
                                case 3: kx--; break;
                                case 4: kx++;break;

                        }Thread.sleep(u);//the more the u, the more delay and hence the ball moves slower
                        if(ky==(500-r1)) {life--;c=1;} //hitting the bottom wall
                        if(ky==0) {life--;c=2;} //hitting the top wall
                        if(kx==(500-r1)) {life--;c=3;} //hitting the right wall
                        if(kx==0) { c=4; life--;} //hitting the left wall

                        //flag denotes if I have already captured the seed
                        //as soon as the seed comes under the ball, i set it to true which then
                        // changes the position of the seed and increases radius of ball (see paint())

                        if (x1>kx-10&&x1<(kx+r1)&&y1>ky-10&&y1<(ky+r1))
                                {flag=true;score=(score+sc*(r1*2));repaint();}  //the more the sc,
                                                                                //the greater score jump
                        if(life==-1) {repaint();stopFlag=true;}
                        if(stopFlag) break;
                        }catch(InterruptedException e) {}
                }
        }
        public void keyReleased(KeyEvent ke) {}
        public void keyTyped(KeyEvent ke) {}


        public void stop() {
        stopFlag = true;
        t = null;
        }

public void paint(Graphics g)
{
        if(f2)
        {//setFont(new Font("SansSerif", Font.PLAIN, 50));
        g.drawString("GRAB THEM BALLS!",150, 200);
        g.drawString("- Developed by Anurag Choudhary",145, 225);
        //setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Press F2 to start the game.", 150, 300); f2=false;}
        Random x= new Random();

        //caught the seed
        if(flag)
        {       r1=r1+5;//increase radius of ball
                x1= 15+x.nextInt(440);//randomize x coordinate of seed within 400 pixels
                y1 = 15+x.nextInt(440);//randomize y coordinate of seed within 440 pixels
                flag=false;
        }
        g.setColor(Color.blue);
        g.fillOval(x1, y1, 10, 10);
        g.setColor(Color.green);
        g.fillOval(kx, ky, r1, r1);
        g.setColor(Color.black);
        g.drawLine(0, 500, 500, 500);
        g.drawLine(0, 501, 500, 501);
        g.drawLine(0,0,0,500);
        g.drawLine(500,0,500,500);
        g.drawLine(1,0,1,500);
        g.drawLine(499,0,499,500);
        g.drawLine(0,0,500,0);
        g.drawLine(0,1,500,1);

        g.drawString("Speed Setting: " + (7-u) + "x", 350, 50);
        g.drawString("You lose lives on hitting the walls.",20, 525);
        g.drawString("Remaining lives: ", 100, 550);
        g.drawString("Score: "+score, 100, 565);
        g.drawString("Press SHIFT to increase level and multiply your score!", 100, 580);
        if(life==-1)
        {
        if(score>high||score==high)
                {high=score;g.drawString("New Highscore!", 150, 275);}
        else g.drawString("Highscore: "+ high, 150, 275);
        g.drawString("Final score: "+score, 150, 250);

        g.drawString("Press F1 to restart the game!", 150, 300);}
        g.setColor(Color.red);

        for(int i =1; i<=life; i++)
        {
                g.fillOval(200+i*20, 540, 10, 10);
        }
}
}
