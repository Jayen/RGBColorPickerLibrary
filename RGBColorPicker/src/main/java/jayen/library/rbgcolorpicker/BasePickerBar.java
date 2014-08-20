package jayen.library.rbgcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class is the base class for the different
 * colour pickers. The class defines and implements
 * common methods for all the different picker types.
 */
public abstract class BasePickerBar extends View {

    RectF bar = new RectF();
    Paint barPaint;
    Shader shader;

    Paint PointerPaint;
    Paint circleRingPaint;

    float viewHeight;
    float viewWidth;

    float left;
    float right;
    float top;
    float bottom;
    float barHeight;
    float barWidth;

    float xPointerPos;
    float yPointerPos;
    float pointerRadius;
    float pointerRingRadius;

    //variables which store the current Color values
    int alphaValue;
    int redValue;
    int greenValue;
    int blueValue;

    boolean isPointerMoving = false;
    boolean orientationChange = false;

    public void init() {
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        alphaValue = 255;
        redValue = 0;
        greenValue = 0;
        blueValue = 0;

        //setup the pointer info
        circleRingPaint.setColor(Color.BLACK);
        circleRingPaint.setAlpha(100);
    }

    public BasePickerBar(Context context) {
        super(context);
    }

    public BasePickerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePickerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {;
        barPaint.setShader(shader);
        canvas.drawRect(bar, barPaint);
        //draw circle ring
        canvas.drawCircle(xPointerPos,yPointerPos,pointerRingRadius,circleRingPaint);
        //draw pointer
        PointerPaint.setColor(Color.argb(alphaValue,redValue,greenValue,blueValue));
        canvas.drawCircle(xPointerPos, yPointerPos, pointerRadius,PointerPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        top = viewHeight*0.25f;
        bottom = viewHeight * 0.75f;

        barHeight = bottom - top;
        pointerRadius = barHeight * 0.75f;
        pointerRingRadius = barHeight * 0.95f;

        left = pointerRingRadius;
        right = (viewWidth - pointerRingRadius);
        barWidth = right-left;

        bar.set(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchXCoordinate = event.getX();
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            isPointerMoving = true;
            //if touch is near the pointer then update view
            if(touchXCoordinate>=xPointerPos-pointerRingRadius &&
                    touchXCoordinate<=xPointerPos+pointerRingRadius &&
                    touchXCoordinate>=left-1 && touchXCoordinate<=right+1) {
                xPointerPos = (int) touchXCoordinate;
                updateColor(xPointerPos);
                invalidate();
            }
        }
        else if(event.getAction()==MotionEvent.ACTION_MOVE) {
            //user has dragged pointer
            if(isPointerMoving) {
                if(touchXCoordinate>=left-1 && touchXCoordinate<=right+1) {
                    xPointerPos = (int) touchXCoordinate;
                    updateColor(xPointerPos);
                    invalidate();
                }
            }
        }
        else if(event.getAction()==MotionEvent.ACTION_UP) {
            isPointerMoving = false;
        }
        return true;
    }

    /**
     * Update the color depending on the
     * current position of the pointer.
     * @param xPointerPos
     */
    public abstract void updateColor(float xPointerPos);

    public abstract int getColorValue();

    public void setColor(int alphaValue, int redValue, int greenValue, int blueValue) {
        this.alphaValue = alphaValue;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }
}
