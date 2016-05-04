package ev.controls;

import loon.LTexture;
import loon.component.LComponent;

/**
 * Created by liuzh on 2016/5/4.
 * Charging-pile
 */
public class Pile extends MapComponent
{
    private float maxPower = 10000;
    private float totalComsumption;

    private boolean charging;

    private Vehicle vehicle;

    public Pile(int x, int y, int width, int height, LTexture texture)
    {
        super(x, y, width, height, texture);
    }

    public Pile(int x, int y, LTexture texture)
    {
        super(x, y, texture);
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public float getMaxPower()
    {
        return maxPower;
    }

    public void setMaxPower(float maxPower)
    {
        this.maxPower = maxPower;
    }

    public float getTotalComsumption()
    {
        return totalComsumption;
    }

    public void resetTotalComsumption()
    {
        this.totalComsumption = 0;
    }

    public boolean isCharging()
    {
        return charging;
    }

    @Override
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        if(vehicle != null)
        {
            float energy = maxPower * elapsedTime * getController().timeScale();
            charging = !vehicle.charge(energy);
            if(charging)
                totalComsumption += energy;
        }
        else
        {
            charging = false;
        }
    }

    @Override
    public String getUIName()
    {
        return "Pile";
    }
}
