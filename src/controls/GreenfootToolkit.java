package controls;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.lang.Runtime;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Point;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Write a description of class controls.GreenfootToolkit here.
 *
 * @author Gevater_Tod4711
 * @version (a version number or a date)
 */
public class GreenfootToolkit
{
    private static GreenfootToolkit greenfootToolkit = new GreenfootToolkit();
    
    public GreenfootToolkit()
    {
        
    }
    
    public static GreenfootToolkit getToolkit()
    {
        return greenfootToolkit;
    }
    
    
    //Methods to load, save or check files:
    
    /**
     * Saves the Strings given as the second to the last parameter in the file named like given in filename.
     *
     * @param filename          The name of the file where the Strings should be saved.
     * @param addToExistingFile If you want to add the text to an existing file this variable has to be true;
     * @param fileText          The strings that should be saved in the file.
     * @return Returns true if the file was successfully createt. False if not.
     */
    public boolean saveFile(String filename, boolean addToExistingFile, String... fileText)
    {
        List<String> existingText = loadFile(filename);
        BufferedWriter file = null;
        try
        {
            file = new BufferedWriter(new FileWriter(filename));
            if(addToExistingFile)
            {
                for(String output : existingText)
                {
                    file.write(output);
                    file.write('\n');
                }
            }
            for(String output : fileText)
            {
                file.write(output);
                file.write('\n');
            }
            file.close();
        }
        catch(IOException ioe)
        {
            //ioe.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                file.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(NullPointerException npe)
            {
                //npe.printStackTrace();
            }
        }
        return true;
    }
    
    /**
     * Saves the Strings given as the second to the last parameter in the file named like given in filename.
     *
     * @param filename          The name of the file where the Strings should be saved.
     * @param addToExistingFile If you want to add the text to an existing file this variable has to be true;
     * @param fileText          The strings that should be saved in the file as a list.
     * @return Returns true if the file was successfully createt. False if not.
     */
    public boolean saveFile(String filename, boolean addToExistingFile, java.util.List<String> fileText)
    {
        List<String> existingText = loadFile(filename);
        BufferedWriter file = null;
        try
        {
            file = new BufferedWriter(new FileWriter(filename));
            if(addToExistingFile)
            {
                for(String output : existingText)
                {
                    file.write(output);
                    file.write('\n');
                }
            }
            for(String output : fileText)
            {
                file.write(output);
                file.write('\n');
            }
            file.close();
        }
        catch(IOException ioe)
        {
            //ioe.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                file.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(NullPointerException npe)
            {
                //npe.printStackTrace();
            }
        }
        return true;
    }
    
    /**
     * Loads the text of the file whith the given filename.
     *
     * @param filename The name of the file that should be loaded.
     * @return Returns a list of Strings consisting of the text of the file.
     * Each line of the file is a new element of the list.
     */
    public java.util.List<String> loadFile(String filename)
    {
        ArrayList<String> fileText = new ArrayList<String>();
        BufferedReader file = null;
        try
        {
            file = new BufferedReader(new FileReader(filename));
            String input;
            while((input = file.readLine()) != null)
            {
                fileText.add(input);
            }
        }
        catch(FileNotFoundException fnfe)
        {
            //fnfe.printStackTrace();
            return null;
        }
        catch(IOException ioe)
        {
            //ioe.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                file.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(NullPointerException npe)
            {
                //npe.printStackTrace();
            }
        }
        return fileText;
    }
    
    /**
     * Deletes the content of a file.
     *
     * @param filename The name of the file that should be deleted.
     * @return Returns true if the file has ben deleted or if the file didn't exist.
     * Returns false if the file couldn't be deleted.
     */
    public boolean deleteFile(String filename)
    {
        File file = new File(filename);
        if(!file.delete())
        {
            BufferedWriter fileWriter = null;
            try
            {
                fileWriter = new BufferedWriter(new FileWriter(filename));
                fileWriter.write("");
                fileWriter.close();
            }
            catch(FileNotFoundException fnfe)
            {
                //fnfe.printStackTrace();
                return true;
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
                return false;
            }
            finally
            {
                try
                {
                    fileWriter.close();
                }
                catch(IOException ioe2)
                {
                    ioe2.printStackTrace();
                }
                catch(NullPointerException npe)
                {
                    //npe.printStackTrace();
                }
            }
            return true;
        }
        return true;
    }
    
    /**
     * Check whether a file with the given name is currently existing.
     *
     * @param filename The name of the file that should be checked.
     * @return Returns true if the file is existing.
     * Returns false if the file is not found or if there was a IOException.
     */
    public boolean fileExisting(String filename)
    {
        BufferedReader file = null;
        try
        {
            file = new BufferedReader(new FileReader(filename));
        }
        catch(FileNotFoundException fnfe)
        {
            //fnfe.printStackTrace();
            return false;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                file.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(NullPointerException npe)
            {
                //npe.printStackTrace();
            }
        }
        return true;
    }
    
    /**
     * Returns a list of Strings concerning the names of all existing files in the choosen directiory.
     */
    public java.util.List<String> getExistingFileNames(String path)
    {
        java.io.File file;
        List<java.io.File> files;
        ArrayList<String> fileNames = new ArrayList<String>();
        file = new java.io.File(path);
        files = Arrays.asList(file.listFiles());
        for(java.io.File temp : files)
        {
            fileNames.add(temp.getName());
        }
        return fileNames;
    }
    
    /**
     * Returns a list of all files in the choosen directory.
     */
    public java.util.List<java.io.File> getExistingFiles(String path)
    {
        java.io.File file = new java.io.File(path);
        return Arrays.asList(file.listFiles());
    }
    
    
    //Methods to load and save values in a UserInfo;
    
