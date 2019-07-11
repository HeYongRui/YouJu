package com.heyongrui.base.widget.collisionball;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.heyongrui.base.R;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;


/**
 * Created by Mr.He on 2019/3/3.
 */

public class BallView {

    private Context context;
    private World world;//世界
    private int pWidth;//父控件的宽度
    private int pHeight;//父控件的高度
    private ViewGroup mViewGroup;//父控件
    private float density = 0.2f;//物质密度
    private float friction = 0.5f;//摩擦系数
    private float restitution = 0.3f;//恢复系数
    private final Random random;
    private boolean startEnable = true;//是否开始绘制
    private int velocityIterations = 3;//迭代速度
    private int positionIterations = 10;//位置迭代
    private float dt = 1f / 60;//刷新时间
    private int ratio = 50;//物理世界与手机虚拟比例

    public BallView(Context context, ViewGroup viewGroup) {
        this.context = context;
        this.mViewGroup = viewGroup;
        random = new Random();
    }


    public void onDraw(Canvas canvas) {
        if (!startEnable) return;
        world.step(dt, velocityIterations, positionIterations);
        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mViewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.body_tag); //从view中获取绑定的刚体
            if (body != null) {
                //获取刚体的位置信息
                view.setX(metersToPixels(body.getPosition().x) - view.getWidth() / 2);
                view.setY(metersToPixels(body.getPosition().y) - view.getHeight() / 2);
                view.setRotation(radiansToDegrees(body.getAngle() % 360));
            }
        }

        mViewGroup.invalidate();//更新view的位置

    }

    /**
     * @param b
     */
    public void onLayout(boolean b) {
        createWorld(b);
    }

    /**
     * 创建物理世界
     */
    private void createWorld(boolean haveDifferent) {

        if (world == null) {
            world = new World(new Vec2(0f, 10f));//创建世界,设置重力方向
            initWorldBounds();//设置边界
        }
        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mViewGroup.getChildAt(i);
            Body body = (Body) childAt.getTag(R.id.body_tag);
            if (body == null || haveDifferent) {
                createBody(world, childAt);
            }
        }
    }

    /**
     * 创建刚体
     */
    private void createBody(World world, View view) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        //设置初始参数，为view的中心点
        bodyDef.position.set(pixelsToMeters(view.getX() + view.getWidth() / 2),
                pixelsToMeters(view.getY() + view.getHeight() / 2));
        Shape shape = null;
        Boolean isCircle = (Boolean) view.getTag(R.id.circle_tag);
        if (isCircle != null && isCircle) {
            shape = createCircle(view);
        }
        FixtureDef fixture = new FixtureDef();
        fixture.setShape(shape);
        fixture.friction = friction;
        fixture.restitution = restitution;
        fixture.density = density;

        //用世界创建出刚体
        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
        view.setTag(R.id.body_tag, body);
        //初始化物体的运动行为
        body.setLinearVelocity(new Vec2(random.nextFloat(), random.nextFloat()));

    }

    /**
     * 设置世界边界
     */
    private void initWorldBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;//设置零重力,零速度
        float bodyWidth = pixelsToMeters(pWidth);
        float bodyHeight = pixelsToMeters(pHeight);
        float bodyRatio = pixelsToMeters(ratio);
        PolygonShape polygonShape1 = new PolygonShape();
        polygonShape1.setAsBox(bodyWidth, bodyRatio);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape1;
        fixtureDef.density = 1f;//物质密度
        fixtureDef.friction = 0.3f;//摩擦系数
        fixtureDef.restitution = 0.5f;//恢复系数

        bodyDef.position.set(0, -bodyRatio);
        Body bodyTop = world.createBody(bodyDef);//世界中创建刚体
        bodyTop.createFixture(fixtureDef);//刚体添加夹具

        bodyDef.position.set(0, bodyHeight + bodyRatio);
        Body bodyBottom = world.createBody(bodyDef);//世界中创建刚体
        bodyBottom.createFixture(fixtureDef);

        PolygonShape polygonShape2 = new PolygonShape();
        polygonShape2.setAsBox(bodyRatio, bodyHeight);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = polygonShape2;
        fixtureDef2.density = 0.5f;//物质密度
        fixtureDef2.friction = 0.3f;//摩擦系数
        fixtureDef2.restitution = 0.5f;//恢复系数

        bodyDef.position.set(-bodyRatio, bodyHeight);
        Body bodyLeft = world.createBody(bodyDef);//世界中创建刚体
        bodyLeft.createFixture(fixtureDef2);//刚体添加物理属性

        bodyDef.position.set(bodyWidth + bodyRatio, 0);
        Body bodyRight = world.createBody(bodyDef);//世界中创建刚体
        bodyRight.createFixture(fixtureDef2);//刚体添加物理属性

    }

    /**
     * 创建圆形描述
     */
    private Shape createCircle(View view) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(pixelsToMeters(view.getWidth() / 2));
        return circleShape;

    }

    /**
     * 随机运动
     * 施加一个脉冲,立刻改变速度
     */
    public void rockBallByImpulse() {
        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 mImpulse = new Vec2(random.nextInt(800), random.nextInt());
            View view = mViewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.body_tag);
            if (body != null) {
                body.applyLinearImpulse(mImpulse, body.getPosition(), true);
                Log.v("btn", "有脉冲");
            } else {
                Log.v("btn", "body == null");
            }
        }
    }

    /**
     * 向指定位置移动
     */
    public void rockBallByImpulse(float x, float y) {
        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 mImpulse = new Vec2(x, y);
            View view = mViewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.body_tag);
            if (body != null) {
                body.applyLinearImpulse(mImpulse, body.getPosition(), true);
            }
        }
    }

    public float metersToPixels(float meters) {
        return meters * ratio;
    }

    public float pixelsToMeters(float pixels) {
        return pixels / ratio;
    }


    /**
     * 弧度转角度
     *
     * @param radians
     * @return
     */
    private float radiansToDegrees(float radians) {
        return radians / 3.14f * 180f;

    }

    /**
     * 大小发生变化
     *
     * @param pWidth
     * @param pHeight
     */
    public void onSizeChanged(int pWidth, int pHeight) {

        this.pWidth = pWidth;
        this.pHeight = pHeight;
    }

    private void setStartEnable(boolean b) {
        startEnable = b;
    }

    public void onStart() {
        setStartEnable(true);
    }

    public void onStop() {
        setStartEnable(false);
    }


}
