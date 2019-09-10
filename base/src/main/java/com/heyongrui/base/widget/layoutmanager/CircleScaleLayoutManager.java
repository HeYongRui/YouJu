package com.heyongrui.base.widget.layoutmanager;

import android.content.Context;
import android.view.View;

/**
 * An implementation of {@link ViewPagerLayoutManager}
 * which layouts item in a circle and will change the child's centerScale while scrolling
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class CircleScaleLayoutManager extends ViewPagerLayoutManager {
    public static final int LEFT = 10;
    public static final int RIGHT = 11;
    public static final int TOP = 12;
    public static final int BOTTOM = 13;

    public static final int LEFT_ON_TOP = 4;
    public static final int RIGHT_ON_TOP = 5;
    public static final int CENTER_ON_TOP = 6;

    private int radius;
    private int angleInterval;
    private float moveSpeed;
    private float centerScale;
    private float maxRemoveAngle;
    private float minRemoveAngle;
    private int gravity;
    private boolean flipRotate;
    private int zAlignment;

    public CircleScaleLayoutManager(Context context) {
        this(new Builder(context));
    }

    public CircleScaleLayoutManager(Context context, int gravity, boolean reverseLayout) {
        this(new Builder(context).setGravity(gravity).setReverseLayout(reverseLayout));
    }

    public CircleScaleLayoutManager(Context context, boolean reverseLayout) {
        this(new Builder(context).setReverseLayout(reverseLayout));
    }

    public CircleScaleLayoutManager(Builder builder) {
        this(builder.context, builder.radius, builder.angleInterval, builder.centerScale, builder.moveSpeed,
                builder.maxRemoveAngle, builder.minRemoveAngle, builder.gravity, builder.zAlignment,
                builder.flipRotate, builder.maxVisibleItemCount, builder.distanceToBottom, builder.reverseLayout);
    }

    private CircleScaleLayoutManager(Context context, int radius, int angleInterval, float centerScale,
                                     float moveSpeed, float max, float min, int gravity, int zAlignment,
                                     boolean flipRotate, int maxVisibleItemCount, int distanceToBottom, boolean reverseLayout) {
        super(context, HORIZONTAL, reverseLayout);
        setEnableBringCenterToFront(true);
        setMaxVisibleItemCount(maxVisibleItemCount);
        setDistanceToBottom(distanceToBottom);
        this.radius = radius;
        this.angleInterval = angleInterval;
        this.centerScale = centerScale;
        this.moveSpeed = moveSpeed;
        this.maxRemoveAngle = max;
        this.minRemoveAngle = min;
        this.gravity = gravity;
        this.flipRotate = flipRotate;
        this.zAlignment = zAlignment;
    }

    public int getRadius() {
        return radius;
    }

    public int getAngleInterval() {
        return angleInterval;
    }

    public float getCenterScale() {
        return centerScale;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getMaxRemoveAngle() {
        return maxRemoveAngle;
    }

    public float getMinRemoveAngle() {
        return minRemoveAngle;
    }

    public int getGravity() {
        return gravity;
    }

    public boolean getFlipRotate() {
        return flipRotate;
    }

    public int getZAlignment() {
        return zAlignment;
    }

    public void setRadius(int radius) {
        assertNotInLayoutOrScroll(null);
        if (this.radius == radius) return;
        this.radius = radius;
        removeAllViews();
    }

    public void setAngleInterval(int angleInterval) {
        assertNotInLayoutOrScroll(null);
        if (this.angleInterval == angleInterval) return;
        this.angleInterval = angleInterval;
        removeAllViews();
    }

    public void setCenterScale(float centerScale) {
        assertNotInLayoutOrScroll(null);
        if (this.centerScale == centerScale) return;
        this.centerScale = centerScale;
        requestLayout();
    }

    public void setMoveSpeed(float moveSpeed) {
        assertNotInLayoutOrScroll(null);
        if (this.moveSpeed == moveSpeed) return;
        this.moveSpeed = moveSpeed;
    }

    public void setMaxRemoveAngle(float maxRemoveAngle) {
        assertNotInLayoutOrScroll(null);
        if (this.maxRemoveAngle == maxRemoveAngle) return;
        this.maxRemoveAngle = maxRemoveAngle;
        requestLayout();
    }

    public void setMinRemoveAngle(float minRemoveAngle) {
        assertNotInLayoutOrScroll(null);
        if (this.minRemoveAngle == minRemoveAngle) return;
        this.minRemoveAngle = minRemoveAngle;
        requestLayout();
    }

    public void setGravity(int gravity) {
        assertNotInLayoutOrScroll(null);
        assertGravity(gravity);
        if (this.gravity == gravity) return;
        this.gravity = gravity;
        if (gravity == LEFT || gravity == RIGHT) {
            setOrientation(VERTICAL);
        } else {
            setOrientation(HORIZONTAL);
        }
        requestLayout();
    }

    public void setFlipRotate(boolean flipRotate) {
        assertNotInLayoutOrScroll(null);
        if (this.flipRotate == flipRotate) return;
        this.flipRotate = flipRotate;
        requestLayout();
    }

    public void setZAlignment(int zAlignment) {
        assertNotInLayoutOrScroll(null);
        assertZAlignmentState(zAlignment);
        if (this.zAlignment == zAlignment) return;
        this.zAlignment = zAlignment;
        requestLayout();
    }

    @Override
    protected float setInterval() {
        return angleInterval;
    }

    @Override
    protected void setUp() {
        radius = radius == Builder.INVALID_VALUE ? mDecoratedMeasurementInOther : radius;
    }

    @Override
    protected float maxRemoveOffset() {
        return maxRemoveAngle;
    }

    @Override
    protected float minRemoveOffset() {
        return minRemoveAngle;
    }

    @Override
    protected int calItemLeft(View itemView, float targetOffset) {
        switch (gravity) {
            case LEFT:
                return (int) (radius * Math.sin(Math.toRadians(90 - targetOffset)) - radius);
            case RIGHT:
                return (int) (radius - radius * Math.sin(Math.toRadians(90 - targetOffset)));
            case TOP:
            case BOTTOM:
            default:
                return (int) (radius * Math.cos(Math.toRadians(90 - targetOffset)));
        }
    }

    @Override
    protected int calItemTop(View itemView, float targetOffset) {
        switch (gravity) {
            case LEFT:
            case RIGHT:
                return (int) (radius * Math.cos(Math.toRadians(90 - targetOffset)));
            case TOP:
                return (int) (radius * Math.sin(Math.toRadians(90 - targetOffset)) - radius);
            case BOTTOM:
            default:
                return (int) (radius - radius * Math.sin(Math.toRadians(90 - targetOffset)));
        }
    }

    @Override
    protected void setItemViewProperty(View itemView, float targetOffset) {
        float scale = 1f;
        switch (gravity) {
            case RIGHT:
            case TOP:
                if (flipRotate) {
                    itemView.setRotation(targetOffset);
                    if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                        float diff = Math.abs(Math.abs(itemView.getRotation() - angleInterval) - angleInterval);
                        scale = (centerScale - 1f) / -angleInterval * diff + centerScale;
                    }
                } else {
                    itemView.setRotation(360 - targetOffset);
                    if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                        float diff = Math.abs(Math.abs(360 - itemView.getRotation() - angleInterval) - angleInterval);
                        scale = (centerScale - 1f) / -angleInterval * diff + centerScale;
                    }
                }
                break;
            case LEFT:
            case BOTTOM:
            default:
                if (flipRotate) {
                    itemView.setRotation(360 - targetOffset);
                    if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                        float diff = Math.abs(Math.abs(360 - itemView.getRotation() - angleInterval) - angleInterval);
                        scale = (centerScale - 1f) / -angleInterval * diff + centerScale;
                    }
                } else {
                    itemView.setRotation(targetOffset);
                    if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                        float diff = Math.abs(Math.abs(itemView.getRotation() - angleInterval) - angleInterval);
                        scale = (centerScale - 1f) / -angleInterval * diff + centerScale;
                    }
                }
                break;
        }
        itemView.setScaleX(scale);
        itemView.setScaleY(scale);
    }

    @Override
    protected float setViewElevation(View itemView, float targetOffset) {
        if (zAlignment == LEFT_ON_TOP)
            return (540 - targetOffset) / 72;
        else if (zAlignment == RIGHT_ON_TOP)
            return (targetOffset - 540) / 72;
        else
            return (360 - Math.abs(targetOffset)) / 72;
    }

    @Override
    protected float getDistanceRatio() {
        if (moveSpeed == 0) return Float.MAX_VALUE;
        return 1 / moveSpeed;
    }

    private static void assertGravity(int gravity) {
        if (gravity != LEFT && gravity != RIGHT && gravity != TOP && gravity != BOTTOM) {
            throw new IllegalArgumentException("gravity must be one of LEFT RIGHT TOP and BOTTOM");
        }
    }

    private static void assertZAlignmentState(int zAlignment) {
        if (zAlignment != LEFT_ON_TOP && zAlignment != RIGHT_ON_TOP && zAlignment != CENTER_ON_TOP) {
            throw new IllegalArgumentException("zAlignment must be one of LEFT_ON_TOP RIGHT_ON_TOP and CENTER_ON_TOP");
        }
    }

    public static class Builder {
        private static int INTERVAL_ANGLE = 30;// The default mInterval angle between each items
        private static float DISTANCE_RATIO = 10f; // Finger swipe distance divide item rotate angle
        private static final float SCALE_RATE = 1.2f;
        private static int INVALID_VALUE = Integer.MIN_VALUE;

        private int radius;
        private int angleInterval;
        private float centerScale;
        private float moveSpeed;
        private float maxRemoveAngle;
        private float minRemoveAngle;
        private boolean reverseLayout;
        private Context context;
        private int gravity;
        private boolean flipRotate;
        private int zAlignment;
        private int maxVisibleItemCount;
        private int distanceToBottom;

        public Builder(Context context) {
            this.context = context;
            radius = INVALID_VALUE;
            angleInterval = INTERVAL_ANGLE;
            centerScale = SCALE_RATE;
            moveSpeed = 1 / DISTANCE_RATIO;
            maxRemoveAngle = 90;
            minRemoveAngle = -90;
            reverseLayout = false;
            flipRotate = false;
            gravity = BOTTOM;
            zAlignment = CENTER_ON_TOP;
            distanceToBottom = ViewPagerLayoutManager.INVALID_SIZE;
            maxVisibleItemCount = ViewPagerLayoutManager.DETERMINE_BY_MAX_AND_MIN;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setAngleInterval(int angleInterval) {
            this.angleInterval = angleInterval;
            return this;
        }

        public Builder setCenterScale(float centerScale) {
            this.centerScale = centerScale;
            return this;
        }

        public Builder setMoveSpeed(int moveSpeed) {
            this.moveSpeed = moveSpeed;
            return this;
        }

        public Builder setMaxRemoveAngle(float maxRemoveAngle) {
            this.maxRemoveAngle = maxRemoveAngle;
            return this;
        }

        public Builder setMinRemoveAngle(float minRemoveAngle) {
            this.minRemoveAngle = minRemoveAngle;
            return this;
        }

        public Builder setReverseLayout(boolean reverseLayout) {
            this.reverseLayout = reverseLayout;
            return this;
        }

        public Builder setGravity(int gravity) {
            assertGravity(gravity);
            this.gravity = gravity;
            return this;
        }

        public Builder setFlipRotate(boolean flipRotate) {
            this.flipRotate = flipRotate;
            return this;
        }

        public Builder setZAlignment(int zAlignment) {
            assertZAlignmentState(zAlignment);
            this.zAlignment = zAlignment;
            return this;
        }

        public Builder setMaxVisibleItemCount(int maxVisibleItemCount) {
            this.maxVisibleItemCount = maxVisibleItemCount;
            return this;
        }

        public Builder setDistanceToBottom(int distanceToBottom) {
            this.distanceToBottom = distanceToBottom;
            return this;
        }

        public CircleScaleLayoutManager build() {
            return new CircleScaleLayoutManager(this);
        }
    }
}