    /**
     * Saves the Strings given as the fifth to the last parameter in the given String of the userInfo.
     *
     * @param stringNumber      The number of the UserInfos String where this Strings should be saved. There are 10 Strings in the UserInfo so the value has to be 1 - 10.
     * @param addToExistingFile If you want to add the text to an existing UserInfo String this variable has to be true;
     * @param splitUpText       One String each of the UserInfo can at most save 50 characters. If you want your text to be splited up if it's to long this value has to be true.
     * @param info              The UserInfo object there the Strings should be saved. If this parameter is null the current UserInfo is taken if it's avaliable.
     * @param fileText          The strings that should be saved in the UserInfo Strings.
     */
    public void saveUserInfoFile(int stringNumber, boolean addToExistingFile, boolean splitUpText, UserInfo info, String... fileText) throws IllegalArgumentException, IOException
    {
        if(stringNumber > 10 || stringNumber < 1)
        {
            throw new IllegalArgumentException("stringNumber (" + stringNumber + ") has to be more that 0 and less that 11");
        }
        if(info == null)
        {
            if(UserInfo.isStorageAvailable())
            {
                info = UserInfo.getMyInfo();
            }
            else
            {
                throw new IllegalArgumentException("There is no UserInfo object where the data could be written.");
            }
        }
        List<String> existingText = null;
        List<String> splitedText = null;
        String userInfoText = "";
        if(addToExistingFile)
        {
            existingText = loadUserInfoFile(stringNumber, true, info);
            for(String text : existingText)
            {
                userInfoText += text;
                userInfoText += "\n";
            }
        }
        for(String text : fileText)
        {
            userInfoText += text;
            userInfoText += "\n";
        }
        if(userInfoText.length() > 50)
        {
            if(splitUpText)
            {
                if(userInfoText.length() > 500)
                {
                    throw new IOException("The string you are trying to write is to long. UserInfo can save at most 500 characters");
                }
                splitedText = new ArrayList<String>();
                for(int i = stringNumber - 1, j = 0; i < 10 && userInfoText.length() >= (j * 50); i++, j++)
                {
                    splitedText.add(userInfoText.substring(j * 50, (j + 1) * 50));
                }
            }
            else
            {
                throw new IOException("The string you are trying to write is to long. One string each of the UserInfo can save at most 50 characters.");
            }
        }
        if(splitedText != null)
        {
            for(int i = stringNumber - 1; i < 10 && i < splitedText.size(); i++)
            {
                info.setString(i, splitedText.get(i));
            }
        }
        else
        {
            info.setString(stringNumber - 1, userInfoText);
        }
        info.store();
    }
    
    /**
     * Saves the Strings given as the fifth to the last parameter in the given String of the userInfo.
     *
     * @param stringNumber      The number of the UserInfos String where this Strings should be saved. There are 10 Strings in the UserInfo so the value has to be 1 - 10.
     * @param addToExistingFile If you want to add the text to an existing UserInfo String this variable has to be true;
     * @param splitUpText       One String each of the UserInfo can at most save 50 characters. If you want your text to be splited up if it's to long this value has to be true.
     * @param info              The UserInfo object there the Strings should be saved. If this parameter is null the current UserInfo is taken if it's avaliable.
     * @param fileText          The strings that should be saved in the UserInfo Strings.
     */
    public void saveUserInfoFile(int stringNumber, boolean addToExistingFile, boolean splitUpText, UserInfo info, java.util.List<String> fileText) throws IllegalArgumentException, IOException
    {
        if(stringNumber > 10 || stringNumber < 1)
        {
            throw new IllegalArgumentException("stringNumber (" + stringNumber + ") has to be more that 0 and less that 11");
        }
        if(info == null)
        {
            if(UserInfo.isStorageAvailable())
            {
                info = UserInfo.getMyInfo();
            }
            else
            {
                throw new IllegalArgumentException("There is no UserInfo object where the data could be written.");
            }
        }
        List<String> existingText = null;
        List<String> splitedText = null;
        String userInfoText = "";
        if(addToExistingFile)
        {
            existingText = loadUserInfoFile(stringNumber, true, info);
            for(String text : existingText)
            {
                userInfoText += text;
                userInfoText += "\n";
            }
        }
        for(String text : fileText)
        {
            userInfoText += text;
            userInfoText += "\n";
        }
        if(userInfoText.length() > 50)
        {
            if(splitUpText)
            {
                if(userInfoText.length() > 500)
                {
                    throw new IOException("The string you are trying to write is to long. UserInfo can save at most 500 characters");
                }
                splitedText = new ArrayList<String>();
                for(int i = stringNumber - 1, j = 0; i < 10 && userInfoText.length() >= (j * 50); i++, j++)
                {
                    splitedText.add(userInfoText.substring(j * 50, (j + 1) * 50));
                }
            }
            else
            {
                throw new IOException("The string you are trying to write is to long. One string each of the UserInfo can save at most 50 characters.");
            }
        }
        if(splitedText != null)
        {
            for(int i = stringNumber - 1; i < 10 && i < splitedText.size(); i++)
            {
                info.setString(i, splitedText.get(i));
            }
        }
        else
        {
            info.setString(stringNumber - 1, userInfoText);
        }
        info.store();
    }
    
    /**
     * Loads the text of the UserInfo Strings whith the given filename.
     *
     * @param stringNumber The number of the String that should be loaded.
     * @param splitedText  If the text is splited into more strings, because a UserInfo String can at most save 50 characters, this parameter has to be true to load the whole text.
     * @param info         The UserInfo object there the Strings should be saved. If this parameter is null the current UserInfo is taken if it's avaliable.
     * @return Returns a list of Strings consisting of the text of the file.
     * Each line of the file is a new element of the list.
     */
    public java.util.List<String> loadUserInfoFile(int stringNumber, boolean splitedText, UserInfo info)
    {
        if(stringNumber > 10 || stringNumber < 1)
        {
            throw new IllegalArgumentException("stringNumber (" + stringNumber + ") has to be more that 0 and less that 11");
        }
        if(info == null)
        {
            if(UserInfo.isStorageAvailable())
            {
                info = UserInfo.getMyInfo();
            }
            else
            {
                throw new IllegalArgumentException("There is no UserInfo object where the data could be written.");
            }
        }
        ArrayList<String> fileText = new ArrayList<String>();
        String[] splitedUserInfoText;
        String userInfoText;
        int startIndex = 0;
        int endIndex = 0;
        if(splitedText)
        {
            userInfoText = info.getString(stringNumber - 1);
            if(userInfoText.length() == 50)
            {
                for(int i = stringNumber; i < 10; i++)
                {
                    userInfoText += info.getString(i);
                    if(info.getString(i).length() < 50)
                    {
                        break;
                    }
                }
            }
            splitedUserInfoText = userInfoText.split("\n");
            for(String split : splitedUserInfoText)
            {
                fileText.add(split);
            }
        }
        else
        {
            userInfoText = info.getString(stringNumber - 1);
            splitedUserInfoText = userInfoText.split("\n");
            for(String split : splitedUserInfoText)
            {
                fileText.add(split);
            }
        }
        return fileText;
    }
    
    
    //Methods to calculate distances:
    
    /**
     * Calculates the distance between the given actors location and the values for x and y.
     *
     * @param actor The Actor which location is the first point.
     * @param x     The x coordinate of the point that is the second checked point.
     * @param y     The y coordinate of the point that is the second checked point.
     * @return The distance between the actor and the point as a double value.
     * @throws NullPointerException is thrown if the actor is null.
     */
    public double getAbsoluteDistance(Actor actor, double x, double y) throws NullPointerException
    {
        return Math.hypot(x - actor.getX(), y - actor.getY());
    }

    /**
     * Calculates the distance of the given distances in x and y direction.
     *
     * @param deltaX The x distance between the two points.
     * @param deltaY The y distance between the two points.
     * @return The calculated distance as a double value.
     */
    public double getRelativeDistance(double deltaX, double deltaY)
    {
        return Math.hypot(deltaX, deltaY);
    }

