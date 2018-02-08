package ir.farabi.hotelpardis;

public class Room {
    private String roomNumber;
    private String bed;
    private String type;
    private String view;
    private int price;

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBed() {
        return bed;
    }
    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getview() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }

    public int getPrice() {return price;}
    public void setPrice(int price) {
        this.price = price;
    }

}
