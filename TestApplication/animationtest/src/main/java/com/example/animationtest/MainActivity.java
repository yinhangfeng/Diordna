package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.target_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.View_Property_Animator) {
            viewPropertyAnimator();
            return true;
        } else if(id == R.id.Value_Animator) {
            valueAnimator();
            return true;
        } else if(id == R.id.Object_Animator) {
            objectAnimator();
            return true;
        } else if(id == R.id.Value_Animator1) {
            valueAnimator1();
            return true;
        } else if(id == R.id.XML_Animator1) {
            xmlAnimator1();
            return true;
        } else if(id == R.id.XML_Animator2) {
            xmlAnimator2();
            return true;
        } else if(id == R.id.fragment_animator) {
            fragmentAnimator();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fragmentAnimator() {
        getFragmentManager().beginTransaction()
                //.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                //Fragment切换动画资源必须是ObjectAnimator
                .setCustomAnimations(R.animator.card_flip_right_in1, R.animator.card_flip_left_out1)
                .add(R.id.fragment_container, new BlankFragment())
                .addToBackStack(null)
                .commit();
    }

    private void xmlAnimator2() {
        reset();
        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator2);
        //对于ValueAnimator setTarget是无效的 所以从XML加载之后需addUpdateListener中处理
        valueAnimator.setTarget(view);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate animation=" + animation + " AnimatedFraction=" + animation.getAnimatedFraction());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
        valueAnimator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                Log.d(TAG, "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                Log.d(TAG, "onAnimationResume");
            }
        });
        valueAnimator.start();
    }

    private void xmlAnimator1() {
        reset();
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator1);
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    private void reset() {
        view.setTranslationX(0);
        view.setTranslationY(0);
        //API 21
        //view.setTranslationZ(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setRotation(0);
        view.setRotationX(0);
        view.setRotationY(0);
    }

    private void valueAnimator1() {
        reset();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setStartDelay(1000);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(2);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("Float", 0, 0.2f, 0.5f, 0.8f, 1f);
        propertyValuesHolder1.setEvaluator(new FloatEvaluator());

        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofInt("Integer", 0, 1, 2, 3);
        valueAnimator.setValues(propertyValuesHolder1, propertyValuesHolder2);


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate animation=" + animation + " AnimatedFraction=" + animation.getAnimatedFraction());
                Float float1 = (Float) animation.getAnimatedValue("Float");
                view.setRotationY(float1 * 360);
                Integer integer1 = (Integer) animation.getAnimatedValue("Integer");
                view.setTranslationX(integer1 * 150);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
        valueAnimator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                Log.d(TAG, "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                Log.d(TAG, "onAnimationResume");
            }
        });
        valueAnimator.start();
    }

    private void objectAnimator() {
        reset();
        ObjectAnimator objectAnimator = new ObjectAnimator();
        objectAnimator.setTarget(view);
        objectAnimator.setPropertyName("RotationY");
        objectAnimator.setFloatValues(0, 120, 360);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(2);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setStartDelay(500);

        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate animation=" + animation + " AnimatedFraction=" + animation.getAnimatedFraction());
            }
        });

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
        objectAnimator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                Log.d(TAG, "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                Log.d(TAG, "onAnimationResume");
            }
        });
        objectAnimator.start();
    }

    private void valueAnimator() {
        reset();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.2f, 0.5f, 0.8f, 1f);
        valueAnimator.setStartDelay(1000);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(2);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate animation=" + animation + " AnimatedFraction=" + animation.getAnimatedFraction());
                float fraction = animation.getAnimatedFraction();
                view.setRotationY(360 * fraction);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
        valueAnimator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                Log.d(TAG, "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                Log.d(TAG, "onAnimationResume");
            }
        });
        valueAnimator.start();
    }

    private void viewPropertyAnimator() {
        reset();
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        viewPropertyAnimator.translationX(300);
        viewPropertyAnimator.translationY(500);
        //API 21
        //viewPropertyAnimator.translationZ(30);
        viewPropertyAnimator.scaleX(2);
        viewPropertyAnimator.scaleY(0.8f);
        viewPropertyAnimator.rotation(30);
        viewPropertyAnimator.rotationX(35);
        viewPropertyAnimator.rotationY(50);
        viewPropertyAnimator.alpha(0.5f);

        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setStartDelay(500);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
        //API 19
        viewPropertyAnimator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate animation=" + animation + " AnimatedFraction=" + animation.getAnimatedFraction());
            }
        });
    }
}