    /**
     * Calculates the distance between two Actors.
     *
     * @param a The first Actor.
     * @param b The second Actor.
     * @return Returns the distance between the two Actors as a double value.
     */
    public double getObjectsDistance(Actor a, Actor b) throws NullPointerException
    {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    /**
     * Calculates the distance between two Points.
     *
     * @param p1 The first Point.
     * @param p2 The second Point.
     * @return Returns the distance between the two Points as a double value.
     */
    public double getPointsDistance(Point p1, Point p2) throws NullPointerException
    {
        return Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }
    
    
    //Methods to work on images:
    
    /**
     * Scales the image to the given size.
     *
     * @param img  The GreenfootImage that should be scaled.
     * @param size The new size of the image as a percent value.
     */
    public void scaleImage(GreenfootImage img, double size) throws NullPointerException
    {
        img.scale(((int)((double)img.getWidth() * size / 100)), (int)((double)img.getHeight() * size / 100));
    }
    
    /**
     * Change the image of an Actor the the given GreenfootImage.
     *
     * @param actor The Actor which image is changed.
     * @param img   The new image of the Actor.
     */
    public void changeImage(Actor actor, GreenfootImage img) throws NullPointerException
    {
        actor.getImage().clear();
        actor.getImage().scale(img.getWidth(), img.getHeight());
        actor.getImage().drawImage(img, 0, 0);
    }
    
    
    //Methods for statistic graphs:
    
    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius        The radius of the pie chart that is created.
     * @param percentValues The values for each part of the pie chart as percent values.
     * @param colors        The colors for each part of the pie chart.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors)
    {
        return createPieChart(radius, percentValues, colors, new String[0], false, Color.white, Color.black, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius        The radius of the pie chart that is created.
     * @param percentValues The values for each part of the pie chart as percent values.
     * @param colors        The colors for each part of the pie chart.
     * @param legend        The names of each field for the legend.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend)
    {
        return createPieChart(radius, percentValues, colors, legend, true, Color.white, Color.black, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius          The radius of the pie chart that is created.
     * @param percentValues   The values for each part of the pie chart as percent values.
     * @param colors          The colors for each part of the pie chart.
     * @param legend          The names of each field for the legend.
     * @param backgroundColor The background color of the whole chart.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend, Color backgroundColor)
    {
        return createPieChart(radius, percentValues, colors, legend, true, backgroundColor, Color.black, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius          The radius of the pie chart that is created.
     * @param percentValues   The values for each part of the pie chart as percent values.
     * @param colors          The colors for each part of the pie chart.
     * @param legend          The names of each field for the legend.
     * @param backgroundColor The background color of the whole chart.
     * @param textColor       The text color of the legend.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend, Color backgroundColor, Color textColor)
    {
        return createPieChart(radius, percentValues, colors, legend, true, backgroundColor, textColor, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius          The radius of the pie chart that is created.
     * @param percentValues   The values for each part of the pie chart as percent values.
     * @param colors          The colors for each part of the pie chart.
     * @param legend          The names of each field for the legend.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param backgroundColor The background color of the whole chart.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend, boolean displayValues, Color backgroundColor)
    {
        return createPieChart(radius, percentValues, colors, legend, displayValues, backgroundColor, Color.black, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius          The radius of the pie chart that is created.
     * @param percentValues   The values for each part of the pie chart as percent values.
     * @param colors          The colors for each part of the pie chart.
     * @param legend          The names of each field for the legend.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param backgroundColor The background color of the whole chart.
     * @param textColor       The text color of the legend.
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend, boolean displayValues, Color backgroundColor, Color textColor)
    {
        return createPieChart(radius, percentValues, colors, legend, displayValues, backgroundColor, textColor, Color.black, 5);
    }

    /**
     * Create a pie chart with the given values for the radius, the percentValues, the colors...
     *
     * @param radius          The radius of the pie chart that is created.
     * @param percentValues   The values for each part of the pie chart as percent values.
     * @param colors          The colors for each part of the pie chart.
     * @param legend          The names of each field for the chart.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param backgroundColor The background color of the whole chart.
     * @param textColor       The text color of the legend.
     * @param frameColor      The color of the frame (if there is one).
     * @param frameWidth      The width of the frame (0 = no frame).
     * @return Returns a GreenfootImage with a pie chart calculated by the given values.
     */
    public GreenfootImage createPieChart(int radius, double[] percentValues, java.awt.Color[] colors, String[] legend, boolean displayValues, Color backgroundColor, Color textColor, Color frameColor, int frameWidth)
    {
        GreenfootImage img;
        GreenfootImage legendImg;
        GreenfootImage legendField = new GreenfootImage(10, 10);
        GreenfootImage[] pieParts = new GreenfootImage[percentValues.length];
        Color removingColor;
        Color transparency = new Color(0, 0, 0, 0);
        double startingDegrees = -90.0;
        double[] degreeValues = new double[percentValues.length];
        int[] polygonX;
        int[] polygonY;
        double[] temp;
        String legendText = "";
        for(int i = 0; i < legend.length; i++)
        {
            legendText += legend[i] + (displayValues ? ": " + round(percentValues[i], 2) + "%" : "");
            legendText += "\n";
        }
        legendImg = new GreenfootImage(legendText, 20, textColor, transparency);
        img = new GreenfootImage((radius * 2 > (legendImg.getWidth() + 30) ? radius * 2 : (legendImg.getWidth() + 30)) + frameWidth * 2 + 30, radius * 2 + legendImg.getHeight() + frameWidth * 2 + 50);
        img.setColor(frameColor);
        img.fill();
        img.setColor(backgroundColor);
        img.fillRect(frameWidth, frameWidth, img.getWidth() - (frameWidth * 2), img.getHeight() - (frameWidth * 2));
        img.drawImage(legendImg, frameWidth + 30, frameWidth + radius * 2 + 40);
        for(int i = 0; i < legend.length; i++)
        {
            legendField.setColor(colors[i]);
            legendField.fill();
            img.drawImage(legendField, frameWidth + 10, frameWidth + radius * 2 + 45 + 20 * i);
        }
        for(int i = 0; i < degreeValues.length; i++)
        {
            degreeValues[i] = percentValues[i] * 3.6;
        }
        for(int i = 0; i < pieParts.length; i++)
        {
            pieParts[i] = new GreenfootImage(radius * 2, radius * 2);
            polygonX = new int[4];
            polygonY = new int[4];
            pieParts[i].setColor(colors[i]);
            pieParts[i].fillOval(0, 0, radius * 2, radius * 2);
            removingColor = (Color.black.equals(colors[i]) ? Color.white : Color.black);
            pieParts[i].setColor(removingColor);
            if(degreeValues[i] <= 180)
            {
                temp = move(radius, radius, startingDegrees, radius * 2);
                polygonX[0] = (int)temp[0];
                polygonY[0] = (int)temp[1];
                temp = move(temp[0], temp[1], startingDegrees - 90, radius * 2);
                polygonX[1] = (int)temp[0];
                polygonY[1] = (int)temp[1];
                temp = move(radius, radius, startingDegrees + 180, radius * 2);
                polygonX[3] = (int)temp[0];
                polygonY[3] = (int)temp[1];
                temp = move(temp[0], temp[1], startingDegrees - 90, radius * 2);
                polygonX[2] = (int)temp[0];
                polygonY[2] = (int)temp[1];
                pieParts[i].fillPolygon(polygonX, polygonY, 4);
                startingDegrees += degreeValues[i];
                temp = move(radius, radius, startingDegrees, radius * 2);
                polygonX[0] = (int)temp[0];
                polygonY[0] = (int)temp[1];
                temp = move(temp[0], temp[1], startingDegrees + 90, radius * 2);
                polygonX[1] = (int)temp[0];
                polygonY[1] = (int)temp[1];
                temp = move(radius, radius, startingDegrees + 180, radius * 2);
                polygonX[3] = (int)temp[0];
                polygonY[3] = (int)temp[1];
                temp = move(temp[0], temp[1], startingDegrees + 90, radius * 2);
                polygonX[2] = (int)temp[0];
                polygonY[2] = (int)temp[1];
                pieParts[i].fillPolygon(polygonX, polygonY, 4);
            }
            else
            {
                polygonX[0] = radius;
                polygonY[0] = radius;
                temp = move(radius, radius, startingDegrees, radius * 2);
                polygonX[1] = (int)temp[0];
                polygonY[1] = (int)temp[1];
                temp = move(radius, radius, startingDegrees + degreeValues[i], radius * 2);
                polygonX[3] = (int)temp[0];
                polygonY[3] = (int)temp[1];
                temp = calculateCrossingPointByAngle(new Point(polygonX[1], polygonY[1]), new Point(polygonX[3], polygonY[3]), startingDegrees - 90, startingDegrees + degreeValues[i] + 90);
                polygonX[2] = (int)temp[0];
                polygonY[2] = (int)temp[1];
                pieParts[i].fillPolygon(polygonX, polygonY, 4);
                startingDegrees += degreeValues[i];
            }
            for(int x = 0; x < radius * 2; x++)
            {
                for(int y = 0; y < radius * 2; y++)
                {
                    if(pieParts[i].getColorAt(x, y).equals(removingColor))
                    {
                        pieParts[i].setColorAt(x, y, transparency);
                    }
                }
            }
        }
        for(GreenfootImage piePart : pieParts)
        {
            img.drawImage(piePart, frameWidth + 15, frameWidth + 15);
        }
        return img;
    }
    
    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue       The maximum value that the bar chart can reach.
     * @param absoluteValues The values for each bar of the bar chart as absolute values.
     * @param colors         The colors for each bar of the bar chart.
     * @param legend         The names of each bar for the chart.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, int width, int height)
    {
        return createBarChart(maxValue, absoluteValues, colors, legend, true, width, height, Color.white, Color.black, Color.black, 5);
    }

    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue       The maximum value that the bar chart can reach.
     * @param absoluteValues The values for each bar of the bar chart as absolute values.
     * @param colors         The colors for each bar of the bar chart.
     * @param legend         The names of each bar for the chart.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @param textColor      The text color of the legend.
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, int width, int height, Color textColor)
    {
        return createBarChart(maxValue, absoluteValues, colors, legend, true, width, height, Color.white, textColor, Color.black, 5);
    }

    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue       The maximum value that the bar chart can reach.
     * @param absoluteValues The values for each bar of the bar chart as absolute values.
     * @param colors         The colors for each bar of the bar chart.
     * @param legend         The names of each bar for the chart.
     * @param displayValues  You can choose whether the values should be displayed or not.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, boolean displayValues, int width, int height)
    {
        return createBarChart(maxValue, absoluteValues, colors, legend, displayValues, width, height, Color.white, Color.black, Color.black, 5);
    }

    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue        The maximum value that the bar chart can reach.
     * @param absoluteValues  The values for each bar of the bar chart as absolute values.
     * @param colors          The colors for each bar of the bar chart.
     * @param legend          The names of each bar for the chart.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, boolean displayValues, int width, int height, Color backgroundColor)
    {
        return createBarChart(maxValue, absoluteValues, colors, legend, displayValues, width, height, backgroundColor, Color.black, Color.black, 5);
    }

    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue        The maximum value that the bar chart can reach.
     * @param absoluteValues  The values for each bar of the bar chart as absolute values.
     * @param colors          The colors for each bar of the bar chart.
     * @param legend          The names of each bar for the chart.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param textColor       The text color of the legend.
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, boolean displayValues, int width, int height, Color backgroundColor, Color textColor)
    {
        return createBarChart(maxValue, absoluteValues, colors, legend, displayValues, width, height, backgroundColor, textColor, Color.black, 5);
    }

