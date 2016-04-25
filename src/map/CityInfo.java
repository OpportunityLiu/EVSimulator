package map;

import ev.Resources;
import loon.Json;
import loon.utils.TArray;

/**
 * Created by liuzh on 2016/4/25.
 * List of data.
 */
public abstract class CityInfo
{
    public static void reset()
    {
        try
        {
            final Json.Array provinceList = Resources.game().json().parseArray(Resources.assets().getTextSync("map/city_center.json"));
            data = new Province[provinceList.length()];
            for(int i = 0; i < provinceList.length(); i++)
            {
                final Json.Object province = provinceList.getObject(i);
                final Json.Array cities = province.getArray("cities");
                City[] cityList = new City[cities.length()];
                for(int j = 0; j < cities.length(); j++)
                {
                    final Json.Object city = cities.getObject(j);
                    cityList[j] = (new City(city.getString("n"), MeterXY.parse(city.getString("g"))));
                }
                data[i] = new Province(province.getString("n"), cityList);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static Province[] data;

    public static Province[] data()
    {
        if(data == null)
            reset();
        return data;
    }

    public static class Province
    {
        public final String name;
        public final City[] cities;

        Province(String name, City[] cities)
        {
            this.name = name;
            this.cities = cities;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    public static class City
    {
        public final String name;
        public final MeterXY coordinate;
        private MapTile tile;

        City(String name, MeterXY coordinate)
        {
            this.name = name;
            this.coordinate = coordinate;
        }

        @Override
        public String toString()
        {
            return name;
        }

        public MapTile getTile()
        {
            if(tile == null)
            {
                final MeterXY.MapTileInfo tileInfo = coordinate.getTileOfThis(12);
                tile = MapTile.getTile(tileInfo.x, tileInfo.y, tileInfo.scale);
            }
            return tile;
        }
    }
}
