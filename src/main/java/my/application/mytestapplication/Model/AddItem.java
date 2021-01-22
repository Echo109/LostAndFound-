package my.application.mytestapplication.Model;

public class AddItem {

    private String date,Itemname,pnum,location,status,description;
    private String image;
    private String key;
    private int position;


    public AddItem() {


    }

    public AddItem (int position) {
        this.position = position;

    }

    public AddItem(String date,String Itemname, String image ,String pnum,String location,
                   String status,String Des) {
        if (Itemname.trim().equals("")) {
            Itemname = "No Name";
        }
        this.date = date;
        this.Itemname = Itemname;
        this.image = image;
        this.pnum = pnum;
        this.location = location;
        this.status = status;
        this.description = Des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemname() {

        return Itemname;
    }

    public void setItemname(String name) {
        this.Itemname = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}