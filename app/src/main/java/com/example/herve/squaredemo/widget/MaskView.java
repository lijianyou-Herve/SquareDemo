package com.example.herve.squaredemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.herve.squaredemo.R;


/**
 * Created           :Herve on 2016/10/11.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/11
 * @ projectName     :SquareDemo
 * @ version
 */
public class MaskView extends View {


    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    /*背景绘制器*/
    Paint backGroundPaint;
    /*镂空绘制器*/
    Paint cleanSquarePaint;
    /*三角区域绘制器*/
    Paint clearDrawPaint;
    /*虚线绘制器*/
    Paint dashedLinePaint;


    private boolean showMask = true;

    /*默认资源图片*/
    int tipBitmapRes = -1;

    /*位置屬性值*/

    /*镂空部分的宽度*/
    private int childViewWidth = 0;
    /*镂空部分的高度*/
    private int childViewHeight = 0;
    /*镂空部分的左间距*/
    private float hollow_margin_left = 0;
    /*镂空部分的右间距*/
    private float hollow_margin_right = 0;
    /*镂空部分的上间距*/
    private float hollow_margin_top = 0;

    private float hollow_margin_bottom = 0;
    /*是否清空镂空部分，布局预览效果*/
    private boolean hollow_clear_square = true;
    /*遮罩背景的透明值*/
    private float maskView_hollow_alpha = 0;
    /*遮罩背景颜色*/
    private int hollow_mask_color = 0;

    private boolean alignParentBottom = false;
    private boolean alignParentRight = false;

    private boolean centerVertical = false;
    private boolean centerHorizontal = false;

    /**
     * 绘制虚线区域
     */
    private RectF roundRect;

    private float roundRx = 10;
    private float roundRY = 10;

    /**
     * 指示箭头方向
     */
    private int hollow_mask_orientation = 1;


    public MaskView(Context context) {
        this(context, null);
    }

