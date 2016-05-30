package ev.controls;

import loon.LTexture;
import loon.canvas.LColor;
import loon.component.LComponent;
import loon.geom.Affine2f;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import loon.utils.MathUtils;
import map.Direction;
import map.LongLat;
import map.MeterXY;
import map.controls.TileMap;
import org.jetbrains.annotations.NotNull;
import sun.misc.Queue;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuzh on 2016/5/4.
 * EV
 */
public class Vehicle extends MapComponent
{
    private final float maxCapacity;
    private float remainingCapacity;
    private float consumption = 1;
    private Pile currentPile;
    private static ArrayList<Pile> piles;
    private Vector2f departure, destination;

    private LTexture image;

    private int state;
    private float speed = 0.05f;

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public static ArrayList<Pile> getPiles()
    {
        return piles;
    }

    public static void setPiles(ArrayList<Pile> piles)
    {
        Vehicle.piles = piles;
    }

    public Vehicle(int x, int y, int width, int height, LTexture texture, float maxCapacity)
    {
        super(x, y, width, height, texture);
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
        this.image = texture;
        setSize(width, height);
    }

    public Vehicle(int x, int y, LTexture texture, float maxCapacity)
    {
        super(x, y, texture);
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
        this.image = texture;
    }

    public void setDeparture(Vector2f departure)
    {
        this.departure = departure;
    }

    public Vector2f getDeparture()
    {
        return departure;
    }

    public void setDestination(Vector2f destination)
    {
        this.destination = destination;
    }

    public Vector2f getDestination()
    {
        return destination;
    }

    public float getConsumption()
    {
        return consumption;
    }

    public void setConsumption(float consumption)
    {
        this.consumption = consumption;
    }

    public float getMaxCapacity()
    {
        return maxCapacity;
    }

    public float getRemainingCapacity()
    {
        return remainingCapacity;
    }

    @Override
    public void move(float dx, float dy)
    {
        if(remainingCapacity <= 0)
            return;
        double distance = Math.hypot(dx, dy);
        remainingCapacity -= distance * consumption;
        setRotation(MathUtils.atan2(dy, dx) * MathUtils.RAD_TO_DEG + 90);
        super.move(dx, dy);
    }

    /**
     * @param energy
     * @return is full
     */
    public boolean charge(float energy)
    {
        remainingCapacity += energy;
        if(remainingCapacity >= maxCapacity)
        {
            remainingCapacity = maxCapacity;
            return true;
        }
        return false;
    }

    public Pile getCurrentPile()
    {
        return currentPile;
    }

    public void setCurrentPile(Pile currentPile)
    {
        this.currentPile = currentPile;
    }

    @Override
    public String getUIName()
    {
        return "Vehicle";
    }

    public void start()
    {
        state = 1;
        setX(departure.x);
        setY(departure.y);
    }

    private List<Vector2f> route;

    private MeterXY pixelsToCoordinates(Vector2f p)
    {
        Vector2f tf = map.pixelsToTiles(p.x, p.y);
        return map.tilesToCoordinates(tf.x, tf.y);
    }

    private Vector2f coordinatesToPixels(MeterXY p)
    {
        Vector2f tf = map.coordinatesToTiles(p);
        return map.tilesToPixels(tf.x, tf.y);
    }

    private void setRoute(Vector2f from, Vector2f to)
    {
        try
        {
            MeterXY f = pixelsToCoordinates(from);
            MeterXY t = pixelsToCoordinates(to);
            List<LongLat> r = Direction.getDirection(f, t, map.getRegion());
            route = r.stream().map(p -> coordinatesToPixels(p.toMeterXY())).collect(Collectors.toList());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void moveNext(long elapsedTime)
    {
        Vector2f next = route.get(0);
        float dx = next.x - getX();
        float dy = next.y - getY();
        Vector2f dv = new Vector2f(dx, dy);
        float distance = elapsedTime * speed;
        if(dv.length() <= distance)
        {
            move(dx, dy);
            route.remove(0);
            if(route.isEmpty())
                route = null;
            return;
        }
        Vector2f move = dv.nor().mul(distance);
        move(move.x, move.y);
    }

    private Vector2f nearestPile()
    {
        Vector2f ret = null;
        float minDistance = Float.MAX_VALUE;
        for(Pile p : piles)
        {
            float distance = MathUtils.dist(getX(), getY(), p.getX(), p.getY());
            if(distance < minDistance)
            {
                minDistance = distance;
                ret = p.getLocation().cpy();
                targetPile = p;
            }
        }
        return ret;
    }

    Pile targetPile;

    @Override
    public void createUI(GLEx g)
    {
        g.draw(image, screenX - getWidth() / 2, screenY - getHeight() / 2, getWidth(), getHeight(), getRotation());
        g.drawText(remainingCapacity + "/" + maxCapacity, screenX, screenY + getHeight(), LColor.black, 0);
    }

    @Override
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        switch(state)
        {
        case 1://to destination
            if(route == null)
            {
                setRoute(_location, destination);
            }
            moveNext(elapsedTime);
            if(route == null)
                state = 7;
            if(remainingCapacity / maxCapacity < 0.2)
            {
                route = null;
                state = 3;
            }
            break;
        case 2://to departure
            if(route == null)
            {
                setRoute(_location, departure);
            }
            moveNext(elapsedTime);
            if(route == null)
                state = 8;
            if(remainingCapacity / maxCapacity < 0.2)
            {
                route = null;
                state = 4;
            }
            break;
        case 3://to destination - to charge
            if(route == null)
            {
                setRoute(_location, nearestPile());
            }
            moveNext(elapsedTime);
            if(route == null)
                state = 5;
            break;
        case 4://to departure - to charge
            if(route == null)
            {
                setRoute(_location, nearestPile());
            }
            moveNext(elapsedTime);
            if(route == null)
                state = 6;
            break;
        case 5://to destination - charging
            if(currentPile == null)
            {
                currentPile = targetPile;
            }
            if(charge(currentPile.getPower() * elapsedTime))
            {
                currentPile = null;
                targetPile = null;
                state = 1;
            }
            break;
        case 6://to departure - charging
            if(currentPile == null)
            {
                currentPile = targetPile;
            }
            if(charge(currentPile.getPower() * elapsedTime))
            {
                currentPile = null;
                targetPile = null;
                state = 2;
            }
            break;
        case 7://arrived destination
            delay += elapsedTime;
            if(delay >= 3000)
            {
                delay = 0;
                state = 2;
            }
            break;
        case 8://arrived departure - charging
            delay += elapsedTime;
            if(delay >= 3000)
            {
                delay = 0;
                state = 1;
            }
            break;
        }
    }

    private long delay;

    @Override
    public boolean intersects(float x1, float y1)
    {
        return super.intersects(x1 + getWidth() / 2, y1 + getHeight() / 2);
    }
}
