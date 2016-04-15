package controls;

import greenfoot.World;

/**
 * Created by liuzh on 2016/4/15.
 */
public class FullScreenWorld extends World
{

    public FullScreenWorld(int cellSize)
    {
        super(ScenarioHelper.getFrameWidth(), ScenarioHelper.getFrameHeight(), cellSize);
    }

    public FullScreenWorld(int cellSize, boolean bounded)
    {
        super(ScenarioHelper.getFrameWidth(), ScenarioHelper.getFrameHeight(), cellSize, bounded);
    }
}
