package ev.controls;

import loon.LTexture;

/**
 * Created by liuzh on 2016/5/4.
 * EV
 */
public class Vehicle extends MapComponent
{
    private final float maxCapacity;
    private float remainingCapacity;
    private float consumption = 100;

    public Vehicle(int x, int y, int width, int height, LTexture texture, float maxCapacity)
    {
        super(x, y, width, height, texture);
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
    }

    public Vehicle(int x, int y, LTexture texture, float maxCapacity)
    {
        super(x, y, texture);
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
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
        super.move(dx, dy);
    }

    /**
     *
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

    @Override
    public String getUIName()
    {
        return "Vehicle";
    }

    @Override
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
    }
}
