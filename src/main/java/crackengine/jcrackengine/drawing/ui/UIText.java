package crackengine.jcrackengine.drawing.ui;

import crackengine.jcrackengine.math.Coordinate;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class UIText extends UIComponent {
    private String text="";
    private Font font = new Font("Arial", 12);
    private Color color = Color.BLACK;
    private TextAlignment align = TextAlignment.LEFT;
    private VPos baseline = VPos.TOP;


    public UIText(String text) {
        this.text = text;
    }

    public UIText(Coordinate position, String text){
        this.position = position;
        this.text = text;
    }

    public UIText(){}

    public Coordinate getPosition(){
        return position;
    }

    public UIText setPosition(Coordinate position){
        this.position = position;
        return this;
    }

    public Font getFont(){
        return font;
    }

    public UIText setFont(Font font){
        this.font = font;
        return this;
    }

    public Color getColor(){
        return color;
    }
    public UIText setColor(Color color){
        this.color = color;
        return this;
    }

    public String getText(){
        return text;
    }

    public UIText setText(String text){
        this.text=text;
        return this;
    }

    public TextAlignment getAlign() {
        return align;
    }

    public UIText setAlign(TextAlignment align) {
        this.align = align;
        return this;
    }

    public VPos getBaseline() {
        return baseline;
    }

    public UIText setBaseline(VPos baseline) {
        this.baseline = baseline;
        return this;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(color);
        g.setFont(font);
        g.setTextAlign(align);
        g.setTextBaseline(baseline);
        g.fillText(text, position.x, position.y);
    }
}
