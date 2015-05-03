package jayen.library.rbgcolorpicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 The MIT License (MIT)

 Copyright (c) [2015] [Jayen kumar Jaentilal]

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

/**
 * This class creates a colorPicker using the base
 * class DialogFragment.
 * To show this dialog, first instantiate then
 * use the show method to display the dialog.
 */
public class ColorPickerDialog extends DialogFragment implements Observer {


    private boolean initialColor;

    /* The activity/fragment that creates an instance of this dialog fragment must
                 * implement this interface in order to receive event callbacks.
                 * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ColorPickerDialogListener {
        public void onColorConfirmed(DialogFragment dialog,int alpha, int red, int green, int blue);

        public void onCancelled(DialogFragment dialog);
    }

    //methods for the Observer interface

    @Override
    public void update(Subject subject, int value) {
        if(subject instanceof AlphaPickerBar) {
            alphaValue = value;
        }
        else if(subject instanceof RedPickerBar) {
            redValue = value;
        }
        else if(subject instanceof GreenPickerBar) {
            greenValue = value;
        }
        else if(subject instanceof BluePickerBar) {
            blueValue = value;
        }
        currentColorTV.setBackgroundColor(Color.argb(alphaValue,redValue,greenValue,blueValue));
        currentColorTV.setText("#"+Integer.toHexString(alphaValue)+Integer.toHexString(redValue)+
                                Integer.toHexString(greenValue)+Integer.toHexString(blueValue));
    }


    private ColorPickerDialogListener mListener;

    private TextView currentColorTV;
    private AlphaPickerBar alphaPickerBar;
    private RedPickerBar redPickerBar;
    private BluePickerBar bluePickerBar;
    private GreenPickerBar greenPickerBar;

    private int alphaValue;
    private int redValue;
    private int blueValue;
    private int greenValue;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity/fragment implements the callback interface
        try {
            // Instantiate the ColorPickerDialogListener so we can send events to the host
            // If there is a target fragment then use that as the listener.
            mListener = (ColorPickerDialogListener) this.getTargetFragment();
            if(mListener==null) {
                //no target fragment so use activity
                mListener = (ColorPickerDialogListener) activity;
            }
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ColorPickerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.color_picker, null);
        builder.setView(view);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user confirmed
                        alphaValue = alphaPickerBar.getColorValue();
                        redValue = redPickerBar.getColorValue();
                        greenValue = greenPickerBar.getColorValue();
                        blueValue = bluePickerBar.getColorValue();
                        mListener.onColorConfirmed(ColorPickerDialog.this,alphaValue,redValue,greenValue,blueValue);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled
                        mListener.onCancelled(ColorPickerDialog.this);
                    }
                });

        currentColorTV = (TextView) view.findViewById(R.id.currentColorTV);

        alphaPickerBar = (AlphaPickerBar) view.findViewById(R.id.alphaBar);
        redPickerBar = (RedPickerBar) view.findViewById(R.id.redBar);
        greenPickerBar = (GreenPickerBar) view.findViewById(R.id.greenBar);
        bluePickerBar = (BluePickerBar) view.findViewById(R.id.blueBar);

        alphaPickerBar.register(this);
        alphaPickerBar.register(redPickerBar);
        alphaPickerBar.register(greenPickerBar);
        alphaPickerBar.register(bluePickerBar);

        redPickerBar.register(this);
        redPickerBar.register(alphaPickerBar);
        redPickerBar.register(greenPickerBar);
        redPickerBar.register(bluePickerBar);

        greenPickerBar.register(this);
        greenPickerBar.register(alphaPickerBar);
        greenPickerBar.register(redPickerBar);
        greenPickerBar.register(bluePickerBar);

        bluePickerBar.register(this);
        bluePickerBar.register(alphaPickerBar);
        bluePickerBar.register(redPickerBar);
        bluePickerBar.register(greenPickerBar);

        Dialog dialog = builder.create();

        if(initialColor) {
            alphaPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            redPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            greenPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            bluePickerBar.setColor(alphaValue,redValue,greenValue,blueValue);

            currentColorTV.setBackgroundColor(Color.argb(alphaValue,redValue,greenValue,blueValue));
            currentColorTV.setText("#"+Integer.toHexString(alphaValue)+Integer.toHexString(redValue)+
                                     Integer.toHexString(greenValue)+Integer.toHexString(blueValue));
            initialColor = false;
        }

        if(savedInstanceState!=null) {
            alphaValue = savedInstanceState.getInt("alpha");
            redValue = savedInstanceState.getInt("red");
            greenValue = savedInstanceState.getInt("green");
            blueValue = savedInstanceState.getInt("blue");
            alphaPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            redPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            greenPickerBar.setColor(alphaValue,redValue,greenValue,blueValue);
            bluePickerBar.setColor(alphaValue,redValue,greenValue,blueValue);

            currentColorTV.setBackgroundColor(Color.argb(alphaValue,redValue,greenValue,blueValue));
            currentColorTV.setText("#"+Integer.toHexString(alphaValue)+Integer.toHexString(redValue)+
                                     Integer.toHexString(greenValue)+Integer.toHexString(blueValue));
        }

        return dialog;
    }

    public void setInitialColor(int alphaValue, int redValue, int greenValue, int blueValue) {
        initialColor = true;
        this.alphaValue = alphaValue;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("alpha",alphaValue);
        outState.putInt("red",redValue);
        outState.putInt("green",greenValue);
        outState.putInt("blue",blueValue);
    }
}
