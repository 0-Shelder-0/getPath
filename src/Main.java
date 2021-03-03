import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> stringList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() == 0) {
                findPath(stringList);
                stringList.clear();
            } else {
                stringList.add(line);
            }
        }
    }

    private static void findPath(List<String> stringList) {
        String[] stringArray = new String[stringList.size()];
        stringList.toArray(stringArray);
        PointLinkedList pointLinkedList = getPath(stringArray);
        if (pointLinkedList != null)
            printResult(pointLinkedList, stringArray);
        else
            System.out.println("The path does not exist!");
    }

    private static PointLinkedList getPath(String[] stringList) {
        Point start = getStartPoint(stringList);
        Queue<PointLinkedList> queue = new ArrayDeque<>();
        var visited = new HashSet<Point>();
        queue.add(new PointLinkedList(start));

        while (queue.size() != 0) {
            var point = queue.remove();
            if (isNotCorrect(point, visited, stringList))
                continue;
            visited.add(point.value);
            if (stringList[point.value.x].charAt(point.value.y) == 'f')
                return point;

            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++)
                    if (Math.abs(x) != Math.abs(y))
                        queue.add(getPointLinkedList(point, x, y));
        }
        return null;
    }

    private static void printResult(PointLinkedList pointLinkedList, String[] stringList) {
        List<Point> pointList = convertToPointList(pointLinkedList);
        StringBuilder[] stringBuilderList = new StringBuilder[stringList.length];

        for (int i = 0; i < stringBuilderList.length; i++) {
            stringBuilderList[i] = new StringBuilder(stringList[i]);
        }

        for (Point point : pointList) {
            stringBuilderList[point.x].deleteCharAt(point.y);
            stringBuilderList[point.x].insert(point.y, '*');
        }

        for (StringBuilder strBuilder : stringBuilderList)
            System.out.println(strBuilder.toString());
    }

    private static List<Point> convertToPointList(PointLinkedList pointLinkedList) {
        List<Point> pointList = new ArrayList<>();
        while (pointLinkedList.previous != null) {
            pointList.add(pointLinkedList.value);
            pointLinkedList = pointLinkedList.previous;
        }
        Collections.reverse(pointList);
        pointList.remove(pointList.size() - 1);
        return pointList;
    }

    private static Point getStartPoint(String[] line) {
        for (int i = 0; i < line.length; i++) {
            for (int k = 0; k < line[i].length(); k++) {
                if (line[i].charAt(k) == 's')
                    return new Point(i, k);
            }
        }
        return new Point();
    }

    private static boolean isNotCorrect(PointLinkedList point, HashSet<Point> visited, String[] stringList) {
        return point.value.x < 0 ||
                point.value.x >= stringList.length ||
                point.value.y < 0 ||
                point.value.y >= stringList[point.value.x].length() ||
                visited.contains(point.value) ||
                stringList[point.value.x].charAt(point.value.y) == '#';
    }

    private static PointLinkedList getPointLinkedList(PointLinkedList point, int x, int y) {
        return new PointLinkedList(new Point(point.value.x + x, point.value.y + y),
                new PointLinkedList(point.value, point.previous));
    }
}
