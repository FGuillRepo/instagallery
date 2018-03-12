package instagallery.app.com.gallery.view;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.R;


/**
 * Custom View Linearlayout  with Lottie animation
 *
 */
public class CollapseLayoutLottieView extends LinearLayout  {

    @BindView(R.id.lottieAnimationView) public LottieAnimationView lottie;

     private String lottieAnimationFolder;
     private String lottieAnimationName;

    public Animator.AnimatorListener animatorListener;

    public CollapseLayoutLottieView(Context context) {
        super(context);
        initializeViews(context);
    }


    public CollapseLayoutLottieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        initializeViews(context);

    }

    public CollapseLayoutLottieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // get view dimensions suggested by layout
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // width should never be more than half of a screen
        int displayWidth = getScreenWidth();
        int displayHeight = getScreenHeight();

        // use the values
        super.onMeasure(
                MeasureSpec.makeMeasureSpec( displayWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(displayHeight/(int) 2.9, MeasureSpec.EXACTLY)
        );
    }


    private void setupAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CollapseLayoutLottieView, 0, 0);
        try {
            lottieAnimationFolder = a.getString(R.styleable.CollapseLayoutLottieView_lottieAnimationFolder);
            lottieAnimationName = a.getString(R.styleable.CollapseLayoutLottieView_lottieAnimationName);
        } finally {
            a.recycle();
        }
    }




    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_toolbaranimation, this);
        ButterKnife.bind(this,this);

        lottie.setImageAssetsFolder(lottieAnimationFolder+"/");
        lottie.setAnimation(lottieAnimationName);
    }



    public void startAnimation(){
        lottie.loop(false);
        lottie.playAnimation();
    }

    public LottieAnimationView getLottie() {
        return lottie;
    }

    public void setAnimationListener(Animator.AnimatorListener listener) {
        this.animatorListener = listener;
        lottie.addAnimatorListener(animatorListener);
    }


    public void ReMeasure() {
        invalidate();
        requestLayout();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}