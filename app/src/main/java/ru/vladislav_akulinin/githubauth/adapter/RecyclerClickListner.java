package ru.vladislav_akulinin.githubauth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class RecyclerClickListner implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector; //для того, чтобы понять, что произошел клик
    private GestureDetector.SimpleOnGestureListener gestureListener =
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e){
                    return true;
                }
            };

    public RecyclerClickListner(Context context){
        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if(gestureDetector.onTouchEvent(motionEvent)){
            //для определеия нажатого элемента
            View clickedChild = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if(clickedChild != null && !clickedChild.dispatchTouchEvent(motionEvent)){
                //позиция
                int clickedPosition = recyclerView.getChildPosition(clickedChild);
                if(clickedPosition != RecyclerView.NO_POSITION){
                    onItemClick(recyclerView, clickedChild, clickedPosition);
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void onItemClick(RecyclerView recyclerView, View itemView, int position);

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
