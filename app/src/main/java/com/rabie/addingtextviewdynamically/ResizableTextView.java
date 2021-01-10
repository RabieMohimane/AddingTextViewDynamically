package com.rabie.addingtextviewdynamically;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class ResizableTextView extends androidx.appcompat.widget.AppCompatTextView implements View.OnTouchListener {
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    float mLastTouchX, mLastTouchY, mPosX, mPosY,firstX,firstY;

    public ResizableTextView(@NonNull Context context) {
        super(context);
    }

    public ResizableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        //  mScaleDetector.onTouchEvent(ev);

        final int action = ev.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = ev.getActionIndex();
                final float x = ev.getX( pointerIndex);
                final float y = ev.getY( pointerIndex);
                if(x<20 && y<20 || x<20 && y>this.getLayoutParams().height-20 || x>this.getLayoutParams().width-20 && y>this.getLayoutParams().height-20 ) {
                    firstX = x;
                    firstY = y;

                    // Remember where we started (for dragging)
                    mLastTouchX = x;
                    mLastTouchY = y;
                    // Save the ID of this pointer (for dragging)
                    mActivePointerId = ev.getPointerId(0);
                }else{
                    return  false;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.e("ACTION","ACTION_MOVE");
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        ev.findPointerIndex( mActivePointerId);

                final float x = ev.getX( pointerIndex);
                final float y = ev.getY( pointerIndex);

                // Calculate the distance moved
                Log.e("ACTION","x="+x+" x+ width/2= "+x+((float)this.getLayoutParams().width/2));
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;
                Log.e("ACTION2","x "+x+" y "+y);
                Log.e("ACTION2","dx "+dx+" dy "+dy);
                Log.e("ACTION2","x+width/2 "+(int)this.getLayoutParams().width/2);
                Log.e("ACTION2","y+height/2 "+(int)this.getLayoutParams().height/2);

               // this.getLayoutParams().height += dy;
                if(firstX<(float)this.getLayoutParams().width/2){
                    Log.e("ACTION3-1","new width"+(this.getLayoutParams().width -  dx));
                    if(this.getLayoutParams().width -  dx>60.0 &&this.getLayoutParams().width -  dx<1000.0)
                    this.getLayoutParams().width -=  dx;
                }else{
                    Log.e("ACTION3-2","new width"+(this.getLayoutParams().width +  dx));
                    if(this.getLayoutParams().width +  dx<1000.0 && this.getLayoutParams().width +  dx>60.0)
                    this.getLayoutParams().width +=  dx;
                }
                if(firstY<(float)this.getLayoutParams().height/2){
                    Log.e("ACTION3-3","new height"+(this.getLayoutParams().height -  dy));
                    if((this.getLayoutParams().height -  dy)>60.0 &&(this.getLayoutParams().height -  dy)<1000.0) {
                        this.getLayoutParams().height -= dy;
                    }
                }else{
                    Log.e("ACTION3-4","new height"+(this.getLayoutParams().height + dy));
                    Log.e("ACTION3-4 result if","if"+(this.getLayoutParams().height + dy<1000.0 && this.getLayoutParams().height + dy>60.0));

                    if(this.getLayoutParams().height + dy<1000.0 && this.getLayoutParams().height + dy>60.0) {
                        this.getLayoutParams().height += dy;
                        Log.e("ACTION3-4 ","inside if");
                    }
                }

                this.requestLayout();
                invalidate();

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;
                firstX=x;
                firstY=y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId( pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX( newPointerIndex);
                    mLastTouchY = ev.getY( newPointerIndex);
                    mActivePointerId = ev.getPointerId( newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        return false;
    }

    @Override
    public boolean performClick() {
        Log.e("RESIZE", "perform click");
        super.performClick();
        return true;
    }
}
