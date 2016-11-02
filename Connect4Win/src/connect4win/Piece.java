package connect4win;
import java.awt.*;

public class Piece {
    private Color color;
    private int value;
    Piece(Color _color)
    {
        color = _color;
        value = (int)((Math.random()*5)+1);

    }
    Color getColor()
    {
        return (color);
    }
    int getValue()
    {
        return (value);
    }
}
