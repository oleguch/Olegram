import java.awt.*;

public class PhotoPanel extends ImagePanel {
    private boolean online;

    public PhotoPanel(Image image, boolean opaque, boolean keepRatio, Insets insets, boolean online) {
        super(image, opaque, keepRatio, insets);
        this.online = online;
    }

    public PhotoPanel(Image image, boolean opaque, boolean keepRatio, int inset, boolean online) {
        super(image, opaque, keepRatio, inset);
        this.online = online;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Colors.getColorBorderUserList());            //Граница сверху-снизу
        g.drawLine(0,0,this.getWidth() - 1, 0);
        g.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,this.getHeight() - 1);
        double onlineSignSize = 0.2;

        //int dSmall = 10;
        //int dx = 2;
//        int rightBorder = 3;
//        int dBig = dSmall + 2*dx;
        int dx = (int)(this.getWidth() * onlineSignSize);
        int dy = (int)(this.getHeight() * onlineSignSize);
        int x = this.getWidth() - dx;
        int y = this.getHeight() - dy;
//        int xBig = this.getWidth() - dBig - rightBorder;
//        int yBig = this.getHeight() - dBig;
//        int xSmall = this.getWidth() - dBig - rightBorder + dx;
//        int ySmall = this.getHeight() - dBig + dx;
        dx -= 3;
        if(online) {
            g.setColor(Colors.getColorOnlineStatusBorder());
          //  g.fillOval(xBig,yBig,dBig, dBig);
            g.fillOval(x,y,dx+4, dx+4);
            g.setColor(Colors.getColorOnlineStatus());
           // g.fillOval(xSmall, ySmall, dSmall, dSmall);
            g.fillOval(x+2, y+2,dx,dx);
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        if(this.online != online) {
            this.online = online;
            repaint();
        }
    }
}