    public MaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.maskView);
        childViewWidth = typedArray.getDimensionPixelSize(R.styleable.maskView_hollow_width, -1);
        childViewHeight = typedArray.getDimensionPixelSize(R.styleable.maskView_hollow_height, -1);

        hollow_margin_top = typedArray.getDimension(R.styleable.maskView_hollow_margin_top, 10);
        hollow_margin_left = typedArray.getDimension(R.styleable.maskView_hollow_margin_left, 10);
        hollow_margin_right = typedArray.getDimension(R.styleable.maskView_hollow_margin_right, 10);

        maskView_hollow_alpha = typedArray.getFloat(R.styleable.maskView_hollow_alpha, 0.5f);
        hollow_mask_color = typedArray.getColor(R.styleable.maskView_hollow_mask_color, Color.BLACK);
        hollow_mask_orientation = typedArray.getInt(R.styleable.maskView_hollow_mask_orientation, 1);
        hollow_clear_square = typedArray.getBoolean(R.styleable.maskView_hollow_clear_square, true);
        tipBitmapRes = typedArray.getResourceId(R.styleable.maskView_hollow_src, R.drawable.create_tip);

        /*控制位置的信息*/
        alignParentBottom = typedArray.getBoolean(R.styleable.maskView_hollow_alignParentBottom, false);
        alignParentRight = typedArray.getBoolean(R.styleable.maskView_hollow_alignParentRight, false);

        centerVertical = typedArray.getBoolean(R.styleable.maskView_hollow_centerVertical, false);
        centerHorizontal = typedArray.getBoolean(R.styleable.maskView_hollow_centerHorizontal, false);

        Log.i(TAG, "MaskView: childViewWidth=" + childViewWidth);
        Log.i(TAG, "MaskView: childViewHeight=" + childViewHeight);
        Log.i(TAG, "MaskView: hollow_margin_left=" + hollow_margin_left);
        Log.i(TAG, "MaskView: hollow_margin_top=" + hollow_margin_top);

        typedArray.recycle();

        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);

        Log.i(TAG, "顺序：onMeasure: 测量View大小");


        setMeasuredDimension(width, height);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }


    public void init(Context context) {
        this.mContext = context;
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!showMask) {
            return;
        }

        Log.i(TAG, "顺序：onDraw: 绘制View视图");

        getViewWH();


        if (roundRect == null) {
            initRoundRectDraftData();
        }

        float rectHeight = roundRect.height();

        float rectWidth = roundRect.width();

        if (alignParentBottom) {
            alignParentBottom = false;
            roundRect.top = getHeight() - rectHeight + hollow_margin_top;
            roundRect.bottom = getHeight() + hollow_margin_top;
        }

        if (alignParentRight) {
            alignParentRight = false;
            roundRect.right = getWidth() + hollow_margin_left - hollow_margin_right;
            roundRect.left = roundRect.right - rectWidth;

        }

        if (centerHorizontal) {
            centerHorizontal = false;
            roundRect.left = getWidth() / 2 - rectWidth / 2 + hollow_margin_left - hollow_margin_right;
            roundRect.right = roundRect.left + rectWidth;
        }

        if (centerVertical) {
            centerVertical = false;
            roundRect.top = getHeight() / 2 - rectHeight / 2 + hollow_margin_top - hollow_margin_bottom;
            roundRect.bottom = roundRect.top + rectHeight;
        }

        //创建一个图层，在图层上演示图形混合后的效果
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        int save = canvas.save(Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        // 创建背景
        drawBackground(canvas);

        //擦拭除所需区域

        if (hollow_clear_square) {
            cleanSquare(canvas);
        }

        /*画虚线*/
        drawdashedLine(canvas);

        /*指示箭头部分*/

        drawDeltaView(canvas);


        /*画Text说明*/
        drawDetailBitmap(canvas);

        // 还原画布
        canvas.restoreToCount(save);
    }

    private void drawDeltaView(Canvas canvas) {


        Path pathDr = new Path();
        float floatW = roundRect.width();
        float floatH = roundRect.height();

        float floatLeft = roundRect.left;
        float floatTop = roundRect.top;


        float x1 = floatW / 4 + floatLeft;
        float y1 = floatH + floatTop;


        float x2 = floatW / 4 + px2dip(mContext, 80) + roundRect.left;
        float y2 = floatH + px2dip(mContext, 100) + floatTop;

        float x3 = floatW / 4 + floatH / 8 + floatLeft + px2dip(mContext, 80);
        float y3 = floatH + floatTop;

        if (hollow_mask_orientation == 2) {
            y1 = floatTop;
            y2 = floatTop - px2dip(mContext, 100);
            y3 = floatTop;
        }

        /*清除一部分虚线*/
        if (hollow_clear_square) {
            pathDr.moveTo(x1, y1);// 此点为多边形的起点
            pathDr.lineTo(x2, y2);
            pathDr.lineTo(x3, y3);
            cleanDraw(canvas, pathDr);
        }


        Path pathDr2 = new Path();
        pathDr2.moveTo(x1, y1);// 此点为多边形的起点
        pathDr2.lineTo(x2, y2);
        pathDr2.lineTo(x3, y3);

        /*说明箭头*/
        draView(canvas, pathDr2);


    }


    /**
     * 0 为TiP箭头朝上
     * 1 为TiP箭头朝下
     */
    public void setHollow_mask_orientation(int hollow_mask_orientation) {
        this.hollow_mask_orientation = hollow_mask_orientation;
    }

    /**
     * Bitmap缩小的方法
     */
    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f, bitmap.getWidth() / 2, bitmap.getHeight() / 2); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private void drawDetailBitmap(Canvas canvas) {


        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), tipBitmapRes);
