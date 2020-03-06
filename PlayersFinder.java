import eg.edu.alexu.csd.datastructure.iceHockey.sarah.IPlayersFinder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayersFinder implements IPlayersFinder {

    Point highest = new Point();
    Point lowest = new Point();
    float area = 0;
    int maxRows;
    int maxColumns;
    List<Point> players = new ArrayList<>();
    List<Integer> check = new ArrayList<>();
    public Point[] findPlayers(String[] photo, int team, int threshold) {
        if(photo == null)
            throw new NullPointerException("Can't handle null arrays");
        if(photo.length == 0)
            throw new IllegalArgumentException("Can't handle zero-length arrays.");
            maxRows = photo.length;
            maxColumns = photo[0].length();
            int Row = 0;
            while (Row < maxRows) {
                int i = photo[Row].indexOf((char) (team + '0'), highest.x );
                if (i != -1 && !check.contains(i + (Row) * maxColumns)) {
                    area = 1;
                    lowest.setLocation(i, Row);
                    highest.setLocation(i, Row);
                    check.add(i + highest.y * maxColumns);
                    search(photo, team, highest.y, i);
                    if (area >= (float)threshold / 4) {
                        players.add(calculateCoordinates());
                    }
                }
                else if(i != -1 && check.contains(i + (Row) * maxColumns))
                    highest.x ++;
                    else{
                        Row++;
                        highest.x = 0;
                    }
                }

            Point[] myArray = new Point[players.size()];
            players.toArray(myArray);
            return bubbleSort(myArray);
        }

    public void search (String[] photo,int team,int column, int i) {

        if (i+1<maxColumns && photo[column].charAt(i+1) == (char)(team+'0')&& !check.contains(i+1 + (column) * maxColumns)) {
            highest.x=Math.max(i + 1, highest.x);
            check.add(i+1+column*maxColumns);
            area++;
            search(photo,team,column,i+1);
        }
        if (column+1< maxRows && photo[column+1].charAt(i) == (char)(team+'0') && !check.contains(i + (column + 1) * maxColumns)) {
            highest.y=Math.max(column + 1, highest.y);
            check.add(i+(column+1)*maxColumns);
            area++;
            search(photo,team,column+1,i);
        }
        if (i>0 && photo[column].charAt(i-1) == (char)(team+'0')&& !check.contains(i-1 + (column) * maxColumns)) {
            lowest.x=Math.min(i - 1, lowest.x);
            area++;
            check.add(i-1+column*maxColumns);
            search(photo,team,column,i-1);
        }
        if (column>0 && photo[column-1].charAt(i) == (char)(team+'0') && !check.contains(i + (column - 1) * maxColumns)) {
            lowest.y=Math.min(column - 1, lowest.y);
            area++;
            check.add(i+(column-1)*maxColumns);
            search(photo,team,column-1,i);
        }
    }

    public Point calculateCoordinates ()
    {
        Point center = new Point();
        center.x = highest.x+lowest.x+1;
        center.y = highest.y+lowest.y+1;
        return center;
    }
    public static Point[] bubbleSort(Point[] arr)
    {
        Point temp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j].x>arr[j+1].x)
                {
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
                else if (arr[j].x==arr[j+1].x && (arr[j].y>arr[j+1].y))
                {
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }

        }
        return arr;
    }

}
