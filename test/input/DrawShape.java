package input;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import input.components.FigureNode;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.parser.JSONParser;

public class DrawShape {
  private SegmentNodeDatabase _segments;

  public DrawShape(SegmentNodeDatabase segments) {
    _segments = segments;
  }

  public static void main(String[] args) {
    String filename = args[0];

    JSONParser parser = new JSONParser();
    String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

    FigureNode figure = (FigureNode) parser.parse(figureStr);
    DrawShape viewer = new DrawShape(figure.getSegments());
    viewer.launch();
  }

  public void launch() {
    // Create a frame
    Frame frame = new Frame();

    // Add a component with a custom paint method
    frame.add(new CustomPaintComponent());

    // Display the frame
    int frameWidth = 800;
    int frameHeight = 800;

    frame.setSize(frameWidth, frameHeight);
    frame.setVisible(true);
    frame.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowClosing(WindowEvent e) {
        frame.dispose();
      }

      @Override
      public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }

    });
  }

  /**
   * To draw on the screen, it is first necessary to subclass a Component
   * and override its paint() method. The paint() method is automatically called
   * by the windowing system whenever component's area needs to be repainted.
   */
  class CustomPaintComponent extends Component {
    private static final int SCALE = 20;

    private int scale(int val, boolean flip, int size) {
      int offset = size / 2 + 1;

      int scaledVal = offset + ((size / (SCALE * 2)) * (val * (flip ? -1 : 1)));

      return scaledVal;
    }

    private int scaleX(int x) {
      int w = getSize().width - 1;

      return scale(x, false, w);
    }

    private int scaleY(int y) {
      int h = getSize().height - 1;

      return scale(y, true, h);
    }

    public void paint(Graphics g) {

      // Retrieve the graphics context; this object is used to paint shapes
      Graphics2D g2d = (Graphics2D) g;

      // Draw an oval that fills the window
      int x = 0;
      int y = 0;

      int w = getSize().width - 1;
      int h = getSize().height - 1;

      /**
       * The coordinate system of a graphics context is such that the origin is at the
       * northwest corner and x-axis increases toward the right while the y-axis
       * increases
       * toward the bottom.
       */

      // grid
      g2d.drawLine(w / 2, y, w / 2, h);
      g2d.drawLine(x, h / 2, w, h / 2);

      // g2d.drawLine(
      // scaleX(0),
      // scaleY(0),
      // scaleX(10),
      // scaleY(10));

      // shape lines
      for (SegmentNode segment : _segments.asUniqueSegmentList()) {
        g2d.setColor(java.awt.Color.BLACK);
        g2d.drawLine(
            scaleX((int) (segment.getPoint1().getX() + 0.5)),
            scaleY((int) (segment.getPoint1().getY() + 0.5)),
            scaleX((int) (segment.getPoint2().getX() + 0.5)),
            scaleY((int) (segment.getPoint2().getY() + 0.5)));
        g2d.setColor(java.awt.Color.RED);
        g2d.drawOval(
            scaleX((int) (segment.getPoint1().getX() + 0.5)),
            scaleY((int) (segment.getPoint1().getY() + 0.5)),
            1, 1);
        g2d.drawOval(
            scaleX((int) (segment.getPoint2().getX() + 0.5)),
            scaleY((int) (segment.getPoint2().getY() + 0.5)),
            2, 2);
        g2d.drawString(
            segment.getPoint1().getName(),
            scaleX((int) (segment.getPoint1().getX() + 0.5)),
            scaleY((int) (segment.getPoint1().getY() + 0.5)));
        g2d.drawString(
            segment.getPoint2().getName(),
            scaleX((int) (segment.getPoint2().getX() + 0.5)),
            scaleY((int) (segment.getPoint2().getY() + 0.5)));
      }
    }
  }
}