//        bitmap = small(bitmap);

        float floatW = roundRect.width();
        float floatH = roundRect.height();
        float floatLeft = roundRect.left;
        float floatTop = roundRect.top;


        float x2 = floatW / 4 + px2dip(mContext, 80) + floatLeft;


        float left = x2 - bitmap.getWidth() / 3;
        float top = floatH + px2dip(mContext, 100) + floatTop + 10;

        if (hollow_mask_orientation == 2) {
            top = floatTop - px2dip(mContext, 100) - bitmap.getHeight();
        }

        canvas.drawBitmap(bitmap, left, top, null);

    }

    private void draView(Canvas canvas, Path pathDr) {
        canvas.drawPath(pathDr, dashedLinePaint);

    }

    private void cleanDraw(Canvas canvas, Path pathDr) {
        if (clearDrawPaint == null) {
            clearDrawPaint = new Paint();
            PorterDuffXfermode clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            clearDrawPaint.setXfermode(clearMode);
            clearDrawPaint.setStrokeWidth(4);//设置画笔宽度
            clearDrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            clearDrawPaint.setColor(Color.TRANSPARENT);
            clearDrawPaint.setAntiAlias(true); // 是否抗锯齿
        }

        pathDr.close();

        canvas.drawPath(pathDr, clearDrawPaint);

    }

    private void drawdashedLine(Canvas canvas) {
        if (dashedLinePaint == null) {
            dashedLinePaint = new Paint();
            dashedLinePaint.setStyle(Paint.Style.STROKE);//设置画笔style空心
            dashedLinePaint.setColor(Color.WHITE);
            dashedLinePaint.setStrokeWidth(4);//设置画笔宽度
            dashedLinePaint.setAntiAlias(true); // 是否抗锯齿

            PathEffect effect = new DashPathEffect(new float[]{30, 20, 30, 20}, 1);
            dashedLinePaint.setPathEffect(effect);
        }

        canvas.drawRoundRect(roundRect, roundRx, roundRY, dashedLinePaint);
    }

    private void cleanSquare(Canvas canvas) {
        /*
        PorterDuffXfermode  这是一个非常强大的转换模式，使用它，可以使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas图像进行交互。
        */

        if (cleanSquarePaint == null) {
            cleanSquarePaint = new Paint();
            PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            cleanSquarePaint.setXfermode(mode);
            cleanSquarePaint.setColor(Color.TRANSPARENT);
            cleanSquarePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            //设置笔刷的样式，默认为BUTT，如果设置为ROUND(圆形),SQUARE(方形)，需要将填充类型Style设置为STROKE或者FILL_AND_STROKE
            cleanSquarePaint.setStrokeCap(Paint.Cap.SQUARE);
            //设置画笔的结合方式
            cleanSquarePaint.setStrokeJoin(Paint.Join.ROUND);
        }

        canvas.drawRect(roundRect, cleanSquarePaint);
    }


    private void drawBackground(Canvas canvas) {

        if (backGroundPaint == null) {
            backGroundPaint = new Paint();
            backGroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置填满
            backGroundPaint.setAntiAlias(true); // 是否抗锯齿
        }

        backGroundPaint.setColor(hollow_mask_color);// 设置黑色

        backGroundPaint.setAlpha((int) (maskView_hollow_alpha * 255));

        RectF backRectF = new RectF(0, 0, getWidth(), getHeight());

        canvas.drawRect(backRectF, backGroundPaint);
    }

    private void initRoundRectDraftData() {


        roundRect = new RectF(hollow_margin_left - hollow_margin_right, hollow_margin_top, childViewWidth - hollow_margin_right + hollow_margin_left, childViewHeight + hollow_margin_top);
        roundRx = 10;
        roundRY = 10;
    }


    private void getViewWH() {
        if (fillView != null) {
            childViewWidth += fillView.getWidth();
            childViewHeight = fillView.getHeight();

            roundRect.left = fillView.getLeft();
            roundRect.top = fillView.getTop();
            roundRect.right = fillView.getRight();
            roundRect.bottom = fillView.getBottom();

        } else {

            /*获取默认宽高*/
            if (childViewWidth == -1) {
                childViewWidth = getWidth();
            }
            if (childViewHeight == -1) {
                childViewHeight = 500;
            }
        }

    }

    private View fillView;


    /*包裹指定的View*/
    public void setFillView(View fillViews) {
        this.fillView = fillViews;
    }


    /**
     * @param width       矩形的宽信息
     * @param height      矩形的高位置信息
     * @param marginTop   圆角
     * @param marginLeft  圆角
     * @param marginRight 圆角
     */
    public void setRoundRect(int width, int height, float marginTop, float marginLeft, float marginRight) {
        hollow_margin_top = marginTop;
        hollow_margin_left = marginLeft;
        hollow_margin_right = marginRight;

        roundRect = new RectF();

        roundRect.left = hollow_margin_left;
        roundRect.top = hollow_margin_top;
        roundRect.right = width - hollow_margin_right;
        roundRect.bottom = height + hollow_margin_top;


    }

    /**
     * @param width  矩形的宽信息
     * @param height 矩形的高位置信息
     */
    public void setRoundRect(int width, int height) {

        roundRect.right = width - hollow_margin_right;
        roundRect.bottom = height + hollow_margin_top;

        hollow_margin_left = roundRect.left;


    }

    public void setRoundRxy(float roundRx, float roundRY) {
        this.roundRx = roundRx;
        this.roundRY = roundRY;
    }

    public void setRoundRx(float roundRx) {
        this.roundRx = roundRx;
    }

    public void setRoundRy(float roundRY) {
        this.roundRY = roundRY;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public boolean isShowMask() {
        return showMask;
    }


    public void showMask() {
        showMask = true;
        invalidate();

    }

    /*用某个资源图片来显示遮罩*/
    public void showMask(@DrawableRes int tipBitmapRes) {

        this.tipBitmapRes = tipBitmapRes;
        showMask = true;
        invalidate();

    }

    /*设置遮罩*/
    public void setTipBitmapRes(@DrawableRes int tipBitmapRes) {
        this.tipBitmapRes = tipBitmapRes;
    }

    public void hindMask() {
        showMask = false;
        invalidate();
    }


    @Override
    public void invalidate() {
        super.invalidate();
        setVisibility(VISIBLE);

    }


    public float getHollow_margin_left() {
        return hollow_margin_left;
    }

    public void setHollow_margin_left(float hollow_margin_left) {
        this.hollow_margin_left = hollow_margin_left;
    }

    public float getHollow_margin_right() {
        return hollow_margin_right;
    }

    public void setHollow_margin_right(float hollow_margin_right) {
        this.hollow_margin_right = hollow_margin_right;
    }

    public float getHollow_margin_top() {
        return hollow_margin_top;
    }

    public void setHollow_margin_top(float hollow_margin_top) {
        this.hollow_margin_top = hollow_margin_top;
    }

    public float getHollow_margin_bottom() {
        return hollow_margin_bottom;
    }

    public void setHollow_margin_bottom(float hollow_margin_bottom) {
        this.hollow_margin_bottom = hollow_margin_bottom;
    }

    public boolean isHollow_clear_square() {
        return hollow_clear_square;
    }

    public void setHollow_clear_square(boolean hollow_clear_square) {
        this.hollow_clear_square = hollow_clear_square;
    }

    public float getMaskView_hollow_alpha() {
        return maskView_hollow_alpha;
    }

    public void setMaskView_hollow_alpha(float maskView_hollow_alpha) {
        this.maskView_hollow_alpha = maskView_hollow_alpha;
    }

    public boolean isAlignParentBottom() {
        return alignParentBottom;
    }

    public void setAlignParentBottom(boolean alignParentBottom) {
        this.alignParentBottom = alignParentBottom;
    }

    public boolean isAlignParentRight() {
        return alignParentRight;
    }

    public void setAlignParentRight(boolean alignParentRight) {
        this.alignParentRight = alignParentRight;
    }

    public boolean isCenterVertical() {
        return centerVertical;
    }

    public void setCenterVertical(boolean centerVertical) {
        this.centerVertical = centerVertical;
    }

    public boolean isCenterHorizontal() {
        return centerHorizontal;
    }

    public void setCenterHorizontal(boolean centerHorizontal) {
        this.centerHorizontal = centerHorizontal;
    }

    public int getHollow_mask_orientation() {
        return hollow_mask_orientation;
    }

    public void switchMask() {
        if (isShowMask()) {
            hindMask();
        } else {
            showMask();
        }
    }


}
