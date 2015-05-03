package jayen.library.rbgcolorpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * This class was created by jayen on 06/08/14.
 */
public class BluePickerBar extends BasePickerBar implements Subject, Observer{

    private ArrayList<Observer> observers;

    //methods for the Observer interface

    @Override
    public void update(Subject subject,int value) {
        if(subject instanceof AlphaPickerBar) {
            alphaValue = value;
        }
        else if(subject instanceof RedPickerBar) {
            redValue = value;
        }
        else if(subject instanceof GreenPickerBar) {
            greenValue = value;
        }
        shader = new LinearGradient(left,0f,right,0f,
                Color.argb(alphaValue,redValue,greenValue,0),
                Color.argb(alphaValue,redValue,greenValue,255), Shader.TileMode.CLAMP);
        invalidate();
    }

    //Methods for the Subject interface3

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
        for(Observer observer: observers) {
            observer.update(this,blueValue);
        }
    }

    public void init() {
        super.init();
        observers = new ArrayList<Observer>();
    }

    public BluePickerBar(Context context) {
        super(context);
        init();
    }

    public BluePickerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BluePickerBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
            xPointerPos = barWidth / 2;
            updateColor(xPointerPos);
            shader = new LinearGradient(0f,0f,right,0f,
                    Color.argb(alphaValue,redValue,greenValue,0),
                    Color.argb(alphaValue,redValue,greenValue,255), Shader.TileMode.CLAMP);
            noColorSet = false;
        }
        else {
            xPointerPos = Math.round((blueValue/255f) * barWidth + left);
            shader = new LinearGradient(left, 0f, right, 0f,
                    Color.argb(alphaValue, redValue, greenValue, 0),
                    Color.argb(alphaValue, redValue, greenValue, 255), Shader.TileMode.CLAMP);
        }
        invalidate();
    }

    public void updateColor(float xPointerPos) {
        blueValue = Math.round(((xPointerPos-left)/barWidth)*255f);
        this.notifyObservers();
    }

    @Override
    public int getColorValue() {
        return blueValue;
    }

    @Override
    public void setColor(int alphaValue, int redValue, int greenValue, int blueValue) {
        noColorSet = false;
        super.setColor(alphaValue, redValue, greenValue, blueValue);
    }
}