    /**
     * Creates a bar chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue        The maximum value that the bar chart can reach.
     * @param absoluteValues  The values for each bar of the bar chart as absolute values.
     * @param colors          The colors for each bar of the bar chart.
     * @param legend          The names of each bar for the chart.
     * @param displayValues   You can choose whether the values should be displayed or not.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param textColor       The text color of the legend.
     * @param frameColor      The color of the frame (if there is one).
     * @param frameWidth      The width of the frame (0 = no frame).
     * @return Returns a GreenfootImage with a bar chart calculated by the given values.
     */
    public GreenfootImage createBarChart(double maxValue, double[] absoluteValues, java.awt.Color[] colors, String[] legend, boolean displayValues, int width, int height, Color backgroundColor, Color textColor, Color frameColor, int frameWidth)
    {
        GreenfootImage img;
        GreenfootImage barChart;
        GreenfootImage legendImg;
        GreenfootImage legendField = new GreenfootImage(10, 10);
        String legendText = "";
        double[] barHeight = new double[absoluteValues.length];
        double barWidth = ((double)width) / absoluteValues.length;
        for(int i = 0; i < legend.length; i++)
        {
            legendText += legend[i] + (displayValues ? ": " + round(absoluteValues[i], 2) : "");
            legendText += "\n";
        }
        for(int i = 0; i < absoluteValues.length; i++)
        {
            barHeight[i] = (height / maxValue) * absoluteValues[i];
        }
        legendImg = new GreenfootImage(legendText, 20, (backgroundColor.equals(Color.black) ? Color.white : Color.black), new Color(0, 0, 0, 0));
        barChart = new GreenfootImage(width + 27, height + 20);
        barChart.setColor(Color.black);
        barChart.fillRect(25, 10, width + 2, height + 2);
        barChart.setColor(Color.lightGray);
        barChart.fillRect(27, 10, width, height);
        for(int i = 0; i < 5; i++)
        {
            barChart.setColor(Color.black);
            barChart.drawLine(25, height - height / 4 * i + 10, width + 27, height - height / 4 * i + 10);
            barChart.drawImage(new GreenfootImage(Integer.toString((int)Math.round(maxValue / 4 * i)), 18, Color.black, new Color(0, 0, 0, 0)), 0, height - height / 4 * i + 3);
        }
        for(int i = 0; i < absoluteValues.length; i++)
        {
            barChart.setColor(colors[i]);
            barChart.fillRect((int)(27 + (barWidth * i)), (int)(height - barHeight[i]) + 10, (int)barWidth, (int)barHeight[i]);
        }
        img = new GreenfootImage((width > (legendImg.getWidth() + 30) ? width : (legendImg.getWidth() + 30)) + frameWidth * 2 + 50, height + frameWidth * 2 + legendImg.getHeight() + 50);
        img.setColor(frameColor);
        img.fill();
        img.setColor(backgroundColor);
        img.fillRect(frameWidth, frameWidth, img.getWidth() - (frameWidth * 2), img.getHeight() - (frameWidth * 2));
        img.drawImage(legendImg, frameWidth + 30, frameWidth + height + 45);
        for(int i = 0; i < legend.length; i++)
        {
            legendField.setColor(colors[i]);
            legendField.fill();
            img.drawImage(legendField, frameWidth + 10, frameWidth + height + 50 + 20 * i);
        }
        img.drawImage(barChart, frameWidth + 10, frameWidth + 15);
        return img;
    }
    
