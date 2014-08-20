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
public class RedPickerBar extends BasePickerBar implements Subject, Observer {

    private ArrayList<Observer> observers;

    //methods for the Observer interface

    @Override
    public void update(Subject subject,int value) {
        if(subject instanceof AlphaPickerBar) {
            alphaValue = value;
        }
        else if(subject instanceof GreenPickerBar) {
            greenValue = value;
        }
        else if(subject instanceof BluePickerBar) {
            blueValue = value;
        }
        shader = new LinearGradient(left,0f,right,0f,
                Color.argb(alphaValue,0,greenValue,blueValue),
                Color.argb(alphaValue,255,greenValue,blueValue), Shader.TileMode.CLAMP);
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
        for(Observer observer: observers) {
            observer.update(this,redValue);
        }
    }

    public void init() {
        super.init();
        observers = new ArrayList<Observer>();
    }

    public RedPickerBar(Context context) {
        super(context);
        init();
    }

    public RedPickerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedPickerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        yPointerPos = (top + bottom) / 2;
        /*
         * If there was a orientation change, we may
         * already have color values therefore we need to
         * set the pointer and the shader to those colors.
         */
        if(!orientationChange) {
            xPointerPos = barWidth / 2;
            updateColor(xPointerPos);
            shader = new LinearGradient(0f,0f,right,0f,
                    Color.argb(alphaValue,0,greenValue,blueValue),
                    Color.argb(alphaValue,255,greenValue,blueValue), Shader.TileMode.CLAMP);
        }
        else {
            xPointerPos = Math.round((redValue/255f) * barWidth + left);
            shader = new LinearGradient(left, 0f, right, 0f,
                    Color.argb(alphaValue, 0, greenValue, blueValue),
                    Color.argb(alphaValue, 255, greenValue, blueValue), Shader.TileMode.CLAMP);
            invalidate();
        }
    }

    public void updateColor(float xPointerPos) {
        redValue = Math.round(((xPointerPos-left)/barWidth)*255f);
        this.notifyObservers();
    }

    @Override
    public int getColorValue() {
        return redValue;
    }

    @Override
    public void setColor(int alphaValue, int redValue, int greenValue, int blueValue) {
        orientationChange = true;
        super.setColor(alphaValue, redValue, greenValue, blueValue);
    }
}
