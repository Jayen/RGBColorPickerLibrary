package jayen.app.rgbcolorpickerapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import jayen.library.rbgcolorpicker.ColorPickerDialog;


public class Demo extends Activity implements ColorPickerDialog.ColorPickerDialogListener {

    private ImageView iv;
    private int alphaValue = 255;
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        iv = (ImageView) findViewById(R.id.colourIV);
        iv.setBackgroundColor(Color.argb(alphaValue,redValue,greenValue,blueValue));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPickClicked(View view) {
        ColorPickerDialog colorPickerDialog =  new ColorPickerDialog();
        colorPickerDialog.setInitialColor(alphaValue, redValue, greenValue, blueValue);
        colorPickerDialog.show(getFragmentManager(), "tag");
    }

    // The colour picker dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the ColorPickerDialog.ColourPickerDialogListener interface
    @Override
    public void onColorConfirmed(DialogFragment dialog,int alpha, int red, int green, int blue) {
        alphaValue = alpha;
        redValue = red;
        greenValue = green;
        blueValue = blue;
        Toast.makeText(getApplicationContext(),"User confirmed colour"+Color.argb(alpha,red,green,blue),Toast.LENGTH_LONG).show();
        iv.setBackgroundColor(Color.argb(alpha,red,green,blue));
    }

    @Override
    public void onCancelled(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(),"User cancelled",Toast.LENGTH_LONG).show();
    }
}