    /**
     * Creates a line chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue       The maximum value that the line chart can reach.
     * @param absoluteValues The values for each point of the line chart as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @return Returns a GreenfootImage with a line chart calculated by the given values.
     */
    public GreenfootImage createLineChart(double maxValue, Point[] absoluteValues, int width, int height)
    {
        return createLineChart(maxValue, absoluteValues, width, height, Color.white, Color.black, Color.black, 0);
    }

    /**
     * Creates a line chart with the given values for the max height, absolute values, colors, ...
     *
     * @param maxValue       The maximum value that the line chart can reach.
     * @param absoluteValues The values for each point of the line chart as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @param lineColor      The color of the line.
     * @return Returns a GreenfootImage with a line chart calculated by the given values.
     */
    public GreenfootImage createLineChart(double maxValue, Point[] absoluteValues, int width, int height, Color lineColor)
    {
        return createLineChart(maxValue, absoluteValues, width, height, Color.white, lineColor, Color.black, 0);
    }

    /**
     * Creates a line chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the line chart can reach.
     * @param absoluteValues  The values for each point of the line chart as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param lineColor       The color of the line.
     * @return Returns a GreenfootImage with a line chart calculated by the given values.
     */
    public GreenfootImage createLineChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color lineColor)
    {
        return createLineChart(maxValue, absoluteValues, width, height, backgroundColor, lineColor, Color.black, 0);
    }

    /**
     * Creates a line chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the line chart can reach.
     * @param absoluteValues  The values for each point of the line chart as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param lineColor       The color of the line.
     * @param frameColor      The color of the frame (if there is one).
     * @param frameWidth      The width of the frame (0 = no frame).
     * @return Returns a GreenfootImage with a line chart calculated by the given values.
     */
    public GreenfootImage createLineChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color lineColor, Color frameColor, int frameWidth)
    {
        GreenfootImage img;
        GreenfootImage lineChart;
        double maxValueX = absoluteValues[absoluteValues.length - 1].getX() + 0.1 * absoluteValues[absoluteValues.length - 1].getX();
        lineChart = new GreenfootImage(width + 40, height + 35);
        lineChart.setColor(Color.black);
        lineChart.fillRect(25, 10, width + 2, height + 2);
        lineChart.setColor(Color.lightGray);
        lineChart.fillRect(27, 10, width, height);
        for(int i = 0; i < 5; i++)
        {
            lineChart.setColor(Color.black);
            lineChart.drawLine(25, height - height / 4 * i + 10, width + 27, height - height / 4 * i + 10);
            lineChart.drawImage(new GreenfootImage(Integer.toString((int)Math.round(maxValue / 4 * i)), 18, Color.black, new Color(0, 0, 0, 0)), 0, height - height / 4 * i + 3);
            lineChart.drawLine(26 + width / 4 * i, 10, 26 + width / 4 * i, height + 10);
            lineChart.drawImage(new GreenfootImage((Integer.toString((int)Math.round(maxValueX / 4 * i))), 18, Color.black, new Color(0, 0, 0, 0)), width / 4 * i + 15, height + 15);
        }
        img = new GreenfootImage(width + 70 + frameWidth * 2, height + 55 + frameWidth * 2);
        img.setColor(frameColor);
        img.fill();
        img.setColor(backgroundColor);
        img.fillRect(frameWidth, frameWidth, img.getWidth() - (frameWidth * 2), img.getHeight() - (frameWidth * 2));
        lineChart.setColor(lineColor);
        for(int i = 0; i < absoluteValues.length - 1; i++)
        {
            lineChart.drawLine((int)(absoluteValues[i].getX() * (width / maxValueX) + 27), (int)(height + frameWidth + 5 - ((height / maxValue) * absoluteValues[i].getY())), (int)(absoluteValues[i + 1].getX() * (width / maxValueX) + 27), (int)(height + frameWidth + 5 - ((height / maxValue) * absoluteValues[i + 1].getY())));
        }
        img.drawImage(lineChart, frameWidth + 20, frameWidth + 10);
        return img;
    }
    
    /**
     * Creates a point chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue       The maximum value that the point chart can reach.
     * @param absoluteValues The values for each point as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @return Returns a GreenfootImage with a point chart calculated by the given values.
     */
    public GreenfootImage createPointChart(double maxValue, Point[] absoluteValues, int width, int height)
    {
        return createPointChart(maxValue, absoluteValues, width, height, Color.white, Color.black, Color.black, 0);
    }

    /**
     * Creates a point chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue       The maximum value that the point chart can reach.
     * @param absoluteValues The values for each point as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @param pointColor     The color of the points.
     * @return Returns a GreenfootImage with a point chart calculated by the given values.
     */
    public GreenfootImage createPointChart(double maxValue, Point[] absoluteValues, int width, int height, Color pointColor)
    {
        return createPointChart(maxValue, absoluteValues, width, height, Color.white, pointColor, Color.black, 0);
    }

    /**
     * Creates a point chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the point chart can reach.
     * @param absoluteValues  The values for each point as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param pointColor      The color of the points.
     * @return Returns a GreenfootImage with a point chart calculated by the given values.
     */
    public GreenfootImage createPointChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color pointColor)
    {
        return createPointChart(maxValue, absoluteValues, width, height, backgroundColor, pointColor, Color.black, 0);
    }

    /**
     * Creates a point chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the point chart can reach.
     * @param absoluteValues  The values for each point as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param pointColor      The color of the points.
     * @param frameColor      The color of the frame (if there is one).
     * @param frameWidth      The width of the frame (0 = no frame).
     * @return Returns a GreenfootImage with a point chart calculated by the given values.
     */
    public GreenfootImage createPointChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color pointColor, Color frameColor, int frameWidth)
    {
        GreenfootImage img;
        GreenfootImage pointChart;
        double maxValueX = absoluteValues[absoluteValues.length - 1].getX() + 0.1 * absoluteValues[absoluteValues.length - 1].getX();
        pointChart = new GreenfootImage(width + 40, height + 35);
        pointChart.setColor(Color.black);
        pointChart.fillRect(25, 10, width + 2, height + 2);
        pointChart.setColor(Color.lightGray);
        pointChart.fillRect(27, 10, width, height);
        for(int i = 0; i < 5; i++)
        {
            pointChart.setColor(Color.black);
            pointChart.drawLine(25, height - height / 4 * i + 10, width + 27, height - height / 4 * i + 10);
            pointChart.drawImage(new GreenfootImage(Integer.toString((int)Math.round(maxValue / 4 * i)), 18, Color.black, new Color(0, 0, 0, 0)), 0, height - height / 4 * i + 3);
            pointChart.drawLine(26 + width / 4 * i, 10, 26 + width / 4 * i, height + 10);
            pointChart.drawImage(new GreenfootImage((Integer.toString((int)Math.round(maxValueX / 4 * i))), 18, Color.black, new Color(0, 0, 0, 0)), width / 4 * i + 15, height + 15);
        }
        img = new GreenfootImage(width + 70 + frameWidth * 2, height + 55 + frameWidth * 2);
        img.setColor(frameColor);
        img.fill();
        img.setColor(backgroundColor);
        img.fillRect(frameWidth, frameWidth, img.getWidth() - (frameWidth * 2), img.getHeight() - (frameWidth * 2));
        pointChart.setColor(pointColor);
        for(int i = 0; i < absoluteValues.length; i++)
        {
            pointChart.fillOval((int)(absoluteValues[i].getX() * (width / maxValueX) + 27) - 2, (int)(height + frameWidth + 5 - ((height / maxValue) * absoluteValues[i].getY())) - 2, 5, 5);
        }
        img.drawImage(pointChart, frameWidth + 20, frameWidth + 10);
        return img;
    }
    
    /**
     * Creates a surface chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue       The maximum value that the point chart can reach.
     * @param absoluteValues The values for each point as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @return Returns a GreenfootImage with a surface chart calculated by the given values.
     */
    public GreenfootImage createSurfaceChart(double maxValue, Point[] absoluteValues, int width, int height)
    {
        return createSurfaceChart(maxValue, absoluteValues, width, height, Color.white, Color.black, Color.black, 0);
    }

    /**
     * Creates a surface chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue       The maximum value that the point chart can reach.
     * @param absoluteValues The values for each point as absolute values.
     * @param width          The width of the chart itselves. This value is not the width of the whole image.
     * @param height         The height of the chart itselves. This value is not the height of the whole image.
     * @param surfaceColor   The color of the surface.
     * @return Returns a GreenfootImage with a surface chart calculated by the given values.
     */
    public GreenfootImage createSurfaceChart(double maxValue, Point[] absoluteValues, int width, int height, Color surfaceColor)
    {
        return createSurfaceChart(maxValue, absoluteValues, width, height, Color.white, surfaceColor, Color.black, 0);
    }

    /**
     * Creates a surface chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the point chart can reach.
     * @param absoluteValues  The values for each point as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param surfaceColor    The color of the surface.
     * @return Returns a GreenfootImage with a surface chart calculated by the given values.
     */
    public GreenfootImage createSurfaceChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color surfaceColor)
    {
        return createSurfaceChart(maxValue, absoluteValues, width, height, backgroundColor, surfaceColor, Color.black, 0);
    }

    /**
     * Creates a surface chart with the given values for the max height, absolute values, ...
     *
     * @param maxValue        The maximum value that the point chart can reach.
     * @param absoluteValues  The values for each point as absolute values.
     * @param width           The width of the chart itselves. This value is not the width of the whole image.
     * @param height          The height of the chart itselves. This value is not the height of the whole image.
     * @param backgroundColor The background color of the whole chart.
     * @param surfaceColor    The color of the surface.
     * @param frameColor      The color of the frame (if there is one).
     * @param frameWidth      The width of the frame (0 = no frame).
     * @return Returns a GreenfootImage with a surface chart calculated by the given values.
     */
    public GreenfootImage createSurfaceChart(double maxValue, Point[] absoluteValues, int width, int height, Color backgroundColor, Color surfaceColor, Color frameColor, int frameWidth)
    {
        GreenfootImage img;
        GreenfootImage surfaceChart;
        double maxValueX = absoluteValues[absoluteValues.length - 1].getX() + 0.1 * absoluteValues[absoluteValues.length - 1].getX();
        int[] valuesX = new int[absoluteValues.length + 2];
        int[] valuesY = new int[absoluteValues.length + 2];
        surfaceChart = new GreenfootImage(width + 40, height + 35);
        surfaceChart.setColor(Color.black);
        surfaceChart.fillRect(25, 10, width + 2, height + 2);
        surfaceChart.setColor(Color.lightGray);
        surfaceChart.fillRect(27, 10, width, height);
        for(int i = 0; i < 5; i++)
        {
            surfaceChart.setColor(Color.black);
            surfaceChart.drawLine(25, height - height / 4 * i + 10, width + 27, height - height / 4 * i + 10);
            surfaceChart.drawImage(new GreenfootImage(Integer.toString((int)Math.round(maxValue / 4 * i)), 18, Color.black, new Color(0, 0, 0, 0)), 0, height - height / 4 * i + 3);
            surfaceChart.drawLine(26 + width / 4 * i, 10, 26 + width / 4 * i, height + 10);
            surfaceChart.drawImage(new GreenfootImage((Integer.toString((int)Math.round(maxValueX / 4 * i))), 18, Color.black, new Color(0, 0, 0, 0)), width / 4 * i + 15, height + 15);
        }
        img = new GreenfootImage(width + 70 + frameWidth * 2, height + 55 + frameWidth * 2);
        img.setColor(frameColor);
        img.fill();
        img.setColor(backgroundColor);
        img.fillRect(frameWidth, frameWidth, img.getWidth() - (frameWidth * 2), img.getHeight() - (frameWidth * 2));
        surfaceChart.setColor(surfaceColor);
        for(int i = 0; i < absoluteValues.length; i++)
        {
            surfaceChart.fillOval((int)(absoluteValues[i].getX() * (width / maxValueX) + 27) - 2, (int)(height + frameWidth + 5 - ((height / maxValue) * absoluteValues[i].getY())) - 2, 5, 5);
            valuesX[i] = (int)(absoluteValues[i].getX() * (width / maxValueX) + 27);
            valuesY[i] = (int)(height + frameWidth + 5 - ((height / maxValue) * absoluteValues[i].getY()));
        }
        valuesX[valuesX.length - 2] = (int)(absoluteValues[absoluteValues.length - 1].getX() * (width / maxValueX) + 27);
        valuesY[valuesX.length - 2] = (int)(height + frameWidth + 5);
        valuesX[valuesX.length - 1] = (int)(absoluteValues[0].getX() * (width / maxValueX) + 27);
        valuesY[valuesX.length - 1] = (int)(height + frameWidth + 5);
        surfaceChart.setColor(surfaceColor);
        surfaceChart.fillPolygon(valuesX, valuesY, valuesX.length);
        img.drawImage(surfaceChart, frameWidth + 20, frameWidth + 10);
        return img;
    }
    
    
    //Methods for movement:
    
    /**
     * Rotates an Actor arround a given point.
     *
     * @param rotationPoint The point arround which the Actor is rotated.
     * @param rotatedActor  The Actor that is rotated arround a point.
     * @param angle         The absolute angle the actor should be rotated.
     *                      With 0 as a rotation to the right hand side and going on clockwise.
     * @throws NullPointerException is thrown if rotatedActor or rotationPoint is null.
     */
    public void rotateArroundPoint(Point rotationPoint, Actor rotatedActor, double angle) throws NullPointerException
    {
        double distance = getPointsDistance(rotationPoint, new Point(rotatedActor.getX(), rotatedActor.getY()));
        double[] newCoordinates = move(rotationPoint.getX(), rotationPoint.getY(), angle, (int)distance);
        rotatedActor.setRotation((int)angle);
        rotatedActor.setLocation((int)newCoordinates[0], (int)newCoordinates[1]);
    }
    
    /**
     * Make the given Actor move the given distance.
     *
     * @param actor    The Actor that is moved.
     * @param distance The distance the Actor is moved.
     * @throws NullPointerException is thrown if the Actor is null.
     */
    public void move(Actor actor, double distance) throws NullPointerException
    {
        double angle = Math.toRadians(actor.getRotation());
        int x = (int)Math.round(actor.getX() + Math.cos(angle) * distance);
        int y = (int)Math.round(actor.getY() + Math.sin(angle) * distance);
        actor.setLocation(x, y);
    }

    /**
     * Make the given Actor move the given distance into a given direction.
     *
     * @param actor    The Actor that is moved.
     * @param angle    The angle to which the actor should be moved.
     * @param distance The distance the Actor is moved.
     * @throws NullPointerException is thrown if the Actor is null.
     */
    public void move(Actor actor, double angle, double distance) throws NullPointerException
    {
        angle = Math.toRadians(angle);
        actor.setLocation((int)(actor.getX() + Math.cos(angle) * distance), (int)(actor.getY() + Math.sin(angle) * distance));
    }

    /**
     * The position of an Actor that is moved the given distance into the given direction.
     *
     * @param currentX The current x coordinate of the actor.
     * @param currentY The current y coordinate of the actor.
     * @param angle    The angle to which the actor should be moved.
     * @param distance The distance the actor should be moved.
     * @return Returns the new coordinates of the actor as a double array.
     */
    public double[] move(double currentX, double currentY, double angle, double distance)
    {
        double[] coordinates = new double[2];
        angle = Math.toRadians(angle);
        coordinates[0] = currentX + Math.cos(angle) * distance;
        coordinates[1] = currentY + Math.sin(angle) * distance;
        return coordinates;
    }
    
    //Methods to calculate trigonometric things:
    
    /**
     * Let the actor bounce of of the world edge.
     *
     * @param actor The actor that should bounce off at the worlds edge.
     */
    public void bounceOffWorldEdge(Actor actor) throws NullPointerException
    {
        if(actor.getX() < 20)
        {
            if(actor.getRotation() < 180)
            {
                actor.setRotation(180 - actor.getRotation());
            }
            else if(actor.getRotation() >= 180)
            {
                actor.setRotation((180 - actor.getRotation()));
            }
            if(actor.getX() < 20)
            {
                for(int i = 0; i < 5 && actor.getX() < 20; i++)
                {
                    actor.setLocation(actor.getX() + 5, actor.getY());
                }
            }
        }
        else if(actor.getX() > actor.getWorld().getWidth() - 20)
        {
            if(actor.getRotation() <= 90)
            {
                actor.setRotation(180 - actor.getRotation());
            }
            else if(actor.getRotation() > 270)
            {
                actor.setRotation(180 + (360 - actor.getRotation()));
            }
            if(actor.getX() > actor.getWorld().getWidth() - 20)
            {
                for(int i = 0; i < 5 && actor.getX() > actor.getWorld().getWidth() - 20; i++)
                {
                    actor.setLocation(actor.getX() - 5, actor.getY());
                }
            }
        }
        else if(actor.getY() < 20)
        {
            if(actor.getRotation() <= 270)
            {
                actor.setRotation(90 + (270 - actor.getRotation()));
            }
            else if(actor.getRotation() > 270)
            {
                actor.setRotation(90 - (actor.getRotation() - 270));
            }
            if(actor.getY() < 20)
            {
                for(int i = 0; i < 5 && actor.getY() < 20; i++)
                {
                    actor.setLocation(actor.getX(), actor.getY() + 5);
                }
            }
        }
        else if(actor.getY() > actor.getWorld().getHeight() - 20)
        {
            if(actor.getRotation() <= 90)
            {
                actor.setRotation(270 + (90 - actor.getRotation()));
            }
            else if(actor.getRotation() > 90)
            {
                actor.setRotation(270 - (actor.getRotation() - 90));
            }
            if(actor.getY() > actor.getWorld().getHeight() - 20)
            {
                for(int i = 0; i < 5 && actor.getY() > actor.getWorld().getHeight() - 20; i++)
                {
                    actor.setLocation(actor.getX(), actor.getY() - 5);
                }
            }
        }
    }
    
    /**
     * Calculate the angle from the first to the second point.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @return Returns the angle from the first to the second point.
     */
    public double getAngle(Point p1, Point p2)
    {
        return direction(p2.getX() - p1.getX(), p2.getY() - p1.getY());
    }
    
    /**
     * Make the given actor turn to a given point.
     *
     * @param actor The actor that should be truned.
     * @param x     The x coordinate of the point the actor should turn to.
     * @param y     The y coordinate of the point the actor should turn to.
     */
    public void turnTo(Actor actor, double x, double y) throws NullPointerException
    {
        setDirection(actor, x - actor.getX(), y - actor.getY());
    }

    /**
     * The angle the actor must turn to look in the direction of the point.
     *
     * @param actor The actor that should be truned.
     * @param x     The x coordinate of the point the actor should turn to.
     * @param y     The y coordinate of the point the actor should turn to.
     */
    public double directionToTurnTo(Actor actor, double x, double y) throws NullPointerException
    {
        return direction(x - actor.getX(), y - actor.getY());
    }
    
    /**
     * The angle the actor must turn to look in the direction of a point given as the vectors from the current location.
     *
     * @param deltaX The change in X direction of the point the actor should turn to.
     * @param deltaY The change in Y direction of the point the actor should turn to.
     */
    public double direction(double deltaX, double deltaY)
    {
        double direction;
        if(deltaX == 0 && deltaY == 0)
        {
            direction = 0;
        }
        else if(deltaX == 0 && deltaY < 0)
        {
            direction = -90;
        }
        else if(deltaX == 0 && deltaY > 0)
        {
            direction = 90;
        }
        else if(deltaX > 0 && deltaY == 0)
        {
            direction = 0;
        }
        else if(deltaX < 0 && deltaY == 0)
        {
            direction = 180;
        }
        else
        {
            int w = (int)Math.toDegrees(Math.atan((double)deltaY / (double)deltaX));
            if(deltaX > 0)
            {
                direction = w;
            }
            else
            {
                direction = 180 + w;
            }
        }
        return direction;
    }

    /**
     * Make the given actor turn to a point given as the vectors from the current location.
     *
     * @param actor  The actor that should be truned.
     * @param deltaX The change in X direction of the point the actor should turn to.
     * @param deltaY The change in Y direction of the point the actor should turn to.
     */
    public void setDirection(Actor actor, double deltaX, double deltaY) throws NullPointerException
    {
        int direction;
        if(deltaX == 0 && deltaY == 0)
        {
            direction = 0;
        }
        else if(deltaX == 0 && deltaY < 0)
        {
            direction = -90;
        }
        else if(deltaX == 0 && deltaY > 0)
        {
            direction = 90;
        }
        else if(deltaX > 0 && deltaY == 0)
        {
            direction = 0;
        }
        else if(deltaX < 0 && deltaY == 0)
        {
            direction = 180;
        }
        else
        {
            int w = (int)Math.toDegrees(Math.atan((double)deltaY / (double)deltaX));
            if(deltaX > 0)
            {
                direction = w;
            }
            else
            {
                direction = 180 + w;
            }
        }
        actor.setRotation(direction);
    }
    
    
    //Other methods:
    
    /**
     * Execute a DOS command.
     *
     * @param command The command that should be executed.
     */
    public void exec(String command)
    {
        try
        {
            Runtime.getRuntime().exec(command);
        }
        catch(IOException ioe)
        {
            //ioe.printstackTrace();
        }
    }
    
    /**
     * Check whether the actor is at the edge of the world.
     *
     * @param actor The actor that is checked.
     * @return Returs true if the actor is not more that 20px away from the worlds edge.
     * @throws NullPointerException is thrown if the actor is null or if it doesn't exist in a world.
     */
    public boolean atWorldEdge(Actor actor) throws NullPointerException
    {
        if(actor != null && actor.getWorld() != null)
        {
            if(actor.getX() < 20 || actor.getX() > actor.getWorld().getWidth() - 20)
            {
                return true;
            }
            if(actor.getY() < 20 || actor.getY() > actor.getWorld().getHeight() - 20)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check whether the mouse is currently on the image of an Actor.
     *
     * @param actor The checked actor.
     * @return Returns true if the mouse is on the image of the actor.
     */
    public boolean mouseOnObject(Actor actor)
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        return mouse != null && actor != null && mouse.getX() > actor.getX() - actor.getImage().getWidth() / 2 && mouse.getX() < actor.getX() + actor.getImage().getWidth() / 2 &&
                mouse.getY() > actor.getY() - actor.getImage().getHeight() / 2 && mouse.getY() < actor.getY() + actor.getImage().getHeight() / 2;
    }
    
    /**
     * Check whether the game is played online (on the Greenfoot webside) or offline.
     *
     * @return Returns true if the game is currently played online.
     */
    public boolean playingOnline()
    {
        try
        {
            BufferedWriter file = new BufferedWriter(new FileWriter("checkingFile_dh48ch30ch3c5.txt"));
            file.write("Test String");
            file.close();
            deleteFile("checkingFile_dh48ch30ch3c5.txt");
        }
        catch(Exception e)
        {
            //ioe.printStackTrace();
            return true;
        }
        return false;
    }
    
    /**
     * Round a double value to a variable number of decimal plases.
     *
     * @param value         The double value that should be rounded.
     * @param decimalPlaces The number of decimal places you want to round the double value to.
     * @return The double value roundet to the given number of decimal places.
     */
    public double round(double value, int decimalPlaces)
    {
        return (((double)Math.round(value * Math.pow(10, decimalPlaces))) / Math.pow(10, decimalPlaces));
    }
    
    /**
     * Calculates the crossing point of two lines that are calculated by a point of each line and the angle of each line.
     *
     * @param p1     Any point of the first line.
     * @param p2     Any point of the second line.
     * @param angle1 The angle of the first line in degrees (0 => vertical line, 90 => horizontal line).
     * @param angle2 The angle of the second line in degrees (0 => vertical line, 90 => horizontal line).
     * @return The coordinates of the crossing point of the two lines as a double array.
     */
    public double[] calculateCrossingPointByAngle(Point p1, Point p2, double angle1, double angle2)
    {
        double[] crossingPoint = new double[2];
        double[] coordinatesP2 = {p2.getX() - p1.getX(), p2.getY() - p1.getY()};
        double slope1 = (angle1 == 90 ? Math.pow(10, 10) : (angle1 == 270 ? -Math.pow(10, 10) : Math.tan(Math.toRadians(angle1))));
        double slope2 = (angle2 == 90 ? Math.pow(10, 10) : (angle2 == 270 ? -Math.pow(10, 10) : Math.tan(Math.toRadians(angle2))));
        double yAxisSectionP2 = coordinatesP2[1] - (coordinatesP2[0] * slope2);
        crossingPoint[0] = yAxisSectionP2 / (slope1 - slope2);
        crossingPoint[1] = slope1 * crossingPoint[0];
        return crossingPoint;
    }

    /**
     * Calculates the crossing point of two lines that are calculated by a point of each line and the slope of each line.
     *
     * @param p1     Any point of the first line.
     * @param p2     Any point of the second line.
     * @param slope1 The slope of the first line.
     * @param slope2 The slope of the second line.
     * @return The coordinates of the crossing point of the two lines as a double array.
     */
    public double[] calculateCrossingPointBySlope(Point p1, Point p2, double slope1, double slope2)
    {
        double[] crossingPoint = new double[2];
        double[] coordinatesP2 = {p2.getX() - p1.getX(), p2.getY() - p1.getY()};
        double yAxisSectionP2 = coordinatesP2[1] - (coordinatesP2[0] * slope2);
        crossingPoint[0] = yAxisSectionP2 / (slope1 - slope2);
        crossingPoint[1] = slope1 * crossingPoint[0];
        return crossingPoint;
    }
}