public class Scan {
    boolean isVertical;
    int rowOrColumn;
    int startPoint;
    int endPoint;

    Scan(String strInput) {
        this.isVertical = strInput.charAt(0) == 'x';
        String[] splited = strInput.split(", ");
        if (splited.length != 0) {
            try {
                this.rowOrColumn = Integer.parseInt(splited[0].replaceAll("\\D", ""));
                splited = splited[1].split("\\.\\.");
                if (splited.length != 0) {
                    this.startPoint = Integer.parseInt(splited[0].replaceAll("\\D", ""));
                    this.endPoint = Integer.parseInt(splited[1]);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
