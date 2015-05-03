package jayen.library.rbgcolorpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import java.util.ArrayList;

public class AlphaPickerBar extends BasePickerBar implements Subject, Observer {

    private ArrayList<Observer> observers;

    //methods for the Observer interface

    @Override
    public void update(Subject subject, int value) {
        if (subject instanceof RedPickerBar) {
            redValue = value;
        } else if (subject instanceof GreenPickerBar) {
            greenValue = value;
        } else if (subject instanceof BluePickerBar) {
            blueValue = value;
        }
        shader = new LinearGradient(left, 0f, right, 0f,
                Color.argb(0, redValue, greenValue, blueValue),
                Color.argb(255, redValue, greenValue, blueValue), Shader.TileMode.CLAMP);
        invalidate();
    }

    //Methods for the Subject interface

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, alphaValue);
        }
    }

    public void init() {
        super.init();
        observers = new ArrayList<Observer>();
    }

    public AlphaPickerBar(Context context) {
        super(context);
        init();
    }

    public AlphaPickerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphaPickerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        yPointerPos = (top + bottom) / 2;
        /*
         * We may already have color values therefore we need to
         * set the pointer and the shader to those colors.
         */
        if(noColorSet) {
            xPointerPos = right;
            updateColor(xPointerPos);
            shader = new LinearGradient(left, 0f, right, 0f,
                    Color.argb(0, redValue, greenValue, blueValue),
                    Color.argb(255, redValue, greenValue, blueValue), Shader.TileMode.CLAMP);
            noColorSet = false;
        }
        else {
            xPointerPos = Math.round((alphaValue/255f) * barWidth + left);
            shader = new LinearGradient(left, 0f, right, 0f,
                    Color.argb(0, redValue, greenValue, blueValue),
                    Color.argb(255, redValue, greenValue, blueValue), Shader.TileMode.CLAMP);
        }
        invalidate();
    }

    public void updateColor(float xPointerPos) {
        alphaValue = Math.round(((xPointerPos - left) / barWidth) * 255f);
        this.notifyObservers();
    }

    @Override
    public int getColorValue() {
        return alphaValue;
    }

    @Override
    public void setColor(int alphaValue, int redValue, int greenValue, int blueValue) {
        noColorSet = false;
        super.setColor(alphaValue, redValue, greenValue, blueValue);
    }
}